package com.jdeveloperapps.kotlinproject.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.jdeveloperapps.kotlinproject.R

class LoginDialog(val onLogout: () -> Unit): DialogFragment() {

    companion object {
        val TAG = LoginDialog::class.java.name + "TAG"
        fun newInstance(onLogout: () -> Unit) = LoginDialog(onLogout)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
                .setTitle(getString(R.string.exit))
                .setMessage(getString(R.string.main_logout_message))
                .setPositiveButton(R.string.main_logout_ok) {dialog, which -> onLogout() }
                .setNegativeButton(R.string.main_logout_cancel) {dialog, which -> dismiss() }
                .create()
    }

}