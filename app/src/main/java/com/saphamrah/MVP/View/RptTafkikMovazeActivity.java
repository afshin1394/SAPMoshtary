package com.saphamrah.MVP.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.RptTafkikMovazeAdapter;
import com.saphamrah.BaseMVP.RptDarkhastFaktorVazeiatMVP;
import com.saphamrah.BaseMVP.RptTafkikMovazeMVP;
import com.saphamrah.CustomView.CustomSpinner;
import com.saphamrah.MVP.Presenter.RptTafkikMovazePresenter;
import com.saphamrah.Model.RptTafkikMovazeDataModel;
import com.saphamrah.Model.TafkikKalaMovazeDataModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomLoadingDialog;
import com.saphamrah.Utils.CustomSpinnerResponse;
import com.saphamrah.Utils.StateMaintainer;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import me.anwarshahriar.calligrapher.Calligrapher;

public class RptTafkikMovazeActivity extends AppCompatActivity implements RptTafkikMovazeMVP.RequiredViewOps {

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , RptTafkikMovazeActivity.this);
    private RptTafkikMovazeMVP.PresenterOps mPresenter;
    private CustomAlertDialog customAlertDialog;
    private CustomSpinner customSpinner;
    private CustomLoadingDialog customLoadingDialog;
    private AlertDialog alertDialogLoading;
    private RecyclerView recyclerView;
    private TextView vaznTxt;
    private TextView hajmTxt;
    private FloatingActionButton fabRefresh;

    private TextView kartonCount;
    private TextView BasteCount;
    private TextView AdadCount;
    private TextView VaznCount;
    private TextView HajmCount;

    private String ccDarkhastFaktors;
    private ArrayList<RptTafkikMovazeDataModel> darkhastFaktorModels;
    private List<String> titleFaktorsList ;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpt_tafkik_movaze);

        recyclerView = findViewById(R.id.recyclerView);
        vaznTxt = findViewById(R.id.lblvazn_txt);
        hajmTxt = findViewById(R.id.lblhajm_txt);
        fabRefresh= findViewById(R.id.fabRefresh);
        kartonCount = findViewById(R.id.karton_count);
        BasteCount = findViewById(R.id.baste_count);
        AdadCount = findViewById(R.id.adad_count);
        VaznCount = findViewById(R.id.vazn_count);
        HajmCount = findViewById(R.id.hajm_count);
        final FloatingActionMenu fabMenu = findViewById(R.id.fabMenu);


        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        customAlertDialog = new CustomAlertDialog(RptTafkikMovazeActivity.this);
        customLoadingDialog = new CustomLoadingDialog();
        customSpinner = new CustomSpinner();

        startMVPOps();

        mPresenter.getDarkhastFaktorList();

        fabRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                mPresenter.getDarkhastFaktorList();
            }
        });
    }

    @Override
    public Context getAppContext() {
        return RptTafkikMovazeActivity.this;
    }

    @SuppressLint("AutoDispose")
    @Override
    public void onGetDarkhastFaktorList(ArrayList<RptTafkikMovazeDataModel> darkhastFaktorModels) {
        this.darkhastFaktorModels = darkhastFaktorModels;
        titleFaktorsList = new ArrayList<>();

        Observable.fromIterable(darkhastFaktorModels)
                .switchMap(darkhastFaktorModel -> {
                    titleFaktorsList.add(darkhastFaktorModel.toStringCustom());

                    return Observable.just(titleFaktorsList);
                }).subscribe(new Observer<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<String> marjoeeMamorPakhshModels) {

            }

            @Override
            public void onError(Throwable e) {
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "", "RptTafkikMovazeActivity", "onGetDarkhastFaktorList", "");
            }

            @Override
            public void onComplete() {
                showSpinner(titleFaktorsList);
            }
        });


//        for (RptTafkikMovazeDataModel tafkikMovazeDataModel:darkhastFaktorModels){
//            titleFaktorsList.add(tafkikMovazeDataModel.toStringCustom());
//        }

//        showSpinner(titleFaktorsList);
    }

    @Override
    public void onGetTafkikKalaList(ArrayList<TafkikKalaMovazeDataModel> tafkikKalaMovazeDataModels) {
//        vaznTxt.setText(String.format("%1$s (%2$s)",getAppContext().getString(R.string.Vazn),tafkikKalaMovazeDataModels.get(0).getNameVahedVazn()));
//        hajmTxt.setText(String.format("%1$s (%2$s)",getAppContext().getString(R.string.Hajm),tafkikKalaMovazeDataModels.get(0).getNameVahedSize()));
        kartonCount.setText(tafkikKalaMovazeDataModels.get(tafkikKalaMovazeDataModels.size()-1).getTedadDarKarton() + "");
        BasteCount.setText(tafkikKalaMovazeDataModels.get(tafkikKalaMovazeDataModels.size()-1).getTedadDarBasteh() + "");
        AdadCount.setText(tafkikKalaMovazeDataModels.get(tafkikKalaMovazeDataModels.size()-1).getAdad() + "");
        VaznCount.setText((int) (1000 * tafkikKalaMovazeDataModels.get(tafkikKalaMovazeDataModels.size()-1).getVaznNaKhales())/1000.0 + "");
        HajmCount.setText((int) (1000 * tafkikKalaMovazeDataModels.get(tafkikKalaMovazeDataModels.size()-1).getHajm())/1000.0 + "");

        RptTafkikMovazeAdapter adapter = new RptTafkikMovazeAdapter(this, tafkikKalaMovazeDataModels);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this , 0));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemViewCacheSize(tafkikKalaMovazeDataModels.size());
    }

    @Override
    public void closeLoadingAlert() {
        if (alertDialogLoading != null)
        {
            try
            {
                alertDialogLoading.dismiss();
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptDarkhastFaktorVazeiatActivity", "closeLoadingAlert", "");
            }
        }
    }

    @Override
    public void showToast(int resId, int messageType, int duration) {
        customAlertDialog.showToast(RptTafkikMovazeActivity.this, getResources().getString(resId), messageType, duration);

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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptDarkhastFaktorVazeiatActivity", "startMVPOps", "");
        }
    }


    private void initialize(RptTafkikMovazeMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new RptTafkikMovazePresenter(view);
            stateMaintainer.put(RptTafkikMovazeMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptDarkhastFaktorVazeiatActivity", "initialize", "");
        }
    }


    private void reinitialize(RptTafkikMovazeMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(RptDarkhastFaktorVazeiatMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "RptDarkhastFaktorVazeiatActivity", "reinitialize", "");
            }
        }
    }

    private void showSpinner(List<String> titles){
        titles.add(0,"انتخاب همه");
        ccDarkhastFaktors = "";

        customSpinner.showMultiSelectSpinner(RptTafkikMovazeActivity.this, titles, new CustomSpinnerResponse() {
            @Override
            public void onApplySingleSelection(int selectedIndex) {
                if (selectedIndex != 0){
                    ccDarkhastFaktors = darkhastFaktorModels.get(selectedIndex).getCcDarkhastFaktor() + "";
                    titles.remove(0);
                }
                else {
                    for (int i = 0; i < darkhastFaktorModels.size(); i++){
                        ccDarkhastFaktors += darkhastFaktorModels.get(i).getCcDarkhastFaktor() + ",";

                    }
                    if (ccDarkhastFaktors.endsWith(","))
                        ccDarkhastFaktors = ccDarkhastFaktors.substring(0, ccDarkhastFaktors.lastIndexOf(','));

                }
                mPresenter.getTafkikKalaList(ccDarkhastFaktors);

            }

            @Override
            public void onApplyMultiSelection(ArrayList<Integer> selectedIndexes) {

                titles.remove(0);

                if(!selectedIndexes.contains(0)){
                    Observable.fromIterable(selectedIndexes)
                            .switchMap(selectedIndex -> {

                                ccDarkhastFaktors += darkhastFaktorModels.get(--selectedIndex).getCcDarkhastFaktor() + ",";

                                return Observable.just(titleFaktorsList);
                            }).subscribe(new Observer<List<String>>() {
                        @Override
                        public void onSubscribe(Disposable d) {}

                        @Override
                        public void onNext(List<String> marjoeeMamorPakhshModels) {}

                        @Override
                        public void onError(Throwable e) {
                            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "", "RptTafkikMovazeActivity", "showSpinner", "");
                        }

                        @Override
                        public void onComplete() {
                            if (ccDarkhastFaktors.endsWith(","))
                                ccDarkhastFaktors = ccDarkhastFaktors.substring(0, ccDarkhastFaktors.lastIndexOf(','));


                            mPresenter.getTafkikKalaList(ccDarkhastFaktors);

                        }
                    });
                }
                else {
                    for (int i = 0; i < darkhastFaktorModels.size(); i++){
                        ccDarkhastFaktors += darkhastFaktorModels.get(i).getCcDarkhastFaktor() + ",";

                    }
                    if (ccDarkhastFaktors.endsWith(","))
                        ccDarkhastFaktors = ccDarkhastFaktors.substring(0, ccDarkhastFaktors.lastIndexOf(','));

                    mPresenter.getTafkikKalaList(ccDarkhastFaktors);
                }
            }
        });
    }
}
