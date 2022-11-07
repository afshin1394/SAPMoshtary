package com.saphamrah.customer.base;


import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseActivity<T extends BasePresenterOps, S extends ViewBinding> extends AppCompatActivity implements BaseView {
    private final Integer PERMISSION_REQUEST_CODE = 9824;
    public static Map<String,Object> results ;
    protected T presenter;
    protected S viewBinding;
    protected  void onKeyBoardVisibilityChange(boolean visible){

    }

    boolean isKeyboardShowing = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        results = new HashMap<>();
        viewBinding = inflateBiding(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        initViews();
        checkKeyboard();
    }

    private void checkKeyboard() {
        // ContentView is the root view of the layout of this activity/fragment
        viewBinding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect r = new Rect();
                        viewBinding.getRoot().getWindowVisibleDisplayFrame(r);
                        int screenHeight = viewBinding.getRoot().getRootView().getHeight();

                        // r.bottom is the position above soft keypad or device button.
                        // if keypad is shown, the r.bottom is smaller than that before.
                        int keypadHeight = screenHeight - r.bottom;


                        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                            // keyboard is opened
                            if (!isKeyboardShowing) {
                                isKeyboardShowing = true;
                                onKeyBoardVisibilityChange(true);
                            }
                        }
                        else {
                            // keyboard is closed
                            if (isKeyboardShowing) {
                                isKeyboardShowing = false;
                                onKeyBoardVisibilityChange(false);
                            }
                        }
                    }
                });
    }

    protected abstract void initViews();

    protected abstract S inflateBiding(LayoutInflater inflater);


    public abstract void onPermission(ArrayList<BasePermissionModel> basePermissionModels);

    public void setActivityPresenter(T presenter) {
        this.presenter = presenter;

    }

    public void checkPermissions(String[] permissions)
    {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        results = null;

    }
}
