package com.example.datn.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import com.example.datn.R
import com.example.datn.utils.extension.showToast
import com.whiteelephant.monthpicker.MonthPickerDialog
import java.time.Year
import java.util.*

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

    fun showDatePickerDialog(
        context: Context,
        selectedDate: (Triple<Int, Int, Int>) -> Unit,
    ) {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dataPickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate.invoke(Triple(selectedDay, selectedMonth + 1, selectedYear))
            },
            year,
            month,
            day
        )

        dataPickerDialog.show()
    }

    fun showMonthPickerDialog(
        context: Context,
        selectedDate: (Pair<Int, Int>) -> Unit,
    ) {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)

        val monthPickerBuilder = MonthPickerDialog.Builder(
            context,
            { selectedMonth, selectedYear ->
                selectedDate.invoke(Pair(selectedMonth + 1, selectedYear))
            },
            year,
            month
        )

        monthPickerBuilder.setActivatedYear(year).build().show()
    }
}





















