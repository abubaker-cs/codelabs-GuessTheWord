package com.example.android.guesstheword.screens.score

import android.util.Log
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int) : ViewModel() {

    // The final score
    var score = finalScore

    // Equivalent to onCreateView() in the UI Controller
    init {
        Log.i("ScoreViewModel", "I received $finalScore from GameFragment.kt and stored it in the score with the value of $score")
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("GameViewModel", "GameViewModel destroyed")
    }

}