package fr.airweb.news.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import fr.airweb.news.model.NewsModel
import fr.airweb.news.network.NewsService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsListViewModel @Inject constructor(var newsService: NewsService): ViewModel() {


    var newsList = MutableLiveData<NewsModel>()
    var error = MutableLiveData<String>()

    init {
        getAirwebNews()
    }

    fun getAirwebNews() {
        newsService.getNews()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.d("airwebNews", "irwebNews got ok $it")
                    newsList.postValue(it)
                },
                {
                    Log.d("airwebNews", "irwebNews got ko throwable !! $it")
                    error.postValue("Error - getNews API : $it")
                }
            )
    }
}