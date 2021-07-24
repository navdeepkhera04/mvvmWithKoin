package com.squareboat.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.squareboat.R
import com.squareboat.network.retrofit.Event
import com.squareboat.util.*
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity : AppCompatActivity() {
    val NETWORK_CONNECTION_CHANGED_LIVE_DATA = MutableLiveData<Event<Boolean>>()
    var store: Prefs? = null



    val deviceToken: String?
        get() = if (store !!.getString(Const.DEVICE_TOKEN) !!.isEmpty()) {
            uniqueDeviceId
        } else {
            store !!.getString(Const.DEVICE_TOKEN)
        }


    val uniqueDeviceId: String
        @SuppressLint("HardwareIds")
        get() = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)


    private val connectivityManager: ConnectivityManager by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateStatusBarColor(R.color.white, true)

        if (showTitleBar()) supportActionBar?.show()
        else supportActionBar?.hide() //AndroidInjection.inject(this)
        store = Prefs(this)



        registerNetworkBroadCast()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

    abstract fun showTitleBar(): Boolean


    fun hideKeyBoard(input: View?) {

        input?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(input.windowToken, 0)
        }
    }

    fun showLoading(message: String?) {
        ProgressBarDialog.showProgressBar(this, message)
    }

    fun hideLoading() {
        ProgressBarDialog.dismissProgressDialog()
    }

    val TAG = "NETWORK_BROADCAST"

    private fun registerNetworkBroadCast() {

        val request = NetworkRequest.Builder()
        request.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        request.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        request.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)

        connectivityManager.requestNetwork(
            request.build(),
            object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Log.i(TAG, "AVAILABLE")
                    NETWORK_CONNECTION_CHANGED_LIVE_DATA.postValue(
                        Event(true)
                    )

                }


                override fun onLosing(
                    network: Network,
                    maxMsToLive: Int
                ) {
                    super.onLosing(network, maxMsToLive)

                    Log.i(TAG, "LOSING")
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    Log.i(TAG, "LOST")

                    NETWORK_CONNECTION_CHANGED_LIVE_DATA.postValue(
                        Event(false)
                    )
                }


            })

        /**observing network connectivity to display snack bar*/
        NETWORK_CONNECTION_CHANGED_LIVE_DATA.observe(this, Observer {

            it?.getContentIfNotHandled()?.let {
                if (!it) {

                    window.decorView.rootView?.apply {
                        hideKeyBoard(this)

                        showSnackbar(getString(R.string.internet_connection), Snackbar.LENGTH_LONG)

                    }

                }

            }

        })
    }


    private fun setSystemBarTheme(isStatusBarFontDark: Boolean) {

         window.decorView.systemUiVisibility =
            if (isStatusBarFontDark) {
                0
            } else {
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }

    }


    fun updateStatusBarColor(@ColorRes colorId: Int, isStatusBarFontDark: Boolean = true,statusBarColor:Int=R.color.transparent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, statusBarColor)
            window.navigationBarColor = ContextCompat.getColor(this, R.color.transparent)
            window.setBackgroundDrawable(ContextCompat.getDrawable(this, colorId))
            setSystemBarTheme(isStatusBarFontDark)

        }
    }



}


