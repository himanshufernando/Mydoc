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

    val isNewsListLoading = ObservableField<Boolean>()
    var repo = NewsRepo(client,dataDao)

    private val _selectedResultResponse = MutableLiveData<ResultResponse>()
    val selectedResultResponse: LiveData<ResultResponse> = _selectedResultResponse

    val dataRefrash = MutableLiveData<Boolean>()

    init {
        dataRefrash.value=true
    }

    val newsList = dataRefrash.switchMap { id ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            isNewsListLoading.set(true)
            try {
                emit(Result.success(repo.getNews()))
                isNewsListLoading.set(false)
            } catch(ioException: Throwable) {
                isNewsListLoading.set(false)
                emit(Result.failure(ioException))
            }
        }
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