package com.example.datn.features.main.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
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

    fun addData(device: ChildDeviceModel) {
        mListDevice.add(device)
        notifyDataSetChanged()
    }

    fun updateData(device: ChildDeviceModel) {
        for ((index, childDevice) in mListDevice.withIndex()) {
            if (device.model_code == childDevice.model_code) {
                childDevice.state = device.state
                notifyItemChanged(index)
            }
        }
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

        holder.apply {
            swState.isChecked = (device.state == 1)

            tvOnOff.text =
                if (device.state == 1) itemView.context.getString(R.string.on) else itemView.context.getString(R.string.off)

            tvOnOff.setTextColor(ContextCompat.getColor(
                itemView.context,
                if (device.state == 1) R.color.light_blue else R.color.grey_light_text
            ))
        }
    }

    override fun getItemCount(): Int = mListDevice.size

    class DeviceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var ivDevice: ImageView
        var tvDeviceName: TextView
        var swState: SwitchCompat
        var tvOnOff: TextView

        init {
            ivDevice = view.findViewById(R.id.iv_device)
            tvDeviceName = view.findViewById(R.id.tv_device_name)
            swState = view.findViewById(R.id.sw_state_device)
            tvOnOff = view.findViewById(R.id.tv_on_off)
        }
    }
}