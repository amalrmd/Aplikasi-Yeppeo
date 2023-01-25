package com.example.iyeppeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Review extends AppCompatActivity {

    FloatingActionButton fab;
    RecyclerView recyclerView;
    DatabaseReference database;
    RecycleViewReviewAdapter myAdapter;
    ArrayList<Helper3Class> list;
    ValueEventListener eventListener;
    SearchView searchView;
    TextView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recycleView);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        back = findViewById(R.id.back);

        database = FirebaseDatabase.getInstance().getReference("reviewuser");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Review.this,1);
        recyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(Review.this);
        builder.setCancelable(false);
        builder.setView(R.layout.proses_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        list = new ArrayList<>();
        myAdapter = new RecycleViewReviewAdapter(Review.this,list);
        recyclerView.setAdapter(myAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Review.this, MainActivity.class);
                startActivity(intent);
            }
        });

        eventListener = database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()){
                    Helper3Class review = itemSnapshot.getValue(Helper3Class.class);
                    review.setKey(itemSnapshot.getKey());
                    list.add(review);
                }
                myAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Review.this, Upload.class);
                startActivity(intent);
            }
        });
    }
    public void searchList(String text){
        ArrayList<Helper3Class> searchList = new ArrayList<>();
        for (Helper3Class review: list){
            if (review.getNamarreview().toLowerCase().contains(text.toLowerCase())){
                searchList.add(review);
            }
        }
        myAdapter.searchDataList(searchList);
    }
}