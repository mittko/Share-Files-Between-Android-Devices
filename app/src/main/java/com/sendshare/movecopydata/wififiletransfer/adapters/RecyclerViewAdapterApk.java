package com.sendshare.movecopydata.wififiletransfer.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyDialog;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyUtility;
import com.wifi.mitko.sharewifiles3.R;

import java.util.ArrayList;
import java.util.HashSet;

public class RecyclerViewAdapterApk extends RecyclerView.Adapter<RecyclerViewAdapterApk.MyViewHolder4> {

    private ArrayList<ApkInfo> apkInfos = new ArrayList<>();
    private Context context;
    private HashSet<TextView> selectedViews = new HashSet<>();

    public RecyclerViewAdapterApk(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_row_apk,parent,false);
        return new MyViewHolder4(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder4 holder, int position) {
        TextView textView = holder.textView;
        textView.setText(apkInfos.get(position).getName());
        if( MyUtility.filesToSend.containsKey(apkInfos.get(position).getApkFilepath())) {// apkInfos.get(getAdapterPosition()).isChecked();//linearLayout.isSelected();) {
            textView.setTextColor(context.getResources().getColor(R.color.start_color));
        } else {
            textView.setTextColor(Color.BLACK);
        }

        ImageView imageView = holder.imageView;
        Glide.with(context).load(apkInfos.get(position).getDrawableIcon()).placeholder(R.mipmap.share_blue_launcher).into(imageView);
    }

    @Override
    public int getItemCount() {
        return apkInfos.size();
    }

    class MyViewHolder4 extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imageView;
        public MyViewHolder4(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.file_textview4);
            this.imageView = itemView.findViewById(R.id.thumbnail_imageview4);
            final LinearLayout linearLayout = itemView.findViewById(R.id.linearlayout_recyvlerview_row4);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(! MyUtility.filesToSend.containsKey(apkInfos.get(getAdapterPosition()).getApkFilepath())) {
                        textView.setTextColor(context.getResources().getColor(R.color.start_color));
                        MyUtility.filesToSend.put(apkInfos.get(getAdapterPosition()).getApkFilepath()
                        ,apkInfos.get(getAdapterPosition()).getDrawableIcon());
                        selectedViews.add(textView);
                    } else  {
                        textView.setTextColor(Color.BLACK);
                        MyUtility.filesToSend.remove(apkInfos.get(getAdapterPosition()).getApkFilepath());
                        selectedViews.remove(textView);
                    }
                    MyDialog.showDialogShort(MyUtility.filesToSend.size()+" files selected", context);
                }
            });
        }
    }

    public void updateList(ArrayList<ApkInfo> infos) {
        apkInfos.addAll(infos);
        notifyDataSetChanged();
    }
    public void clearSelectedItems() {
        for(TextView textView : selectedViews) {
            textView.setTextColor(Color.BLACK);
        }
        selectedViews.clear();
        notifyDataSetChanged();
    }
}
