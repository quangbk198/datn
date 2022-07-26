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
                viewModel.apply {
                    thresholdUpTem = tvThresholdTemValue2.text.toString().toInt()
                    thresholdDownTem = tvThresholdTemValue1.text.toString().toInt()
                }

                clThresholdTem.visibility = if (checked) View.VISIBLE else View.GONE
            }

            checkboxThresholdHumi.setOnCheckedChangeListener { _, checked ->
                viewModel.apply {
                    thresholdUpHumi = tvThresholdHumiValue2.text.toString().toInt()
                    thresholdDownHumi = tvThresholdHumiValue1.text.toString().toInt()
                }

                clThresholdHumi.visibility = if (checked) View.VISIBLE else View.GONE
            }

            checkboxLight.setOnCheckedChangeListener { _, checked ->
                viewModel.apply {
                    if (radioOnLight.isChecked) stateLight = 1
                    if (radioOffLight.isChecked) stateLight = 0
                }

                setViewRadioGroupLight(checked)
            }

            checkboxPump.setOnCheckedChangeListener { _, checked ->
                viewModel.apply {
                    if (radioOnPump.isChecked) statePump = 1
                    if (radioOffPump.isChecked) statePump = 0
                }

                setViewRadioGroupPump(checked)
            }

            tvThresholdTem.setOnClickListener {
                viewModel.temThreshold.value?.mode?.let { currentMode ->
                    DialogView.showDialogThresholdLabel(
                        this@SettingActivity,
                        currentMode
                    ) { mode ->
                        setThresholdTemMode(mode)
                    }
                }
            }

            tvThresholdHumi.setOnClickListener {
                viewModel.humiThreshold.value?.mode?.let { currentMode ->
                    DialogView.showDialogThresholdLabel(
                        this@SettingActivity,
                        currentMode
                    ) { mode ->
                        setThresholdHumiMode(mode)
                    }
                }
            }

            tvThresholdTemValue1.setOnClickListener {
                DialogView.showDialogSelectValue(
                    this@SettingActivity,
                    tvThresholdTemValue1.text.toString().trim().toInt(),
                    0,
                    50
                ) { value ->
                    tvThresholdTemValue1.text = value.toString()
                    viewModel.thresholdDownTem = value
                }
            }

            tvThresholdHumiValue1.setOnClickListener {
                DialogView.showDialogSelectValue(
                    this@SettingActivity,
                    tvThresholdHumiValue1.text.toString().trim().toInt(),
                    0,
                    100
                ) { value ->
                    tvThresholdHumiValue1.text = value.toString()
                    viewModel.thresholdDownHumi = value
                }
            }

            tvThresholdTemValue2.setOnClickListener {
                DialogView.showDialogSelectValue(
                    this@SettingActivity,
                    tvThresholdTemValue2.text.toString().trim().toInt(),
                    0,
                    50
                ) { value ->
                    tvThresholdTemValue2.text = value.toString()
                    viewModel.thresholdUpTem = value
                }
            }

            tvThresholdHumiValue2.setOnClickListener {
                DialogView.showDialogSelectValue(
                    this@SettingActivity,
                    tvThresholdHumiValue2.text.toString().trim().toInt(),
                    0,
                    100
                ) { value ->
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
            } else if (!checkboxLight.isChecked && !checkboxPump.isChecked) {
                showToast(getString(R.string.main_activity_text_not_select_output_condition))
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
                            checkboxThresholdHumi.isChecked,
                            checkboxLight.isChecked,
                            checkboxPump.isChecked
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

            binding.apply {
                temThreshold.observe(this@SettingActivity) { tem ->
                    checkboxThresholdTemperature.isChecked = tem.select == true
                    tem.mode?.let { setThresholdTemMode(it) }
                    tvThresholdTemValue1.text = tem.threshold_down.toString()
                    tvThresholdTemValue2.text = tem.threshold_up.toString()
                }

                humiThreshold.observe(this@SettingActivity) { humi ->
                    checkboxThresholdHumi.isChecked = humi.select == true
                    humi.mode?.let { setThresholdHumiMode(it) }
                    tvThresholdHumiValue1.text = humi.threshold_down.toString()
                    tvThresholdHumiValue2.text = humi.threshold_up.toString()
                }

                stateLightOutput.observe(this@SettingActivity) { state ->
                    checkboxLight.isChecked = state.select == true
                    state.state?.let {
                        radioOnLight.isChecked = it == 1
                        radioOffLight.isChecked = it == 0
                    }
                }

                statePumpOutput.observe(this@SettingActivity) { state ->
                    checkboxPump.isChecked = state.select == true
                    state.state?.let {
                        radioOnPump.isChecked = it == 1
                        radioOffPump.isChecked = it == 0
                    }
                }
            }
        }
    }

    private fun setThresholdHumiMode(mode: Int) {
        viewModel.apply {
            thresholdHumiMode = mode
            if (mode != 1) thresholdDownHumi = 0
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
            if (mode != 1) thresholdDownTem = 0
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