package com.example.top10downloader

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.net.URL

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val downloadData = DownaloadData()
        downloadData.execute("http://ax.itunes.apple.com/WebObjects/MZStoreServices.woa/ws/RSS/topsongs/limit=10/xml")
    }

    companion object {
        private class DownaloadData : AsyncTask<String, Void, String>() {
            private val TAG = "DownloadData"

            override fun onPostExecute(result: String?) {
                Log.d(TAG, result.toString())
                super.onPostExecute(result)
            }

            override fun doInBackground(vararg url: String?): String {
                val rssFeed = downloadXML(url[0])
                if (rssFeed.isEmpty()) {
                    Log.e(TAG, "doInBackground: Error downloading")
                }
                return rssFeed
            }

            private fun downloadXML(urlPath: String?): String {
                return URL(urlPath).readText()
            }
        }
    }
}
