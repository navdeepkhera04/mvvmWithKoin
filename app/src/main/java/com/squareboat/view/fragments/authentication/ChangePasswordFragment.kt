package com.squareboat.view.fragments.authentication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.squareboat.R
import com.squareboat.base.BaseFragment
import com.squareboat.databinding.FragmentChangePasswordBinding
import com.squareboat.network.retrofit.DataResult
import com.squareboat.util.*
import com.squareboat.util.extensions.isValidPassword
import com.squareboat.util.extensions.ismatchPassword
import com.squareboat.view.activity.InitialActivity
import com.squareboat.viewmodel.ChangePasswordViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding>(), HandleClick {
    private val viewModel by viewModel<ChangePasswordViewModel>()
    private val isValid: Boolean
        get() {
            when {

                binding.viewModel!!.changePasswordData.value!!.password.isNullOrEmpty() -> {
                    binding.passwordET.error = baseActivity.getString(R.string.plz_enter_password)
                    binding.passwordET.requestFocus()
                }
                binding.viewModel!!.changePasswordData.value!!.confirmPassword.isNullOrEmpty() -> {
                    binding.confirmPasswordET.error = baseActivity.getString(R.string.plz_enter_confirm_password)
                    binding.confirmPasswordET.requestFocus()
                }

                !binding.viewModel!!.changePasswordData.value!!.password!!.isValidPassword() -> {
                    binding.passwordET.error =
                        baseActivity.getString(R.string.plz_enter_valid_password)
                    binding.passwordET.requestFocus()
                }

                !binding.viewModel!!.changePasswordData.value!!.password!!.ismatchPassword(binding.viewModel!!.changePasswordData.value!!.confirmPassword!!) -> {
                    binding.confirmPasswordET.error =
                        baseActivity.getString(R.string.password_missmatch)
                    binding.confirmPasswordET.requestFocus()
                }
                else -> {

                    return true
                }
            }

            return false
        }



    override fun getLayoutRes() = R.layout.fragment_change_password

    override fun onResume() {
        super.onResume()
        updateStatusBarColor(R.color.white, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initBinding()
        addObserver()
    }

    private fun initBinding() {
        binding.handleClick = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onClickHandle(view: View) {

        when(view.id) {
            R.id.setNewPassBT -> {
                if(isValid){
                    hitApiChangePassword()
                }
            }
            R.id.backIV -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun hitApiChangePassword() {
        viewModel.hitApiChangePassword(Prefs.with(activity)!!.getString(Const.USER_TOKEN, ""))
    }


    private fun addObserver() {
        viewModel.changePasswordLiveData.observe(viewLifecycleOwner, Observer { it ->
            hideLoading()
            it.getContentIfNotHandled().let {
                when(it) {
                    is DataResult.Loading -> {
                        showLoading("")
                    }
                    is DataResult.Success -> {
                        val data = it.data.data
                        if(it.data.success!!){
                            showSuccess(requireContext(), "Password Changed Successfully")
                            Prefs.with(activity)!!.editor.clear().apply()
                            val refresh = Intent(activity, InitialActivity::class.java)
                            startActivity(refresh)
                        }
                    }
                    is DataResult.Failure -> {
                        it.message.let { it1 -> showError(requireContext(), it1!!) }
                    }
                }

            }
        })
    }
}

