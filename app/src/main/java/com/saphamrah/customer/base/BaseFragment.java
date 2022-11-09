package com.saphamrah.customer.base;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentFactory;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;


import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.temp.JayezehEntekhabiMojodiModel;
import com.saphamrah.customer.presentation.createRequest.CreateRequestActivity;

import org.reactivestreams.Subscriber;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment<T extends BasePresenterOps, S extends ViewBinding,A extends Activity> extends Fragment implements BaseView {

    private final Integer PERMISSION_REQUEST_CODE = 9824;
    public static final String TAG = BaseFragment.class.getSimpleName();
    private Integer layout;

    public BaseFragment(Integer layout) {
        this.layout = layout;
    }

    protected CompositeDisposable compositeDisposable;

    protected T presenter;
    protected S viewBinding;
    protected Context context;
    protected A activity;

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
                navigateUp();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        this.activity = ((A) getActivity());
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

    public void setFragmentResult(String key, Object value) {
        NavHostFragment.findNavController(this).getPreviousBackStackEntry().getSavedStateHandle().set(key,value);
        navigateUp();
    }

    public  Object getFragmentResult (String key){
        return NavHostFragment.findNavController(this).getCurrentBackStackEntry().getSavedStateHandle().get(key);
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
        NavOptions option = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.fade_in)
                .setExitAnim(R.anim.fade_out)
                .setPopExitAnim(R.anim.fade_out)
                .setPopEnterAnim(R.anim.fade_in)
                .build();
        Navigation.findNavController(requireView()).navigate(action, option);
    }

    public void navigateUp() {
        NavHostFragment.findNavController(this).navigateUp();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        presenter.onDestroy();
        viewBinding = null;
    }



}



