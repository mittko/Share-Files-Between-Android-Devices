package com.sendshare.movecopydata.wififiletransfer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.sendshare.movecopydata.wififiletransfer.interfaces.VisualizeDataTransfer;
import com.wifi.mitko.sharewifiles3.R;

import static com.sendshare.movecopydata.wififiletransfer.utilities.MyUtility.getHumanReadableBytes;

public class SharedCustomView extends View
        implements VisualizeDataTransfer {

    private Paint paint = new Paint();
    private Paint textPaint = new Paint();
    private String text = "0";
    private String title;
    private volatile double SENDED_BYTES = 0;
    private String msg;
    private String networkCode;
    public SharedCustomView(Context context,  String networkCode, String msg ) {
        super(context);
        this.msg = msg;
        this.networkCode = networkCode;

        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
      //  paint.setStrokeWidth(12f);
        paint.setTextSize(65f);
        paint.setAntiAlias(true);

        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(75f);

        setBackgroundResource(R.color.tab_pressed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        synchronized (this) {
            // private String title;
            int w = getWidth() >> 1;
            int h = getHeight() >> 1;
            int minDist = Math.min(w,h);
            canvas.drawText(text, w - (paint.measureText(text) / 2), h, textPaint);
            canvas.drawText(msg,w - (paint.measureText(msg) / 2),h + textPaint.getTextSize(),textPaint);
            int titlePos = h / 5;
            canvas.drawText("network code ", 0,0 + paint.getTextSize(),paint);
            canvas.drawText("/ " + networkCode + " /", 0, paint.getTextSize() * 2,paint);

        }

    }


    @Override
    public void visualize(double partByte,double allByte) {
        //  MyConsole.println("value = " + value);
        synchronized (this) {
            SENDED_BYTES += partByte;
            this.text =  getHumanReadableBytes(SENDED_BYTES) ;
            invalidate();
            //  if(VOLATILE_SWAP_ANGLE >= 359.5) {
            //       Interestial.getInstance().showAd();
            //    }
        }

    }

    @Override
    public void showFileName(String fileName) {

    }

    @Override
    public void handleConnectionError(String text) {
        title = text;
    }


}

