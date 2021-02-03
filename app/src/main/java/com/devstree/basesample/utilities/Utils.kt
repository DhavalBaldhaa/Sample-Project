package com.devstree.foodguru.utility

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.StringRes
import com.devstree.basesample.view.ui.base.Controller
import java.util.*

/**
 * Created by Dhaval Baldha on 14/4/20
 */
object Utils {
    /**
     * Return pseudo unique ID
     * @return ID
     */
    fun getUniquePsuedoID(): String {
        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their device or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        val m_szDevIDShort =
            "35" + Build.BOARD.length % 10 + Build.BRAND.length % 10 + Build.CPU_ABI.length % 10 + Build.DEVICE.length % 10 + Build.MANUFACTURER.length % 10 + Build.MODEL.length % 10 + Build.PRODUCT.length % 10
        // Thanks to @Roman SL!
        // https://stackoverflow.com/a/4789483/950427
        // Only devices with API >= 9 have android.os.Build.SERIAL
        // http://developer.android.com/reference/android/os/Build.html#SERIAL
        // If a user upgrades software or roots their device, there will be a duplicate entry
        var serial: String? = null
        try {
            serial = Build::class.java.getField("SERIAL")[null]!!.toString()
            // Go ahead and return the serial for api => 9
            return UUID(m_szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
        } catch (exception: Exception) { // String needs to be initialized
            serial = "serial" // some value
        }
        // Thanks @Joe!
        // https://stackoverflow.com/a/2853253/950427
        // Finally, combine the values we have found by using the UUID class to find a unique identifier
        return UUID(m_szDevIDShort.hashCode().toLong(), serial.hashCode().toLong()).toString()
    }


    fun CheckOnMainThread(): Boolean {
        return Looper.getMainLooper().thread == Thread.currentThread()
    }

    fun executeOnMain(runnable: Runnable) {
        Handler(Looper.getMainLooper()).post(runnable)
    }

    fun executeInBackground(runnable: Runnable?) {
        Thread(runnable).start()
    }

    fun executeDelay(runnable: Runnable, delay: Long) {
        Handler().postDelayed(runnable, delay)
    }

    @JvmStatic
    fun Toast(s: String) {
        Toast.makeText(Controller.instance, s, Toast.LENGTH_SHORT).show()
    }

    fun Toast(@StringRes message: Int) {
        Toast.makeText(Controller.instance, message, Toast.LENGTH_SHORT).show()
    }

//    fun requestPermissions(fragment: Fragment?, rationalMessage: String?, request_code: Int, perms: Array<String>) {
//        EasyPermissions.requestPermissions(PermissionRequest.Builder(fragment!!, request_code, *perms)
//            .setRationale(rationalMessage)
//            .setTheme(R.style.AlertDialog)
//            .build())
//    }
//
//    fun requestPermissions(activity: Activity, rationalMessage: String?, request_code: Int, perms: Array<String>) {
//        EasyPermissions.requestPermissions(PermissionRequest.Builder(activity, request_code, *perms)
//            .setRationale(rationalMessage)
//            .setTheme(R.style.AlertDialog)
//            .build())
//    }

//    fun hasInternet(): Boolean {
//        var connected = false
//        try {
//            val connectivityManager = Controller.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//            val networkInfo = connectivityManager.activeNetworkInfo
//            connected = networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected
//            return connected
//        } catch (e: Exception) {
//            Log.e("connectivity $e")
//        }
//        return connected
//    }
//
//    //https://stackoverflow.com/questions/1560788/how-to-check-internet-access-on-android-inetaddress-never-times-out
//    fun hasActiveInternetConnection(): Single<Boolean> {
//        return Single.fromCallable {
//            try {
//                // Connect to Google DNS to check for connection
//                val timeoutMs = 1500
//                val socket = Socket()
//                val socketAddress = InetSocketAddress("8.8.8.8", 53)
//                socket.connect(socketAddress, timeoutMs)
//                socket.close()
//                true
//            } catch (e: IOException) {
//                false
//            }
//        }
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//    }
//
//    fun isActiveInternet(): Boolean {
//        return try {
//            val p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com")
//            val returnVal = p1.waitFor()
//            returnVal == 0
//        } catch (e: Exception) {
//            e.printStackTrace()
//            false
//        }
//    }
}