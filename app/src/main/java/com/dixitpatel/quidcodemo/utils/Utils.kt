package com.dixitpatel.quidcodemo.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.dixitpatel.quidcodemo.constant.API_KEY
import com.dixitpatel.quidcodemo.constant.API_SECRET
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * Common utils methods
 */
class Utils
{
    companion object {

        fun genMethodSignature(username: String, password: String): String {
            val builder = "api_key" +
                    API_KEY +
                    "methodauth.getMobileSessionpassword" +
                    password + "username" +
                    username +
                    API_SECRET
            return md5Custom(builder)
        }

        /**
         * Method for generating MD5 hash.
         */
        fun md5Custom(st: String): String {
            val messageDigest: MessageDigest
            var digest: ByteArray? = ByteArray(0)
            try {
                messageDigest = MessageDigest.getInstance("MD5")
                messageDigest.reset()
                messageDigest.update(st.toByteArray())
                digest = messageDigest.digest()
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            }
            val bigInt = BigInteger(1, digest)
            val md5Hex = StringBuilder(bigInt.toString(16))
            while (md5Hex.length < 32) {
                md5Hex.insert(0, "0")
            }
            return md5Hex.toString()
        }

        fun isNetworkAvailable(context: Context): Boolean
        {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            var isNetworkAvail = false
            try {
                val n = cm?.activeNetwork
                val nc = cm?.getNetworkCapabilities(n)
                isNetworkAvail = nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                return isNetworkAvail
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            return isNetworkAvail
        }

        fun hideKeyboard(activity: Activity, flags: Int) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val viewWithFocus = activity.currentFocus
            val windowToken = viewWithFocus?.windowToken
            if (windowToken != null) {
                imm.hideSoftInputFromWindow(windowToken, flags)
            }
        }

        @JvmOverloads
        fun hideKeyboard(fragment: Fragment, flags: Int = InputMethodManager.HIDE_NOT_ALWAYS) {
            val activity: Activity? = fragment.activity
            if (activity != null) {
                hideKeyboard(activity, flags)
            }
        }

        fun showSoftKeyboard(me: Activity, edt: EditText?) {
            try {
                val imm = me.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(edt, InputMethodManager.SHOW_IMPLICIT)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}