package com.devstree.basesample.network

import com.devstree.basesample.model.BaseModel
import com.devstree.basesample.model.ObjectBaseModel
import com.devstree.basesample.model.User
import com.devstree.basesample.network.RetroClient.apiService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*

object NetworkCall {
    fun login(param: HashMap<String, Any>?, callback: AbstractResponseListener<ObjectBaseModel<User>>) {
        apiService.login(param)?.enqueue(callback)
    }

    fun socialLogin(param: HashMap<String, Any>?, callback: AbstractResponseListener<ObjectBaseModel<User>>) {
        apiService.socialLogin(param)?.enqueue(callback)
    }

    fun register(param: HashMap<String, RequestBody>?, image: MultipartBody.Part?, callback: AbstractResponseListener<ObjectBaseModel<User>>) {
        apiService.register(param, image)?.enqueue(callback)
    }

    fun updateProfile(param: HashMap<String, RequestBody>?, image: MultipartBody.Part?, callback: AbstractResponseListener<ObjectBaseModel<User>>) {
        apiService.updateProfile(param, image)?.enqueue(callback)
    }

    fun getUserProfile(callback: AbstractResponseListener<ObjectBaseModel<User>>) {
        apiService.getUserProfile()?.enqueue(callback)
    }

    fun verifyCode(code: String, callback: AbstractResponseListener<ObjectBaseModel<User>>) {
        apiService.verifyCode(code)?.enqueue(callback)
    }

    fun resendCode(param: HashMap<String, Any>?, callback: AbstractResponseListener<BaseModel>) {
        apiService.resendCode(param)?.enqueue(callback)
    }

    fun forgotPassword(param: HashMap<String, Any>?, callback: AbstractResponseListener<BaseModel>) {
        apiService.forgotPassword(param)?.enqueue(callback)
    }

    fun resetPassword(param: HashMap<String, Any>?, code: String, callback: AbstractResponseListener<BaseModel>) {
        apiService.resetPassword(param, code)?.enqueue(callback)
    }

    fun changePassword(param: HashMap<String, Any>?, callback: AbstractResponseListener<BaseModel>) {
        apiService.changePassword(param)?.enqueue(callback)
    }
}