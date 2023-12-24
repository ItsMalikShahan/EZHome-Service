package com.example.ezhomeservice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ezhomeservice.model.ServiceItemModel;

import java.util.List;

public class AcTechnicianAdapter extends RecyclerView.Adapter<AcTechnicianAdapter.ViewHolder> {

    Context context;
    List<ServiceItemModel> acTechnicianList;

    public AcTechnicianAdapter(Context context, List<ServiceItemModel> acTechnicianList) {
        this.context = context;
        this.acTechnicianList = acTechnicianList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_service_provider, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceItemModel model = acTechnicianList.get(position);
        Glide.with(holder.itemView.getContext())
                .load(model.getProfileImgUrl()).into(holder.profileImg);
        holder.name.setText(model.getName());
        holder.experience.setText(model.getExperience());
        holder.workingDays.setText(model.getWorkingDays());
        holder.workingHours.setText(model.getWorkingHours());
        holder.description.setText(model.getDescription());
        holder.areaCovered.setText(model.getAreaCover());
    }

    @Override
    public int getItemCount() {
        return acTechnicianList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileImg;
        TextView experience, workingHours, workingDays, description,
                areaCovered, name;
        Button msg, call;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImg = itemView.findViewById(R.id.profile_image);
            experience = itemView.findViewById(R.id.tv_exp);
            workingHours = itemView.findViewById(R.id.tv_workingHour);
            workingDays = itemView.findViewById(R.id.tv_workingDay);
            description = itemView.findViewById(R.id.tv_description);
            areaCovered = itemView.findViewById(R.id.tv_areaCoverning);
            msg = itemView.findViewById(R.id.btn_msg);
            call = itemView.findViewById(R.id.btn_call);
            name = itemView.findViewById(R.id.tv_name);
        }
    }
}
