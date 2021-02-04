package com.devstree.basesample.utilities

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern


/**
 * Created by Dhaval Baldha on 6/1/21
 */
object Validator {
    const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$"

    fun isValidPassword(password: String?): Boolean {
        if (password.isNullOrEmpty()) return false
        val matcher = Pattern.compile(PASSWORD_PATTERN).matcher(password)
        return matcher.matches()
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        if (target == null) return false
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

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
}