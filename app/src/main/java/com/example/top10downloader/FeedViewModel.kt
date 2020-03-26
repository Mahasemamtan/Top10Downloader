package com.example.top10downloader

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

/**
 * Created by Bojan Antić on 3/22/20
 **/

private const val TAG = "FeedViewModel"
val EMPTY_FEED_LIST: List<FeedEntry> = Collections.emptyList()

class FeedViewModel : ViewModel(), DownaloadData.DownloaderaCallBack {

    private var downloadData: DownaloadData? = null
    private var cachedFeedUrl = "INVALIDATED"

    private val feed = MutableLiveData<List<FeedEntry>>()
    val feedEntries: LiveData<List<FeedEntry>>
        get() = feed

    init {
        feed.postValue(EMPTY_FEED_LIST)
    }

    fun downloadUrl(feedUrl: String) {
        Log.d(TAG, ".downloadUrl: called with url: $feedUrl")
        if (feedUrl != cachedFeedUrl) {
            downloadData = DownaloadData(this)
            downloadData?.execute(feedUrl)
            cachedFeedUrl = feedUrl
        } else {
            Log.d(TAG, "feedUrl - UNCHANGED")
        }
    }

    fun invalidate() {
        cachedFeedUrl = "INVALIDATE"
    }

    override fun onDataAvailable(data: List<FeedEntry>) {
        Log.d(TAG, ".onDataAvailable: called")
        feed.value = data
        Log.d(TAG, ".onDataAvailable: ends")
    }

    override fun onCleared() {
        Log.d(TAG, ".onCleared: canceling pending downloads")
        downloadData?.cancel(true)
    }
}