package com.sendshare.movecopydata.wififiletransfer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.sendshare.movecopydata.wififiletransfer.modules.GlideApp;
import com.wifi.mitko.sharewifiles3.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecyclerViewAdapterAsPackages extends RecyclerView.Adapter<RecyclerViewAdapterAsPackages.MyViewHolder2>
     {

    private ArrayList<String> packageList = new ArrayList<>();
    private Context context;
    private HashMap<String, ArrayList<String>> map = new HashMap<>();
    private ImageView selectedImage = null;
    private JustFilesRecyclerViewAdapter justFilesRecyclerViewAdapter;
    public RecyclerViewAdapterAsPackages(Context context, JustFilesRecyclerViewAdapter justFilesRecyclerViewAdapter) {
        this.context = context;
        this.justFilesRecyclerViewAdapter = justFilesRecyclerViewAdapter;
     }

 /*    called in JustFragmentFiles.updateList*/
    public void clearPackageList() {
        map.clear();
        packageList.clear();
        notifyDataSetChanged();
    }

    public void updateList(HashMap<String , ArrayList<String>> map) {
        this.map.clear();
        this.map.putAll(map);
        int position = this.map.size();// ????????
        for(Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            packageList.add(entry.getKey());
        }
        notifyItemRangeInserted(position, map.size());
    }

    public void clearMemoryFromImages() {
        GlideApp.get(context).clearMemory();
    }
  /*  @Override
    public void onViewRecycled(@NonNull MyViewHolder2 holder) {
        super.onViewRecycled(holder);
        ImageView imageView = (holder.view).findViewById(R.id.thumbnail_imageview4);
        GlideApp.with(context).clear(imageView);
    }*/

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_row_package,viewGroup,false);
       return new MyViewHolder2(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 myViewHolder,final int i) {

        final TextView textView = myViewHolder.textView;
        String file = packageList.get(i);
        textView.setText(file);
        ImageView imageView = myViewHolder.imageView;

        imageView.setImageResource(R.drawable.folder);
    }

    @Override
    public int getItemCount() {
        return packageList.size();
    }


    private String chooseFolder = "";

    class MyViewHolder2 extends RecyclerView.ViewHolder {

        private View view;
        private TextView textView;
        private ImageView imageView;
        private ConstraintLayout constraintLayout;
        MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            textView = view.findViewById(R.id.file_textview_package);
            imageView = view.findViewById(R.id.thumbnail_imageview_package);
            constraintLayout = view.findViewById(R.id.constraint_layout_package);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(selectedImage != null) {
                        selectedImage.setImageResource(R.drawable.folder);
                    }
                    imageView.setImageResource(R.drawable.folder_opened);
                    selectedImage = imageView;

                     chooseFolder = textView.getText().toString();

                     requestFiles();
                }
            });
        }

    }


    public void requestFiles() {
        justFilesRecyclerViewAdapter.clearFileList();
        justFilesRecyclerViewAdapter.requestVideos(map.get(chooseFolder), chooseFolder);
    }
   public void loadMore() {
       justFilesRecyclerViewAdapter.requestVideos(map.get(chooseFolder), chooseFolder);
   }
}

