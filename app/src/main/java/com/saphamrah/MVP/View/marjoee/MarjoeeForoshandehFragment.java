package com.saphamrah.MVP.View.marjoee;


import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Adapter.marjoee.MarjoeeForoshandehAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.marjoee.MarjoeeForoshandehMVP;
import com.saphamrah.MVP.Presenter.marjoee.MarjoeeForoshandehPresenter;
import com.saphamrah.Model.ElamMarjoeeForoshandehModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anwarshahriar.calligrapher.Calligrapher;

public class MarjoeeForoshandehFragment extends Fragment implements MarjoeeForoshandehMVP.RequiredViewOps {
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;
    private MarjoeeForoshandehMVP.PresenterOps mPresenter;
    private String ccDarkhastFaktor;
    private String ccMoshtary;
    private CustomAlertDialog customAlertDialog;


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    MarjoeeForoshandehAdapter adapter;
    private int noeSabtMarjoeeForMamorPakhsh;
    private int noeMasouliat;
    ArrayList<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModelsAdpater = new ArrayList<>();
    ElamMarjoeeForoshandehModel elamMarjoeeForoshandehModelCheckTaeidSabt = new ElamMarjoeeForoshandehModel();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        Constants.SELECTED_FRAGMENT = 1;
        TextView lblActivityTitle = getActivity().findViewById(R.id.lblActivityTitle);
        lblActivityTitle.setText("مرجوعی فروشنده");

        return inflater.inflate(R.layout.fragment_marjoee_foroshandeh, container, false);
    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Calligrapher calligrapher = new Calligrapher(BaseApplication.getContext());
        calligrapher.setFont(getActivity(), getResources().getString(R.string.fontPath), true);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ccDarkhastFaktor = getArguments().getString("marjoee");
            ccMoshtary = getArguments().getString("ccMoshtaryMarjoee");
        }
        stateMaintainer = new StateMaintainer(getFragmentManager(), TAG, BaseApplication.getContext());
        startMVPOps();
        customAlertDialog = new CustomAlertDialog(getActivity());
        mPresenter.getMarjoee(Long.parseLong(ccDarkhastFaktor));
        mPresenter.getForoshandehMamorPakhshInfo();

    }


    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(getActivity(), getResources().getString(resId), messageType, duration);
    }

    @Override
    public void onGetMarjoee(ArrayList<ElamMarjoeeForoshandehModel> elamMarjoeeForoshandehModels) {
        elamMarjoeeForoshandehModelsAdpater = elamMarjoeeForoshandehModels;
        adapter = new MarjoeeForoshandehAdapter(BaseApplication.getContext(), elamMarjoeeForoshandehModelsAdpater, (operation, elamMarjoeeForoshandehModel, position) -> {

            if (Constants.CLEARING() == operation) {
                elamMarjoeeForoshandehModelCheckTaeidSabt = elamMarjoeeForoshandehModel;
                showEditCountmAlert(elamMarjoeeForoshandehModel.getCcElamMarjoeeSatr(), elamMarjoeeForoshandehModel.getNameKala(), elamMarjoeeForoshandehModel.getTedad3(), position);
            } else if (Constants.DELETE() == operation) {
                elamMarjoeeForoshandehModelCheckTaeidSabt = elamMarjoeeForoshandehModel;
                mPresenter.checkTaeidSabtMarjoee(elamMarjoeeForoshandehModelCheckTaeidSabt, ccDarkhastFaktor, elamMarjoeeForoshandehModel.getCcElamMarjoeeSatr(), 0, 0, position);
                mPresenter.deleteMarjoee(ccDarkhastFaktor);
            }


        });
        recyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onGetForoshandehMamorPakhshInfo(int noeMasouliat, int noeSabtMarjoee) {
        this.noeSabtMarjoeeForMamorPakhsh = noeSabtMarjoee;
        this.noeMasouliat = noeMasouliat;
    }

    @Override
    public void onTaeidSabtMarjoee(int selectedCount, int position) {
        elamMarjoeeForoshandehModelsAdpater.get(position).setExtraProp_TedadMarjoee(selectedCount);
        adapter.notifyItemChanged(position);
    }


    /**
     * show dialog for get marjoee count
     *
     * @param ccElamMarjoeeSatr
     * @param itemName
     * @param itemCount
     * @param position
     */
    private void showEditCountmAlert(final int ccElamMarjoeeSatr, String itemName, final int itemCount, int position) {
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getResources().getString(R.string.fontPath));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_edit_item_count, null);
        TextView lblItemName = view.findViewById(R.id.lblItemName);
        final EditText txtCount = view.findViewById(R.id.txtCount);
        final TextView lblError = view.findViewById(R.id.lblError);
        Button btnOK = view.findViewById(R.id.btnApply);
        lblItemName.setTypeface(font);
        txtCount.setTypeface(font);
        lblError.setTypeface(font);
        btnOK.setTypeface(font);
        lblItemName.setText(itemName);
        txtCount.setText(String.valueOf(itemCount));
        builder.setCancelable(true);
        builder.setView(view);
        builder.create();
        if (!(getActivity()).isFinishing()) {
            final AlertDialog show = builder.show();
            try {
                if (show.getWindow() != null) {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(getActivity(), Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeeForoshandehFragment", "showEditCountmAlert", "");
            }
            btnOK.setOnClickListener(v -> {
                lblError.setVisibility(View.GONE);
                /*
                selectedCount is marjoee count user
                 */
                int selectedCount = itemCount;
                try {
                    selectedCount = Integer.parseInt(txtCount.getText().toString());

                    /*
                    check noe noeMasouliat
                     */
                    Log.i("MarjoeeForoshandeh", "noe masouliat : " + noeMasouliat);
                    if (noeMasouliat == 4 || noeMasouliat == 5) {
                        Log.i("MarjoeeForoshandeh", "noeSabtMarjoeeForMamorPakhsh : " + noeSabtMarjoeeForMamorPakhsh);
                        if (noeSabtMarjoeeForMamorPakhsh == Constants.NOE_SABT_MARJOEE_2) {
                            if (selectedCount > 0 && selectedCount <= itemCount) {
                                show.dismiss();
                                //TODO::
//                                    mPresenter.checkUpdateCountOfMarjoee(ccDarkhastFaktor, ccElamMarjoeeSatr, itemCount, selectedCount);
                            } else {
                                lblError.setVisibility(View.VISIBLE);
                                lblError.setText(getResources().getString(R.string.invalidRangeForEditMarjoee));
                            }
                        } else {
                            if (selectedCount > 0 && selectedCount <= elamMarjoeeForoshandehModelsAdpater.get(position).getTedad3()) {

                                mPresenter.checkTaeidSabtMarjoee(elamMarjoeeForoshandehModelCheckTaeidSabt, ccDarkhastFaktor, ccElamMarjoeeSatr, itemCount, selectedCount, position);
                                show.dismiss();


                            } else if (selectedCount == 0) {
                                lblError.setVisibility(View.VISIBLE);
                                lblError.setText(getResources().getString(R.string.errorZeroCount));
                            } else if (selectedCount > 0 && selectedCount > elamMarjoeeForoshandehModelsAdpater.get(position).getTedad3()) {
                                lblError.setVisibility(View.VISIBLE);
                                lblError.setText(getResources().getString(R.string.errorBiggerThanMojodi));
                            }
                        }
                    } else {
                        if (selectedCount > 0) {
                            show.dismiss();
//                            elamMarjoeeForoshandehModelsAdpater.get(position).setTedadMarjoee(selectedCount);
//                            adapter.notifyItemChanged(position);
                            //TODO::
//                                mPresenter.checkUpdateCountOfMarjoee(ccDarkhastFaktor, ccElamMarjoeeSatr, itemCount, selectedCount);
                        } else {
                            lblError.setVisibility(View.VISIBLE);
                            lblError.setText(getResources().getString(R.string.errorZeroCount));
                        }
                    }
                } catch (Exception e) {
                    lblError.setVisibility(View.VISIBLE);
                    lblError.setText(getResources().getString(R.string.invalidInputCount));
                }
            });

        }
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeiForoshandehFragment", "startMVPOps", "");
        }
    }


    private void initialize(MarjoeeForoshandehMVP.RequiredViewOps view) {
        try {
            mPresenter = new MarjoeeForoshandehPresenter(view);
            stateMaintainer.put(MarjoeeForoshandehMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeiForoshandehFragment", "initialize", "");
        }
    }


    private void reinitialize(MarjoeeForoshandehMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(MarjoeeForoshandehMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeiForoshandehFragment", "reinitialize", "");
            }
        }
    }

}
