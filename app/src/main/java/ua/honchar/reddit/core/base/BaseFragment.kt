package ua.honchar.reddit.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.direct
import org.kodein.di.generic.instance

abstract class BaseFragment<B: ViewBinding>: Fragment(), KodeinAware {

    private val _parentKodein: Kodein by closestKodein()
    override val kodein: Kodein = Kodein.lazy {
        extend(_parentKodein, true)
        import(kodeinModule, true)
    }

    open val kodeinModule: Kodein.Module = Kodein.Module(this::class.java.simpleName) {}

    private lateinit var _binding: B
    protected val binding: B
        get() = _binding

    abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return _binding.root
    }
}