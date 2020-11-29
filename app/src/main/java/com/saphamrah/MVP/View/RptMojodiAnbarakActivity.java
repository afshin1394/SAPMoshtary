package com.saphamrah.MVP.View;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.saphamrah.Adapter.RptMojodiAnbarakAdapter;
import com.saphamrah.Adapter.RptMojodiAnbarakTableAdapter;
import com.saphamrah.BaseMVP.RptMojodiAnbrakMVP;
import com.saphamrah.MVP.Presenter.RptMojodiAnbarakPresenter;
import com.saphamrah.Model.RptMojodiAnbarModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;

import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.CirclePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.CirclePromptFocal;

public class RptMojodiAnbarakActivity extends AppCompatActivity implements RptMojodiAnbrakMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RptMojodiAnbarakActivity.this);
    private RptMojodiAnbrakMVP.PresenterOps mPresenter;

    private MaterialSearchView searchView;
    private RecyclerView recyclerView;
    private RptMojodiAnbarakAdapter adapter;
    private RptMojodiAnbarakTableAdapter tableAdapter;
    /*private ImageView imgCloseSearch;
    private EditText edttxtSearchWord;*/
    private FloatingActionMenu fabMenu;
    private FloatingActionButton fabSort;
    private FloatingActionButton fabChangeView;
    private FloatingActionButton fabSearch;
    private FloatingActionButton fabRefresh;
    private boolean searchMode;
    private int sortedType; //sort by code kala = 1 , sort by name kala = 2 , sort by count = 3
    private boolean viewAsTable;
    private ArrayList<RptMojodiAnbarModel> mojodiAnbarModels;

    private final int SORT_BY_CODE_KALA = 1;
    private final int SORT_BY_NAME_KALA = 2;
    private final int SORT_BY_COUNT = 3;

    //private ArrayList<RptMojodiAnbarModel> mojodiAnbarModelsSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_mojodi_anbarak);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);


        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        fabMenu = findViewById(R.id.fabMenu);
        fabSort = findViewById(R.id.fabSort);
        fabChangeView = findViewById(R.id.fabChangeView);
        fabSearch = findViewById(R.id.fabSearch);
        FloatingActionButton fabHelp = findViewById(R.id.fabHelp);
        fabRefresh = findViewById(R.id.fabRefresh);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try
        {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        searchView.setVoiceSearch(false);
        searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String searchWord = query.trim();
                mPresenter.checkSearchNameKala(mojodiAnbarModels , searchWord , viewAsTable);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.trim().length() > 0)
                {
                    mPresenter.checkSearchNameKala(mojodiAnbarModels, newText, viewAsTable);
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
                if (viewAsTable)
                {
                    setAdapterAsTable(mojodiAnbarModels);
                }
                else
                {
                    setAdapter(mojodiAnbarModels);
                }
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_up_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchWord = ((TextView)findViewById(com.miguelcatalan.materialsearchview.R.id.searchTextView)).getText().toString().trim();
                mPresenter.checkSearchNameKala(mojodiAnbarModels , searchWord , viewAsTable);
            }
        });

        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.closeSearch();
                searchMode = false;
                if (viewAsTable)
                {
                    setAdapterAsTable(mojodiAnbarModels);
                }
                else
                {
                    setAdapter(mojodiAnbarModels);
                }
            }
        });


        searchMode = false;
        sortedType = SORT_BY_CODE_KALA;
        viewAsTable = false;
        mojodiAnbarModels = new ArrayList<>();
        //mojodiAnbarModelsSearch = new ArrayList<>();

        startMVPOps();

        mPresenter.getMojodiAnbar();


        fabSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                searchMode = false;
                /*edttxtSearchWord.setText("");
                imgCloseSearch.setVisibility(View.GONE);*/
                searchView.closeSearch();
                if (sortedType == SORT_BY_CODE_KALA)
                {
                    mPresenter.sortByNameKala(viewAsTable);
                }
                else if (sortedType == SORT_BY_NAME_KALA)
                {
                    mPresenter.sortByCount(viewAsTable);
                }
                else
                {
                    mPresenter.sortByCodeKala(mojodiAnbarModels , viewAsTable);
                }
            }
        });


        fabChangeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                searchMode = false;
                /*edttxtSearchWord.setText("");
                imgCloseSearch.setVisibility(View.GONE);*/
                searchView.closeSearch();
                if (viewAsTable)
                {
                    setAdapter(mojodiAnbarModels);
                }
                else
                {
                    setAdapterAsTable(mojodiAnbarModels);
                }
            }
        });

        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                searchView.showSearch(true);
            }
        });


        fabHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.open(true);
                showHelp(0);
            }
        });


        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                mPresenter.updateMojodiAnbar();
            }
        });


        /*imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RptMojodiAnbarakActivity.this.finish();
            }
        });*/

    }


    @Override
    protected void onResume()
    {
        super.onResume();
        recyclerView.findFocus();
        recyclerView.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.material_search_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public Context getAppContext()
    {
        return RptMojodiAnbarakActivity.this;
    }


    @Override
    public void setAdapter(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels)
    {
        this.mojodiAnbarModels = new ArrayList<>();
        this.mojodiAnbarModels = mojodiAnbarModels;
        adapter = new RptMojodiAnbarakAdapter(RptMojodiAnbarakActivity.this , mojodiAnbarModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptMojodiAnbarakActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RptMojodiAnbarakActivity.this , 0));
        recyclerView.setAdapter(adapter);
        fabChangeView.setLabelText(getResources().getString(R.string.showAsTable));
        fabChangeView.setImageResource(R.drawable.ic_table_list);
        viewAsTable = false;
    }

    @Override
    public void showSearchResult(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels , boolean viewAsTable)
    {
        searchMode = true;
        //imgCloseSearch.setVisibility(View.VISIBLE);
        if (viewAsTable)
        {
            tableAdapter = new RptMojodiAnbarakTableAdapter(RptMojodiAnbarakActivity.this , mojodiAnbarModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptMojodiAnbarakActivity.this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(RptMojodiAnbarakActivity.this , 0));
            recyclerView.setAdapter(tableAdapter);
        }
        else
        {
            adapter = new RptMojodiAnbarakAdapter(RptMojodiAnbarakActivity.this , mojodiAnbarModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptMojodiAnbarakActivity.this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(RptMojodiAnbarakActivity.this , 0));
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onSortByCodeKala(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels , boolean viewAsTable)
    {
        this.mojodiAnbarModels = new ArrayList<>();
        this.mojodiAnbarModels = mojodiAnbarModels;
        if (viewAsTable)
        {
            tableAdapter = new RptMojodiAnbarakTableAdapter(RptMojodiAnbarakActivity.this , mojodiAnbarModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptMojodiAnbarakActivity.this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(RptMojodiAnbarakActivity.this , 0));
            recyclerView.setAdapter(tableAdapter);
        }
        else
        {
            adapter = new RptMojodiAnbarakAdapter(RptMojodiAnbarakActivity.this , mojodiAnbarModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptMojodiAnbarakActivity.this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(RptMojodiAnbarakActivity.this , 0));
            recyclerView.setAdapter(adapter);
        }
        sortedType = SORT_BY_CODE_KALA;
        fabSort.setLabelText(getResources().getString(R.string.sortByNameKala));
        fabSort.setImageResource(R.drawable.ic_sort_by_alphabet);
    }

    @Override
    public void onSortByNameKala(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels , boolean viewAsTable)
    {
        this.mojodiAnbarModels = new ArrayList<>();
        this.mojodiAnbarModels = mojodiAnbarModels;
        if (viewAsTable)
        {
            tableAdapter = new RptMojodiAnbarakTableAdapter(RptMojodiAnbarakActivity.this , mojodiAnbarModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptMojodiAnbarakActivity.this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(RptMojodiAnbarakActivity.this , 0));
            recyclerView.setAdapter(tableAdapter);
        }
        else
        {
            adapter = new RptMojodiAnbarakAdapter(RptMojodiAnbarakActivity.this , mojodiAnbarModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptMojodiAnbarakActivity.this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(RptMojodiAnbarakActivity.this , 0));
            recyclerView.setAdapter(adapter);
        }
        sortedType = SORT_BY_NAME_KALA;
        fabSort.setLabelText(getResources().getString(R.string.sortByCount));
        fabSort.setImageResource(R.drawable.ic_sort_by_inventory);
    }

    @Override
    public void onSortByCount(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels, boolean viewAsTable)
    {
        this.mojodiAnbarModels = new ArrayList<>();
        this.mojodiAnbarModels = mojodiAnbarModels;
        if (viewAsTable)
        {
            tableAdapter = new RptMojodiAnbarakTableAdapter(RptMojodiAnbarakActivity.this , mojodiAnbarModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptMojodiAnbarakActivity.this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(RptMojodiAnbarakActivity.this , 0));
            recyclerView.setAdapter(tableAdapter);
        }
        else
        {
            adapter = new RptMojodiAnbarakAdapter(RptMojodiAnbarakActivity.this , mojodiAnbarModels);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptMojodiAnbarakActivity.this);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(RptMojodiAnbarakActivity.this , 0));
            recyclerView.setAdapter(adapter);
        }
        sortedType = SORT_BY_COUNT;
        fabSort.setLabelText(getResources().getString(R.string.sortByCodeKala));
        fabSort.setImageResource(R.drawable.ic_sort_by_numeric);
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(RptMojodiAnbarakActivity.this);
        customAlertDialog.showToast(RptMojodiAnbarakActivity.this, getResources().getString(resId), messageType, duration);
    }

    @Override
    public void setAdapterAsTable(ArrayList<RptMojodiAnbarModel> mojodiAnbarModels)
    {
        tableAdapter = new RptMojodiAnbarakTableAdapter(RptMojodiAnbarakActivity.this , mojodiAnbarModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(RptMojodiAnbarakActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(RptMojodiAnbarakActivity.this , 0));
        recyclerView.setAdapter(tableAdapter);
        viewAsTable = true;
        fabChangeView.setLabelText(getResources().getString(R.string.showAsList));
        fabChangeView.setImageResource(R.drawable.ic_list);
    }

    /*private void showHelp()
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(RptMojodiAnbarakActivity.this);
        customAlertDialog.showRptMojodiAnbarakHelpAlert(RptMojodiAnbarakActivity.this);
    }*/


    private void disableButtons(View[] views)
    {
        for (View view : views)
        {
            view.setEnabled(false);
        }
    }


    private void enableButtons(View[] views)
    {
        for (View view : views)
        {
            view.setEnabled(true);
        }
    }

    private void showHelp(final int index)
    {
        Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));
        final View[] VIEW_ID_OF_HELP = new View[]{fabRefresh         , fabSearch           , fabChangeView         , fabSort};
        final int[] TITLE_ID_OF_HELP = new int[]{R.string.refresh    , R.string.search     , R.string.showType     , R.string.sortType};
        final int[] DESC_ID_OF_HELP = new int[]{R.string.helpRefresh , R.string.helpSearch , R.string.helpShowType , R.string.helpSort};
        final int[] FOCAL_COLOR_ID_OF_HELP = new int[]{getResources().getColor(R.color.colorFabRefresh) , getResources().getColor(R.color.colorFabSearch)
                , getResources().getColor(R.color.colorFabList) , getResources().getColor(R.color.colorFabSort)};

        if (index == 0)
        {
            disableButtons(VIEW_ID_OF_HELP);
        }

        new MaterialTapTargetPrompt.Builder(this)
                .setTarget(VIEW_ID_OF_HELP[index])
                .setPrimaryText(TITLE_ID_OF_HELP[index])
                .setSecondaryText(DESC_ID_OF_HELP[index])
                .setBackgroundColour(FOCAL_COLOR_ID_OF_HELP[index])
                .setFocalColour(getResources().getColor(R.color.colorWhite))
                .setPromptBackground(new CirclePromptBackground())
                .setPromptFocal(new CirclePromptFocal())
                .setPrimaryTextTypeface(font)
                .setSecondaryTextTypeface(font)
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                    @Override
                    public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                        if (state ==  MaterialTapTargetPrompt.STATE_DISMISSED || state ==  MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                        {
                            fabMenu.open(true);
                            if (index+1 < VIEW_ID_OF_HELP.length)
                            {
                                showHelp(index+1);
                            }
                            else
                            {
                                enableButtons(VIEW_ID_OF_HELP);
                                fabMenu.close(true);
                            }
                        }
                    }
                }).show();
    }


    private void visibleCloseSearchIcon()
    {
        findViewById(com.miguelcatalan.materialsearchview.R.id.action_empty_btn).setVisibility(View.VISIBLE);
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", RptMojodiAnbarakActivity.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(RptMojodiAnbrakMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RptMojodiAnbarakPresenter(view);
            stateMaintainer.put(RptMojodiAnbrakMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", RptMojodiAnbarakActivity.class.getSimpleName(), "initialize", "");
        }
    }


    private void reinitialize(RptMojodiAnbrakMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RptMojodiAnbrakMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", RptMojodiAnbarakActivity.class.getSimpleName(), "reinitialize", "");
            }
        }
    }



}
