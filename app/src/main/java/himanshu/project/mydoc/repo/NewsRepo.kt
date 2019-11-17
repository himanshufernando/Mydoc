package himanshu.project.mydoc.repo


import androidx.lifecycle.liveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import himanshu.project.mydoc.BuildConfig
import himanshu.project.mydoc.data.dataModel.Data
import himanshu.project.mydoc.data.db.DataDao
import himanshu.project.mydoc.services.api.APIInterface
import himanshu.project.mydoc.services.network.InternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

import java.io.IOException
import javax.inject.Inject

class NewsRepo @Inject constructor(var client: APIInterface, val dataDao: DataDao) {
    private val apiKey = BuildConfig.API_KEY


    suspend fun getNews(): Data {
        return if (InternetConnection.checkInternetConnection()) {
            var list = client.getNews(apiKey)
            dataDao.insertAll(list)
            list
        } else {
            dataDao.getNewsFromDB()
        }

    }



}