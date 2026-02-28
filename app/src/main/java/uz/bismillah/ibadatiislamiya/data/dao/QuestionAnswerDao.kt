package uz.bismillah.ibadatiislamiya.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import uz.bismillah.ibadatiislamiya.data.model.QuestionAnswer

@Dao
interface QuestionAnswerDao {
    @Query("SELECT * FROM question_answer")
    fun getAllQuestions() : List<QuestionAnswer>

    @Query("SELECT * FROM question_answer WHERE topic_id IN (:topicIds)")
    fun getAllQuestionsByTopics(topicIds: IntArray) : List<QuestionAnswer>

    @Query("SELECT * FROM question_answer WHERE id = :id")
    fun getQuestionById(id: Int) : QuestionAnswer

    @Query("SELECT * FROM question_answer WHERE topic_id = (:topicId)")
    fun getQuestionsByTopic(topicId: Int) : List<QuestionAnswer>

    @Query("SELECT * FROM question_answer WHERE question = :question")
    fun getQuestionByQuestion(question: String) : QuestionAnswer

    @Query("SELECT * FROM question_answer WHERE is_favorite = 1")
    fun getAllFavoriteQuestions() : List<QuestionAnswer>

    @Query("SELECT question FROM question_answer WHERE question LIKE :word")
    fun searchQuestions(word: String) : List<String>

    @Update
    fun updateQuestion(questionAnswer: QuestionAnswer)
}