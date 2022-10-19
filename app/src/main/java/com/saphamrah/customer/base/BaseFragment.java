package com.saphamrah.customer.base;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import com.saphamrah.customer.databinding.FragmentSendOtpLoginBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment<T extends BasePresenterOps, S extends ViewBinding> extends Fragment implements BaseView {

    private final Integer PERMISSION_REQUEST_CODE = 9824;

    private Integer layout;

    public BaseFragment(Integer layout) {
        this.layout = layout;
    }

    protected CompositeDisposable compositeDisposable;

    protected T presenter;
    protected S viewBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = inflateBiding(inflater, container);
        return viewBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setPresenter();
        compositeDisposable = new CompositeDisposable();
        initViews();

    }

    protected abstract void initViews();

    protected abstract void setPresenter();

    protected abstract S inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container);

    public void addDisposable(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    protected void onPermission(ArrayList<BasePermissionModel> basePermissionModels) {
    }



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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.clear();
        viewBinding = null;
    }
}



