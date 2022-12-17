package com.saphamrah.customer.base;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.saphamrah.customer.R;

import java.util.ArrayList;
import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseBottomDialogFragment<A extends Activity,T extends BasePresenterOps, S extends ViewBinding> extends BottomSheetDialogFragment implements BaseView {
    private final Integer PERMISSION_REQUEST_CODE = 9824;
    public static final String TAG = BaseFragment.class.getSimpleName();

    private Integer layout;



    protected CompositeDisposable compositeDisposable;

    protected T presenter;
    protected S viewBinding;
    protected A activity;
    protected Context context;
    protected boolean isFragmentVisible;

    protected abstract void onBackPressed();

    protected abstract void initViews();

    protected abstract void setPresenter();

    protected abstract S inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container);



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                onBackPressed();
                Navigation.findNavController(viewBinding.getRoot()).navigateUp();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = ((A) requireActivity());
    }

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

    @Override
    public void onResume() {
        super.onResume();
        isFragmentVisible = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isFragmentVisible = false;
    }

    @Override
    public int getTheme() {
        return R.style.RoundedCornersDialog;
    }

    public void getFragmentResult(String key, Observer<Object> observer) {
        Log.i(TAG, "getFragmentResult: "+viewBinding);
        Objects.requireNonNull(Navigation.findNavController(viewBinding.getRoot()).getCurrentBackStackEntry()).getSavedStateHandle().getLiveData(key).observe(getViewLifecycleOwner(),observer);
    }


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

    public void setFragmentResult(String key, Object value) {

            NavHostFragment.findNavController(this).getPreviousBackStackEntry().getSavedStateHandle().set(key, value);

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.MyDialogAnimation;

    }
}
