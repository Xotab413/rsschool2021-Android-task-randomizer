package com.rsschool.android2021

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.hideKeyboard() = view?.let { activity?.hideKeyboard(it) }

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Fragment.snackMessage(@StringRes resourceID: Int) = view?.let {
    hideKeyboard()
    Snackbar.make(it, resourceID, Snackbar.LENGTH_SHORT).apply {
        view.findViewById<TextView>(R.id.snackbar_text).apply {
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            textSize = 25F
        }
        setAction(R.string.snackmessage_action_name) {}
    }.show()
}