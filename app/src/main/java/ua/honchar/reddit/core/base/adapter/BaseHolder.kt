package ua.honchar.reddit.core.base.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseHolder<IT: IAdapterItem>(
    private val binding: ViewBinding
): RecyclerView.ViewHolder(binding.root) {

    val context: Context
    get() = itemView.context

    abstract fun onBind(item: IT)
}