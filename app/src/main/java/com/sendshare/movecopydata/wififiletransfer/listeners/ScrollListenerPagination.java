package com.sendshare.movecopydata.wififiletransfer.listeners;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class ScrollListenerPagination extends RecyclerView.OnScrollListener {
    private boolean fingerDown;
    private LinearLayoutManager layoutManager;
    public ScrollListenerPagination(LinearLayoutManager linearLayoutManager) {
        this.layoutManager = linearLayoutManager;
    }
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if(newState == 1) {
            fingerDown = true;
        }
    }

    @Override
    public void onScrolled( RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(dy > 0) {
            int lastVisibleCount = layoutManager.findLastVisibleItemPosition();
            int totalItems = layoutManager.getItemCount();
            if(fingerDown && lastVisibleCount >= totalItems - 1) {
                fingerDown = false;
                onLoadMoreItems();
            }
        }
    }

    public abstract void onLoadMoreItems();

}
