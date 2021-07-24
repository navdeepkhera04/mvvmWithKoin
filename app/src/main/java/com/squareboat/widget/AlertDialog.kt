package com.squareboat.widget

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface

class AlertDialog(val dialogContext: Context?,
                  var title: String,
                  var msg: String = "",
                  var positiveListener: DialogInterface.OnClickListener? = null,
                  var negativeListener: DialogInterface.OnClickListener? = null,
                  var cancellable: Boolean = false
): AlertDialog.Builder(dialogContext) {

    init {
        this.setTitle(title)
        if (msg.isNotEmpty()) this.setMessage(msg)
        this.setCancelable(cancellable)
        this.setPositiveButton("OK", positiveListener)
        this.setNegativeButton("CANCEL", negativeListener)

        build()
    }

    fun build(){
        val alertDialog = this.create()
        alertDialog.show()
    }
}