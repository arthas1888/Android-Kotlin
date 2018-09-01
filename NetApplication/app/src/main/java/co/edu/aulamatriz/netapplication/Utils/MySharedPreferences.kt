package co.edu.aulamatriz.netapplication.Utils

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences {

    val NAME = "MySharedPreferences_NET"
    var sharedPreferences: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    constructor(mContext: Context) {
        sharedPreferences = mContext.getSharedPreferences(NAME, 0)
        editor = sharedPreferences!!.edit()
    }

    public fun isLogin(): Boolean {
        return sharedPreferences!!.getBoolean("is_login", false)
    }

    fun setLogin(isLogin : Boolean) {
        editor!!.putBoolean("is_login", isLogin)
        editor!!.apply()
    }
}