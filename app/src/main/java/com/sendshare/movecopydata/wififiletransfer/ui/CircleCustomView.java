package com.sendshare.movecopydata.wififiletransfer.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import com.sendshare.movecopydata.wififiletransfer.interfaces.VisualizeDataTransfer;
import com.wifi.mitko.sharewifiles3.R;

public class CircleCustomView extends View
 implements VisualizeDataTransfer {

    private RectF rect;
    private Paint paint = new Paint();
    private Paint textPaint = new Paint();
    Paint helpPaint = new Paint();
    final int startAngle = 270;
    private float  swapAngle = 0;
    private String text = "0 %";
    private String title;

    public CircleCustomView(String title, Context context) {
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
        textPaint.setTextSize(28f);

        helpPaint.setColor(Color.LTGRAY);
        helpPaint.setStyle(Paint.Style.STROKE);
        helpPaint.setStrokeWidth(12f);

        //setBackgroundResource(R.drawable.view_border);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // private String title;
        int w = getWidth() >> 1;
        int h = getHeight() >> 1;
        int minDist = Math.min(w,h);
        int radius = (minDist / 2) + (minDist / 4);

        canvas.drawCircle(w,h,radius,helpPaint);

        rect.set(w - radius, h - radius, w + radius, h + radius);
        canvas.drawText(text, w - (paint.measureText(text)/2), h, textPaint);
        canvas.drawArc(rect,startAngle, swapAngle, false, paint);

        int titlePos = (minDist / 2) + (minDist / 3);
        canvas.drawText(title, 0,h - titlePos,textPaint);
    }


    @Override
    public void visualize(double value, double value1) {
        swapAngle = (float)(360.0 * value);
        if(swapAngle < 360.0) {
            this.text = (int)(value * 100) + " %";
        } else {
            this.text = "Done !!!";
        }
        invalidate();
    }

    @Override
    public void showFileName(String fileName) {

    }

    @Override
    public void handleConnectionError(String text) {
        title = text;
    }
}
