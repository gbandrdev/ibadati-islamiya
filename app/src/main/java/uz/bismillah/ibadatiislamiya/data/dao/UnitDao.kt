package uz.bismillah.ibadatiislamiya.data.dao

import androidx.room.Dao
import androidx.room.Query
import uz.bismillah.ibadatiislamiya.data.model.Units

@Dao
interface UnitDao {
    @Query("SELECT * FROM units")
    fun getAllUnits(): List<Units>
}