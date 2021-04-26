package com.saphamrah.MVP.View;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.saphamrah.Adapter.DrawerExpandablelistAdapter;
import com.saphamrah.Adapter.MainMenuPagerAdapter;
import com.saphamrah.BaseMVP.MainMVP;
import com.saphamrah.DAO.ForoshandehMamorPakhshDAO;
import com.saphamrah.MVP.Presenter.MainPresenter;
import com.saphamrah.Model.ForoshandehMamorPakhshModel;
import com.saphamrah.Model.MessageBoxModel;
import com.saphamrah.Model.SystemMenuModel;
import com.saphamrah.Network.AsyncDownloadFile;
import com.saphamrah.Network.AsyncTaskResponse;
import com.saphamrah.PubFunc.ForoshandehMamorPakhshUtils;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Receiver.LocationReceiver;
import com.saphamrah.Service.GpsTracker;
import com.saphamrah.Shared.ServerIPShared;
import com.saphamrah.Utils.Constants;
import com.saphamrah.Utils.CustomAlertDialog;
import com.saphamrah.Utils.CustomAlertDialogResponse;
import com.saphamrah.Utils.StateMaintainer;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import devlight.io.library.ntb.NavigationTabBar;
import me.anwarshahriar.calligrapher.Calligrapher;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.extras.PromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.PromptFocal;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.CirclePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.backgrounds.RectanglePromptBackground;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.CirclePromptFocal;
import uk.co.samuelwall.materialtaptargetprompt.extras.focals.RectanglePromptFocal;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;


public class MainActivity extends AppCompatActivity implements MainMVP.RequiredViewOps, AsyncTaskResponse
{

    private final String TAG = this.getClass().getSimpleName();
    StateMaintainer stateMaintainer = new StateMaintainer(this.getSupportFragmentManager() , TAG , MainActivity.this);
    MainMVP.PresenterOps mPresenter;
    private boolean doubleBackToExitPressedOnce = false;


    private SlidingRootNav slidingRootNav;
    private LinearLayout layForoshandehInfo;
    private RelativeLayout layMessageBox;
    private LinearLayout layMenu;
    private ExpandableListView expandableListView;
    private CircleImageView imgProfile;
    private CustomAlertDialog customAlertDialog;
    private ArrayList<SystemMenuModel> arrayListHeaders;
    private HashMap<SystemMenuModel, List<SystemMenuModel>> hashMapMenu;
    private int lastExpandedGroup = -1;
    private final int IMAGE_CAPTURE_RESULT = 1;
    private final int CHECK_CAMERA_AND_STORAGE_WITH_OPEN_CAMERA = 200;
    private final int CHECK_CAMERA_AND_STORAGE_WITHOUT_OPEN_CAMERA = 201;
    private final int CHECK_ACCESS_FINE_LOCATION = 202;
    private String profileImageCurrentPath = "";
    private int noeMasouliat = 0;
    private int ccForoshandeh;
    private int ccMamorPakhsh;
    private int ccAfrad;
    private int ccMasir;
    private boolean isTestNewVersion;
    private String downloadUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calligrapher calligrapher = new Calligrapher(this);
        calligrapher.setFont(this, getResources().getString(R.string.fontPath), true);

        startMVPOps();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            if (getSupportActionBar() != null) {
                this.getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        } catch (Exception exception) {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainActivity", "onCreate", "");
            exception.printStackTrace();
        }

        createNotificationChannel();

        checkPermissions(CHECK_CAMERA_AND_STORAGE_WITHOUT_OPEN_CAMERA);

        ccForoshandeh = 0;
        ccMamorPakhsh = 0;
        ccAfrad = 0;
        ccMasir = 0;

        noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(new ForoshandehMamorPakhshDAO(this).getIsSelect());

        customAlertDialog = new CustomAlertDialog(MainActivity.this);
        slidingRootNav = new SlidingRootNavBuilder(this)
                .withMenuOpened(false).withContentClickableWhenMenuOpened(false).withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer).withGravity(SlideGravity.RIGHT).inject();


        layForoshandehInfo = slidingRootNav.getLayout().findViewById(R.id.layForoshandehInfo);
        imgProfile = findViewById(R.id.imgProfile);
        ImageView imgCustomerSupport = findViewById(R.id.imgCustomerSupport);
        ImageView imgHelp = findViewById(R.id.imgHelp);
        layMessageBox = findViewById(R.id.layMessageBox);
        layMenu = findViewById(R.id.layMenu);
        expandableListView = findViewById(R.id.listMenu);
        MainMenuPagerAdapter pagerAdapter = new MainMenuPagerAdapter(getSupportFragmentManager(), noeMasouliat);
        NavigationTabBar navigationTabBar = findViewById(R.id.navigationTabBar);
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(5);
        profileImageCurrentPath = "";

        navigationTabBar.setModels(createNavigationTabItems());
        navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ALL);
        navigationTabBar.setViewPager(viewPager , 4);
        navigationTabBar.setTypeface(getResources().getString(R.string.fontPath));
        navigationTabBar.setIsTitled(true);

        arrayListHeaders = new ArrayList<>();
        hashMapMenu = new HashMap<>();
        expandableListView = findViewById(R.id.listMenu);
        DrawerExpandablelistAdapter adapter = new DrawerExpandablelistAdapter(MainActivity.this, arrayListHeaders, hashMapMenu);
        expandableListView.setAdapter(adapter);

        mPresenter.checkForoshandehMamorPakhsh();

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            mPresenter.getImageProfile();
        }

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition)
            {
                if (lastExpandedGroup != -1 && lastExpandedGroup != groupPosition)
                {
                    expandableListView.collapseGroup(lastExpandedGroup);
                }
                lastExpandedGroup = groupPosition;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                try
                {
                    SystemMenuModel systemMenuModelHeader = arrayListHeaders.get(groupPosition);
                    SystemMenuModel systemMenuModelChild = hashMapMenu.get(systemMenuModelHeader).get(childPosition);
                    slidingRootNav.closeMenu(true);
                    mPresenter.checkSelectedMenuItem(systemMenuModelChild);
                }
                catch (Exception exception)
                {
                    mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString() + "\n" + " group position : " + groupPosition + " , child position : " + childPosition, "", "MainActivity", "onCreate", "expandableListView.setOnChildClickListener");
                    exception.printStackTrace();
                }
                return false;
            }
        });


        layMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingRootNav.isMenuClosed()) {
                    slidingRootNav.openMenu();
                }
            }
        });


        layMessageBox.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this , MessageBoxActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
        });


        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Log.d("permission" , "seton imageprofile click");
                if (Build.VERSION.SDK_INT >= 23)
                {
                    checkPermissions(CHECK_CAMERA_AND_STORAGE_WITH_OPEN_CAMERA);
                }
                else
                {
                    Log.d("openCamera" , "setOnClickListener");
                    openCamera();
                }
            }
        });


        if (!isServiceRunning(LocationReceiver.class))
        {
            if (Build.VERSION.SDK_INT >= 23)
            {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(MainActivity.this , new String[]{Manifest.permission.ACCESS_FINE_LOCATION} ,CHECK_ACCESS_FINE_LOCATION);
                }
                else
                {
                    mPresenter.getGPSReceiverConfig();
                }
            }
            else
            {
                mPresenter.getGPSReceiverConfig();
            }
        }


        imgHelp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                slidingRootNav.closeMenu(true);
                showHelp(0);
            }
        });


        imgCustomerSupport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this , OnlineSupportActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
        });



    }


    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce)
        {
            super.onBackPressed();
            return;
        }

        doubleBackToExitPressedOnce = true;
        customAlertDialog.showToast(MainActivity.this, getResources().getString(R.string.doubleClickForClose), Constants.INFO_MESSAGE(), Constants.DURATION_SHORT());

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        mPresenter.checkServerTime();
        mPresenter.checkForGetParameter();
        mPresenter.checkVersion();
        //createNotification();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        mPresenter.checkShowMessageBox();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        slidingRootNav.closeMenu(true);
    }


    @Override
    public Context getAppContext() {
        return MainActivity.this;
    }


    @Override
    public void onCheckForoshandehMamorPakhsh(ForoshandehMamorPakhshModel foroshandehMamorPakhshModel , String deviceIMEI , int ccMasir)
    {
        Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));

        TextView lblForoshandehName = slidingRootNav.getLayout().findViewById(R.id.lblForoshandehName);
        TextView lblNoeForoshandeh = slidingRootNav.getLayout().findViewById(R.id.lblNoeForoshandeh);
        TextView lblForoshandehSazmanForosh = slidingRootNav.getLayout().findViewById(R.id.lblForoshandehSazmanForosh);
        TextView lblForoshandehMarkazForosh = slidingRootNav.getLayout().findViewById(R.id.lblForoshandehMarkazForosh);
        final TextView lblIMEI = slidingRootNav.getLayout().findViewById(R.id.lblIMEI);
        //RelativeLayout layoutIMEI = (RelativeLayout) slidingRootNav.getLayout().findViewById(R.id.layoutIMEI);
        //RelativeLayout layoutName = (RelativeLayout) slidingRootNav.getLayout().findViewById(R.id.layoutName);

        lblForoshandehName.setTypeface(font);
        lblNoeForoshandeh.setTypeface(font);
        lblForoshandehSazmanForosh.setTypeface(font);
        lblForoshandehMarkazForosh.setTypeface(font);

        try
        {
            lblForoshandehName.setText(foroshandehMamorPakhshModel.getFullName());
            lblNoeForoshandeh.setText(foroshandehMamorPakhshModel.getNameNoeForoshandehMamorPakhsh());
            lblForoshandehSazmanForosh.setText(foroshandehMamorPakhshModel.getNameSazmanForosh());
            lblForoshandehMarkazForosh.setText(foroshandehMamorPakhshModel.getNameMarkazForosh());
            lblIMEI.setText(deviceIMEI);
            int noeMasouliat = new ForoshandehMamorPakhshUtils().getNoeMasouliat(foroshandehMamorPakhshModel);
            if (noeMasouliat == 1 || noeMasouliat == 2 || noeMasouliat == 3  || noeMasouliat == 6 || noeMasouliat ==8)
            {
                ccForoshandeh = foroshandehMamorPakhshModel.getCcForoshandeh();
                ccMamorPakhsh = 0;
            }
            else if (noeMasouliat == 4 || noeMasouliat == 5)
            {
                ccForoshandeh = 0;
                ccMamorPakhsh = foroshandehMamorPakhshModel.getCcMamorPakhsh();
                lblForoshandehSazmanForosh.setVisibility(View.GONE);
                lblForoshandehMarkazForosh.setText(foroshandehMamorPakhshModel.getNameMarkazAnbar());
            }
            this.ccAfrad = foroshandehMamorPakhshModel.getCcAfrad();
            this.ccMasir = ccMasir;
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainActivity", "onCheckForoshandehMamorPakhsh", "");
            exception.printStackTrace();
        }


        lblForoshandehName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mPresenter.checkForCopyToClipboard("IMEI" , lblIMEI.getText().toString());
            }
        });

        mPresenter.createDrawerMenu(foroshandehMamorPakhshModel);
    }

	@Override
    public void showForceUpdateGallery()
    {
        customAlertDialog.showMessageAlert(MainActivity.this, "", getString(R.string.forceUpdateGallery), Constants.INFO_MESSAGE(), getString(R.string.apply), new CustomAlertDialogResponse()
        {
            @Override
            public void setOnCancelClick() {}
            @Override
            public void setOnApplyClick()
            {
                startActivity(new Intent(MainActivity.this, GoodsInfoActivity.class));
            }
        });
    }

    @Override
    public void showForceUpdateJayezehTakhfif()
    {
        customAlertDialog.showMessageAlert(MainActivity.this, "", getString(R.string.forceUpdateJayezehTakhfif), Constants.INFO_MESSAGE(), getString(R.string.apply), new CustomAlertDialogResponse()
        {
            @Override
            public void setOnCancelClick() {}
            @Override
            public void setOnApplyClick()
            {
                startActivity(new Intent(MainActivity.this, GetProgramActivity.class));
            }
        });
    }
	
    @Override
    public void hideMessageBox()
    {
        layMessageBox.setVisibility(View.GONE);
    }

    @Override
    public void showMessageBox(int unreadCount)
    {
        TextView lblUnreadCount = findViewById(R.id.lblUnreadMessageCount);
        ImageView imgviewUnreadCount = findViewById(R.id.imgviewUnreadCount);
        layMessageBox.setVisibility(View.VISIBLE);
        if (unreadCount > 0)
        {
            imgviewUnreadCount.setVisibility(View.VISIBLE);
            lblUnreadCount.setVisibility(View.VISIBLE);
            lblUnreadCount.setText(String.valueOf(unreadCount));
        }
        else
        {
            imgviewUnreadCount.setVisibility(View.INVISIBLE);
            lblUnreadCount.setVisibility(View.INVISIBLE);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void showNotifForMessages(ArrayList<MessageBoxModel> messageBoxModels)
    {
        //removeAllNotification();
        sendPushNotification(messageBoxModels);
    }

    final String CHANNEL_ID = "21";
    private void createNotificationChannel()
    {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            CharSequence name = getString(R.string.notificationChannelName);
            String description = getString(R.string.notificationChannelDesc);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void removeAllNotification()
    {
        try
        {
            if (Build.VERSION.SDK_INT >= 26)
            {
                NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.cancelAll();
                /*for (int i=0; i<notificationId ; i++)
                {
                    NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.cancel(i);
                }*/
            }
            else
            {
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                notificationManager.cancelAll();
                /*for (int i=0; i<notificationId ; i++)
                {
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    notificationManager.cancel(i);
                }*/
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
/**
 *
 * TODO
 * NEW SEND PUSH NOTIFICATION METHODS
 * TODO
 *
 */

public static final String SEND_PUSH_NOTIFICATION="SEND_PUSH_NOTIFICATION";
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void sendPushNotification(ArrayList<MessageBoxModel> messageBoxModels) {
        for (MessageBoxModel messageBoxModel:messageBoxModels){
            Log.i(SEND_PUSH_NOTIFICATION, "sendPushNotification: "+messageBoxModel.getCcMessage());
        }
        String ccMessagesNewNofit = "-1";
        int ccForoshandeh = 0;
        int ccMamorPakhsh = 0;
        Log.i(SEND_PUSH_NOTIFICATION, "sdk version"+Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            NotificationChannel channelFinal = new NotificationChannel(Constants.MAIN_CHANNEL_ID(), "HighPriorityChannel", NotificationManager.IMPORTANCE_HIGH);
            channelFinal.setDescription("highPriority");
            channelFinal.setImportance(NotificationManager.IMPORTANCE_HIGH);

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channelFinal);

            Log.i(SEND_PUSH_NOTIFICATION, "messageBoxSize" + messageBoxModels.size());
            for (MessageBoxModel message : messageBoxModels) {

                ccMessagesNewNofit += "," + message.getCcMessage();
                ccForoshandeh = message.getCcForoshandeh();
                ccMamorPakhsh = message.getCcMamorPakhsh();
                Log.i(SEND_PUSH_NOTIFICATION, "CcMessage: " + message.getCcMessage() + "MessageNoeNewFit: " + ccMessagesNewNofit);
                manager.notify(message.getCcMessage(), BuildNotification(message.getMessage(), message.getTitle(), message.getCcMessage(), message.getNoeMessage()));

            }
            mPresenter.checkccMessagesForUpdateNotifStatus(ccForoshandeh, ccMamorPakhsh, ccMessagesNewNofit);
            ccMessagesNewNofit = "-1";

        } else {

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            Log.i(SEND_PUSH_NOTIFICATION, "sendPushNotification: ");
            for (MessageBoxModel message : messageBoxModels) {
                ccMessagesNewNofit += "," + message.getCcMessage();
                ccForoshandeh = message.getCcForoshandeh();
                ccMamorPakhsh = message.getCcMamorPakhsh();
                Log.i(SEND_PUSH_NOTIFICATION, "sendPushNotification: "+message.getCcMessage());
                notificationManager.notify(message.getCcMessage()+4, BuildNotification(message.getMessage(), message.getTitle(), message.getCcMessage(), message.getNoeMessage()));


            }
            Log.i(SEND_PUSH_NOTIFICATION, "sendPushNotification: "+ccForoshandeh+"ccMamoorPakhsh:"+ccMamorPakhsh+"ccMessagesNewNotif:"+ccMessagesNewNofit);
            mPresenter.checkccMessagesForUpdateNotifStatus(ccForoshandeh, ccMamorPakhsh, ccMessagesNewNofit);
            ccMessagesNewNofit = "-1";


        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public Notification BuildNotification(String message, String title, int ccMessage, int noeMessage) {

        Intent notificationIntent = new Intent(this, MessageDetailActivity.class);
        notificationIntent.putExtra("ccMessage", ccMessage);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, ccMessage, notificationIntent, PendingIntent.FLAG_ONE_SHOT);





        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_push_collapsed);
        contentView.setImageViewResource(R.id.image, R.drawable.ic_launcher_icon);
        contentView.setTextViewText(R.id.titleNotif, title);
        contentView.setTextViewText(R.id.textNotif, message);
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(this,Constants.MAIN_CHANNEL_ID());
        int smallIconId = this.getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());
        Bitmap icon = BitmapFactory.decodeResource(MainActivity.this.getResources(),
                R.drawable.logo);
        notificationBuilder
                .setContentTitle(title)
                .setContentText(message)
                .setChannelId(Constants.MAIN_CHANNEL_ID())
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomHeadsUpContentView(contentView)
                .setCustomContentView(contentView)
                .setCustomBigContentView(contentView)
                .setLargeIcon(icon)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(message))
                .setAutoCancel(true)
                .setContentIntent(notificationPendingIntent);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.ic_launcher_icon);

        } else {
            notificationBuilder.setSmallIcon(R.drawable.ic_launcher_icon);
        }

        Notification notification=notificationBuilder.build();

        if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {
            if (smallIconId != 0) {
                if (notification.contentView != null)
                    notification.contentView.setViewVisibility(smallIconId, View.GONE);
            }
        }


        return notification;
    }
    /**
     *
     * TODO
     * NEW SEND PUSH NOTIFICATION METHODS
     * TODO
     *
     */

    private void createNotification(ArrayList<MessageBoxModel> messageBoxModels)
    {
        try
        {
            String ccMessagesNewNofit = "-1";
            int ccForoshandeh = 0;
            int ccMamorPakhsh = 0;
            if (Build.VERSION.SDK_INT >= 26)
            {
                NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                Notification.Builder notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID);

                for (MessageBoxModel message : messageBoxModels)
                {
                    ccForoshandeh = message.getCcForoshandeh();
                    ccMamorPakhsh = message.getCcMamorPakhsh();
                    Intent notificationIntent = new Intent(this, MessageDetailActivity.class);
                    notificationIntent.putExtra("ccMessage" , message.getCcMessage());
                    PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

                    if (message.getNoeMessage() == 1)
                    {
                        notification.setSmallIcon(R.drawable.ic_message);
                    }
                    else
                    {
                        notification.setSmallIcon(R.drawable.ic_show_faktor_detail);
                    }
                    notification.setContentTitle(message.getTitle());
                    notification.setContentText(message.getMessage());
                    notification.setAutoCancel(true);
                    notification.setTimeoutAfter(150000);
                    notification.setContentIntent(notificationPendingIntent);

                    mNotificationManager.notify(message.getCcMessage(), notification.build());
                    ccMessagesNewNofit += "," + message.getCcMessage();
                    //notificationId++;
                }
            }
            else
            {
                NotificationCompat.Builder notification = new NotificationCompat.Builder(this , CHANNEL_ID);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                for (MessageBoxModel message : messageBoxModels)
                {
                    ccForoshandeh = message.getCcForoshandeh();
                    ccMamorPakhsh = message.getCcMamorPakhsh();

                    Intent notificationIntent = new Intent(this, MessageDetailActivity.class);
                    notificationIntent.putExtra("ccMessage" , message.getCcMessage());
                    PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

                    if (message.getNoeMessage() == 1)
                    {
                        notification.setSmallIcon(R.drawable.ic_message);
                    }
                    else
                    {
                        notification.setSmallIcon(R.drawable.ic_show_faktor_detail);
                    }
                    notification.setContentTitle(message.getTitle());
                    notification.setContentText(message.getMessage());
                    //.setColor(getResources().getColor(R.color.colorAccent))
                    notification.setVibrate(new long[]{0, 300, 300, 400});
                        //.setLights(Color.WHITE, 1000, 5000)
                        //.setDefaults(Notification.DEFAULT_ALL)
                    notification.setContentIntent(notificationPendingIntent);
                    notification.setAutoCancel(true);
                        //.setCustomHeadsUpContentView(notificationLayout)
                        //.setCustomBigContentView(notificationLayoutExpanded)
                        //.setShowWhen(true)
                    notification.setTimeoutAfter(150000);
                    notification.setPriority(NotificationCompat.PRIORITY_HIGH);

                    notificationManager.notify(message.getCcMessage(), notification.build());
                    ccMessagesNewNofit += "," + message.getCcMessage();
                    //notificationId++;
                }
            }
            mPresenter.checkccMessagesForUpdateNotifStatus(ccForoshandeh, ccMamorPakhsh, ccMessagesNewNofit);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), e.toString(), "", "MainActivity", "createNotification", "");
        }



        /*Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);*/

        /*RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_small);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_large);*/

        /*NotificationCompat.Builder builder = new NotificationCompat.Builder(this , "25")
                .setSmallIcon(R.drawable.red_circle)
                .setContentTitle("new Request")
                .setContentText("new request has received")
                //.setColor(getResources().getColor(R.color.colorAccent))
                .setVibrate(new long[]{300, 300, 300})
                //.setLights(Color.WHITE, 1000, 5000)
                //.setDefaults(Notification.DEFAULT_ALL)
                //.setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                //.setCustomHeadsUpContentView(notificationLayout)
                //.setCustomBigContentView(notificationLayoutExpanded)
                //.setShowWhen(true)
                .setPriority(NotificationCompat.PRIORITY_LOW);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(notificationId, builder.build());
        notificationId++;*/


    }


    @Override
    public void startGPSService(int minDistance, int timeInterval, int fastestTimeInterval, int maxAccurancy)
    {
        Log.d("gps" , "ccForoshandeh : " + ccForoshandeh);
        Intent intent = new Intent(MainActivity.this, GpsTracker.class);
        intent.putExtra(Constants.DISTANCE() , minDistance);
        intent.putExtra(Constants.INTERVAL() , timeInterval);
        intent.putExtra(Constants.FASTEST_INTERVAL() , fastestTimeInterval);
        intent.putExtra(Constants.ACCURACY() , maxAccurancy);
        intent.putExtra("ccAfrad" , ccAfrad);
        intent.putExtra("ccForoshandeh" , ccForoshandeh);
        intent.putExtra("ccMamorPakhsh" , ccMamorPakhsh);
        intent.putExtra("ccMasir" , ccMasir);
        try
        {
            Log.d("locationReceiver" , "service start");
            if (Build.VERSION.SDK_INT >= 26)
            {
                LocationReceiver locationReceiver = new LocationReceiver();
                IntentFilter filter = new IntentFilter("com.sap.gpstracker");
                registerReceiver(locationReceiver , filter);
                startForegroundService(intent);
                //Toast.makeText(MainActivity.this, "Tracking Started..", Toast.LENGTH_SHORT).show();
            }
            else
            {
                startService(intent);
                //Toast.makeText(MainActivity.this, "Tracking Started..", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainActivity", "onCreate" , "");
        }
    }

    @Override
    public void setMenuAdapter(ArrayList<SystemMenuModel> headersList , HashMap<SystemMenuModel, List<SystemMenuModel>> hashMapMenuItems)
    {
        arrayListHeaders = new ArrayList<>();
        arrayListHeaders = headersList;
        hashMapMenu = new HashMap<>();
        hashMapMenu = hashMapMenuItems;
        DrawerExpandablelistAdapter adapter = new DrawerExpandablelistAdapter(MainActivity.this , arrayListHeaders , hashMapMenu);
        expandableListView.setAdapter(adapter);
        try
        {
            expandableListView.expandGroup(0);
            lastExpandedGroup = 0;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }


    public ArrayList<NavigationTabBar.Model> createNavigationTabItems()
    {
        String[] colors = getResources().getStringArray(R.array.mainMenuNavigationTabBarColors);

        ArrayList<NavigationTabBar.Model> tabItms = new ArrayList<>();
        tabItms.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_settings), Color.parseColor(colors[0]))
                .title(getResources().getString(R.string.system)).build());

        tabItms.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_report), Color.parseColor(colors[1]))
                .title(getResources().getString(R.string.report)).build());

        if (noeMasouliat == 7)
        {
            tabItms.add(new NavigationTabBar.Model.Builder(
                    getResources().getDrawable(R.drawable.ic_marketing), Color.parseColor(colors[3]))
                    .title(getResources().getString(R.string.marketing)).build());
        }
        else
        {
            tabItms.add(new NavigationTabBar.Model.Builder(
                    getResources().getDrawable(R.drawable.ic_funds), Color.parseColor(colors[2]))
                    .title(getResources().getString(R.string.vosol)).build());
        }

        tabItms.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_handshake), Color.parseColor(colors[3]))
                .title(getResources().getString(R.string.sell)).build());

        tabItms.add(new NavigationTabBar.Model.Builder(
                getResources().getDrawable(R.drawable.ic_house), Color.parseColor(colors[4]))
                .title(getResources().getString(R.string.home)).build());

        return tabItms;

    }

    @Override
    public void openActivity(String activityName)
    {
        if (activityName.equals(AddCustomerActivity.class.getSimpleName()))
        {
            mPresenter.removeAddCustomerSharedData();
        }
        else
        {
            try
            {
                Class<?> activityClass = Class.forName(getPackageName() + Constants.DIRECTORY_OF_ACTIVITY() + activityName);
                Intent intent = new Intent(MainActivity.this, activityClass);
                startActivity(intent);
                overridePendingTransition(R.anim.right_to_center, R.anim.center_to_left);
            }
            catch (Exception exception)
            {
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(MainActivity.this, Constants.LOG_EXCEPTION(), exception.toString(), "" , "MainActivity", "openActivity" , "");
                exception.printStackTrace();
            }
        }
    }

    @Override
    public void onRemovedAddCustomerSharedData()
    {
        Intent intent = new Intent(MainActivity.this , AddCustomerActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSuccessCopyIMEI(String imei)
    {
        String message = String.format(getResources().getString(R.string.imeiCopied) , imei);
        customAlertDialog.showToast(MainActivity.this, message, Constants.SUCCESS_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onGetProfileImage(byte[] profileImage)
    {
        if (profileImage != null && profileImage.length > 0)
        {
            imgProfile.setImageBitmap(new PubFunc().new ImageUtils().convertByteArrayToBitmap(MainActivity.this, profileImage));
        }
        else
        {
            imgProfile.setImageResource(R.drawable.ic_account);
        }
    }

    @Override
    public void forceUpdate(final String downloadUrl)
    {
        this.downloadUrl = downloadUrl;
        isTestNewVersion = false;
        customAlertDialog.showMessageAlert(MainActivity.this, getResources().getString(R.string.forceUpdate), getResources().getString(R.string.forceUpdateMessage), Constants.FAILED_MESSAGE(), getResources().getString(R.string.yes), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {}

            @Override
            public void setOnApplyClick() {
                checkWriteStoragePermission();
            }
        });
    }

    @Override
    public void forceUpdateTest(final String downloadUrl)
    {
        this.downloadUrl = downloadUrl;
        isTestNewVersion = true;
        customAlertDialog.showMessageAlert(MainActivity.this, getResources().getString(R.string.forceUpdate), getResources().getString(R.string.forceTestUpdateMessage), Constants.INFO_MESSAGE(), getResources().getString(R.string.yes), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {}

            @Override
            public void setOnApplyClick() {
                checkWriteStoragePermission();
            }
        });
    }

    @Override
    public void newVersionReleased(final String downloadUrl)
    {
        this.downloadUrl = downloadUrl;
        customAlertDialog.showLogMessageAlert(MainActivity.this, false, getResources().getString(R.string.newVersion), getResources().getString(R.string.newVersionReleased), Constants.INFO_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {}

            @Override
            public void setOnApplyClick() {
                checkWriteStoragePermission();
            }
        });
    }


    private void checkWriteStoragePermission()
    {
        ArrayList<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (permissions.size() > 0)
        {
            ActivityCompat.requestPermissions(MainActivity.this, permissions.toArray(new String[permissions.size()]), Constants.WRITE_EXTERNAL_STORAGE());
        }
        else
        {
            downloadNewVersion();
        }
    }

    private void downloadNewVersion()
    {
        ServerIPShared shared = new ServerIPShared(MainActivity.this);
        String ip = shared.getString(shared.IP_GET_REQUEST()
 , "");
        String port = shared.getString(shared.PORT_GET_REQUEST()
 , "");
        if (ip.trim().equals("") || port.trim().equals(""))
        {
            customAlertDialog.showMessageAlert(MainActivity.this, true, getResources().getString(R.string.error), getResources().getString(R.string.errorFindServerIP), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
        }
        else
        {
            AsyncDownloadFile asyncDownloadFile = new AsyncDownloadFile(MainActivity.this);
            asyncDownloadFile.delegate = MainActivity.this;
            asyncDownloadFile.execute(downloadUrl);
        }
    }



    @Override
    public void processFinished(Object object)
    {
        ArrayList<String> arrayList = (ArrayList<String>) object;
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(MainActivity.this);
        if (arrayList.get(0).equals("1"))
        {
            final String folder = arrayList.get(1);
            final String fileName = arrayList.get(2);

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // without this flag android returned a intent error!
                intent.setDataAndType(Uri.fromFile(new File(folder + fileName)), "application/vnd.android.package-archive");
                startActivity(intent);
                MainActivity.this.finish();
            }
            else
            {
                File file = new File(folder , fileName);
                if (file != null)
                {
                    Uri uri = FileProvider.getUriForFile(MainActivity.this, "com.saphamrah.imagefileprovider", file);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    //Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                    getApplicationContext().grantUriPermission(PACKAGE_NAME, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(uri , "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(intent);
                    MainActivity.this.finish();
                }
                else
                {
                    customAlertDialog.showMessageAlert(MainActivity.this, true,getResources().getString(R.string.error),getResources().getString(R.string.installManually),Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
                }
            }
        }
        else if (arrayList.get(0).equals("-1"))
        {
            customAlertDialog.showMessageAlert(MainActivity.this, true,getResources().getString(R.string.error),getResources().getString(R.string.errorOccurred),Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
        }
        else if (arrayList.get(0).equals("-2"))
        {
            customAlertDialog.showMessageAlert(MainActivity.this, true,getResources().getString(R.string.error),getResources().getString(R.string.downloadUrlNotFound),Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
        }
    }

    @Override
    public void showAboutAlert(String currentVersion, String newestVersion, String lastStableVersion, String newFeatures)
    {
        customAlertDialog.showAboutAlert(MainActivity.this, currentVersion, newestVersion, lastStableVersion, newFeatures);
    }

    @Override
    public void showExitAlert()
    {
        CustomAlertDialog customAlertDialog = new CustomAlertDialog(MainActivity.this);
        customAlertDialog.showLogMessageAlert(MainActivity.this, false, getResources().getString(R.string.warning), getResources().getString(R.string.exitWarning), Constants.INFO_MESSAGE(), getResources().getString(R.string.no), getResources().getString(R.string.yes), new CustomAlertDialogResponse() {
            @Override
            public void setOnCancelClick() {
            }

            @Override
            public void setOnApplyClick() {
                try
                {
                    int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    startActivity(intent);
                }
            }
        });
    }

	@Override
    public void showErrorAlert(boolean closeActivity, int titleResId, String message, int messageType, int buttonTextResId)
    {
        customAlertDialog.showMessageAlert(MainActivity.this, closeActivity, getResources().getString(titleResId), message, messageType, getResources().getString(buttonTextResId));
    }
	
    @Override
    public void showResourceError(boolean closeActivity, int titleResId, int messageResId, int messageType, int buttonTextResId)
    {
        customAlertDialog.showMessageAlert(MainActivity.this, closeActivity, getResources().getString(titleResId), getResources().getString(messageResId), messageType, getResources().getString(buttonTextResId));
    }

    @Override
    public void showToast(int resId, int messageType, int duration)
    {
        customAlertDialog.showToast(MainActivity.this, getResources().getString(resId) , messageType, duration);
    }


    private void openCamera()
    {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY))
        {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null)
            {
                File photoFile = null;
                try
                {
                    photoFile = createImageFile();
                }
                catch (IOException ex)
                {ex.printStackTrace();}
                if (photoFile != null)
                {
                    Uri photoURI = FileProvider.getUriForFile(MainActivity.this, "com.saphamrah.imagefileprovider", photoFile);
                    List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : resInfoList)
                    {
                        String packageName = resolveInfo.activityInfo.packageName;
                        grantUriPermission(packageName, photoURI, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(intent, IMAGE_CAPTURE_RESULT);
                }
            }
        }
        else
        {
            customAlertDialog.showMessageAlert(MainActivity.this, false, getResources().getString(R.string.error), getResources().getString(R.string.cameraNotFount), Constants.FAILED_MESSAGE(), getResources().getString(R.string.apply));
        }
    }


    private File createImageFile() throws IOException
    {
        String imageFileName = "profile";
        imageFileName = imageFileName + "-" + ccAfrad;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/SapHamrah/Pictures/Profile");
        boolean directoryExists = true;
        if (!storageDir.exists())
        {
            directoryExists = storageDir.mkdirs();
        }
        if (directoryExists)
        {
            for (File file : storageDir.listFiles())
            {
                file.delete();
            }
            //File image = File.createTempFile(
              //      imageFileName,  /* prefix */
                //    ".jpg",         /* suffix */
                //    storageDir      /* directory */
            //);
            File image = new File(storageDir, imageFileName + ".jpg");
            profileImageCurrentPath = image.getAbsolutePath();
            return image;
        }
        else
        {
            return null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_RESULT)
        {
            if (resultCode == RESULT_OK)
            {
                try
                {
                    mPresenter.checkProfileImage(profileImageCurrentPath);
                }
                catch (Exception exception)
                {
                    customAlertDialog.showToast(MainActivity.this, getResources().getString(R.string.errorSelectImage), Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
                    exception.printStackTrace();
                    mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainActivity", "onActivityResult", "");
                }
            }
        }
    }


    private void checkPermissions(int requestCode)
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            ArrayList<String> permissions = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(MainActivity.this , Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (ContextCompat.checkSelfPermission(MainActivity.this , Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (permissions.size() > 0)
            {
                ActivityCompat.requestPermissions(MainActivity.this , permissions.toArray(new String[permissions.size()]) , requestCode);
            }
            else if (requestCode == CHECK_CAMERA_AND_STORAGE_WITH_OPEN_CAMERA)
            {
                openCamera();
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("permission" , "requestCode : " + requestCode);
        if (requestCode == CHECK_CAMERA_AND_STORAGE_WITH_OPEN_CAMERA)
        {
            openCamera();
        }
        else if (requestCode == CHECK_ACCESS_FINE_LOCATION)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                mPresenter.getGPSReceiverConfig();
            }
            else
            {
                MainActivity.this.finish();
            }
        }
    }


    private boolean isServiceRunning(Class<?> serviceClass)
    {
        try
        {
            ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
            {
                if (serviceClass.getName().equals(service.service.getClassName()))
                {
                    return true;
                }
            }
            return false;
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainActivity", "isServiceRunning", "");
            return false;
        }
    }



    private void disableViewsShowInHelp(View[] VIEW_ID_OF_HELP)
    {
        for (View view : VIEW_ID_OF_HELP)
        {
            view.setEnabled(false);
        }
    }

    private void enableViewsShowInHelp(View[] VIEW_ID_OF_HELP)
    {
        for (View view : VIEW_ID_OF_HELP)
        {
            view.setEnabled(true);
        }
    }


    private void showHelp(final int index)
    {
        Typeface font = Typeface.createFromAsset(getAssets() , getResources().getString(R.string.fontPath));
        final View[] VIEW_ID_OF_HELP = new View[]{layMenu      , imgProfile                       , layForoshandehInfo       , expandableListView};
        final int[] TITLE_ID_OF_HELP = new int[]{R.string.menu , R.string.foroshandehProfileImage , R.string.foroshandehInfo , R.string.menu};
        final int[] DESC_ID_OF_HELP = new int[]{R.string.helpOpenMenu , R.string.helpForoshandehProfileImage , R.string.helpForoshandehInfo , R.string.helpMenu};
        final int[] FOCAL_COLOR_ID_OF_HELP = new int[]{getResources().getColor(R.color.colorFabRefresh) , getResources().getColor(R.color.colorFabSearch), getResources().getColor(R.color.colorFabList) , getResources().getColor(R.color.colorFabSort)};
        final PromptBackground[] PROMPT_BACKGROUND_OF_HELP = new PromptBackground[]{new RectanglePromptBackground(), new CirclePromptBackground(), new RectanglePromptBackground(), new RectanglePromptBackground()};
        final PromptFocal[] PROMPT_FOCAL_OF_HELP = new PromptFocal[]{new RectanglePromptFocal(), new CirclePromptFocal(), new RectanglePromptFocal(), new RectanglePromptFocal()};

        if (index == 0)
        {
            disableViewsShowInHelp(VIEW_ID_OF_HELP);
        }

        new MaterialTapTargetPrompt.Builder(this)
                .setTarget(VIEW_ID_OF_HELP[index])
                .setPrimaryText(TITLE_ID_OF_HELP[index])
                .setSecondaryText(DESC_ID_OF_HELP[index])
                .setBackgroundColour(getResources().getColor(R.color.colorPrimary))
                .setFocalColour(getResources().getColor(R.color.colorAccent))
                .setPromptBackground(PROMPT_BACKGROUND_OF_HELP[index])
                .setPromptFocal(PROMPT_FOCAL_OF_HELP[index])
                .setPrimaryTextTypeface(font)
                .setSecondaryTextTypeface(font)
                .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener() {
                    @Override
                    public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state) {
                        if (state ==  MaterialTapTargetPrompt.STATE_DISMISSED || state ==  MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                        {
                            slidingRootNav.openMenu(true);
                            if (index+1 < VIEW_ID_OF_HELP.length)
                            {
                                showHelp(index+1);
                            }
                            else
                            {
                                enableViewsShowInHelp(VIEW_ID_OF_HELP);
                                slidingRootNav.closeMenu(true);
                            }
                        }
                    }
                }).show();
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
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainActivity", "startMVPOps", "");
        }
    }


    private void initialize(MainMVP.RequiredViewOps view )
    {
        try
        {
            mPresenter = new MainPresenter(view);
            stateMaintainer.put(MainMVP.PresenterOps.class.getSimpleName(), mPresenter);
        }
        catch (Exception exception)
        {
            mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainActivity", "initialize", "");
        }
    }


    private void reinitialize(MainMVP.RequiredViewOps view)
    {
        try
        {
            mPresenter = stateMaintainer.get(MainMVP.PresenterOps.class.getSimpleName());
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
                mPresenter.checkInsertLogToDB(Constants.LOG_EXCEPTION(), exception.toString(), "", "MainActivity", "reinitialize", "");
            }
        }
    }

}
