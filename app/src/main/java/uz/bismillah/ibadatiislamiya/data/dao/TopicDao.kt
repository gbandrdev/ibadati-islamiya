package uz.bismillah.ibadatiislamiya.data.dao

import androidx.room.Dao
import androidx.room.Query
import uz.bismillah.ibadatiislamiya.data.model.Topic

@Dao
interface TopicDao {
    @Query("SELECT * FROM topics")
    fun getAllTopics() : List<Topic>

    @Query("SELECT * FROM topics WHERE unit_id = :unitId")
    fun getTopicsByUnit(unitId: Int) : List<Topic>

    @Query("SELECT name FROM topics WHERE id = :id")
    fun getTopicNameById(id: Int) : String

    @Query("SELECT has_prefix FROM topics WHERE id = :id")
    fun hasPrefix(id: Int) : Int

    @Query("SELECT * FROM topics WHERE unit_id = :unitId AND name LIKE :word")
    fun searchTopicByName(unitId: Int, word: String) : List<Topic>
}