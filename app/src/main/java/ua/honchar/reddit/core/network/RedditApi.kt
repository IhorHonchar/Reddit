package ua.honchar.reddit.core.network

import io.reactivex.Single
import retrofit2.http.GET
import ua.honchar.reddit.domain.model.PostsResponseModel

interface RedditApi {

    @GET("/top")
    fun fetchPosts(): Single<PostsResponseModel>
}