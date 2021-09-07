package com.saphamrah.MVP.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.BaseMVP.RptMoshtaryGharardadMVP;
import com.saphamrah.MVP.Presenter.RptMoshtaryGharardadPresenter;
import com.saphamrah.Adapter.RptMoshtaryGharardadAdapter;
import com.saphamrah.Model.MoshtaryGharardadModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.RptMoshtaryGharardadUiModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.anwarshahriar.calligrapher.Calligrapher;


public class RptMoshtaryGharardad extends AppCompatActivity implements RptMoshtaryGharardadMVP.RequiredViewOps {

    private RptMoshtaryGharardadPresenter mPresenter;
    private BottomSheetBehavior bottomSheetBehavior;
    private CustomAlertDialog customAlertDialog;
    private ArrayList<String> moshtarytitle = new ArrayList<>();
    private ArrayList<MoshtaryModel> moshtaryModels = new ArrayList<>();
    private ArrayList<String> gharardadTitle = new ArrayList<>();
    private ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels = new ArrayList<>();
    private ArrayList<RptMoshtaryGharardadUiModel> rptMoshtaryGharardadUiModels = new ArrayList<>();
    private ArrayList<RptMoshtaryGharardadUiModel> rptSearchModel = new ArrayList<>();
    private CustomSpinner customSpinner;
    private int selectedCcMoshtaryParent;
    private String selectedNameMoshtary;
    private String selectedCodeMoshtary;
    private String selectedSazmanForosh;
    private String selectedShomarehGharardad;
    private String selectedCcMoshtaryGharardad;
    private String selectedCcSazmanForosh;
    private RptMoshtaryGharardadAdapter adapter;
    private int from = 0;

    @BindView(R.id.lnrlayRoot)
    CardView lnrlayBottomsheet;
    @BindView(R.id.fabMenu)
    FloatingActionMenu fabMenu;
    @BindView(R.id.txtMoshtary)
    EditText editTxtMoshtary;
    @BindView(R.id.txtGharardad)
    EditText editTxtGharardad;
    @BindView(R.id.layTitleCustomer)
    LinearLayout layTitleCustomer;
    @BindView(R.id.lblCustomerFullNameCode)
    TextView lblCustomerFullNameCode;
    @BindView(R.id.lblCustomerShomarehGharardad)
    TextView lblCustomerShomarehGharardad;
    @BindView(R.id.lblCustomerSazmanForosh)
    TextView lblCustomerSazmanForosh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.linTopLevel)
    LinearLayout linTopLevel;
    @BindView(R.id.searchKalaName)
    EditText searchKalaName;

    @OnClick(R.id.fabFilter)
    public void fabFilter(){
        fabMenu.close(true);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @OnClick(R.id.btnApply)
    public void btnApply(){

        if (!String.valueOf(editTxtMoshtary.getText()).equals(""))
            if (!String.valueOf(editTxtGharardad.getText()).equals("")){
                layTitleCustomer.setVisibility(View.VISIBLE);
                lblCustomerFullNameCode.setText(selectedNameMoshtary + " - " + selectedCodeMoshtary);
                lblCustomerSazmanForosh.setText(selectedSazmanForosh);
                lblCustomerShomarehGharardad.setText(getResources().getString(R.string.shomareGharardad) + " : " + selectedShomarehGharardad);
                mPresenter.getKala(selectedCcMoshtaryGharardad , selectedCcSazmanForosh , from);
            }else {
                layTitleCustomer.setVisibility(View.GONE);
            }


        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_moshtary_gharardad);
        ButterKnife.bind(this);
        customAlertDialog = new CustomAlertDialog(RptMoshtaryGharardad.this);
        customSpinner = new CustomSpinner();
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);
        mPresenter = new RptMoshtaryGharardadPresenter(this);
        bottomSheetBehavior = BottomSheetBehavior.from(lnrlayBottomsheet);


        Intent getIntent = getIntent();
        if (getIntent != null)
        from = getIntent.getIntExtra("from" , 0);

        /**
         * from == 1 : darkhastKalaActivity
         * from == 0 : menu
         */
        if (from == 1){
            mPresenter.getKala("" , "" , from);
            fabMenu.setVisibility(View.GONE);
        } else if (from == 0){
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            mPresenter.getMoshtary();
        }



        editTxtMoshtary.setOnClickListener(v -> openMoshtarySpinner());

        editTxtMoshtary.setOnFocusChangeListener((v, hasFocus) -> {
            changeDrawableLeftTint(editTxtMoshtary , hasFocus);
            if (hasFocus)
            {
                openMoshtarySpinner();
            }
        });

        editTxtGharardad.setOnClickListener(v -> {
            if (moshtaryGharardadModels.size() > 0) {
                openMoshtaryGharardadSpinner();
            }else {
                showToast(R.string.firstSelectedMoshtary, Constants.INFO_MESSAGE(),Constants.DURATION_LONG());
            }
                });



        editTxtGharardad.setOnFocusChangeListener((v, hasFocus) -> {

            changeDrawableLeftTint(editTxtGharardad , hasFocus);
            if (hasFocus)
            {
                if (moshtaryGharardadModels.size() > 0) {
                    openMoshtaryGharardadSpinner();
                } else {
                    showToast(R.string.firstSelectedMoshtary, Constants.INFO_MESSAGE(),Constants.DURATION_LONG());
                }
            }
        });

        searchKala();

    }


    @Override
    public void onGetMoshtary(ArrayList<MoshtaryModel> moshtaryModels, ArrayList<String> title) {
        moshtarytitle = title;
        this.moshtaryModels = moshtaryModels;
    }

    @Override
    public void onGetMoshtaryGharardad(ArrayList<MoshtaryGharardadModel> moshtaryGharardadModels, ArrayList<String> title) {
        gharardadTitle = title;
        this.moshtaryGharardadModels = moshtaryGharardadModels;
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onGetKala(ArrayList<RptMoshtaryGharardadUiModel> models) {
        rptMoshtaryGharardadUiModels.clear();
        rptMoshtaryGharardadUiModels.addAll(models);
        rptSearchModel.addAll(models);
        linTopLevel.setVisibility(View.VISIBLE);
        setRecycler();
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(RptMoshtaryGharardad.this, getResources().getString(resId), messageType, duration);
    }

    @Override
    public void onDetailsMoshtary(String nameMoshtary, String codeMoshtary, String shomarehGharardad, String nameSazmanForosh) {
        lblCustomerFullNameCode.setText(nameMoshtary + " - " + codeMoshtary);
        lblCustomerShomarehGharardad.setText(getResources().getString(R.string.shomareGharardad) + " : " + shomarehGharardad);
        lblCustomerSazmanForosh.setText(nameSazmanForosh);
        layTitleCustomer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishActivity() {
        finish();
    }

    private void changeDrawableLeftTint(EditText editText , boolean hasFocus)
    {
        if (hasFocus)
        {
            try
            {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_arrow_down);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorTextPrimary));
                editText.setCompoundDrawablesWithIntrinsicBounds(wrappedDrawable , null , editText.getCompoundDrawables()[2] , null);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
        else
        {
            try
            {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.ic_arrow_down);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable, getResources().getColor(R.color.colorTextPrimary));
                editText.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_arrow_down) , null , editText.getCompoundDrawables()[2] , null);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    private void openMoshtarySpinner()
    {
        customSpinner.showSpinner(this,moshtarytitle , new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                gharardadTitle.clear();
                editTxtGharardad.setText(null);
                moshtaryGharardadModels.clear();
                recyclerView.setAdapter(null);
                linTopLevel.setVisibility(View.GONE);
                layTitleCustomer.setVisibility(View.GONE);
                searchKalaName.getText().clear();
                editTxtMoshtary.setText(moshtarytitle.get(selectedIndex));
                selectedCcMoshtaryParent = moshtaryModels.get(selectedIndex).getccMoshtaryParent();
                selectedNameMoshtary = moshtaryModels.get(selectedIndex).getNameMoshtary();
                selectedCodeMoshtary = moshtaryModels.get(selectedIndex).getCodeMoshtary();
                mPresenter.getGharardadMoshtary(selectedCcMoshtaryParent);

            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    private void openMoshtaryGharardadSpinner()
    {
        customSpinner.showSpinner(this,gharardadTitle , new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                editTxtGharardad.setText(gharardadTitle.get(selectedIndex));
                selectedSazmanForosh = moshtaryGharardadModels.get(selectedIndex).getNameSazmanForosh();
                selectedCcSazmanForosh = String.valueOf(moshtaryGharardadModels.get(selectedIndex).getCcSazmanForosh());
                selectedShomarehGharardad = moshtaryGharardadModels.get(selectedIndex).getShomarehGharardad();
                selectedCcMoshtaryGharardad = String.valueOf(moshtaryGharardadModels.get(selectedIndex).getCcMoshtaryGharardad());
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    private void setRecycler() {
        adapter = new RptMoshtaryGharardadAdapter(BaseApplication.getContext(),rptMoshtaryGharardadUiModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(BaseApplication.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(BaseApplication.getContext(), 0));
        recyclerView.setAdapter(adapter);

    }

    private void searchKala(){
        searchKalaName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                adapter.notifyDataSetChanged();

                if (s.length() != 0) {
                    String searchWord = new PubFunc().new LanguageUtil().convertFaNumberToEN(s.toString());
                    rptMoshtaryGharardadUiModels.clear();
                    adapter.notifyDataSetChanged();

                    for (int i = 0; i < rptSearchModel.size(); i++) {
                        String nameKala = new PubFunc().new LanguageUtil().convertFaNumberToEN(rptSearchModel.get(i).getNameKala() + rptSearchModel.get(i).getCodeKala());
                        if (nameKala.contains(searchWord)) {
                            rptMoshtaryGharardadUiModels.add(rptSearchModel.get(i));
                            adapter.notifyDataSetChanged();
                        }
                    }
                } else
                {
                    rptMoshtaryGharardadUiModels.clear();
                    rptMoshtaryGharardadUiModels.addAll(rptSearchModel);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
