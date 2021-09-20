package com.sendshare.movecopydata.wififiletransfer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sendshare.movecopydata.wififiletransfer.adapters.ApkInfo;
import com.sendshare.movecopydata.wififiletransfer.adapters.RecyclerViewAdapterApk;
import com.sendshare.movecopydata.wififiletransfer.interfaces.ListRefresher2;
import com.sendshare.movecopydata.wififiletransfer.media.GetApkFiles;
import com.wifi.mitko.sharewifiles3.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class JustFragmentApk extends Fragment  implements ListRefresher2 {
    private RecyclerViewAdapterApk recyclerViewAdapterApk;
    private int scrollFrom = 0;
    private int pageNumber;
    private static final String stringPage = "stringPage";
    private RecyclerView recyclerView = null;
    public JustFragmentApk() {
        // Required empty public constructor
    }

    public static Fragment getInstance(int page) {
        JustFragmentApk justFragment = new JustFragmentApk();
        Bundle arguments = new Bundle();
        arguments.putInt(stringPage,page);
        justFragment.setArguments(arguments);
        return justFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            pageNumber = getArguments().getInt(stringPage);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_just2, container, false);
        initRecyclerView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requestApkData();
    }

    private void initRecyclerView(View view) {
        recyclerViewAdapterApk = new RecyclerViewAdapterApk(getContext());
        recyclerView = view.findViewById(R.id.files_recyclerview2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapterApk);
    }


     private void requestApkData() {
         GetApkFiles getApkFiles = new GetApkFiles(new WeakReference<Context>(getContext()),this);
         getApkFiles.execute();
     }
    @Override
    public void onStop() {
        super.onStop();
        recyclerViewAdapterApk.clearSelectedItems();
    }

    @Override
    public void updateList(ArrayList<ApkInfo> infos) {
        recyclerViewAdapterApk.updateList(infos);
    }

}
