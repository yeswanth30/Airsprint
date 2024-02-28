package com.airsprint.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.airsprint.Models.Delivery;
import com.airsprint.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> {
    private List<Delivery> deliveryList;
    private Context context;

    public DeliveryAdapter(Context context, List<Delivery> deliveryList) {
        this.context = context;
        this.deliveryList = deliveryList;
    }

    public void setData(List<Delivery> data) {
        deliveryList.clear();
        deliveryList.addAll(data);
        notifyDataSetChanged();
    }

//    public void filterList(String searchText) {
//        List<Delivery> filteredList = new ArrayList<>();
//        if (TextUtils.isEmpty(searchText)) {
//            filteredList.addAll(originalDeliveryList); // Show all items if search text is empty
//        } else {
//            for (Delivery delivery : originalDeliveryList) {
//                if (delivery.getTrackingId().contains(searchText)) {
//                    filteredList.add(delivery);
//                }
//            }
//        }
//        deliveryList.clear();
//        deliveryList.addAll(filteredList);
//        notifyDataSetChanged();
//    }




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
        holder.destination.setText(delivery.getDestination() + "," + delivery.getDetailedAddress());

        String status = delivery.getStatus();
        String statusText = "";
        int backgroundColor = 0;
        int textColor = 0;

        switch (status) {
            case "0":
                statusText = "Pending";
                backgroundColor = R.drawable.rounded_orange_background;
                textColor = Color.parseColor("#ffac46");
                holder.imageView.setImageResource(R.drawable.orange);
                break;
            case "1":
                statusText = "Accepted";
                backgroundColor = R.drawable.rounded_orange_background;
                textColor = Color.parseColor("#ffac46");
                holder.imageView.setImageResource(R.drawable.orange);
                break;
            case "2":
                statusText = "Rejected";
                backgroundColor = R.drawable.rounded_red_background;
                textColor = Color.parseColor("#f66b6b");
                holder.imageView.setImageResource(R.drawable.red);
                break;
            case "3":
                statusText = "Parcel Collected";
                backgroundColor = R.drawable.rounded_orange_background;
                textColor = Color.parseColor("#ffac46");
                holder.imageView.setImageResource(R.drawable.orange);
                break;
            case "4":
                statusText = "On the way";
                backgroundColor = R.drawable.rounded_orange_background;
                textColor = Color.parseColor("#ffac46");
                holder.imageView.setImageResource(R.drawable.orange);
                break;
            case "5":
                statusText = "Delivered";
                backgroundColor = R.drawable.rounded_green_background;
                textColor = Color.parseColor("#57cd43");
                holder.imageView.setImageResource(R.drawable.green);
                break;
            case "6":
                statusText = "Failed";
                backgroundColor = R.drawable.rounded_red_background;
                textColor = Color.parseColor("#f66b6b");
                holder.imageView.setImageResource(R.drawable.red);
                break;
            default:
                statusText = "Unknown";
                break;
        }

        holder.StatusValue.setText(statusText);
        holder.StatusValue.setBackgroundResource(backgroundColor);
        holder.StatusValue.setTextColor(textColor);
    }



    @Override
    public int getItemCount() {
        return deliveryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Trackingid;
        TextView selecteddate;
        TextView source,destination,StatusValue;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Trackingid = itemView.findViewById(R.id.Trackingid);
            selecteddate = itemView.findViewById(R.id.selecteddate);
            source = itemView.findViewById(R.id.source);
            destination = itemView.findViewById(R.id.destination);
            StatusValue = itemView.findViewById(R.id.StatusValue);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
