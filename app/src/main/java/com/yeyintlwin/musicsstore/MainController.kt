package com.yeyintlwin.musicsstore

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.aspsine.multithreaddownload.DownloadConfiguration
import com.aspsine.multithreaddownload.DownloadManager

class MainController : Application() {

    @SuppressLint("CommitPrefEdits")
    override fun onCreate() {
        super.onCreate()
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        editor = preferences!!.edit()
        initDownloader()
    }

    private fun initDownloader() {
        val downloadConfiguration = DownloadConfiguration()
        downloadConfiguration.maxThreadNum = 10
        downloadConfiguration.threadNum = 1
        DownloadManager.getInstance().init(this, downloadConfiguration)
    }

    companion object {
        private var preferences: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null

        fun putBoolean(key: String, value: Boolean?) {
            editor!!.putBoolean(key, value!!).apply()
        }

        fun getBoolean(key: String, defValue: Boolean?): Boolean {
            return preferences!!.getBoolean(key, defValue!!)
        }

        fun putInt(key: String, value: Int?) {
            editor!!.putInt(key, value!!).apply()
        }

        fun getInt(key: String, defValue: Int?): Int {
            return preferences!!.getInt(key, defValue!!)
        }

        fun putString(key: String, value: String) {
            editor!!.putString(key, value).apply()
        }

        fun getString(key: String, defValue: String): String? {
            return preferences!!.getString(key, defValue)
        }
    }
}
