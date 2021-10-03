package ua.honchar.reddit.domain.model

import ua.honchar.reddit.core.base.adapter.IAdapterItem

data class PostModelView(
    override val id: String,
    val name: String,
    val title: String,
    val author: String,
    val thumbnail: String,
    val numComments: Int,
    val created: Long,
    val ratio: Double,
    val ups: Int
): IAdapterItem
