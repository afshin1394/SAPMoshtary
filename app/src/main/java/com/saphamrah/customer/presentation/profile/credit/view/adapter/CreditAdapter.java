package com.saphamrah.customer.presentation.profile.credit.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.local.rptEtebarModel.EtebarType;
import com.saphamrah.customer.data.local.rptEtebarModel.RptEtebarParentModel;
import com.saphamrah.customer.data.local.rptEtebarModel.RptEtebarRialiModel;
import com.saphamrah.customer.data.local.rptEtebarModel.RptEtebarTedadiModatModel;
import com.saphamrah.customer.databinding.RptEtebarListItemBinding;

import java.text.DecimalFormat;
import java.util.List;


public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.CreditViewHolder> {
    private List<RptEtebarParentModel> mItems;
    private Context mContext;

    public CreditAdapter(List<RptEtebarParentModel> items, Context context) {
        mItems = items;
        mContext = context;

    }

    public void setEtebarList(List<RptEtebarParentModel> items) {
        mItems = items;
    }

    @NonNull
    @Override
    public CreditViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RptEtebarListItemBinding binding = RptEtebarListItemBinding.inflate(LayoutInflater.from(mContext), parent, false);
        return new CreditViewHolder(binding, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class CreditViewHolder extends RecyclerView.ViewHolder {
        private RptEtebarParentModel mEtebarMolde;
        private Context mContext;
        private RptEtebarListItemBinding mBinding;
        private DecimalFormat formatter = new DecimalFormat("#,###,###");

        public CreditViewHolder(RptEtebarListItemBinding binding, final Context context) {
            super(binding.constraintLayoutEtebarItem);
            mBinding = binding;
            mContext = context;
            Typeface font = Typeface.createFromAsset(mContext.getAssets(), context.getResources().getString(R.string.fontPath));
            mBinding.textViewEtebarTitle.setTypeface(font);
            mBinding.textViewEtebar.setTypeface(font);
            mBinding.textViewEtebarMande.setTypeface(font);
            mBinding.textViewEtebarMasrafi.setTypeface(font);
        }

        public void bind(RptEtebarParentModel etebarModel) {
            mEtebarMolde = etebarModel;
            mBinding.textViewEtebarTitle.setText(getTitleString());
            switch (mEtebarMolde.getEtebarType()) {
                case Riali:
                    mBinding.textViewEtebar.setText (mContext.getResources().getString(R.string.etebar) + ":\n " + formatter.format(((RptEtebarRialiModel) mEtebarMolde).getEtebar()));
                    mBinding.textViewEtebarMasrafi.setText(mContext.getResources().getString(R.string.etebarRialiMasrafShode) + ":\n " + formatter.format(((RptEtebarRialiModel) mEtebarMolde).getMasrafShode()));
                    mBinding.textViewEtebarMande.setText(mContext.getResources().getString(R.string.mandeEtebar) + ":\n " + formatter.format(((RptEtebarRialiModel) mEtebarMolde).getMande()));
                    break;
                case Tedati:
                    mBinding.textViewEtebar.setText(mContext.getResources().getString(R.string.etebar) + ":\n " + ((RptEtebarTedadiModatModel) mEtebarMolde).getEtebar());
                    mBinding.textViewEtebarMasrafi.setText(mContext.getResources().getString(R.string.etebarRialiMasrafShode) + ":\n " + ((RptEtebarTedadiModatModel) mEtebarMolde).getMasrafShode());
                    mBinding.textViewEtebarMande.setText(mContext.getResources().getString(R.string.mandeEtebar) + ":\n " + ((RptEtebarTedadiModatModel) mEtebarMolde).getMande());
                    break;
                case ModatEtebar:
                    mBinding.textViewEtebar.setText(mContext.getResources().getString(R.string.modatEtebar) + ":\n " + ((RptEtebarTedadiModatModel) mEtebarMolde).getEtebar());
                    mBinding.textViewEtebarMasrafi.setText(mContext.getResources().getString(R.string.etebarRialiMasrafShode) + ":\n" + ((RptEtebarTedadiModatModel) mEtebarMolde).getMasrafShode());
                    mBinding.textViewEtebarMande.setText(mContext.getResources().getString(R.string.mandeEtebar) + ":\n" + ((RptEtebarTedadiModatModel) mEtebarMolde).getMande());
                    break;
            }

            if (mEtebarMolde.isMandeLessThanZero()) {
                mBinding.textViewEtebarMande.setText( mContext.getResources().getString(R.string.mandeEtebar) + ":\n" + mEtebarMolde.getMandeAbsuluteValue());
                mBinding.rootCardview.setStrokeColor(mContext.getResources().getColor(R.color.colorBadStatus));
                mBinding.layStatusLeft.setBackgroundColor(mContext.getResources().getColor(R.color.colorBadStatus));
            }
        }


        public String getTitleString() {
            String title = "";
            switch (mEtebarMolde.getRptEtebarType()) {
                case Saghf:
                    if (mEtebarMolde.getEtebarType() == EtebarType.Riali)
                        title = mContext.getResources().getString(R.string.saghfEtebarRiali);
                    else if (mEtebarMolde.getEtebarType() == EtebarType.Tedati)
                        title = mContext.getResources().getString(R.string.saghfEtebarTedadi);
                    else
                        title = mContext.getResources().getString(R.string.saghfEtebarModat);
                    break;
                case Moavagh:
                    if (mEtebarMolde.getEtebarType() == EtebarType.Riali)
                        title = mContext.getResources().getString(R.string.etebarRialMoavagh);
                    else if (mEtebarMolde.getEtebarType() == EtebarType.Tedati)
                        title = mContext.getResources().getString(R.string.etebarTedadMoavagh);
                    else
                        title = mContext.getResources().getString(R.string.etebarModatMoavagh);
                    break;
                case Asnad:
                    if (mEtebarMolde.getEtebarType() == EtebarType.Riali)
                        title = mContext.getResources().getString(R.string.etebarRialAsnad);
                    else if (mEtebarMolde.getEtebarType() == EtebarType.Tedati)
                        title = mContext.getResources().getString(R.string.etebarTedadAsnad);
                    break;
                case AsnadBargashti:
                    if (mEtebarMolde.getEtebarType() == EtebarType.Riali)
                        title = mContext.getResources().getString(R.string.etebarRialBargashti);
                    else if (mEtebarMolde.getEtebarType() == EtebarType.Tedati)
                        title = mContext.getResources().getString(R.string.etebarTedadBargashti);
                    else
                        title = mContext.getResources().getString(R.string.etebarModatBargashti);
                    break;

            }
            return title;
        }
    }
}
