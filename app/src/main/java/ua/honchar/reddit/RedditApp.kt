package ua.honchar.reddit

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import ua.honchar.reddit.core.di.AppModule

class RedditApp: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(AppModule.get(this@RedditApp))
    }
}