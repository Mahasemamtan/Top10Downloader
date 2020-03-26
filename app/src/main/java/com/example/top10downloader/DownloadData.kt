package com.example.top10downloader

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

/**
 * Created by Bojan Antić on 3/22/20
 **/

private const val TAG = "DownloadData"

class DownaloadData(private val callBack: DownloaderaCallBack) :
    AsyncTask<String, Void, String>() {

    interface DownloaderaCallBack {
        fun onDataAvailable(data: List<FeedEntry>)
    }

    override fun onPostExecute(result: String) {
        val parseApplications = ParseApplications()
        if (result.isNotEmpty()) {
            parseApplications.parse(result)
        }

        callBack.onDataAvailable(parseApplications.applications)
    }

    override fun doInBackground(vararg url: String): String {
        Log.d(TAG, ".doInBackground: starts with ${url[0]}")
        val rssFeed = downloadXML(url[0])
        if (rssFeed.isEmpty()) {
            Log.e(TAG, "doInBackground: Error downloading")
        }
        return rssFeed
    }

    private fun downloadXML(urlPath: String): String {
        try {
            return URL(urlPath).readText()
        } catch (e: MalformedURLException) {
            Log.d(TAG, ".downloadXML: Invalid URL: " +e.message)
        } catch (e: IOException) {
            Log.d(TAG, ".downloadXML: IOException reading data: " +e.message)
        } catch (e: SecurityException) {
            Log.d(TAG, ".downloadXML: Security exception. Need Permission? " +e.message)
        }

        return "" // Return an empty string in case of an exception
    }
}