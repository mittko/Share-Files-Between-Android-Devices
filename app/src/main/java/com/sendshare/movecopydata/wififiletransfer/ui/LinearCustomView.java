package com.sendshare.movecopydata.wififiletransfer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.sendshare.movecopydata.wififiletransfer.interfaces.VisualizeDataTransfer;
import com.sendshare.movecopydata.wififiletransfer.utilities.MyUtility;
import com.wifi.mitko.sharewifiles3.R;

public class LinearCustomView extends View implements VisualizeDataTransfer {
    private Paint paint = new Paint();
    private Paint textPaint = new Paint();
    private Paint  percentPaint = new Paint();
    private Rect rect;
    private int WIDTH;
    private int HEIGHT;
    private String text = " 0 b";
    private String filepath;
    private String fileName = "";
   /* public void setFilepath(String filepath) {
        int index = filepath.lastIndexOf("/");
        if(index != -1) {
            this.filepath = filepath.substring(index+1);
        } else {
            this.filepath = filepath;
        }
    }*/

    private void setColor(int color) {
        paint.setColor(color);
    }


    public LinearCustomView(Context context, int width, int height) {
        super(context);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(getResources().getColor(R.color.tab_pressed));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(Color.BLACK);
        percentPaint.setStyle(Paint.Style.FILL);
     //   percentPaint.setColor(Color.parseColor("#FFA60C"));
        rect = new Rect();

        this.WIDTH = width;
        this.HEIGHT = height;
        int spSize = HEIGHT/7;
        float scaledSizeInPixels = spSize * getResources().getDisplayMetrics().scaledDensity;
        textPaint.setTextSize(scaledSizeInPixels);
        percentPaint.setTextSize(scaledSizeInPixels);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rect,paint);
        canvas.drawText(fileName ,0,this.HEIGHT / 2,textPaint);
        canvas.drawText(text,(int)(WIDTH) - (textPaint.measureText(text)),(int)(HEIGHT/2),percentPaint);
    }


    @Override
    public void visualize(double partByte, double allByte) {
        double percentage = (partByte / allByte) * (double) WIDTH;
        rect.set(0,(int)(HEIGHT/7),(int)percentage,(int)(HEIGHT/1.3));
        this.text = MyUtility.getHumanReadableBytes(partByte) + " / " + MyUtility.getHumanReadableBytes(allByte);// " " + Math.round(value * 100) + " %";
        invalidate();
    }

    @Override
    public void showFileName(String fileName) {
        this.fileName = fileName;
        invalidate();
    }

    @Override
    public void handleConnectionError(String text) {

    }
   /* @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }*/

}
