package com.sendshare.movecopydata.wififiletransfer.fragments;


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

import com.sendshare.movecopydata.wififiletransfer.adapters.JustFilesRecyclerViewAdapter;
import com.sendshare.movecopydata.wififiletransfer.adapters.RecyclerViewAdapterAsPackages;
import com.sendshare.movecopydata.wififiletransfer.interfaces.ListRefresher3;
import com.sendshare.movecopydata.wififiletransfer.listeners.ScrollListenerPagination;
import com.sendshare.movecopydata.wififiletransfer.media.GetAudioFilesAsPackages;
import com.sendshare.movecopydata.wififiletransfer.media.GetDocumentFilesAsPackages;
import com.sendshare.movecopydata.wififiletransfer.media.GetImageFilesAsPackages;
import com.sendshare.movecopydata.wififiletransfer.media.GetVideoFilesAsPackages;
import com.wifi.mitko.sharewifiles3.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class JustFragmentFiles extends Fragment implements ListRefresher3 {

   // private RecyclerViewAdapterMediaFiles recyclerViewAdapter;
    private RecyclerViewAdapterAsPackages recyclerViewAdapterAsPackages;
    private int pageNumber;
    private static final String stringPage = "stringPage";
    JustFilesRecyclerViewAdapter justFilesRecyclerViewAdapter;
    public JustFragmentFiles() {
        // Required empty public constructor
    }

    public static Fragment getInstance(int page) {
        JustFragmentFiles justFragmentFiles = new JustFragmentFiles();
        justFragmentFiles.setPageNumber(page);
        Bundle arguments = new Bundle();
        arguments.putInt(stringPage,page);
        justFragmentFiles.setArguments(arguments);
        return justFragmentFiles;
    }
    private void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
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
        return inflater.inflate(R.layout.two_recycler_views, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initChildRecyclerView(view);
        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        recyclerViewAdapterAsPackages = new RecyclerViewAdapterAsPackages(getContext(), justFilesRecyclerViewAdapter);
        RecyclerView recyclerView = view.findViewById(R.id._packages);
//        float screenWidth = 200;
//        if(getActivity() != null) {
//             screenWidth = MyUtility.getWidthInPixels((AppCompatActivity) getActivity());
//        }
//            ConstraintLayout.LayoutParams layoutParams =
//                    new ConstraintLayout.LayoutParams((int) (1.3 / 4.0 * screenWidth), ConstraintLayout.LayoutParams.MATCH_PARENT);
//        recyclerView.setLayoutParams(layoutParams);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recyclerViewAdapterAsPackages);
    }
    private void initChildRecyclerView(View view) {
        justFilesRecyclerViewAdapter = new JustFilesRecyclerViewAdapter(getContext(),pageNumber);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        final RecyclerView recyclerView = view.findViewById(R.id.just_files);
//        float screenWidth = MyUtility.getWidthInPixels((AppCompatActivity) getContext());
//        ConstraintLayout.LayoutParams layoutParams =
//                new ConstraintLayout.LayoutParams((int)( 2.7/4.0 * screenWidth), ConstraintLayout.LayoutParams.MATCH_PARENT);
//        ConstraintSet set = new ConstraintSet();
//        ConstraintLayout constraintLayout =  view.findViewById(R.id.linear_layout_4);
//        RecyclerView button = view.findViewById(R.id._packages);
//        layoutParams.leftToRight = button.getId();
//        layoutParams.rightToRight = constraintLayout.getId();
//        set.applyTo(constraintLayout);
//        recyclerView.setLayoutParams(layoutParams);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(new ScrollListenerPagination(layoutManager) {
            @Override
            public void onLoadMoreItems() {
                if(recyclerViewAdapterAsPackages != null) {
                    recyclerViewAdapterAsPackages.loadMore();
                }
            }
        });

        recyclerView.removeAllViews();
        recyclerView.setAdapter(justFilesRecyclerViewAdapter);
      //  justFilesRecyclerViewAdapter.clearList();// ???

    }

    @Override
    public void updateList(HashMap<String, ArrayList<String>> map) {
        recyclerViewAdapterAsPackages.clearPackageList();
        recyclerViewAdapterAsPackages.updateList(map);
    }

    public void requestData(int page) {
        switch (page) {
            case 1 :
                GetVideoFilesAsPackages getVideoFiles =
                        new GetVideoFilesAsPackages(getContext(), this);
                getVideoFiles.execute();
                break;
            case 2 :
                GetImageFilesAsPackages getImageFilesAsPackagesFiles =
                        new GetImageFilesAsPackages(getContext(), this);
                getImageFilesAsPackagesFiles.execute();
                break;
            case 3 :
                GetAudioFilesAsPackages getAudioFilesAsPackages =
                        new GetAudioFilesAsPackages(getContext(), this);
                getAudioFilesAsPackages.execute();
                break;
            case 4 :
                GetDocumentFilesAsPackages getDocumentFilesAsPackages =
                        new GetDocumentFilesAsPackages(getContext(), this);
                getDocumentFilesAsPackages.execute();
                break;
            default:break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(justFilesRecyclerViewAdapter != null)
        justFilesRecyclerViewAdapter.clearSelectedItems();
    }
}
