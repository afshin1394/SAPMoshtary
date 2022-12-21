package com.saphamrah.customer.presentation.main.shopping;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseDialogFragment;
import com.saphamrah.customer.base.BaseFragment;
import com.saphamrah.customer.data.local.RptStatusModel;
import com.saphamrah.customer.data.local.temp.ElamMarjoeeForoshandehModel;
import com.saphamrah.customer.databinding.FragmentShoppingListBinding;
import com.saphamrah.customer.presentation.main.MainActivity;
import com.saphamrah.customer.presentation.main.MainInteracts;
import com.saphamrah.customer.presentation.main.MainPresenter;
import com.saphamrah.customer.presentation.main.shopping.adapter.RptStatusAdapter;
import com.saphamrah.customer.utils.AdapterUtil.AdapterAction;
import com.saphamrah.customer.utils.AdapterUtil.AdapterItemListener;
import com.saphamrah.customer.utils.CheckTabletOrPhone;
import com.saphamrah.customer.utils.Constants;
import com.saphamrah.customer.utils.customViews.CustomSnackBar;
import com.saphamrah.customer.utils.customViews.CustomSpinner;
import com.saphamrah.customer.utils.customViews.CustomSpinnerResponse;
import com.saphamrah.customer.utils.dialogs.DoubleActionFragmentDialog;
import com.saphamrah.customer.utils.dialogs.IDoubleActionDialog;
import com.saphamrah.customer.utils.dialogs.ISingleActionDialog;
import com.saphamrah.customer.utils.dialogs.SingleActionDialogFragment;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class ShoppingListFragment extends BaseFragment<MainPresenter, FragmentShoppingListBinding, MainActivity> implements MainInteracts.RequiredViewOps {
    private CustomSpinner customSpinner = new CustomSpinner();

    List<String> laqvDarkhastTitles = new ArrayList<>();


    @Override
    protected void onBackPressed() {

    }

    @Override
    protected void initViews() {
        laqvDarkhastTitles.add("اشتباه در ثبت سفارش");
        laqvDarkhastTitles.add("تغییر آدرس");

        ArrayList<RptStatusModel> models = new ArrayList<>();

        models.add(new RptStatusModel("2254652145", "1401/05/29", "2,420,000", "نقد", 1, true));
        models.add(new RptStatusModel("2254652146", "1401/05/29", "2,150,000", "نقد", 2, true));
        models.add(new RptStatusModel("2254652147", "1401/05/29", "3,450,000", "نقد", 3, true));
        models.add(new RptStatusModel("2254652148", "1401/05/19", "1,450,000", "نقد", 4, true));
        models.add(new RptStatusModel("2254652149", "1401/05/09", "4,450,000", "نقد", "کمبود موجودی", Constants.FAILED_FROM_SERVER, false));

        RptStatusAdapter adapter = new RptStatusAdapter(requireContext(), models, null);

        AdapterItemListener<RptStatusModel> listener = (model, position, Action) -> {

            switch (Action) {
                case REMOVE:
//                    new DoubleActionFragmentDialog(context.getString(R.string.sureDeleteDarkhast), true, new IDoubleActionDialog() {
//                        @Override
//                        public void onConfirmClick() {
//
//                        }
//
//                        @Override
//                        public void onCancelClick() {
//
//                        }
//                    }).show(fragmentTransaction,"confirm");
                    customSpinner.showSpinnerSingleButton(activity, laqvDarkhastTitles, false, new CustomSpinnerResponse() {
                        @Override
                        public void onChangeItem(int selectedIndex) {
                        }

                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onSingleSelect(int selectedIndex) {
                            models.get(position).setElateAdamDarkhast(laqvDarkhastTitles.get(selectedIndex));
                            models.get(position).setSuccessful(false);
                            models.get(position).setStatusCode(Constants.FAILED_BY_USER);
                            adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onMultiSelect(ArrayList<Integer> selectedIndexes) {

                        }
                    });
                    break;

                case DETAIL:
                    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Screenshots/Emza-320989.jpg";
                    File file = new File(path);
                    FragmentTransaction wft = getChildFragmentManager().beginTransaction();
                     new SingleActionDialogFragment("",Uri.fromFile(file) , true, new ISingleActionDialog() {
                        @Override
                        public void onClick(BaseDialogFragment dialogFragment) {
                            dialogFragment.dismiss();
                        }
                    }).show(wft,"dialog");

                    break;
            }

        };
        adapter.setDataChanged(models, listener);


        LinearLayoutManager mLayoutManager = new LinearLayoutManager(requireContext());
        viewBinding.recyclerView.setLayoutManager(mLayoutManager);
        viewBinding.recyclerView.setAdapter(adapter);

        viewBinding.shimmerViewContainer.startShimmer();

        new Handler().postDelayed(() -> {
            viewBinding.shimmerViewContainer.stopShimmer();
            viewBinding.shimmerViewContainer.setVisibility(View.GONE);
        }, 50);


    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentShoppingListBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentShoppingListBinding.inflate(inflater, container, false);
    }

    @Override
    public void onError(String error) {

    }

    @Override
    public void showLoading(String message) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoConnection() {

    }

    @Override
    public Context getAppContext() {
        return requireActivity();
    }

}