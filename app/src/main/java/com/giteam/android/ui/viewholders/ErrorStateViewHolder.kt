package com.giteam.android.ui.viewholders

import android.view.View
import com.giteam.android.R
import com.giteam.android.databinding.ItemErrorStateBinding
import com.giteam.android.ui.adapter.BaseViewHolder
import com.giteam.android.ui.adapter.BaseViewItem

data class ErrorStateItem(val messageResId: Int? = null): BaseViewItem

class ErrorStateViewHolder(itemView: View): BaseViewHolder<ErrorStateItem>(itemView) {
    private val binding: ItemErrorStateBinding = ItemErrorStateBinding.bind(itemView)

    override fun setOnClickListener(listener: (View) -> Unit) {
        binding.btnRetry.setOnClickListener { listener.invoke(it) }
    }

    override fun bind(item: ErrorStateItem) {
        item.messageResId?.let {
            binding.txtError.setText(it)
        }
    }

    companion object {
        const val LAYOUT = R.layout.item_error_state
    }
}