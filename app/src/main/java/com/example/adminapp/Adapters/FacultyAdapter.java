package com.example.adminapp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminapp.HandleClickInterface.OnItemClickListener;
import com.example.adminapp.Models.Faculty;
import com.example.adminapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.FacultyViewHolder> {

    Context context;
    List<Faculty> faculties;
    OnItemClickListener clickListener;

    public FacultyAdapter(Context context, List<Faculty> faculties, OnItemClickListener clickListener) {
        this.context = context;
        this.faculties = faculties;
        this.clickListener = clickListener;
    }

    public FacultyAdapter() {
    }

    public FacultyAdapter(Context context, List<Faculty> faculties) {
        this.context = context;
        this.faculties = faculties;
    }

    @NonNull
    @NotNull
    @Override
    public FacultyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.faculty_item,parent,false);
        return new FacultyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FacultyViewHolder holder, int position) {
            Faculty faculty= faculties.get(position);
            holder.facultyName.setText(faculty.getFacultyName());
            holder.facultyEmail.setText(faculty.getFacultyEmail());
            holder.facultyPost.setText(faculty.getFacultyPost());
            String url=faculty.getImageUrl();
            if(url!=null || url.length()!=0)
            Glide.with(context).load(faculty.getImageUrl()).into(holder.facultyImage);
            else
                Glide.with(context).load(R.drawable.profile).into(holder.facultyImage);
            holder.updateFacultyItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(position);
                }
            });
//            notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return faculties.size();
    }

    public class FacultyViewHolder extends RecyclerView.ViewHolder{

        TextView facultyName,facultyEmail,facultyPost,updateFacultyItem;
        ImageView facultyImage;
        public FacultyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            facultyName=itemView.findViewById(R.id.facultyItemName);
            facultyEmail=itemView.findViewById(R.id.facultyItemEmail);
            facultyPost=itemView.findViewById(R.id.facultyItemPost);
            facultyImage=itemView.findViewById(R.id.facultyItemImage);
            updateFacultyItem=itemView.findViewById(R.id.updateFacultyItem);
        }
    }
}
