package ua.honchar.reddit.presentation

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

object MainModule {
    fun get(activity: MainActivity) = Kodein.Module(MainActivity::class.java.simpleName){
        bind(overrides = true) from provider {
            activity.resources
        }
    }

    private const val TAG = "MainActivity"
}