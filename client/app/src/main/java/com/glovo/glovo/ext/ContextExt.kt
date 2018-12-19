package com.glovo.glovo.ext

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import com.google.android.material.snackbar.Snackbar


fun Context.getOkDialog(
    title: String, msg: String, okTxt: String, cancelTxt: String, okAction: () -> Unit
): Dialog {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)

    if (!TextUtils.isEmpty(title))
        builder.setTitle(title)

    builder.setMessage(msg)
    builder.setPositiveButton(okTxt) { dialog, _ ->
        okAction()
        dialog.dismiss()
    }
    builder.setNegativeButton(cancelTxt) { dialog, _ -> dialog.dismiss() }

    return builder.create()
}

