package com.saphamrah.MVP.View;

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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bxl.config.editor.BXLConfigLoader;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.saphamrah.Adapter.RptMarjoeeAdapter;
import com.saphamrah.Adapter.RptMarjoeePrintAdapter;
import com.saphamrah.DAO.RptMarjoeeDAO;
import com.saphamrah.DAO.SystemConfigTabletDAO;
import com.saphamrah.Model.RptMarjoeeKalaModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Service.BluetoothPrintService;
import com.saphamrah.Utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import jpos.JposException;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import jpos.config.JposEntry;
import me.anwarshahriar.calligrapher.Calligrapher;

public class PrintMarjoeeActivity extends AppCompatActivity
{
    private String baseDirectory;
    private String date;

    private FloatingActionMenu fabMenu;
    private FloatingActionButton fabSearch;
    private FloatingActionButton fabPrint;

    View MainView;

    Bitmap DarkhastImage;

    //-------------------bluetooth----------------------------
    private static BluetoothPrintService mPrintService = null;

    public static final int MESSAGE_DEVICE_NAME = 1;
    public static final int MESSAGE_TOAST = 2;
    public static final int MESSAGE_READ = 3;

    public static final String TOAST = "toast";
    public static final String DEVICE_NAME = "DEVICE_NAME";
    public static final String DEVICE_ADDRESS = "DEVICE_ADDRESS";

    private BluetoothAdapter mBluetoothAdapter = null;


    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 2;
    private static final int REQUEST_ENABLE_BT = 3;
    SystemConfigTabletDAO systemconfig_tabletDAO;// = new SystemConfigTabletDAO(this);


    //----------------------bixolon------------------------
    private static final String DEVICE_ADDRESS_START = " (";
    private static final String DEVICE_ADDRESS_END = ")";

    private static BXLConfigLoader bxlConfigLoader;
    private static POSPrinter posPrinter;
    private static String logicalName;
    private static int brightness = 100;
    private static int compress = 1;
    int height = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_marjoee);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        fabMenu = findViewById(R.id.fabMenu);
        fabSearch = findViewById(R.id.fabSearch);
        fabPrint = findViewById(R.id.fabPrint);

        baseDirectory = Environment.getExternalStorageDirectory() +  "/SapHamrah/Print/Marjoee";
        date = new PubFunc().new DateUtils().todayDateWithSlash(PrintMarjoeeActivity.this);
        systemconfig_tabletDAO = new SystemConfigTabletDAO(PrintMarjoeeActivity.this);


        try
        {
            MainView = findViewById(R.id.Main);
            TextView lblDateForosh = findViewById(R.id.lblDate);
            TextView lblPrintDate = findViewById(R.id.lblPrintDate);
            lblDateForosh.setText(date);
            lblPrintDate.setText(String.format("%1$s : %2$s", getResources().getString(R.string.printDate), new PubFunc().new DateUtils().gregorianToPersianDateTime(new Date())));

            //------------------------------------- Marjoee -----------------------------------------------------
            LinearLayout lyMarjooe = findViewById(R.id.lyMarjoee);
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            RptMarjoeeDAO rptMarjoeeDAO = new RptMarjoeeDAO(PrintMarjoeeActivity.this);
            ArrayList<RptMarjoeeKalaModel> rptMarjoeeModels = rptMarjoeeDAO.getAllOrderByCustomer();
            if(rptMarjoeeModels.size() != 0)
            {
                RptMarjoeePrintAdapter adapter = new RptMarjoeePrintAdapter(PrintMarjoeeActivity.this, rptMarjoeeModels);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(PrintMarjoeeActivity.this);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(PrintMarjoeeActivity.this , 0));
                recyclerView.setAdapter(adapter);

                /*ArrayList<RptMarjoeeKalaModel> rptMarjoeeKalaModelsChild = new ArrayList<>();
                ArrayList<RptMarjoeeKalaModel> rptMarjoeeKalaModels1Headers = new ArrayList<>();
                HashMap hashMap = new HashMap();

                for (RptMarjoeeKalaModel marjoee : rptMarjoeeModels)
                {
                    Log.d("printMarjoee" , "marjoee : " + marjoee.toString());
                    if (marjoee.getRadif() == -1)
                    {
                        rptMarjoeeKalaModelsChild.add(marjoee);
                        hashMap.put(marjoee , rptMarjoeeKalaModelsChild);
                        rptMarjoeeKalaModelsChild = new ArrayList<>();
                        rptMarjoeeKalaModels1Headers.add(marjoee);
                    }
                    else
                    {
                        rptMarjoeeKalaModelsChild.add(marjoee);
                    }
                }

                RptMarjoeePrintAdapter adapter = new RptMarjoeePrintAdapter(PrintMarjoeeActivity.this, rptMarjoeeKalaModels1Headers, hashMap);
                expandableListView.setAdapter(adapter);


                for ( int i = 0; i < rptMarjoeeKalaModels1Headers.size(); i++ )
                {
                    expandableListView.expandGroup(i);
                    for (int j = 0; j < expandableListView.getChildCount(); j++)
                    {
                        height += expandableListView.getChildAt(j).getMeasuredHeight();
                        height += expandableListView.getDividerHeight();
                    }
                    expandableListView.getLayoutParams().height = (height+6);
                }*/

                /*expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener()
                {
                    @Override
                    public void onGroupExpand(int groupPosition)
                    {
                        Log.d("marjoee" , "setOnGroupExpandListener");
                        //height = 0;
                        for (int j = 0; j < expandableListView.getChildCount(); j++)
                        {
                            height += expandableListView.getChildAt(j).getMeasuredHeight();
                            height += expandableListView.getDividerHeight();
                        }
                        expandableListView.getLayoutParams().height = (height+6);
                    }
                });*/

                /*expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
                {
                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id)
                    {
                        return true;
                    }
                });*/

            }
            else
            {
                lyMarjooe.setVisibility(View.GONE);
            }

            //----------------------------------------------------------------------------------------------------------
            takeScreenshotOfFaktor(date.replace("/" , "-"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(PrintMarjoeeActivity.this, Constants.LOG_EXCEPTION(), e.toString(), "", "PrintMarjoeeActivity", "onCreate", "");
        }


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.hasnt, Toast.LENGTH_LONG).show();
            finish();
            return;
        }


        bxlConfigLoader = new BXLConfigLoader(PrintMarjoeeActivity.this);
        try {
            bxlConfigLoader.openFile();
        } catch (Exception e) {
            e.printStackTrace();
            bxlConfigLoader.newFile();
        }
        posPrinter = new POSPrinter(PrintMarjoeeActivity.this);

        //SetPrinter();



        fabSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(PrintMarjoeeActivity.this, BixolonDeviceListActivity.class);
                startActivityForResult(intent, REQUEST_CONNECT_DEVICE_INSECURE);
            }
        });


        fabPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                fabMenu.close(true);
                try
                {
                    PrintBixolon();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });



    }


    public void takeScreenshotOfFaktor(String date)
    {
        try
        {
            File tempdir = new File(baseDirectory);
            if (!tempdir.exists())
            {
                tempdir.mkdirs();
            }

            baseDirectory = baseDirectory + "/Print-Marjoee-" + date + ".jpg";

            MainView.setDrawingCacheEnabled(true);

            MainView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            MainView.layout(0, 0, MainView.getMeasuredWidth(), MainView.getMeasuredHeight());

            DarkhastImage = getBitmapFromView(MainView, MainView.getHeight(), MainView.getWidth());
            MainView.setDrawingCacheEnabled(false);

            File imageFile = new File(baseDirectory);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            DarkhastImage.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            Resize(date);

        }
        catch (Throwable e)
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
        {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return bitmap;
    }

    public void Resize(String date)
    {
        float Zarib=0;
        int ResizeWidth=0, ResizeHeight=0;
        int quality = 100;
        //baseDirectory = baseDirectory + "/Print-Marjoee-" + date + ".jpg";
        //-----------------resize-------------------------
        Bitmap tmp = BitmapFactory.decodeFile(baseDirectory);
        int printSize = systemconfig_tabletDAO.getAll().get(0).getSizePrint();
        Log.d("marjoee" , "printSize : " + printSize);
        if(printSize==384)
        {
            Zarib= (float) 384 / (tmp.getWidth());
            ResizeWidth=(int) (tmp.getWidth()*Zarib);
            ResizeHeight=(int) (tmp.getHeight()*Zarib);
        }
        else if(printSize==576)
        {
            Zarib= (float) 576 / (tmp.getWidth());
            ResizeWidth=(int) (tmp.getWidth()*Zarib);
            ResizeHeight=(int) (tmp.getHeight()*Zarib);
        }
        else if(printSize==832)
        {
            Zarib= (float) 823 / (tmp.getWidth());
            ResizeWidth=(int) (tmp.getWidth()*Zarib);
            ResizeHeight=(int) (tmp.getHeight()*Zarib);
        }

        Log.d("marjoee" , "Zarib : " + Zarib);
        Log.d("marjoee" , "ResizeWidth : " + ResizeWidth);
        Log.d("marjoee" , "ResizeHeight : " + ResizeHeight);

        Bitmap bitmap = Bitmap.createBitmap(ResizeWidth, ResizeHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Bitmap scaled = Bitmap.createScaledBitmap(tmp, ResizeWidth, ResizeHeight, true);
        int leftOffset = 0;//(bitmap.getWidth() - scaled.getWidth()) / 2;
        int topOffset = 0;
        canvas.drawBitmap(scaled, leftOffset, topOffset, null);

        File imageFile = new File(baseDirectory);


        FileOutputStream outStream;

        try
        {
            outStream = new FileOutputStream(imageFile);

            try
            {
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outStream);
                outStream.flush();
                outStream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void PrintBixolon() throws IOException
    {
        posPrinter = new POSPrinter(PrintMarjoeeActivity.this);
        //String PathFaktorImage = baseDirectory + "/Print-Marjoee-" + date + ".jpg";
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(baseDirectory, options);
        //int current_inch = bitmap.getWidth();
        print(bitmap);
        closePrinter();
    }

    private void SetPrinter(Intent data) {

        String device = data.getExtras().getString("EXTRA_DEVICE_NAME")+DEVICE_ADDRESS_START+data.getExtras().getString("EXTRA_DEVICE_ADDRESS")+DEVICE_ADDRESS_END;

        String name = device.substring(0, device.indexOf(DEVICE_ADDRESS_START));

        String address = device.substring(device.indexOf(DEVICE_ADDRESS_START)+ DEVICE_ADDRESS_START.length(),device.indexOf(DEVICE_ADDRESS_END));

        try {
            for (Object entry : bxlConfigLoader.getEntries()) {
                JposEntry jposEntry = (JposEntry) entry;
                bxlConfigLoader.removeEntry(jposEntry.getLogicalName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            logicalName = setProductName(name);
            bxlConfigLoader.addEntry(logicalName,
                    BXLConfigLoader.DEVICE_CATEGORY_POS_PRINTER,
                    logicalName,
                    BXLConfigLoader.DEVICE_BUS_BLUETOOTH, address);

            bxlConfigLoader.saveFile();
            Toast.makeText(PrintMarjoeeActivity.this, "اتصال با " + logicalName + " برقرار شد", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print(final Bitmap PrintFile)
    {
        try
        {
            posPrinter.open(logicalName);
            posPrinter.claim(0);
            posPrinter.setDeviceEnabled(true);
            Toast.makeText(PrintMarjoeeActivity.this, "success", Toast.LENGTH_SHORT).show();

            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.put((byte) POSPrinterConst.PTR_S_RECEIPT);
            buffer.put((byte) brightness);
            buffer.put((byte) compress);
            buffer.put((byte) 0x00);

            posPrinter.printBitmap(buffer.getInt(0), PrintFile,	posPrinter.getRecLineWidth(), POSPrinterConst.PTR_BM_LEFT);

            closePrinter();
        }
        catch (JposException e)
        {
            e.printStackTrace();
        }
    }

    public void closePrinter() {
        try {
            posPrinter.close();
        } catch (JposException e) {
            e.printStackTrace();
            //Toast.makeText(PrintActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private static String setProductName(String name){
        String productName = BXLConfigLoader.PRODUCT_NAME_SPP_R200II;

        if((name.indexOf("SPP-R200II")>=0)){
            if(name.length() > 10){
                if(name.substring(10, 11).equals("I")){
                    productName = BXLConfigLoader.PRODUCT_NAME_SPP_R200III;
                }
            }
        }else if((name.indexOf("SPP-R210")>=0)){
            productName = BXLConfigLoader.PRODUCT_NAME_SPP_R210;
        }else if((name.indexOf("SPP-R310")>=0)){
            productName = BXLConfigLoader.PRODUCT_NAME_SPP_R310;
        }else if((name.indexOf("SPP-R300")>=0)){
            productName = BXLConfigLoader.PRODUCT_NAME_SPP_R300;
        }else if((name.indexOf("SPP-R400")>=0)){
            productName = BXLConfigLoader.PRODUCT_NAME_SPP_R400;
        }

        return productName;
    }


    public void onStart()
    {

        super.onStart();
        Log.e("onStart", "++ ON START ++");

        if (Build.VERSION.SDK_INT >= 23)
        {
            if (checkSelfPermission(Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(PrintMarjoeeActivity.this, new String[]{Manifest.permission.BLUETOOTH}, 100);
            }
            else
            {
                if (!mBluetoothAdapter.isEnabled())
                {
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enableIntent, REQUEST_ENABLE_BT);

                }
            }
        }
        else
        {
            if (!mBluetoothAdapter.isEnabled())
            {
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            }
        }



    }

    public synchronized void onResume() {

        super.onResume();
        Log.e("onResume", "+ ON RESUME +");

        if (mPrintService != null)
        {
            if (mPrintService.getState() == BluetoothPrintService.STATE_NONE)
            {
                mPrintService.start();
            }
        }
    }

    public void onDestroy() {

        super.onDestroy();
        if (mPrintService != null)
            mPrintService.stop();
        Log.e("onDestroy", "--- ON DESTROY ---");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PrintMarjoeeActivity.this.finish();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult", "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_INSECURE:
                if (resultCode == Activity.RESULT_OK) {
                    SetPrinter(data);
                }
                break;
            case REQUEST_ENABLE_BT:
                if (resultCode != Activity.RESULT_OK) {
                    Log.d("onActivityResult", "BT not enabled");
                    Toast.makeText(this, "bt_not_enabled_leaving", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }


    //------------- Header ---------------------------
    protected int getlayoutResID()
    {
        //return R.layout.activity_rpt_faktorprint;
        return -5;
    }
    protected String getlayoutTitle()
    {
        return "چاپ فاکتور فروش";
    }
    protected int getlayoutIcon()
    {
        return R.drawable.logo;
    }


}
