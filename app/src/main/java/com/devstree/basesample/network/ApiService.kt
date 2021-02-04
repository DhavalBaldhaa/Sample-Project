package com.devstree.basesample.network

import com.devstree.basesample.model.BaseModel
import com.devstree.basesample.model.ObjectBaseModel
import com.devstree.basesample.model.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ApiService {

    @POST(NetworkURL.LOGIN)
    fun login(@Body params: HashMap<String, Any>?): Call<ObjectBaseModel<User>>?

    @POST(NetworkURL.SOCIAL_LOGIN)
    fun socialLogin(@Body params: HashMap<String, Any>?): Call<ObjectBaseModel<User>>?

    @Multipart
    @POST(NetworkURL.REGISTER)
    fun register(@PartMap params: HashMap<String, RequestBody>?, @Part image: MultipartBody.Part?): Call<ObjectBaseModel<User>>?

    @Multipart
    @POST(NetworkURL.USER_UPDATE)
    fun updateProfile(@PartMap params: HashMap<String, RequestBody>?, @Part image: MultipartBody.Part?): Call<ObjectBaseModel<User>>?

    @GET(NetworkURL.GET_USER_PROFILE)
    fun getUserProfile(): Call<ObjectBaseModel<User>>?

    @POST(NetworkURL.FORGOT_PASSWORD)
    fun forgotPassword(@Body params: HashMap<String, Any>?): Call<BaseModel>?

    @GET(NetworkURL.VERIFY_CODE + "{code}")
    fun verifyCode(@Path("code") code: String): Call<ObjectBaseModel<User>>?

    @POST(NetworkURL.RESENT_CODE)
    fun resendCode(@Body params: HashMap<String, Any>?): Call<BaseModel>?

    @POST(NetworkURL.RESET_PASSWORD + "{code}")
    fun resetPassword(@Body params: HashMap<String, Any>?, @Path("code") code: String): Call<BaseModel>?

    @POST(NetworkURL.CHANGE_PASSWORD)
    fun changePassword(@Body params: HashMap<String, Any>?): Call<BaseModel>?
}