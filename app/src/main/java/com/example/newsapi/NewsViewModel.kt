package com.example.newsapi

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    private val _news = MutableLiveData<List<Article>>()
    val news: LiveData<List<Article>> get() = _news

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchHealthNews() {
        if (_loading.value == true) {
            Log.d("NewsViewModel", "Already loading, skipping request")
            return
        }
        _loading.value = true
        RetrofitClient.apiService.getHealthNews()
            .enqueue(object : Callback<News> {
                override fun onResponse(call: Call<News>, response: Response<News>) {
                    _loading.value = false
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles ?: emptyList()
                        _news.value = articles
                        Log.d("NewsViewModel", "Fetched ${articles.size} articles")
                    } else {
                        _error.value = "Error: ${response.code()}"
                        Log.e("NewsViewModel", "API Error: ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<News>, t: Throwable) {
                    _loading.value = false
                    _error.value = t.message
                    Log.e("NewsViewModel", "API request failed: ${t.message}")
                }
            })
    }
}
