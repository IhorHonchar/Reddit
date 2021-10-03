package ua.honchar.reddit.core.base.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<itemType: IAdapterItem>: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val itemsList: List<itemType> = listOf()

    override fun getItemCount() = itemsList.size

    @SuppressLint("NotifyDataSetChanged")
    open fun submitList(list: List<itemType>){
        itemsList.toMutableList().apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }
}