package com.group11.rentacaradmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AddVehicleActivity extends AppCompatActivity {

    private Button addImage,addVehicle;
    private EditText passengers,price,brand;
    private Spinner transmission;
    private TextView mTextViewShowUploads;
    private ImageView imageView;
    private ProgressBar mProgressBar;
    private Uri imageUri;
    private static final int PICK_IMAGE_REQUEST = 1;


    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private StorageTask uploadTask;
    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);

        addImage = findViewById(R.id.uploadImgBtn);
        addVehicle = findViewById(R.id.add_vehicle);
        price = findViewById(R.id.price);
        brand = findViewById(R.id.brand);
        passengers = findViewById(R.id.passengers);
        transmission = findViewById(R.id.transmission);
        mProgressBar = findViewById(R.id.progress_bar);
        imageView = findViewById(R.id.image);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        storageRef = FirebaseStorage.getInstance().getReference("Vehicle");
        databaseRef = FirebaseDatabase.getInstance().getReference("Vehicle");

        //progressDialog = new ProgressDialog(this);

        //Add Validation For Price
        //awesomeValidation.addValidation(this,R.id.price, RegexTemplate.NOT_EMPTY,R.string.invalid_price);
        awesomeValidation.addValidation(this,R.id.price, "[0-9]{4}$",R.string.invalid_price);

        //Add Validation For Brand
        awesomeValidation.addValidation(this,R.id.brand, RegexTemplate.NOT_EMPTY,R.string.invalid_brand);

        //Add Validation For Passengers
        awesomeValidation.addValidation(this,R.id.passengers, RegexTemplate.NOT_EMPTY,R.string.invalid_passengers);


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();
            }
        });
        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    if (uploadTask != null && uploadTask.isInProgress()) {
                        Toast.makeText(AddVehicleActivity.this, "Uploading..", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Vehicleupload();
                    }
                }
            }
        });

      /*  mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });
*/



    }
    private void FileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
            Picasso.with(this).load(imageUri).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(imageView);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void Vehicleupload() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("uploading");
        pd.show();

        if (imageUri != null && awesomeValidation.validate()) {

            StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 300);

                            pd.dismiss();
                            Toast.makeText(AddVehicleActivity.this, "Vehicle Added Successfully", Toast.LENGTH_LONG).show();
                            VehicleModel vehicleModel = new VehicleModel(taskSnapshot.getDownloadUrl().toString(),Double.parseDouble(price.getText().toString().trim()),brand.getText().toString().trim(),
                                    Integer.parseInt(passengers.getText().toString().trim()), (String) transmission.getSelectedItem());

                            String uploadId = databaseRef.push().getKey();
                            databaseRef.child(uploadId).setValue(vehicleModel);
                        }
                    })




                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddVehicleActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

  /*  private void openImagesActivity() {
        Intent intent = new Intent(this, ImagesActivity.class);
        startActivity(intent);
    }*/
}