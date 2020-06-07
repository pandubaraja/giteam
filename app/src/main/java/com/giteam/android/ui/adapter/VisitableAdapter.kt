package com.giteam.android.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class DiffUtilItemCallback(private val factory: ItemTypeFactory) : DiffUtil.ItemCallback<BaseViewItem>() {
    override fun areItemsTheSame(oldItem: BaseViewItem, newItem: BaseViewItem): Boolean {
        return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: BaseViewItem, newItem: BaseViewItem): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}

abstract class BaseViewHolder<T: BaseViewItem>(containerView: View): RecyclerView.ViewHolder(containerView) {
    abstract fun bind(item: T)
    open fun setOnClickListener(listener: (View) -> Unit) {  } //override if want to use it
    open fun setOnLongClickListener(listener: (View) -> Unit) {  } //override if want to use it
}

typealias VisitableAdapterItemClickListener = ((BaseViewItem, View) -> Unit)

class VisitableRecyclerAdapter(
    private val factory: ItemTypeFactory,
    private val onClick: VisitableAdapterItemClickListener? = null,
    private val onLongClick: VisitableAdapterItemClickListener? = null
): ListAdapter<BaseViewItem, BaseViewHolder<BaseViewItem>>(DiffUtilItemCallback(factory)) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<BaseViewItem> {
        return factory.onCreateViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent,false), viewType).apply {
            setOnClickListener{ onClick?.invoke(currentList.get(adapterPosition), it) }
            setOnLongClickListener{ onLongClick?.invoke(currentList.get(adapterPosition), it) }
        } as BaseViewHolder<BaseViewItem>
    }

    override fun getItemViewType(position: Int): Int = currentList[position].typeOf(factory)

    override fun onBindViewHolder(holder: BaseViewHolder<BaseViewItem>, position: Int) {
        holder.bind(currentList[position])
    }
}