package com.saphamrah.customer.base;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public abstract class BaseActivity<T extends BasePresenterOps> extends AppCompatActivity implements BaseView {
    private final Integer PERMISSION_REQUEST_CODE = 9824;

    protected T presenter;

    public abstract void onPermission(ArrayList<BasePermissionModel> basePermissionModels);

    public void setActivityPresenter(T presenter) {
        this.presenter = presenter;

    }

    public void checkPermissions(String[] permissions) {
        ArrayList<BasePermissionModel> permissionArray = new ArrayList<>();

        for (String permission : permissions) {
            permissionArray.add(new BasePermissionModel(permission, true));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE);

        }
        onPermission(permissionArray);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {

            ArrayList<BasePermissionModel> permissionResults = new ArrayList<>();

            for (int i=0 ; i<permissions.length ; i++) {
                permissionResults.add(
                        new BasePermissionModel(
                                permissions[i], grantResults[i] == PackageManager.PERMISSION_GRANTED
                        )
                );
            }

            onPermission(permissionResults);

        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
