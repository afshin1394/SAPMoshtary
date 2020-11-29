package com.saphamrah.PubFunc;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


public class DeviceInfoTest
{

    private DeviceInfo deviceInfo = new DeviceInfo();
    private Context context = InstrumentationRegistry.getInstrumentation().getContext();


    @Test
    public void getIMEI()
    {
        String imei = deviceInfo.getIMEI(context);
        Assert.assertNotEquals("" , imei.replace("0" , ""));
        Assert.assertNotEquals(null , imei);
        Assert.assertNotEquals("" , imei);
    }

    @Test
    public void getAPILevel()
    {
        int apiLevel = deviceInfo.getAPILevel(context);
        int maxApiLevel = 29;
        int minApiLevel = 19;
        assertTrue(apiLevel >= minApiLevel && apiLevel <= maxApiLevel);
    }

}