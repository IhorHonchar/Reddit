package ua.honchar.reddit.domain.repository.remote

import io.reactivex.Single
import ua.honchar.reddit.domain.model.PostData
import ua.honchar.reddit.domain.model.PostModelView

interface IRemoteRepository {
    fun fetchPosts(): Single<List<PostModelView?>>
}