package uz.bismillah.ibadatiislamiya.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import uz.bismillah.ibadatiislamiya.data.model.Prefix

@Dao
interface PrefixDao {
    @Query("SELECT * FROM prefixes")
    fun getAllPrefixes() : List<Prefix>

    @Query("SELECT * FROM prefixes WHERE topic_id = (:topicId)")
    fun getPrefixByTopic(topicId: Int) : Prefix

    @Query("SELECT * FROM prefixes WHERE id = :id")
    fun getPrefixById(id: Int) : Prefix

    @Query("SELECT * FROM prefixes WHERE is_favorite = 1")
    fun getAllFavoritePrefixes() : List<Prefix>

    @Update
    fun updatePrefix(prefix: Prefix)
}