package com.example.datn.features.userinfo.ui.fragment

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.datn.R
import com.example.datn.core.base.BaseFragment
import com.example.datn.databinding.FragmentChangePasswordBinding
import com.example.datn.features.userinfo.viewmodel.UserInfoViewModel
import com.example.datn.utils.extension.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChangePasswordFragment : BaseFragment<FragmentChangePasswordBinding, UserInfoViewModel>() {

    companion object {
        @JvmStatic
        fun newInstance() = ChangePasswordFragment()
    }

    override val viewModel: UserInfoViewModel by viewModels()

    override fun getTagFragment(): String = ChangePasswordFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentChangePasswordBinding = FragmentChangePasswordBinding.inflate(layoutInflater)

    override fun onCommonViewLoaded() {

    }

    override fun addViewListener() {
        binding.apply {
            ivBack.setOnClickListener { backPress() }
            btnCancel.setOnClickListener { backPress() }

            btnSave.setOnClickListener {
                val oldPass = edtOldPass.text.toString()
                val newPass = edtNewPass.text.toString()
                val newPassAgain = edtNewPassAgain.text.toString()

                if (oldPass.isBlank() || newPass.isBlank() || newPassAgain.isBlank()) {
                    activity?.showToast(getString(R.string.enter_enough_infomation))
                } else if (newPass != newPassAgain) {
                    activity?.showToast(getString(R.string.change_pass_fragment_new_pass_not_match))
                } else {
                    onLoading(true)
                    viewModel.changePass(oldPass, newPass)
                }
            }
        }
    }

    override fun addDataObserver() {
        super.addDataObserver()

        viewModel.apply {
            messageUpdatePass.observe(this@ChangePasswordFragment) { message ->
                onLoading(false)
                activity?.showToast(message)
                if (message == getString(R.string.change_pass_fragment_update_pass_success)) {
                    backPress()
                }
            }
        }
    }

    private fun backPress() {
        activity?.supportFragmentManager?.popBackStack()
    }
}