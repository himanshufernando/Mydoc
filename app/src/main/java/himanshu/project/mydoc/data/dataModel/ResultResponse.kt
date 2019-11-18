package himanshu.project.mydoc.data.dataModel

import android.os.Parcel
import android.os.Parcelable
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

    ) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        TODO("multimedia")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(resultId)
        parcel.writeString(created_date)
        parcel.writeString(item_type)
        parcel.writeString(byline)
        parcel.writeString(kicker)
        parcel.writeString(material_type_facet)
        parcel.writeString(published_date)
        parcel.writeString(section)
        parcel.writeString(short_url)
        parcel.writeString(subsection)
        parcel.writeString(title)
        parcel.writeString(newsContent)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResultResponse> {
        override fun createFromParcel(parcel: Parcel): ResultResponse {
            return ResultResponse(parcel)
        }

        override fun newArray(size: Int): Array<ResultResponse?> {
            return arrayOfNulls(size)
        }
    }
}