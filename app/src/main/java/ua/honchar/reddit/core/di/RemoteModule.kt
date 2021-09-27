package ua.honchar.reddit.core.di

import io.reactivex.internal.schedulers.RxThreadFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ua.honchar.reddit.BuildConfig
import ua.honchar.reddit.core.network.RedditApi
import ua.honchar.reddit.domain.repository.remote.IRemoteRepository
import ua.honchar.reddit.domain.repository.remote.RemoteRepository
import java.util.concurrent.TimeUnit

object RemoteModule {
    private const val TIMEOUT_SEC: Long = 2

    fun get() = Kodein.Module(this@RemoteModule::class.java.simpleName) {

        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(instance())
                .build()
        }

        bind<OkHttpClient>() with singleton {
            val builder = OkHttpClient.Builder()

            builder.connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            builder.retryOnConnectionFailure(true)

            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
            }

            builder.build()
        }

        applyApiModule()
        applyRemoteRepositoryModule()
    }

    private fun Kodein.Builder.applyApiModule() {
        bind<RedditApi>() with singleton { instance<Retrofit>().create(RedditApi::class.java) }
    }

    private fun Kodein.Builder.applyRemoteRepositoryModule() {
        bind<IRemoteRepository>() with singleton { RemoteRepository(instance()) }
    }
}