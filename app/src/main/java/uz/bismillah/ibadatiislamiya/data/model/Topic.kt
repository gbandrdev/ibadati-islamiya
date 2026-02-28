package uz.bismillah.ibadatiislamiya.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topics")
data class Topic(
    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "unit_id")
    val unitId: Int,

    @ColumnInfo(name = "has_prefix")
    val hasPrefix: Int
)
