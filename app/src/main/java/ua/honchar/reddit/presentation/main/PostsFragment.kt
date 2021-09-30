package ua.honchar.reddit.presentation.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.closestKodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import ua.honchar.reddit.R
import ua.honchar.reddit.core.base.BaseFragment
import ua.honchar.reddit.core.di.ViewModelFactory
import ua.honchar.reddit.core.util.extensions.bindViewModel
import ua.honchar.reddit.core.util.extensions.viewModel
import ua.honchar.reddit.databinding.PostsFragmentBinding

class PostsFragment : BaseFragment<PostsFragmentBinding>() {

    val viewModel: PostsViewModel by viewModel()
    override val layoutRes = R.layout.posts_fragment

    override val kodeinModule = Kodein.Module(this::class.java.simpleName){
        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }
        bindViewModel<PostsViewModel>() with provider {
            PostsViewModel(instance(), instance())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()

        binding.message.setOnClickListener {
            viewModel.loadPosts()
        }

        viewModel.loadPosts()
    }

    private fun initObservers(){
        viewModel.checkPosts().observe(viewLifecycleOwner){
            Log.d(TAG, "initObservers: $it")
        }
        viewModel.getErrorAction().observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            Log.d(TAG, "initObservers: $it")
        }
    }
}