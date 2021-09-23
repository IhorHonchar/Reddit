package ua.honchar.reddit.domain.repository.remote

import ua.honchar.reddit.core.network.RedditApi

class RemoteRepository(
    private val api: RedditApi
): IRemoteRepository {
}