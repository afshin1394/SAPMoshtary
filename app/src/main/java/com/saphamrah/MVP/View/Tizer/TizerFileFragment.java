package com.saphamrah.MVP.View.Tizer;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Adapter.tizerAdapter.TizerFileAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.RptThreeMonthPurchaseMVP;
import com.saphamrah.BaseMVP.TizerMVP;
import com.saphamrah.MVP.Presenter.TizerPresenter;
import com.saphamrah.Model.TizerModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import com.saphamrah.Utils.VideoPlayerActivity;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anwarshahriar.calligrapher.Calligrapher;

public class TizerFileFragment extends Fragment implements TizerMVP.RequiredViewOps {
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;
    private TizerMVP.PresenterOps mPresenter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    TizerFileAdapter tizerAdapter;
    private int widthPixels;
    String folderName ;
    private int file_size;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_tizer_file, container, false);
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Calligrapher calligrapher = new Calligrapher(BaseApplication.getContext());
        calligrapher.setFont(getActivity(), getResources().getString(R.string.fontPath), true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            folderName = getArguments().getString("folderName");
        }


        /*
        get width
         */
        getWidth();

        stateMaintainer = new StateMaintainer(getFragmentManager(), TAG, BaseApplication.getContext());
        startMVPOps();
        mPresenter.getListFile(folderName);


    }


    /*
    get result get Folder and use TizerFolderFragment
     */
    @Override
    public void setListFolder(ArrayList<String> tizerModels) {



    }
    /*
        get result get Folder and use TizerFilerFragment
         */
    @Override
    public void setListFile(ArrayList<TizerModel> tizerModels) {
        tizerAdapter = new TizerFileAdapter(BaseApplication.getContext(), tizerModels, widthPixels, (url, position) -> {

            new Thread(new Runnable() {
                @SuppressLint("ShowToast")
                @Override
                public void run() {
                    try {
                        URL myUrl = new URL(url);
                        URLConnection urlConnection = myUrl.openConnection();
                        urlConnection.connect();
                        file_size = urlConnection.getContentLength();
                        Log.i("TizerFileFragment", "file_size = " + file_size);
                        if (file_size > 1500) {

                            startActivityBundle(VideoPlayerActivity.class , "LinkVideo" , url);
                        } else {

                            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showToast();
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            /*
            check
             */



        });
        RecyclerView.LayoutManager rvLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(rvLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(BaseApplication.getContext(), 0));
        recyclerView.setAdapter(tizerAdapter);
    }

    /*
      use TizerFolderFragment for refresh
     */
    @Override
    public void closeLoadingDialog() {

    }

    @Override
    public void showToast(int resId, int messageType, int duration) {

    }


    private void showToast(){
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(getActivity());
        customAlertDialog.showToast(getActivity(), getResources().getString(R.string.failPlayTizer) ,Constants.FAILED_MESSAGE(), Constants.DURATION_LONG() );

    }


    /*
          get width
           */
    private void getWidth() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        widthPixels = displaymetrics.widthPixels;
        widthPixels = (widthPixels / 2) + 50;
    }
    private void startActivityBundle(Class<?> otherActivityClass , String keyValue ,  String bundle){
        Intent i = new Intent(BaseApplication.getContext(), otherActivityClass);
        i.putExtra(keyValue, bundle);
        startActivity(i);
    }
    /*
    set Up start
     */
    public void startMVPOps() {
        try {
            if (stateMaintainer.firstTimeIn()) {
                initialize(this);
            } else {
                reinitialize(this);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptThreeMonthPurchaseActivity", "startMVPOps", "");
        }
    }


    private void initialize(TizerMVP.RequiredViewOps view) {
        try {
            mPresenter = new TizerPresenter(view);
            stateMaintainer.put(RptThreeMonthPurchaseMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptThreeMonthPurchaseActivity", "initialize", "");
        }
    }


    private void reinitialize(TizerMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(TizerMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptThreeMonthPurchaseActivity", "reinitialize", "");
            }
        }
    }
}
