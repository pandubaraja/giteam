package com.giteam.android.ui.viewholders

import android.view.View
import com.giteam.android.R
import com.giteam.android.ui.adapter.BaseViewHolder
import com.giteam.android.ui.adapter.BaseViewItem

class LoadingStateItem: BaseViewItem

class LoadingStateViewHolder(itemView: View): BaseViewHolder<LoadingStateItem>(itemView) {

    override fun bind(item: LoadingStateItem) {
    }

    companion object {
        const val LAYOUT = R.layout.item_loading_state
    }
}