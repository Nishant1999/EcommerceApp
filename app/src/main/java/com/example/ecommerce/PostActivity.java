package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.model.PostImage;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;


public class PostActivity extends AppCompatActivity {

    private Uri imageUri;
    private String imageUrl;
    private ImageView closeIV,imageAddedIV;
    private TextView postTV;
    private SocialAutoCompleteTextView descriptionSACTV;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        initialize();
        setListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            imageUri=result.getUri();

            imageAddedIV.setImageURI(imageUri);
        }
        else{
            Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this,MainActivity.class));
            finish();
        }
    }


    private void initialize(){
        closeIV=findViewById(R.id.activity_post_image_close_iv);
        imageAddedIV=findViewById(R.id.activity_post_image_added_iv);
        postTV=findViewById(R.id.activity_post_tv);
        descriptionSACTV=findViewById(R.id.activity_post_social_textview_stv);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    private void setListener() {

        closeIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PostActivity.this,MainActivity.class));
                finish();
            }
        });

        postTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadToFirebase();
            }
        });

        CropImage.activity().start(PostActivity.this);
    }

    private void uploadToFirebase() {

        progressDialog.setMessage("Uploading");
        progressDialog.show();

        if (imageUri != null) {

            StorageReference filePath = FirebaseStorage.getInstance()
                    .getReference("Posts")
                    .child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            UploadTask uploadTask = filePath.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        imageUrl=downloadUri.toString();

                        savePostToFirestore();

                    } else {
                        Toast.makeText(PostActivity.this, "Uploading Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private String getFileExtension(Uri uri) {
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(this.getContentResolver().getType(uri));
    }

    private void savePostToFirestore() {

        ArrayList<String> hashTags= (ArrayList<String>) descriptionSACTV.getHashtags();
        PostImage post=new PostImage();

        DocumentReference postDocRef = firebaseFirestore.collection("Posts").document();
        post.setPostId(postDocRef.getId());
        post.setImageUrl(imageUrl);
        post.setDescription(descriptionSACTV.getText().toString());
        post.setUserId(firebaseAuth.getUid());
        post.setHashTags(hashTags);

        postDocRef.set(post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                startActivity(new Intent(PostActivity.this,MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PostActivity.this, "Message:"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}