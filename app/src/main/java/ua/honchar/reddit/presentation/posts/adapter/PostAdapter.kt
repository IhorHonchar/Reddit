package ua.honchar.reddit.presentation.posts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ua.honchar.reddit.core.base.adapter.BaseAdapter
import ua.honchar.reddit.core.util.ImageLoadingUtil
import ua.honchar.reddit.databinding.HolderPostBinding
import ua.honchar.reddit.domain.model.PostModelView
import ua.honchar.reddit.presentation.posts.adapter.holder.PostHolder

class PostAdapter(private val imageLoadingUtil: ImageLoadingUtil) : BaseAdapter<PostModelView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PostHolder(
            HolderPostBinding.inflate(LayoutInflater.from(parent.context)),
            imageLoadingUtil
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostHolder) {
            holder.onBind(itemsList[position])
        }
    }
}