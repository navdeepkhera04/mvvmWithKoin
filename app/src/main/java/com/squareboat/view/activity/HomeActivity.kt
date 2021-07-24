package com.squareboat.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.squareboat.BuildConfig
import com.squareboat.R
import com.squareboat.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {

    lateinit var navController: NavController
    override fun showTitleBar() = true



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolBar)
        navController = findNavController(R.id.nav_host_fragment)
    }


    fun getToolBar() = supportActionBar

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

           /* android.R.id.home -> {
                if (currentFragment() is HomeCustomerFragment) { //
                    if (drawer_layout.isDrawerOpen(GravityCompat.START)) drawer_layout.close()
                    else drawer_layout.openDrawer(GravityCompat.START)
                } else {
                    onBackPressed()
                }
            }
*/

            else -> {
                return super.onOptionsItemSelected(item)
            }
        }

        return false
    }


    fun currentFragment() = nav_host_fragment.childFragmentManager.fragments[0]




}