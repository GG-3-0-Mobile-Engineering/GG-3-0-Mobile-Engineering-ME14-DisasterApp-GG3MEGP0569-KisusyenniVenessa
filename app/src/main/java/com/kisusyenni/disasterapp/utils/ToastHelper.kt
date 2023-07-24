package com.kisusyenni.disasterapp.utils

import android.content.Context
import android.widget.Toast

object ToastHelper {
    fun setToastShort(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
    fun setToastLong(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}