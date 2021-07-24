package com.squareboat.view.fragments.Recruiter

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareboat.R
import com.squareboat.base.BaseFragment
import com.squareboat.databinding.FragmentRecruiterHomeBinding
import com.squareboat.model.pojo.DeleteJobModel
import com.squareboat.network.retrofit.DataResult
import com.squareboat.util.*
import com.squareboat.view.activity.InitialActivity
import com.squareboat.view.adapter.JobsDataAdapter
import com.squareboat.viewmodel.RecruiterViewModel
import com.squareboat.widget.AlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel


@SuppressLint("ResourceType")
class RecruiterHomeFragment : BaseFragment<FragmentRecruiterHomeBinding>(), HandleClick,
    RecyclerviewOnClickListener {

    override fun getLayoutRes() = R.layout.fragment_recruiter_home
    private val viewModel by viewModel<RecruiterViewModel>()


    override fun onResume() {
        super.onResume()
        updateStatusBarColor(R.color.white, true, R.color.blackTransparent)
    }


    private fun initBinding() {
        binding.handleClick = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        hitApiGetJobs()
    }


    override fun onClickHandle(view: View) {

        when (view.id) {
            R.id.createJobBT -> {
                findNavController().navigate(R.id.action_addrecruiterHome_to_addJobFragment)
            }
            R.id.logoutBT -> {
                Prefs.with(activity)!!.editor.clear().apply()
                val refresh = Intent(activity, InitialActivity::class.java)
                startActivity(refresh)
            }
        }
    }


    @SuppressLint("ResourceType")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initBinding()
        addObserver()
    }

    private fun hitApiGetJobs() {
        viewModel.hitApiGetJobs()
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
                        val data = it.data.data!!.data
                        if (it.data.success!!) {
                            if(data.isNullOrEmpty()){
                                showSuccess(requireContext(), "No post posted Yet! Please post a job")
                            }else {
                                val jobAdapter = JobsDataAdapter(this,
                                    data
                                )
                                val mLayoutManager = LinearLayoutManager(requireContext())
                                binding.jobsRCW.layoutManager = mLayoutManager
                                binding.jobsRCW.adapter = jobAdapter

                            }
                        }

                    }
                    is DataResult.Failure -> {
                        it.message?.let { it1 -> showError(requireContext(), it1) }
                    }
                }
            }

        })



        viewModel.deleteJobResponseLiveData.observe(viewLifecycleOwner, Observer { it ->
            hideLoading()
            it.getContentIfNotHandled()?.let {
                when (it) {
                    is DataResult.Loading -> {
                        showLoading("")
                    }
                }
                viewModel.hitApiGetJobs()

            }
        })
    }

    override fun recyclerviewClick(id: String) {
        AlertDialog(context,"Delete Job", "Are you sure to delete this job?", DialogInterface.OnClickListener { dialog, p1 ->
            val model = DeleteJobModel(id)
            viewModel.hitApideleteJob(model)
        }, DialogInterface.OnClickListener { dialog, _ -> dialog?.dismiss() })
    }

}