package com.devstree.basesample.utilities

import android.content.Context
import android.content.SharedPreferences
import com.devstree.basesample.Controller

class PreferenceManager {
    companion object {
        private var instance: PreferenceManager? = null
        fun getInstance(): PreferenceManager {
            if (instance == null)
                instance = PreferenceManager()
            return instance!!
        }

        fun getInstance(context: Context): PreferenceManager {
            if (instance == null)
                instance = PreferenceManager(context)
            return instance!!
        }
    }

    private var sp: SharedPreferences

    private constructor() {
        sp = Controller.instance.getSharedPreferences(SharedPrefConstant.prefName, Context.MODE_PRIVATE)
    }

    private constructor(context: Context) {
        sp = context.getSharedPreferences(SharedPrefConstant.prefName, Context.MODE_PRIVATE)
    }

    fun putString(key: String, value: String?) {
        sp.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return sp.getString(key, "")
    }

    fun getString(key: String, defaultString: String?): String? {
        return sp.getString(key, defaultString)
    }

    fun putBoolean(key: String, value: Boolean) {
        sp.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sp.getBoolean(key, false)
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sp.getBoolean(key, defaultValue)
    }

    fun removeKey(key: String) {
        sp.edit().remove(key).apply()
    }

    fun clearPreference() {
//        sp.edit().clear().apply()
        removeKey(SharedPrefConstant.IS_LOGIN)
        removeKey(SharedPrefConstant.USER_DETAILS)
        removeKey(SharedPrefConstant.BUSINESS_DETAILS)
        removeKey(SharedPrefConstant.USER_TYPE)
        removeKey(SharedPrefConstant.FCM_TOKEN)
    }
}