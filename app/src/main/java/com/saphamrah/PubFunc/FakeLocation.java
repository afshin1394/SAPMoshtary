package com.saphamrah.PubFunc;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

import com.saphamrah.Utils.Constants;

import java.util.List;

public class FakeLocation
{

    public boolean useFakeLocation(Context context)
    {
        if (Settings.Secure.getString(context.getContentResolver() , Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"))
        {
            return isMockPermissionApps(context);
        }
        else
        {
            return true;
        }
    }

    private boolean isMockPermissionApps(Context context)
    {
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo applicationInfo : packages)
        {
            try
            {
                PackageInfo packageInfo = pm.getPackageInfo(applicationInfo.packageName, PackageManager.GET_PERMISSIONS);

                String[] requestedPermissions = packageInfo.requestedPermissions;
                if (requestedPermissions != null)
                {
                    for (int i = 0; i < requestedPermissions.length; i++)
                    {
                        if (requestedPermissions[i].equals("android.permission.ACCESS_MOCK_LOCATION") && !applicationInfo.packageName.equals(context.getPackageName()) && !(applicationInfo.sourceDir).substring(1,7).equals("system"))
                        {
                            return true;
                        }
                    }
                }
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                PubFunc.Logger logger = new PubFunc().new Logger();
                logger.insertLogToDB(context, Constants.LOG_EXCEPTION(), exception.toString(), "FakeLocation", "", "isMockPermissionApps", "");
                return false;
            }
        }
        return false;
    }

}
