package com.saphamrah.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.R;
import com.saphamrah.UIModel.JayezehByccKalaCodeModel;

import java.util.ArrayList;

public class JayezehAlertAdapter extends RecyclerView.Adapter<JayezehAlertAdapter.MyViewHolder> {

    private ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeModels;
    private int tedadKala;
    private Context context;

    public JayezehAlertAdapter(Context context, ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeModels, int tedadKala, double mablaghKol) {
        this.context = context;
        this.jayezehByccKalaCodeModels = jayezehByccKalaCodeModels;
        this.tedadKala = tedadKala;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.jayezeh_alert_customlist, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        // show tedad
        if (jayezehByccKalaCodeModels.get(position).getNoeTedadRial() == 1) {
            holder.lbl_tedadJayezeh.setText(context.getResources().getText(R.string.tedadJayezeh) + " " + jayezehByccKalaCodeModels.get(position).getTedadJayezeh());
            if (jayezehByccKalaCodeModels.get(position).getAz() <= tedadKala && tedadKala <= jayezehByccKalaCodeModels.get(position).getTa()) {
                holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
                holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            } else {
                holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
                holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
            }
        }
        // show rial
        else if (jayezehByccKalaCodeModels.get(position).getNoeTedadRial() == 2) {
            holder.lbl_tedadJayezeh.setText(context.getResources().getText(R.string.tedadJayezeh) + " " + jayezehByccKalaCodeModels.get(position).getTedadJayezeh());
            if (jayezehByccKalaCodeModels.get(position).getAz() <= tedadKala && tedadKala <= jayezehByccKalaCodeModels.get(position).getTa()) {
                holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
                holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            } else {
                holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
                holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorYellow));
            }
        }
        holder.lbl_az.setText(" از : " + jayezehByccKalaCodeModels.get(position).getAz());
        holder.lbl_ta.setText(" تا : " + jayezehByccKalaCodeModels.get(position).getTa());
        holder.lbl_beEza.setText(context.getResources().getText(R.string.beEza) + " " + jayezehByccKalaCodeModels.get(position).getBeEza());

    }


    @Override
    public int getItemCount() {

        return jayezehByccKalaCodeModels.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView lbl_az;
        private TextView lbl_ta;
        private TextView lbl_tedadJayezeh;
        private TextView lbl_beEza;
        private LinearLayout layStatusLeft;
        private LinearLayout layStatusRight;

        private MyViewHolder(View view) {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));

            lbl_az = view.findViewById(R.id.lbl_az);
            lbl_ta = view.findViewById(R.id.lbl_ta);
            lbl_tedadJayezeh = view.findViewById(R.id.lbl_tedadJayezeh);
            lbl_beEza = view.findViewById(R.id.lbl_beEza);
            layStatusLeft = view.findViewById(R.id.layStatusLeft);
            layStatusRight = view.findViewById(R.id.layStatusRight);

            lbl_az.setTypeface(font);
            lbl_beEza.setTypeface(font);
            lbl_ta.setTypeface(font);
            lbl_tedadJayezeh.setTypeface(font);

        }


    }
}
