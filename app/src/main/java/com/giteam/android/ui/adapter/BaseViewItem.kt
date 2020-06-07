package com.giteam.android.ui.adapter

interface BaseViewItem {
    fun typeOf(itemFactory: ItemTypeFactory): Int = itemFactory.type(this)
    fun areContentsTheSame(newItem: BaseViewItem): Boolean {
        return false
    }
}