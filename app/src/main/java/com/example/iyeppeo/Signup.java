package com.example.iyeppeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signup extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://iyeppeodb-default-rtdb.firebaseio.com/");

    TextInputLayout signupnamalengkap, signupusername, signuppassword;
    Button btn_signup;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupnamalengkap = findViewById(R.id.namalengkap_signup);
        signupusername = findViewById(R.id.username_signup);
        signuppassword = findViewById(R.id.password_signup);
        btn_signup = findViewById(R.id.btnsignup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");

                String nama = String.valueOf(signupnamalengkap.getEditText().getText());
                String username = String.valueOf(signupusername.getEditText().getText());
                String password = String.valueOf(signuppassword.getEditText().getText());

                HelperClass helperClass =  new HelperClass(nama, username, password);
                reference.child(username).setValue(helperClass);

                Toast.makeText(Signup.this, "Sukses", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
            }
        });
    }
}