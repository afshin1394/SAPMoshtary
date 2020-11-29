package com.saphamrah.MVP.View.Tizer;


import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.tizerAdapter.TizerFolderNameAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.RptThreeMonthPurchaseMVP;
import com.saphamrah.BaseMVP.TizerMVP;
import com.saphamrah.MVP.Presenter.TizerPresenter;
import com.saphamrah.Model.TizerModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anwarshahriar.calligrapher.Calligrapher;

public class TizerFolderFragment extends Fragment implements TizerMVP.RequiredViewOps {
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;
    private TizerMVP.PresenterOps mPresenter;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    TizerFolderNameAdapter tizerAdapter;
    private int widthPixels;
    @BindView(R.id.fabMenu)
    FloatingActionMenu fabMenu;
    @BindView(R.id.fabRefresh)
    FloatingActionButton fabRefresh;
    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_tizer_folder, container, false);
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Calligrapher calligrapher = new Calligrapher(BaseApplication.getContext());
        calligrapher.setFont(getActivity(), getResources().getString(R.string.fontPath), true);

        /*
        get width
         */
        getWidth();
        customAlertDialog = new CustomAlertDialog(getActivity());
        customLoadingDialog = new CustomLoadingDialog();
        stateMaintainer = new StateMaintainer(getFragmentManager(), TAG, BaseApplication.getContext());
        startMVPOps();
        mPresenter.getListFolder();
//        alertDialogLoading = customLoadingDialog.showLoadingDialog(getActivity());

        fabRefresh.setOnClickListener(v -> {
            fabMenu.close(true);
            alertDialogLoading = customLoadingDialog.showLoadingDialog(getActivity());
            mPresenter.updateData(TAG);
        });


    }

    /*
     get result get Folder and use TizerFolderFragment
      */
    @Override
    public void setListFolder(ArrayList<String> tizerModels) {
        tizerAdapter = new TizerFolderNameAdapter(BaseApplication.getContext(), tizerModels, widthPixels, (folderName, position) -> {
            TizerFileFragment tizerFileFragment=new TizerFileFragment();

            Bundle bundle = new Bundle();
            bundle.putString("folderName", folderName);
            tizerFileFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frag,tizerFileFragment)
                    .addToBackStack(TAG)
                    .commit();
        });
        RecyclerView.LayoutManager rvLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(rvLayoutManager);
//        recyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(BaseApplication.getContext(), 0));
        recyclerView.setAdapter(tizerAdapter);
        Log.i("", "");

    }

    /*
        get result get Folder and use TizerFilerFragment
         */
    @Override
    public void setListFile(ArrayList<TizerModel> tizerModels) {

    }

    @Override
    public void closeLoadingDialog() {
        if (alertDialogLoading != null) {
            try {
                alertDialogLoading.dismiss();
            } catch (Exception exception) {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptFaktorTozieNashodeActivity", "closeLoadingDialog", "");
            }
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(getActivity(), getResources().getString(resId), messageType, duration);

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
