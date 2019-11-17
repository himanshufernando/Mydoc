package himanshu.project.mydoc.views.fragments

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.observe
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController

import himanshu.project.mydoc.R
import himanshu.project.mydoc.data.dataModel.NetworkError
import himanshu.project.mydoc.data.dataModel.ResultResponse
import himanshu.project.mydoc.databinding.FragmentNewsBinding
import himanshu.project.mydoc.services.network.NetworkErrorHandler
import himanshu.project.mydoc.viewmodels.NewsViewModels
import himanshu.project.mydoc.views.adapters.NewsAdapters
import kotlinx.android.synthetic.main.fragment_news.view.*


class NewsFragment : Fragment() {

    private val viewmodel: NewsViewModels by viewModels { NewsViewModels.LiveDataVMFactory }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var binding:FragmentNewsBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_news, container,false)
        binding.news=viewmodel

        val adapter = NewsAdapters()
        binding.root.recyclerview_news_list.adapter = adapter
        subscribeNewsToUi(adapter)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    private fun subscribeNewsToUi(adapter: NewsAdapters) {
        viewmodel.newsList.observe(viewLifecycleOwner){news -> news.onSuccess {it
            adapter.submitList(it.results)
            adapter.setOnItemClickListener(object : NewsAdapters.ClickListener {
                override fun onClick(resultResponse: ResultResponse, aView: View) {



                    val bundle = bundleOf("amount" to resultResponse)
                    view?.findNavController()?.navigate(R.id.fragmentNewstoNewsContent,bundle)
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
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setTitle(networkError.errorTitle)
        alertDialogBuilder.setMessage(networkError.errorMessage)
        alertDialogBuilder.setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { _, _ -> return@OnClickListener })
        alertDialogBuilder.show()
    }


}