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
    lateinit var binding:FragmentNewsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_news, container,false)
        binding.news=viewmodel


        binding.root.swipe_refresh_layout.setOnRefreshListener {
            viewmodel.refreshUsers()
        }
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = NewsAdapters()
        binding.root.recyclerview_news_list.adapter = adapter
        subscribeNewsToUi(adapter)
    }
    private fun subscribeNewsToUi(adapter: NewsAdapters) {
        binding.root.swipe_refresh_layout.isRefreshing = true

        viewmodel.newsList.observe(viewLifecycleOwner){news ->
            news.onSuccess {it
                binding.root.swipe_refresh_layout.isRefreshing = false
                adapter.submitList(it.results)
                adapter.setOnItemClickListener(object : NewsAdapters.ClickListener {
                    override fun onClick(resultResponse: ResultResponse, aView: View) {
                        val resultAcction = NewsFragmentDirections.fragmentNewstoNewsContent(resultResponse)
                        view?.findNavController()?.navigate(resultAcction)
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
