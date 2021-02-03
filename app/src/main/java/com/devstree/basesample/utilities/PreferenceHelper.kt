package com.devstree.basesample.utilities

import android.content.Context
import android.content.SharedPreferences
import com.devstree.basesample.view.ui.base.Controller
import com.omninos.yabisso.utility.SharedPrefConstant

class PreferenceHelper {
    private var pref: SharedPreferences
    private constructor() {
        pref = Controller.instance.getSharedPreferences(
            SharedPrefConstant.prefName,
            Context.MODE_PRIVATE
        )
    }
    
    private constructor(context: Context) {
        pref = context.getSharedPreferences(
            SharedPrefConstant.prefName,
            Context.MODE_PRIVATE
        )
    }
    
    fun putString(key: String?, value: String?) {
        pref.edit().putString(key, value).apply()
    }
    
    fun getString(key: String?): String? {
        return pref.getString(key, "")
    }
    
    fun getString(key: String?, defaultString: String?): String? {
        return pref.getString(key, defaultString)
    }
    
    fun putBoolean(key: String?, value: Boolean) {
        pref.edit().putBoolean(key, value).apply()
    }
    
    fun getBoolean(key: String?): Boolean {
        return pref.getBoolean(key, false)
    }
    
    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return pref.getBoolean(key, defaultValue)
    }
    
    fun removeKey(key: String?) {
        pref.edit().remove(key).apply()
    }
    
    fun clearPreference() {
        pref.edit().clear().apply()
    }
    
    companion object {
        private var instance: PreferenceHelper? = null
        fun getInstance(): PreferenceHelper {
            if (instance == null)
                instance = PreferenceHelper()
            return instance!!
        }
        
        fun getInstance(context: Context): PreferenceHelper {
            if (instance == null)
                instance = PreferenceHelper(context)
            return instance!!
        }
    }
}