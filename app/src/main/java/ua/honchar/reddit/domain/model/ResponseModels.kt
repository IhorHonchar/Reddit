package ua.honchar.reddit.domain.model

import com.google.gson.annotations.SerializedName

data class PostsResponseModel(
    val kind: String? = null,
    val data: PostsContainerModel? = null
)

data class PostsContainerModel(
    val after: String? = null,
    val dist: Int? = null,
    val modhash: String? = null,
    @SerializedName("geo_filter")
    val geoFilter: String? = null,
    val children: List<PostModel>
)

data class PostModel(
    val kind: String? = null,
    val data: PostData? = null
)

data class PostData(
    val id: String? = null,
    val name: String? = null,
    val title: String? = null,
    val author: String? = null,
    val thumbnail: String? = null,
    @SerializedName("num_comments")
    val numComments: Int? = null,
    @SerializedName("created_utc")
    val created: Long? = null,
    @SerializedName("upvote_ratio")
    val ratio: Double? = null,
    val ups: Int? = null,
    val permalink: String? = null
)