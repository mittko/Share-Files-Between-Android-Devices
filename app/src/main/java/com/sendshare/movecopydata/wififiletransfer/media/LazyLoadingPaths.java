package com.sendshare.movecopydata.wififiletransfer.media;

import android.os.AsyncTask;

import com.sendshare.movecopydata.wififiletransfer.interfaces.ListRefresher;
import com.sendshare.movecopydata.wififiletransfer.utilities.Konstants;

import java.util.ArrayList;

public class LazyLoadingPaths extends AsyncTask<Void, Void, ArrayList<String>> {
    private ArrayList<String> paths;
    private String folder;
    private int scrollFrom;
    private ListRefresher listRefresher;
    public LazyLoadingPaths(ArrayList<String> paths, ListRefresher listRefresher, String folder, int scrollFrom) {
        this.paths = paths;
        this.listRefresher = listRefresher;
        this.scrollFrom = scrollFrom;
        this.folder = folder;
    }
    @Override
    protected ArrayList<String> doInBackground(Void... voids) {
        if(paths == null) return null;
        ArrayList<String> p = new ArrayList<>();
        for(int i = scrollFrom; i < scrollFrom + Konstants.ITEM_TO_SCROLL && i < paths.size(); i++) {
            p.add(paths.get(i));
        }
        return p;
    }

    @Override
    protected void onPostExecute(ArrayList<String> strings) {
        super.onPostExecute(strings);
        listRefresher.updateList(strings);
    }
}
