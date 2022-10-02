package com.saphamrah.customer.adapter.recycler;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.customer.R;
import com.saphamrah.customer.data.network.model.MenuModel;

import java.util.ArrayList;

public class DialogMenuAdapter extends RecyclerView.Adapter<DialogMenuAdapter.ViewHolder>
{

    private Context context;
//    private final OnItemClickListener listener;
    private ArrayList<MenuModel> models;

    public DialogMenuAdapter(Context context, ArrayList<MenuModel> models )
    {
        this.context = context;
//        this.listener = listener;
        this.models = models;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_sheet_menu , parent , false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.title.setText(models.get(0).getTitle());
    }


    @Override
    public int getItemCount()
    {
        return models.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView title;

        public ViewHolder(View view)
        {
            super(view);
//            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getResources().getString(R.string.fontPath));

            title = view.findViewById(R.id.text_sheet_menu);

        }

//        void bind(final KalaElamMarjoeeModel kalaElamMarjoeeModel , final int position , final OnItemClickListener listener)
//        {
//            layDelete.setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    swipe.close(true);
//                    listener.onItemClick(Constants.DELETE(), kalaElamMarjoeeModel , position);
//                }
//            });
//
//            layEditCount.setOnClickListener(new View.OnClickListener() {
//                @Override public void onClick(View v) {
//                    swipe.close(true);
//                    listener.onItemClick(Constants.CLEARING(), kalaElamMarjoeeModel , position);
//                }
//            });
//        }

    }


//    public interface OnItemClickListener
//    {
//        void onItemClick(int operation, KalaElamMarjoeeModel kalaElamMarjoeeModel , int position);
//    }


//    @Override
//    public int getSwipeLayoutResourceId(int position) {
//        return R.id.swipe;
//    }

}
