package com.example.android.guesstheword.screens.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel
 */
class GameViewModel : ViewModel() {

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
     * Method: Moves to the next word in the list
     */
    private fun nextWord() {
        // Changed !wordList.isEmpty() to wordList.isEmpty()
        if (wordList.isEmpty()) {
            onGameFinish()
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

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
    }
}