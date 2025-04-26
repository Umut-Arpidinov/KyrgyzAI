package edu.alatoo.kyrgyzlearning.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.vertexai.vertexAI
import edu.alatoo.kyrgyzlearning.common.base.BaseViewModel
import edu.alatoo.kyrgyzlearning.data.local.db.entities.Word
import edu.alatoo.kyrgyzlearning.domain.interfaces.DictionaryRepository
import edu.alatoo.kyrgyzlearning.domain.usecases.GetLastFiveWordsUseCase
import edu.alatoo.kyrgyzlearning.domain.usecases.GetWordsFromDbUseCase
import timber.log.Timber
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class HomeViewModel(
    private val getLastFiveWordsUseCase: GetLastFiveWordsUseCase,
    private val getWordsFromDbUseCase: GetWordsFromDbUseCase
) : BaseViewModel() {

    private val _words: MutableLiveData<List<Word>> = MutableLiveData()
    val words: LiveData<List<Word>> get() = _words

    private val _wordCount: MutableLiveData<Int> = MutableLiveData()
    val wordCount: LiveData<Int> get() = _wordCount

    private val _wordOfDay: MutableLiveData<Word?> = MutableLiveData()
    val wordOfDay: LiveData<Word?> get() = _wordOfDay
    private var allWords: List<Word> = emptyList()

    private var lastWordOfDayTimestamp: Long = 0


    init {
       loadData()
    }

    private fun loadData() {
        getLastWordsFromDb()
        getAllWordsFromDb()
        getWordOfDay()
    }

    private fun getLastWordsFromDb() {
        dbRequest(
            source = { getLastFiveWordsUseCase.invoke() }
        ) {
            _words.value = it
        }
    }

    private fun getAllWordsFromDb() {
        dbRequest(
            source = { getWordsFromDbUseCase.invoke() }
        ) {
            allWords = it
           _wordCount.value = it.size
        }
    }

    private fun initMockData() {
        val mockWords = listOf(
            Word("салам"),
            Word("рахмат"),
            Word("китеп"),
            Word("мектеп"),
            Word("тамак"),
            Word("тамак"),
            Word("тамак"),
            Word("тамак"),
            Word("тамак"),
            Word("тамак"),
            Word("тамак"),
            Word("тамак"),
            Word("тамак"),
            Word("тамак"),

        )
        _words.value = mockWords // Set the mock data to the LiveData
        _wordCount.value = mockWords.size // Set the mock count
    }

     fun getWordOfDay() {
        val currentTime = Calendar.getInstance().timeInMillis
        val twentyFourHoursInMillis = TimeUnit.HOURS.toMillis(24)

        if (currentTime - lastWordOfDayTimestamp > twentyFourHoursInMillis || _wordOfDay.value == null) {
            if (allWords.isNotEmpty()) {
                val randomIndex = Random.nextInt(allWords.size)
                _wordOfDay.value = allWords[randomIndex]
                lastWordOfDayTimestamp = currentTime
            } else {
                _wordOfDay.value = null
            }
        }
    }
}