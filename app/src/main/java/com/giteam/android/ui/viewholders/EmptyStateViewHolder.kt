package com.giteam.android.ui.viewholders

import android.view.View
import com.giteam.android.R
import com.giteam.android.ui.adapter.BaseViewHolder
import com.giteam.android.ui.adapter.BaseViewItem

class EmptyStateItem: BaseViewItem

class EmptyStateViewHolder(itemView: View): BaseViewHolder<EmptyStateItem>(itemView) {

    override fun bind(item: EmptyStateItem) {
    }

    companion object {
        const val LAYOUT = R.layout.item_empty_state
    }
}