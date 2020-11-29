package com.saphamrah.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saphamrah.R;
import com.saphamrah.Valhalla.Maneuver;

import java.util.List;

public class RoutingDirectionAdapter extends RecyclerView.Adapter<RoutingDirectionAdapter.ViewHolder>
{

    private Context context;
    private List<Maneuver> maneuvers;


    public RoutingDirectionAdapter(Context context, List<Maneuver> maneuvers)
    {
        this.context = context;
        this.maneuvers = maneuvers;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.routing_direction_customview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        String identifier = "drawable/ic_route_type_" + maneuvers.get(position).getType();
        String instruction = maneuvers.get(position).getInstruction();
        if (maneuvers.get(position).getVerbalPostTransitionInstruction() != null)
        {
            instruction += "\n" + maneuvers.get(position).getVerbalPostTransitionInstruction();
        }
        holder.lblDirection.setText(instruction);
        holder.imgDirection.setImageResource(context.getResources().getIdentifier(identifier, null, context.getPackageName()));
    }

    @Override
    public int getItemCount()
    {
        return maneuvers.size();
    }





    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView lblDirection;
        private ImageView imgDirection;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            Typeface font = Typeface.createFromAsset(context.getAssets() , context.getString(R.string.fontPath));

            lblDirection = itemView.findViewById(R.id.lblRoutingInstruction);
            imgDirection = itemView.findViewById(R.id.imgRoutingDirection);

            lblDirection.setTypeface(font);
        }
    }


}
