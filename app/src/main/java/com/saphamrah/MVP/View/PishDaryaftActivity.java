package com.saphamrah.MVP.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionMenu;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.saphamrah.Adapter.GetProgramItemsStatusAdapter;
import com.saphamrah.Adapter.PishDaryaftAdapter;
import com.saphamrah.BaseMVP.PishDaryaftMVP;
import com.saphamrah.MVP.Presenter.PisDaryaftPresenter;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import it.sephiroth.android.library.viewrevealanimator.ViewRevealAnimator;
import me.anwarshahriar.calligrapher.Calligrapher;

public class PishDaryaftActivity extends AppCompatActivity implements PishDaryaftMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , PishDaryaftActivity.this);
    private PishDaryaftMVP.PresenterOps mPresenter;

    private CustomAlertDialog customAlertDialog;
    private PishDaryaftAdapter adapter;
    private ArrayList<MoshtaryModel> moshtaryModels;
    private boolean searchMode;

    private List<Integer> getCustomerInfoItemsStatus;
    private ViewRevealAnimator viewRevealAnimator;
    private RecyclerView recyclerViewGetProgramItems;
    private ProgressBar progressBar;
    private TextView lblPassedItemCounter;
    private TextView lblProgressPercentage;
    private ShineButton imgGetProgramResultFailed;
    private ShineButton imgGetProgramResultSuccess;
    private TextView lblGetProgramResult;
    private Button btnGetProgramResultClose;
    private GetProgramItemsStatusAdapter alertAdapter;
    private AlertDialog show;
    private int getCustomerInfoItemCount;
    private int selectedccMasir;
    private final int OPEN_INVOICE_SETTLEMENT = 100;

    // find View
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fabMenu)
    FloatingActionMenu fabMenu;
    @BindView(R.id.searchView)
    MaterialSearchView searchView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    // click Listener
    @OnClick(R.id.fabChooseRoute)
    public void fabChooseRoute(){
        fabMenu.close(true);
        searchView.closeSearch();
    }
    @OnClick(R.id.fabSearch)
    public void fabSearch(){
        fabMenu.close(true);
        searchView.showSearch(true);
        searchMode = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pish_daryaft);
        ButterKnife.bind(this);

        // set font
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);


        searchView.setVoiceSearch(false);
        searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        removeToolbar();


        customAlertDialog = new CustomAlertDialog(PishDaryaftActivity.this);
        moshtaryModels = new ArrayList<>();
        searchMode = false;
        getCustomerInfoItemCount = getResources().getStringArray(R.array.getCustomerInfo).length;
        selectedccMasir = -1;

        startMVPOps();

        mPresenter.getAllCustomers();


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                String searchWord = query.trim();
                mPresenter.searchCustomer(searchWord , moshtaryModels);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0)
                {
                    mPresenter.searchCustomer(newText , moshtaryModels);
                }
                else
                {
                    visibleCloseSearchIcon();
                }
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                visibleCloseSearchIcon();
            }

            @Override
            public void onSearchViewClosed() {
                searchMode = false;
                mPresenter.getAllCustomers();
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchWord = ((TextView)findViewById(com.miguelcatalan.materialsearchview.R.id.searchTextView)).getText().toString().trim();
                mPresenter.searchCustomer(searchWord , moshtaryModels);
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.closeSearch();
                searchMode = false;
                mPresenter.getAllCustomers();
            }
        });

    }

    @Override
    public Context getAppContext()
    {
        return PishDaryaftActivity.this;
    }


    /**
     * set adapter for show all customers
     * @param moshtaryModels
     */
    @Override
    public void onGetAllCustomers(ArrayList<MoshtaryModel> moshtaryModels)
    {
        this.moshtaryModels = new ArrayList<>();
        this.moshtaryModels.addAll(moshtaryModels);
        adapter = new PishDaryaftAdapter(PishDaryaftActivity.this, moshtaryModels,(operation, position) -> {
            if (operation == Constants.EDIT()){
                openInvoiceSettlement(moshtaryModels.get(position).getCcMoshtary() , 0);
            } else if (operation == Constants.SEND()){
                showSendAlert(moshtaryModels.get(position).getCcMoshtary() , position);
            }

        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PishDaryaftActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(PishDaryaftActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    /**
     * set adapter for search
     * @param moshtaryModels
     */
    @Override
    public void onGetSearchResult(final ArrayList<MoshtaryModel> moshtaryModels)
    {
        adapter = new PishDaryaftAdapter(PishDaryaftActivity.this, moshtaryModels, (PishDaryaftAdapter.OnItemClickListener) (operation ,position) -> {
           if (operation == Constants.EDIT()){
               openInvoiceSettlement(moshtaryModels.get(position).getCcMoshtary() , 0);
           } else if (operation == Constants.SEND()){
               showSendAlert(moshtaryModels.get(position).getCcMoshtary() , position);
           }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PishDaryaftActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(PishDaryaftActivity.this , 0));
        recyclerView.setAdapter(adapter);
    }

    /**
     * send pish daryaft
     *
     */
    private void showSendAlert( int ccMoshtary  ,  int position)
    {
        customAlertDialog.showLogMessageAlert(this, false, "", getResources().getString(R.string.sendWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.cancel), getResources().getString(R.string.apply), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {}
            @Override
            public void setOnApplyClick() {
                mPresenter.getDariaftPardakhtForSend(ccMoshtary  , position);
            }
        });
    }

    /**
     * show dialog success for send to server
     * @param resId
     * @param messageType
     */
    @Override
    public void showAlertMessage(int resId, int messageType) {
        customAlertDialog.showMessageAlert(this, false, "", getResources().getString(resId), messageType, getResources().getString(R.string.apply));
    }



    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(PishDaryaftActivity.this, getResources().getString(resId), messageType, duration);
    }


    /**
     *  open activity
     */
    private void openInvoiceSettlement(int ccMoshtary , long ccDarkhastFaktor)
    {
        Intent intent = new Intent(this , InvoiceSettlementActivity.class);
        intent.putExtra("ccMoshtary" , ccMoshtary);
        intent.putExtra("ccDarkhastFaktor" , ccDarkhastFaktor);
        intent.putExtra("sourceActivity" , "PishDaryaftActivity");
        startActivityForResult(intent , OPEN_INVOICE_SETTLEMENT);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "CustomersListActivity", "startMVPOps", "");
        }
    }


    private void initialize(PishDaryaftMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new PisDaryaftPresenter(view);
            stateMaintainer.put(PishDaryaftMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "CustomersListActivity", "initialize", "");
        }
    }


    private void reinitialize(PishDaryaftMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(PishDaryaftMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "CustomersListActivity", "reinitialize", "");
            }
        }
    }


    private void visibleCloseSearchIcon()
    {
        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);
    }

    private void removeToolbar()
    {
        setSupportActionBar(toolbar);
        try
        {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

}
