package com.omninos.yabisso.utility

interface SharedPrefConstant {
    companion object {
        const val prefName = "offlineChat"
        const val IS_LOGIN = "is_login"
        const val JWT_TOKEN = "jwt_token"
        const val USER_DETAILS = "user_details"
        const val IS_AUTO_START_PERMISSION = "is_auto_start_permission"
        const val APP_LANGUAGE = "app_language"
        const val IS_SELECT_LANGUAGE = "is_select_language"
        const val DATE_LAST_TOKEN_UPDATE = "date_last_token_update"
        const val FCM_TOKEN = "fire_base_token"
        const val IS_EXISTS_IN_SERVER = "is_exists_in_server"
        const val IS_SOCIAL_LOGIN = "is_social_login"
    }
}