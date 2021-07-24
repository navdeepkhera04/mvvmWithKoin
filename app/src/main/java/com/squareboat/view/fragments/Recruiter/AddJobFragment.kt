package com.squareboat.view.fragments.Recruiter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.squareboat.R
import com.squareboat.base.BaseFragment
import com.squareboat.databinding.FragmentAddJobBinding
import com.squareboat.network.retrofit.DataResult
import com.squareboat.util.HandleClick
import com.squareboat.util.extensions.isValidEmail
import com.squareboat.util.showError
import com.squareboat.util.showSuccess
import com.squareboat.viewmodel.AddJobViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


@SuppressLint("ResourceType")
class AddJobFragment : BaseFragment<FragmentAddJobBinding>(), HandleClick {

    override fun getLayoutRes() = R.layout.fragment_add_job
    private val viewModel by viewModel<AddJobViewModel>()
    private val isValid: Boolean
        get() {
            when {

                binding.viewModel!!.newJobData.value!!.title.isNullOrEmpty() -> {
                    binding.titleET.error = baseActivity.getString(R.string.field_required)
                    binding.titleET.requestFocus()
                }
                binding.viewModel!!.newJobData.value!!.description!!.isNullOrEmpty() -> {
                    binding.descriptionET.error = baseActivity.getString(R.string.field_required)
                    binding.descriptionET.requestFocus()
                }
                binding.viewModel!!.newJobData.value!!.location.isNullOrEmpty() -> {
                    binding.locationET.error = baseActivity.getString(R.string.field_required)
                    binding.locationET.requestFocus()
                }

                else -> {

                    return true
                }
            }

            return false
        }


    override fun onResume() {
        super.onResume()
        updateStatusBarColor(R.color.white, true, R.color.blackTransparent)
    }


    private fun initBinding() {
        binding.handleClick = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }


    override fun onClickHandle(view: View) {

        when (view.id) {
            R.id.createJobBT -> {
                if (isValid) {
                    hitApiAddNewJob()
                }
            }
        }
    }


    @SuppressLint("ResourceType")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBinding()
        addObserver()
    }

    private fun hitApiAddNewJob() {
        viewModel.hitApiCreateJob()
    }

    private fun addObserver() {
        viewModel.createJobResponseLiveData.observe(viewLifecycleOwner, Observer { it ->
            hideLoading()
            it.getContentIfNotHandled()?.let {
                when(it) {
                    is DataResult.Loading -> {
                        showLoading("")
                    }
                    is DataResult.Success -> {
                        val data = it.data.data
                        if(it.data.success!!){
                            showSuccess(requireContext(),"Job Created Successfully")
                            findNavController().popBackStack()
                        }

                    }
                    is DataResult.Failure -> {
                        it.message?.let { it1 -> showError(requireContext(), it1) }
                    }
                }
            }
        })
    }


}