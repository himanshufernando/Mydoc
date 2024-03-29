package himanshu.project.mydoc.viewmodels

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import himanshu.project.mydoc.MyDoc
import himanshu.project.mydoc.data.dataModel.Data
import himanshu.project.mydoc.data.db.AppDatabase
import himanshu.project.mydoc.data.db.DataDao
import himanshu.project.mydoc.repo.NewsRepo
import himanshu.project.mydoc.services.api.APIInterface
import kotlinx.coroutines.Dispatchers

class NewsViewModels(private val client: APIInterface,val dataDao : DataDao) : ViewModel() {

    val isNewsListLoading = ObservableField<Boolean>()
    private val reloadTrigger = MutableLiveData<Boolean>()
    var repo = NewsRepo(client,dataDao)


    val newsList: LiveData<Result<Data>> = liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
        isNewsListLoading.set(true)
        try {
            emit(Result.success(repo.getNews()))
            isNewsListLoading.set(false)
        } catch(ioException: Throwable) {
            isNewsListLoading.set(false)
            emit(Result.failure(ioException))
        }
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