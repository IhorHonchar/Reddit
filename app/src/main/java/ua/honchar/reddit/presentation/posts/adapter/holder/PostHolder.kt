package ua.honchar.reddit.presentation.posts.adapter.holder

import ua.honchar.reddit.R
import ua.honchar.reddit.core.base.adapter.BaseHolder
import ua.honchar.reddit.core.util.ImageLoadingUtil
import ua.honchar.reddit.core.util.extensions.thousandsWithK
import ua.honchar.reddit.databinding.HolderPostBinding
import ua.honchar.reddit.domain.model.PostModelView

class PostHolder(
    private val binding: HolderPostBinding,
    private val imageLoadingUtil: ImageLoadingUtil
) : BaseHolder<PostModelView>(binding) {

    override fun onBind(item: PostModelView) {
        binding.apply {

            userName.text = context.getString(
                R.string.user_name_template,
                item.author,
                "temporary"
            )
            title.text = item.title
            ups.text = item.ups.thousandsWithK()
            comments.text = item.numComments.thousandsWithK()

            imageLoadingUtil.loadImage(item.thumbnail, thumbnail)
        }
    }
}