package com.saphamrah.MVP.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.saphamrah.Adapter.SelectKalaCountAdapter;
import com.saphamrah.BaseMVP.KalaAmargarMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.MVP.Presenter.KalaAmargarPresenter;
import com.saphamrah.Model.BrandModel;
import com.saphamrah.Model.KalaGorohModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;

import static com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED;

public class KalaAmargarActivity extends AppCompatActivity implements KalaAmargarMVP.RequiredViewOps
{

    public static final String CC_PORSESHNAME_KEY = "ccPorseshname";

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , this);
    private KalaAmargarMVP.PresenterOps mPresenter;

    private EditText edttxtBrand;
    private EditText edttxtGorohKala;
    private RecyclerView recyclerView;
    private BottomSheetBehavior bottomSheetBehavior;
    private CustomAlertDialog customAlertDialog;
    private CustomSpinner customSpinner;
    private SelectKalaCountAdapter adapter;
    private int ccPorseshname;
    private Map<Integer,Integer> mapGoodsCount;
    private List<BrandModel> brandModels;
    private List<String> brandTitles;
    private Integer selectedBrandId;
    private Integer selectedKalaGorohId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kala_amargar);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        edttxtBrand = findViewById(R.id.txtBrand);
        edttxtGorohKala = findViewById(R.id.txtGoroh);
        recyclerView = findViewById(R.id.recyclerView);
        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);
        final FloatingActionButton fabFilter = findViewById(R.id.fabFilter);
        Button btnApplyFilter = findViewById(R.id.btnApplyFilter);
        Button btnApply = findViewById(R.id.btnApply);
        LinearLayout lnrlayBottomsheet = findViewById(R.id.lnrlayRoot);
        bottomSheetBehavior = BottomSheetBehavior.from(lnrlayBottomsheet);
        bottomSheetBehavior.setState(STATE_COLLAPSED);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        customAlertDialog = new CustomAlertDialog(this);
        customSpinner = new CustomSpinner();
        mapGoodsCount = new HashMap<>();
        ccPorseshname = getIntent().getIntExtra(CC_PORSESHNAME_KEY, -1);
        brandTitles = new ArrayList<>();
        brandModels = new ArrayList<>();

        startMVPOps();

        mPresenter.getAllGoods(ccPorseshname);
        mPresenter.getAllBrands();

        //Brand
        edttxtBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                openBrand();
            }
        });
        edttxtBrand.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                {
                    openBrand();
                }
            }
        });
        //Brand


        //Brand
        edttxtGorohKala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.getGorohKala(selectedBrandId);
            }
        });
        edttxtGorohKala.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus)
            {
                if (hasFocus)
                {
                    mPresenter.getGorohKala(selectedBrandId);
                }
            }
        });
        //Brand


        fabFilter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });


        btnApplyFilter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bottomSheetBehavior.setState(STATE_COLLAPSED);
                mPresenter.getGoodsByGorohKala(selectedBrandId, selectedKalaGorohId);
            }
        });


        btnApply.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mPresenter.insertGoodsCount(mapGoodsCount, ccPorseshname);
            }
        });

    }

    @Override
    public void onBackPressed()
    {

    }

    @Override
    public Context getAppContext()
    {
        return this;
    }

    @Override
    public void showGoods(List<KalaModel> kalaModels, Map<Integer,Integer> porseshnamehShomareshModels)
    {
        if (porseshnamehShomareshModels != null)
        {
            mapGoodsCount.clear();
            mapGoodsCount.putAll(porseshnamehShomareshModels);
        }
        adapter = new SelectKalaCountAdapter(this, kalaModels, mapGoodsCount, new SelectKalaCountAdapter.OnItemClickListener()
        {
            @Override
            public void onRemoveFocus(KalaModel kalaModel, int position, String count)
            {
                mapGoodsCount.remove(kalaModel.getCcKalaCode());
                kalaModel.setAdad(-1);
                if (count != null && !count.trim().equals(""))
                {
                    try
                    {
                        int tedad = Integer.parseInt(count);
                        mapGoodsCount.put(kalaModel.getCcKalaCode(), tedad);
                        kalaModel.setAdad(tedad);
                    }
                    catch (Exception e){}
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onGetAllBrands(List<BrandModel> brandModels, List<String> brandTitles)
    {
        this.brandModels = brandModels;
        this.brandTitles = brandTitles;
    }

    private void openBrand()
    {
        customSpinner.showSpinner(this, brandTitles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtBrand.setText(brandTitles.get(selectedIndex));
                selectedBrandId = brandModels.get(selectedIndex).getCcBrand();
                edttxtGorohKala.setText("");
                selectedKalaGorohId = null;
                Log.d("KalaAmargar", "selected brand activity : " + selectedBrandId);
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void onGetGorohKala(final List<KalaGorohModel> kalaGorohModels, List<String> titles)
    {
        customSpinner.showSpinner(this, titles, new CustomSpinnerResponse()
        {
            @Override
            public void onApplySingleSelection(int selectedIndex)
            {
                edttxtGorohKala.setText(kalaGorohModels.get(selectedIndex).getNameGoroh());
                selectedKalaGorohId = kalaGorohModels.get(selectedIndex).getCcGoroh();
            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes)
            {

            }
        });
    }

    @Override
    public void closeActivityAndOpenReport()
    {
        startActivity(new Intent(KalaAmargarActivity.this, PorseshnameAdamActivity.class));
        KalaAmargarActivity.this.finish();
    }

    @Override
    public void showErrorNotFoundGoods()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorNotFoundKala), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorInsert()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorSaveData), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorSelectBrand()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorSelectBrand), Constants.FAILED_MESSAGE(), getString(R.string.apply));
    }

    @Override
    public void showErrorSelectGorohKala()
    {
        customAlertDialog.showMessageAlert(this, false, "", getString(R.string.errorSelectKalaGoroh), Constants.FAILED_MESSAGE(), getString(R.string.apply));
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

    private void initialize(KalaAmargarMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new KalaAmargarPresenter(view);
            stateMaintainer.put(KalaAmargarMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(LogPPCModel.LOG_EXCEPTION, exception.toString(), "", TAG, "initialize", "");
        }
    }

    private void reinitialize(KalaAmargarMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(KalaAmargarMVP.PresenterOps.class.getSimpleName());
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
