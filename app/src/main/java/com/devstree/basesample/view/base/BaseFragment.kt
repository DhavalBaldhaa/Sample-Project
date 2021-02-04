package com.devstree.foodguru.common

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.devstree.basesample.view.base.BaseActivity
import com.devstree.basesample.view.base.NavigationActivity
import java.util.*

abstract class BaseFragment : Fragment() {
    var mContext: Context? = null
    var base: BaseActivity? = null
    var navigation: NavigationActivity? = null
    var rootView: View? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        if (context is BaseActivity) {
            base = context
        }
        if (context is NavigationActivity) {
            navigation = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = view
    }

    fun showSnackBar(message: String?) {
        if (base == null) return
        base?.showSnackBar(rootView, message)
    }

    fun showSnackBar(view: View?, message: String?) {
        if (base == null) return
        base?.showSnackBar(view, message)
    }

    fun getDummyList(length: Int): List<String> {
        val list: MutableList<String> =
            ArrayList()
        for (i in 0 until length) {
            list.add("test $i")
        }
        return list
    }
//
//    protected fun toMultipartBody(param: String, file: File?): MultipartBody.Part? {
//        var attachment: MultipartBody.Part? = null
//        val requestFile: RequestBody
//        if (file != null) {
//            requestFile = RequestBody.create(MediaType.get("image/*"), file)
//            val filename = file.name
//            attachment = MultipartBody.Part.createFormData(param, filename, requestFile)
//        }
//        return attachment
//    }
}