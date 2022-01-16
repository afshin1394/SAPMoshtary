package com.saphamrah.MVP.View.marjoee;


import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.saphamrah.Adapter.CustomSpinnerAdapter;
import com.saphamrah.Adapter.marjoee.MarjoeeMorediAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.marjoee.MarjoeeMorediMVP;
import com.saphamrah.MVP.Presenter.marjoee.MarjoeeMorediPresenter;
import com.saphamrah.Model.ElatMarjoeeKalaModel;
import com.saphamrah.Model.MarjoeeMamorPakhshModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.anwarshahriar.calligrapher.Calligrapher;

public class MarjoeeMorediFragment extends Fragment implements MarjoeeMorediMVP.RequiredViewOps , IOnclickSearchNameKalaMarjoee {
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;
    private MarjoeeMorediPresenter mPresenter;
    private String ccDarkhastFaktor;
    private CustomAlertDialog customAlertDialog;
    private String ccMoshtary;
    private MarjoeeMorediAdapter adapter;
    private MarjoeeMamorPakhshModel marjoeeMamorPakhshModelCheckTaeidSabt ;
    private ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModelsAdpater ;
    private ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModelsAll ;
    ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels = new ArrayList<>();
    ArrayList<String> elatMarjoeeKalaTitles = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MaterialSearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Constants.SELECTED_FRAGMENT = 2;
        TextView lblActivityTitle = getActivity().findViewById(R.id.lblActivityTitle);
        searchView = getActivity().findViewById(R.id.searchView);

        lblActivityTitle.setText("مرجوعی موردی");
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
        /**
         * set new
         */
        marjoeeMamorPakhshModelCheckTaeidSabt = new MarjoeeMamorPakhshModel();
        marjoeeMamorPakhshModelsAdpater = new ArrayList<>();
        startMVPOps();
        searchView.setVoiceSearch(false);
        searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        recyclerSetup();
                customAlertDialog = new CustomAlertDialog(getActivity());
        mPresenter.getMarjoeeMoredi(Integer.parseInt(ccMoshtary));
        mPresenter.getElatMarjoeeMoredi();

    }


    @Override
    public void onResume() {

        /**
         * search name kala
         */

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                String searchWord = query.trim();

                    mPresenter.searchNameKala(searchWord , marjoeeMamorPakhshModelsAll);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0)
                {
                    mPresenter.searchNameKala(newText , marjoeeMamorPakhshModelsAll);
                }
                else
                {
                    mPresenter.getMarjoeeMoredi(Integer.parseInt(ccMoshtary));
                    visibleCloseSearchIcon();
                }
                return false;
            }
        });

        getActivity().findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.closeSearch();
                mPresenter.getMarjoeeMoredi(Integer.parseInt(ccMoshtary));
            }
        });

        getActivity().findViewById(com.miguelcatalan.materialsearchview.R.id.action_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchWord = ((TextView)getActivity().findViewById(com.miguelcatalan.materialsearchview.R.id.searchTextView)).getText().toString().trim();
                mPresenter.searchNameKala(searchWord , marjoeeMamorPakhshModelsAll);
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                visibleCloseSearchIcon();
            }

            @Override
            public void onSearchViewClosed() {
                mPresenter.getMarjoeeMoredi(Integer.parseInt(ccMoshtary));
            }
        });
        super.onResume();
    }

    private void visibleCloseSearchIcon()
    {
        getActivity().findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {

    }

    @Override
    public void onGetMarjoeeMoredi(ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModels) {
        marjoeeMamorPakhshModelsAdpater.clear();
        marjoeeMamorPakhshModelsAdpater.addAll(marjoeeMamorPakhshModels);
        marjoeeMamorPakhshModelsAll = marjoeeMamorPakhshModels;
        adapter.notifyDataSetChanged();

    }
    /**
     * setup recycler
     */
    private void recyclerSetup(){
        adapter = new MarjoeeMorediAdapter(BaseApplication.getContext() , marjoeeMamorPakhshModelsAdpater , true , (operation, model, position) ->{
            if (Constants.CLEARING() == operation) {
                marjoeeMamorPakhshModelCheckTaeidSabt = model;
                showEditCountmAlert(model.getCcMarjoeeMamorPakhsh(), model.getNameKala(), model.getTedad3(), position);
            } else if (Constants.DELETE() == operation) {
                marjoeeMamorPakhshModelCheckTaeidSabt = model;
                mPresenter.checkTaeidSabtMarjoee(marjoeeMamorPakhshModelCheckTaeidSabt, Long.valueOf(ccDarkhastFaktor), 0, 0, position , null);
                mPresenter.deleteMarjoee(model.getCcMarjoeeMamorPakhsh() , model.getCcMoshtary(),model.getCcKalaCode());
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    /**
     * click on search name kala fab
     */
    @Override
    public void clickSearchNameKalaMarjoee() {
        searchView.showSearch();
        searchView.setHint(getResources().getString(R.string.searchKalaName));
    }

    /**
     *get result elat marjoee
     */
    @Override
    public void onGetElatMarjoeeMoredi(ArrayList<ElatMarjoeeKalaModel> elatMarjoeeKalaModels, ArrayList<String> elatMarjoeeKalaTitles) {
        this.elatMarjoeeKalaModels.addAll(elatMarjoeeKalaModels) ;
        this.elatMarjoeeKalaTitles.addAll(elatMarjoeeKalaTitles);
    }

    @Override
    public void onTaeidSabtMarjoee(int selectedCount,String nameelatMarjoee, int position) {
        marjoeeMamorPakhshModelsAdpater.get(position).setExtraProp_TedadNahaeeMarjoee(selectedCount);
        marjoeeMamorPakhshModelsAdpater.get(position).setExtraProp_NameElatMarjoee(nameelatMarjoee);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void onGetSearch(ArrayList<MarjoeeMamorPakhshModel> marjoeeMamorPakhshModelsSearch) {
        marjoeeMamorPakhshModelsAdpater.clear();
        marjoeeMamorPakhshModelsAdpater.addAll(marjoeeMamorPakhshModelsSearch);
        adapter.notifyDataSetChanged();

    }


    /**
     * show dialog for get marjoee count
     *
     * @param ccMarjoeeMamorPakhsh
     * @param itemName
     * @param itemCount
     * @param position
     */

    private void showEditCountmAlert( int ccMarjoeeMamorPakhsh, String itemName,  int itemCount, int position) {
         ArrayList<ElatMarjoeeKalaModel> elatMarjoee = new ArrayList<>();
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), getResources().getString(R.string.fontPath));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.alert_edit_item_count_moredi, null);
        TextView lblItemName = view.findViewById(R.id.lblItemName);
         EditText txtCount = view.findViewById(R.id.txtCount);
         TextView lblError = view.findViewById(R.id.lblError);
        Spinner spinnerElatMarjoee = view.findViewById(R.id.spinnerSelectElatMarjoee);
        Button btnOK = view.findViewById(R.id.btnApply);
        lblItemName.setTypeface(font);
        txtCount.setTypeface(font);
        lblError.setTypeface(font);
        btnOK.setTypeface(font);
        lblItemName.setText(itemName);
        txtCount.setText(String.valueOf(itemCount));
        CustomSpinnerAdapter adapterOne = new CustomSpinnerAdapter(BaseApplication.getContext(), android.R.layout.simple_spinner_item, elatMarjoeeKalaTitles);
        adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerElatMarjoee.setAdapter(adapterOne);
        builder.setCancelable(true);
        builder.setView(view);
        builder.create();
        if (!(getActivity()).isFinishing()) {
             AlertDialog show = builder.show();
            try {
                if (show.getWindow() != null) {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(getActivity(), Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeeForoshandehFragment", "showEditCountmAlert", "");
            }

            spinnerElatMarjoee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    elatMarjoee.clear();
                    elatMarjoee.add(elatMarjoeeKalaModels.get(position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            btnOK.setOnClickListener(v -> {
                lblError.setVisibility(View.GONE);
                /*
                selectedCount is marjoee count user
                 */
                int selectedCount = itemCount;
                try {
                    selectedCount = Integer.parseInt(txtCount.getText().toString());

                    if (elatMarjoee.size() > 0) {

                        if (selectedCount > 0 && selectedCount <= marjoeeMamorPakhshModelsAdpater.get(position).getTedad3()) {
                            mPresenter.checkTaeidSabtMarjoee(marjoeeMamorPakhshModelCheckTaeidSabt, Long.valueOf(ccDarkhastFaktor), itemCount, selectedCount, position , elatMarjoee);
                            show.dismiss();
                        } else if (selectedCount == 0) {
                            lblError.setVisibility(View.VISIBLE);
                            lblError.setText(getResources().getString(R.string.errorZeroCount));
                        } else if (selectedCount > 0 && selectedCount > marjoeeMamorPakhshModelsAdpater.get(position).getTedad3()) {
                            lblError.setVisibility(View.VISIBLE);
                            lblError.setText(getResources().getString(R.string.errorBiggerThanMojodi));
                        }
                    }
                else {
                        lblError.setVisibility(View.VISIBLE);
                        lblError.setText(getResources().getString(R.string.errorSelectElatMarjooe));
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeeMorediFragment", "startMVPOps", "");
        }
    }


    private void initialize(MarjoeeMorediMVP.RequiredViewOps view) {
        try {
            mPresenter = new MarjoeeMorediPresenter(view);
            stateMaintainer.put(MarjoeeMorediMVP.PresenterOps.class.getSimpleName(), mPresenter);
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeeMorediFragment", "initialize", "");
        }
    }


    private void reinitialize(MarjoeeMorediMVP.RequiredViewOps view) {
        try {
            mPresenter = stateMaintainer.get(MarjoeeMorediMVP.PresenterOps.class.getSimpleName());
            if (mPresenter == null) {
                initialize(view);
            } else {
                mPresenter.onConfigurationChanged(view);
            }
        } catch (Exception exception) {
            if (mPresenter != null) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MarjoeeMorediFragment", "reinitialize", "");
            }
        }
    }



}
