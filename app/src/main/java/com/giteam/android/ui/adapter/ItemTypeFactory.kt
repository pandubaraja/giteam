package com.giteam.android.ui.adapter

import android.view.View
import com.giteam.android.ui.viewholders.*
import java.lang.ClassCastException

abstract class ItemTypeFactory {
    abstract fun onCreateViewHolder(
        containerView: View,
        viewType: Int
    ): BaseViewHolder<out BaseViewItem>

    abstract fun type(item: BaseViewItem): Int
}

class MainActivityItemTypeFactory: ItemTypeFactory() {
    override fun onCreateViewHolder(
        containerView: View,
        viewType: Int
    ): BaseViewHolder<out BaseViewItem> {
        return when(viewType){
            UserViewHolder.LAYOUT -> UserViewHolder(containerView)
            EmptyStateViewHolder.LAYOUT -> EmptyStateViewHolder(containerView)
            LoadingStateViewHolder.LAYOUT -> LoadingStateViewHolder(containerView)
            ErrorStateViewHolder.LAYOUT -> ErrorStateViewHolder(containerView)
            NoMoreDataStateViewHolder.LAYOUT -> NoMoreDataStateViewHolder(containerView)
            IdleStateViewHolder.LAYOUT -> IdleStateViewHolder(containerView)
            else -> throw Exception("ViewHolder not registered!")
        }
    }

    override fun type(item: BaseViewItem): Int {
        return when(item){
            is UserItem -> UserViewHolder.LAYOUT
            is EmptyStateItem -> EmptyStateViewHolder.LAYOUT
            is LoadingStateItem -> LoadingStateViewHolder.LAYOUT
            is ErrorStateItem -> ErrorStateViewHolder.LAYOUT
            is NoMoreDataItem -> NoMoreDataStateViewHolder.LAYOUT
            is IdleStateItem -> IdleStateViewHolder.LAYOUT
            else -> throw ClassCastException()
        }
    }

}