package com.saphamrah.customer.base;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BaseFragment<T extends BasePresenterOps> extends Fragment implements BaseView {

    private final Integer PERMISSION_REQUEST_CODE = 9824;

    private Integer layout;

    public BaseFragment(Integer layout) {
        this.layout = layout;
    }

    protected View view;
    protected T presenter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getView() == null) {
            view = inflater.inflate(layout, container, false);
            setPresenter();
            initViews();
        }
        return view;
    }

    protected abstract void initViews();

    protected abstract void setPresenter();

    protected void onPermission(ArrayList<BasePermissionModel> basePermissionModels) {
    }

    ;

    void checkPermissions(String[] permissions) {
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

            for (int i = 0; i < permissions.length; i++) {
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


    public void navigate(Integer action) {

        Navigation.findNavController(requireView()).navigate(action);
    }

    public void navigate(Integer action, Bundle bundle) {

        Navigation.findNavController(requireView()).navigate(action, bundle);
    }

    public void navigate(NavDirections action) {

        Navigation.findNavController(requireView()).navigate(action);
    }
}



