package com.giteam.android.ui.viewholders

import android.view.View
import com.giteam.android.R
import com.giteam.android.ui.adapter.BaseViewHolder
import com.giteam.android.ui.adapter.BaseViewItem

class NoMoreDataItem: BaseViewItem

class NoMoreDataStateViewHolder(itemView: View): BaseViewHolder<NoMoreDataItem>(itemView) {

    override fun bind(item: NoMoreDataItem) {
    }

    companion object {
        const val LAYOUT = R.layout.item_no_more_data_state
    }
}