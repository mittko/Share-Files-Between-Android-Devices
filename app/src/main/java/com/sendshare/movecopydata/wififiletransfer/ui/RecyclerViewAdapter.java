/*
package com.wifi.movecopydata.sharewifiles3.ui;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import MainActivity;
import com.wifi.movecopydata.sharewifiles3.R;
import GetAudioFiles;
import GetDocumentFiles;
import GetPicturesFiles;
import GetVideoFiles;
import com.wifi.movecopydata.sharewifiles3.modules.GlideApp;
import MyConsole;
import MyDialog;
import MyUtility;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static Konstants.FILE_NAME;
import static Konstants.FILE_PATH;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>
 {

    private ArrayList<ListRow> rowArrayList = new ArrayList<>();
    private Context context;

    public RecyclerViewAdapter(Context context) {
        this.context = context;
    }
    public void clearList() {
        rowArrayList.clear();
        notifyDataSetChanged();
    }

    public void updateList(ArrayList<ListRow> newList) {
        int position = rowArrayList.size();
        rowArrayList.addAll(newList);
     //   MyConsole.println("recyclerview size = "+rowArrayList.size());
        notifyItemRangeInserted(position, newList.size());
    }

    public void clearMemoryFromImages() {
        GlideApp.get(context).clearMemory();
    }
     @Override
     public void onViewRecycled(@NonNull MyViewHolder holder) {
         super.onViewRecycled(holder);
         ImageView imageView = (holder.view).findViewById(R.id.thumbnail_imageview);
         GlideApp.with(context).clear(imageView);
     }

     @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      //  context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_row2,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder,final int i) {

         final TextView textView = (myViewHolder.view).findViewById(R.id.file_textview);
        String file = rowArrayList.get(i).getPath();

        int index =  file.lastIndexOf("/");
    //    MyConsole.println("INDEX = "+index);
        String fileName = file;
        if(index != -1) {
            fileName = file.substring(index + 1);
        }
        textView.setText(fileName);
        ImageView imageView = (myViewHolder.view).findViewById(R.id.thumbnail_imageview);
        TextView parentDirectory = ((MainActivity) context).findViewById(R.id.go_back);
        String parentFolder = parentDirectory.getText().toString();
        switch (parentFolder) {
            case "": //(isDirectory(file)) {
                GlideApp.with(context).load(R.drawable.icon_folder).into(imageView);
                break;
            case "Videos":
              */
/*  long videoId = Long.parseLong(file);
                Picasso.get().load(ContentUris.withAppendedId(
                        MediaStore.Video.Media.EXTERNAL_CONTENT_URI, videoId)).resize(FileManager.reqWidth,
                        FileManager.reqHeight)
                      .placeholder(
                        R.drawable.video).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        MyConsole.println("ON SUCCESS");
                    }

                    @Override
                    public void onError(Exception e) {
                        MyConsole.println("Picasso error "+e.getMessage());
                    }
                });*//*

                GlideApp.with(context).load(file).skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .centerInside().placeholder(R.drawable.video).into(imageView);
                break;
            case "Pictures":
                GlideApp.with(context).load(file).skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .centerInside().placeholder(R.drawable.image).into(imageView);
                break;
            case "Audios":
                Bitmap bitmap1 = coverpicture(file);
                GlideApp.with(context).load(bitmap1).skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .centerInside().placeholder(R.drawable.audio_).into(imageView);
                break;
            case "Documents":
                GlideApp.with(context).load(file).skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .centerInside().placeholder(R.drawable.document_).into(imageView);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return rowArrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private Bitmap bitmap;
        public void storeBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
        public void clearBitmap() {
            if(bitmap != null) {
                bitmap.recycle();
                MyConsole.println("CLEARED");
            }
        }
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            LinearLayout linearLayout = view.findViewById(R.id.linearlayout_recyvlerview_row);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView textView = view.findViewById(R.id.file_textview);
                    final String text = textView.getText().toString().trim();
                    switch (text) {
                        case "Pictures":
                            clearList();
                            GetPicturesFiles getPicturesFiles =
                                    new GetPicturesFiles(0, new WeakReference<>(context));
                            getPicturesFiles.execute();
                            break;
                        case "Videos":
                            clearList();
                            GetVideoFiles getVideoFiles =
                                    new GetVideoFiles(0, new WeakReference<>(context));
                            getVideoFiles.execute();
                            break;
                        case "Audios":
                            clearList();
                            GetAudioFiles getAudioFiles =
                                    new GetAudioFiles(0, new WeakReference<>(context));
                            getAudioFiles.execute();
                            break;
                        case "Documents":
                            clearList();
                            GetDocumentFiles getDocumentFiles =
                                    new GetDocumentFiles(0, new WeakReference<>(context));
                            getDocumentFiles.execute();
                            break;
                        default:
                            startP2PActivity(text,getAdapterPosition());
                          */
/*  PopupMenu popupMenu =
                                    new PopupMenu(context, view);//, Gravity.NO_GRAVITY,R.attr.actionOverflowMenuStyle,0);
                            popupMenu.getMenuInflater()
                                    .inflate(R.menu.popup_menu, popupMenu.getMenu());
                            Menu menu = popupMenu.getMenu();
                            MenuCompat.setGroupDividerEnabled(menu, true);// to make line appear in menu

                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem menuItem) {
                                    switch (menuItem.getItemId()) {
                                        case R.id.send_file:
                                            startP2PActivity(getAdapterPosition());
                                            return true;
                                       *//*
*/
/* case R.id.open_file:
                                            FileManager.openFile(
                                                    rowArrayList.get(getAdapterPosition()).getPath()
                                                    , context);
                                            return true;*//*
*/
/*
                                      *//*
*/
/*  case R.id.share_file:
                                            FileManager.shareFile(
                                                    rowArrayList.get(getAdapterPosition()).getPath()
                                                    , context
                                            );
                                            return true;*//*
*/
/*
                                        default:
                                            return false;
                                    }
                                }
                            });
                            popupMenu.show();*//*

                            break;
                    }

                }
            });
        }

    }
        private void startP2PActivity(String fileName, int position) {
            if (MyUtility.checkIsWifiOn(context)) {
                Intent intent = new Intent(context, P2PActivity.class);
                intent.putExtra(FILE_NAME,fileName);
                intent.putExtra(FILE_PATH, rowArrayList.get(position).getPath());
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    MyDialog.showDialog(e.getMessage() + "", context);
                }
            } else {
                MyDialog.showDialog("Wifi is off !", context);
            }

        }

        private Bitmap coverpicture(String path) {
            MediaMetadataRetriever mr = new MediaMetadataRetriever();
            mr.setDataSource(path);
            byte[] byte1 = mr.getEmbeddedPicture();
            mr.release();
            if (byte1 != null)
                return BitmapFactory.decodeByteArray(byte1, 0, byte1.length);
            else
                return null;
        }



  */
/*  private boolean isDirectory(String path) {
        File file = new File(path);
        if(!file.exists()) {
            return true;
        }
        return  file.isDirectory();
    }*//*


}
*/
