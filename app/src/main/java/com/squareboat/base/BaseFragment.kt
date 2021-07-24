package com.squareboat.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.squareboat.R
import com.squareboat.util.InternetCheck

abstract class BaseFragment<DB : ViewDataBinding> : Fragment(), View.OnTouchListener {

    open lateinit var binding: DB

    var isLoaded = false

    lateinit var baseActivity: BaseActivity

    @LayoutRes
    abstract fun getLayoutRes(): Int

    private fun init(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        init(inflater, container)
        super.onCreateView(inflater, container, savedInstanceState)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        isLoaded = true
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            this.baseActivity = context
        }
    }

    fun hideKeyBoard(input: View?) {
        input?.let {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(input.windowToken, 0)
        }
    }

    fun showLoading(message: String?) {
        baseActivity.showLoading(message)
    }

    fun hideLoading() {
        baseActivity.hideLoading()
    }


    fun isInternetConnected(): Boolean {
        return InternetCheck.isConnectedToInternet(requireContext())
    }

    fun setToolbar(
        title: String? = null,
        navigationBtn: Int? = null,
        endButton: Int? = null,
        onClickEndButton: () -> Unit
    ) {


    }

    open fun backPress() {
    }








    fun updateStatusBarColor(@ColorRes colorId: Int, isStatusBarFontDark: Boolean = true,statusBarColor:Int=R.color.transparent) {

    baseActivity.updateStatusBarColor(colorId,isStatusBarFontDark,statusBarColor)

    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {


        if (v is AppCompatEditText && v.hasFocus()) {
            v.parent.requestDisallowInterceptTouchEvent(true)
            when (event.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_SCROLL -> {
                    v.parent.requestDisallowInterceptTouchEvent(false)
                    return true
                }
            }
        }
        return false
    }


}