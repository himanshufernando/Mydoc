package himanshu.project.mydoc.views.activitys.news

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import himanshu.project.mydoc.R
import himanshu.project.mydoc.data.dataModel.NetworkError
import himanshu.project.mydoc.data.dataModel.ResultResponse
import himanshu.project.mydoc.databinding.ActivityMainBinding
import himanshu.project.mydoc.services.network.NetworkErrorHandler
import himanshu.project.mydoc.viewmodels.NewsViewModels
import himanshu.project.mydoc.views.adapters.NewsAdapters
import kotlinx.android.synthetic.main.activity_main.*



class NewsActivity : AppCompatActivity() {

   private val viewmodel: NewsViewModels by viewModels { NewsViewModels.LiveDataVMFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )
        binding.lifecycleOwner = this
        binding.news = viewmodel

        val adapter = NewsAdapters()
        recyclerview_news_list.adapter = adapter
        subscribeNewsToUi(adapter)


    }

    private fun subscribeNewsToUi(adapter: NewsAdapters) {
        viewmodel.newsList.observe(this){news -> news.onSuccess {it
                adapter.submitList(it.results)
                adapter.setOnItemClickListener(object : NewsAdapters.ClickListener {
                    override fun onClick(resultResponse: ResultResponse, aView: View) {

                    }
                })
            }
            news.onFailure {it
                val networkErrorHandler = NetworkErrorHandler()
                errorAlertDialog(networkErrorHandler(it))
            }
        }
    }


    private fun errorAlertDialog(networkError: NetworkError) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle(networkError.errorTitle)
        alertDialogBuilder.setMessage(networkError.errorMessage)
        alertDialogBuilder.setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { _, _ -> return@OnClickListener })
        alertDialogBuilder.show()
    }


}

