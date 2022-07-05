package com.example.datn.utils

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.Window
import android.widget.DatePicker
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.datn.R
import com.example.datn.databinding.DialogSelectThresholdLabelBinding
import com.example.datn.databinding.DialogSelectValueBinding
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

    fun showDialogThresholdLabel(
        activity: Activity,
        onClickSave: (Int) -> Unit
    ) {
        val listLabel = arrayListOf(
            activity.resources.getString(R.string.lower),
            activity.resources.getString(R.string.between),
            activity.resources.getString(R.string.higher)
        )

        val binding = DialogSelectThresholdLabelBinding.inflate(LayoutInflater.from(activity))

        val dialog = Dialog(activity)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(binding.root)
            window!!.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setLayout(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                setGravity(Gravity.BOTTOM)
            }
        }

        binding.apply {
            thresholdMode.apply {
                displayedValues = listLabel.toTypedArray()
                minValue = 0
                maxValue = 2
                value = 1
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            btnSave.setOnClickListener {
                onClickSave.invoke(thresholdMode.value)
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    fun showDialogSelectValue(
        activity: Activity,
        onClickSave: (Int) -> Unit
    ) {
        val binding = DialogSelectValueBinding.inflate(LayoutInflater.from(activity))

        val dialog = Dialog(activity)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(binding.root)
            window!!.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setLayout(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                setGravity(Gravity.BOTTOM)
            }
        }

        binding.apply {
            thresholdMode.apply {
                minValue = 0
                maxValue = 50
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            btnSave.setOnClickListener {
                onClickSave.invoke(thresholdMode.value)
                dialog.dismiss()
            }
        }

        dialog.show()
    }
}





















