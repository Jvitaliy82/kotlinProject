package com.jdeveloperapps.kotlinproject.ui.splash

import android.os.Handler
import androidx.lifecycle.ViewModelProviders
import com.jdeveloperapps.kotlinproject.ui.base.BaseActivity
import com.jdeveloperapps.kotlinproject.ui.main.MainActivity

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    override val viewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java)
    }

    override val layoutRes = null

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({viewModel.requestUser()}, 1000)
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