package com.example.adminapp.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.adminapp.HandleClickInterface.OnItemClickListener;
import com.example.adminapp.Models.Notice;
import com.example.adminapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {

    Context context;
    List<Notice> notices;
    OnItemClickListener clickListener;

    public NoticeAdapter(Context context, List<Notice> notices, OnItemClickListener clickListener) {
        this.context = context;
        this.notices = notices;
        this.clickListener = clickListener;
    }

    @NonNull
    @NotNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.notice_item,parent,false );
       return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NoticeViewHolder holder, int position) {
        Notice notice=notices.get(position);
        holder.noticeTitle.setText(notice.getTitle());
        String dateTime=notice.getDate()+" "+notice.getTime();
        holder.noticeDateTime.setText(dateTime);
        Glide.with(context).load(notice.getImage()).into(holder.noticeImage);

        holder.deleteNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder{

        TextView noticeTitle,noticeDateTime;
        Button deleteNotice;
        ImageView noticeImage;

        public NoticeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            noticeTitle=itemView.findViewById(R.id.noticeItemTitle);
            noticeDateTime=itemView.findViewById(R.id.noticeItemDateTime);
            noticeImage=itemView.findViewById(R.id.noticeItemImageView);
            deleteNotice=itemView.findViewById(R.id.noticeItemDeleteButton);
        }
    }
}
