package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.AnnouncementModel;
import com.example.myapplication.Model.DataModel;
import com.example.myapplication.R;

import java.util.List;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {
    private List<AnnouncementModel> modelList;

    public AnnouncementAdapter(List<AnnouncementModel> modelList) {
        this.modelList = modelList;
    }
    public void setData(List<AnnouncementModel> annModel) {
        modelList.clear();
        modelList.addAll(annModel);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnnouncementAdapter.AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.,parent,false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.AnnouncementViewHolder holder, int position) {
        AnnouncementModel annMode = modelList.get(position);
        holder.bind(annMode);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
