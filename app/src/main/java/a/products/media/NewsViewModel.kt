package a.products.media

import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NewsViewModel: ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    init {
        Log.d("NewsAPI", "Используемый ключ: ${Constant.apiKey.take(5)}...")
        fetchNewsTopHeadlines()
    }

    private fun fetchNewsTopHeadlines() {
        val newsApiClient = NewsApiClient(Constant.apiKey)
        val request = TopHeadlinesRequest.Builder()
            .language("en")
            .country("us")
            .build()

        newsApiClient.getTopHeadlines(request, object : NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let {
                    _articles.postValue(it)
                }
            }

            override fun onFailure(throwable: Throwable?) {
                Log.e("NewsAPI", "Ошибка запроса: ${throwable?.message}", throwable)
                throwable?.printStackTrace()
            }

        })

    }
}