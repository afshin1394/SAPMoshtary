package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.R;
import com.saphamrah.UIModel.JayezehEntekhabiMojodiModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SelectBonusAdapter extends RecyclerView.Adapter<SelectBonusAdapter.ViewHolder>
{

    private final OnItemClickListener listener;
    private Context context;
    private ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels;
    private int lastSelectedItem;
    private DecimalFormat formatter;

    public SelectBonusAdapter(Context context, ArrayList<JayezehEntekhabiMojodiModel> jayezehEntekhabiMojodiModels , OnItemClickListener listener)
    {
        this.listener = listener;
        this.context = context;
        this.jayezehEntekhabiMojodiModels = jayezehEntekhabiMojodiModels;
        this.lastSelectedItem = -1;
        formatter = new DecimalFormat("#,###,###");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_bonus_customlist , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.lblCodeNameKala.setText(String.format("%1$s - %2$s", jayezehEntekhabiMojodiModels.get(position).getCodeKalaOld(), jayezehEntekhabiMojodiModels.get(position).getNameKala()));
        holder.lblCount.setText(String.format("%1$s : %2$s", context.getResources().getString(R.string.tedadMojodi), jayezehEntekhabiMojodiModels.get(position).getTedad()));
        holder.lblCost.setText(String.format("%1$s %2$s", formatter.format(jayezehEntekhabiMojodiModels.get(position).getGheymatForosh()), context.getString(R.string.rial)));
        holder.editTextCount.setText(String.valueOf(jayezehEntekhabiMojodiModels.get(position).getSelectedCount()).equals("0") ? "" : String.valueOf(jayezehEntekhabiMojodiModels.get(position).getSelectedCount()));

        //holder.bind(jayezehEntekhabiMojodiModels.get(position) , position , holder.editTextCount.getText().toString() , listener);
    }


    @Override
    public int getItemCount()
    {
        return jayezehEntekhabiMojodiModels.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        LinearLayout layRoot;
        private TextView lblCodeNameKala;
        private TextView lblCount;
        private TextView lblCost;
        private EditText editTextCount;
        private CustomTextInputLayout textInputLayoutCount;
        //private ImageView imgSelect;

        public ViewHolder(View view)
        {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            layRoot = view.findViewById(R.id.layRoot);
            lblCodeNameKala = view.findViewById(R.id.lblCodeNameKala);
            lblCount = view.findViewById(R.id.lblCount);
            lblCost = view.findViewById(R.id.lblCost);
            editTextCount = view.findViewById(R.id.txtCount);
            textInputLayoutCount = view.findViewById(R.id.txtinputCount);
            //imgSelect = view.findViewById(R.id.imgSelect);

            lblCodeNameKala.setTypeface(font);
            lblCount.setTypeface(font);
            lblCost.setTypeface(font);
            editTextCount.setTypeface(font);
            textInputLayoutCount.setTypeface(font);


            editTextCount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    listener.onRemoveFocus(jayezehEntekhabiMojodiModels.get(getAdapterPosition()) , getAdapterPosition() , s.toString());
                }
            });

        }



        /*void bind(final JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel , final int position , final String count , final OnItemClickListener listener)
        {
            editTextCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus)
                    {
                        int intCount = 0;
                        try
                        {
                            intCount = Integer.parseInt(((EditText)getChildAt(position).findViewById(R.id.txtCount)).getText().toString());
                        }
                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                        listener.onRemoveFocus(jayezehEntekhabiMojodiModel , position , count);
                    }
                }
            });
        }*/

    }


    public interface OnItemClickListener
    {
        void onRemoveFocus(JayezehEntekhabiMojodiModel jayezehEntekhabiMojodiModel , int position , String count);
    }



}
