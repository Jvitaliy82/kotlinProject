package com.jdeveloperapps.kotlinproject.ui.splash

import android.os.Handler
import com.jdeveloperapps.kotlinproject.ui.base.BaseActivity
import com.jdeveloperapps.kotlinproject.ui.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    override val model: SplashViewModel by viewModel()

    override val layoutRes = null

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({model.requestUser()}, 1000)
    }

    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    private fun startMainActivity() {
        MainActivity.start(this)
    }


}