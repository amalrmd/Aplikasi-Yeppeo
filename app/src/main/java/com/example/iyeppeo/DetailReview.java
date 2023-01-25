package com.example.iyeppeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailReview extends AppCompatActivity {

    TextView detailKomentar, detailNamaProduk, detailNama, back;
    ImageView detailGambar;
    FloatingActionButton deleteButton, editButton;
    String key = "";
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_review);

        detailKomentar = findViewById(R.id.detailKomentar);
        detailGambar = findViewById(R.id.detailGambar);
        detailNamaProduk = findViewById(R.id.detailNamaProduk);
        deleteButton = findViewById(R.id.deleteButton);
        editButton = findViewById(R.id.editButton);
        detailNama = findViewById(R.id.detailNama);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailReview.this, Review.class);
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            detailKomentar.setText(bundle.getString("KomentarReview"));
            detailNamaProduk.setText(bundle.getString("NamaProdukReview"));
            detailNama.setText(bundle.getString("NamaReview"));
            key = bundle.getString("Key");
            imageUrl = bundle.getString("GambarReview");
            Glide.with(this).load(bundle.getString("GambarReview")).into(detailGambar);
        }
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("reviewuser");
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        reference.child(key).removeValue();
                        Toast.makeText(DetailReview.this, "Terhapus", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), Review.class));
                        finish();
                    }
                });
            }
        });
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailReview.this, Update.class)
                        .putExtra("NamaProdukReview", detailNamaProduk.getText().toString())
                        .putExtra("KomentarReview", detailKomentar.getText().toString())
                        .putExtra("NamaReview", detailNama.getText().toString())
                        .putExtra("GambarReview", imageUrl)
                        .putExtra("Key", key);
                startActivity(intent);
            }
        });
    }
}