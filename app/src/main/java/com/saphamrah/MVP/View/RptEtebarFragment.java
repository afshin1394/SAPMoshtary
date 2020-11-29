package com.saphamrah.MVP.View;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphamrah.Adapter.RptEtebarAdapter;
import com.saphamrah.BaseMVP.RptEtebarMVP;
import com.saphamrah.BaseMVP.RptMojodiAnbrakMVP;
import com.saphamrah.MVP.Presenter.RptEtebarPresenter;
import com.saphamrah.R;
import com.saphamrah.UIModel.rptEtebarModel.RptEtebarParentModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;
import com.saphamrah.databinding.RptEtebarFragmentBinding;

import java.text.DecimalFormat;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RptEtebarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RptEtebarFragment extends Fragment implements RptEtebarMVP.RequiredViewOps {

    private static final String ARG_CCFOROSHANDE = "ccForoshande";
    private static final String ARG_CCMOSHTARY = "ccMoshtary";
    private static final String ARG_CCSAZMANFOROSH = "ccSazmanForosh";
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;
    private RptEtebarMVP.PresenterOps mPresenter;

    private RptEtebarFragmentBinding mBinding;
    private RptEtebarAdapter mAdapter;
    private int mCcForoshande;
    private int mCcMoshtary;
    private int mCcSazmanForosh;


    public RptEtebarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param ccForoshande Parameter 1.
     * @param ccMoshtary   Parameter 2.
     * @param ccSazmanForosh   Parameter 3.
     * @return A new instance of fragment RptEtebarFragment.
     */

    public static RptEtebarFragment newInstance(int ccForoshande, int ccMoshtary, int ccSazmanForosh) {
        RptEtebarFragment fragment = new RptEtebarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CCFOROSHANDE, ccForoshande);
        args.putInt(ARG_CCMOSHTARY, ccMoshtary);
        args.putInt(ARG_CCSAZMANFOROSH, ccSazmanForosh);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCcForoshande = getArguments().getInt(ARG_CCFOROSHANDE);
            mCcMoshtary = getArguments().getInt(ARG_CCMOSHTARY);
            mCcSazmanForosh = getArguments().getInt(ARG_CCSAZMANFOROSH);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.rpt_etebar_fragment, container, false);
        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont(getActivity(), getResources().getString(R.string.fontPath), true);

        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Calligrapher calligrapher = new Calligrapher(getContext());
        calligrapher.setFont(view, getContext().getResources().getString(R.string.fontPath));


        stateMaintainer = new StateMaintainer(getFragmentManager(), TAG, getContext());
        startMVPOps();
        mPresenter.getEtebar(mCcForoshande, mCcMoshtary, mCcSazmanForosh);
    }

    @Override
    public Context getAppContext() {
        return getContext();
    }

    @Override
    public void setAdapter(List<RptEtebarParentModel> rptEtebarModels) {
        mBinding.recyclerViewEtebarReports.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        if (mAdapter == null) {
            mAdapter = new RptEtebarAdapter(rptEtebarModels, getContext());
            mBinding.recyclerViewEtebarReports.setAdapter(mAdapter);
        } else {
            mAdapter.setEtebarList(rptEtebarModels);
            mAdapter.notifyDataSetChanged();
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", RptEtebarFragment.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(RptEtebarMVP.RequiredViewOps view) {
        try {
            mPresenter = new RptEtebarPresenter(view);
            stateMaintainer.put(RptEtebarMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", RptEtebarFragment.class.getSimpleName(), "initialize", "");
        }
    }


    private void reinitialize(RptEtebarMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(RptEtebarMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", RptEtebarFragment.class.getSimpleName(), "reinitialize", "");
            }
        }
    }


}