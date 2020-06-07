package com.giteam.android.ui.mappers

import com.giteam.android.models.User
import com.giteam.android.ui.viewholders.UserItem

class UserMapper {

    companion object {
        fun mapToUserItem(users: List<User>): List<UserItem>  = users.map {
            UserItem(it.login, it.avatar_url)
        }
    }
}