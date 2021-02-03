package com.devstree.basesample.view.ui.base

import android.app.ActivityManager
import android.content.Context

abstract class NavigationActivity : BaseActivity() {

    fun isServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    fun openLoginActivity() {
//        launchActivity<LoginActivity> {
//            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//        }
        finishAffinity()
    }

    fun openRegisterActivity() {
//        launchActivity<RegisterActivity>()
    }


//    fun openViewQrCodeActivity(raw: String) {
//        launchActivity<ViewQrCodeActivity> {
//            putExtra(Constant.QR_DETAILS, raw)
//        }
//    }

}