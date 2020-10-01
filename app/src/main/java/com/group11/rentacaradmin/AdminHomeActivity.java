package com.group11.rentacaradmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private ProgressBar progress;
    private Button edit;

    private FirebaseStorage storage;
    private DatabaseReference databaseRef;
    private ValueEventListener DBListener;
    private List<VehicleModel> vehicles;
    private Context mContext;
    public ImageView newImage;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        newImage = findViewById(R.id.imageEdt);
        //edit = findViewById(R.id.editBtn);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //progress = findViewById(R.id.progresscycle);
        vehicles = new ArrayList<>();

        adapter = new ImageAdapter(AdminHomeActivity.this, vehicles);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(AdminHomeActivity.this);


        storage=FirebaseStorage.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("Vehicle");

        drawerLayout = findViewById(R.id.drawer_layout1);

        DBListener =databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                vehicles.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    VehicleModel vehicleModel = postSnapshot.getValue(VehicleModel.class);
                    vehicleModel.setVehicleID(postSnapshot.getKey());
                    vehicles.add(vehicleModel);
                }

                adapter.notifyDataSetChanged();
                // progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AdminHomeActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.INVISIBLE);
            }
        });


    }


    public void onItemClick(int position) {
        Toast.makeText(AdminHomeActivity.this, "Normal click at position"+ position, Toast.LENGTH_SHORT).show();

        //   Upload up = new Upload();
        VehicleModel selectedItem = vehicles.get(position);

        Intent in = new Intent(AdminHomeActivity.this,EditActivity.class);
        in.putExtra("photo",selectedItem.getImageUrl());
        in.putExtra("price",selectedItem.getPrice());
        in.putExtra("brand",selectedItem.getBrand());
        in.putExtra("id",selectedItem.getVehicleID());
        in.putExtra("passengers",selectedItem.getPassengers());
        in.putExtra("transmission",selectedItem.getTransmission());

        startActivity(in);




        System.out.println(selectedItem.getImageUrl());
        System.out.println(selectedItem.getVehicleID());
    }

/*    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(AdminHomeActivity.this, "whtever click at position"+position, Toast.LENGTH_SHORT).show();


    } */

    @Override
    public void onDeleteClick(int position) {
        VehicleModel selectedItem = vehicles.get(position);
        final String selectedkey = selectedItem.getVehicleID();

        StorageReference imageref = storage.getReferenceFromUrl(selectedItem.getImageUrl());
        imageref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseRef.child(selectedkey).removeValue();
                Toast.makeText(AdminHomeActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickClose(View view){
        closeDrawer(drawerLayout);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        recreate();
    }

    public void ClickAdd(View view){
        Intent intent = new Intent(AdminHomeActivity.this,AddVehicleActivity.class);
        startActivity(intent);
    }

    public void ClickSignOut(View view){
        // Signout(this);
    }


}