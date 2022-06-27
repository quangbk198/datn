package vn.vnpt.ONEHome.core.recycleview

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.datn.databinding.ItemEmptyBinding

abstract class BaseViewHolder<T : ViewBinding>(open val binding: T) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bindData(position: Int)
}

class EmptyViewHolder : BaseViewHolder<ItemEmptyBinding> {

    constructor(itemEmptyBinding: ItemEmptyBinding) : super(itemEmptyBinding)

    constructor(itemEmptyBinding: ItemEmptyBinding, contentEmpty: String, imageEmpty: Int) : super(
        itemEmptyBinding
    ) {

    }

    constructor(
        itemEmptyBinding: ItemEmptyBinding,
        contentEmpty: String
    ) : super(itemEmptyBinding) {

    }


    constructor(
        itemEmptyBinding: ItemEmptyBinding,
        contentEmpty: String,
        imageEmpty: Int,
        sizeIcon: Float
    ) : super(itemEmptyBinding) {

    }

    override fun bindData(position: Int) {

    }

}