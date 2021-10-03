package ua.honchar.reddit.presentation.posts

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ua.honchar.reddit.core.base.presentation.BaseViewModel
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

    fun loadPosts() {
        loadTopPostsUseCase.loadPosts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                postsLiveData.postValue(it)
            }, ::handleError).unsubscribeOnCleared()
    }
}