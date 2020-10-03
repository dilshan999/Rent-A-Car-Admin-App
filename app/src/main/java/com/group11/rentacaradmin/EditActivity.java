package com.group11.rentacaradmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    ImageView imageChange;
    EditText editBrand, editPass, editPrice;
    Spinner trans;
    Button update, choose, cancelBtn;
    String brand;
    int pass;
    Double price;
    String spinner;

    String myimage, myimage2, myimage3;
    private Context mContext;
    String id;
    VehicleModel vm;
    DatabaseReference Ref;
    private Uri ImageUri;

    private StorageReference StorageRef;
    private DatabaseReference DatabaseRef;

    private StorageTask UpdateTask;

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        StorageRef = FirebaseStorage.getInstance().getReference("Vehicle");
        DatabaseRef = FirebaseDatabase.getInstance().getReference("Vehicle");

        imageChange = findViewById(R.id.imageEdt);
        editBrand = findViewById(R.id.brandEdt);
        update = findViewById(R.id.update);
        choose = findViewById(R.id.changeImg);
        editPass = findViewById(R.id.passengersEdt);
        editPrice = findViewById(R.id.priceEdt);
        trans = findViewById(R.id.transmissionEdit);
        cancelBtn = findViewById(R.id.cancelEdtBtn);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        vm = new VehicleModel();

        //Add Validation For Price
        awesomeValidation.addValidation(this,R.id.priceEdt, "[0-9]{4}$",R.string.invalid_price);

        //Add Validation For Brand
        awesomeValidation.addValidation(this,R.id.brandEdt, RegexTemplate.NOT_EMPTY,R.string.invalid_brand);

        //Add Validation For Passengers
        awesomeValidation.addValidation(this,R.id.passengersEdt, RegexTemplate.NOT_EMPTY,R.string.invalid_passengers);

        getData();
        setData();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {

                    uploadFile();

                }
            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditActivity.this,AdminHomeActivity.class);
                startActivity(intent);
            }
        });


    }

    private void openFileChooser() {
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
            ImageUri = data.getData();
            imageChange.setImageURI(ImageUri);
            Picasso.with(this).load(ImageUri).into(imageChange);
        }


    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {

        if (ImageUri != null) {

            StorageReference fileReference = StorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(ImageUri));
            UpdateTask = fileReference.putFile(ImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //    mProgressBar.setProgress(0);
                                }
                            }, 300);

                            myimage2=   taskSnapshot.getDownloadUrl().toString();


                            DatabaseReference uRef = FirebaseDatabase.getInstance().getReference().child("Vehicle").child(id);
                            uRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // if(dataSnapshot.hasChild(key)){

                                    vm.setImageUrl(myimage2.toString().trim());
                                    vm.setPrice(Double.parseDouble(editPrice.getText().toString().trim()));
                                    vm.setBrand(editBrand.getText().toString().trim());
                                    vm.setPassengers(Integer.parseInt(editPass.getText().toString().trim()));
                                    vm.setTransmission((String) trans.getSelectedItem());

                                    Ref=FirebaseDatabase.getInstance().getReference().child("Vehicle").child(id);
                                    Ref.setValue(vm);

                                    Toast.makeText(EditActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(EditActivity.this, "Not Updated successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });


        }else{

            DatabaseReference upref = FirebaseDatabase.getInstance().getReference().child("Vehicle").child(id);
            upref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // if(dataSnapshot.hasChild(key)){

                    vm.setImageUrl(myimage.toString().trim());
                    vm.setPrice(Double.parseDouble(editPrice.getText().toString().trim()));
                    vm.setBrand(editBrand.getText().toString().trim());
                    vm.setPassengers(Integer.parseInt(editPass.getText().toString().trim()));
                    vm.setTransmission((String) trans.getSelectedItem());

                    Ref=FirebaseDatabase.getInstance().getReference().child("Vehicle").child(id);
                    Ref.setValue(vm);

                    Toast.makeText(EditActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(EditActivity.this, "Not Updated successfully", Toast.LENGTH_SHORT).show();
                }
            });


        }


    }

    private void getData(){
        if(getIntent().hasExtra("brand") && getIntent().hasExtra("photo") ){

            brand = getIntent().getStringExtra("brand");
            myimage = getIntent().getStringExtra("photo");
            id = getIntent().getStringExtra("id");
            pass = getIntent().getIntExtra("passengers",1);
            price = getIntent().getDoubleExtra("price",5.00);
            spinner = getIntent().getStringExtra("transmission");

            System.out.println(spinner);

        }
        else
            Toast.makeText(this,"no data",Toast.LENGTH_SHORT).show();
    }

    private void setData(){


        //   updimage.setImageResource(Integer.parseInt(myimage));
        Picasso.with(mContext)
                .load(myimage)
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(imageChange);

        //updimage.setImageURI(myimage);
        editBrand.setText(brand);


        editPass.setText(String.valueOf(pass));
        editPrice.setText(String.valueOf(price));
        //trans.getSelectedItem()=pspinner;

        if(spinner.equals("Automatic")){

            List<String> category = new ArrayList<>();
            category.add("Automatic");
            category.add("Manual");

            ArrayAdapter<String> dataAdapter;
            dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,category);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            trans.setAdapter(dataAdapter);

        }
        else{


            List<String> category = new ArrayList<>();
            category.add("Manual");
            category.add("Automatic");

            ArrayAdapter<String> dataAdapter;
            dataAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,category);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            trans.setAdapter(dataAdapter);


        }


    }


}