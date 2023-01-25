package com.example.iyeppeo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Update extends AppCompatActivity {

    ImageView updategambar;
    Button updateButton;
    EditText updatekomentar, updatenama, updatenamaproduk;
    String NamaReview, NamaProdukReview, KomentarReview;
    String imageUrl;
    String key, oldImageURL;
    Uri uri;
    DatabaseReference databaseReference;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateButton = findViewById(R.id.updateButton);
        updatekomentar = findViewById(R.id.updatekomentar);
        updatenama = findViewById(R.id.updatenama);
        updatenamaproduk = findViewById(R.id.updatenamaproduk);
        updategambar = findViewById(R.id.updateGambar);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            updategambar.setImageURI(uri);
                        } else {
                            Toast.makeText(Update.this, "Tidak Ada Gambar dipilih!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            Glide.with(Update.this).load(bundle.getString("GambarReview")).into(updategambar);
            updatenama.setText(bundle.getString("NamaReview"));
            updatenamaproduk.setText(bundle.getString("NamaProdukReview"));
            updatekomentar.setText(bundle.getString("KomentarReview"));
            key = bundle.getString("Key");
            oldImageURL = bundle.getString("GambarReview");
        }
        databaseReference = FirebaseDatabase.getInstance().getReference("reviewuser").child(key);
        updategambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(Update.this, Review.class);
                startActivity(intent);
            }
        });
    }
    public void saveData(){
        storageReference = FirebaseStorage.getInstance().getReference().child("review").child(uri.getLastPathSegment());
        AlertDialog.Builder builder = new AlertDialog.Builder(Update.this);
        builder.setCancelable(false);
        builder.setView(R.layout.proses_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                updateData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }
    public void updateData(){
        NamaReview = updatenama.getText().toString().trim();
        NamaProdukReview = updatenamaproduk.getText().toString().trim();
        KomentarReview = updatekomentar.getText().toString().trim();

        Helper3Class dataClass = new Helper3Class(NamaReview, imageUrl, NamaProdukReview, KomentarReview);
        databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(oldImageURL);
                    reference.delete();
                    Toast.makeText(Update.this, "Diperbarui", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Update.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}