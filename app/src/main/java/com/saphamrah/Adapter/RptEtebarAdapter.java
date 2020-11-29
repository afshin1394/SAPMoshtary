package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.R;
import com.saphamrah.UIModel.rptEtebarModel.EtebarType;
import com.saphamrah.UIModel.rptEtebarModel.RptEtebarParentModel;
import com.saphamrah.UIModel.rptEtebarModel.RptEtebarTedadiModatModel;
import com.saphamrah.UIModel.rptEtebarModel.RptEtebarRialiModel;
import com.saphamrah.databinding.RptEtebarListItemBinding;

import java.text.DecimalFormat;
import java.util.List;


public class RptEtebarAdapter extends RecyclerView.Adapter<RptEtebarAdapter.RptEtebarViewHolder> {
    private List<RptEtebarParentModel> mItems;
    private Context mContext;

    public RptEtebarAdapter(List<RptEtebarParentModel> items, Context context) {
        mItems = items;
        mContext = context;

    }

    public void setEtebarList(List<RptEtebarParentModel> items) {
        mItems = items;
    }

    @NonNull
    @Override
    public RptEtebarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RptEtebarListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.rpt_etebar_list_item, parent, false);
        return new RptEtebarViewHolder(binding, mContext);
    }

    @Override
    public void onBindViewHolder(@NonNull RptEtebarViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class RptEtebarViewHolder extends RecyclerView.ViewHolder {
        private RptEtebarParentModel mEtebarMolde;
        private Context mContext;
        private RptEtebarListItemBinding mBinding;
        private DecimalFormat formatter = new DecimalFormat("#,###,###");

        public RptEtebarViewHolder(RptEtebarListItemBinding binding, final Context context) {
            super(binding.getRoot());
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
                    mBinding.textViewEtebar.setText(mContext.getResources().getString(R.string.etebar) + ":\n " + formatter.format(((RptEtebarRialiModel) mEtebarMolde).getEtebar()));
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
                mBinding.layStatus.setBackgroundColor(mContext.getResources().getColor(R.color.colorBadStatus));
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
