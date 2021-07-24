package com.squareboat.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareboat.R
import com.squareboat.model.pojo.DataItem
import com.squareboat.util.RecyclerviewOnClickListener


class AppliedJobAdapter(
    var listener: RecyclerviewOnClickListener, private var ItemModelArrayList: List<DataItem?>?
) : RecyclerView.Adapter<AppliedJobAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_job_data, parent, false)
        return ViewHolder(v)
    }


    @SuppressLint("RecyclerView", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTV.text = ItemModelArrayList!![position]!!.title
        holder.descTV.text = ItemModelArrayList!![position]!!.description
        holder.locationTV.text = "Location : " + ItemModelArrayList!![position]!!.location
        holder.parentLL.setOnLongClickListener {
            listener.recyclerviewClick(ItemModelArrayList!![position]!!.id!!)
            true
        }

    }


    override fun getItemCount(): Int {
        return ItemModelArrayList!!.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTV: TextView = itemView.findViewById(R.id.titleTV)
        var descTV: TextView = itemView.findViewById(R.id.descTV)
        var locationTV: TextView = itemView.findViewById(R.id.locationTV)
        var parentLL:LinearLayout = itemView.findViewById(R.id.parentLL)
    }

}