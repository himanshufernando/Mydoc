package himanshu.project.mydoc.data.dataModel

import androidx.room.*
import com.google.gson.annotations.SerializedName


@Entity(tableName = "resultDB")
@TypeConverters(Converters::class)
class ResultResponse(
    @PrimaryKey val resultId: Long,
    var created_date: String,
    var item_type: String,
    var byline: String,
    var kicker: String,
    var material_type_facet: String,
    var published_date: String,
    var section: String,
    var short_url: String,
    var subsection: String,
    var title: String,
    @SerializedName("abstract")
    var newsContent: String,
    var url: String,
    var multimedia: List<Multimedia>

    )