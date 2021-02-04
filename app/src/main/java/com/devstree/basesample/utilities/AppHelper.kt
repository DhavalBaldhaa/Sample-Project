package com.devstree.basesample.utilities

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.text.format.DateFormat
import android.util.Base64
import android.util.DisplayMetrics
import com.devstree.basesample.BuildConfig
import com.devstree.basesample.Controller
import com.devstree.basesample.model.User
import com.google.gson.Gson
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.DecimalFormat
import java.util.*

/**
 * Created by Dhaval Baldha on 13/4/20
 */
object AppHelper {

    var user: User? = null
    fun setLogin(isLogin: Boolean) {
        PreferenceManager.getInstance().putBoolean(SharedPrefConstant.IS_LOGIN, isLogin)
    }

    fun isLogin(): Boolean {
        return PreferenceManager.getInstance().getBoolean(SharedPrefConstant.IS_LOGIN)
    }

    fun setUserDetail(user: User?) {
        if (user == null) return
        PreferenceManager.getInstance().putString(SharedPrefConstant.USER_DETAILS, Gson().toJson(user))
        this.user = user
    }

    fun getUserDetail(): User? {
        if (user == null) {
            user = Gson().fromJson(PreferenceManager.getInstance().getString(SharedPrefConstant.USER_DETAILS), User::class.java)
        }
        return user
    }

    fun getAuthToken(): String {
        return getUserDetail()?.token.orEmpty()
    }

    @Suppress("DEPRECATION")
    @SuppressLint("PackageManagerGetSignatures")
    fun printKeyHash() {
        try {
            val info = Controller.instance.packageManager.getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

    fun dpToPx(dp: Int): Int {
        val displayMetrics = Controller.instance.getResources().getDisplayMetrics()
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }


    fun getFormattedDate(time: Long): String {
        val smsTime = Calendar.getInstance()
        smsTime.timeInMillis = time
        val now = Calendar.getInstance()
        val timeFormatString = ", MMMM d"
        val dateTimeFormatString = "EEEE, MMMM d"
        val HOURS = 60 * 60 * 60.toLong()
        return when {
            now[Calendar.DATE] == smsTime[Calendar.DATE] -> {
                "Today" + DateFormat.format(timeFormatString, smsTime)
            }
            now[Calendar.DATE] - smsTime[Calendar.DATE] == 1 -> {
                DateFormat.format(dateTimeFormatString, smsTime).toString()
            }
            now[Calendar.YEAR] == smsTime[Calendar.YEAR] -> {
                DateFormat.format(dateTimeFormatString, smsTime).toString()
            }
            else -> {
                DateFormat.format("MMMM dd yyyy, h:mm aa", smsTime).toString()
            }
        }
    }

    fun getDecimalFormatter(): DecimalFormat {
        val formatter = DecimalFormat("##.00")
        return formatter
    }
}