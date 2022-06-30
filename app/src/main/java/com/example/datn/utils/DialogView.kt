package com.example.datn.utils

import android.app.Activity
import android.app.AlertDialog
import com.example.datn.R

object DialogView {
    fun showAlertDialogLogout(
        activity: Activity,
        onClickYes: () -> Unit,
    ) {
        val alertDialog = AlertDialog.Builder(activity)
        alertDialog.apply {
            setCancelable(false)
            setTitle(activity.getString(R.string.notification))
            setMessage(activity.getString(R.string.user_info_activity_message_dialog_confirm_logout))
            setPositiveButton(activity.getString(R.string.yes)) { _, _ ->
                onClickYes.invoke()
            }
            setNegativeButton(activity.getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }

            show()
        }
    }
}