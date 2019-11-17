package himanshu.project.mydoc.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import himanshu.project.mydoc.data.dataModel.ResultResponse
import himanshu.project.mydoc.databinding.ListNewsBinding

class NewsAdapters: ListAdapter<ResultResponse, RecyclerView.ViewHolder>(ToolsDiffCallback()) {

    lateinit var mClickListener: ClickListener
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tools = getItem(position)
        (holder as NewsViewHolder).bind(tools)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NewsViewHolder(ListNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false),mClickListener)
    }
    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }
    interface ClickListener {
        fun onClick(tool: ResultResponse, aView: View)
    }
    class NewsViewHolder(private val binding: ListNewsBinding ,var mClickListener: ClickListener ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { binding.item?.let { tools -> mClickListener.onClick(tools,it) } }
        }
        fun bind(tool: ResultResponse) {
            binding.apply { item = tool
                executePendingBindings()
            }

        }
    }
}

private class ToolsDiffCallback : DiffUtil.ItemCallback<ResultResponse>() {
    override fun areItemsTheSame(oldItem: ResultResponse, newItem: ResultResponse): Boolean {
        return oldItem.resultId == newItem.resultId
    }
    override fun areContentsTheSame(oldItem: ResultResponse, newItem: ResultResponse): Boolean {
        return oldItem == newItem
    }
}