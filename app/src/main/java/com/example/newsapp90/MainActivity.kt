package com.example.newsapp90

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*


data class News(val title:String,val author:String,val url:String,val urlToImage:String)

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter:NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "News App"
        rvNews.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        rvNews.adapter = mAdapter
    }

    private fun fetchData() {
        val url = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=c41f1b2f66264e9c86b5630d8d37a44d"
        val jsonObjectRequest = object: JsonObjectRequest(
            Request.Method.GET,url,null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }
                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {
                Toast.makeText(this,"Error",Toast.LENGTH_LONG).show()

            }
        )
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"]="Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}