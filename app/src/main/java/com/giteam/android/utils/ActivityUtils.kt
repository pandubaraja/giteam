package com.giteam.android.utils

import android.app.Activity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar

inline fun <reified VM: ViewModel> AppCompatActivity.viewModelOf(factory: ViewModelProvider.Factory) =
    ViewModelProvider(this, factory).get(VM::class.java)

fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Activity.showSnackBar(constraintLayout: ConstraintLayout,
                          message: String,
                          actionMessage: String? = null,
                          onClick: (() -> Unit)? = null){
    val snackBar = Snackbar
        .make(constraintLayout, message, Snackbar.LENGTH_LONG)

    if(actionMessage != null){
        snackBar.setAction(actionMessage) {
            onClick?.invoke()
        }
    }

    snackBar.show()
}
