package com.jdeveloperapps.kotlinproject.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.jdeveloperapps.kotlinproject.R

class LogoutDialog(val onLogout: () -> Unit): DialogFragment() {

    companion object {
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun newInstance(onLogout: () -> Unit) = LogoutDialog(onLogout)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(context)
                .setTitle(getString(R.string.exit))
                .setMessage(getString(R.string.question_message))
                .setPositiveButton(R.string.ok) { dialog, which -> onLogout() }
                .setNegativeButton(R.string.cancel) { dialog, which -> dismiss() }
                .create()
    }

}