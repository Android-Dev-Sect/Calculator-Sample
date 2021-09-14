package com.kre4.calculator.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kre4.calculator.R

class CustomRecyclerAdapter(private val items: List<Item> ):

    RecyclerView.Adapter<CustomRecyclerAdapter.CustomViewHolder>()
    {
        class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var largeText: TextView? = null
            var smallText: TextView? = null

            init {
                largeText = itemView.findViewById(R.id.item_title)
                smallText = itemView.findViewById(R.id.item_subtitle)
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val itemView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
            return CustomViewHolder(itemView)
        }
        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.largeText?.text = items[position].expression
            holder.smallText?.text = items[position].result
        }

        override fun getItemCount() = items.size
    }
