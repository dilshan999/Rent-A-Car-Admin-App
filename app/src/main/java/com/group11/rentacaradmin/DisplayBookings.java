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

public class DisplayBookings extends AppCompatActivity implements ImageAdapter2.OnItemClickListener {

    private RecyclerView recyclerView;
    private ImageAdapter2 adapter;
    private ProgressBar progress;
    private Button edit;

    private FirebaseStorage storage;
    private DatabaseReference databaseRef;
    private ValueEventListener DBListener;
    private List<Booking> bookings;
    private Context mContext;
    public ImageView newImage;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_bookings);

        newImage = findViewById(R.id.imageEdt);
        //edit = findViewById(R.id.editBtn);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //progress = findViewById(R.id.progresscycle);
        bookings = new ArrayList<>();

        adapter = new ImageAdapter2(DisplayBookings.this, bookings);
        recyclerView.setAdapter(adapter);
        //adapter.setOnItemClickListener(DisplayBookings.this);


        storage=FirebaseStorage.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("Booking");

        drawerLayout = findViewById(R.id.drawer_layout1);

        DBListener =databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookings.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Booking bookingModel = postSnapshot.getValue(Booking.class);
                    bookingModel.setModel(postSnapshot.getKey());
                    bookings.add(bookingModel);
                }

                adapter.notifyDataSetChanged();
                // progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DisplayBookings.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.INVISIBLE);
            }
        });


    }


    public void onItemClick(int position) {

    }

/*    @Override
    public void onWhatEverClick(int position) {
        Toast.makeText(AdminHomeActivity.this, "whtever click at position"+position, Toast.LENGTH_SHORT).show();


    } */


    @Override
    public void onDeleteClick(int position) {
        Booking selectedItem = bookings.get(position);
        final String selectedkey = selectedItem.getCusPhone();

        StorageReference imageref = storage.getReferenceFromUrl(selectedItem.getCusPhone());
        imageref.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseRef.child(selectedkey).removeValue();
                Toast.makeText(DisplayBookings.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //nav Drawer

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
        Intent intent = new Intent(DisplayBookings.this,AddVehicleActivity.class);
        startActivity(intent);
    }

    public void ClickAddFaq(View view){
        Intent intent = new Intent(DisplayBookings.this,MainActivity.class);
        startActivity(intent);
    }

    public void ClickSignOut(View view){
        // Signout(this);
    }


}