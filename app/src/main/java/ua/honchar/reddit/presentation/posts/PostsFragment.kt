package ua.honchar.reddit.presentation.posts

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.direct
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import ua.honchar.reddit.R
import ua.honchar.reddit.core.base.presentation.BaseFragment
import ua.honchar.reddit.core.di.ViewModelFactory
import ua.honchar.reddit.core.util.extensions.bindViewModel
import ua.honchar.reddit.core.util.extensions.viewModel
import ua.honchar.reddit.databinding.PostsFragmentBinding
import ua.honchar.reddit.presentation.posts.adapter.PostAdapter

class PostsFragment : BaseFragment<PostsFragmentBinding>() {

    val viewModel: PostsViewModel by viewModel()
    override val layoutRes = R.layout.posts_fragment

    private val adapter by lazy {
        PostAdapter()
    }

    override val kodeinModule = Kodein.Module(this::class.java.simpleName){
        bind<ViewModelProvider.Factory>() with singleton { ViewModelFactory(kodein.direct) }
        bindViewModel<PostsViewModel>() with provider {
            PostsViewModel(instance(), instance())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()

        binding.postsRecycler.adapter = adapter


        viewModel.loadPosts()
    }

    private fun initObservers(){
        viewModel.checkPosts().observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        viewModel.getErrorAction().observe(viewLifecycleOwner){
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
}