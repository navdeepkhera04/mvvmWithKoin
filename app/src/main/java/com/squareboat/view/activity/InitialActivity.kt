package com.squareboat.view.activity

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.squareboat.R
import com.squareboat.base.BaseActivity
import com.squareboat.util.showInfo


class InitialActivity: BaseActivity() {

    lateinit var navController: NavController

    override fun showTitleBar()=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initial)


        navController = findNavController(R.id.nav_host_fragment)
    }

    override fun onBackPressed() {

        when(navController.currentDestination!!.id) {
            R.id.loginSignupFragment,R.id.homeFragment-> {
                backAction()
            }
            else -> {
                navController.popBackStack()
            }
        }
    }

    private var exit: Boolean = false
    fun backAction() {
        when {
            exit -> {
                finishAffinity()
            }
            else -> {
                showInfo(this,getString(R.string.press_one_more_time))
                exit = true
                Handler().postDelayed({ exit = false }, (2 * 1000).toLong())
            }
        }
    }

}