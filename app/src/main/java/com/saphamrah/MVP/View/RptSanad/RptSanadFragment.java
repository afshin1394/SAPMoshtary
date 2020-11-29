package com.saphamrah.MVP.View.RptSanad;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphamrah.Adapter.RptSanadAdapter;
import com.saphamrah.BaseMVP.RptMojodiAnbrakMVP;
import com.saphamrah.BaseMVP.RptSanadMVP;
import com.saphamrah.MVP.Presenter.RptSanadPresenter;
import com.saphamrah.Model.RptSanadModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.StateMaintainer;
import com.saphamrah.databinding.RptSanadFragmentBinding;

import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RptSanadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RptSanadFragment extends Fragment implements RptSanadMVP.RequiredViewOps {

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;
    private RptSanadMVP.PresenterOps mPresenter;

    private RptSanadFragmentBinding mBinding;
    private RptSanadAdapter mRptSanadAdapter;
    private CustomLoadingDialog mCustomLoadingDialog;
    private AlertDialog mAlertDialogLoading;

    public RptSanadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RptSanadFragment.
     */

    public static RptSanadFragment newInstance() {
        RptSanadFragment fragment = new RptSanadFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.rpt_sanad_fragment, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont(view, getContext().getResources().getString(R.string.fontPath));

        mCustomLoadingDialog = new CustomLoadingDialog();

        stateMaintainer = new StateMaintainer(getFragmentManager(), TAG, getContext());
        startMVPOps();
        setListener();
        mPresenter.getRptSanad();
    }

    private void setListener() {
        mBinding.fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.fabMenu.close(true);
                mAlertDialogLoading = mCustomLoadingDialog.showLoadingDialog(getActivity());
                mPresenter.updateRptSanad();
            }
        });

    }


    @Override
    public Context getAppContext() {
        return getContext();
    }

    @Override
    public void setAdapter(List<RptSanadModel> rptSanadModels) {
        mBinding.recyclerViewSanadReport.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        if (mRptSanadAdapter == null) {
            mRptSanadAdapter = new RptSanadAdapter(rptSanadModels, getContext());
            mBinding.recyclerViewSanadReport.setAdapter(mRptSanadAdapter);
        } else {
            mRptSanadAdapter.setSanadList(rptSanadModels);
            mRptSanadAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void closeLoadingDialog() {
        if (mAlertDialogLoading != null) {
            try {
                mAlertDialogLoading.dismiss();
            } catch (Exception exception) {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptCheckBargashtyActivity", "closeLoadingAlert", "");
            }
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(getActivity());
        customAlertDialog.showToast(getActivity(), getResources().getString(resId), messageType, duration);
    }


    public void startMVPOps() {
        try {
            if (stateMaintainer.firstTimeIn()) {
                initialize(this);
            } else {
                reinitialize(this);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", RptSanadFragment.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(RptSanadMVP.RequiredViewOps view) {
        try {
            mPresenter = new RptSanadPresenter(view);
            stateMaintainer.put(RptMojodiAnbrakMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", RptSanadFragment.class.getSimpleName(), "initialize", "");
        }
    }

    private void reinitialize(RptSanadMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(RptSanadMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", RptSanadFragment.class.getSimpleName(), "reinitialize", "");
            }
        }
    }
}