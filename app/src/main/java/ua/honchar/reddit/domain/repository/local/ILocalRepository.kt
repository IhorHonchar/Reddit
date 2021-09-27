package ua.honchar.reddit.domain.repository.local

import ua.honchar.reddit.domain.model.PostModelView

interface ILocalRepository {
    fun save(list: List<PostModelView>)
}