package ua.honchar.reddit.presentation.main

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import ua.honchar.reddit.core.base.BaseViewModel
import ua.honchar.reddit.domain.model.PostModelView
import ua.honchar.reddit.domain.usecase.ILoadTopPostsUseCase

class PostsViewModel(
    private val loadTopPostsUseCase: ILoadTopPostsUseCase,
    resources: Resources
) : BaseViewModel(
    resources
) {

    private val postsLiveData = MutableLiveData<List<PostModelView>>()

    fun checkPosts(): LiveData<List<PostModelView>> = postsLiveData.distinctUntilChanged()


    init {
        loadTopPostsUseCase.loadPosts().subscribe({
              postsLiveData.postValue(it)
        }, ::handleError).unsubscribeOnCleared()
    }
}