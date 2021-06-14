package com.example.adminapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminapp.HandleClickInterface.OnItemClickListener;
import com.example.adminapp.Models.Notice;
import com.example.adminapp.R;
import com.example.adminapp.UploadClasses.DeleteNotice;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    Context context;
    List<Notice> notices;

    public NoticeAdapter(Context context, List<Notice> notices) {
        this.context = context;
        this.notices = notices;

    }

    @NonNull
    @NotNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_item, parent, false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoticeViewHolder holder, int position) {
        Notice notice = notices.get(position);
        holder.noticeTitle.setText(notice.getTitle());
        String dateTime = notice.getDate() + " " + notice.getTime();
        holder.noticeDateTime.setText(dateTime);
        Log.i("TAG", notice.getTitle() + "--->" + (notice.getImage()).equalsIgnoreCase("") + "");
        if (!(notice.getImage()).equalsIgnoreCase(""))
            Glide.with(context).load(notice.getImage()).into(holder.noticeImage);
        else
            holder.noticeImage.setVisibility(View.GONE);

        holder.deleteNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref = ref.child("Notices");
                ref = ref.child(notice.getKey());
                ref.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Successfully deleted!", Toast.LENGTH_LONG).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(context, "Something went wrong, please try again!", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {

        TextView noticeTitle, noticeDateTime;
        Button deleteNotice;
        ImageView noticeImage;

        public NoticeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            noticeTitle = itemView.findViewById(R.id.noticeItemTitle);
            noticeDateTime = itemView.findViewById(R.id.noticeItemDateTime);
            noticeImage = itemView.findViewById(R.id.noticeItemImageView);
            deleteNotice = itemView.findViewById(R.id.noticeItemDeleteButton);
        }
    }
}
