package ua.honchar.reddit.core.base.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseHolder<IT: IAdapterItem>(
    val binding: ViewBinding
): RecyclerView.ViewHolder(binding.root) {

    abstract fun onBind(item: IT)
}