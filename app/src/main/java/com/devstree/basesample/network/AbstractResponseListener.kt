package com.devstree.basesample.network

import com.devstree.basesample.model.BaseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class AbstractResponseListener<T : BaseModel> : Callback<T> {
    var code = 200
    var message = ""
        private set
    var isSuccess = false
        private set
    var messageCode = ""
    var exception: Throwable? = null

    abstract fun result(result: T?)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.isSuccessful) {
            success(response.body())
        } else {
            onFailure(ParserHelper.baseError(response))
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        t.printStackTrace()
        onError(call, t)
    }

    private fun success(data: T?) {
        if (data != null) {
            message = data.message
            isSuccess = data.success
            messageCode = data.messageCode
        } else {
            isSuccess = false
        }
        result(data)
    }

    private fun onFailure(baseModel: BaseModel?) {
        if (baseModel != null) {
            message = baseModel.message
            isSuccess = baseModel.success
            messageCode = baseModel.messageCode
        } else {
            isSuccess = false
        }
        result(null)
    }

    private fun onError(call: Call<T>, t: Throwable) {
        message = ResponseHandler.handleErrorResponse(t)
        isSuccess = false
        exception = t
        result(null)
    }
}