package ua.honchar.reddit.core.base.presentation

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.json.JSONObject
import retrofit2.HttpException
import ua.honchar.reddit.R
import java.net.ConnectException
import java.net.UnknownHostException

abstract class BaseViewModel(
    val resources: Resources
): ViewModel() {

    private var subscription: CompositeDisposable = CompositeDisposable()
    private val onErrorAction = MutableLiveData<String?>()

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }

    fun getErrorAction(): LiveData<String?> = onErrorAction.distinctUntilChanged()

    fun handleError(error: Throwable?) {
        val errorMessage = getErrorMessage(error)
        onErrorAction.postValue(errorMessage)
    }

    fun Disposable.unsubscribeOnCleared(): Disposable {
        subscription.add(this)
        return this
    }

    private fun getErrorMessage(error: Throwable?): String? {
        var errorMessage: String? = error?.message
        when(error){
            is UnknownHostException -> {
                errorMessage = resources.getString(R.string.error_server_connection)
            }
            is HttpException -> {
                val response = error.response()?.errorBody()
                errorMessage = response?.let {
                    try {
                        JSONObject(response.string()).getString("message")
                    } catch (e: Exception) {
                        e.message
                    } finally {
                        it.close()
                    }
                }
            }
        }
        if (errorMessage.isNullOrEmpty()){
            errorMessage = when {
                isNoInternetError(error) -> resources.getString(R.string.no_internet_connection)
                else -> resources.getString(R.string.some_wrong)
            }
        }
        return errorMessage
    }

    private fun isNoInternetError(throwable: Throwable?) =
        throwable is UnknownHostException || throwable is ConnectException
}