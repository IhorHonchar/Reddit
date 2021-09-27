package ua.honchar.reddit.domain.usecase

import io.reactivex.Single
import ua.honchar.reddit.domain.model.PostModelView

interface ILoadTopPostsUseCase {
    fun loadPosts(): Single<List<PostModelView>>
}