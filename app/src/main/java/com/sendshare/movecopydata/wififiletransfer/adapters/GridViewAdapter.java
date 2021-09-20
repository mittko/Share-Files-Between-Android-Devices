/*
package com.sendshare.movecopydata.wififiletransfer.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sendshare.movecopydata.wififiletransfer.ui.CircleCustomView;
import com.sendshare.movecopydata.wififiletransfer.ui.CustomView;

import java.util.ArrayList;

public class GridViewAdapter extends BaseAdapter {

    private ArrayList<CircleCustomView> circleCustomViews;
    private ArrayList<CustomView> linearCustomViews;

    public GridViewAdapter(ArrayList<CustomView> linearCustomViews) {//ArrayList<CircleCustomView> circleCustomViews
        this.linearCustomViews = linearCustomViews;
    }
    @Override
    public int getCount() {
        return linearCustomViews.size();
    } //circleCustomViews.size()

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return linearCustomViews.get(i); //circleCustomViews.get(i)
    }
}
*/
