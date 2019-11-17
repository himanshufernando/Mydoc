package himanshu.project.mydoc.data.dataModel
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters


@Entity(tableName = "dataDB")
@TypeConverters(Converters::class)
data class Data(
    @PrimaryKey val dataId: Long,
    var copyright: String,
    var last_updated: String,
    var num_results: Int,
    var section: String,
    var status: String,
    var results: List<ResultResponse>
){

}