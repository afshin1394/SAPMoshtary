package com.saphamrah.MVP.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.saphamrah.Adapter.AddCustomerShomareHesabAdapter;
import com.saphamrah.BaseMVP.AddCustomerShomareHesabMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.MVP.Presenter.AddCustomerShomareHesabPresenter;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.BankModel;
import com.saphamrah.Model.MoshtaryShomarehHesabModel;
import com.saphamrah.Model.NoeHesabModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;


public class AddCustomerShomareHesabFragment extends Fragment implements AddCustomerShomareHesabMVP.RequiredViewOps
{

    private ViewSwitcher viewSwitcher;
    private RecyclerView recyclerView;
    private AddCustomerShomareHesabAdapter adapter;
    private com.github.clans.fab.FloatingActionButton fabAddNew;
    private com.github.clans.fab.FloatingActionMenu fabMenu;
    private TextInputLayout txtinputBank;
    private TextInputLayout txtinputNoeHesab;
    private TextInputLayout txtinputShartBardasht;
    private TextInputLayout txtinputNameShobe;
    private TextInputLayout txtinputCodeShobe;
    private TextInputLayout txtinputShomareHesab;
    private TextInputLayout txtinputSahebHesab;
    private EditText edttxtBank;
    private EditText edttxtNoeHesab;
    private EditText edttxtShartBardasht;
    private EditText edttxtNameShobe;
    private EditText edttxtCodeShobe;
    private EditText edttxtShomareHesab;
    private EditText edttxtSahebHesab;
    private Button btnCancel;
    private Button btnApply;

    private int bankId;
    private String bankName = "";
    private int noeHesabId;
    private String noeHesabName = "";
    private int shartBardashtId;
    private String shartBardashtName = "";

    private Context context;
    private AddCustomerInfoModel addCustomerInfoModel;
    private ArrayList<MoshtaryShomarehHesabModel> moshtaryShomarehHesabModels = new ArrayList<>();

    AddCustomerShomareHesabMVP.PresenterOps mPresenter;
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;


    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.add_customer_shomarehesab_fragment , container , false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Calligrapher calligrapher = new Calligrapher(context);
        calligrapher.setFont(view , context.getResources().getString(R.string.fontPath));

        bindViews(view);

        stateMaintainer = new StateMaintainer(getFragmentManager() , TAG , context);
        startMVPOps();

        mPresenter.getAddCustomerInfo();

        adapter = new AddCustomerShomareHesabAdapter(context, moshtaryShomarehHesabModels, 1, new AddCustomerShomareHesabAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MoshtaryShomarehHesabModel moshtaryShomarehHesabModel, int position)
            {
                mPresenter.deleteShomareHesab(position);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context , 0));
        recyclerView.setAdapter(adapter);

        fabAddNew.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (viewSwitcher.getCurrentView() == view.findViewById(R.id.relativelayList))
                {
                    fabMenu.close(true);
                    viewSwitcher.showNext();
                }
            }
        });

        edttxtBank.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                {
                    mPresenter.getBanksItems();
                }
            }
        });

        edttxtBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getBanksItems();
            }
        });


        edttxtNoeHesab.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                {
                    mPresenter.getNoeHesabItems();
                }
            }
        });

        edttxtNoeHesab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getNoeHesabItems();
            }
        });


        edttxtShartBardasht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getShartBardashtItems();
            }
        });

        edttxtShartBardasht.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    mPresenter.getShartBardashtItems();
                }
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clearData();
                viewSwitcher.showPrevious();
            }
        });


        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
    }


    private void bindViews(View view)
    {
        viewSwitcher = (ViewSwitcher)view.findViewById(R.id.viewSwitcher);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        fabAddNew = (FloatingActionButton) view.findViewById(R.id.fabAddShomareHesab);
        fabMenu = (FloatingActionMenu)view.findViewById(R.id.fabMenu);
        txtinputBank = (TextInputLayout)view.findViewById(R.id.txtinputBank);
        txtinputNoeHesab = (TextInputLayout)view.findViewById(R.id.txtinputNoeHesab);
        txtinputShartBardasht = (TextInputLayout)view.findViewById(R.id.txtinputShartBardasht);
        txtinputNameShobe = (TextInputLayout)view.findViewById(R.id.txtinputNameShobe);
        txtinputCodeShobe = (TextInputLayout)view.findViewById(R.id.txtinputCodeShobe);
        txtinputShomareHesab = (TextInputLayout)view.findViewById(R.id.txtinputShomareHesab);
        txtinputSahebHesab = (TextInputLayout)view.findViewById(R.id.txtinputSahebHesab);
        edttxtBank = (EditText) view.findViewById(R.id.txtBank);
        edttxtNoeHesab = (EditText)view.findViewById(R.id.txtNoeHesab);
        edttxtShartBardasht = (EditText)view.findViewById(R.id.txtShartBardasht);
        edttxtNameShobe = (EditText)view.findViewById(R.id.txtNameShobe);
        edttxtCodeShobe = (EditText)view.findViewById(R.id.txtCodeShobe);
        edttxtShomareHesab = (EditText)view.findViewById(R.id.txtShomareHesab);
        edttxtSahebHesab = (EditText)view.findViewById(R.id.txtSahebHesab);
        btnApply = (Button) view.findViewById(R.id.btnApply);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);

        Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
        txtinputBank.setTypeface(font);
        txtinputNoeHesab.setTypeface(font);
        txtinputShartBardasht.setTypeface(font);
        txtinputNameShobe.setTypeface(font);
        txtinputCodeShobe.setTypeface(font);
        txtinputShomareHesab.setTypeface(font);
        txtinputSahebHesab.setTypeface(font);
        edttxtBank.setTypeface(font);
        edttxtNoeHesab.setTypeface(font);
        edttxtShartBardasht.setTypeface(font);
        edttxtNameShobe.setTypeface(font);
        edttxtCodeShobe.setTypeface(font);
        edttxtShomareHesab.setTypeface(font);
        edttxtSahebHesab.setTypeface(font);
        btnApply.setTypeface(font);
        btnCancel.setTypeface(font);
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putParcelable("addCustomerInfoModel" , addCustomerInfoModel);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
        {
            addCustomerInfoModel = savedInstanceState.getParcelable("addCustomerInfoModel");
            if (addCustomerInfoModel != null)
            {
                moshtaryShomarehHesabModels = addCustomerInfoModel.getMoshtaryShomarehHesabModels();
                Gson json = new Gson();
                Log.d("fragment" , "restored shomare hesab : " + json.toJson(addCustomerInfoModel));
                adapter = new AddCustomerShomareHesabAdapter(context, moshtaryShomarehHesabModels, 1, new AddCustomerShomareHesabAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(MoshtaryShomarehHesabModel moshtaryShomarehHesabModel, int position)
                    {
                        mPresenter.deleteShomareHesab(position);
                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(context , 0));
                recyclerView.setAdapter(adapter);
            }
        }
    }


    private void checkData()
    {
        txtinputBank.setError(null);
        txtinputNoeHesab.setError(null);
        txtinputShartBardasht.setError(null);
        txtinputNameShobe.setError(null);
        txtinputCodeShobe.setError(null);
        txtinputShomareHesab.setError(null);
        txtinputSahebHesab.setError(null);

        MoshtaryShomarehHesabModel moshtaryShomarehHesabModel = new MoshtaryShomarehHesabModel();
        moshtaryShomarehHesabModel.setCcBank(bankId);
        moshtaryShomarehHesabModel.setNameBank(bankName);
        moshtaryShomarehHesabModel.setCcNoeHesab(noeHesabId);
        moshtaryShomarehHesabModel.setNameNoeHesab(noeHesabName);
        moshtaryShomarehHesabModel.setShartBardashtAzHesab(shartBardashtId);
        moshtaryShomarehHesabModel.setCodeShobeh(edttxtCodeShobe.getText().toString().trim());
        moshtaryShomarehHesabModel.setNameShobeh(edttxtNameShobe.getText().toString().trim());
        moshtaryShomarehHesabModel.setShomarehHesab(edttxtShomareHesab.getText().toString().trim());
        moshtaryShomarehHesabModel.setSahebanHesab(edttxtSahebHesab.getText().toString().trim());

        String customerNameFamily = addCustomerInfoModel.getFirstName() + " " + addCustomerInfoModel.getLastName();
        mPresenter.checkNewShomareHesab(moshtaryShomarehHesabModel ,  customerNameFamily , moshtaryShomarehHesabModels);
    }

    private void clearData()
    {
        edttxtBank.setText("");
        edttxtNoeHesab.setText("");
        edttxtShartBardasht.setText("");
        edttxtNameShobe.setText("");
        edttxtCodeShobe.setText("");
        edttxtShomareHesab.setText("");
        edttxtSahebHesab.setText("");
        bankId = 0;
        bankName = "";
        noeHesabId = 0;
        noeHesabName = "";
        shartBardashtId = 0;
        shartBardashtName = "";
    }


    @Override
    public Context getAppContext()
    {
        return context;
    }

    @Override
    public void onGetBanksItems(final ArrayList<BankModel> bankModels)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        final ArrayList<String> bankNames = new ArrayList<>();
        for (BankModel bankModel : bankModels)
        {
            bankNames.add(bankModel.getNameBank());
        }
        customSpinner.showSpinner(getActivity(), bankNames, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtBank.setText(bankNames.get(selectedIndex));
                bankId = bankModels.get(selectedIndex).getCcBank();
                bankName = bankModels.get(selectedIndex).getNameBank();
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetNoeHesabItems(final ArrayList<NoeHesabModel> noeHesabModels)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        final ArrayList<String> noeHesabTitles = new ArrayList<>();
        for (NoeHesabModel noeHesabModel : noeHesabModels)
        {
            noeHesabTitles.add(noeHesabModel.getNameNoeHesab());
        }
        customSpinner.showSpinner(getActivity(), noeHesabTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtNoeHesab.setText(noeHesabTitles.get(selectedIndex));
                noeHesabId = noeHesabModels.get(selectedIndex).getCcNoeHesab();
                noeHesabName = noeHesabModels.get(selectedIndex).getNameNoeHesab();
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetShartBardashtItems(final ArrayList<Integer> itemIds , final ArrayList<String> itemTitles)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(getActivity(), itemTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtShartBardasht.setText(itemTitles.get(selectedIndex));
                shartBardashtId = itemIds.get(selectedIndex);
                shartBardashtName = itemTitles.get(selectedIndex);
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onSuccessGetAddCustomerInfo(AddCustomerInfoModel addCustomerInfoModel)
    {
        if (addCustomerInfoModel == null)
        {
            this.addCustomerInfoModel = new AddCustomerInfoModel();
        }
        else
        {
            this.addCustomerInfoModel = addCustomerInfoModel;
        }
    }

    @Override
    public void onSuccessSaveNewSomareHesab(MoshtaryShomarehHesabModel moshtaryShomarehHesabModel)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity) context);
        moshtaryShomarehHesabModels.add(moshtaryShomarehHesabModel);
        addCustomerInfoModel.setMoshtaryShomarehHesabModels(moshtaryShomarehHesabModels);
        adapter.notifyDataSetChanged();
        customAlertDialog.showToast((Activity)context, getResources().getString(R.string.shomareHesabAdded), Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        clearData();
        viewSwitcher.showPrevious();
    }

    @Override
    public void onErrorBank()
    {
        txtinputBank.setError(getResources().getString(R.string.errorBank));
    }

    @Override
    public void onErrorNoeHesab()
    {
        txtinputNoeHesab.setError(getResources().getString(R.string.errorNoeHesab));
    }

    @Override
    public void onErrorShartBardasht()
    {
        txtinputShartBardasht.setError(getResources().getString(R.string.errorShartBardasht));
    }

    @Override
    public void onErrorNameShobe()
    {
        txtinputNameShobe.setError(getResources().getString(R.string.errorNameShobe));
    }

    @Override
    public void onErrorCodeShobe()
    {
        txtinputCodeShobe.setError(getResources().getString(R.string.errorCodeShobe));
    }

    @Override
    public void onErrorShomareHesab()
    {
        txtinputShomareHesab.setError(getResources().getString(R.string.errorShomareHesab));
    }

    @Override
    public void onErrorDuplicateShomareHesab()
    {
        txtinputShomareHesab.setError(getResources().getString(R.string.errorDuplicateShomareHesab));
    }

    @Override
    public void onErrorSahebHesab()
    {
        txtinputSahebHesab.setError(getResources().getString(R.string.errorSahebHesab));
    }

    @Override
    public void onErrorWrongNameSahebHesab()
    {
        txtinputSahebHesab.setError(getResources().getString(R.string.errorWrongNameSahebHesab));
    }

    @Override
    public void onSuccessDeleteShomareHesab(int position)
    {
        moshtaryShomarehHesabModels.remove(position);
        addCustomerInfoModel.setMoshtaryShomarehHesabModels(moshtaryShomarehHesabModels);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity)context);
        customAlertDialog.showMessageAlert((Activity)context, closeActivity, context.getResources().getString(titleResId), context.getResources().getString(messageResId), messageType, ((Activity) context).getResources().getString(buttonTextResId));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity)context);
        customAlertDialog.showToast((Activity)context, context.getResources().getString(resId), messageType, duration);
    }


    private void startMVPOps()
    {
        try
        {
            if ( stateMaintainer.firstTimeIn() )
            {
                initialize(this);
            }
            else
            {
                reinitialize(this);
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerShomareHesabFragment.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(AddCustomerShomareHesabMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new AddCustomerShomareHesabPresenter(view);
            stateMaintainer.put(AddCustomerShomareHesabMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerShomareHesabFragment.class.getSimpleName(), "initialize", "");
        }
    }


    private void reinitialize(AddCustomerShomareHesabMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(AddCustomerShomareHesabMVP.PresenterOps.class.getSimpleName());
            if ( mPresenter == null )
            {
                initialize( view );
            }
            else
            {
                mPresenter.onConfigurationChanged(view);
            }
        }
        catch (Exception exception)
        {
            if (mPresenter != null)
            {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerShomareHesabFragment.class.getSimpleName(), "reinitialize", "");
            }
        }
    }


}
