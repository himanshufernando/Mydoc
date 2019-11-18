package himanshu.project.mydoc.views.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation

import himanshu.project.mydoc.R
import himanshu.project.mydoc.databinding.FragmentNewsContentBinding
import himanshu.project.mydoc.viewmodels.NewsViewModels


class NewsContentFragment : Fragment() {

    private val viewmodel: NewsViewModels by viewModels { NewsViewModels.LiveDataVMFactory }
    lateinit var binding:FragmentNewsContentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_news_content, container,false)
        binding.viemodelcontent=viewmodel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val resultResponse = this!!.arguments?.let { NewsContentFragmentArgs.fromBundle(it).rresultDetails }
        resultResponse?.let { viewmodel.setNewsDetails(it) }
    }


}
