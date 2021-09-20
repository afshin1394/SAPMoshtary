package com.saphamrah.MVP.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.JayezehPrintAdapter;
import com.saphamrah.Adapter.KalaPrintAdapter;
import com.saphamrah.Adapter.MarjoeePrintAdapter;
import com.saphamrah.Adapter.TakhfifPrintAdatper;
import com.saphamrah.BaseMVP.FaktorDetailsMVP;
import com.saphamrah.MVP.Presenter.FaktorDetailsPresenter;
import com.saphamrah.Model.DarkhastFaktorEmzaMoshtaryModel;
import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.StateMaintainer;


import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import me.anwarshahriar.calligrapher.Calligrapher;

public class FaktorDetailsActivity extends AppCompatActivity implements FaktorDetailsMVP.RequiredViewOps
{

    private final String TAG = this.getClass().getSimpleName();
    private StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , FaktorDetailsActivity.this);
    private FaktorDetailsMVP.PresenterOps mPresenter;

    private String sourceActivity; // VerifyCustomerRequestActivity , TemporaryRequestsListActivity , TreasuryListActivity
    private CustomAlertDialog customAlertDialog;
    private FloatingActionMenu fabMenu;
    private View mainView;
    private boolean imagedSaved; // اگر این فیلد true باشد به این معنی است که کاربر بر روی ثبت تصویر کلیک کرده و تصویر فاکتور ثبت شده. از این فیلد برای برگشت به فرم قبلی استفاده میشود و در فرم قبلی (فرم لیست درخواست ها) چک میشود تا اگر مقدار این فیلد برابر true بود، آداپتر بروزرسانی میشود.
    private long ccDarkhastFaktor;
    //TODO
    private ScrollView scrollMain;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faktor_details);
        scrollMain = this.getWindow().getDecorView().findViewById(R.id.scrollMain);


        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        fabMenu = findViewById(R.id.fabMenu);
        FloatingActionButton fabTakePicture = findViewById(R.id.fabScreenShot);
        fabMenu.setVisibility(View.GONE);
        mainView = findViewById(R.id.Main);

        customAlertDialog = new CustomAlertDialog(FaktorDetailsActivity.this);

        startMVPOps();

        imagedSaved = false;
        sourceActivity = "";
        Intent getIntent = getIntent();
        ccDarkhastFaktor = getIntent.getLongExtra("ccDarkhastFaktor" , -1);
        sourceActivity = getIntent.getStringExtra("sourceActivity");


        if (sourceActivity.equalsIgnoreCase("TreasuryListActivity") || sourceActivity.equalsIgnoreCase("TreasuryListOfflineActivity"))
        {
            mPresenter.getFaktorDetails(ccDarkhastFaktor , false);
        }
        else
        {
            mPresenter.getFaktorDetails(ccDarkhastFaktor , true);
        }

        fabTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.close(true);
                Bitmap bmp = getPicture();
                saveScreenshotOfFaktor(ccDarkhastFaktor , bmp);
                byte[] bytes = new PubFunc().new ImageUtils().convertBitmapToByteArray(FaktorDetailsActivity.this , bmp , 70);
                mPresenter.checkUpdateDarkhastFaktorEmza(bytes , ccDarkhastFaktor);
                                scrollMain.removeView(mainView);
                scrollMain.addView(mainView);
                bmp.recycle();
            }
        });

    }


    @Override
    public void onBackPressed()
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("imagedSaved", imagedSaved);
        resultIntent.putExtra("ccDarkhastFaktor", ccDarkhastFaktor);
        setResult(RESULT_OK , resultIntent);
        finish();
    }

    @Override
    public Context getAppContext()
    {
        return FaktorDetailsActivity.this;
    }

    @Override
    public void onGetFaktorDetails(String customerCode, String customerName, float mablaghArzeshAfzoode, double mablaghKol, double mablaghKhalesFaktor, String noeVosol, int modatVosol, String address)
    {
        DecimalFormat formatter = new DecimalFormat("#,###,###");

        TextView lblCustomerCode = findViewById(R.id.txtCodeMoshtary);
        TextView lblCustomerName = findViewById(R.id.txtNameMoshtary);
        TextView lblMablaghBaArzeshAfzoode = findViewById(R.id.txtMablaghBaArzeshAfzoode);
        TextView lblMablaghKhalesFaktor = findViewById(R.id.txtMablaghFaktor);
        TextView lblMablaghKol = findViewById(R.id.txtMablaghKol);
        TextView lblNoeVosol = findViewById(R.id.txtNoeVosol);
        TextView lblModatVosol = findViewById(R.id.txtModatVosol);
        TextView lblAddress = findViewById(R.id.txtAddress);

        lblCustomerCode.setText(String.format(" : %1$s" , customerCode));
        lblCustomerName.setText(String.format(" : %1$s" , customerName));
        lblMablaghBaArzeshAfzoode.setText(String.format(" : %1$s" , formatter.format(mablaghArzeshAfzoode)));
        lblMablaghKhalesFaktor.setText(String.format(" : %1$s" , formatter.format(mablaghKhalesFaktor)));
        lblMablaghKol.setText(String.format(" : %1$s" , formatter.format(mablaghKol)));
        lblNoeVosol.setText(String.format(" : %1$s" , noeVosol));
        lblModatVosol.setText(String.format(" : %1$s" , String.valueOf(modatVosol)));
        lblAddress.setText(address);

        if (sourceActivity.equalsIgnoreCase("TreasuryListActivity") || sourceActivity.equalsIgnoreCase("TreasuryListOfflineActivity"))
        {
            TextView lblMablaghKolTitle = findViewById(R.id.lblMablaghKol);
            TextView lblMablaghKhalesFaktorTitle = findViewById(R.id.lblMablaghFaktor);
            TextView lblMablaghBaArzeshAfzoodeTitle = findViewById(R.id.lblMablaghBaArzeshAfzoode);

            lblMablaghBaArzeshAfzoodeTitle.setVisibility(View.GONE);
            lblMablaghKhalesFaktorTitle.setVisibility(View.GONE);
            lblMablaghKolTitle.setVisibility(View.GONE);

            lblMablaghBaArzeshAfzoode.setVisibility(View.GONE);
            lblMablaghKhalesFaktor.setVisibility(View.GONE);
            lblMablaghKol.setVisibility(View.GONE);
        }

    }

    @Override
    public void onGetKala(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorSatrModels)
    {
        RecyclerView recyclerViewKala = findViewById(R.id.lstKalaInfo);
        LinearLayout layKala = findViewById(R.id.layKala);
        if (kalaDarkhastFaktorSatrModels.size() > 0)
        {
            layKala.setVisibility(View.VISIBLE);
            KalaPrintAdapter adapter = new KalaPrintAdapter(FaktorDetailsActivity.this, kalaDarkhastFaktorSatrModels, 1);
            recyclerViewKala.setLayoutManager(new LinearLayoutManager(FaktorDetailsActivity.this));
            recyclerViewKala.setItemAnimator(new DefaultItemAnimator());
            recyclerViewKala.addItemDecoration(new DividerItemDecoration(FaktorDetailsActivity.this , 0));
            recyclerViewKala.setAdapter(adapter);
        }
        else
        {
            layKala.setVisibility(View.GONE);
        }
    }

    @Override
    public void onGetTakhfif(ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifModels)
    {
        RecyclerView recyclerTakhfif = findViewById(R.id.lstTakhfif);
        LinearLayout layTakhfif = findViewById(R.id.lyTakhfif);
        if (sourceActivity.equalsIgnoreCase("TreasuryListActivity") || sourceActivity.equalsIgnoreCase("TreasuryListOfflineActivity"))
        {
            recyclerTakhfif.setVisibility(View.GONE);
            layTakhfif.setVisibility(View.GONE);
        }
        else
        {
            if (darkhastFaktorTakhfifModels.size() > 0)
            {
                layTakhfif.setVisibility(View.VISIBLE);
                TakhfifPrintAdatper adapter = new TakhfifPrintAdatper(FaktorDetailsActivity.this, darkhastFaktorTakhfifModels, 1);
                recyclerTakhfif.setLayoutManager(new LinearLayoutManager(FaktorDetailsActivity.this));
                recyclerTakhfif.setItemAnimator(new DefaultItemAnimator());
                recyclerTakhfif.addItemDecoration(new DividerItemDecoration(FaktorDetailsActivity.this , 0));
                recyclerTakhfif.setAdapter(adapter);
            }
            else
            {
                layTakhfif.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onGetKalaElamMarjoee(ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels)
    {
        RecyclerView recyclerMarjoee = findViewById(R.id.lstKalaMarjoee);
        LinearLayout layMarjoee = findViewById(R.id.lyMarjoee);
        if (sourceActivity.equalsIgnoreCase("TreasuryListActivity"))
        {
            recyclerMarjoee.setVisibility(View.GONE);
            layMarjoee.setVisibility(View.GONE);
        }
        else
        {
            if (kalaElamMarjoeeModels.size() > 0)
            {
                layMarjoee.setVisibility(View.VISIBLE);
                MarjoeePrintAdapter adapter = new MarjoeePrintAdapter(FaktorDetailsActivity.this, kalaElamMarjoeeModels, 1);
                recyclerMarjoee.setLayoutManager(new LinearLayoutManager(FaktorDetailsActivity.this));
                recyclerMarjoee.setItemAnimator(new DefaultItemAnimator());
                recyclerMarjoee.addItemDecoration(new DividerItemDecoration(FaktorDetailsActivity.this , 0));
                recyclerMarjoee.setAdapter(adapter);
            }
            else
            {
                layMarjoee.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onGetJayezeh(ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehModels)
    {
        RecyclerView recyclerJayezeh = findViewById(R.id.lstKalaJayezeh);
        LinearLayout layJayezeh = findViewById(R.id.lyJayezeh);
        if (sourceActivity.equalsIgnoreCase("TreasuryListActivity") || sourceActivity.equalsIgnoreCase("TreasuryListOfflineActivity"))
        {
            recyclerJayezeh.setVisibility(View.GONE);
            layJayezeh.setVisibility(View.GONE);
        }
        else
        {
            if (darkhastFaktorJayezehModels.size() > 0)
            {
                layJayezeh.setVisibility(View.VISIBLE);
                JayezehPrintAdapter adapter = new JayezehPrintAdapter(FaktorDetailsActivity.this, darkhastFaktorJayezehModels, -1);
                recyclerJayezeh.setLayoutManager(new LinearLayoutManager(FaktorDetailsActivity.this));
                recyclerJayezeh.setItemAnimator(new DefaultItemAnimator());
                recyclerJayezeh.addItemDecoration(new DividerItemDecoration(FaktorDetailsActivity.this , 0));
                recyclerJayezeh.setAdapter(adapter);
            }
            else
            {
                layJayezeh.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onGetEmzaDetail(DarkhastFaktorEmzaMoshtaryModel darkhastFaktorEmzaMoshtaryModel)
    {
        ImageView imgSign = findViewById(R.id.imgEmza);
        if (sourceActivity.equalsIgnoreCase("TreasuryListActivity") || sourceActivity.equalsIgnoreCase("TreasuryListOfflineActivity"))
        {
            imgSign.setVisibility(View.GONE);
        }
        else
        {
            try
            {
                if (darkhastFaktorEmzaMoshtaryModel.getEmzaImage() != null && darkhastFaktorEmzaMoshtaryModel.getEmzaImage().length > 0)
                {
                    imgSign.setImageBitmap(new PubFunc().new ImageUtils().convertByteArrayToBitmap(FaktorDetailsActivity.this , darkhastFaktorEmzaMoshtaryModel.getEmzaImage()));
                }
                else
                {
                    if (sourceActivity.trim().equalsIgnoreCase("TemporaryRequestsListActivity"))
                    {
                        customAlertDialog.showMessageAlert(FaktorDetailsActivity.this, true, "", getResources().getString(R.string.errorCaptureWithoutEmzaMoshtary), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
                    }
                }
            }
            catch (Exception exception)
            {
                customAlertDialog.showMessageAlert(FaktorDetailsActivity.this, true, "", getResources().getString(R.string.errorCaptureWithoutEmzaMoshtary), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
                exception.printStackTrace();
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "FaktorDetailsActivity", "onGetEmzaDetail", "");
            }
        }

        if (sourceActivity.trim().equalsIgnoreCase("VerifyCustomerRequestActivity"))
        {
            fabMenu.setVisibility(View.GONE);
        }
        else if (sourceActivity.trim().equalsIgnoreCase("TemporaryRequestsListActivity"))
        {
            if (darkhastFaktorEmzaMoshtaryModel.getHave_FaktorImage() == 1)
            {
                fabMenu.setVisibility(View.GONE);
            }
            else
            {
                fabMenu.setVisibility(View.VISIBLE);
            }
        }
        else if (sourceActivity.trim().equalsIgnoreCase("TreasuryListActivity") || sourceActivity.trim().equalsIgnoreCase("TreasuryListOfflineActivity"))
        {
            fabMenu.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccessUpdateDarkhastFaktorEmza()
    {
        fabMenu.setVisibility(View.GONE);
        imagedSaved = true;
        showToast(R.string.successfullyDoneOps, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(FaktorDetailsActivity.this, getResources().getString(resId), messageType, duration);
    }


    private Bitmap getPicture()
    {
        mainView.setDrawingCacheEnabled(true);
        mainView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mainView.layout(0, 0, mainView.getMeasuredWidth(), mainView.getMeasuredHeight());
        Bitmap bmpDarkhastImage = getBitmapFromView(mainView, mainView.getHeight(), mainView.getWidth());
        mainView.setDrawingCacheEnabled(false);
        return bmpDarkhastImage;
    }

    private Bitmap getBitmapFromView(View view, int height, int width)
    {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
        {
            canvas.drawColor(Color.WHITE);
            bgDrawable.draw(canvas);
        }
        else
        {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return bitmap;
    }

    public void saveScreenshotOfFaktor(long ccDarkhastFaktor , Bitmap bmpDarkhastImage)
    {
        try
        {
            String mPath = Environment.getExternalStorageDirectory() +  "/SapHamrah/EmzaMoshtaryImage";
            File tempdir = new File(mPath);
            if (!tempdir.exists())
            {
                tempdir.mkdirs();
            }
            mPath = mPath + "/" + ccDarkhastFaktor + ".jpg";
            mainView.setDrawingCacheEnabled(true);
            mainView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            mainView.layout(0, 0, mainView.getMeasuredWidth(), mainView.getMeasuredHeight());
            bmpDarkhastImage = getBitmapFromView(mainView, mainView.getHeight(), mainView.getWidth());
            mainView.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 70;
            bmpDarkhastImage.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            bmpDarkhastImage.recycle();
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "", "FaktorDetailsActivity", "takeScreenshotOfFaktor", "");
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "FaktorDetailsActivity", "startMVPOps", "");
        }
    }


    private void initialize(FaktorDetailsMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new FaktorDetailsPresenter(view);
            stateMaintainer.put(FaktorDetailsMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "FaktorDetailsActivity", "initialize", "");
        }
    }


    private void reinitialize(FaktorDetailsMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(FaktorDetailsMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "FaktorDetailsActivity", "reinitialize", "");
            }
        }
    }


}
