package com.giteam.android.ui.viewholders

import android.view.View
import com.giteam.android.R
import com.giteam.android.ui.adapter.BaseViewHolder
import com.giteam.android.ui.adapter.BaseViewItem

class IdleStateItem: BaseViewItem

class IdleStateViewHolder(itemView: View): BaseViewHolder<IdleStateItem>(itemView) {

    override fun bind(item: IdleStateItem) {
    }

    companion object {
        const val LAYOUT = R.layout.item_idle_state
    }
}