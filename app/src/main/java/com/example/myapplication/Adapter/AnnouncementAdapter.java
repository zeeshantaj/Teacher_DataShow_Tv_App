package com.example.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.Model.AnnouncementModel;
import com.example.myapplication.Model.DataModel;
import com.example.myapplication.R;
import com.example.myapplication.databinding.AnnounceDataLayoutBinding;
import com.example.myapplication.databinding.AnnounceImgLaoutBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {
    private List<AnnouncementModel> announcementModelList;
    private Context context;
    public AnnouncementAdapter(List<AnnouncementModel> announcementModelList, Context context) {
        this.announcementModelList = announcementModelList;
        this.context = context;
    }
    public void setData(List<AnnouncementModel> newDataList) {
        announcementModelList.clear();
        announcementModelList.addAll(newDataList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;
        if (viewType == 0) {
            // Inflate layout for title/description
            view = inflater.inflate(R.layout.announce_data_layout, parent, false);
        } else {
            // Inflate layout for image
            view = inflater.inflate(R.layout.announce_img_laout, parent, false);
        }
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        AnnouncementModel model = announcementModelList.get(position);

        if (getItemViewType(position) == 0) {
            // Set data for title/description layout
            AnnounceDataLayoutBinding binding = AnnounceDataLayoutBinding.bind(holder.itemView);
            binding.itemDate.setText(model.getCurrent_date());
            binding.itemTitle.setText(model.getTitle());
            binding.itemTitle.setSelected(true);
            binding.itemDes.setText(model.getDescription());
            binding.itemDes.setSelected(true);
            binding.itemDueDate.setText(model.getDue_date());

            compareDate(model.getDue_date(),binding.dayCountTxt);

        } else {
            // Set data for image layout
            AnnounceImgLaoutBinding binding = AnnounceImgLaoutBinding.bind(holder.itemView);
            //binding.itemDate.setText(model.getCurrent_date());


            Glide.with(holder.itemView.getContext())
                    .load(model.getImageUrl())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(35)))
                    .into(binding.itemImage);
        }


    }
    private void compareDate(String due_date,TextView textView){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:M:d", Locale.US);
        try {
            Date givenDate = simpleDateFormat.parse(due_date);
            Date currentDate = new Date();
            long diffInMillls = givenDate.getTime() - currentDate.getTime();
            long diffInDays = diffInMillls / (1000 * 60 * 60 * 24);
            String dayStr = String.format("Days Left: %s",diffInDays);
            textView.setText(dayStr);
            if (diffInMillls < 0){

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Override
    public int getItemCount() {
        return announcementModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        AnnouncementModel model = announcementModelList.get(position);
        return model.getImageUrl() != null ? 1 : 0; // Return 1 for image, 0 for title/description
    }

    public class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
