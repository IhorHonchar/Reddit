package ua.honchar.reddit.core.di

import org.kodein.di.Kodein
import org.kodein.di.android.androidCoreModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import ua.honchar.reddit.RedditApp
import ua.honchar.reddit.core.network.RedditApi
import ua.honchar.reddit.domain.repository.remote.IRemoteRepository
import ua.honchar.reddit.domain.repository.remote.RemoteRepository

object AppModule {
    fun get(app: RedditApp) = Kodein.Module(this@AppModule::class.java.simpleName) {
        import(androidCoreModule(app))
        import(RetrofitModule.get())

        applyApiModule()
        applyRemoteRepositoryModule()

        applyDaoModule()
        applyLocalRepositoryModule()
    }

    private fun Kodein.Builder.applyApiModule() {
        bind<RedditApi>() with singleton { instance<Retrofit>().create(RedditApi::class.java) }
    }

    private fun Kodein.Builder.applyRemoteRepositoryModule() {
        bind<IRemoteRepository>() with singleton { RemoteRepository(instance()) }
    }

    private fun Kodein.Builder.applyDaoModule(){

    }

    private fun Kodein.Builder.applyLocalRepositoryModule(){

    }
}