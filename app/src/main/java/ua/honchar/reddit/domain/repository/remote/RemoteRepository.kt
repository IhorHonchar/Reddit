package ua.honchar.reddit.domain.repository.remote

import io.reactivex.Single
import ua.honchar.reddit.core.network.RedditApi
import ua.honchar.reddit.core.util.extensions.toModelView
import ua.honchar.reddit.domain.model.PostModelView

class RemoteRepository(
    private val api: RedditApi
) : IRemoteRepository {
    override fun fetchPosts(): Single<List<PostModelView?>> {
        return api.fetchPosts().map {
            it.data?.children?.map { postModel ->
                postModel.data?.toModelView()
            }.orEmpty()
        }
    }
}