package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.MoshtaryChidmanModel;
import com.saphamrah.PubFunc.ImageUtils;
import com.saphamrah.R;

import java.util.ArrayList;

public class MoshtaryChidmanAdapter extends RecyclerView.Adapter<MoshtaryChidmanAdapter.ViewHolder> {
    Context context;
    ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels;
    IMoshtaryChidmanAdapter iMoshtaryChidmanAdapter;
    int selectedPosition = -1;


    public MoshtaryChidmanAdapter(Context context, ArrayList<MoshtaryChidmanModel> moshtaryChidmanModels, IMoshtaryChidmanAdapter iMoshtaryChidmanAdapter) {
        this.context = context;
        this.moshtaryChidmanModels = moshtaryChidmanModels;
        this.iMoshtaryChidmanAdapter = iMoshtaryChidmanAdapter;
    }

    @NonNull
    @Override
    public MoshtaryChidmanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_moshtary_chidman_customlist, parent, false);
        return new MoshtaryChidmanAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoshtaryChidmanAdapter.ViewHolder holder, int position) {
        MoshtaryChidmanModel moshtaryChidmanModel = moshtaryChidmanModels.get(position);
        holder.bind(moshtaryChidmanModel, position);
    }

    @Override
    public int getItemCount() {
        return moshtaryChidmanModels.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layRoot;
        private TextView chidmanDetails;
        private ImageView chidmanImg;
        private CardView chidmanCard;

        public ViewHolder(View view) {
            super(view);
            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));
            layRoot = view.findViewById(R.id.layRoot);
            chidmanDetails = view.findViewById(R.id.chidmanDetails);
            chidmanImg = view.findViewById(R.id.chidmanImg);
            chidmanCard = view.findViewById(R.id.chidmanCard);
            chidmanDetails.setTypeface(font);
        }

        void bind(MoshtaryChidmanModel moshtaryChidmanModel, int position) {
            if (position == selectedPosition) {
                chidmanCard.setCardBackgroundColor(context.getResources().getColor(R.color.colorLightGreen));
            } else {
                chidmanCard.setCardBackgroundColor(Color.WHITE);
            }
            chidmanDetails.setText(moshtaryChidmanModel.getDescription());

            if (moshtaryChidmanModel.getImage() != null)
                chidmanImg.setImageBitmap(new ImageUtils().convertByteArrayToBitmap(context, moshtaryChidmanModel.getImage()));
            else
                chidmanImg.setImageResource(R.drawable.ic_stand_foreground);

            itemView.setOnClickListener(view -> {

                iMoshtaryChidmanAdapter.onItemClick(moshtaryChidmanModel,selectedPosition);
                selectedPosition = -1;
                notifyDataSetChanged();

            });
            itemView.setOnLongClickListener(view -> {
                selectedPosition = position;
                iMoshtaryChidmanAdapter.onItemLongClick(moshtaryChidmanModel, selectedPosition);
                notifyDataSetChanged();
                return false;
            });
        }

    }

    public interface IMoshtaryChidmanAdapter {
        void onItemClick(MoshtaryChidmanModel moshtaryChidmanModel , int selectedPosition);

        void onItemLongClick(MoshtaryChidmanModel moshtaryChidmanModel, int selectedPosition);
    }



}
