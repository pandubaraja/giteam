package com.giteam.android.ui.viewholders

import android.view.View
import coil.api.load
import coil.transform.CircleCropTransformation
import com.giteam.android.R
import com.giteam.android.databinding.ItemUserBinding
import com.giteam.android.ui.adapter.BaseViewHolder
import com.giteam.android.ui.adapter.BaseViewItem

class UserItem(val name: String,
               val avatarUrl: String): BaseViewItem
{
    override fun areContentsTheSame(newItem: BaseViewItem): Boolean {
        val old = this as? UserItem
        val new = newItem as? UserItem

        return old?.name == new?.name
    }
}

class UserViewHolder(itemView: View): BaseViewHolder<UserItem>(itemView) {

    private val binding: ItemUserBinding = ItemUserBinding.bind(itemView)

    override fun bind(item: UserItem) {
        with(binding){
            textName.text = item.name
            imgProfile.load(item.avatarUrl) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_account)
            }
        }
    }

    companion object {
        const val LAYOUT = R.layout.item_user
    }
}