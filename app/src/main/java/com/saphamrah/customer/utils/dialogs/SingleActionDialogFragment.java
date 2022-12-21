package com.saphamrah.customer.utils.dialogs;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.saphamrah.customer.R;
import com.saphamrah.customer.base.BaseDialogFragment;
import com.saphamrah.customer.databinding.FragmentDoubleActionDialogBinding;
import com.saphamrah.customer.databinding.FragmentSingleActionDialogBinding;


public class SingleActionDialogFragment extends BaseDialogFragment<FragmentSingleActionDialogBinding> {
    ISingleActionDialog iSingleActionDialog;
    Uri uri;
    String title;

    public SingleActionDialogFragment(String title, Uri uri, boolean isCancelable, ISingleActionDialog iSingleActionDialog) {
        this.iSingleActionDialog = iSingleActionDialog;
        this.title = title;
        this.uri = uri;
        this.setCancelable(isCancelable);
    }


    @Override
    protected void onBackPressed() {
        iSingleActionDialog.onClick(SingleActionDialogFragment.this);
    }

    @Override
    protected void initViews() {
        if (title.equals("") )
        {
            viewBinding.relTitle.setVisibility(View.GONE);
            viewBinding.scrollImgMain.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(uri)
                    .placeholder(R.drawable.nopic_whit)
                    .into(viewBinding.imgMain);
//            Glide.with(SingleActionDialogFragment.this)
//                    .load(uri)
//                    .into(viewBinding.imgMain);
        }else{

            viewBinding.scrollImgMain.setVisibility(View.GONE);
            viewBinding.relTitle.setVisibility(View.VISIBLE);
            viewBinding.txtTitle.setText(title);
        }
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        viewBinding.BTNConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iSingleActionDialog.onClick(SingleActionDialogFragment.this);

            }
        });


    }

    @Override
    protected void setPresenter() {

    }

    @Override
    protected FragmentSingleActionDialogBinding inflateBiding(LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentSingleActionDialogBinding.inflate(inflater, container, false);

    }


}