package com.example.iyeppeo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://iyeppeodb-default-rtdb.firebaseio.com/");

    Button btn_login;
    TextView lnksignup,lupapassword;
    TextInputLayout loginusername, loginpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = findViewById(R.id.btn_login);
        lnksignup = findViewById(R.id.lnksignup);
        lupapassword = findViewById(R.id.lupapassword);
        loginusername = findViewById(R.id.username_login);
        loginpassword = findViewById(R.id.password_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!validateUsername() | !validatePassword()){

                }
                else{
                    checkUser();
                }
            }
        });

        lnksignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Login.this, Signup.class);
                startActivity(intent);
            }
        });

    }

    public Boolean validateUsername(){
        String val = String.valueOf(loginusername.getEditText().getText());
        if(val.isEmpty()){
            loginusername.setError("Username Harus Diisi!");
            return false;
        }
        else{
            loginusername.setError(null);
            return true;
        }
    }

    public Boolean validatePassword(){
        String val = String.valueOf(loginpassword.getEditText().getText());
        if(val.isEmpty()){
            loginpassword.setError("Password Harus Diisi!");
            return false;
        }
        else{
            loginpassword.setError(null);
            return true;
        }
    }

    public void checkUser(){
        String userUsername = String.valueOf(loginusername.getEditText().getText());
        String userPassword = String.valueOf(loginpassword.getEditText().getText());

        DatabaseReference reference =  FirebaseDatabase.getInstance().getReference("users");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setCancelable(false);
        builder.setView(R.layout.proses_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    loginusername.setError(null);
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userPassword)){
                        loginusername.setError(null);

                        String namaFromDB = snapshot.child(userUsername).child("nama").getValue(String.class);
                        String usernameFromDB = snapshot.child(userUsername).child("username").getValue(String.class);


                        Intent intent = new Intent(Login.this, MainActivity.class);

                        intent.putExtra("nama", namaFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                        dialog.dismiss();
                    }
                    else{
                        loginpassword.setError("Password Salah");
                        loginpassword.requestFocus();
                        dialog.dismiss();
                    }
                }
                else{
                    loginusername.setError("Username Tidak ditemukan");
                    loginusername.requestFocus();
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();

            }
        });
    }
}