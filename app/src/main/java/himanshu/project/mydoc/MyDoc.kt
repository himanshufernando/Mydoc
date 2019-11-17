package himanshu.project.mydoc

import android.app.Application
import android.content.Context

class MyDoc: Application() {
    init {
        instance = this
    }
    companion object {
        private var instance: MyDoc? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        val context: Context = MyDoc.applicationContext()

    }

}