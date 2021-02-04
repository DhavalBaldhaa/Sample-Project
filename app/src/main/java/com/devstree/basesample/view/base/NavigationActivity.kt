package com.devstree.basesample.view.base

abstract class NavigationActivity : BaseActivity() {

    fun openLoginActivity() {
//        val intent = Intent(this, LoginActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(intent)
//        finishAffinity()
    }

    fun openSignUpActivity() {
//        val intent = Intent(this, SignUpActivity::class.java)
//        startActivity(intent)
    }

    fun openForgotPasswordActivity() {
//        val intent = Intent(this, ForgotPasswordActivity::class.java)
//        startActivity(intent)
    }

    fun openDashBoardActivity() {
//        val intent = Intent(this, MainDashboardActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//        startActivity(intent)
//        finishAffinity()
    }

    fun openVerificationCodeActivity(requestCode: Int) {
//        val intent = Intent(this, VerificationCodeActivity::class.java)
//        startActivityForResult(intent, requestCode)
    }

}