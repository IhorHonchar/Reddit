package ua.honchar.reddit.presentation.posts.adapter.holder

import ua.honchar.reddit.core.base.adapter.BaseHolder
import ua.honchar.reddit.databinding.HolderPostBinding
import ua.honchar.reddit.domain.model.PostModelView

class PostHolder(
    binding: HolderPostBinding
): BaseHolder<PostModelView>(binding) {

    override fun onBind(item: PostModelView) {
        binding.let {

        }
    }
}