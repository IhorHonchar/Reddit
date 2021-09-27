package ua.honchar.reddit.domain.usecase

import io.reactivex.Single
import ua.honchar.reddit.domain.model.PostModelView
import ua.honchar.reddit.domain.repository.local.LocalRepository
import ua.honchar.reddit.domain.repository.remote.RemoteRepository

class LoadTopPostsUseCase(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
): ILoadTopPostsUseCase {

    override fun loadPosts(): Single<List<PostModelView>> {
        return remoteRepository.fetchPosts().map {
            it.filterNotNull().let { notNullList ->
                localRepository.save(notNullList)
                notNullList
            }
        }
    }
}