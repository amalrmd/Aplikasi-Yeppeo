package com.example.iyeppeo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Upload extends AppCompatActivity {

    ImageView uploadGambar;
    Button uploadButton;
    EditText uploadnama, uploadnamaproduk, uploadkomentar;
    String imageURL;
    Uri uri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_upload);

        uploadGambar = findViewById(R.id.uploadGambar);
        uploadnama = findViewById(R.id.uploadNama);
        uploadnamaproduk = findViewById(R.id.uploadNamaProduk);
        uploadkomentar = findViewById(R.id.uploadKomentar);
        uploadButton = findViewById(R.id.uploadButton);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            uploadGambar.setImageURI(uri);
                        }
                        else {
                            Toast.makeText(Upload.this, "Tidak Ada Gambar yang Dipilih", Toast.LENGTH_SHORT).show();
                        }
            }
        });
        uploadGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pilihfoto = new Intent(Intent.ACTION_PICK);
                pilihfoto.setType("image/*");
                activityResultLauncher.launch(pilihfoto);
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("review").child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(Upload.this);
        builder.setCancelable(false);
        builder.setView(R.layout.proses_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlGambar = uriTask.getResult();
                imageURL = urlGambar.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void uploadData(){
        String namarreview = uploadnama.getText().toString();
        String namaprodukreview = uploadnamaproduk.getText().toString();
        String komentarreview = uploadkomentar.getText().toString();

        Helper3Class dataclass = new Helper3Class(namarreview,imageURL,namaprodukreview,komentarreview);
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmssSS");
        Date myDate = new Date();
        String filename = timeStampFormat.format(myDate);

        FirebaseDatabase.getInstance().getReference("reviewuser").child(filename).setValue(dataclass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Upload.this, "Tersimpan", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Upload.this,e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}