package ua.honchar.reddit.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import ua.honchar.reddit.R
import ua.honchar.reddit.presentation.main.PostsFragment

class MainActivity : AppCompatActivity(), KodeinAware {

    private val _parentKodein by closestKodein()
    override val kodein: Kodein by retainedKodein {
        extend(_parentKodein)
        import(MainModule.get(this@MainActivity))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}