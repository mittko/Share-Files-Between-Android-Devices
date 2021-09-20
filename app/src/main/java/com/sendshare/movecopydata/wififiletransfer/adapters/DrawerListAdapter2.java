package com.sendshare.movecopydata.wififiletransfer.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.sendshare.movecopydata.wififiletransfer.activities.DataReceiverActivity;
import com.sendshare.movecopydata.wififiletransfer.activities.FileTransferActivity2;
import com.sendshare.movecopydata.wififiletransfer.activities.MainActivity;
import com.sendshare.movecopydata.wififiletransfer.utilities.FileManager;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyDialog;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyUtility;
import com.wifi.mitko.sharewifiles3.R;

import java.util.ArrayList;


public class DrawerListAdapter2 extends ArrayAdapter<String> {
    private Context context;
    private MainActivity mainActivity;
    private int resource;
    private ArrayList<String> titles;
    public DrawerListAdapter2( Context context, int resource, ArrayList<String> titles) {
        super(context, resource,titles);
        this.context = context;
        this.resource = resource;
        mainActivity = (MainActivity)context;
        this.titles = titles;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater =
                (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = layoutInflater.inflate(this.resource,parent,false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText(titles.get(position));
        viewHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textView = (TextView)view;
                String title = textView.getText().toString();
                switch (title) {
                    case "Receive" :
                        if(MyUtility.checkIsWifiOn(context)) {
                            startDataReceiverActivity();
                        } else {
                            startDataReceiverActivity();
                            MyDialog.showDialog("Wifi is off !!!",context);
                        }
                        break;
                    case "Share" :
                        if(MyUtility.checkIsWifiOn(context)) {
                            if (MyUtility.filesToSend.size() > 0) {
                                Intent intent = new Intent(context, FileTransferActivity2.class);
                                context.startActivity(intent);

                         //     MyDialog.showInputDialog(context,"Enter friend's network code in the field and press SEND");
                            } else {
                                MyDialog.showDialog("No files selected !!!", context);
                            }
                        } else {
                            MyDialog.showDialog("Wifi is off !!!",context);
                        }
                        break;
                    case "Received Files" :
                        openShareByteFolder();
                        break;
                    case "Select a directory to save files" :
                    /*  Version Code 6 ->  Intent settingsIntent =
                                new Intent(context, SettingsActivity.class);
                        context.startActivity(settingsIntent);*/
                        FileManager.grandPermission(context);
                        break;
                    case "Help":
                        MyDialog.showHelpText(context,"How it's work ?",
                                "This app is used to copy multimedia files" +
                                        " from one device to another via WIFI (No internet required).On both devices" +
                                        " this app must be installed !!!\n" +
                                        "1)Both devices must have WIFI Turned On and be on the same network.\n" +
                                        "2)Navigate through different media type, select files which you want to share" +
                                        " and from the menu in the upper left corner click \"Share Data\".\n" +
                                        "3)The user of the device receiving the data must click \"Receive Data\" " +
                                        "from the menu in the upper left corner to show his code.\n" +
                                        "4) Enter friend's network code,  press \"SEND\" and the transfer will begin.\n" +
                                        "Note: By default files will be saved in folder named \"Share Blue\"" +
                                        " in Main Storage. After opening app you can change where " +
                                        "to save files from \"Select a directory to save files\" " +
                                        "item from the menu in upper left corner.");
                        break;
                        case  "Privacy Policy" :
                            try {
                                Intent browserIntent =
                                        new Intent(Intent.ACTION_VIEW,
                                                Uri.parse("http://mittko.great-site.net/mysite/ShareBlue.html"));
                                context.startActivity(browserIntent);
                            } catch (ActivityNotFoundException e) {
                                MyDialog.showDialog(
                                        "Unable to open web page",context);
                            }
                            break;
                    case "Good Bye" :
                        mainActivity.finish();
                        break;
                    default:break;
                }
                closeDrawer();
            }
        });
        return convertView;
    }

    private void closeDrawer() {
        DrawerLayout drawerLayout = mainActivity.findViewById(R.id.drawer_layout);
        ListView drawerList = mainActivity.findViewById(R.id.drawer_list);
        drawerLayout.closeDrawer(drawerList);
    }


    private class ViewHolder {
        TextView textView;
        private ViewHolder(View view) {
            textView = view.findViewById(R.id.drawerlist_row);
            textView.setTextColor(Color.WHITE);
            view.setTag(this);
            textView.setHeight((int)(0.1 *
                    MyUtility.getHeightInPixels((AppCompatActivity)context)));
        }
    }


    private void startDataReceiverActivity() {
    /*    String myIp = MyUtility.getWifi_IPAddress(context);
        String[] spl = myIp.split("\\.");
        String mycode = "";
        mycode += Konstants.getStringCodeFromInt(Integer.parseInt(spl[0]));
        mycode += Konstants.getStringCodeFromInt(Integer.parseInt(spl[1]));
        mycode += Konstants.getStringCodeFromInt(Integer.parseInt(spl[2]));
        mycode += Konstants.getStringCodeFromInt(Integer.parseInt(spl[3]));*/
        Intent intent = new Intent(context, DataReceiverActivity.class);
        context.startActivity(intent);
    }

    private void openShareByteFolder() {
    }

}
