package com.example.datn.features.main.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.datn.R
import com.example.datn.data.model.ChildDeviceModel
import com.example.datn.utils.Constants

/**
 * Created by quangnh
 * Date: 30/6/2022
 * Time: 10:52 PM
 * Project datn
 */

@SuppressLint("NotifyDataSetChanged")
class DeviceAdapter(var mContext: Context) : RecyclerView.Adapter<DeviceAdapter.DeviceViewHolder>() {

    private val mListDevice = mutableListOf<ChildDeviceModel>()

    fun setData(list: MutableList<ChildDeviceModel>) {
        mListDevice.apply {
            clear()
            addAll(list)
        }

        notifyDataSetChanged()
    }

    fun addData(device: ChildDeviceModel) {
        mListDevice.add(device)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)

        return DeviceViewHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        val device = mListDevice[position]

        when (device.model_code) {
            Constants.DeviceModel.Light.modelCode -> {
                holder.apply {
                    ivDevice.setImageResource(R.drawable.ic_light)
                    tvDeviceName.text = mContext.getString(R.string.light)
                }
            }

            Constants.DeviceModel.Pump.modelCode -> {
                holder.apply {
                    ivDevice.setImageResource(R.drawable.ic_pump)
                    tvDeviceName.text = mContext.getString(R.string.pump)
                }
            }
        }

        holder.swState.isChecked = (device.state == 1)
    }

    override fun getItemCount(): Int = mListDevice.size

    class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivDevice: ImageView
        var tvDeviceName: TextView
        var swState: SwitchCompat

        init {
            ivDevice = view.findViewById(R.id.iv_device)
            tvDeviceName = view.findViewById(R.id.tv_device_name)
            swState = view.findViewById(R.id.sw_state_device)
        }
    }
}