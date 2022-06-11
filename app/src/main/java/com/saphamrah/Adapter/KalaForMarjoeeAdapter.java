package com.saphamrah.Adapter;

import static com.saphamrah.Application.BaseApplication.TAG;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.Model.ListKalaForMarjoeeModel;
import com.saphamrah.PubFunc.PubFunc;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class KalaForMarjoeeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModel;
    private int lastSelectedItem;
    private int mabnaType;
    private boolean canEditPrice;
    private SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
    private PubFunc.DateUtils dateUtils = new PubFunc().new DateUtils();

    public KalaForMarjoeeAdapter(int mabnaType,Context context, ArrayList<ListKalaForMarjoeeModel> listKalaForMarjoeeModel ,boolean canEditPrice, OnItemClickListener listener)
    {
        this.canEditPrice = canEditPrice;
        this.mabnaType = mabnaType;
        this.listener = listener;
        this.context = context;
        this.listKalaForMarjoeeModel = listKalaForMarjoeeModel;
        this.lastSelectedItem = -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (mabnaType == Constants.BA_MABNA) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kala_for_marjoee_customlist, parent, false);
                    return new ViewHolderBaMabna(view);
            } else {
                if (canEditPrice) {
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kala_for_marjoee_customlist_based, parent, false);
                    return new ViewHolderBiMabna(view);
                }else{
                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kala_for_marjoee_customlist_based, parent, false);
                    return new ViewHolderBiMabna(view);
                }
            }


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {



        switch (mabnaType){

            case Constants.BA_MABNA:
                 ViewHolderBaMabna viewHolderBaMabna = (ViewHolderBaMabna) holder;
                 viewHolderBaMabna.bind(listKalaForMarjoeeModel.get(position) , position , listener);
                break;

            case Constants.BI_MABNA:
                if (canEditPrice) {
                    ViewHolderBiMabna viewHolderBiMabna = (ViewHolderBiMabna) holder;
                    viewHolderBiMabna.bind(listKalaForMarjoeeModel.get(position), position, listener);
                } else {
                    ViewHolderBaMabna viewHolderBaMabna2 = (ViewHolderBaMabna) holder;
                    viewHolderBaMabna2.bind(listKalaForMarjoeeModel.get(position), position, listener);
                }
                break;

        }
    }



    @Override
    public int getItemCount()
    {
        return listKalaForMarjoeeModel.size();
    }

    public void clearSelecedItem()
    {
        lastSelectedItem = -1;
    }

    public class ViewHolderBiMabna extends RecyclerView.ViewHolder {
        LinearLayout layRoot;
        private TextView lblNameKala;
        private TextView lblShomareBach;
        private TextView lblCost;
        private EditText editableCost;
        private ImageView imgSelect;
        private CardView crdviewRoot;
        private CustomTextInputLayout customTextInputLayout;

        public ViewHolderBiMabna(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = view.findViewById(R.id.layRoot);
            lblNameKala = view.findViewById(R.id.lblNameKala);
            lblShomareBach = view.findViewById(R.id.lblShomareBach);
            lblCost = view.findViewById(R.id.lblGheymat);
            imgSelect = view.findViewById(R.id.imgSelect);
            crdviewRoot = view.findViewById(R.id.crdviewRoot);
            editableCost = view.findViewById(R.id.lblEditableGheymat);
            customTextInputLayout = view.findViewById(R.id.txtinputEditableGheymat);
            editableCost.addTextChangedListener(textWatcher);

            editableCost.setTypeface(font);
            lblNameKala.setTypeface(font);
            lblShomareBach.setTypeface(font);
            lblCost.setTypeface(font);
            customTextInputLayout.setTypeface(font);
        }

        void bind(final ListKalaForMarjoeeModel listKalaForMarjoeeModel , final int position , final OnItemClickListener listener)
        {

            DecimalFormat formatter = new DecimalFormat("#,###,###");
            lblNameKala.setText(listKalaForMarjoeeModel.getNameKala());
            lblShomareBach.setText(String.format("%1$s \n %2$s", context.getResources().getString(R.string.shomareBach), listKalaForMarjoeeModel.getShomarehBach()));
            lblCost.setText(String.format("%1$s \n %2$s", context.getResources().getString(R.string.mablaghForosh), formatter.format((int)listKalaForMarjoeeModel.getMablaghForosh())));
            editableCost.setText(String.format("%1$s" , formatter.format((int)listKalaForMarjoeeModel.getMablaghForosh())));

            if (position == lastSelectedItem)
            {
                imgSelect.setImageResource(R.drawable.ic_success);
                customTextInputLayout.setEnabled(true);
                customTextInputLayout.requestFocus();
            }
            else
            {
                imgSelect.setImageResource(R.drawable.circle_tick_gray);
                customTextInputLayout.setEnabled(false);
            }

            if (listKalaForMarjoeeModel.getIsKalaZayeatTolid()==1){
                crdviewRoot.setBackgroundResource(R.color.marjoeeKamel);
            } else {
                crdviewRoot.setBackgroundResource(R.color.defultTreasuryList);
            }
            layRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    lastSelectedItem = position;
                    notifyDataSetChanged();

                    listener.onItemClick(listKalaForMarjoeeModel , position);
                    Log.d("MarjoeeKala","position:" + position + " , listKalaForMarjoeeModel: "+ listKalaForMarjoeeModel.getNameKala() + " ,Tedad: " + listKalaForMarjoeeModel.getTedad());
                    editableCost.requestFocus();
                }
            });
        }
       TextWatcher textWatcher = new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               if (!charSequence.toString().isEmpty() ) {
                   String withoutComma = charSequence.toString().replace(",","").trim();
                   float modifiedPrice = Float.parseFloat(withoutComma);
                   if (modifiedPrice > listKalaForMarjoeeModel.get(getAdapterPosition()).getMablaghForosh()) {
                       customTextInputLayout.setError(context.getResources().getString(R.string.errorPrice));
                       listener.onTextChange(getAdapterPosition(),modifiedPrice, false);
                   }
                   else
                   {
                       customTextInputLayout.setError(null);
                       listener.onTextChange(getAdapterPosition(),modifiedPrice, true);
                   }

               }
           }

           @Override
           public void afterTextChanged(Editable editable) {


           }


       };

    }

    public class ViewHolderBaMabna extends RecyclerView.ViewHolder
    {
        LinearLayout layRoot;
        private TextView lblNameKala;
        private TextView lblShomareBach;
        private TextView lblTarikh;
        private TextView lblCost;

        private ImageView imgSelect;
        private CardView crdviewRoot;

        public ViewHolderBaMabna(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = view.findViewById(R.id.layRoot);
            lblNameKala = view.findViewById(R.id.lblNameKala);
            lblShomareBach = view.findViewById(R.id.lblShomareBach);
            lblTarikh = view.findViewById(R.id.lblTarikh);
            lblCost = view.findViewById(R.id.lblGheymat);
            imgSelect = view.findViewById(R.id.imgSelect);
            crdviewRoot = view.findViewById(R.id.crdviewRoot);

            lblNameKala.setTypeface(font);
            lblShomareBach.setTypeface(font);
            lblCost.setTypeface(font);
            lblTarikh.setTypeface(font);
        }

        void bind(final ListKalaForMarjoeeModel listKalaForMarjoeeModel , final int position , final OnItemClickListener listener)
        {

            DecimalFormat formatter = new DecimalFormat("#,###,###");
            lblNameKala.setText(listKalaForMarjoeeModel.getNameKala());
            lblShomareBach.setText(String.format("%1$s \n %2$s", context.getResources().getString(R.string.shomareBach), listKalaForMarjoeeModel.getShomarehBach()));
            lblCost.setText(String.format("%1$s \n %2$s", context.getResources().getString(R.string.mablaghForosh), formatter.format((int)listKalaForMarjoeeModel.getMablaghForosh())));

            try {
                Date date = sdf.parse(listKalaForMarjoeeModel.getTarikh());
                String tarikh = (String) DateFormat.format(Constants.DATE_SHORT_FORMAT_WITH_SLASH(), date);
                lblTarikh.setText(String.format("%1$s \n %2$s", context.getResources().getString(R.string.tarikhForosh), dateUtils.gregorianWithSlashToPersianSlash(tarikh)));
            } catch (Exception e){
                Log.i(TAG, "bind: " + e.getMessage() );
            }

            if (position == lastSelectedItem)
            {
                imgSelect.setImageResource(R.drawable.ic_success);
            }
            else
            {
                imgSelect.setImageResource(R.drawable.circle_tick_gray);
            }

            if (listKalaForMarjoeeModel.getIsKalaZayeatTolid()==1){
                crdviewRoot.setBackgroundResource(R.color.marjoeeKamel);
            } else {
                crdviewRoot.setBackgroundResource(R.color.defultTreasuryList);
            }


            layRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    lastSelectedItem = position;
                    notifyDataSetChanged();
                    listener.onItemClick(listKalaForMarjoeeModel , position);
                    Log.d("MarjoeeKala","position:" + position + " , listKalaForMarjoeeModel: "+ listKalaForMarjoeeModel.getNameKala() + " ,Tedad: " + listKalaForMarjoeeModel.getTedad());
                }
            });
        }

    }

    public interface OnItemClickListener
    {
        void onItemClick(ListKalaForMarjoeeModel listKalaForMarjoeeModel , int position);
        void onTextChange(int position,float modifiedPrice,boolean priceOk);
    }




}
