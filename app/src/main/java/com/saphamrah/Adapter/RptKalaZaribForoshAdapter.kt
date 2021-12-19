package com.saphamrah.Adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.saphamrah.R
import com.saphamrah.UIModel.kalaZaribForosh.KalaZaribForoshUiModel
import kotlinx.android.synthetic.main.rpt_kala_zarib_forosh_customlist.view.*
import me.anwarshahriar.calligrapher.Calligrapher
import java.util.ArrayList

class RptKalaZaribForoshAdapter(
    private val context: Context,
    private val models: ArrayList<KalaZaribForoshUiModel>
) : RecyclerView.Adapter<RptKalaZaribForoshAdapter.ViewHolder>() {

    private var lastPosition = -1
    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.rpt_kala_zarib_forosh_customlist, parent, false)
        return ViewHolder(view , context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(models[position], context)
        setAnimation(holder.itemView, position)
    }

    override fun getItemCount(): Int {
        return models.size;
    }

class ViewHolder(view: View ,context: Context) : RecyclerView.ViewHolder(view) {
    init {
        val font = Typeface.createFromAsset(context.assets, context.resources.getString(R.string.fontPath))
        itemView.txtRadif.typeface = font
        itemView.txtZaribForosh.typeface = font
        itemView.txtNameKalaCodeKala.typeface = font
    }
            fun bind(model: KalaZaribForoshUiModel, context: Context) {
            itemView.txtRadif.text = (adapterPosition + 1).toString()
            itemView.txtZaribForosh.text = String.format(
                "%1\$s : %2\$s %3\$s",
                context.resources.getString(R.string.zaribForosh),
                model.ZaribForosh,
                context.resources.getString(R.string.adad)
            )
            itemView.txtNameKalaCodeKala.text = String.format(
                "%1\$s : %2\$s - %3\$s",
                context.resources.getString(R.string.nameKala),
                model.NameKala,
                model.CodeKala
            )

        }
}

    private fun setAnimation(viewToAnimate: View, position: Int) {
        if (position > lastPosition) {
            val animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
            viewToAnimate.startAnimation(animation)
            lastPosition = position
        }
    }

}


