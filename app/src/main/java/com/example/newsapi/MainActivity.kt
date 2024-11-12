package com.example.newsapi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsAdapter(mutableListOf())
        binding.recyclerView.adapter = newsAdapter

        newsViewModel.fetchHealthNews()

        newsViewModel.news.observe(this, Observer { articles ->
            newsAdapter.updateData(articles)
        })

        newsViewModel.error.observe(this, Observer { errorMessage ->
            Toast.makeText(this, errorMessage ?: "Unknown error", Toast.LENGTH_SHORT).show()
        })

    }
}
