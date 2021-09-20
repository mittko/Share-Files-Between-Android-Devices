package com.sendshare.movecopydata.wififiletransfer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.sendshare.movecopydata.wififiletransfer.interfaces.VisualizeDataTransfer;
import com.wifi.mitko.sharewifiles3.R;

public class SharedCircleCustomView extends View
        implements VisualizeDataTransfer {

    private RectF rect;
    private Paint paint = new Paint();
    private Paint textPaint = new Paint();
    Paint helpPaint = new Paint();
    final int startAngle = 270;
    private volatile float  VOLATILE_SWAP_ANGLE = 0;
    private String text = "0 %";
    private String title;
   // private volatile double SENDED_BYTES = 0;

    public SharedCircleCustomView(Context context,  String title) {
        super(context);
        this.title = title;
        rect = new RectF();
        paint.setColor(getResources().getColor(R.color.progress_green));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(12f);
        paint.setTextSize(28f);
        paint.setAntiAlias(true);

        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(35f);

        helpPaint.setColor(Color.LTGRAY);
        helpPaint.setStyle(Paint.Style.STROKE);
        helpPaint.setStrokeWidth(12f);

        setBackgroundResource(R.drawable.view_border);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        synchronized (this) {
            // private String title;
            int w = getWidth() >> 1;
            int h = getHeight() >> 1;
            int minDist = Math.min(w,h);
            int radius = (minDist / 2) + (minDist / 4);

            canvas.drawCircle(w,h,radius,helpPaint);

            rect.set(w - radius, h - radius, w + radius, h + radius);
            canvas.drawText(text, w - (paint.measureText(text)/2), h, textPaint);
            canvas.drawArc(rect,startAngle, VOLATILE_SWAP_ANGLE, false, paint);

            int titlePos = (minDist / 2) + (minDist / 3);
            canvas.drawText(title, 0,h - titlePos,textPaint);
        }

    }


    @Override
    public void visualize(double partByte,double allByte) {
      //  MyConsole.println("value = " + value);
        synchronized (this) {
            VOLATILE_SWAP_ANGLE += (float)(360.0 * (partByte / allByte) );
        //    MyConsole.println(" VOLATILE_SWAP_ANGLE = " + VOLATILE_SWAP_ANGLE);
        //    SENDED_BYTES += partByte;
            this.text =  Math.round(VOLATILE_SWAP_ANGLE /  3.6) + " %";//getHumanReadableBytes(SENDED_BYTES) ;

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
