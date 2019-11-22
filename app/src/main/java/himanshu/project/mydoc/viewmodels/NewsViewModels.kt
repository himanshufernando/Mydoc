package himanshu.project.mydoc.viewmodels

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import himanshu.project.mydoc.MyDoc
import himanshu.project.mydoc.data.dataModel.Data
import himanshu.project.mydoc.data.dataModel.ResultResponse
import himanshu.project.mydoc.data.db.AppDatabase
import himanshu.project.mydoc.data.db.DataDao
import himanshu.project.mydoc.repo.NewsRepo
import himanshu.project.mydoc.services.api.APIInterface
import kotlinx.coroutines.Dispatchers

class NewsViewModels(private val client: APIInterface,val dataDao : DataDao) : ViewModel() {


    var repo = NewsRepo(client,dataDao)

    private val _selectedResultResponse = MutableLiveData<ResultResponse>()
    val selectedResultResponse: LiveData<ResultResponse> = _selectedResultResponse

    private val _newsRefrashStatus = MutableLiveData<Boolean>()
    val newsRefrashStatus: LiveData<Boolean> = _newsRefrashStatus



    init {
        refreshNews()
    }


    val newsList = newsRefrashStatus.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            try {
                emit(Result.success(repo.getNews()))
            } catch(ioException: Throwable) {
                emit(Result.failure(ioException))
            }
        }
    }

    fun refreshNews() {
        _newsRefrashStatus.value =true
    }






    fun setNewsDetails(resultResponse: ResultResponse){
        _selectedResultResponse.value = resultResponse
    }


    object LiveDataVMFactory : ViewModelProvider.Factory {
        private val dataSource = APIInterface.create()
        var app : Context = MyDoc.applicationContext()
        private val dataDAO = AppDatabase.getInstance(app).dataDao()

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModels(dataSource,dataDAO) as T
        }
    }
}