package com.airsprint.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airsprint.Models.NotificationModel;
import com.airsprint.R;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationModel> notificationList;

    public NotificationAdapter(List<NotificationModel> notificationList) {
        this.notificationList = notificationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NotificationModel notification = notificationList.get(position);
        holder.notificationMessageTextView.setText(notification.getNotificationMessage());
        holder.timetextview.setText(notification.getTime());
        holder.datetextview.setText(notification.getDate());

        int status = notification.getStatus();
        if (status != 0) {
            switch (status) {
                case 0:
                    holder.imageView.setImageResource(R.drawable.orange);
                    break;
                case 1:
                    holder.imageView.setImageResource(R.drawable.orange);
                    break;
                case 2:
                    holder.imageView.setImageResource(R.drawable.red);
                    break;
                case 3:
                    holder.imageView.setImageResource(R.drawable.orange);
                    break;
                case 4:
                    holder.imageView.setImageResource(R.drawable.orange);
                    break;
                case 5:
                    holder.imageView.setImageResource(R.drawable.green);
                    break;
                case 6:
                    holder.imageView.setImageResource(R.drawable.red);
                    break;
                default:
                    break;
            }

        }


    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView notificationMessageTextView,timetextview,datetextview;

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationMessageTextView = itemView.findViewById(R.id.NameTextview);
            timetextview = itemView.findViewById(R.id.timetextview);
            datetextview = itemView.findViewById(R.id.datetextview);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}




