package com.example.android.guesstheword.screens.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 * ViewModel
 */
class GameViewModel : ViewModel() {

    companion object {

        // Time when the game is over
        private const val DONE = 0L

        // Countdown time interval
        private const val ONE_SECOND = 1000L

        // Total time for the game
        private const val COUNTDOWN_TIME = 60000L

    }

    // Countdown time <LiveData>
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // The string version of the current time
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    // For Timer
    private val timer: CountDownTimer

    /**
     * Dynamic DATA Type: MutableLiveData<>() is a Dynamic Type, i.e. it is not a constant variable
     * We need to chose DATA Type: Since it is a generic class, thus we need to specify DATA type, like <String> <Int>, etc
     *
     * Constant: LiveData<>
     * Variable: MutableLiveData<>()
     */

    // Event which triggers the end of the game
    private val _eventGameFinish = MutableLiveData<Boolean>()

    // Backing Property:
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    // The current word
    // var word = ""
    private val _word = MutableLiveData<String>()
    val word: LiveData<String>
        get() = _word

    // The current score
    // var score = 0
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // The list of words - the front of the list is the next word to guess
    private lateinit var wordList: MutableList<String>


    init {
        Log.i("GameViewModel", "GameViewModel created!")

        _word.value = ""
        _score.value = 0

        resetList()
        nextWord()


        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            // onTick()
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished / ONE_SECOND
            }

            // onFinish()
            override fun onFinish() {
                _currentTime.value = DONE
                onGameFinish()
            }

        }

        timer.start()
    }

    /**
     * Resets the list of words and randomizes the order
     */
    private fun resetList() {
        wordList = mutableListOf(
                "queen",
                "hospital",
                "basketball",
                "cat",
                "change",
                "snail",
                "soup",
                "calendar",
                "sad",
                "desk",
                "guitar",
                "home",
                "railway",
                "zebra",
                "jelly",
                "car",
                "crow",
                "trade",
                "bag",
                "roll",
                "bubble"
        )
        wordList.shuffle()
    }

    /**
     * Method: For the game completed Event
     */
    fun onGameFinish() {
        _eventGameFinish.value = true
    }

    /**
     * Reset the value, once the List of Words will be completed
     */
    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    /**
     * Method: Moves to the next word in the list
     */
    private fun nextWord() {
        // Changed !wordList.isEmpty() to wordList.isEmpty()
        if (wordList.isEmpty()) {
            // onGameFinish()
            resetList()
        } else {
            //Select and remove a word from the list
            _word.value = wordList.removeAt(0)
        }
        // updateWordText()
        // updateScoreText()
    }

    /** Methods for buttons presses **/

    fun onSkip() {
        // score--
        _score.value = (score.value)?.minus(1)
        nextWord()
    }

    fun onCorrect() {
        // score++
        _score.value = (score.value)?.plus(1)
        nextWord()
    }

    // This method is called, before the ViewModel is destroyed
    override fun onCleared() {
        super.onCleared()
        timer.cancel() // To avoid memory leaks
        Log.i("GameViewModel", "GameViewModel destroyed")
    }
}