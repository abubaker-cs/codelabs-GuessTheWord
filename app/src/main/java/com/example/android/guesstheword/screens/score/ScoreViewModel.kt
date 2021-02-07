package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {

    // The final score
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    // Equivalent to onCreateView() in the UI Controller
    init {
        // Log.i("ScoreViewModel", "I received $finalScore from GameFragment.kt and stored it in the score with the value of $score")
        _score.value = finalScore
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
    }

}