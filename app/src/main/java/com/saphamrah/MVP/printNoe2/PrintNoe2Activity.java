package com.saphamrah.MVP.printNoe2;

import static com.saphamrah.Utils.Constants.BIXOLON;
import static com.saphamrah.Utils.Constants.UROVO;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.JayezehPrintAdapter;
import com.saphamrah.Adapter.KalaPrintAdapter;
import com.saphamrah.Adapter.MarjoeePrintAdapter;
import com.saphamrah.Adapter.TakhfifPrintAdatper;
import com.saphamrah.Application.BaseApplication;
import com.saphamrah.DAO.CodeTypeDAO;
import com.saphamrah.DAO.CompanyDAO;
import com.saphamrah.DAO.DariaftPardakhtDarkhastFaktorPPCDAO;
import com.saphamrah.DAO.DariaftPardakhtPPCDAO;
import com.saphamrah.DAO.DarkhastFaktorDAO;
import com.saphamrah.DAO.DarkhastFaktorEmzaMoshtaryDAO;
import com.saphamrah.DAO.DarkhastFaktorJayezehDAO;
import com.saphamrah.DAO.DarkhastFaktorSatrDAO;
import com.saphamrah.DAO.DarkhastFaktorTakhfifDAO;
import com.saphamrah.DAO.ForoshandehDAO;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.DAO.KalaDAO;
import com.saphamrah.DAO.KalaDarkhastFaktorSatrDAO;
import com.saphamrah.DAO.KalaElamMarjoeeDAO;
import com.saphamrah.DAO.MoshtaryAddressDAO;
import com.saphamrah.DAO.MoshtaryDAO;
import com.saphamrah.DAO.ParameterChildDAO;
import com.saphamrah.DAO.RptJashnvarehDAO;
import com.saphamrah.DAO.SystemConfigTabletDAO;
import com.saphamrah.MVP.View.BixolonDeviceListActivity;
import com.saphamrah.MVP.View.FaktorDetailsActivity;
import com.saphamrah.Model.CodeTypeModel;
import com.saphamrah.Model.DariaftPardakhtDarkhastFaktorPPCModel;
import com.saphamrah.Model.DariaftPardakhtPPCModel;
import com.saphamrah.Model.DarkhastFaktorJayezehModel;
import com.saphamrah.Model.DarkhastFaktorModel;
import com.saphamrah.Model.DarkhastFaktorTakhfifModel;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.ForoshandehModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.LogPPCModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.MoshtaryModel;
import com.saphamrah.Model.ParameterChildModel;
import com.saphamrah.Model.SystemConfigTabletModel;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PrinterUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Service.BluetoothPrintService;
import com.saphamrah.Shared.SelectFaktorShared;
import com.saphamrah.Shared.UserTypeShared;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.UIModel.KalaElamMarjoeeModel;
import com.saphamrah.Utils.BixolonPrinter;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.Printer;
import com.saphamrah.Utils.UrovoPrinter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.anwarshahriar.calligrapher.Calligrapher;

public class PrintNoe2Activity extends AppCompatActivity
{

    TextView txtTitr;
    TextView txtSazman;
    TextView lblSazman;
    TextView txtMarkaz;
    TextView lblMarkaz;
    TextView txtTarikhFaktor;
    TextView lblTarikhFaktor;
    TextView txtShomareFaktor;
    TextView lblShomareFaktor;
    TextView txtNameForoshandeh;
    TextView lblNameForoshandeh;
    TextView txtNameMoshtary;
    TextView lblNameMoshtary;
    TextView txtCodeMoshtary;
    TextView lblCodeMoshtary;
    TextView txtNameTablo;
    TextView lblNameTablo;
    TextView txtAddress;
    TextView lblAddress;
    TextView txtCodePosty;
    TextView lblCodePosty;
    TextView txtTel;
    TextView lblTel;
    TextView lblAghlamFaktor;
    TextView lblTakhfif;
    TextView lblAghlamMarjoee;
    TextView RialMablaghNakhalesFaktor;
    TextView txtMablaghNakhalesFaktor;
    TextView lblMablaghNakhalesFaktor;
    TextView RialMablaghTakhfif;
    TextView txtMablaghTakhfif;
    TextView lblMablaghTakhfif;
    TextView txtMablaghArzeshAfzoode;
    TextView RialMablaghMarjoee;
    TextView txtMablaghMarjoee;
    TextView lblMablaghMarjoee;
    TextView RialMablaghFaktor;
    TextView txtMablaghFaktor;
    TextView lblMablaghFaktor;
    TextView RialMablaghPardakht;
    TextView txtMablaghPardakht;
    TextView lblMablaghPardakht;
    TextView RialMablaghMandehFaktor;
    TextView txtMablaghMandehFaktor;
    TextView lblMablaghMandehFaktor;
    TextView RialMablaghMandehKol;
    TextView txtMablaghMandehKol;
    TextView lblMablaghMandehKol;
    TextView txtTimePrint;
    TextView lblTimePrint;
    TextView lblPayam;
    TextView lblTelForoshandeh;
    TextView txtTelForoshandeh;
    TextView lblTelBazresi;
    TextView txtTelBazresi;
    TextView lblTelMarkaz;
    TextView txtTelMarkaz;
    ImageView imgLogoPrint;

    TextView txtSumKarton;
    TextView txtSumBasteh;
    TextView txtSumAdad;
    TextView txtTitleSarjam;

    LinearLayout layArzeshAfoozde;
    private FloatingActionMenu fabMenu;
    private View MainView;

    long ccDarkhastFaktor;
    int ccMoshtary;
    long TotalTakhfif = 0, TedadAghlamFaktor = 0, TotalMarjoee = 0;
    double MablaghMaliat = 0;
    int DarsadMaliat = 0, DarsadAvarez = 0;
    NumberFormat nf = NumberFormat.getNumberInstance();
    DecimalFormat formatter = new DecimalFormat("#,###,###");
    Bitmap DarkhastImage;
    int NoeMasouliat = 0;
    private boolean imagedSaved; // اگر این فیلد true باشد به این معنی است که کاربر بر روی ثبت تصویر کلیک کرده و تصویر فاکتور ثبت شده. از این فیلد برای برگشت به فرم قبلی استفاده میشود و در فرم قبلی (فرم لیست درخواست ها) چک میشود تا اگر مقدار این فیلد برابر true بود، آداپتر بروزرسانی میشود.
    private boolean haveImage = false;

    private SystemConfigTabletDAO systemconfig_tabletDAO;// = new SystemConfigTabletDAO(this);
    DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(BaseApplication.getContext());

    private CustomAlertDialog customAlertDialog;
    Printer printer;
    private View mainView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        systemconfig_tabletDAO = new SystemConfigTabletDAO(PrintNoe2Activity.this);
        int noeFaktorPrint = systemconfig_tabletDAO.getAll().get(0).getNoeFaktorPrint();
        if (noeFaktorPrint == 1)
        {
            setContentView(R.layout.activity_print_noe2);
        }
        else if (noeFaktorPrint == 2)
        {
            setContentView(R.layout.activity_print_noe2_two);
        }



        imagedSaved = false;
        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        fabMenu = findViewById(R.id.fabMenu);
        mainView = findViewById(R.id.Main);
        FloatingActionButton fabScreenShot = findViewById(R.id.fabScreenShot);
        layArzeshAfoozde = findViewById(R.id.layArzeshAfzoode);

        customAlertDialog = new CustomAlertDialog(PrintNoe2Activity.this);


        ParameterChildDAO childParameterDAO = new ParameterChildDAO(PrintNoe2Activity.this);
        ArrayList<ParameterChildModel> childParameterModels = childParameterDAO.getAllByccParameter(String.valueOf(Constants.CC_PARAMETER_PRINT_CONFIG()));
        for (ParameterChildModel printConfig : childParameterModels)
        {
            if (printConfig.getCcParameterChild() == Constants.CC_CHILD_PRINT_SHOW_ARZESH_AFZOODE())
            {
                if (printConfig.getValue().equals("0"))
                {
                    layArzeshAfoozde.setVisibility(View.GONE);
                }
                else if (printConfig.getValue().equals("1"))
                {
                    layArzeshAfoozde.setVisibility(View.VISIBLE);
                }
            }
        }



        //--------------Noe Masouliat---------------------------
        NoeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(PrintNoe2Activity.this).getIsSelect());

        setPrinter();
        try
        {
            MainView = findViewById(R.id.Main);
            imgLogoPrint = findViewById(R.id.imgLogoPrint);
            txtTitr = findViewById(R.id.txtTitr);
            txtSazman = findViewById(R.id.txtSazman);
            lblSazman = findViewById(R.id.lblSazman);
            txtMarkaz = findViewById(R.id.txtMarkaz);
            lblMarkaz = findViewById(R.id.lblMarkaz);
            txtTarikhFaktor = findViewById(R.id.txtTarikhFaktor);
            lblTarikhFaktor = findViewById(R.id.lblTarikhFaktor);
            txtShomareFaktor = findViewById(R.id.txtShomareFaktor);
            lblShomareFaktor = findViewById(R.id.lblShomareFaktor);
            txtNameForoshandeh = findViewById(R.id.txtNameForoshandeh);
            lblNameForoshandeh = findViewById(R.id.lblNameForoshandeh);
            txtNameMoshtary = findViewById(R.id.txtNameMoshtary);
            lblNameMoshtary = findViewById(R.id.lblNameMoshtary);
            txtCodeMoshtary = findViewById(R.id.txtCodeMoshtary);
            lblCodeMoshtary = findViewById(R.id.lblCodeMoshtary);
            txtNameTablo = findViewById(R.id.txtNameTablo);
            lblNameTablo = findViewById(R.id.lblNameTablo);
            txtAddress = findViewById(R.id.txtAddress);
            lblAddress = findViewById(R.id.lblAddress);
            txtCodePosty = findViewById(R.id.txtCodePosty);
            lblCodePosty = findViewById(R.id.lblCodePosty);
            txtTel = findViewById(R.id.txtTel);
            lblTel = findViewById(R.id.lblTel);
            lblAghlamFaktor = findViewById(R.id.lblAghlamFaktor);
            lblTakhfif = findViewById(R.id.lblTakhfif);
            lblAghlamMarjoee = findViewById(R.id.lblAghlamMarjoee);
            RialMablaghNakhalesFaktor = findViewById(R.id.RialMablaghNakhalesFaktor);
            txtMablaghNakhalesFaktor = findViewById(R.id.txtMablaghNakhalesFaktor);
            lblMablaghNakhalesFaktor = findViewById(R.id.lblMablaghNakhalesFaktor);
            RialMablaghTakhfif = findViewById(R.id.RialMablaghTakhfif);
            txtMablaghTakhfif = findViewById(R.id.txtMablaghTakhfif);
            lblMablaghTakhfif = findViewById(R.id.lblMablaghTakhfif);
            txtMablaghArzeshAfzoode = findViewById(R.id.txtMablaghArzeshAfzoode);
            RialMablaghMarjoee = findViewById(R.id.RialMablaghMarjoee);
            txtMablaghMarjoee = findViewById(R.id.txtMablaghMarjoee);
            lblMablaghMarjoee = findViewById(R.id.lblMablaghMarjoee);
            RialMablaghFaktor = findViewById(R.id.RialMablaghFaktor);
            txtMablaghFaktor = findViewById(R.id.txtMablaghFaktor);
            lblMablaghFaktor = findViewById(R.id.lblMablaghFaktor);
            RialMablaghPardakht = findViewById(R.id.RialMablaghPardakht);
            txtMablaghPardakht = findViewById(R.id.txtMablaghPardakht);
            lblMablaghPardakht = findViewById(R.id.lblMablaghPardakht);
            RialMablaghMandehFaktor = findViewById(R.id.RialMablaghMandehFaktor);
            txtMablaghMandehFaktor = findViewById(R.id.txtMablaghMandehFaktor);
            lblMablaghMandehFaktor = findViewById(R.id.lblMablaghMandehFaktor);
            RialMablaghMandehKol = findViewById(R.id.RialMablaghMandehKol);
            txtMablaghMandehKol = findViewById(R.id.txtMablaghMandehKol);
            lblMablaghMandehKol = findViewById(R.id.lblMablaghMandehKol);
            txtTimePrint = findViewById(R.id.txtTimePrint);
            lblTimePrint = findViewById(R.id.lblTimePrint);
            lblTelForoshandeh = findViewById(R.id.lblTelForoshandeh);
            txtTelForoshandeh = findViewById(R.id.txtTelForoshandeh);
            lblTelBazresi = findViewById(R.id.lblTelBazresi);
            txtTelBazresi = findViewById(R.id.txtTelBazresi);
            lblTelMarkaz = findViewById(R.id.lblTelMarkaz);
            txtTelMarkaz = findViewById(R.id.txtTelMarkaz);

            RecyclerView lstKalaInfo;

            Intent intent = getIntent();
            ccDarkhastFaktor = intent.getLongExtra("ccDarkhastFaktor", 0);
            ccMoshtary = intent.getIntExtra("ccMoshtary", 0);

            haveImage = darkhastFaktorEmzaMoshtaryDAO.haveImage(ccDarkhastFaktor);
            if (haveImage){
                fabMenu.setVisibility(View.GONE);
            }
            //---------------------------Company--------------------------

            if (Constants.CURRENT_VERSION_TYPE() == 6)
            {
                imgLogoPrint.setImageResource(R.drawable.logo_print);
            }
            else if (Constants.CURRENT_VERSION_TYPE() == 0 || Constants.CURRENT_VERSION_TYPE() == 1 || Constants.CURRENT_VERSION_TYPE() == 2)
            {
                CompanyDAO companyDAO = new CompanyDAO(PrintNoe2Activity.this);
                byte[] bitmapdata = null;

                bitmapdata = companyDAO.getByccCompany(1).getLogoPhotoPrint();//mihan
                Bitmap bmpLogoPrint = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                imgLogoPrint.setImageBitmap(bmpLogoPrint);
            }
            else if(Constants.CURRENT_VERSION_TYPE() == 4 || Constants.CURRENT_VERSION_TYPE() == 5 || Constants.CURRENT_VERSION_TYPE() == 7)
            {
                CompanyDAO companyDAO = new CompanyDAO(PrintNoe2Activity.this);
                byte[] bitmapdata = null;

                bitmapdata = companyDAO.getByccCompany(2).getLogoPhotoPrint();//mihan
                Bitmap bmpLogoPrint = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
                imgLogoPrint.setImageBitmap(bmpLogoPrint);
            }

            //---------------------------System Config--------------------------

            ParameterChildDAO parameterChildDAO = new ParameterChildDAO(PrintNoe2Activity.this);
            try
            {
                DarsadMaliat = Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_MALIAT));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                insertLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), "onCreate", "Maliat");
            }
            try
            {
                DarsadAvarez = Integer.parseInt(parameterChildDAO.getValueByccChildParameter(Constants.CC_CHILD_AVAREZ));
            }
            catch (Exception e)
            {
                e.printStackTrace();
                insertLogToDB(LogPPCModel.LOG_EXCEPTION, e.toString(), "onCreate", "Avarez");
            }

            //---------------------------Darkhast Faktor------------------------

            DarkhastFaktorDAO darkhastfaktorDAO = new DarkhastFaktorDAO(PrintNoe2Activity.this);
            DarkhastFaktorModel darkhastfaktor = darkhastfaktorDAO.getByccDarkhastFaktor(ccDarkhastFaktor,ccMoshtary);

            ForoshandehMamorPakhshDAO foroshandehmamorpakhshDAO = new ForoshandehMamorPakhshDAO(PrintNoe2Activity.this);
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModel = foroshandehmamorpakhshDAO.getByccForoshandeh(darkhastfaktor.getCcForoshandeh());

            txtTarikhFaktor.setText(new PubFunc().new DateUtils().todayDateWithSlash(PrintNoe2Activity.this));
            if (darkhastfaktor.getExtraProp_ShomarehDarkhast() != null)
            {
                txtShomareFaktor.setText(darkhastfaktor.getExtraProp_ShomarehDarkhast());
            }
            else
            {
                txtShomareFaktor.setText(createShomareDarkhast(darkhastfaktor.getShomarehDarkhast(), darkhastfaktor.getCcForoshandeh(), NoeMasouliat, foroshandehMamorPakhshModel));
            }

            //---------------------------ForoshandehMamorPakhsh----------------------------
            ForoshandehMamorPakhshModel foroshandehMamorPakhshModelMamorPakhsh = foroshandehmamorpakhshDAO.getIsSelect();
            if (foroshandehMamorPakhshModelMamorPakhsh != null)
            {
                txtTelForoshandeh.setText(foroshandehMamorPakhshModelMamorPakhsh.getTelephoneForoshandehMamorPakhsh());
                txtTelMarkaz.setText(foroshandehMamorPakhshModelMamorPakhsh.getTelephoneShobeh());
            }

            if (NoeMasouliat == 1 || NoeMasouliat == 2 || NoeMasouliat == 3)
            {
                if (foroshandehMamorPakhshModel != null)
                {
                    txtNameForoshandeh.setText(foroshandehMamorPakhshModel.getFullName());
                    txtSazman.setText(foroshandehMamorPakhshModel.getNameSazmanForosh());
                    txtMarkaz.setText(foroshandehMamorPakhshModel.getNameMarkazForosh());
                }
            }
            else if (NoeMasouliat == 4 || NoeMasouliat == 5)
            {
                ForoshandehDAO foroshandehDAO = new ForoshandehDAO(PrintNoe2Activity.this);
                ForoshandehModel foroshandehModel = foroshandehDAO.getByccForoshande(darkhastfaktor.getCcForoshandeh());
                if (foroshandehModel != null)
                {
                    txtNameForoshandeh.setText(foroshandehModel.getFullNameForoshandeh());
                    txtSazman.setText(foroshandehModel.getNameSazmanForosh());
                    txtMarkaz.setText(foroshandehModel.getNameMarkazForosh());
                }
            }


            //---------------------------Moshtary-------------------------------
            MoshtaryDAO moshtaryDAO = new MoshtaryDAO(PrintNoe2Activity.this);
            MoshtaryModel moshtary = moshtaryDAO.getByccMoshtary(darkhastfaktor.getCcMoshtary());

            txtCodeMoshtary.setText(moshtary.getCodeMoshtary());
            txtNameMoshtary.setText(moshtary.getNameMoshtary());
            txtNameTablo.setText(moshtary.getNameTablo());

            //--------------------------Moshtary Address------------------------

            MoshtaryAddressDAO moshtaryaddressDAO = new MoshtaryAddressDAO(PrintNoe2Activity.this);
            MoshtaryAddressModel moshtaryAddress = moshtaryaddressDAO.getByccMoshtaryAndccAddress(darkhastfaktor.getCcMoshtary(), darkhastfaktor.getCcAddressMoshtary());

            txtAddress.setText(moshtaryAddress.getAddress());
            if (moshtaryAddress.getCodePosty().trim().length() != 0)
            {
                txtCodePosty.setText(moshtaryAddress.getCodePosty());
                txtCodePosty.setVisibility(View.VISIBLE);
                lblCodePosty.setVisibility(View.VISIBLE);
            }
            else
            {
                txtCodePosty.setVisibility(View.GONE);
                lblCodePosty.setVisibility(View.GONE);
            }

            txtTel.setText(moshtaryAddress.getTelephone());

            //--------------------------KalaFaktor -----------------------------

            lstKalaInfo = findViewById(R.id.lstKalaInfo);
            ArrayList<KalaDarkhastFaktorSatrModel> items = new KalaDarkhastFaktorSatrDAO(PrintNoe2Activity.this).getByccDarkhast(darkhastfaktor.getCcDarkhastFaktor());
            KalaPrintAdapter adapter = new KalaPrintAdapter(PrintNoe2Activity.this, items, noeFaktorPrint);
            lstKalaInfo.setLayoutManager(new LinearLayoutManager(PrintNoe2Activity.this));
            lstKalaInfo.setItemAnimator(new DefaultItemAnimator());
            lstKalaInfo.addItemDecoration(new DividerItemDecoration(PrintNoe2Activity.this, 0));
            lstKalaInfo.setAdapter(adapter);

            int size = items.size();
            int TedadKarton = 0, TedadBasteh = 0, TedadAdd = 0;
            int SumKarton = 0, SumBasteh = 0, SumAdd = 0;
            long sumMablaghDarkhast = 0;
            for (int i = 0; i < size; i++)
            {
                KalaDAO kalaDao = new KalaDAO(PrintNoe2Activity.this);
                KalaModel kala = kalaDao.getByccKalaCode(items.get(i).getCcKalaCode());

                int TedadDarKarton = kala.getTedadDarKarton();
                int TedadDarBasteh = kala.getTedadDarBasteh();
                int MandehTedad = items.get(i).getTedad3();
                if (MandehTedad >= TedadDarKarton)
                {
                    TedadKarton = MandehTedad / TedadDarKarton;
                    MandehTedad -= (TedadKarton * TedadDarKarton);
                    SumKarton += TedadKarton;
                }
                if (TedadDarKarton != TedadDarBasteh & MandehTedad >= TedadDarBasteh)
                {
                    TedadBasteh = MandehTedad / TedadDarBasteh;
                    MandehTedad -= (TedadBasteh * TedadDarBasteh);
                    SumBasteh += TedadBasteh;
                }

                TedadAdd = MandehTedad;
                SumAdd += TedadAdd;
                //sumMablaghDarkhast += items.get(i).getMablaghForosh() * items.get(i).getTedad3();
            }
            sumMablaghDarkhast = new DarkhastFaktorSatrDAO(PrintNoe2Activity.this).getSumMablaghFaktorByccDarkhast(ccDarkhastFaktor);
            Log.d("print" , "sumMablaghDarkhast : " + sumMablaghDarkhast);

            if (noeFaktorPrint == 1)
            {
                txtTitleSarjam = findViewById(R.id.txtTitleSarjam);
                txtSumKarton = findViewById(R.id.txtSumKarton);
                txtSumBasteh = findViewById(R.id.txtSumBasteh);
                txtSumAdad = findViewById(R.id.txtSumAdad);
                txtSumKarton.setText(nf.format(SumKarton));
                txtSumBasteh.setText(nf.format(SumBasteh));
                txtSumAdad.setText(nf.format(SumAdd));
            }
            txtMablaghNakhalesFaktor.setText(formatter.format(sumMablaghDarkhast));

            //PubFuncs.ListViewNoHeight(lstKalaInfo);

            for (int i = 0; i < items.size(); i++)
            {
                TedadAghlamFaktor += items.get(i).getTedad3();
            }

            //--------------------------------- Takhfifat ---------------------------------------------------------
            LinearLayout lyTakhfif = (LinearLayout) findViewById(R.id.lyTakhfif);
            RecyclerView lstTakhfif = findViewById(R.id.lstTakhfif);
            DarkhastFaktorTakhfifDAO darkhastFaktorTakhfifDAO = new DarkhastFaktorTakhfifDAO(PrintNoe2Activity.this);
            ArrayList<DarkhastFaktorTakhfifModel> darkhastFaktorTakhfifs = darkhastFaktorTakhfifDAO.getByccDarkhastFaktor(darkhastfaktor.getCcDarkhastFaktor());

            for (int i = 0; i < darkhastFaktorTakhfifs.size(); i++)
            {
                TotalTakhfif += (int) darkhastFaktorTakhfifs.get(i).getMablaghTakhfif();
            }

            if(darkhastFaktorTakhfifs.size() != 0)
            {
                TakhfifPrintAdatper takhfifAdapter = new TakhfifPrintAdatper(PrintNoe2Activity.this, darkhastFaktorTakhfifs, noeFaktorPrint);
                lstTakhfif.setLayoutManager(new LinearLayoutManager(PrintNoe2Activity.this));
                lstTakhfif.setItemAnimator(new DefaultItemAnimator());
                lstTakhfif.addItemDecoration(new DividerItemDecoration(PrintNoe2Activity.this , 0));
                lstTakhfif.setAdapter(takhfifAdapter);
            }
            else
            {
                lyTakhfif.setVisibility(View.GONE);
            }
            //------------------------------------- Marjoee -----------------------------------------------------
            LinearLayout lyMarjooe = findViewById(R.id.lyMarjoee);
            RecyclerView lstKalaMarjoee = findViewById(R.id.lstKalaMarjoee);
            KalaElamMarjoeeDAO kalaElamMarjoeeDAO = new KalaElamMarjoeeDAO(PrintNoe2Activity.this);
            ArrayList<KalaElamMarjoeeModel> kalaElamMarjoeeModels = kalaElamMarjoeeDAO.getByccDarkhastFaktor(darkhastfaktor.getCcDarkhastFaktor(),darkhastfaktor.getCcMoshtary());
            for (int i = 0; i < kalaElamMarjoeeModels.size(); i++)
            {
                TotalMarjoee += (int) kalaElamMarjoeeModels.get(i).getFee() * kalaElamMarjoeeModels.get(i).getTedad3();
            }
            if (kalaElamMarjoeeModels.size() != 0)
            {
                MarjoeePrintAdapter marjoeeAdapter = new MarjoeePrintAdapter(PrintNoe2Activity.this, kalaElamMarjoeeModels, noeFaktorPrint);
                lstKalaMarjoee.setLayoutManager(new LinearLayoutManager(PrintNoe2Activity.this));
                lstKalaMarjoee.setItemAnimator(new DefaultItemAnimator());
                lstKalaMarjoee.addItemDecoration(new DividerItemDecoration(PrintNoe2Activity.this, 0));
                lstKalaMarjoee.setAdapter(marjoeeAdapter);
            }
            else
            {
                lyMarjooe.setVisibility(View.GONE);
            }

            //------------------------------------- Jayezeh -----------------------------------------------------
            LinearLayout lyJayezeh = findViewById(R.id.lyJayezeh);
            RecyclerView lstKalaJayezeh = findViewById(R.id.lstKalaJayezeh);
            DarkhastFaktorJayezehDAO darkhastFaktorJayezehDAO = new DarkhastFaktorJayezehDAO(PrintNoe2Activity.this);
            ArrayList<DarkhastFaktorJayezehModel> darkhastFaktorJayezehs = darkhastFaktorJayezehDAO.getByccDarkhastFaktorAndCodeNoe(darkhastfaktor.getCcDarkhastFaktor() , DarkhastFaktorJayezehModel.CodeNoeJayezehAuto());
            if(darkhastFaktorJayezehs.size()!=0)
            {
                JayezehPrintAdapter jayezehAdapter = new JayezehPrintAdapter(PrintNoe2Activity.this , darkhastFaktorJayezehs, noeFaktorPrint);
                lstKalaJayezeh.setLayoutManager(new LinearLayoutManager(PrintNoe2Activity.this));
                lstKalaJayezeh.setItemAnimator(new DefaultItemAnimator());
                lstKalaJayezeh.addItemDecoration(new DividerItemDecoration(PrintNoe2Activity.this , 0));
                lstKalaJayezeh.setAdapter(jayezehAdapter);
            }
            else
            {
                lyJayezeh.setVisibility(View.GONE);
            }
            //----------------------------------------------------------------------------------------------------

            //MablaghMaliat =  (Math.round(darkhastfaktor.getMablaghKolFaktor()) - TotalTakhfif) * (DarsadMaliat + DarsadAvarez) / 100;
            MablaghMaliat = darkhastfaktor.getExtraProp_MablaghArzeshAfzoodeh();
            //txtMablaghNakhalesFaktor.setText(nf.format(Math.round(darkhastfaktor.getMablaghKolFaktor())));
            //txtMablaghNakhalesFaktor.setText(nf.format(Math.round(darkhastfaktor.getMablaghKolFaktor() + MablaghMaliat)));
            txtMablaghNakhalesFaktor.setText(formatter.format(darkhastfaktor.getMablaghKolFaktor()));
            Log.d("print" , "darkhastfaktor.getMablaghKolFaktor : " + darkhastfaktor.getMablaghKolFaktor());

            if (TotalTakhfif != 0)
            {
                txtMablaghTakhfif.setText(formatter.format(Math.round(TotalTakhfif)));
            }
            else
            {
                RialMablaghTakhfif.setVisibility(View.GONE);
                txtMablaghTakhfif.setVisibility(View.GONE);
                lblMablaghTakhfif.setVisibility(View.GONE);
            }

            if (TotalMarjoee != 0)
                txtMablaghMarjoee.setText(formatter.format(Math.round(TotalMarjoee)));
            else
            {
                RialMablaghMarjoee.setVisibility(View.GONE);
                txtMablaghMarjoee.setVisibility(View.GONE);
                lblMablaghMarjoee.setVisibility(View.GONE);
            }
            //txtMablaghFaktor.setText(nf.format(Math.round(darkhastfaktor.getExtraProp_MablaghNahaeeFaktor())-TotalMarjoee));
            long mablaghJayezeh = 0;
            mablaghJayezeh = new DarkhastFaktorSatrDAO(PrintNoe2Activity.this).getSumMablaghJayezehByccDarkhast(ccDarkhastFaktor);
            //long mablaghGhabelePardakht = Math.round(sumMablaghDarkhast - (TotalTakhfif + TotalMarjoee) + darkhastfaktor.getExtraProp_MablaghArzeshAfzoodeh() + mablaghJayezeh);
            //long mablaghGhabelePardakht = sumMablaghDarkhast - (TotalTakhfif + TotalMarjoee) + (long)darkhastfaktor.getExtraProp_MablaghArzeshAfzoodeh() + mablaghJayezeh;
            long mablaghGhabelePardakht = sumMablaghDarkhast - (TotalTakhfif + TotalMarjoee) + (long)darkhastfaktor.getExtraProp_MablaghArzeshAfzoodeh();
            txtMablaghFaktor.setText(formatter.format(mablaghGhabelePardakht));
            txtMablaghArzeshAfzoode.setText(formatter.format(darkhastfaktor.getExtraProp_MablaghArzeshAfzoodeh()));

            //--------------------------DariaftPardakhtDarkhastFaktorPPC------------------------------------------
            long SumVosol = 0, MablaghMandehKol = 0;
            DariaftPardakhtDarkhastFaktorPPCDAO dariaftpardakhtdarkhastfaktorDAO = new DariaftPardakhtDarkhastFaktorPPCDAO(PrintNoe2Activity.this);
            List<DariaftPardakhtDarkhastFaktorPPCModel> dariaftpardakhtfaktors = dariaftpardakhtdarkhastfaktorDAO.getByccDarkhastFaktor(darkhastfaktor.getCcDarkhastFaktor());
            for (DariaftPardakhtDarkhastFaktorPPCModel dariaftpardakhtfaktor : dariaftpardakhtfaktors)
            {
                Log.d("mablagh" , "dariaftpardakhtfaktor : " + dariaftpardakhtfaktor.toString());
                //SumVosol+=dariaftpardakhtfaktor.getMablagh();
                SumVosol += dariaftpardakhtfaktor.getMablaghDariaftPardakht();
            }

            //--------------------------DariaftPardakhtPPC------------------------------------------
            DariaftPardakhtPPCDAO dariaftpardakhtDAO = new DariaftPardakhtPPCDAO(PrintNoe2Activity.this);
            SelectFaktorShared selectFaktorShared = new SelectFaktorShared(PrintNoe2Activity.this);
            int ccMoshtary = selectFaktorShared.getInt(selectFaktorShared.getCcMoshtary(), -1);
            List<DariaftPardakhtPPCModel> dariaftpardakhts = dariaftpardakhtDAO.getByccMoshtary(ccMoshtary);
            for (DariaftPardakhtPPCModel dariaftpardakhtfaktor : dariaftpardakhts)
            {
                MablaghMandehKol += dariaftpardakhtfaktor.getMablagh();
            }

            long MablaghMandehFaktor = mablaghGhabelePardakht - (SumVosol - TotalMarjoee);//Math.round(darkhastfaktor.getExtraProp_MablaghNahaeeFaktor())-SumVosol;
            MablaghMandehFaktor = MablaghMandehFaktor < 0 ? 0 : MablaghMandehFaktor;

            txtMablaghPardakht.setText(formatter.format(SumVosol - TotalMarjoee));
            txtMablaghMandehFaktor.setText(formatter.format(MablaghMandehFaktor));
            txtMablaghMandehKol.setText(formatter.format(MablaghMandehKol));
            RialMablaghMandehKol.setVisibility(View.GONE);
            txtMablaghMandehKol.setVisibility(View.GONE);
            lblMablaghMandehKol.setVisibility(View.GONE);


            //------------------------------------- Emza ------------------------------------------------------------
            DarkhastFaktorEmzaMoshtaryDAO darkhastFaktorEmzaMoshtaryDAO = new DarkhastFaktorEmzaMoshtaryDAO(PrintNoe2Activity.this);
            Bitmap bmp = darkhastFaktorEmzaMoshtaryDAO.getEmzaImageByccDarkhastFaktor(darkhastfaktor.getCcDarkhastFaktor());
            //ByteArrayOutputStream out = new ByteArrayOutputStream();

            bmp = new PubFunc().new ImageUtils().getResizedBitmap(bmp, 250);

            byte[] bytes = new PubFunc().new ImageUtils().convertBitmapToByteArray(PrintNoe2Activity.this, bmp, 100);


            /*byte[] bytes = new PubFunc().new ImageUtils().convertBitmapToByteArray(PrintActivity.this , bmp , 10);
            bmp = new PubFunc().new ImageUtils().convertByteArrayToBitmap(PrintActivity.this , bytes);*/

            //bmp.compress(Bitmap.CompressFormat.JPEG, 10, out);
            /*BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;*/

            //Bitmap CompressPic = BitmapFactory.decodeStream(new ByteArrayInputStream(out.toByteArray()), new Rect(), options);
            Bitmap CompressPic = new PubFunc().new ImageUtils().convertByteArrayToBitmap(PrintNoe2Activity.this, bytes);

            bmp.recycle();

            ImageView imgEmza = (ImageView) findViewById(R.id.imgEmza);
            imgEmza.setImageBitmap(CompressPic);

            String txtMatn = "";

            if (NoeMasouliat == 2 | NoeMasouliat == 4 | NoeMasouliat == 5)
                txtMatn = "اینجانب " + moshtary.getNameMoshtary() + " , " + " درخواست " + SumKarton + " کارتن، " + SumBasteh + " بسته، " + SumAdd + " عدد کالا  به مبلغ  " + formatter.format(mablaghGhabelePardakht) + "ریال " + " را تحویل گرفتم.";
            else
                txtMatn = "اینجانب " + moshtary.getNameMoshtary() + " , " + " درخواست " + SumKarton + " کارتن، " + SumBasteh + " بسته، " + SumAdd + " عدد کالا  به مبلغ  " + formatter.format(mablaghGhabelePardakht) + "ریال " + " را تایید می نمایم.";

            TextView txtEmza = findViewById(R.id.txtEmza);
            //txtEmza.setTypeface(font);
            txtEmza.setText(txtMatn);

            //---------------------------------Jashnvareh-------------------------
            TextView txtJashnvareh = findViewById(R.id.txtJashnvareh);
            String txtMatnJashnvareh = "";
            Double EmtiazMoshtary =0.0;
            RptJashnvarehDAO rptJashnvarehDAO = new RptJashnvarehDAO(PrintNoe2Activity.this);
            EmtiazMoshtary = rptJashnvarehDAO.getEmtiazJashnvarehByccMoshtary(moshtary.getCcMoshtary());
            if(EmtiazMoshtary>0) {
                txtMatnJashnvareh = "تعداد کد قرعه کشی اخذ شده شما تا قبل از این فاکتور " + Math.round(EmtiazMoshtary) + " می باشد.";
                txtJashnvareh.setText(txtMatnJashnvareh);
            }


            //------------------------------------------------------------------------------------------------------
            Date currentLocalTime = Calendar.getInstance().getTime();
            DateFormat date = new SimpleDateFormat("HH:mm:ss");
            txtTimePrint.setText(new PubFunc().new DateUtils().todayDateWithSlash(PrintNoe2Activity.this) + " - " + date.format(currentLocalTime));

            String telephoneBazresi = new ParameterChildDAO(PrintNoe2Activity.this).getValueByccChildParameter(Constants.CC_CHILD_TELEPHONE_BAZRESI());
            if (telephoneBazresi != null && !telephoneBazresi.trim().equals(""))
            {
                txtTelBazresi.setText(telephoneBazresi);
            }
            else
            {
                LinearLayout layTelBazresi = findViewById(R.id.layTelBazresi);
                layTelBazresi.setVisibility(View.GONE);
            }

            takeScreenshotOfFaktor(darkhastfaktor.getCcDarkhastFaktor());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(PrintNoe2Activity.this, Constants.LOG_EXCEPTION(), e.toString(), "", "PrintActivity", "onCreate", "");
        }


        fabScreenShot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                Bitmap bmp = getPicture();
                saveScreenshotOfFaktor(ccDarkhastFaktor , bmp);
                byte[] bytes = new PubFunc().new ImageUtils().convertBitmapToByteArray(PrintNoe2Activity.this , bmp , 70);
                if (bytes.length > 0 && ccDarkhastFaktor > 0)
                {
                    if (darkhastFaktorEmzaMoshtaryDAO.updateDarkhastFaktorImage(bytes, ccDarkhastFaktor))
                    {
                        fabMenu.setVisibility(View.GONE);
                        imagedSaved = true;
                        customAlertDialog.showToast(PrintNoe2Activity.this, getResources().getString(R.string.successGetScreenShot), Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
                    }
                    else
                    {
                        customAlertDialog.showToast(PrintNoe2Activity.this, getResources().getString(R.string.errorGetScreenShot), Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
                    }
                }
                else
                {
                    customAlertDialog.showToast(PrintNoe2Activity.this, getResources().getString(R.string.errorGetScreenShot), Constants.INFO_MESSAGE(), Constants.DURATION_LONG());

                }

                bmp.recycle();
            }
        });



        Log.d("print", "ccDarkhastFaktor : " + ccDarkhastFaktor);
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



    private void setPrinter()
    {
        ArrayList<SystemConfigTabletModel> systemConfigTabletModels = systemconfig_tabletDAO.getAll();
        if (systemConfigTabletModels.size() > 0)
        {
            if (systemConfigTabletModels.get(0).getNamePrinter() == 0)
            {
                printer = new BixolonPrinter(PrintNoe2Activity.this , ccDarkhastFaktor);
            }
            else if (systemConfigTabletModels.get(0).getNamePrinter() == 1)
            {
                //printer = new WoosimPrinter(PrintActivity.this , ccDarkhastFaktor);
            }
            else if (systemConfigTabletModels.get(0).getNamePrinter() == 2)
            {
                printer = new UrovoPrinter(PrintNoe2Activity.this , ccDarkhastFaktor);
                String printerStatus = printer.getPrinterStateMessage();
                customAlertDialog.showToast(PrintNoe2Activity.this, printerStatus, Constants.INFO_MESSAGE(), Constants.DURATION_LONG());
            }
        }
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
        }
    }

    private String createShomareDarkhast(int shomarehDarkhast, int ccForoshandeh, int noeMasouliat, ForoshandehMamorPakhshModel foroshandehMamorPakhshModel)
    {
        ForoshandehDAO foroshandehDAO = new ForoshandehDAO(PrintNoe2Activity.this);
        String createShomareDarkhast = "";
        String pattern = "";
        String day = "", month = "", year = "", codeForoshandeh = "", codeMarkazForosh = "", radif = "";

        ForoshandehModel foroshandehModel = foroshandehDAO.getByccForoshande(ccForoshandeh);

        int[] persianDate = new PubFunc().new DateUtils().splittedTodayPersianDate(PrintNoe2Activity.this);

        CodeTypeDAO codetypeDAO = new CodeTypeDAO(PrintNoe2Activity.this);
        ArrayList<CodeTypeModel> codetypes = codetypeDAO.getAll();
        if (codetypes.size() > 0)
        {
            pattern = codetypes.get(0).getPattern();
        }

        String[] patternSplit = pattern.split(",");

        if (shomarehDarkhast == 0)
        {
            shomarehDarkhast = 1;
        }

        for (int i = 0; i < patternSplit.length; i++)
        {
            String PatternChild = patternSplit[i];
            switch (PatternChild)
            {
                case "{Day}":
                    day = String.valueOf(persianDate[2]);
                    createShomareDarkhast += day + "/";
                    break;
                case "{Month}":
                    month = String.valueOf(persianDate[1]);
                    createShomareDarkhast += month + "/";
                    break;
                case "{Year}":
                    year = String.valueOf(persianDate[0]);
                    createShomareDarkhast += year.substring(2) + "/";
                    break;
                case "{CodeForoshandeh}":
                    if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3  || noeMasouliat == 6 || noeMasouliat ==8)
                    {
                        codeForoshandeh = foroshandehMamorPakhshModel.getCodeForoshandeh();
                    }
                    else if (noeMasouliat == 4 || noeMasouliat == 5)
                    {
                        codeForoshandeh = foroshandehModel.getCodeForoshandeh();
                    }
                    createShomareDarkhast += codeForoshandeh + "/";
                    break;
                case "{CodeMarkazForosh}":
                    if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3 || noeMasouliat == 6 || noeMasouliat ==8)
                    {
                        codeMarkazForosh = String.valueOf(foroshandehMamorPakhshModel.getCcMarkazSazmanForoshSakhtarForosh());
                    }
                    else if (noeMasouliat == 4 || noeMasouliat == 5)
                    {
                        codeMarkazForosh = String.valueOf(foroshandehModel.getCcMarkazSazmanForoshSakhtarForosh());
                    }
                    createShomareDarkhast += codeMarkazForosh + "/";
                    break;
                case "{Radif}":
                    radif = String.valueOf(shomarehDarkhast);
                    createShomareDarkhast += radif;
                    break;
            }
        }
        return createShomareDarkhast;
    }

    public void takeScreenshotOfFaktor(long ccDarkhastFaktor)
    {
        try
        {
            String mPath = Environment.getExternalStorageDirectory() + "/SapHamrah/Print";
            File tempdir = new File(mPath);
            if (!tempdir.exists())
                tempdir.mkdirs();

            mPath = mPath + "/Print-" + ccDarkhastFaktor + ".jpg";

            MainView.setDrawingCacheEnabled(true);

            MainView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            MainView.layout(0, 0, MainView.getMeasuredWidth(), MainView.getMeasuredHeight());

            DarkhastImage = getBitmapFromView(MainView, MainView.getHeight(), MainView.getWidth());
            MainView.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            DarkhastImage.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            Resize();

        } catch (Throwable e)
        {
            e.printStackTrace();
        }
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
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    public void Resize()
    {
        float Zarib = 0;
        int ResizeWidth = 0, ResizeHeight = 0;
        int quality = 100;
        String mPath = Environment.getExternalStorageDirectory() + "/SapHamrah/Print";
        mPath = mPath + "/Print-" + ccDarkhastFaktor + ".jpg";
        //-----------------resize-------------------------
        Bitmap tmp = BitmapFactory.decodeFile(mPath);
        int printerSize = systemconfig_tabletDAO.getAll().get(0).getSizePrint();
        if (printerSize == 384)
        {
            Zarib = (float) 384 / (tmp.getWidth());
            ResizeWidth = (int) (tmp.getWidth() * Zarib);
            ResizeHeight = (int) (tmp.getHeight() * Zarib);
        }
        else if (printerSize == 576)
        {
            Zarib = (float) 576 / (tmp.getWidth());
            ResizeWidth = (int) (tmp.getWidth() * Zarib);
            ResizeHeight = (int) (tmp.getHeight() * Zarib);
        }
        else if (printerSize == 832)
        {
            Zarib = (float) 832 / (tmp.getWidth());
            ResizeWidth = (int) (tmp.getWidth() * Zarib);
            ResizeHeight = (int) (tmp.getHeight() * Zarib);
        }

        Bitmap bitmap = Bitmap.createBitmap(ResizeWidth, ResizeHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);


        Bitmap scaled = Bitmap.createScaledBitmap(tmp, ResizeWidth, ResizeHeight, true);
        int leftOffset = 0;//(bitmap.getWidth() - scaled.getWidth()) / 2;
        int topOffset = 0;
        canvas.drawBitmap(scaled, leftOffset, topOffset, null);

        File imageFile = new File(mPath);


        FileOutputStream outStream;

        try
        {
            outStream = new FileOutputStream(imageFile);

            try
            {
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outStream);
                outStream.flush();
                outStream.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    private void insertLogToDB(int logType, String message, String functionParent, String functionChild)
    {
        PubFunc.Logger logger = new PubFunc().new Logger();
        logger.insertLogToDB(PrintNoe2Activity.this, logType, message, "", "PrintActivity", functionParent, functionChild);
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

}
