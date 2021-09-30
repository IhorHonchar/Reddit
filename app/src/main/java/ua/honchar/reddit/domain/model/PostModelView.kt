package ua.honchar.reddit.domain.model

data class PostModelView(
    val id: String,
    val name: String,
    val title: String,
    val author: String,
    val thumbnail: String,
    val numComments: Int,
    val created: Long,
    val ratio: Double,
    val ups: Int
)
