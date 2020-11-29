package com.saphamrah.MVP.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.ramotion.foldingcell.FoldingCell;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.saphamrah.Adapter.GetProgramAdapter;
import com.saphamrah.Adapter.GetProgramItemsStatusAdapter;
import com.saphamrah.BaseMVP.GetProgramMVP;
import com.saphamrah.MVP.Presenter.GetProgramPresenter;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.StateMaintainer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import it.sephiroth.android.library.viewrevealanimator.ViewRevealAnimator;
import me.anwarshahriar.calligrapher.Calligrapher;

public class GetProgramActivity extends AppCompatActivity implements GetProgramMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , GetProgramActivity.this);
    private GetProgramMVP.PresenterOps mPresenter;


    private ListView lstview;
    private GetProgramAdapter adapter;
    private CustomAlertDialog customAlertDialog;
    private ForoshandehMamorPakhshModel foroshandehMamorPakhshModel;
    private ArrayList<ForoshandehMamorPakhshModel> arrayListForoshandehMamorPakhshModel;
    private String selectedDate;

    private List<Integer> getProgramItemsStatus;
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
    private int getProgramItemCount;
    private int noeMasouliat;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_program);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        startMVPOps();

        customAlertDialog = new CustomAlertDialog(GetProgramActivity.this);
        foroshandehMamorPakhshModel = new ForoshandehMamorPakhshModel();
        selectedDate = new PubFunc().new DateUtils().todayDateWithSlash(GetProgramActivity.this);

        ImageView imgBack = findViewById(R.id.imgBack);
        lstview = findViewById(R.id.lstview);

        mPresenter.getAllForoshandehMamorPakhsh();

        foroshandehMamorPakhshModel.setBtnGetProgramClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v)
            {
                customAlertDialog.showLogMessageAlert(GetProgramActivity.this, false,
                        getResources().getString(R.string.getProgram), getResources().getString(R.string.getProgramWarning),
                        Constants.INFO_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes),
                        new CustomAlertDialogResponse() {
                            @Override
                            public void setOnCancelClick() {}

                            @Override
                            public void setOnApplyClick() {
                                mPresenter.checkGetProgram(selectedDate , arrayListForoshandehMamorPakhshModel.get((int)v.getTag()));
                            }
                        });

            }
        });


        foroshandehMamorPakhshModel.setbtnShowDateAlertClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showDatePickerAlert(v);
            }
        });

        foroshandehMamorPakhshModel.setBtnUpdateForoshandehClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.checkUpdateForoshandeh(arrayListForoshandehMamorPakhshModel.get((int)v.getTag()));
            }
        });

        foroshandehMamorPakhshModel.setBtnUpdateKalaModatVosolClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getProgramItemCount = getResources().getStringArray(R.array.updateKalaModatVosol).length;
                getProgramItemsStatus = initializeItemsStatus();
                showItemStatusList(Constants.GET_PROGRAM_UPDATE_KALA());
                mPresenter.checkUpdateKalaModatVosol(arrayListForoshandehMamorPakhshModel.get((int)v.getTag()));
            }
        });

        foroshandehMamorPakhshModel.setBtnUpdateJayezehTakhfifClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getProgramItemCount = getResources().getStringArray(R.array.updateJayezehTakhfif).length;
                getProgramItemsStatus = initializeItemsStatus();
                showItemStatusList(Constants.GET_PROGRAM_UPDATE_JAYEZE());
                mPresenter.checkUpdateJayezehTakhfif(arrayListForoshandehMamorPakhshModel.get((int)v.getTag()));
            }
        });

        foroshandehMamorPakhshModel.setBtnUpdateCustomersClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getProgramItemCount = getResources().getStringArray(R.array.updateCustomers).length;
                getProgramItemsStatus = initializeItemsStatus();
                showItemStatusList(Constants.GET_PROGRAM_UPDATE_MOSHTARY());
                mPresenter.checkUpdateCustomers(selectedDate , arrayListForoshandehMamorPakhshModel.get((int)v.getTag()));
            }
        });


        foroshandehMamorPakhshModel.setBtnUpdateParametersClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getProgramItemCount = getResources().getStringArray(R.array.updateParameters).length;
                getProgramItemsStatus = initializeItemsStatus();
                showItemStatusList(Constants.GET_PROGRAM_UPDATE_PARAMETERS());
                mPresenter.checkUpdateParameter(arrayListForoshandehMamorPakhshModel.get((int)v.getTag()));
            }
        });


        foroshandehMamorPakhshModel.setBtnUpdateEtebarForoshandehClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                getProgramItemCount = getResources().getStringArray(R.array.updateEtebarForoshandeh).length;
                getProgramItemsStatus = initializeItemsStatus();
                showItemStatusList(Constants.GET_PROGRAM_UPDATE_ETEBAR_FOROSHANDEH());
                mPresenter.checkUpdateEtebarForoshandeh(arrayListForoshandehMamorPakhshModel.get((int)v.getTag()));
            }
        });


        lstview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(((FoldingCell) view).isUnfolded())
                {
                    ((FoldingCell) view).fold(false);
                    adapter.registerFold(position);
                }
                else
                {
                    HashSet<Integer> unfoldedIndexes = adapter.getUnfoldedIndexes();
                    for (int i : unfoldedIndexes)
                    {
                        if (i != position)
                        {
                            ((FoldingCell)getViewByPosition(i , lstview)).fold(false);
                            adapter.registerFold(i);
                        }
                    }
                    ((FoldingCell) view).unfold(false);
                    adapter.registerUnfold(position);
                }
            }
        });


        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                GetProgramActivity.this.finish();
            }
        });

    }

    private List<Integer> initializeItemsStatus()
    {
        return new ArrayList<>(Collections.nCopies(getProgramItemCount , 0));
    }


    public void showDatePickerAlert(final View myView)
    {
        PersianCalendar persianCalendar = new PersianCalendar();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth)
                    {
                        try
                        {
                            monthOfYear++;
                            String month = monthOfYear < 10 ? "0" + monthOfYear : String.valueOf(monthOfYear);
                            String day = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                            selectedDate = getResources().getString(R.string.dateWithSplashFormat , String.valueOf(year) , month , day);
                            ((TextView)((LinearLayout)myView).getChildAt(1)).setText(selectedDate);
                        }
                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", GetProgramActivity.class.getSimpleName(), "showDatePickerAlert", "onDateSet");
                        }
                    }
                }, persianCalendar.getPersianYear(), persianCalendar.getPersianMonth() , persianCalendar.getPersianDay());
        datePickerDialog.show(getFragmentManager(), "Datepickerdialog");
    }

    public View getViewByPosition(int pos, ListView listView)
    {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition )
        {
            return listView.getAdapter().getView(pos, null, listView);
        }
        else
        {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }


    @Override
    public Context getAppContext()
    {
        return GetProgramActivity.this;
    }


    @Override
    public void setAdapter(ArrayList<ForoshandehMamorPakhshModel> foroshandehMamorPakhshModels)
    {
        foroshandehMamorPakhshModel = foroshandehMamorPakhshModels.get(0);
        arrayListForoshandehMamorPakhshModel = foroshandehMamorPakhshModels;
        adapter = new GetProgramAdapter(this , foroshandehMamorPakhshModels /*, profileImage , ccAfradProfileImage*/);
        lstview.setAdapter(adapter);
        Log.d("GetProgram","foroshandehMamorPakhshModels="+foroshandehMamorPakhshModels.toString());
    }

    @Override
    public void onGetNoeMasouliat(int noeMasouliat)
    {
        this.noeMasouliat = noeMasouliat;
        if (noeMasouliat != 7)
        {
            getProgramItemCount = getResources().getStringArray(R.array.getProgramItems).length;
            getProgramItemsStatus = initializeItemsStatus();
            showItemStatusList(Constants.GET_PROGRAM_FULL());
        }
        else
        {
            getProgramItemCount = getResources().getStringArray(R.array.getProgramAmargarItems).length;
            getProgramItemsStatus = initializeItemsStatus();
            showItemStatusList(Constants.GET_PROGRAM_FULL());
        }
    }

    @Override
    public void updateStatusOfSuccessfulItem(int itemIndex)
    {
        getProgramItemsStatus.set(itemIndex , 1);
        alertAdapter.notifyDataSetChanged();
        recyclerViewGetProgramItems.smoothScrollToPosition(itemIndex + 1);
        int percentage = (itemIndex*100)/getProgramItemCount;
        progressBar.setProgress(percentage);
        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , percentage));
        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , String.valueOf(itemIndex + 1) , String.valueOf(getProgramItemCount)));
    }

    @Override
    public void updateStatusOfFailedItem(int getProgramType , int itemIndex, String error)
    {
        Log.d("GetProgram","getProgramType:"+getProgramType+" itemIndex: "+itemIndex + " ,error:"+error);
        getProgramItemsStatus.set(itemIndex , -1);
        alertAdapter.notifyDataSetChanged();
        imgGetProgramResultFailed.setVisibility(View.VISIBLE);
        imgGetProgramResultSuccess.setVisibility(View.GONE);
        try
        {
            viewRevealAnimator.showNext();
        }catch (Exception e){e.printStackTrace();}
        lblGetProgramResult.setText(getResources().getString(R.string.getProgramError , getItemName(getProgramType , itemIndex)));
        btnGetProgramResultClose.setBackground(getResources().getDrawable(R.drawable.altdlg_btn_bg_failed));
        imgGetProgramResultFailed.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgGetProgramResultFailed.performClick();
                imgGetProgramResultFailed.setPressed(true);
                imgGetProgramResultFailed.invalidate();
            }
        } , 800);
    }


    private String getItemName(int getProgramType , int itemIndex)
    {
        try
        {
            JSONObject jsonObject = new JSONObject();
            if (getProgramType == Constants.GET_PROGRAM_FULL())
            {
                if (noeMasouliat != 7)
                {
                    jsonObject = new JSONObject(getResources().getStringArray(R.array.getProgramItems)[itemIndex]);
                }
                else
                {
                    jsonObject = new JSONObject(getResources().getStringArray(R.array.getProgramAmargarItems)[itemIndex]);
                }
            }
            else if (getProgramType == Constants.GET_PROGRAM_UPDATE_KALA())
            {
                jsonObject = new JSONObject(getResources().getStringArray(R.array.updateKalaModatVosol)[itemIndex]);
            }
            else if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE())
            {
                jsonObject = new JSONObject(getResources().getStringArray(R.array.updateJayezehTakhfif)[itemIndex]);
            }
            else if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY())
            {
                jsonObject = new JSONObject(getResources().getStringArray(R.array.updateCustomers)[itemIndex]);
            }
            else if (getProgramType == Constants.GET_PROGRAM_UPDATE_PARAMETERS())
            {
                jsonObject = new JSONObject(getResources().getStringArray(R.array.updateParameters)[itemIndex]);
            }
            else if (getProgramType == Constants.GET_PROGRAM_UPDATE_ETEBAR_FOROSHANDEH())
            {
                jsonObject = new JSONObject(getResources().getStringArray(R.array.updateEtebarForoshandeh)[itemIndex]);
            }
            return jsonObject.getString("name");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", GetProgramActivity.class.getSimpleName(), "getItemName", "");
            return "";
        }
    }


    @Override
    public void showCompletedGetProgramSuccessfully()
    {
        Log.d("getProgram" , "end get Program : " + System.currentTimeMillis());
        recyclerViewGetProgramItems.smoothScrollToPosition(getProgramItemCount);
        progressBar.setProgress(100);
        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , 100));
        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , String.valueOf(getProgramItemCount) , String.valueOf(getProgramItemCount)));
        imgGetProgramResultFailed.setVisibility(View.GONE);
        imgGetProgramResultSuccess.setVisibility(View.VISIBLE);
        viewRevealAnimator.showNext();
        lblGetProgramResult.setText(getResources().getString(R.string.getProgramCompleted));
        btnGetProgramResultClose.setBackground(getResources().getDrawable(R.drawable.altdlg_btn_bg_success));
        imgGetProgramResultSuccess.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgGetProgramResultSuccess.performClick();
                imgGetProgramResultSuccess.setPressed(true);
                imgGetProgramResultSuccess.invalidate();
            }
        } , 800);

    }

    @Override
    public void showCompletedUpdateKalaModatVosolSuccessfully()
    {
        recyclerViewGetProgramItems.smoothScrollToPosition(getProgramItemCount);
        progressBar.setProgress(100);
        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , 100));
        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , String.valueOf(getProgramItemCount) , String.valueOf(getProgramItemCount)));
        imgGetProgramResultFailed.setVisibility(View.GONE);
        imgGetProgramResultSuccess.setVisibility(View.VISIBLE);
        viewRevealAnimator.showNext();
        lblGetProgramResult.setText(getResources().getString(R.string.updateKalaModatVosolCompleted));
        btnGetProgramResultClose.setBackground(getResources().getDrawable(R.drawable.altdlg_btn_bg_success));
        imgGetProgramResultSuccess.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgGetProgramResultSuccess.performClick();
                imgGetProgramResultSuccess.setPressed(true);
                imgGetProgramResultSuccess.invalidate();
            }
        } , 800);
    }

    @Override
    public void showCompletedUpdateJayezehTakhfifSuccessfully()
    {
        recyclerViewGetProgramItems.smoothScrollToPosition(getProgramItemCount);
        progressBar.setProgress(100);
        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , 100));
        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , String.valueOf(getProgramItemCount) , String.valueOf(getProgramItemCount)));
        imgGetProgramResultFailed.setVisibility(View.GONE);
        imgGetProgramResultSuccess.setVisibility(View.VISIBLE);
        viewRevealAnimator.showNext();
        lblGetProgramResult.setText(getResources().getString(R.string.updateJayezehTakhfifCompleted));
        btnGetProgramResultClose.setBackground(getResources().getDrawable(R.drawable.altdlg_btn_bg_success));
        imgGetProgramResultSuccess.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgGetProgramResultSuccess.performClick();
                imgGetProgramResultSuccess.setPressed(true);
                imgGetProgramResultSuccess.invalidate();
            }
        } , 800);
    }

    @Override
    public void showCompletedUpdateCustomerSuccessfully()
    {
        recyclerViewGetProgramItems.smoothScrollToPosition(getProgramItemCount);
        progressBar.setProgress(100);
        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , 100));
        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , String.valueOf(getProgramItemCount) , String.valueOf(getProgramItemCount)));
        imgGetProgramResultFailed.setVisibility(View.GONE);
        imgGetProgramResultSuccess.setVisibility(View.VISIBLE);
        viewRevealAnimator.showNext();
        lblGetProgramResult.setText(getResources().getString(R.string.updateCustomersCompleted));
        btnGetProgramResultClose.setBackground(getResources().getDrawable(R.drawable.altdlg_btn_bg_success));
        imgGetProgramResultSuccess.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgGetProgramResultSuccess.performClick();
                imgGetProgramResultSuccess.setPressed(true);
                imgGetProgramResultSuccess.invalidate();
            }
        } , 800);
    }


    @Override
    public void showCompletedUpdateParametersSuccessfully()
    {
        recyclerViewGetProgramItems.smoothScrollToPosition(getProgramItemCount);
        progressBar.setProgress(100);
        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , 100));
        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , String.valueOf(getProgramItemCount) , String.valueOf(getProgramItemCount)));
        imgGetProgramResultFailed.setVisibility(View.GONE);
        imgGetProgramResultSuccess.setVisibility(View.VISIBLE);
        viewRevealAnimator.showNext();
        lblGetProgramResult.setText(getResources().getString(R.string.updateParametersCompleted));
        btnGetProgramResultClose.setBackground(getResources().getDrawable(R.drawable.altdlg_btn_bg_success));
        imgGetProgramResultSuccess.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgGetProgramResultSuccess.performClick();
                imgGetProgramResultSuccess.setPressed(true);
                imgGetProgramResultSuccess.invalidate();
            }
        } , 800);
    }


    @Override
    public void showCompletedUpdateEtebarForoshandehSuccessfully()
    {
        recyclerViewGetProgramItems.smoothScrollToPosition(getProgramItemCount);
        progressBar.setProgress(100);
        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , 100));
        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , String.valueOf(getProgramItemCount) , String.valueOf(getProgramItemCount)));
        imgGetProgramResultFailed.setVisibility(View.GONE);
        imgGetProgramResultSuccess.setVisibility(View.VISIBLE);
        viewRevealAnimator.showNext();
        lblGetProgramResult.setText(getResources().getString(R.string.updateEtebarForoshandehCompleted));
        btnGetProgramResultClose.setBackground(getResources().getDrawable(R.drawable.altdlg_btn_bg_success));
        imgGetProgramResultSuccess.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgGetProgramResultSuccess.performClick();
                imgGetProgramResultSuccess.setPressed(true);
                imgGetProgramResultSuccess.invalidate();
            }
        } , 800);
    }

    @Override
    public void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId)
    {
        try
        {
            if (show != null)
            {
                show.dismiss();
            }
        }
        catch (Exception e) {e.printStackTrace();}
        customAlertDialog.showMessageAlert(GetProgramActivity.this, closeActivity, getResources().getString(titleResId), getResources().getString(messageResId), messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showServerMessage(boolean closeActivity, int titleResId, String message, int messageType, int buttonTextResId)
    {
        customAlertDialog.showMessageAlert(GetProgramActivity.this, closeActivity, getResources().getString(titleResId), message, messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(GetProgramActivity.this, getResources().getString(resId), messageType, duration);
    }


    public void showItemStatusList(int getProgramType)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(GetProgramActivity.this);
        View myview = getLayoutInflater().inflate(R.layout.alert_recyclerlist_without_btn , null);
        Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));
        viewRevealAnimator = myview.findViewById(R.id.animator);
        recyclerViewGetProgramItems = myview.findViewById(R.id.recycler_view);
        progressBar = myview.findViewById(R.id.progress);
        lblPassedItemCounter = myview.findViewById(R.id.lblItemCounter);
        lblProgressPercentage = myview.findViewById(R.id.lblProgressPercentage);
        imgGetProgramResultFailed = myview.findViewById(R.id.shineBtnGetProgramResultFailed);
        imgGetProgramResultSuccess = myview.findViewById(R.id.shineBtnGetProgramResultSuccess);
        lblGetProgramResult = myview.findViewById(R.id.lblStatus);
        btnGetProgramResultClose = myview.findViewById(R.id.btnApply);

        List<String> itemsTitle = new ArrayList<>();
        if (getProgramType == Constants.GET_PROGRAM_FULL())
        {
            if (noeMasouliat != 7)
            {
                itemsTitle = Arrays.asList(getResources().getStringArray(R.array.getProgramItems));
            }
            else
            {
                itemsTitle = Arrays.asList(getResources().getStringArray(R.array.getProgramAmargarItems));
            }
        }
        else if (getProgramType == Constants.GET_PROGRAM_UPDATE_KALA())
        {
            itemsTitle = Arrays.asList(getResources().getStringArray(R.array.updateKalaModatVosol));
        }
        else if (getProgramType == Constants.GET_PROGRAM_UPDATE_JAYEZE())
        {
            itemsTitle = Arrays.asList(getResources().getStringArray(R.array.updateJayezehTakhfif));
        }
        else if (getProgramType == Constants.GET_PROGRAM_UPDATE_MOSHTARY())
        {
            itemsTitle = Arrays.asList(getResources().getStringArray(R.array.updateCustomers));
        }
        else if (getProgramType == Constants.GET_PROGRAM_UPDATE_PARAMETERS())
        {
            itemsTitle = Arrays.asList(getResources().getStringArray(R.array.updateParameters));
        }
        else if (getProgramType == Constants.GET_PROGRAM_UPDATE_ETEBAR_FOROSHANDEH())
        {
            itemsTitle = Arrays.asList(getResources().getStringArray(R.array.updateEtebarForoshandeh));
        }

        alertAdapter = new GetProgramItemsStatusAdapter(GetProgramActivity.this , getProgramItemsStatus , itemsTitle);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(GetProgramActivity.this);
        recyclerViewGetProgramItems.setLayoutManager(mLayoutManager);
        recyclerViewGetProgramItems.setItemAnimator(new DefaultItemAnimator());
        recyclerViewGetProgramItems.addItemDecoration(new DividerItemDecoration(GetProgramActivity.this, DividerItemDecoration.VERTICAL));
        recyclerViewGetProgramItems.setAdapter(alertAdapter);
        progressBar.setProgress(0);
        lblGetProgramResult.setTypeface(font);
        btnGetProgramResultClose.setTypeface(font);
        lblProgressPercentage.setText(getResources().getString(R.string.percentageValue , 0));
        lblPassedItemCounter.setText(String.format("%1$s / %2$s" , "0" , String.valueOf(getProgramItemCount)));

        builder.setCancelable(false);
        builder.setView(myview);
        builder.create();

        btnGetProgramResultClose.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (show.getWindow() != null)
                {
                    try
                    {
                        show.dismiss();
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            }
        });

        if (!(GetProgramActivity.this).isFinishing())
        {
            show = builder.show();
            imgGetProgramResultFailed.setFixDialog(show);
            imgGetProgramResultSuccess.setFixDialog(show);

            imgGetProgramResultFailed.setClickable(false);
            imgGetProgramResultFailed.setFocusable(false);
            imgGetProgramResultFailed.setFocusableInTouchMode(false);

            imgGetProgramResultSuccess.setClickable(false);
            imgGetProgramResultSuccess.setFocusable(false);
            imgGetProgramResultSuccess.setFocusableInTouchMode(false);

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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", GetProgramActivity.class.getSimpleName(), "startMVPOps", "");
        }
    }


    private void initialize(GetProgramMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new GetProgramPresenter(view);
            stateMaintainer.put(GetProgramMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", GetProgramActivity.class.getSimpleName(), "initialize", "");
        }
    }


    private void reinitialize(GetProgramMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(GetProgramMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", GetProgramActivity.class.getSimpleName(), "reinitialize", "");
            }
        }
    }


}
