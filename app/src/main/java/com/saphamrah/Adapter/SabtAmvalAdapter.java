package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.AmvalModel;
import com.saphamrah.R;

import java.util.ArrayList;
import java.util.List;

public class SabtAmvalAdapter extends RecyclerView.Adapter<SabtAmvalAdapter.ViewHolder>
{

    private List<AmvalModel> models;
    private Context context;


    public SabtAmvalAdapter(Context context, ArrayList<AmvalModel> models)
    {
        this.context = context;
        this.models = models;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sabamval_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

        AmvalModel model = models.get(position);
        holder.nameAmval.setText(model.getNameAmval());
        holder.abaadTxt.setText(model.getTol() + " * "  + model.getArz() + " * " + model.getArz());

    }

    @Override
    public int getItemCount()
    {
        return models.size();
    }

    public interface onClickListener
    {
        void onItemClickListener(int operation, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameAmval;
        TextView abaadTxt;


        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));

            nameAmval = itemView.findViewById(R.id.lblNameAmval);
            abaadTxt = itemView.findViewById(R.id.abadtxt);

            nameAmval.setTypeface(font);
            abaadTxt.setTypeface(font);

        }
    }

}
