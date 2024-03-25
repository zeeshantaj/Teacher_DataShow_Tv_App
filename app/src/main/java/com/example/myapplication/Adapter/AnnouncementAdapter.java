package com.example.myapplication.Adapter;

import android.content.Context;
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

import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {
    private List<AnnouncementModel> announcementModelList;
    private Context context;
    public AnnouncementAdapter(List<AnnouncementModel> announcementModelList, Context context) {
        this.announcementModelList = announcementModelList;
        this.context = context;
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
            binding.itemDes.setText(model.getDescription());
            binding.itemDueDate.setText(model.getDue_date());
        } else {
            // Set data for image layout
            AnnounceImgLaoutBinding binding = AnnounceImgLaoutBinding.bind(holder.itemView);
            //binding.itemDate.setText(model.getCurrent_date());


            Glide.with(holder.itemView.getContext())
                    .load(model.getImageUrl())
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(15)))
                    .into(binding.itemImage);
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
