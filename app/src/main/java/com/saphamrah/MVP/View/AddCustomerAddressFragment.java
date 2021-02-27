package com.saphamrah.MVP.View;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.saphamrah.Adapter.AddCustomerAddressAdapter;
import com.saphamrah.BaseMVP.AddCustomerAddressMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.MVP.Presenter.AddCustomerAddressPresenter;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.MahalCodePostiModel;
import com.saphamrah.Model.MahalModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.PolygonForoshSatrModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polygon;

import java.util.ArrayList;


public class AddCustomerAddressFragment extends Fragment implements AddCustomerAddressMVP.RequiredViewOps
{

    private ViewSwitcher viewSwitcher;
    private RecyclerView recyclerView;
    private AddCustomerAddressAdapter adapter;
    private CustomTextInputLayout txtinputOstan;
    private CustomTextInputLayout txtinputShahrestan;
    private CustomTextInputLayout txtinputBakhsh;
    private CustomTextInputLayout txtinputShahr;
    private CustomTextInputLayout txtinputMantaghe;
    private CustomTextInputLayout txtinputNoeAddress;
    private CustomTextInputLayout txtinputTelephone;
    private CustomTextInputLayout txtinputCodePosti;
    private CustomTextInputLayout txtinputKhiabanAsli;
    private CustomTextInputLayout txtinputKhiabanFaree1;
    private CustomTextInputLayout txtinputKhiabanFaree2;
    private CustomTextInputLayout txtinputKoocheAsli;
    private CustomTextInputLayout txtinputKoocheFaree1;
    private CustomTextInputLayout txtinputPelak;
    //private CustomTextInputLayout txtinputAddress;

    private EditText edttxtOstan;
    private EditText edttxtShahrestan;
    private EditText edttxtBakhsh;
    private EditText edttxtShahr;
    private EditText edttxtMantaghe;
    private EditText edttxtNoeAddress;
    private EditText edttxtTelephoneCode;
    private EditText edttxtTelephone;
    private EditText edttxtCodePosti;
    private EditText edttxtKhiabanAsli;
    private EditText edttxtKhiabanFaree1;
    private EditText edttxtKhiabanFaree2;
    private EditText edttxtKoocheAsli;
    private EditText edttxtKoocheFaree1;
    private EditText edttxtPelak;
    //private EditText edttxtAddress;
    private Button btnCancel;
    private Button btnRegisterAddress;
    private MapView map;
    private Context context;

    private int ostanId;
    private String ostanName = "";
    private int shahrestanId;
    private String shahrestanName = "";
    private int bakhshId;
    private String bakhshName = "";
    private int shahrId;
    private String shahrName = "";
    private int mantagheId;
    private String mantagheName = "";
    private int noeAddressId;
    private String noeAddressName = "";
    private ArrayList<MoshtaryAddressModel> moshtaryAddressModels = new ArrayList<>();
    private AddCustomerInfoModel addCustomerInfoModel;
    private final int LOCATION_PERMISSION = 100;

    private AddCustomerAddressMVP.PresenterOps mPresenter;
    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer;

    private boolean requireTelephone;
    private boolean requireCodePosti;
    private ArrayList<MahalCodePostiModel> mahalCodePostiModelArrayList;


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {
            if (getView() != null)
            {
                mPresenter.getSellPolygon();
            }
        }
    }

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
        return inflater.inflate(R.layout.add_customer_address_fragment , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        viewSwitcher = view.findViewById(R.id.viewSwitcher);
        recyclerView = view.findViewById(R.id.recyclerView);

        FloatingActionButton fabAddAddress = view.findViewById(R.id.fabAddAddress);
        final FloatingActionMenu fabMenu = view.findViewById(R.id.fabMenu);
        btnCancel = view.findViewById(R.id.btnCancel);
        btnRegisterAddress = view.findViewById(R.id.btnApply);

        stateMaintainer = new StateMaintainer(getFragmentManager() , TAG , context);
        startMVPOps();

		mPresenter.getConfig();
        mPresenter.getAddCustomerInfo();

        adapter = new AddCustomerAddressAdapter(context, moshtaryAddressModels, 1, true, new AddCustomerAddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MoshtaryAddressModel moshtaryAddressModel, int position)
            {
                mPresenter.checkDeleteAddress(position);
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context , 0));
        recyclerView.setAdapter(adapter);

        bindEditTextInput(view);
        bindTextInputLayout(view);
        setTypeFace();
        prepareMap(view);

        //////////////////// OSTAN ////////////////////
        edttxtOstan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtOstan , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getOstanItems();
                }
                else
                {
                    //showAddress();
                }
            }
        });
        edttxtOstan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getOstanItems();
            }
        });
        //////////////////// OSTAN ////////////////////


        //////////////////// SHAHRESTAN ////////////////////
        edttxtShahrestan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtShahrestan , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getShahrestanItems(ostanId);
                }
                else
                {
                    //showAddress();
                }
            }
        });
        edttxtShahrestan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getShahrestanItems(ostanId);
            }
        });
        //////////////////// SHAHRESTAN ////////////////////


        //////////////////// BAKHSH ////////////////////
        edttxtBakhsh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getBakhshItems(shahrestanId);
            }
        });
        edttxtBakhsh.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtBakhsh , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getBakhshItems(shahrestanId);
                }
                else
                {
                    //showAddress();
                }
            }
        });
        //////////////////// BAKHSH ////////////////////



        //////////////////// SHAHR ////////////////////
        edttxtShahr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getShahrItems(bakhshId);
            }
        });
        edttxtShahr.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtShahr , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getShahrItems(bakhshId);
                }
                else
                {
                    //showAddress();
                }
            }
        });
        //////////////////// SHAHR ////////////////////


        //////////////////// MANTAGHE ////////////////////
        edttxtMantaghe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getMantagheItems(shahrId);
            }
        });
        edttxtMantaghe.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtMantaghe , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getMantagheItems(shahrId);
                }
                else
                {
                    //showAddress();
                }
            }
        });
        //////////////////// MANTAGHE ////////////////////


        //////////////////// NOE ADDRESS ////////////////////
        edttxtNoeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getNoeAddressItems();
            }
        });
        edttxtNoeAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                changeDrawableLeftTint(edttxtNoeAddress , hasFocus);
                if (hasFocus)
                {
                    mPresenter.getNoeAddressItems();
                }
                else
                {
                    //showAddress();
                }
            }
        });
        //////////////////// NOE ADDRESS ////////////////////


        edttxtKhiabanAsli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    //showAddress();
                }
            }
        });

        edttxtKhiabanFaree1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    //showAddress();
                }
            }
        });

        edttxtKhiabanFaree2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    //showAddress();
                }
            }
        });

        edttxtKoocheAsli.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    //showAddress();
                }
            }
        });

        edttxtKoocheFaree1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    //showAddress();
                }
            }
        });


        edttxtPelak.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus)
                {
                    //showAddress();
                }
            }
        });

        fabAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewSwitcher.showNext();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                clearEditTexts();
                viewSwitcher.showPrevious();
            }
        });

        btnRegisterAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                checkLocationPermission();
            }
        });

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
                moshtaryAddressModels = addCustomerInfoModel.getMoshtaryAddressModels();
                Gson json = new Gson();
                Log.d("fragment" , "restored : " + json.toJson(addCustomerInfoModel));
                adapter = new AddCustomerAddressAdapter(context, moshtaryAddressModels, 1, true, new AddCustomerAddressAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(MoshtaryAddressModel moshtaryAddressModel, int position)
                    {
                        moshtaryAddressModels.remove(position);
                        adapter.notifyDataSetChanged();
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

    private void bindTextInputLayout(View view)
    {
        txtinputOstan = (CustomTextInputLayout)view.findViewById(R.id.txtinputOstan);
        txtinputShahrestan = (CustomTextInputLayout)view.findViewById(R.id.txtinputShahrestan);
        txtinputBakhsh = (CustomTextInputLayout)view.findViewById(R.id.txtinputBakhsh);
        txtinputShahr = (CustomTextInputLayout)view.findViewById(R.id.txtinputShahr);
        txtinputMantaghe = (CustomTextInputLayout)view.findViewById(R.id.txtinputMantaghe);
        txtinputNoeAddress = (CustomTextInputLayout)view.findViewById(R.id.txtinputNoeAddress);
        txtinputTelephone = (CustomTextInputLayout)view.findViewById(R.id.txtinputTelephone);
        txtinputCodePosti = (CustomTextInputLayout)view.findViewById(R.id.txtinputCodePosti);
        txtinputKhiabanAsli = (CustomTextInputLayout)view.findViewById(R.id.txtinputKhiabanAsli);
        txtinputKhiabanFaree1 = (CustomTextInputLayout)view.findViewById(R.id.txtinputKhiabanFaree1);
        txtinputKhiabanFaree2 = (CustomTextInputLayout)view.findViewById(R.id.txtinputKhiabanFaree2);
        txtinputKoocheAsli = (CustomTextInputLayout)view.findViewById(R.id.txtinputKoocheAsli);
        txtinputKoocheFaree1 = (CustomTextInputLayout)view.findViewById(R.id.txtinputKoochefaree1);
        txtinputPelak = (CustomTextInputLayout)view.findViewById(R.id.txtinputPelak);
        //txtinputAddress = (CustomTextInputLayout)view.findViewById(R.id.txtinputAddress);
    }

    private void bindEditTextInput(View view)
    {
         edttxtOstan = (EditText) view.findViewById(R.id.txtOstan);
         edttxtShahrestan = (EditText)view.findViewById(R.id.txtShahrestan);
         edttxtBakhsh = (EditText)view.findViewById(R.id.txtBakhsh);
         edttxtShahr = (EditText)view.findViewById(R.id.txtShahr);
         edttxtMantaghe = (EditText)view.findViewById(R.id.txtMantaghe);
         edttxtNoeAddress = (EditText)view.findViewById(R.id.txtNoeAddress);
         edttxtTelephoneCode = (EditText)view.findViewById(R.id.txtTelephoneCode);
         edttxtTelephone = (EditText)view.findViewById(R.id.txtTelephone);
         edttxtCodePosti = (EditText)view.findViewById(R.id.txtCodePosti);
         edttxtKhiabanAsli = (EditText)view.findViewById(R.id.txtKhiabanAsli);
         edttxtKhiabanFaree1 = (EditText)view.findViewById(R.id.txtKhiabanFaree1);
         edttxtKhiabanFaree2 = (EditText)view.findViewById(R.id.txtKhiabanFaree2);
         edttxtKoocheAsli = (EditText)view.findViewById(R.id.txtKoocheAsli);
         edttxtKoocheFaree1 = (EditText)view.findViewById(R.id.txtKoochefaree1);
         edttxtPelak = (EditText)view.findViewById(R.id.txtPelak);
         //edttxtAddress = (EditText)view.findViewById(R.id.txtAddress);
    }

    private void setTypeFace()
    {
        Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));
        txtinputOstan.setTypeface(font);
        txtinputShahrestan.setTypeface(font);
        txtinputBakhsh.setTypeface(font);
        txtinputShahr.setTypeface(font);
        txtinputMantaghe.setTypeface(font);
        txtinputNoeAddress.setTypeface(font);
        txtinputTelephone.setTypeface(font);
        txtinputCodePosti.setTypeface(font);
        txtinputKhiabanAsli.setTypeface(font);
        txtinputKhiabanFaree1.setTypeface(font);
        txtinputKhiabanFaree2.setTypeface(font);
        txtinputKoocheAsli.setTypeface(font);
        txtinputKoocheFaree1.setTypeface(font);
        txtinputPelak.setTypeface(font);
        //txtinputAddress.setTypeface(font);

        edttxtOstan.setTypeface(font);
        edttxtShahrestan.setTypeface(font);
        edttxtBakhsh.setTypeface(font);
        edttxtShahr.setTypeface(font);
        edttxtMantaghe.setTypeface(font);
        edttxtNoeAddress.setTypeface(font);
        edttxtTelephone.setTypeface(font);
        edttxtTelephoneCode.setTypeface(font);
        edttxtCodePosti.setTypeface(font);
        edttxtKhiabanAsli.setTypeface(font);
        edttxtKhiabanFaree1.setTypeface(font);
        edttxtKhiabanFaree2.setTypeface(font);
        edttxtKoocheAsli.setTypeface(font);
        edttxtKoocheFaree1.setTypeface(font);
        edttxtPelak.setTypeface(font);
        //edttxtAddress.setTypeface(font);

        btnCancel.setTypeface(font);
        btnRegisterAddress.setTypeface(font);

    }

    private void prepareMap(View view)
    {
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));

        map = view.findViewById(R.id.mapView);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);

        PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider();

        IMapController mapController = new MapController(map);
        mapController.setCenter(new GeoPoint(googleLocationProvider.getLatitude() , googleLocationProvider.getLongitude()));
        mapController.setZoom(17.0);

        Marker marker = new Marker(map);
        marker.setPosition(new GeoPoint(googleLocationProvider.getLatitude() , googleLocationProvider.getLongitude()));
        marker.setTitle(getResources().getString(R.string.yourLocation));
        marker.setIcon(getResources().getDrawable(R.drawable.ic_user_marker));
        map.getOverlays().add(marker);

        mPresenter.getSellPolygon();

    }

    private void clearEditTexts()
    {
        edttxtOstan.setText("");
        edttxtShahrestan.setText("");
        edttxtBakhsh.setText("");
        edttxtShahr.setText("");
        edttxtMantaghe.setText("");
        edttxtNoeAddress.setText("");
        edttxtTelephone.setText("");
        edttxtCodePosti.setText("");
        edttxtKhiabanAsli.setText("");
        edttxtKhiabanFaree1.setText("");
        edttxtKhiabanFaree2.setText("");
        edttxtKoocheAsli.setText("");
        edttxtKoocheFaree1.setText("");
        edttxtPelak.setText("");
        //edttxtAddress.setText("");
    }

    @Override
    public void onGetOstanItems(final ArrayList<MahalModel> ostans)
    {
        final ArrayList<String> ostanNames = new ArrayList<>();
        for (MahalModel ostan : ostans)
        {
            ostanNames.add(ostan.getNameMahal());
        }

        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(getActivity(), ostanNames, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                ostanId = ostans.get(selectedIndex).getCcMahal();
                ostanName = ostans.get(selectedIndex).getNameMahal();
                edttxtOstan.setText(ostanName);
                shahrestanId = 0;
                shahrestanName = "";
                bakhshId = 0;
                bakhshName = "";
                shahrId = 0;
                shahrName = "";
                mantagheId = 0;
                mantagheName = "";
                edttxtShahrestan.setText(shahrestanName);
                edttxtBakhsh.setText(bakhshName);
                edttxtShahr.setText(shahrName);
                edttxtMantaghe.setText(mantagheName);
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetShahrestanItems(final ArrayList<MahalModel> shahrestans)
    {
        final ArrayList<String> shahrestanNames = new ArrayList<>();
        for (MahalModel shahrestan : shahrestans)
        {
            shahrestanNames.add(shahrestan.getNameMahal());
        }

        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(getActivity(), shahrestanNames, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                shahrestanId = shahrestans.get(selectedIndex).getCcMahal();
                shahrestanName = shahrestans.get(selectedIndex).getNameMahal();
                edttxtShahrestan.setText(shahrestanName);
                bakhshId = 0;
                bakhshName = "";
                shahrId = 0;
                shahrName = "";
                mantagheId = 0;
                mantagheName = "";
                edttxtBakhsh.setText(bakhshName);
                edttxtShahr.setText(shahrName);
                edttxtMantaghe.setText(mantagheName);
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetBakhshItems(final ArrayList<MahalModel> bakhshes)
    {
        final ArrayList<String> bakhshNames = new ArrayList<>();
        for (MahalModel bakhsh : bakhshes)
        {
            bakhshNames.add(bakhsh.getNameMahal());
        }

        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(getActivity(), bakhshNames, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                bakhshId = bakhshes.get(selectedIndex).getCcMahal();
                bakhshName = bakhshes.get(selectedIndex).getNameMahal();
                edttxtBakhsh.setText(bakhshName);
                shahrId = 0;
                shahrName = "";
                mantagheId = 0;
                mantagheName = "";
                edttxtShahr.setText(shahrName);
                edttxtMantaghe.setText(mantagheName);
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetShahrItems(final ArrayList<MahalModel> shahres)
    {
        final ArrayList<String> shahrNames = new ArrayList<>();
        for (MahalModel shahr : shahres)
        {
            shahrNames.add(shahr.getNameMahal());
        }

        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(getActivity(), shahrNames, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                shahrId = shahres.get(selectedIndex).getCcMahal();
                shahrName = shahres.get(selectedIndex).getNameMahal();
                edttxtShahr.setText(shahrName);
                mantagheId = 0;
                mantagheName = "";
                edttxtMantaghe.setText(mantagheName);
                edttxtTelephoneCode.setText(shahres.get(selectedIndex).getPishShomareh());
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetMantagheItems(final ArrayList<MahalModel> mantaghes)
    {
        final ArrayList<String> mantagheNames = new ArrayList<>();
        for (MahalModel mantaghe : mantaghes)
        {
            mantagheNames.add(mantaghe.getNameMahal());
        }

        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(getActivity(), mantagheNames, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                mantagheId = mantaghes.get(selectedIndex).getCcMahal();
                mantagheName = mantaghes.get(selectedIndex).getNameMahal();
                edttxtMantaghe.setText(mantagheName);
                mPresenter.getMahalCodePosti(mantagheId);
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onGetNoeAddressItems(final ArrayList<Integer> itemId, final ArrayList<String> itemTitles)
    {
        CustomSpinner customSpinner = new CustomSpinner();
        customSpinner.showSpinner(getActivity(), itemTitles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                noeAddressId = itemId.get(selectedIndex);
                noeAddressName = itemTitles.get(selectedIndex);
                edttxtNoeAddress.setText(noeAddressName);
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

            }
        });
    }

    @Override
    public void onErrorExistDarkhastTahvil()
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity)context);
        customAlertDialog.showToast((Activity)context , getResources().getString(R.string.errorExistDarkhastTahvilAddress), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onErrorCantAddNewAddress()
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity)context);
        customAlertDialog.showToast((Activity)context , getResources().getString(R.string.errorCantAddNewAddress), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onErrorExistDarkhast()
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity)context);
        customAlertDialog.showToast((Activity)context , getResources().getString(R.string.errorExistDarkhastAddress), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onErrorExistTahvil()
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity)context);
        customAlertDialog.showToast((Activity)context , getResources().getString(R.string.errorExistTahvilAddress), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onErrorOstan()
    {
        txtinputOstan.setError(getResources().getString(R.string.errorOstan));
    }

    @Override
    public void onErrorShahrestan()
    {
        txtinputShahrestan.setError(getResources().getString(R.string.errorShahrestan));
    }

    @Override
    public void onErrorBakhsh()
    {
        txtinputBakhsh.setError(getResources().getString(R.string.errorBakhsh));
    }

    @Override
    public void onErrorShahr()
    {
        txtinputShahr.setError(getResources().getString(R.string.errorShahr));
    }

    @Override
    public void onErrorMantaghe()
    {
        txtinputMantaghe.setError(getResources().getString(R.string.errorMantaghe));
    }

    @Override
    public void onErrorNoeAddress()
    {
        txtinputNoeAddress.setError(getResources().getString(R.string.errorNoeAddress));
    }

    @Override
    public void onErrorTelephone()
    {
        txtinputTelephone.setError(getResources().getString(R.string.errorTelephone));
    }

    @Override
    public void onErrorCodePosti(boolean wrong) {
        if (wrong) {
            txtinputCodePosti.setError(getResources().getString(R.string.errorCodePostiWrong));
        } else {
            txtinputCodePosti.setError(getResources().getString(R.string.errorCodePosti));
        }
    }

    @Override
    public void onErrorKhiabanAsli()
    {
        txtinputKhiabanAsli.setError(getResources().getString(R.string.errorKhiabanAsli));
    }

    @Override
    public void onErrorKoocheAsli()
    {
        txtinputKoocheAsli.setError(getResources().getString(R.string.errorKoocheAsli));
    }

    @Override
    public void onErrorEmptyPelak()
    {
        txtinputPelak.setError(getResources().getString(R.string.errorPelak));
    }

    @Override
    public void onErrorLengthPelak()
    {
        txtinputPelak.setError(getResources().getString(R.string.errorLengthPelak));
    }

    @Override
    public void drawSellPolygon(ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels, String polygonColor)
    {
        ArrayList<GeoPoint> geoPoints = new ArrayList<>();
        Polygon polygon = new Polygon();
        try
        {
            JSONObject jsonObject = new JSONObject(polygonColor);
            polygon.setFillColor(Color.parseColor(jsonObject.getString("fill")));
            polygon.setStrokeColor(Color.parseColor(jsonObject.getString("stroke")));
            polygon.setStrokeWidth((float) 3.0);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "AddCustomerAddressFragment", "drawSellPolygon", "");
        }
        for (PolygonForoshSatrModel polygonForoshSatrModel : polygonForoshSatrModels)
        {
            geoPoints.add(new GeoPoint(polygonForoshSatrModel.getLat_y() , polygonForoshSatrModel.getLng_x()));
        }
        polygon.setPoints(geoPoints);
        polygon.setTitle("");
        map.getOverlayManager().add(polygon);
    }

    @Override
    public void onSuccessfullySaveNewAddress(MoshtaryAddressModel moshtaryAddressModel)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity) context);
        moshtaryAddressModels.add(moshtaryAddressModel);
        addCustomerInfoModel.setMoshtaryAddressModels(moshtaryAddressModels);
        adapter.notifyDataSetChanged();
        customAlertDialog.showToast((Activity)context, getResources().getString(R.string.addressAdded), Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
        clearData();
        viewSwitcher.showPrevious();
    }

    @Override
    public void onSuccessDeleteAddress(int deletedPosition)
    {
        moshtaryAddressModels.remove(deletedPosition);
        addCustomerInfoModel.setMoshtaryAddressModels(moshtaryAddressModels);
        adapter.notifyDataSetChanged();
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

    private void changeDrawableLeftTint(EditText editText , boolean hasFocus)
    {
        if (hasFocus)
        {
            try
            {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_arrow_down);
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
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_arrow_down);
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

    private void showAddress()
    {
        /*ostan = edttxtOstan.getText().toString().trim();
        shahrestan = edttxtShahrestan.getText().toString().trim();
        shahr = edttxtShahr.getText().toString().trim();
        noeAddress = edttxtNoeAddress.getText().toString().trim();
        telephone = edttxtTelephone.getText().toString().trim();
        codePosti = edttxtCodePosti.getText().toString().trim();
        khiabanAsli = edttxtKhiabanAsli.getText().toString().trim();
        khiabanFaree1 = edttxtKhiabanFaree1.getText().toString().trim();
        khiabanFaree2 = edttxtKhiabanFaree2.getText().toString().trim();
        koocheAsli = edttxtKoocheAsli.getText().toString().trim();
        koocheFaree1 = edttxtKoocheFaree1.getText().toString().trim();
        pelak = edttxtPelak.getText().toString().trim();

        String address = "";
        if (!ostan.equals(""))
        {
            address = ostan + " - ";
        }
        if (!shahrestan.equals(""))
        {
            address = address + shahrestan + " - ";
        }
        if (!shahr.equals(""))
        {
            address = address + shahr + " - ";
        }
        if (!mantaghe.equals(""))
        {
            address = address + mantaghe + " - ";
        }
        if (!khiabanAsli.equals(""))
        {
            address = address + khiabanAsli + " - ";
        }
        if (!khiabanFaree1.equals(""))
        {
            address = address + khiabanFaree1 + " - ";
        }
        if (!khiabanFaree2.equals(""))
        {
            address = address + khiabanFaree2 + " - ";
        }
        if (!koocheAsli.equals(""))
        {
            address = address + koocheAsli + " - ";
        }
        if (!koocheFaree1.equals(""))
        {
            address = address + koocheFaree1 + " - ";
        }
        if (!pelak.equals(""))
        {
            address = address + pelak;
        }*/
        //edttxtAddress.setText(address);
    }


    private void checkLocationPermission()
    {
        PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider();
        if (Build.VERSION.SDK_INT >= 23)
        {
            ArrayList<String> permissions = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (permissions.size() > 0)
            {
                requestPermissions(permissions.toArray(new String[permissions.size()]), LOCATION_PERMISSION);
            }
            else
            {
                checkAddress(googleLocationProvider);
            }
        }
        else
        {
            checkAddress(googleLocationProvider);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                PubFunc.LocationProvider googleLocationProvider = new PubFunc().new LocationProvider();
                checkAddress(googleLocationProvider);
            }
        }
    }

    private void checkAddress(PubFunc.LocationProvider googleLocationProvider)
    {
        txtinputOstan.setError(null);
        txtinputShahrestan.setError(null);
        txtinputBakhsh.setError(null);
        txtinputShahr.setError(null);
        txtinputMantaghe.setError(null);
        txtinputNoeAddress.setError(null);
        txtinputTelephone.setError(null);
        txtinputCodePosti.setError(null);
        txtinputKhiabanAsli.setError(null);
        txtinputKoocheAsli.setError(null);
        txtinputPelak.setError(null);


        MoshtaryAddressModel moshtaryAddressModel = new MoshtaryAddressModel();
        moshtaryAddressModel.setOstanId(ostanId);
        moshtaryAddressModel.setOstanName(ostanName);
        moshtaryAddressModel.setShahrestanId(shahrestanId);
        moshtaryAddressModel.setShahrestanName(shahrestanName);
        moshtaryAddressModel.setBakhshId(bakhshId);
        moshtaryAddressModel.setBakhshName(bakhshName);
        moshtaryAddressModel.setShahrId(shahrId);
        moshtaryAddressModel.setShahrName(shahrName);
        moshtaryAddressModel.setMantagheId(mantagheId);
        moshtaryAddressModel.setMantagheName(mantagheName);
        moshtaryAddressModel.setCcNoeAddress(noeAddressId);
        moshtaryAddressModel.setNameNoeAddress(noeAddressName);
        moshtaryAddressModel.setTelephone(edttxtTelephoneCode.getText().toString().trim().replace("-" , "") + edttxtTelephone.getText().toString());
        moshtaryAddressModel.setCodePosty(edttxtCodePosti.getText().toString());
        moshtaryAddressModel.setKhiabanAsli(edttxtKhiabanAsli.getText().toString());
        moshtaryAddressModel.setKhiabanFarei1(edttxtKhiabanFaree1.getText().toString());
        moshtaryAddressModel.setKhiabanFarei2(edttxtKhiabanFaree2.getText().toString());
        moshtaryAddressModel.setKoocheAsli(edttxtKoocheAsli.getText().toString());
        moshtaryAddressModel.setKoocheFarei1(edttxtKoocheFaree1.getText().toString());
        moshtaryAddressModel.setPelak(edttxtPelak.getText().toString());
        moshtaryAddressModel.setLatitude_y(googleLocationProvider.getLatitude());
        moshtaryAddressModel.setLongitude_x(googleLocationProvider.getLongitude());
        mPresenter.checkNewAddress(moshtaryAddressModel , moshtaryAddressModels, requireTelephone, requireCodePosti, mahalCodePostiModelArrayList);
    }

    @Override
    public Context getAppContext()
    {
        return context;
    }

	@Override
    public void onGetConfig(boolean requireCodePosti, boolean requireTelephone)
    {
        this.requireCodePosti = requireCodePosti;
        this.requireTelephone = requireTelephone;
    }

    @Override
    public void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId) {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity) context);
        customAlertDialog.showMessageAlert((Activity) context, closeActivity, getResources().getString(titleResId), getResources().getString(messageResId), messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog((Activity)context);
        customAlertDialog.showToast((Activity)context, context.getResources().getString(resId), messageType, duration);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onGetMahalCodePosti(ArrayList<MahalCodePostiModel> mahalCodePostiModels) {
        mahalCodePostiModelArrayList = mahalCodePostiModels;
    }

    private void clearData()
    {
        edttxtOstan.setText("");
        edttxtShahrestan.setText("");
        edttxtBakhsh.setText("");
        edttxtShahr.setText("");
        edttxtMantaghe.setText("");
        edttxtNoeAddress.setText("");
        edttxtTelephone.setText("");
        edttxtCodePosti.setText("");
        edttxtKhiabanAsli.setText("");
        edttxtKhiabanFaree1.setText("");
        edttxtKhiabanFaree2.setText("");
        edttxtKoocheAsli.setText("");
        edttxtKoocheFaree1.setText("");
        edttxtPelak.setText("");
        ostanId = 0;
        ostanName = "";
        shahrestanId = 0;
        shahrestanName = "";
        bakhshId = 0;
        bakhshName = "";
        shahrId = 0;
        shahrName = "";
        mantagheId = 0;
        mantagheName = "";
        noeAddressId = 0;
        noeAddressName = "";
        edttxtTelephone.requestFocus();
        edttxtTelephone.findFocus();
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerAddressFragment.class.getSimpleName(), "startMVPOps", "");
        }
    }

    private void initialize(AddCustomerAddressMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new AddCustomerAddressPresenter(view);
            stateMaintainer.put(AddCustomerAddressMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerAddressFragment.class.getSimpleName(), "initialize", "");
        }
    }

    private void reinitialize(AddCustomerAddressMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(AddCustomerAddressMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", AddCustomerAddressFragment.class.getSimpleName(), "reinitialize", "");
            }
        }
    }



}
