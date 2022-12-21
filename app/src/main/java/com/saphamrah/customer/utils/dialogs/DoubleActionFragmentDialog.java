package com.saphamrah.customer.utils.dialogs;

import androidx.annotation.Nullable;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saphamrah.customer.base.BaseDialogFragment;
import com.saphamrah.customer.databinding.FragmentDoubleActionDialogBinding;

public class DoubleActionFragmentDialog extends BaseDialogFragment<FragmentDoubleActionDialogBinding> {
    private IDoubleActionDialog iDialogDoubleAction;
    private String title;

    public DoubleActionFragmentDialog(String title, boolean isCancelable, IDoubleActionDialog iDialogDoubleAction) {
        this.iDialogDoubleAction = iDialogDoubleAction;
        this.title = title;
        this.setCancelable(isCancelable);
    }



    @Override
    protected void onBackPressed() {

           iDialogDoubleAction.onCancelClick();
           dismiss();
    }

    @Override
    protected void initViews() {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        viewBinding.BTNCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDialogDoubleAction.onCancelClick();
                dismiss();
            }
        });

        viewBinding.BTNConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iDialogDoubleAction.onConfirmClick();
                dismiss();

            }
        });

        viewBinding.txtTitle.setText(title);

    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentDoubleActionDialogBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {

        return FragmentDoubleActionDialogBinding.inflate(inflater, container, false);
    }


}