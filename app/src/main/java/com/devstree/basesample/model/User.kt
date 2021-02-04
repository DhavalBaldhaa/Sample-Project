package com.devstree.basesample.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("id", alternate = ["_id"])
    val userId: String,
    var token: String,

    @SerializedName("f_name")
    val firstName: String,

    @SerializedName("l_name")
    val lastName: String,
    val phone: String,
    val email: String,
    val role: String,

    @SerializedName("profile_pic")
    val profilePic: String,

    @SerializedName("fcm_token")
    val fcmToken: String,

    @SerializedName("sign_up_guid")
    val sign_up_guid: String,

    @SerializedName("device_type")
    val deviceType: String,

    @SerializedName("is_active")
    val isActive: Boolean = false,

    @SerializedName("is_business_details_added")
    var isBusinessDetailsAdded: Boolean = false,

    val facebook_login: String? = ""
) : Serializable {

}

//inline fun <reified T : Any> UserType?.typeCall(normal_user: () -> T, business_user: () -> T): T {
//    return when (this) {
//        UserType.BUSINESS_USER -> business_user.invoke()
//        else -> normal_user.invoke()
//    }
//}
