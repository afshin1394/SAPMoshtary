package com.saphamrah.MVP.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.SuggestAdapter;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.BaseMVP.NazaratAndPishnahadMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.MVP.Presenter.NazaratAndPishnahadPresenter;
import com.saphamrah.Model.NoePishnahadModel;
import com.saphamrah.Model.SuggestModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.CustomSpinnerResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.anwarshahriar.calligrapher.Calligrapher;


public class NazaratAndPishnahadActivity extends AppCompatActivity implements NazaratAndPishnahadMVP.RequiredViewOps {


    @BindView(R.id.lblActivityTitle)
    TextView lblActivityTitle;
    @BindView(R.id.edtNoePishnahad)
    EditText edtNoePishnahad;
    @BindView(R.id.layEdtNoePishnahad)
    CustomTextInputLayout layEdtNoePishnahad;
    @BindView(R.id.edtDescription)
    EditText edtDescription;
    @BindView(R.id.edtDescriptionPishnehad)
    EditText edtDescriptionPishnehad;
    @BindView(R.id.layEdtDescription)
    CustomTextInputLayout layEdtDescription;
    @BindView(R.id.layEdtDescriptionPishnehad)
    CustomTextInputLayout layEdtDescriptionPishnehad;
    @BindView(R.id.lay_details_nazarat)
    ConstraintLayout layDetailsNazarat;
    @BindView(R.id.layButtons)
    LinearLayout layButtons;
    @BindView(R.id.fabMenu)
    FloatingActionMenu fabMenu;
    @BindView(R.id.recycler_nazarat)
    RecyclerView recycler_nazarat;

    private CustomSpinner customSpinner = new CustomSpinner();
    private NazaratAndPishnahadPresenter mPresenter;
    private ArrayList<String> noePishnahadTitles = new ArrayList<>();
    private ArrayList<NoePishnahadModel> noePishnahadModels = new ArrayList<>();
    private CustomAlertDialog customAlertDialog;
    private SuggestAdapter suggestAdapter;
    private ArrayList<SuggestModel> suggestModels = new ArrayList<>();
    private int noePishnahad = 1;
    private int ccMoshtary = 0;
    private int ccNoePishnahad = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nazarat_pishnahadat);
        ButterKnife.bind(this);
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);
        mPresenter = new NazaratAndPishnahadPresenter(this);
        customAlertDialog = new CustomAlertDialog(NazaratAndPishnahadActivity.this);

        lblActivityTitle.setText(this.getResources().getString(R.string.pishnahadForoshandeh));
        Intent getIntent = getIntent();
        if (getIntent != null){
            ccMoshtary = getIntent.getIntExtra("ccMoshtary" , 0);
            if (ccMoshtary !=0){
                noePishnahad = 2;
                lblActivityTitle.setText(this.getResources().getString(R.string.pishnahadMoshtary));
                layEdtDescriptionPishnehad.setVisibility(View.GONE);
            }

        }


        mPresenter.getNoePishnahad(noePishnahad);



        edtNoePishnahad.setOnClickListener(v ->
                openNoePishnahadatSpinner());

        edtNoePishnahad.setOnFocusChangeListener((v, hasFocus) -> {
            changeDrawableLeftTint(edtNoePishnahad , hasFocus);
            if (hasFocus)
            {
                openNoePishnahadatSpinner();
            }
        });



    }

    /**
     * onclick Listener
     */
    @OnClick(R.id.fabAdd)
    public void fabAdd() {
        fabMenu.close(true);
        fabMenu.setVisibility(View.GONE);
        recycler_nazarat.setVisibility(View.GONE);
        layButtons.setVisibility(View.VISIBLE);
        layDetailsNazarat.setVisibility(View.VISIBLE);
    }
    @OnClick(R.id.btnCancel)
    public void btnCancel() {
        clearItems();
        fabMenu.setVisibility(View.VISIBLE);
        layButtons.setVisibility(View.GONE);
        recycler_nazarat.setVisibility(View.VISIBLE);
        layDetailsNazarat.setVisibility(View.GONE);
    }
    @OnClick(R.id.btnApply)
    public void btnApply() {
        clearItems();
        checkData();
    }

    private void checkData()
    {
        layEdtNoePishnahad.setError(null);
        layEdtDescription.setError(null);

        if (edtNoePishnahad.getText().toString().trim().equals("")){
            layEdtNoePishnahad.setError(getResources().getString(R.string.errorNoePishnahad));
            return;
        }

        if (edtDescription.getText().toString().trim().equals("")){
            layEdtDescription.setError(getResources().getString(R.string.errorDescription));

        } else {
            fabMenu.setVisibility(View.VISIBLE);
            layButtons.setVisibility(View.GONE);
            layDetailsNazarat.setVisibility(View.GONE);
            mPresenter.insertPishnahad(ccNoePishnahad,edtDescription.getText().toString().trim(), edtDescriptionPishnehad.getText().toString().trim(),ccMoshtary);
            edtDescription.setText("");
        }
    }


    private void clearItems(){
        edtNoePishnahad.setText("");
        edtDescription.setText("");
        edtDescriptionPishnehad.setText("");
    }

    /**
     * response presenter
     */
    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(NazaratAndPishnahadActivity.this, getResources().getString(resId), messageType, duration);

    }

    @Override
    public void showAlertMessage(int resId, int messageType) {

    }

    @Override
    public void onGetNoePishnahadat(ArrayList<NoePishnahadModel> noePishnahadModels,ArrayList<String> noePishnahadTitles ) {
        this.noePishnahadTitles = noePishnahadTitles;
        this.noePishnahadModels = noePishnahadModels;
        mPresenter.getSuggest(ccMoshtary);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onGetSuggest(ArrayList<SuggestModel> suggestModels) {
        recycler_nazarat.setVisibility(View.VISIBLE);
        this.suggestModels.clear();
        this.suggestModels = suggestModels;
        setupRecycler();
    }


    /**
     * setup recycler
     */
    private void setupRecycler(){
        recycler_nazarat.setLayoutManager(new LinearLayoutManager(BaseApplication.getContext(),LinearLayoutManager.VERTICAL,false));
        recycler_nazarat.setItemAnimator(new DefaultItemAnimator());
        suggestAdapter = new SuggestAdapter(BaseApplication.getContext(), suggestModels, true, (operation, position) -> {

            if (operation == Constants.DELETE()){
               customAlertDialog.showLogMessageAlert(NazaratAndPishnahadActivity.this, false, "", getResources().getString(R.string.deleteWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.cancel), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
                   @Override
                   public void setOnCancelClick() {

                   }

                   @Override
                   public void setOnApplyClick() {
                       if (suggestModels.get(position).getExtraProp_IsSend() == 0){
                           mPresenter.deleteSuggest(suggestModels.get(position).getCcSuggest(),suggestModels.get(position).getCcMoshtary());
                       } else {
                           showToast(R.string.canNotDelete,Constants.FAILED_MESSAGE(),Constants.DURATION_LONG());
                       }
                   }
               });
        }});
        recycler_nazarat.setAdapter(suggestAdapter);


    }

    /**
     * setting dropDown
     */
    private void changeDrawableLeftTint(EditText editText , boolean hasFocus)
    {
        if (hasFocus)
        {
            try
            {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(NazaratAndPishnahadActivity.this , R.drawable.ic_arrow_down);
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
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(NazaratAndPishnahadActivity.this , R.drawable.ic_arrow_down);
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

    private void openNoePishnahadatSpinner()
    {

        customSpinner.showSpinner(NazaratAndPishnahadActivity.this, noePishnahadTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex) {
                layEdtNoePishnahad.setError(null);
                ccNoePishnahad = noePishnahadModels.get(selectedIndex).getCcNoePishnahad();
                edtNoePishnahad.setText(noePishnahadModels.get(selectedIndex).getNameNoePishnahad());
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

}
