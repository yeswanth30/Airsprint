package com.airsprint.Adapters;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airsprint.Models.Delivery;
import com.airsprint.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class  TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {
    private List<Delivery> deliveryList;
    private Context context;

    public TrackAdapter(Context context, List<Delivery> deliveryList) {
        this.context = context;
        this.deliveryList = deliveryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shipping, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Delivery delivery = deliveryList.get(position);

        holder.Trackingid.setText(delivery.getTrackingId());
        holder.selecteddate.setText(delivery.getSelectedDate());
        holder.source.setText(delivery.getSource());
        holder.destination.setText(delivery.getDestination());
        holder.detailedaddress.setText(delivery.getDetailedAddress());

//        Picasso.get().load(delivery.getImageUrl()).into(holder.imageView);
//
//        holder.totalLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DeliveryDetailsActivity.class);
//                intent.putExtra("deliveryId", delivery.getDeliveryId());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Trackingid;
        TextView selecteddate;
        TextView source,destination,detailedaddress;
//        ImageView imageView;
//        RelativeLayout totalLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Trackingid = itemView.findViewById(R.id.Trackingid);
            selecteddate = itemView.findViewById(R.id.selecteddate);
            source = itemView.findViewById(R.id.source);
            destination = itemView.findViewById(R.id.destination);
            detailedaddress = itemView.findViewById(R.id.detailedaddress);
        }
    }
}
