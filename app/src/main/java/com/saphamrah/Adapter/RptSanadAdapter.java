package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.RptSanadModel;
import com.saphamrah.PubFunc.DateUtils;
import com.saphamrah.R;
import com.saphamrah.databinding.RptSanadListItemBinding;

import java.text.DecimalFormat;
import java.util.List;

public class RptSanadAdapter extends RecyclerView.Adapter<RptSanadAdapter.RptSanadViewHolder> {
    private List<RptSanadModel> mItems;
    private Context mContext;


    public RptSanadAdapter(List<RptSanadModel> rptSanadModels , Context context) {
        this.mItems = rptSanadModels;
        mContext = context;
    }

    public void setSanadList(List<RptSanadModel> rptSanadModels) {
        mItems = rptSanadModels;
    }

    @NonNull
    @Override
    public RptSanadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RptSanadListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.rpt_sanad_list_item, parent, false);
        return new RptSanadAdapter.RptSanadViewHolder(binding, mContext);

    }

    @Override
    public void onBindViewHolder(@NonNull RptSanadViewHolder holder, int position) {

        holder.bind(mItems.get(position));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }



    public class RptSanadViewHolder extends RecyclerView.ViewHolder {
        private RptSanadListItemBinding mBinding;
        private RptSanadModel mRptSanadModel;
        private Context mContext;
        private DecimalFormat formatter = new DecimalFormat("#,###,###");


        public RptSanadViewHolder(RptSanadListItemBinding binding , Context context) {
            super(binding.getRoot());
            mBinding = binding;
            mContext = context;
            Typeface font = Typeface.createFromAsset(mContext.getAssets(), context.getResources().getString(R.string.fontPath));
            mBinding.textViewCodeAndNameMoshtary.setTypeface(font);
            mBinding.textViewTarikhFaktor.setTypeface(font);
            mBinding.textViewMablaghFaktor.setTypeface(font);
            mBinding.textViewTarikhCheck.setTypeface(font);
            mBinding.textViewMablaghCheck.setTypeface(font);
        }

        public void bind(RptSanadModel rptSanadModel){
            mRptSanadModel = rptSanadModel;
            mBinding.textViewCodeAndNameMoshtary.setText(mRptSanadModel.getCodeMoshtaryOld() + " - " +mRptSanadModel.getNameMoshtary());
            String tarikhFaktor = mRptSanadModel.getTarikhFaktor();
            tarikhFaktor = tarikhFaktor.subSequence(0 , tarikhFaktor.indexOf("T")).toString();
            tarikhFaktor = new DateUtils().gregorianWithDashToPersianSlash(tarikhFaktor);
            mBinding.textViewTarikhFaktor.setText(tarikhFaktor);
            mBinding.textViewMablaghFaktor.setText(formatter.format(mRptSanadModel.getMablaghKhalesFaktor()));

            String tarikhCheck = mRptSanadModel.getTarikhSanad();
            tarikhCheck = tarikhCheck.subSequence(0 , tarikhCheck.indexOf("T")).toString();
            tarikhCheck = new DateUtils().gregorianWithDashToPersianSlash(tarikhCheck);
            mBinding.textViewTarikhCheck.setText(tarikhCheck);
            mBinding.textViewMablaghCheck.setText(formatter.format(mRptSanadModel.getMablaghCheck()));
        }
    }
}
