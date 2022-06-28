package com.example.datn.core.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.datn.di.component.resource.ResourcesService
import com.example.datn.di.component.scheduler.SchedulerProvider
import com.example.datn.di.component.sharepref.SharedPref
import com.example.datn.utils.Constants
import javax.inject.Inject

abstract class BaseFragment<VB : ViewBinding, ViewModel : BaseViewModel> : Fragment(), BaseBehavior {

    lateinit var binding: VB

    abstract val viewModel: ViewModel

    var underStatusBar: Boolean = false

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var resourcesService: ResourcesService

    @Inject
    lateinit var dataManager: SharedPref

    abstract fun getTagFragment(): String

    protected abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflateLayout(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (underStatusBar) {
            binding.root.setPadding(0, getStatusBarHeight(context), 0, 0)
        }

        addViewListener()
        addDataObserver()

        onCommonViewLoaded()
    }

    private fun getStatusBarHeight(context: Context?): Int {
        var result = 0
        val resourceId = context?.resources?.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId != null) {
            if (resourceId > 0) {
                result = context.resources?.getDimensionPixelSize(resourceId)!!
            }
        }
        return result
    }

    override fun addDataObserver() {
        activity?.let { activity ->
            viewModel.loadingState.observe(activity) {
                onLoading(it)
            }

            viewModel.errorState.observe(activity) {
                onError(it)
            }
        }
    }

    private fun setUpViewModel() {
        if (!viewModel.isInitialized) {
            viewModel.init(
                schedulerProvider = schedulerProvider,
                resourcesService = resourcesService,
                dataManager = dataManager
            )
            viewModel.onDidBindViewModel()
        }
    }

    override fun onError(error: Any) {
        if (activity is BaseActivity<*, *>) {
            (activity as BaseActivity<*, *>).onError(error)
        }
    }

    override fun onBecomeInvisible() {
        if (activity is BaseActivity<*, *>) {
            (activity as BaseActivity<*, *>).onBecomeInvisible()
        }
    }

    override fun onBecomeVisible() {
        if (activity is BaseActivity<*, *>) {
            (activity as BaseActivity<*, *>).onBecomeVisible()
        }
    }

    override fun onKeyboardShowing(isShowing: Boolean) {
        if (activity is BaseActivity<*, *>) {
            (activity as BaseActivity<*, *>).onKeyboardShowing(isShowing)
        }
    }

    fun openActivity(cla: Class<*>, dataSend: HashMap<String, Any>, vararg flags: Int) {
        val intent = Intent(activity, cla)
        for (flag in flags) {
            intent.addFlags(flag)
        }
        startActivity(intent)
    }

    fun openActivity(cla: Class<*>, vararg flags: Int) {
        val intent = Intent(activity, cla)
        for (flag in flags) {
            intent.addFlags(flag)
        }
        startActivity(intent)
    }

    fun openActivityWithAction(action: String) {
        val intent = Intent(action)
        startActivity(intent)
    }

    fun openActivity(cla: Class<*>, data: Bundle, vararg flags: Int) {
        val intent = Intent(activity, cla)
        intent.putExtra(Constants.KEY_BUNDLE, data)
        for (flag in flags) {
            intent.addFlags(flag)
        }
        startActivity(intent)
    }

    override fun onLoading(isLoading: Boolean) {
        if (activity is BaseActivity<*, *>) {
            (activity as BaseActivity<*, *>).onLoading(isLoading)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.compositeDisposable.clear()
    }
}