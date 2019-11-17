package himanshu.project.mydoc.data.dataModel

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {

    @TypeConverter
    fun resultResponseListToJson(value: List<ResultResponse>?): String {
        return Gson().toJson(value)
    }
    @TypeConverter
    fun jsonToresultResponseList(value: String): List<ResultResponse>? {
        val objects = Gson().fromJson(value, Array<ResultResponse>::class.java) as Array<ResultResponse>
        val list = objects.toList()
        return list
    }


    @TypeConverter
    fun multimediaListToJson(value: List<Multimedia>?): String {
        return Gson().toJson(value)
    }
    @TypeConverter
    fun jsonTomultimediaList(value: String): List<Multimedia>? {
        val objects = Gson().fromJson(value, Array<Multimedia>::class.java) as Array<Multimedia>
        val list = objects.toList()
        return list
    }



}