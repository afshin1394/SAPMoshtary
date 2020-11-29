package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.CustomView.CustomTextInputLayout;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.R;

import java.util.List;
import java.util.Map;

public class SelectKalaCountAdapter extends RecyclerView.Adapter<SelectKalaCountAdapter.ViewHolder>
{
    private Context context;
    private List<KalaModel> kalaModels;
    private Map<Integer,Integer> mapSelectedGoods;
    private OnItemClickListener listener;


    public SelectKalaCountAdapter(Context context, List<KalaModel> kalaModels, Map<Integer,Integer> mapSelectedGoods, OnItemClickListener listener)
    {
        this.context = context;
        this.kalaModels = kalaModels;
        this.mapSelectedGoods = mapSelectedGoods;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_kala_count_customlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        // use adad for selected count instead of add new field
        KalaModel kalaModel = kalaModels.get(position);
        holder.lblCodeNameKala.setText(String.format("%1$s - %2$s", kalaModel.getNameBrand(), kalaModel.getNameKala()));
        Integer count = mapSelectedGoods.get(kalaModel.getCcKalaCode());
        holder.editTextCount.setText((count == null || count == 0 || count == -1) ? "" : String.valueOf(count));
    }

    @Override
    public int getItemCount()
    {
        return kalaModels.size();
    }

    public interface OnItemClickListener
    {
        void onRemoveFocus(KalaModel kalaModel , int position , String count);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView lblCodeNameKala;
        EditText editTextCount;
        CustomTextInputLayout textInputLayoutCount;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            lblCodeNameKala = itemView.findViewById(R.id.lblCodeNameKala);
            editTextCount = itemView.findViewById(R.id.txtCount);
            textInputLayoutCount = itemView.findViewById(R.id.txtinputCount);

            lblCodeNameKala.setTypeface(font);
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
                    listener.onRemoveFocus(kalaModels.get(getAdapterPosition()) , getAdapterPosition() , s.toString());
                }
            });

        }
    }
}
