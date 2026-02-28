package uz.bismillah.ibadatiislamiya.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.bismillah.ibadatiislamiya.data.BaseModelQAPrefix

@Entity(tableName = "prefixes")
data class Prefix(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "topic_id")
    val topicId: Int,

    @ColumnInfo(name = "unit_id")
    val unitId: Int,

    @ColumnInfo(name = "is_favorite")
    var isFavorite: Int
) : BaseModelQAPrefix(BaseModelQAPrefix.PREFIX)
