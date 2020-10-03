package com.group11.rentacaradmin;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<VehicleModel> vehicles;
    private OnItemClickListener mListener;

    public ImageAdapter(Context context, List<VehicleModel> uploads) {
        mContext = context;
        vehicles = uploads;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.my_raw, parent, false);
        return new ImageViewHolder(v);
    }
    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        VehicleModel uploadCurrent = vehicles.get(position);
        holder.viewBrand.setText(uploadCurrent.getBrand());
        holder.viewPrice.setText(String.valueOf(uploadCurrent.getPrice()));
        // holder.viewPassengers.setText(String.valueOf(uploadCurrent.getPassengers()));
        //  holder.viewTransmission.setText(String.valueOf(uploadCurrent.getTransmission()));
        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return vehicles.size();
    }
    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnCreateContextMenuListener , MenuItem.OnMenuItemClickListener {
        public TextView viewBrand,viewPrice,viewPassengers,viewTransmission;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            viewBrand = itemView.findViewById(R.id.vehicle_name);
            viewPrice = itemView.findViewById(R.id.vehicle_price);
            imageView = itemView.findViewById(R.id.vehicle_img);
            //  viewPassengers = itemView.findViewById(R.id.viewPass);
            //  viewTransmission = itemView.findViewById(R.id.viewTrans);

            itemView.setOnClickListener(this);

            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mListener!=null){
                int position =getAdapterPosition();
                if(position!= RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select");
            // MenuItem dowhatever = contextMenu.add(Menu.NONE,1,1,"Update");
            MenuItem delete = contextMenu.add(Menu.NONE,1,1,"Delete");

            //dowhatever.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if(mListener!=null){
                int position =getAdapterPosition();
                if(position!= RecyclerView.NO_POSITION){
                    switch(menuItem.getItemId()){

                       /* case 1 :
                            mListener.onWhatEverClick(position);
                            return  true;*/
                        case 1:
                            mListener.onDeleteClick(position);
                            return  true;
                    }
                }
            }
            return false;
        }
    }

    public interface OnItemClickListener{

        void onItemClick(int position);
        // void onWhatEverClick(int position);
        void onDeleteClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener){

        mListener = listener;

    }



}
