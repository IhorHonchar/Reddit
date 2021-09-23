package ua.honchar.reddit.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ua.honchar.reddit.R
import ua.honchar.reddit.presentation.main.PostsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}