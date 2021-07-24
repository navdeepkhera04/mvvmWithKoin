package com.squareboat.view.fragments.candidate

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareboat.R
import com.squareboat.base.BaseFragment
import com.squareboat.databinding.FragmentAppliedJobsBinding
import com.squareboat.network.retrofit.DataResult
import com.squareboat.util.HandleClick
import com.squareboat.util.RecyclerviewOnClickListener
import com.squareboat.util.showError
import com.squareboat.util.showSuccess
import com.squareboat.view.adapter.AppliedJobAdapter
import com.squareboat.viewmodel.AppliedJobViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class AppliedJobsFragment : BaseFragment<FragmentAppliedJobsBinding>(), HandleClick,
    RecyclerviewOnClickListener {

    override fun getLayoutRes() = R.layout.fragment_applied_jobs
    private val viewModel by viewModel<AppliedJobViewModel>()


    override fun onResume() {
        super.onResume()
        updateStatusBarColor(R.color.white, true, R.color.blackTransparent)
    }


    private fun initBinding() {
        binding.handleClick = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        hitApiGetAppliedJobs()
    }


    override fun onClickHandle(view: View) {

        when (view.id) {

        }
    }

    @SuppressLint("ResourceType")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBinding()
        addObserver()
    }

    private fun hitApiGetAppliedJobs() {
        viewModel.hitApiGetAppliedJobs()
    }

    private fun addObserver() {
        viewModel.jobsResponseLiveData.observe(viewLifecycleOwner, Observer { it ->
            hideLoading()
            it.getContentIfNotHandled()?.let {
                when (it) {
                    is DataResult.Loading -> {
                        showLoading("")
                    }
                    is DataResult.Success -> {
                        val data = it.data.data
                        if (it.data.success!!) {
                            if (data.isNullOrEmpty()) {
                                showSuccess(
                                    requireContext(),
                                    "No post posted Yet! Please post a job"
                                )
                            } else {
                                val appliedJobAdapter = AppliedJobAdapter(
                                    this,
                                    data
                                )
                                val mLayoutManager = LinearLayoutManager(requireContext())
                                binding.jobsRCW.layoutManager = mLayoutManager
                                binding.jobsRCW.adapter = appliedJobAdapter

                            }
                        }

                    }
                    is DataResult.Failure -> {
                        it.message?.let { it1 -> showError(requireContext(), it1) }
                    }
                }
            }

        })


    }

    override fun recyclerviewClick(id: String) {

    }

}