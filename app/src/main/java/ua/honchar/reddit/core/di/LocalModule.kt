package ua.honchar.reddit.core.di

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton
import ua.honchar.reddit.domain.repository.local.ILocalRepository
import ua.honchar.reddit.domain.repository.local.LocalRepository

object LocalModule {
    fun get() = Kodein.Module(LocalModule::class.java.simpleName){

        applyDaoModule()
        applyLocalRepositoryModule()
    }

    private fun Kodein.Builder.applyDaoModule(){

    }

    private fun Kodein.Builder.applyLocalRepositoryModule(){
        bind<ILocalRepository>() with singleton { LocalRepository() }
    }
}