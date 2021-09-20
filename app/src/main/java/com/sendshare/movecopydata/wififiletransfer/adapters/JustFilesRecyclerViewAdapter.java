package com.sendshare.movecopydata.wififiletransfer.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.sendshare.movecopydata.wififiletransfer.interfaces.ListRefresher;
import com.sendshare.movecopydata.wififiletransfer.media.LazyLoadingPaths;
import com.sendshare.movecopydata.wififiletransfer.modules.GlideApp;
import com.sendshare.movecopydata.wififiletransfer.utilities.Konstants;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyConsole;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyDialog;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyUtility;
import com.wifi.mitko.sharewifiles3.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class JustFilesRecyclerViewAdapter extends RecyclerView.Adapter<JustFilesRecyclerViewAdapter.MyViewHolder2>
  implements ListRefresher {

    private ArrayList<ListRow> rowArrayList = new ArrayList<>();
    private Context context;
    int pageNumber;
    private HashSet<TextView> selectedViews = new HashSet<>();

    public JustFilesRecyclerViewAdapter(Context context, int pageNumber) {
        this.context = context;
        this.pageNumber = pageNumber;
    }
    public void clearFileList() {
        scrollFrom = 0;
        rowArrayList.clear();
        selectedViews.clear();
        notifyDataSetChanged();
    }

    public void clearMemoryFromImages() {
        GlideApp.get(context).clearMemory();
    }
    /*@Override
    public void onViewRecycled(@NonNull MyViewHolder2 holder) {
        super.onViewRecycled(holder);
        ImageView imageView = (holder.view).findViewById(R.id.thumbnail_imageview);
        GlideApp.with(context).clear(imageView);
    }*/

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recylcerview_row_justfiles,viewGroup,false);
        return new MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 myViewHolder,final int i) {
        String file = rowArrayList.get(i).getPath();

        final TextView textView = myViewHolder.textView;
        if (MyUtility.filesToSend.containsKey(file)) {
            textView.setTextColor(context.getResources().getColor(R.color.start_color));
        } else {
            textView.setTextColor(Color.BLACK);
        }

        int index =  file.lastIndexOf("/");
        String fileName = file;
        if(index != -1) {
            fileName = file.substring(index + 1);
        }
        textView.setText(fileName);

        ImageView imageView = myViewHolder.thumbnailView;

        if (pageNumber == 3) {
            try {
                coverAudioThumb(file, imageView);
            } catch (IOException e) {
                MyConsole.println("from JustFilesRecyclerViewAdapter " + e.getMessage());
            }
        } else {
            GlideApp.with(context).load(file).placeholder(R.mipmap.share_blue_launcher).into(imageView);
        }
    }
    @Override
    public int getItemCount() {
        return rowArrayList.size();
    }
    @Override
    public void updateList(ArrayList<String> arrayList) {
        int position = rowArrayList.size();
        for(int i = 0;i < arrayList.size();i++) {
            rowArrayList.add(new ListRow(arrayList.get(i), false, false));
        }
        notifyItemRangeInserted(position, arrayList.size());
    }

    public void clearSelectedItems() {
        for(TextView textView : selectedViews) {
            textView.setTextColor(Color.BLACK);
        }
        selectedViews.clear();
        notifyDataSetChanged();
    }
    int scrollFrom = 0;
    public void requestVideos(ArrayList<String> paths, String folder) {
                LazyLoadingPaths lazyLoadingPaths =
                        new LazyLoadingPaths(paths, this, folder, scrollFrom);
                lazyLoadingPaths.execute();
        scrollFrom += Konstants.ITEM_TO_SCROLL;
    }

    class MyViewHolder2  extends RecyclerView.ViewHolder {

        private View view;
        private ImageView thumbnailView;
        private TextView textView;
        MyViewHolder2(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            thumbnailView = view.findViewById(R.id.thumbnail_image_view);
            textView = view.findViewById(R.id.file_text_view);
            final ConstraintLayout constraintLayout = view.findViewById(R.id.constraint_layout_recyclerview_row);
            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyConsole.println(rowArrayList.get(getAdapterPosition()).getPath());
                    if(!  MyUtility.filesToSend.containsKey(rowArrayList.get(getAdapterPosition()).getPath())) {
                        textView.setTextColor(context.getResources().getColor(R.color.start_color));
                        Drawable drawable;
                       if(pageNumber == 3 || pageNumber == 4) {
                            drawable = ResourcesCompat.getDrawable(context.getResources(),R.mipmap.share_blue_launcher,null);
                        } else {
                            drawable = Drawable.createFromPath(rowArrayList.get(getAdapterPosition()).getPath());
                        }
                        MyUtility.filesToSend.put(rowArrayList.get(getAdapterPosition()).getPath(),drawable);
                        selectedViews.add(textView);
                    } else {
                        textView.setTextColor(Color.BLACK);
                        MyUtility.filesToSend.remove(rowArrayList.get(getAdapterPosition()).getPath());
                        selectedViews.remove(textView);
                    }
                    MyDialog.showDialogShort(MyUtility.filesToSend.size()+" files selected", context);
                }
            });

        }

    }

    private void coverAudioThumb(String path, ImageView imageView) throws IOException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            GlideApp.with(context).load(R.mipmap.share_blue_launcher).into(imageView);
        } else {
            Bitmap bitmap = null;
            MediaMetadataRetriever mr = new MediaMetadataRetriever();
            try {
                mr.setDataSource(path);
                byte[] byte1 = mr.getEmbeddedPicture();
                mr.release();
                if (byte1 != null) {
                    bitmap = BitmapFactory.decodeByteArray(byte1, 0, byte1.length);
                    GlideApp.with(context).load(bitmap).placeholder(R.mipmap.share_blue_launcher).into(imageView);
                } else {
                    GlideApp.with(context).load(R.mipmap.share_blue_launcher).into(imageView);
                }
            }catch (RuntimeException re) {
                GlideApp.with(context).load(R.mipmap.share_blue_launcher).into(imageView);
            }
        }
    }

}

