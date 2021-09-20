package com.sendshare.movecopydata.wififiletransfer.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.wifi.mitko.sharewifiles3.R;

public class MyDialog {
    public static void showDialog(String msg, Context context) {
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
    public static void showDialogShort(String msg, Context context) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    public static void showHelpText(Context context,String title, String text) {
        final AlertDialog alertDialog =
                new AlertDialog.Builder(context, R.style.InputDialogStyle).create();
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK",(DialogInterface.OnClickListener)null);
      alertDialog.show();
          alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

  /*  public static void showInputDialog(final Context context, String title) {
        final AlertDialog alertDialog =
                new AlertDialog.Builder(context,R.style.InputDialogStyle).create();
        alertDialog.setTitle(title);
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        final View customView = getView(context);
        alertDialog.setView(customView);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,
                "Send",
                (DialogInterface.OnClickListener)null);
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Cancel",
                (DialogInterface.OnClickListener)null);
        alertDialog.show();

        final EditText editText = customView.findViewById(R.id.netcode_input);
        openKeyboard(editText,context);

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(editText.getText().length() != 8) {
                            MyDialog.showDialog("code length can not be different than 8",context);
                            return;
                        }
                           String targetIp = editText.getText().toString();
                            for(int i = 0;i < targetIp.length();i++)  {
                                if (!isValidChar(targetIp.charAt(i))) {
                                    MyDialog.showDialog("wrong character in the code!", context);
                                    return;
                                }
                            }
                           int a= Konstants.getIntCodeFromString(targetIp.substring(0,2));
                           int b = Konstants.getIntCodeFromString(targetIp.substring(2,4));
                           int c = Konstants.getIntCodeFromString(targetIp.substring(4,6));
                           int d = Konstants.getIntCodeFromString(targetIp.substring(6,8));
                           if(a == -1 || b == -1 || c == -1 || d == -1) {
                               MyDialog.showDialog("wrong character in the code!", context);
                               return;
                           }
                  //         MyConsole.println(String.format("%s.%s.%s.%s",a,b,c,d));
                        Intent intent = new Intent(context, FileTransferActivity2.class);// before -> FileTransferActivity.class);
                        intent.putExtra(Konstants.DEVICE_IP,String.format("%s.%s.%s.%s",a,b,c,d));
                        intent.putExtra(Konstants.DEVICE_RECEIVER_PORT,Konstants.SERVER_PORT);
                        context.startActivity(intent);
                        alertDialog.dismiss(); //

                        hideKeyboard(context);
                    }
                });
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        hideKeyboard(context);
                    }
                });
    }
   */
     private static View getView(Context context) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(context);
        return layoutInflater.inflate(R.layout.netcode_dialog,null);
    }
    private static boolean isValidChar(char ch) {
        return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
    }

    private static void openKeyboard(EditText editText, Context context) {
        if(editText.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    context.getSystemService(Context.INPUT_METHOD_SERVICE);
       //     imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT); this not work
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);//  this work
        }
    }

    private  static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);//  this work
    }
}
