package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class ListMoshtarianAdapter extends RecyclerSwipeAdapter<ListMoshtarianAdapter.ViewHolder>
{

    public static final int SEND = 1;
    public static final int DELETE = 2;
    public static final int SHOW_LOCATION = 3;
    public static final int PORSESHNAME = 4;
    public static final int ADAM_FAAL = 5;
    public static final int SUGGEST = 6;

    private Context context;
    private List<ListMoshtarianModel> listMoshtarianModels;
    private onItemClickListener listener;
    private DecimalFormat formatter;
    private SimpleDateFormat sdf;


    public ListMoshtarianAdapter(Context context, List<ListMoshtarianModel> listMoshtarianModels, onItemClickListener listener)
    {
        this.context = context;
        this.listMoshtarianModels = listMoshtarianModels;
        this.listener = listener;
        formatter = new DecimalFormat("#,###,###");
        sdf = new SimpleDateFormat(Constants.DATE_SHORT_FORMAT());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.moshtarian_customlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left , holder.itemView.findViewById(R.id.layLeft));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right , holder.itemView.findViewById(R.id.layRight));

        ListMoshtarianModel listMoshtarianModel = listMoshtarianModels.get(position);
        holder.txtviewRadif.setText(String.valueOf(position+1));
        holder.txtviewFullNameCode.setText(String.format("%1$s - %2$s", listMoshtarianModel.getCodeMoshtaryOld(), listMoshtarianModel.getNameMoshtary()));
        holder.txtviewAddress.setText(listMoshtarianModel.getAddress());
        holder.txtviewTelephone.setText(listMoshtarianModel.getTelephone());
        holder.txtviewMobile.setText(listMoshtarianModel.getMobile());
        try
        {
            holder.txtviewLastPurchaseCost.setText(formatter.format(listMoshtarianModel.getMablaghAkharinFaktor()));
        }catch (Exception e){}

        try
        {
            holder.txtviewLastPurchaseDate.setText(sdf.format(listMoshtarianModel.getTarikhAkharinFaktor()));
        }catch (Exception e){}

        if (listMoshtarianModel.getExtraProp_Status() == ListMoshtarianModel.SAVED_PORSESHNAME)
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGreen));
        }
        else if (listMoshtarianModel.getExtraProp_Status() == ListMoshtarianModel.SAVED_ADAM_FAAL)
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorRed));
        }
        else
        {
            holder.layStatusRight.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
            holder.layStatusLeft.setBackgroundColor(context.getResources().getColor(R.color.colorGray));
        }


        if (listMoshtarianModel.getCodeVazeiatAmargar().equals("0"))
        {
            if (listMoshtarianModel.getCcMoshtary() > 0 && listMoshtarianModel.getCcPorseshnameh() == 0)
            {
                holder.imgviewStatus.setImageResource(R.drawable.ic_way);
            }
            else if (listMoshtarianModel.getCcMoshtary() == 0 && listMoshtarianModel.getCcPorseshnameh() > 0)
            {
                holder.imgviewStatus.setImageResource(R.drawable.ic_porseshname);
            }
        }
        else if (listMoshtarianModel.getCodeVazeiatAmargar().equals("1000"))
        {
            holder.imgviewStatus.setImageResource(R.drawable.ic_mobile_navigator);
        }

    }

    @Override
    public int getItemCount()
    {
        return listMoshtarianModels.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position)
    {
        return R.id.swipe;
    }


    public interface onItemClickListener
    {
        void onItemClick(int operation, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        SwipeLayout swipeLayout;
        ImageView imgviewStatus;
        TextView txtviewRadif;
        TextView txtviewFullNameCode;
        TextView txtviewAddress;
        TextView txtviewTelephone;
        TextView txtviewMobile;
        TextView txtviewLastPurchaseDate;
        TextView txtviewLastPurchaseCost;
        RelativeLayout layAdamFaal;
        RelativeLayout layPorseshname;
        RelativeLayout laySuggest;
       /* RelativeLayout laySend;
        RelativeLayout layDelete;*/
        RelativeLayout layLocation;
        LinearLayout layStatusRight;
        LinearLayout layStatusLeft;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            Typeface font = Typeface.createFromAsset(context.getAssets(), context.getResources().getString(R.string.fontPath));

            swipeLayout = itemView.findViewById(R.id.swipe);
            imgviewStatus = itemView.findViewById(R.id.imgviewStatus);
            txtviewRadif = itemView.findViewById(R.id.lblRadif);
            txtviewFullNameCode = itemView.findViewById(R.id.lblCustomerFullNameCode);
            txtviewAddress = itemView.findViewById(R.id.lblAddress);
            txtviewTelephone = itemView.findViewById(R.id.lblTelephone);
            txtviewMobile = itemView.findViewById(R.id.lblMobile);
            txtviewLastPurchaseDate = itemView.findViewById(R.id.lblLastPurchaseDate);
            txtviewLastPurchaseCost = itemView.findViewById(R.id.lblLastPurchaseCost);
            layAdamFaal = itemView.findViewById(R.id.layAdamFaal);
            layPorseshname = itemView.findViewById(R.id.layPorseshname);
            laySuggest = itemView.findViewById(R.id.laySuggest);
            /*laySend = itemView.findViewById(R.id.laySend);
            layDelete = itemView.findViewById(R.id.layDelete);*/
            layLocation = itemView.findViewById(R.id.layLocation);
            layStatusRight = itemView.findViewById(R.id.layStatusRight);
            layStatusLeft = itemView.findViewById(R.id.layStatusLeft);


            txtviewRadif.setTypeface(font);
            txtviewFullNameCode.setTypeface(font);
            txtviewAddress.setTypeface(font);
            txtviewTelephone.setTypeface(font);
            txtviewMobile.setTypeface(font);
            txtviewLastPurchaseDate.setTypeface(font);
            txtviewLastPurchaseCost.setTypeface(font);


            /*laySend.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    listener.onItemClick(SEND, getAdapterPosition());
                }
            });


            layDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    listener.onItemClick(DELETE, getAdapterPosition());
                }
            });*/


            layLocation.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    listener.onItemClick(SHOW_LOCATION, getAdapterPosition());
                }
            });

            layPorseshname.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    listener.onItemClick(PORSESHNAME, getAdapterPosition());
                }
            });
            laySuggest.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    listener.onItemClick(SUGGEST, getAdapterPosition());
                }
            });

            layAdamFaal.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    listener.onItemClick(ADAM_FAAL, getAdapterPosition());
                }
            });

        }


    }

}
