package himanshu.project.mydoc.data.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import himanshu.project.mydoc.data.dataModel.Converters
import himanshu.project.mydoc.data.dataModel.Data
import himanshu.project.mydoc.data.dataModel.Multimedia
import himanshu.project.mydoc.data.dataModel.ResultResponse
import himanshu.project.mydoc.utilities.DATABASE_NAME


@Database(entities = [Multimedia::class, Data::class, ResultResponse::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase()  {
    abstract fun dataDao(): DataDao
    companion object {
        @Volatile private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                    }
                })
                .build()
        }
    }


}