package com.example.datn.features.setting.ui

import android.view.LayoutInflater
import android.view.View
import com.example.datn.R
import androidx.activity.viewModels
import com.example.datn.core.base.BaseActivity
import com.example.datn.databinding.ActivitySettingBinding
import com.example.datn.features.setting.viewmodel.SettingViewModel
import com.example.datn.utils.Constants
import com.example.datn.utils.DialogView
import com.example.datn.utils.extension.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity : BaseActivity<ActivitySettingBinding, SettingViewModel>() {
    override val viewModel: SettingViewModel by viewModels()

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySettingBinding =
        ActivitySettingBinding.inflate(layoutInflater)

    override fun onCommonViewLoaded() {

    }

    override fun addViewListener() {
        binding.apply {
            ivBack.setOnClickListener { onBackPressed() }

            checkboxThresholdTemperature.setOnCheckedChangeListener { _, checked ->
                clThresholdTem.visibility = if (checked) View.VISIBLE else View.GONE
            }

            checkboxThresholdHumi.setOnCheckedChangeListener { _, checked ->
                clThresholdHumi.visibility = if (checked) View.VISIBLE else View.GONE
            }

            checkboxLight.setOnCheckedChangeListener { _, checked ->
                setViewRadioGroupLight(checked)
            }

            checkboxPump.setOnCheckedChangeListener { _, checked ->
                setViewRadioGroupPump(checked)
            }

            tvThresholdTem.setOnClickListener {
                DialogView.showDialogThresholdLabel(this@SettingActivity) { mode ->
                    setThresholdTemMode(mode)
                }
            }

            tvThresholdHumi.setOnClickListener {
                DialogView.showDialogThresholdLabel(this@SettingActivity) { mode ->
                    setThresholdHumiMode(mode)
                }
            }

            tvThresholdTemValue1.setOnClickListener {
                DialogView.showDialogSelectValue(this@SettingActivity) { value ->
                    tvThresholdTemValue1.text = value.toString()
                    viewModel.thresholdTemValue1 = value
                }
            }

            tvThresholdHumiValue1.setOnClickListener {
                DialogView.showDialogSelectValue(this@SettingActivity) { value ->
                    tvThresholdHumiValue1.text = value.toString()
                    viewModel.thresholdHumiValue1 = value
                }
            }

            tvThresholdTemValue2.setOnClickListener {
                DialogView.showDialogSelectValue(this@SettingActivity) { value ->
                    tvThresholdTemValue2.text = value.toString()
                    viewModel.thresholdTemValue2 = value
                }
            }

            tvThresholdHumiValue2.setOnClickListener {
                DialogView.showDialogSelectValue(this@SettingActivity) { value ->
                    tvThresholdHumiValue2.text = value.toString()
                    viewModel.thresholdHumiValue2 = value
                }
            }

            tvDone.setOnClickListener {
                validateThreshold()
            }

            rgLight.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.radio_on_light -> {
                        viewModel.stateLight = 1
                    }

                    R.id.radio_off_light -> {
                        viewModel.stateLight = 0
                    }
                }
            }

            rgPump.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.radio_on_pump -> {
                        viewModel.statePump = 1
                    }

                    R.id.radio_off_pump -> {
                        viewModel.statePump = 0
                    }
                }
            }
        }
    }

    private fun setViewRadioGroupPump(checked: Boolean) {
        binding.apply {
            rgPump.alpha = if (checked) 1.0f else 0.3f
            radioOffPump.isClickable = checked
            radioOnPump.isClickable = checked
        }
    }

    private fun setViewRadioGroupLight(checked: Boolean) {
        binding.apply {
            rgLight.alpha = if (checked) 1.0f else 0.3f
            radioOffLight.isClickable = checked
            radioOnLight.isClickable = checked
        }
    }

    private fun validateThreshold() {
        binding.apply {
            if (!checkboxThresholdTemperature.isChecked && !checkboxThresholdHumi.isChecked) {
                showToast(getString(R.string.main_activity_text_not_select_input_condition))
            } else {
                viewModel.apply {

                    if (checkboxThresholdTemperature.isChecked &&
                        thresholdModeTem == 1 &&
                        thresholdTemValue1 >= thresholdTemValue2) {
                        showToast(getString(R.string.main_activity_text_not_suitable_threshold))
                    }

                    if (checkboxThresholdHumi.isChecked &&
                        thresholdModeHumi == 1 &&
                        thresholdHumiValue1 >= thresholdHumiValue2) {
                        showToast(getString(R.string.main_activity_text_not_suitable_threshold))
                    }
                }
            }
        }
    }

    private fun setThresholdHumiMode(mode: Int) {
        viewModel.thresholdModeHumi = mode

        binding.apply {
            tvThresholdHumi.text = when (mode) {
                Constants.ThresholdMode.LOWER.mode -> {
                    getString(R.string.lower)
                }

                Constants.ThresholdMode.BETWEEN.mode -> {
                    getString(R.string.between)
                }

                Constants.ThresholdMode.HIGHER.mode -> {
                    getString(R.string.higher)
                }
                else -> {getString(R.string.between)}
            }

            lnThresholdHumiBetween.visibility = if (mode == 1) View.VISIBLE else View.GONE
        }
    }

    private fun setThresholdTemMode(mode: Int) {
        viewModel.thresholdModeTem = mode

        binding.apply {
            tvThresholdTem.text = when (mode) {
                Constants.ThresholdMode.LOWER.mode -> {
                    getString(R.string.lower)
                }

                Constants.ThresholdMode.BETWEEN.mode -> {
                    getString(R.string.between)
                }

                Constants.ThresholdMode.HIGHER.mode -> {
                    getString(R.string.higher)
                }
                else -> {getString(R.string.between)}
            }

            lnThresholdTemBetween.visibility = if (mode == 1) View.VISIBLE else View.GONE
        }
    }
}