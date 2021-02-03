package com.devstree.basesample.view.ui.base

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.devstree.basesample.R
import com.devstree.basesample.utilities.PreferenceHelper
import com.devstree.foodguru.utility.justTry
import com.google.android.material.snackbar.Snackbar
import com.omninos.yabisso.utility.SharedPrefConstant
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
abstract class BaseActivity : AppCompatActivity() {
    private var snackbar: Snackbar? = null
    var mContext: Context = this
    private var toolbar: Toolbar? = null
    private var toolbarTitle: TextView? = null
    private val strUserEndPoints = ArrayList<String>()
    private var alertDialog: AlertDialog? = null
    private var initialLocale: String? = null

    fun showSnackBar(view: View?, str: String?) {
        if (view == null) return
        if (str.isNullOrEmpty()) return
        if (snackbar != null && snackbar!!.isShownOrQueued) {
            snackbar?.dismiss()
        }
        snackbar = Snackbar.make(view, str, Snackbar.LENGTH_SHORT)
        snackbar?.show()
    }

    fun showSnackBar(str: String?) {
        val view: View = this.window.decorView
        if (str.isNullOrEmpty()) return
        if (snackbar != null) snackbar?.dismiss()
        snackbar = Snackbar.make(view, str, Snackbar.LENGTH_SHORT)
        snackbar?.show()
    }

    fun toast(str: String?) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
    }

    open fun recreateActivity() {
        startActivity(intent)
        finish()
        overridePendingTransition(0, 0)
    }

    fun hideKeyBoard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(window.decorView.windowToken, 0)
    }

    private var dialog: ProgressDialog? = null
    fun showProgressDialog() {
        showProgressDialog("")
    }

    fun showProgressDialog(msg: String?) {
        if (dialog == null) {
            dialog = ProgressDialog.show(
                this,
                null,
                getString(R.string.please_wait).takeIf { msg.isNullOrEmpty() } ?: msg,
                true
            )
        }
        if (!dialog!!.isShowing)
            dialog!!.show()
    }

    fun hideProgressDialog() {
        justTry {
            if (dialog != null) {
                dialog!!.dismiss()
            }
        }
    }

    fun setSecureActivity() {
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
    }

    fun keepScreenOn() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    // set toolbar with center text and allow home button if set isHomeUpEnabled is true
    protected fun setUpToolBar(title: String, isHomeUpEnabled: Boolean) {
        try {
//            toolbar = findViewById(R.id.toolbar)
//            toolbarTitle = findViewById(R.id.txtToolbarTitle)
//            setSupportActionBar(toolbar)
//            supportActionBar?.setDisplayShowTitleEnabled(false)
//            toolbarTitle?.text = title
//            if (isHomeUpEnabled) {
//                imgBack?.visibility = View.VISIBLE.takeIf { isHomeUpEnabled } ?: View.GONE
//                imgBack?.setOnClickListener { onBackPressed() }
//            }
        } catch (e: Exception) {
        }
    }

//    protected fun showHorizontalProgressBar(isVisible: Boolean) {
//        Utils.executeOnMain(Runnable {
//            progress_horizontal?.visibility = View.VISIBLE.takeIf { isVisible } ?: View.GONE
//        })
//    }

    protected fun setUpToolBar(@StringRes title: Int, isHomeUpEnabled: Boolean) {
        setUpToolBar(getString(title), isHomeUpEnabled)
    }

    protected fun updateTitle(@StringRes title: Int) {
        toolbarTitle?.setText(title)
    }

    open fun logout(callBack: (isSuccess: Boolean) -> Unit) {
        try {
            PreferenceHelper.getInstance().removeKey(SharedPrefConstant.USER_DETAILS)
            PreferenceHelper.getInstance().removeKey(SharedPrefConstant.JWT_TOKEN)
            PreferenceHelper.getInstance().removeKey(SharedPrefConstant.IS_EXISTS_IN_SERVER)
            callBack.invoke(true)
        } catch (e: Exception) {
            e.printStackTrace()
            callBack.invoke(false)
        }
    }


    open fun showAlertMessage(str: String) {
        showAlertMessage(str, null)
    }

    open fun showAlertMessage(str: String, onClickListener: DialogInterface.OnClickListener?) {
        showAlertMessage(null, str, true, "", onClickListener)
    }

    open fun showAlertMessage(
        title: String?, str: String, isCancelable: Boolean, positiveText: String,
        onClickListener: DialogInterface.OnClickListener?
    ): AlertDialog? {
        try {
            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
            }
            val builder = AlertDialog.Builder(this)
                .setMessage(str).setCancelable(isCancelable)
                .setPositiveButton(positiveText.takeIf { positiveText.isBlank() }
                    ?: getString(R.string.ok), onClickListener)

            if (!title.isNullOrBlank()) builder.setTitle(title)

            alertDialog = builder.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return alertDialog
    }

    open fun showAlertMessage(str: String, isCancelable: Boolean, positiveText: String, nagetiveText: String, callback: (isPositive: Boolean) -> Unit): AlertDialog? {
        try {
            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
            }
//            val builder = AlertDialog.Builder(ContextThemeWrapper(this, R.style.AlertDialogCustom))
            val builder = AlertDialog.Builder(this)
                .setMessage(str)
                .setCancelable(isCancelable)
                .setPositiveButton(positiveText.takeIf { positiveText.isNotBlank() }
                    ?: getString(R.string.ok)) { _, _ -> callback.invoke(true) }
                .setNegativeButton(nagetiveText.takeIf { nagetiveText.isNotBlank() }
                    ?: getString(R.string.cancel)) { _, _ -> callback.invoke(false) }

            if (!title.isNullOrBlank()) builder.setTitle(title)

            alertDialog = builder.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return alertDialog
    }

    fun pickDateTime(onPickedDateTime: (calender: Calendar) -> Unit) {
        val currentDateTime = Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)
        val startHour = currentDateTime.get(Calendar.HOUR_OF_DAY)
        val startMinute = currentDateTime.get(Calendar.MINUTE)

//        DatePickerDialog(mContext, R.style.DatePickerStyle, DatePickerDialog.OnDateSetListener { _, year, month, day ->
//            TimePickerDialog(mContext, R.style.TimePickerStyle, TimePickerDialog.OnTimeSetListener { _, hour, minute ->
//                val pickedDateTime = Calendar.getInstance()
//                pickedDateTime.set(year, month, day, hour, minute)
//                onPickedDateTime.invoke(pickedDateTime)
//            }, startHour, startMinute, false).show()
//        }, startYear, startMonth, startDay).show()
    }

    open fun getFcmToken(callBack: (fcmToken: String, isSuccess: Boolean) -> Unit) {
        val fcmToken = PreferenceHelper.getInstance().getString(SharedPrefConstant.FCM_TOKEN)
        if (fcmToken.isNullOrEmpty()) {
//            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult: InstanceIdResult ->
//                PreferenceHelper.getInstance().putString(SharedPrefConstant.FCM_TOKEN, instanceIdResult.token)
//                callBack.invoke(instanceIdResult.token, true)
//            }.addOnFailureListener { exception ->
//                callBack.invoke(exception.localizedMessage
//                    ?: getString(R.string.something_went_wrong), false)
//            }
        } else {
            callBack.invoke(fcmToken, true)
        }
    }
}
