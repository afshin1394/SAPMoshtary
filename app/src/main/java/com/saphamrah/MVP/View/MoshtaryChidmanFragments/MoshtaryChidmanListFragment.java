package com.saphamrah.MVP.View.MoshtaryChidmanFragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.MoshtaryChidmanAdapter;
import com.saphamrah.MVP.View.MoshtaryChidmanActivity;
import com.saphamrah.Model.MoshtaryChidmanModel;
import com.saphamrah.PubFunc.ImageUtils;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MoshtaryChidmanListFragment extends Fragment {


    private final int TAKE_IMAGE = 101;
    int selectedPosition = -1;
    private FloatingActionButton fabAddChidman;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton fabDelete;
    private FloatingActionButton fabSend;

    public MoshtaryChidmanListFragment() {
        // Required empty public constructor
    }

    ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels;
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moshtaryChidmanModels = new ArrayList<>();
        moshtaryChidmanModels = ((MoshtaryChidmanActivity)getActivity()).getMoshtaryChidmanModels();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moshtary_chidman_list, container, false);
        findViews(view);
        return view;
    }

    private void findViews(View view) {
        initializeRecyclerView(view);
        fabAddChidman = view.findViewById(R.id.fabAdd);
        fabDelete = view.findViewById(R.id.fabDelete);
        fabSend = view.findViewById(R.id.fabSend);
        floatingActionMenu = view.findViewById(R.id.fabMenu);
        fabAddChidman.setVisibility(View.VISIBLE);
        fabSend.setVisibility(hasNotSend(moshtaryChidmanModels)?View.VISIBLE:View.GONE);

        fabAddChidman.setOnClickListener(v -> {
            ((MoshtaryChidmanActivity) context).openCamera(TAKE_IMAGE);
        });
        fabSend.setOnClickListener(v -> {
            ((MoshtaryChidmanActivity) context).sendMoshtaryChidman(moshtaryChidmanModels);
        });



    }

    private void initializeRecyclerView(View view) {

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        MoshtaryChidmanAdapter  moshtaryChidmanAdapter = new MoshtaryChidmanAdapter(context, moshtaryChidmanModels, new MoshtaryChidmanAdapter.IMoshtaryChidmanAdapter() {

            @Override
            public void onItemClick(MoshtaryChidmanModel moshtaryChidmanModel , int selectedPosition)
            {

                if (selectedPosition != -1){
                    floatingActionMenu.close(false);
                    fabDelete.setVisibility(View.GONE);
                    fabAddChidman.setVisibility(View.VISIBLE);

                }else {
                    ((MoshtaryChidmanActivity) context).openAddFragment(moshtaryChidmanModel, MoshtaryChidmanActivity.Action.UPDATE);
                }
            }

            @Override
            public void onItemLongClick(MoshtaryChidmanModel moshtaryChidmanModel,int selectedPosition) {
                fabAddChidman.setVisibility(View.GONE);
                fabSend.setVisibility(View.GONE);
                fabDelete.setVisibility(View.VISIBLE);
                floatingActionMenu.open(false);
                MoshtaryChidmanListFragment.this.selectedPosition = selectedPosition;
                fabDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MoshtaryChidmanActivity) context).deleteMoshtaryChidman(moshtaryChidmanModel);
                        fabDelete.setVisibility(View.GONE);
                    }
                });

            }
        });

        LinearLayoutManager moshtaryChidmanLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(moshtaryChidmanLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.scrollToPosition(MoshtaryChidmanListFragment.this.selectedPosition );
        recyclerView.setAdapter(moshtaryChidmanAdapter);
    }

    private boolean hasNotSend(ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels){
        for (MoshtaryChidmanModel moshtaryChidmanModel : moshtaryChidmanModels)
        {
            if (moshtaryChidmanModel.getExtraProp_IsSend() == 0)
            {
                return true;
            }
        }
        return false;

    }




}