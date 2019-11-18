package himanshu.project.mydoc.data.dataModel


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "multimedia")
data class Multimedia(
    @PrimaryKey  val multimediaId: Long?,
    var caption: String?,
    var type: String?,
    var url: String?
)