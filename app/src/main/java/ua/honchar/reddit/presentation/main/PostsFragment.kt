package ua.honchar.reddit.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ua.honchar.reddit.R
import ua.honchar.reddit.core.base.BaseFragment
import ua.honchar.reddit.databinding.PostsFragmentBinding

class PostsFragment : BaseFragment<PostsFragmentBinding>() {

    override val layoutRes = R.layout.posts_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}