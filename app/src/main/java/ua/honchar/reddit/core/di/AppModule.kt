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
import ua.honchar.reddit.domain.usecase.ILoadTopPostsUseCase
import ua.honchar.reddit.domain.usecase.LoadTopPostsUseCase

object AppModule {
    fun get(app: RedditApp) = Kodein.Module(this@AppModule::class.java.simpleName) {
        import(androidCoreModule(app))
        import(RemoteModule.get())

        applyDaoModule()
        applyLocalRepositoryModule()

        applyUseCaseModule()
    }



    private fun Kodein.Builder.applyDaoModule(){

    }

    private fun Kodein.Builder.applyLocalRepositoryModule(){

    }

    private fun Kodein.Builder.applyUseCaseModule(){
        bind<ILoadTopPostsUseCase>() with singleton { LoadTopPostsUseCase(instance(), instance()) }
    }
}