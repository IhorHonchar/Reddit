package ua.honchar.reddit.core.di

import org.kodein.di.Kodein
import org.kodein.di.android.androidCoreModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import ua.honchar.reddit.RedditApp
import ua.honchar.reddit.core.util.ImageLoadingUtil
import ua.honchar.reddit.domain.usecase.ILoadTopPostsUseCase
import ua.honchar.reddit.domain.usecase.LoadTopPostsUseCase

object AppModule {
    fun get(app: RedditApp) = Kodein.Module(this@AppModule::class.java.simpleName) {
        import(androidCoreModule(app))
        import(RemoteModule.get())
        import(LocalModule.get())


        bind() from provider {
            app.resources
        }

        bind() from singleton {
            ImageLoadingUtil(instance())
        }

        applyUseCaseModule()
    }

    private fun Kodein.Builder.applyUseCaseModule(){
        bind<ILoadTopPostsUseCase>() with singleton { LoadTopPostsUseCase(instance(), instance()) }
    }
}