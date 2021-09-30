package ua.honchar.reddit.domain.usecase

import io.reactivex.Single
import ua.honchar.reddit.domain.model.PostModelView
import ua.honchar.reddit.domain.repository.local.ILocalRepository
import ua.honchar.reddit.domain.repository.remote.IRemoteRepository

class LoadTopPostsUseCase(
    private val remoteRepository: IRemoteRepository,
    private val localRepository: ILocalRepository
) : ILoadTopPostsUseCase {

    override fun loadPosts(): Single<List<PostModelView>> {
        return remoteRepository.fetchPosts().map {
            it.filterNotNull().let { notNullList ->
                localRepository.save(notNullList)
                notNullList
            }
        }
    }
}