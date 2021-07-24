package com.squareboat.util

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.squareboat.R
import com.wang.avi.AVLoadingIndicatorView


object ProgressBarDialog {


    var dialog: Dialog? = null
    private var avi: AVLoadingIndicatorView? = null

    fun showProgressBar(activity: Context, title: String? = null) {

        var msg = title
        if (dialog != null) {
            if (dialog!!.isShowing) {
                return
            }
        }


        if (msg == null)
            msg = activity.getString(R.string.loading)
        try {
            if ("".equals(title, ignoreCase = true)) {
                msg = activity.getString(R.string.loading)
            }
            //            title = "Alert";
            dialog = Dialog(
                activity,
                R.style.Theme_SquareBoat
            )
            //            dialog.getWindow().getAttributes().windowAnimations = R.style.AlertDialog_AppCompat;

            dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.setBackgroundDrawableResource(R.color.blackDarkTransparent)

            val layoutParams = dialog!!.window!!.attributes
            layoutParams.dimAmount = 0.1F
            dialog!!.window!!.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
//            dialog!!.window!!.addFlags(WindowManager.LayoutParams.)

            dialog!!.setCancelable(false)
            dialog!!.setCanceledOnTouchOutside(false)


            dialog!!.setContentView(R.layout.dialog_progress_bar)
            val tvProgressText = dialog!!.findViewById(R.id.tvProgressText) as TextView
            avi = dialog!!.findViewById(R.id.avi)

//            dialog?.lottieLoading!!.visibility = View.VISIBLE

            tvProgressText.text = title
            tvProgressText.visibility = View.VISIBLE
            avi?.show()
            avi?.visibility = View.VISIBLE
            dialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun dismissProgressDialog() {
        try {
            if (dialog != null) {
                if (dialog!!.isShowing) {
                    avi!!.hide()
//                    dialog?.lottieLoading!!.visibility = View.GONE
//                    dialog!!.pbMain.visibility = View.GONE
                    dialog!!.dismiss()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}
