package com.devstree.basesample.utilities

import android.text.format.DateFormat
import com.omninos.yabisso.utility.SharedPrefConstant
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Dhaval Baldha on 13/4/20
 */
object AppHelper {
//    fun getId(): Long {
//        return getUser().userId
//    }

    fun getAuthToken(): String {
        return PreferenceHelper.getInstance().getString(SharedPrefConstant.JWT_TOKEN) ?: ""
    }

    fun isLogin(): Boolean {
        return PreferenceHelper.getInstance().getBoolean(SharedPrefConstant.IS_LOGIN)
    }

    fun isExistsInServer(): Boolean {
        return PreferenceHelper.getInstance().getBoolean(SharedPrefConstant.IS_EXISTS_IN_SERVER)
    }

    fun setExistsInServer(isExistsInServer: Boolean) {
        PreferenceHelper.getInstance()
            .putBoolean(SharedPrefConstant.IS_EXISTS_IN_SERVER, isExistsInServer)
    }

    fun isSocialLogin(): Boolean {
        return PreferenceHelper.getInstance().getBoolean(SharedPrefConstant.IS_SOCIAL_LOGIN)
    }

    fun setSocialLogin(isSocialLogin: Boolean) {
        PreferenceHelper.getInstance().putBoolean(SharedPrefConstant.IS_SOCIAL_LOGIN, isSocialLogin)
    }

//    fun setLoginAndSaveUser(user: User?) {
//        if (user == null) return
//        user.isLoginUser = true
//        user.password = user.getEncryptedPassword()
//        PreferenceHelper.getInstance().putString(SharedPrefConstant.JWT_TOKEN, user.token)
//        setLogin(true)
//        setUserAndInsertToDb(user)
//
//        if (user.google.isNotEmpty() || user.facebook.isNotEmpty()) {
//            setSocialLogin(true)
//        }
//    }

    fun setLogin(isLogin: Boolean) {
        PreferenceHelper.getInstance().putBoolean(SharedPrefConstant.IS_LOGIN, isLogin)
    }
//
//    fun setUser(user: User?) {
//        if (user == null) return
//        PreferenceHelper.getInstance().putString(SharedPrefConstant.USER_DETAILS, Gson().toJson(user))
//    }
//
//    fun getUser(): User {
//        return Gson().fromJson(
//            PreferenceHelper.getInstance().getString(SharedPrefConstant.USER_DETAILS),
//            User::class.java
//        ) ?: User()
//    }

    private val UUID_PATTERN: String =
        "[a-zA-Z0-9]{8}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{12}_?.*"

    fun checkUUID(uuid: String): Boolean {
        val pattern = Pattern.compile(UUID_PATTERN)
        val matcher = pattern.matcher(uuid)
        if (matcher.matches()) {
            return true
        }
        return false
    }


    fun getFormattedDate(time: Long): String? {
        val smsTime = Calendar.getInstance()
        smsTime.timeInMillis = time
        val now = Calendar.getInstance()
        val timeFormatString = ", MMMM d"
        val dateTimeFormatString = "EEEE, MMMM d"
        val HOURS = 60 * 60 * 60.toLong()
        return if (now[Calendar.DATE] == smsTime[Calendar.DATE]) {
            "Today" + DateFormat.format(timeFormatString, smsTime)
        } else if (now[Calendar.DATE] - smsTime[Calendar.DATE] == 1) {
            DateFormat.format(dateTimeFormatString, smsTime).toString()
        } else if (now[Calendar.YEAR] == smsTime[Calendar.YEAR]) {
            DateFormat.format(dateTimeFormatString, smsTime).toString()
        } else {
            DateFormat.format("MMMM dd yyyy, h:mm aa", smsTime).toString()
        }
    }

    fun getDecimalFormatter(): DecimalFormat {
        val formatter = DecimalFormat("##.00")
        return formatter
    }
}