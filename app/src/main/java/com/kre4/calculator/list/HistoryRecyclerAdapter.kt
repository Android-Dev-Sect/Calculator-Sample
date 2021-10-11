package com.kre4.calculator.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kre4.calculator.R

class HistoryRecyclerAdapter(private var historyListItems: List<HistoryListItem> ):

    RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryViewHolder>()
    {
        class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val expression: TextView = itemView.findViewById(R.id.item_title)
            private val result: TextView = itemView.findViewById(R.id.item_subtitle)
            fun bind(historyListItem: HistoryListItem){
                expression.text = historyListItem.expression
                result.text = historyListItem.result

            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
            val itemView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
            return HistoryViewHolder(itemView)
        }
        override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
            holder.bind(historyListItems[position])
        }

        override fun getItemCount() = historyListItems.size

        fun setNewList(historyListItems: List<HistoryListItem> ){
            this.historyListItems = historyListItems
        }
    }
