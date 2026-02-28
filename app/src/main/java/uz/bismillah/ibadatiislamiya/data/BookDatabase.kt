package uz.bismillah.ibadatiislamiya.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.bismillah.ibadatiislamiya.data.dao.*
import uz.bismillah.ibadatiislamiya.data.model.*

@Database(entities = [Units::class, Topic::class, QuestionAnswer::class, Prefix::class], version = 2)
abstract class BookDatabase : RoomDatabase() {
    companion object {
        private lateinit var INSTANCE: BookDatabase

        fun getInstance(context: Context) : BookDatabase =
            Room.databaseBuilder(
                context,
                BookDatabase::class.java,
                "ibadati_islamiya.db3"
            )
                .createFromAsset("ibadati_islamiya.db3")
                .allowMainThreadQueries()
                .build()
    }

    abstract fun unitDao() : UnitDao
    abstract fun topicDao() : TopicDao
    abstract fun questionAnswerDao() : QuestionAnswerDao
    abstract fun prefixDao() : PrefixDao
}