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
                if (!checked) {
                    viewModel.apply {
                        thresholdUpTem = -1
                        thresholdDownTem = -1
                    }
                } else {
                    viewModel.apply {
                        thresholdUpTem = tvThresholdTemValue2.text.toString().toInt()
                        thresholdDownTem = tvThresholdTemValue1.text.toString().toInt()
                    }
                }
                clThresholdTem.visibility = if (checked) View.VISIBLE else View.GONE
            }

            checkboxThresholdHumi.setOnCheckedChangeListener { _, checked ->
                if (!checked) {
                    viewModel.apply {
                        thresholdUpHumi = -1
                        thresholdDownHumi = -1
                    }
                } else {
                    viewModel.apply {
                        thresholdUpHumi = tvThresholdHumiValue2.text.toString().toInt()
                        thresholdDownHumi = tvThresholdHumiValue1.text.toString().toInt()
                    }
                }

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
                    viewModel.thresholdDownTem = value
                }
            }

            tvThresholdHumiValue1.setOnClickListener {
                DialogView.showDialogSelectValue(this@SettingActivity) { value ->
                    tvThresholdHumiValue1.text = value.toString()
                    viewModel.thresholdDownHumi = value
                }
            }

            tvThresholdTemValue2.setOnClickListener {
                DialogView.showDialogSelectValue(this@SettingActivity) { value ->
                    tvThresholdTemValue2.text = value.toString()
                    viewModel.thresholdUpTem = value
                }
            }

            tvThresholdHumiValue2.setOnClickListener {
                DialogView.showDialogSelectValue(this@SettingActivity) { value ->
                    tvThresholdHumiValue2.text = value.toString()
                    viewModel.thresholdUpHumi = value
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
                        thresholdTemMode == 1 &&
                        thresholdDownTem >= thresholdUpTem) {
                        showToast(getString(R.string.main_activity_text_not_suitable_threshold))
                    } else if (checkboxThresholdHumi.isChecked &&
                        thresholdHumiMode == 1 &&
                        thresholdDownHumi >= thresholdUpHumi) {
                        showToast(getString(R.string.main_activity_text_not_suitable_threshold))
                    } else {
                        setThresholdOnFirebase(
                            checkboxThresholdTemperature.isChecked,
                            checkboxThresholdHumi.isChecked
                        )
                    }
                }
            }
        }
    }

    override fun addDataObserver() {
        super.addDataObserver()

        viewModel.apply {
            updateThresholdSuccess.observe(this@SettingActivity) { success ->
                if (success) {
                    showToast(getString(R.string.main_activity_text_set_threshold_success))
                } else {
                    showToast(getString(R.string.main_activity_text_set_threshold_failed))
                }
            }
        }
    }

    private fun setThresholdHumiMode(mode: Int) {
        viewModel.apply {
            thresholdHumiMode = mode
            if (mode != 1) thresholdDownHumi = -1
        }

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
        viewModel.apply {
            thresholdTemMode = mode
            if (mode != 1) thresholdDownTem = -1
        }

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