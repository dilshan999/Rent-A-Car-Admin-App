package com.group11.rentacaradmin;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    Context context;
    List<VehicleModel> vehicleModelList;

    public RecyclerAdapter(Context context, List<VehicleModel> vehicleModelList) {
        this.context = context;
        this.vehicleModelList = vehicleModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_raw,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final VehicleModel vehicleModel = vehicleModelList.get(position);
        holder.name.setText(vehicleModel.getBrand());


        String imageUri = null;
        imageUri = vehicleModel.getImageUrl();
        // Picasso.get().load(imageUri).into(holder.imageView);

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),EditActivity.class);
                i.putExtra("VehicleID",vehicleModel.getVehicleID());
                i.putExtra("Brand",vehicleModel.getBrand());
                v.getContext().startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return vehicleModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name;
        //TextView price;
        Button btnUpdate, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.vehicle_img);
            name = itemView.findViewById(R.id.vehicle_name);
            //price = itemView.findViewById(R.id.vehicle_name);
            // btnUpdate = itemView.findViewById(R.id.editBtn);

        }
    }

}
