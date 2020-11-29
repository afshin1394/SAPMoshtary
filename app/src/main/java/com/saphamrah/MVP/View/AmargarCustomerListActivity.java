package com.saphamrah.MVP.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.Adapter.ListMoshtarianAdapter;
import com.saphamrah.BaseMVP.AmargarCustomerListMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.MVP.Presenter.AmargarCustomerListPresenter;
import com.saphamrah.Model.AmargarMarkazSazmanForoshModel;
import com.saphamrah.Model.ElatAdamMoarefiMoshtaryModel;
import com.saphamrah.Model.ForoshandehModel;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MasirModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;
import com.saphamrah.Valhalla.Location;

import java.util.ArrayList;
import java.util.List;

import me.anwarshahriar.calligrapher.Calligrapher;

public class AmargarCustomerListActivity extends AppCompatActivity implements AmargarCustomerListMVP.RequiredViewOps
{

    private RecyclerView recyclerView;
    private FloatingActionMenu fabMenu;
    private EditText edttxtMarkazForosh;
    private EditText edttxtSazmanForosh;
    private EditText edttxtForoshandeh;
    private EditText edttxtMasir;
    private BottomSheetBehavior bottomSheetBehavior;

    private ListMoshtarianAdapter adapter;
    private CustomSpinner customSpinner;
    private CustomAlertDialog customAlertDialog;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , this);
    private AmargarCustomerListMVP.PresenterOps mPresenter;

    private List<AmargarMarkazSazmanForoshModel> markazModels;
    private List<String> markazTitles;
    private Integer selectedMarkazId;
    private Integer selectedSazmanId;
    private Integer selectedForoshandehId;
    private Integer selectedMasirId;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amargar_customer_list);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        ImageView imageViewBack = findViewById(R.id.imgBack);
        recyclerView = findViewById(R.id.recyclerView);
        fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabMasirForoshandeh = findViewById(R.id.fabMasirForoshandeh);
        FloatingActionButton fabLocation = findViewById(R.id.fabLocation);
        FloatingActionButton fabShowMap = findViewById(R.id.fabMap);
        edttxtMarkazForosh = findViewById(R.id.txtMarkazForosh);
        edttxtSazmanForosh = findViewById(R.id.txtSazmanForosh);
        edttxtForoshandeh = findViewById(R.id.txtForoshandeh);
        edttxtMasir = findViewById(R.id.txtMasir);
        Button btnApplyMasirFilter = findViewById(R.id.btnApply);

        LinearLayout lnrlayBottomsheet = findViewById(R.id.lnrlayRoot);
        bottomSheetBehavior = BottomSheetBehavior.from(lnrlayBottomsheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        customSpinner = new CustomSpinner();
        customAlertDialog = new CustomAlertDialog(this);
        customLoadingDialog = new CustomLoadingDialog();

        markazModels = new ArrayList<>();
        markazTitles = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        startMVPOps();

        mPresenter.getAllCustomers();
        mPresenter.getAmargarMarkazForosh();


        //MarkazForosh
        edttxtMarkazForosh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openMarkazForoshSpinner();
            }
        });
        edttxtMarkazForosh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtMarkazForosh , hasFocus);
                if (hasFocus)
                {
                    openMarkazForoshSpinner();
                }
            }
        });
        //MarkazForosh


        //SazmanForosh
        edttxtSazmanForosh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getAmargarSazmanForosh(selectedMarkazId);
            }
        });
        edttxtSazmanForosh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtSazmanForosh , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getAmargarSazmanForosh(selectedMarkazId);
                }
            }
        });
        //SazmanForosh

        //Foroshandeh
        edttxtForoshandeh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getForoshandeh(selectedSazmanId);
            }
        });
        edttxtForoshandeh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtForoshandeh , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getForoshandeh(selectedSazmanId);
                }
            }
        });
        //Foroshandeh



        //Masir
        edttxtMasir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getMasir(selectedForoshandehId);
            }
        });
        edttxtMasir.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtMasir , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getMasir(selectedForoshandehId);
                }
            }
        });
        //Masir

        fabMasirForoshandeh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });


        btnApplyMasirFilter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.getListMoshtarian(selectedMasirId);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });


        fabLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.getRadiusConfig();
            }
        });


        fabShowMap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(AmargarCustomerListActivity.this, AmargarCustomersMapActivity.class));
            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AmargarCustomerListActivity.this.finish();
            }
        });


    }

    @Override
    public Context getAppContext()
    {
        return this;
    }

    private void openMarkazForoshSpinner()
    {
        customSpinner.showSpinner(this, markazTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtMarkazForosh.setText(markazTitles.get(selectedIndex));
                selectedMarkazId = markazModels.get(selectedIndex).getCcMarkazForosh();
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void onGetMarkazForosh(final List<AmargarMarkazSazmanForoshModel> markazModels, final List<String> titles)
    {
        markazTitles = titles;
        this.markazModels = markazModels;
    }

    @Override
    public void onGetSazmanForosh(final List<AmargarMarkazSazmanForoshModel> markazModels, final List<String> titles)
    {
        customSpinner.showSpinner(this, titles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtSazmanForosh.setText(titles.get(selectedIndex));
                selectedSazmanId = markazModels.get(selectedIndex).getCcSazmanForosh();
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void onGetForoshandeh(final List<ForoshandehModel> foroshandehModels, final List<String> titles)
    {
        customSpinner.showSpinner(this, titles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtForoshandeh.setText(titles.get(selectedIndex));
                selectedForoshandehId = foroshandehModels.get(selectedIndex).getCcForoshandeh();
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void onGetMasir(final List<MasirModel> masirModels, final List<String> titles)
    {
        customSpinner.showSpinner(this, titles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtMasir.setText(titles.get(selectedIndex));
                selectedMasirId = masirModels.get(selectedIndex).getCcMasir();
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void showListMoshtarian(final List<ListMoshtarianModel> arrayListData)
    {
        adapter = new ListMoshtarianAdapter(this, arrayListData, new ListMoshtarianAdapter.onItemClickListener()
        {
            @Override
            public void onItemClick(int operation, int position)
            {
                adapter.closeItem(position);
                onListClickListener(operation, arrayListData.get(position));
            }
        });
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void showListMoshtarianByLocation(final ArrayList<ListMoshtarianModel> arrayListData)
    {
        adapter = new ListMoshtarianAdapter(this, arrayListData, new ListMoshtarianAdapter.onItemClickListener()
        {
            @Override
            public void onItemClick(int operation, int position)
            {
                adapter.closeItem(position);
                onListClickListener(operation, arrayListData.get(position));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void onListClickListener(int operation, ListMoshtarianModel model)
    {
        switch (operation)
        {
            case ListMoshtarianAdapter.SEND:
                mPresenter.checkForSendPorseshname(model.getCcMoshtary());
                break;
            case ListMoshtarianAdapter.DELETE:
                break;
            case ListMoshtarianAdapter.SHOW_LOCATION:
                mPresenter.checkCustomerLocation(model.getNameMoshtary(), model.getLatitudeY(), model.getLongitudeX());
                break;
            case ListMoshtarianAdapter.PORSESHNAME:
                PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider(AmargarCustomerListActivity.this);
                Location currentLocation = new Location(locationProvider.getLatitude(), locationProvider.getLongitude());
                mPresenter.checkForAddPorseshname(currentLocation, model);
                break;
            case ListMoshtarianAdapter.ADAM_FAAL:
                mPresenter.getElatAdamMoarefiMoshtary(model.getCcMoshtary());
                break;
        }
    }

    @Override
    public void onGetRadiusConfig(final ArrayList<String> arrayListRadiusItems)
    {
        final PubFunc.LocationProvider locationProvider = new PubFunc().new LocationProvider(this);
        List<String> titles = new ArrayList<>();
        for (String item : arrayListRadiusItems)
        {
            titles.add(String.format("%1$s %2$s %3$s", getResources().getString(R.string.until), item, getResources().getString(R.string.meter)));
        }
        customSpinner.showSpinner(this, titles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                String selectedItem = arrayListRadiusItems.get(selectedIndex);
                //alertDialogLoading = customLoadingDialog.showLoadingDialog(AmargarCustomerListActivity.this);
                mPresenter.getCustomerListByLocation(selectedItem , String.valueOf(locationProvider.getLatitude()), String.valueOf(locationProvider.getLongitude()));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void showCustomerLocation(double latitude, double longitude)
    {
        Intent intent = new Intent(this , AddCustomerMapActivity.class);
        intent.putExtra(AddCustomerMapActivity.CUSTOMER_lAT_KEY , latitude);
        intent.putExtra(AddCustomerMapActivity.CUSTOMER_lng_KEY , longitude);
        startActivity(intent);
        overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
    }

    @Override
    public void showElatAdamMoarefiMoshtary(final ArrayList<ElatAdamMoarefiMoshtaryModel> elatAdamMoarefiMoshtaryModels, List<String> titles, final int ccMoshtary)
    {
        customSpinner.showSpinner(this, titles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                int selectedccElat = elatAdamMoarefiMoshtaryModels.get(selectedIndex).getCcElatAdamMoarefiMoshtary();
                mPresenter.checkElatAdamMoarefi(selectedccElat, ccMoshtary);
                //mPresenter.getCustomerListByLocation(selectedItem , String.valueOf(locationProvider.getLatitude()), String.valueOf(locationProvider.getLongitude()));
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void showAlertGetCodeMoshtaryTekrari(final int selectedccElat, final int ccMoshtary)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View myview = getLayoutInflater().inflate(R.layout.alert_duplicated_customer_code , null);
        final CustomTextInputLayout txtinputCode = myview.findViewById(R.id.txtinputLay);
        final EditText edttxt = myview.findViewById(R.id.txt);
        Button btnOK = myview.findViewById(R.id.btnApply);
        Typeface font = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.fontPath));
        txtinputCode.setTypeface(font);
        edttxt.setTypeface(font);
        btnOK.setTypeface(font);
        builder.setCancelable(true);
        builder.setView(myview);
        builder.create();
        if (!(AmargarCustomerListActivity.this).isFinishing())
        {
            final AlertDialog show = builder.show();
            try
            {
                if (show.getWindow() != null)
                {
                    show.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(this,Constants.LOG_EXCEPTION(), exception.toString(), "", TAG, "showAlertGetCodeMoshtaryTekrari", "");
            }

            btnOK.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    txtinputCode.setError(null);
                    if (edttxt.getText().toString().length() > 0)
                    {
                        mPresenter.insertAdamFaal(ccMoshtary, selectedccElat, edttxt.getText().toString());
                        show.dismiss();
                    }
                    else
                    {
                        txtinputCode.setError(getResources().getString(R.string.errorCustomerDuplicatedCode));
                    }
                }
            });
        }
    }

    @Override
    public void openAddPorseshnameActivity(int ccMoshtary, String codeMoshtary)
    {
        Intent intent = new Intent(this, AddPorseshnameInfoActivity.class);
        intent.putExtra(AddPorseshnameInfoActivity.CC_MOSHTARY_KEY, ccMoshtary);
        intent.putExtra(AddPorseshnameInfoActivity.CODE_MOSHTARY_KEY, codeMoshtary);
        intent.putExtra(AddPorseshnameInfoActivity.CC_MASIR_KEY, selectedMasirId);
        intent.putExtra(AddPorseshnameInfoActivity.CC_PORSESHNAMEH_KEY, -1);
        startActivity(intent);
    }

    @Override
    public void showAlertLoading()
    {
        alertDialogLoading = customLoadingDialog.showLoadingDialog(this);
    }

    @Override
    public void closeAlertLoading()
    {
        if (alertDialogLoading != null)
        {
            try
            {
                alertDialogLoading.dismiss();
                alertDialogLoading = null;
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void showErrorGetMarkaz()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorGetMarkaz), Constants.INFO_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorSelectSazman()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorSelectSazman), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorSelectMarkaz()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorSelectMarkaz), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorNotFoundForoshandeh()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorFindForoshandeh), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorSelectForoshandeh()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorSelectForoshandehMamorPakhsh), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorNotFoundMasir()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorNotFoundMasir), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorSelectMasir()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorSelectMasir), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorGetListMoshtarian()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorGetCustomerList), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showNotFoundMoshtary()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorNotFoundMoshtary), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorGetRadiusConfig()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorNotFoundMoshtary), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorSelectRadius()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorRadius), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorWrongLocation()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorGetLocation), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorWrongCustomerLocation(String customerName)
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorGetCustomerLocation, customerName), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorSelectCustomer()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorSelectCustomer), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorSendPorseshname()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorSendPorseshname), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorGetPorseshname()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorGetPorseshnameInfo), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorGetElatAdamMoarefiMoshtary()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorGetElatAdamMoarefiMoshtary), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showSuccessInsertAdamFaal()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.successSaveData), Constants.SUCCESS_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorInsertAdamFaal()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorSaveData), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorDistanceForAddPorseshname()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorLocationForPorseshname), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorSendAllData()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorFirstSendAdamAndPorseshname), Constants.FAILED_MESSAGE(), getString(R.string.apply));
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

    public void startMVPOps()
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
            mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "startMVPOps", "");
        }
    }

    private void initialize(AmargarCustomerListMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new AmargarCustomerListPresenter(view);
            stateMaintainer.put(AmargarCustomerListMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "initialize", "");
        }
    }

    private void reinitialize(AmargarCustomerListMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(AmargarCustomerListMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "reinitialize", "");
            }
        }
    }


}
