package com.jdeveloperapps.kotlinproject.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.jdeveloperapps.kotlinproject.R
import com.jdeveloperapps.kotlinproject.data.errors.NoAuthExeption
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<S> : AppCompatActivity(), CoroutineScope {

    companion object {
        private const val RC_SIGN_IN = 1234
    }

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + Job()
    }
    private lateinit var dataJob: Job
    private lateinit var errorJob: Job

    abstract val model: BaseViewModel<S>
    abstract val layoutRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let { setContentView(it) }
    }

    override fun onStart() {
        super.onStart()
        dataJob = launch {
            model.getViewState().consumeEach {
                renderData(it)
            }
        }

        errorJob = launch {
            model.getErrorChannel().consumeEach {
                renderError(it)
            }
        }

    }

    protected fun renderError(error: Throwable) {
        when(error) {
            is NoAuthExeption -> startLogin()
            else -> error?.let {
                it.message?.let { showError(it) }
            }
        }
    }

    fun startLogin() {

        val providers = listOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
        )

        val intent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.ic_launcher_foreground)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build()

        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK) {
            finish()
        }
    }

    fun showError(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    abstract fun renderData(data: S)

    override fun onStop() {
        super.onStop()
        dataJob.cancel()
        errorJob.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

}