package com.devstree.basesample.view.base

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.devstree.basesample.R
import com.devstree.basesample.model.ObjectBaseModel
import com.devstree.basesample.model.User
import com.devstree.basesample.network.AbstractResponseListener
import com.devstree.basesample.network.NetworkCall
import com.devstree.basesample.utilities.AppHelper
import com.devstree.basesample.utilities.PreferenceManager
import com.devstree.basesample.utilities.SharedPrefConstant
import com.devstree.basesample.utilities.justTry
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
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

    abstract fun initUi()

    override fun setContentView(view: View?) {
        super.setContentView(view)
        initUi()
    }

    fun fullScreenActivity() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

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
        showProgressDialog(getString(R.string.please_wait))
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
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }

    fun keepScreenOn() {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    // set toolbar with center text and allow home button if set isHomeUpEnabled is true
    protected fun setUpToolBar(title: String, isHomeUpEnabled: Boolean) {
        try {
            toolbar = findViewById(R.id.toolBar)
            toolbarTitle = findViewById(R.id.txtToolbarTitle)
            setSupportActionBar(toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            toolbarTitle?.text = title
            val back: ImageView? = findViewById(R.id.imgBack)
            back?.visibility = View.VISIBLE.takeIf { isHomeUpEnabled } ?: View.GONE
            if (isHomeUpEnabled) {
                back?.setOnClickListener { onBackPressed() }
            }
        } catch (e: Exception) {
        }
    }


    protected fun setUpToolBar(@StringRes title: Int, isHomeUpEnabled: Boolean) {
        setUpToolBar(getString(title), isHomeUpEnabled)
    }

    protected fun updateTitle(@StringRes title: Int) {
        toolbarTitle?.setText(title)
    }

    open fun getFcmToken(callBack: (fcmToken: String, isSuccess: Boolean) -> Unit) {
        val fcmToken = PreferenceManager.getInstance().getString(SharedPrefConstant.FCM_TOKEN)
        if (fcmToken.isNullOrEmpty()) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    callBack.invoke(task.exception?.localizedMessage
                        ?: "Fetching FCM registration token failed", false)
                    return@OnCompleteListener
                }
                val token = task.result
                if (token.isNullOrEmpty()) {
                    callBack.invoke(task.exception?.localizedMessage
                        ?: "Fetching FCM registration token failed", false)
                    return@OnCompleteListener
                }
                PreferenceManager.getInstance().putString(SharedPrefConstant.FCM_TOKEN, token)
                callBack.invoke(token, true)
            })
        } else {
            callBack.invoke(fcmToken, true)
        }
    }

    open fun logout(callBack: (isSuccess: Boolean) -> Unit) {
        try {
            logoutActions()
            callBack.invoke(true)
        } catch (e: Exception) {
            e.printStackTrace()
            callBack.invoke(false)
        }
    }

    fun logoutActions() {
        PreferenceManager.getInstance().clearPreference()
        AppHelper.user = null
    }

    open fun showAlertMessage(str: String) {
        showAlertMessage(str = str, null)
    }

    open fun showAlertMessage(str: String, onClickListener: DialogInterface.OnClickListener?) {
        showAlertMessage(null, str, true, "", onClickListener)
    }

    open fun showAlertMessage(
        title: String?, str: String, isCancelable: Boolean, positiveText: String,
        onClickListener: DialogInterface.OnClickListener?,
    ): AlertDialog? {
        try {
            if (alertDialog != null && alertDialog!!.isShowing) {
                alertDialog!!.dismiss()
            }
            val builder = AlertDialog.Builder(this)
                .setMessage(str).setCancelable(isCancelable)
                .setPositiveButton(positiveText.takeIf { positiveText.isNotBlank() }
                    ?: getString(R.string.ok), onClickListener)

            if (!title.isNullOrBlank()) builder.setTitle(title)

            alertDialog = builder.show()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return alertDialog
    }

    open fun showAlertMessage(
        title: String = "",
        str: String,
        isCancelable: Boolean,
        positiveText: String,
        nagetiveText: String,
        callback: (isPositive: Boolean) -> Unit,
    ): AlertDialog? {
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

            if (!title.isBlank()) builder.setTitle(title)

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

        val timePickerDialog = TimePickerDialog(mContext, /*R.style.TimePickerStyle,*/ { _, hour, minute ->
            val pickedDateTime = Calendar.getInstance()
            pickedDateTime.set(startYear, startMonth, startDay, hour, minute)
            onPickedDateTime.invoke(pickedDateTime)
        }, startHour, startMinute, false)

        timePickerDialog.show()
    }

    fun pickPreviousDate(selectedDate: Calendar? = null, onPickedDate: (calender: Calendar) -> Unit) {
        val currentDateTime = selectedDate.takeIf { selectedDate == null } ?: Calendar.getInstance()
        val startYear = currentDateTime.get(Calendar.YEAR)
        val startMonth = currentDateTime.get(Calendar.MONTH)
        val startDay = currentDateTime.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(mContext, { _, year, month, day ->
            val pickedDateTime = Calendar.getInstance()
            pickedDateTime.set(year, month, day)
            onPickedDate.invoke(pickedDateTime)
        }, startYear, startMonth, startDay)

        datePickerDialog.datePicker.maxDate = Date().time
        datePickerDialog.show()
    }

    fun getUserProfile(callback: (data: User) -> Unit) {
        NetworkCall.getUserProfile(object : AbstractResponseListener<ObjectBaseModel<User>>() {
            override fun result(result: ObjectBaseModel<User>?) {
                if (result != null) {
                    callback.invoke(result.data)
                }
            }
        })
    }
}
