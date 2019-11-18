package himanshu.project.mydoc.views.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import himanshu.project.mydoc.R
import himanshu.project.mydoc.data.dataModel.Multimedia

object CustomBindingAdapter {

    @BindingAdapter("url")
    @JvmStatic
    fun url(view: ImageView, url: List<Multimedia>) {
        Glide.with(view.context)
            .load(url.last().url!!)
            .placeholder(R.drawable.mydoclogo)
            .centerCrop()
            .into(view)
    }

    @BindingAdapter("pubDate")
    @JvmStatic
    fun setDate(view : TextView, date: String) {
        view.text = date.substring(1,10).toString()
    }
}