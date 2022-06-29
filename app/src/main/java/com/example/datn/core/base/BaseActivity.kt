package com.example.datn.core.base

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.viewbinding.ViewBinding
import com.example.datn.R
import com.example.datn.di.component.resource.ResourcesService
import com.example.datn.di.component.scheduler.SchedulerProvider
import com.example.datn.di.component.sharepref.SharedPref
import com.example.datn.utils.Constants
import com.example.datn.utils.extension.showToast
import javax.inject.Inject

abstract class BaseActivity<viewBinding : ViewBinding, VM : BaseViewModel> : FragmentActivity(), BaseBehavior {

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    @Inject
    lateinit var resourcesService: ResourcesService

    @Inject
    lateinit var dataManager: SharedPref

    open lateinit var binding: viewBinding

    abstract val viewModel: VM

    protected abstract fun inflateLayout(layoutInflater: LayoutInflater): viewBinding

    /**
     * Callback From Fragment
     */
    private var activityInterface: ActivityInterface? = null

    fun setActivityInterface(activityInterface: ActivityInterface?) {
        this.activityInterface = activityInterface
    }

    private val dialogLoading: Dialog by lazy {
        Dialog(this, R.style.AppTheme_FullScreen_LightStatusBar).apply {
            window?.setBackgroundDrawableResource(R.color.white_50)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateLayout(layoutInflater)
        setContentView(binding.root)

        if (overlayStatusBar()) {
            val statusBarColor = ContextCompat.getColor(this, R.color.transparent)
            val navBarColor = ContextCompat.getColor(this, R.color.transparent)

            setWindowStatus(window, statusBarColor)

            // set padding root layout under status bar
            binding.root.setPadding(0, getStatusBarHeight(this), 0, 0)
        }

        createLoadingDialog()
        setUpViewModel()
        onCommonViewLoaded()
        addViewListener()
        addDataObserver()
    }

    open fun setColorTextStatusBar() {
        val view = this.window.decorView
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            requireActivity().window.decorView.windowInsetsController?.hide(WindowInsets.Type.statusBars())
            view.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        } else {
            @Suppress("DEPRECATION")
            view.systemUiVisibility = view.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    private fun setWindowStatus(window: Window, statusbarColor: Int) {
        val flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT || Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT_WATCH) {
            window.attributes.flags = window.attributes.flags or flags
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val uiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.decorView.systemUiVisibility = uiVisibility
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.attributes.flags = window.attributes.flags and flags.inv()
            window.statusBarColor = statusbarColor
        }
    }

    private fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
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

    private fun createLoadingDialog() {
        dialogLoading.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.dialog_loading_view)
        }
    }

    override fun onBecomeVisible() {

    }

    override fun onBecomeInvisible() {

    }

    override fun onKeyboardShowing(isShowing: Boolean) {
        val fragments = supportFragmentManager.fragments
        fragments.filterIsInstance<BaseFragment<*, *>>()
        if (fragments.isNotEmpty()) {
            (fragments.last() as BaseFragment<*, *>).onKeyboardShowing(isShowing)
        }
    }

    override fun addDataObserver() {
        viewModel.errorState.observe(this) {
            onError(it)
        }

        viewModel.loadingState.observe(this) {
            onLoading(it)
        }
    }

    override fun onLoading(isLoading: Boolean) {
        if (isLoading) dialogLoading.show()
        else dialogLoading.dismiss()
    }

    override fun onError(error: Any) {
        onLoading(false)
        showToast(error.toString())
    }

    /**
     * Open Activity
     */
    fun openActivity(cla: Class<*>, vararg flags: Int) {
        val intent = Intent(this, cla)
        for (flag in flags) {
            intent.addFlags(flag)
        }
        startActivity(intent)
    }

    fun openActivity(cla: Class<*>, data: Bundle, vararg flags: Int) {
        val intent = Intent(this, cla)
        intent.putExtra(Constants.KEY_BUNDLE, data)
        for (flag in flags) {
            intent.addFlags(flag)
        }
        startActivity(intent)
    }

    /**
     * Hide keyboard android Os
     */
    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun addFragment(fragment: BaseFragment<*, *>, animate: Boolean) {
        addFragment(
            fragment,
            animate = animate,
            clearStack = false,
            tag = fragment.getTagFragment()
        )
    }

    fun addFragment(fragment: BaseFragment<*, *>, tag: String, animate: Boolean) {
        addFragment(fragment, animate = animate, clearStack = false, tag = tag)
    }

    fun addFragment(
        fragment: BaseFragment<*, *>,
        animate: Boolean = false,
        clearStack: Boolean = false,
        tag: String? = null,
    ) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (clearStack) {
            while (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStackImmediate()
            }
        } else {
            fragmentTransaction.addToBackStack(tag ?: fragment.getTagFragment())
        }
        if (supportFragmentManager.findFragmentByTag(tag ?: fragment.getTagFragment()) != null) {
            return
        }

        if (overlayStatusBar()) {
            fragment.underStatusBar = true
        }

        if (!animate) {
            fragmentTransaction.setCustomAnimations(0, 0)
        }
        hideKeyboard()
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        fragmentTransaction.add(android.R.id.content, fragment, tag ?: fragment.getTagFragment())
        fragmentTransaction.commitAllowingStateLoss()
    }

    /**
     * Pop and remove fragment
     */
    private fun popFragment(tag: String? = null) {
        hideKeyboard()
        if (supportFragmentManager.backStackEntryCount > 0) {
            if (tag == null) {
                supportFragmentManager.popBackStackImmediate()
            } else {
                supportFragmentManager.popBackStackImmediate(
                    tag,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
                )
            }
            setFragmentOnBecomeVisible()
        } else {
            finish()
        }
    }

    private fun setFragmentOnBecomeVisible() {
        val fragmentList = supportFragmentManager.fragments.filterIsInstance<BaseFragment<*, *>>()
        if (!fragmentList.isNullOrEmpty()) {
            val fragmentVisible = fragmentList.last()
            fragmentVisible.onBecomeVisible()
        }
    }

    /**
     * Set statusBar cover layout
     */
    open fun overlayStatusBar(): Boolean {
        return true
    }

    override fun onBackPressed() {
        if (activityInterface != null) {
            activityInterface!!.onBackPressed()
            return
        }
        if (supportFragmentManager.backStackEntryCount > 0) {
            popFragment()
            return
        }

        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        setColorTextStatusBar()
    }

    override fun onDestroy() {
        viewModel.compositeDisposable.dispose()
        super.onDestroy()
    }
}