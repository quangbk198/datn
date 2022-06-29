package com.example.datn.features.userinfo.ui.fragment

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.example.datn.core.base.BaseFragment
import com.example.datn.databinding.FragmentChangePasswordBinding
import com.example.datn.features.userinfo.viewmodel.UserInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by quangnh
 * Date: 29/6/2022
 * Time: 10:55 PM
 * Project datn
 */

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
        }
    }

    private fun backPress() {
        activity?.supportFragmentManager?.popBackStack()

    }
}