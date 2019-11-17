package himanshu.project.mydoc.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import himanshu.project.mydoc.data.dataModel.Data

@Dao
interface DataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: Data)
    @Query("SELECT * FROM dataDB")
    suspend fun getNewsFromDB(): Data


}