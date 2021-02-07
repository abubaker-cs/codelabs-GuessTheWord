/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.guesstheword.screens.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.GameFragmentBinding

/**
 * UI Controller - Deals with the game_fragment.xml file
 */
class GameFragment : Fragment() {

    // We need to CONNECT our UI-Controller (this file) with the ViewModel (GameViewModel.kt)
    private lateinit var viewModel: GameViewModel

    private lateinit var binding: GameFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        // Log.i("GameFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.lifecycleOwner = viewLifecycleOwner

        // Binding with the XML File
        // Set the viewModel for data binding - this allows the bound layout access to all the
        // data in teh ViewModel
        binding.gameViewModel = viewModel

        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer<Boolean> { hasFinished ->
            if (hasFinished) gameFinished()
        })

        // onClick() - Button: Correct
        // binding.correctButton.setOnClickListener { onCorrect() }

        // onClick() - Button: Skip
        // binding.skipButton.setOnClickListener { onSkip() }

        // onEndGame() -
        // binding.endGameButton.setOnClickListener { onEndGame() }

        /**
         * Set up the observation relationship for the score and word LiveDatas
         * Get the LiveData from your view model and call the observe method.
         * Lesson 5 - App Architecture (UI) : Slide #16 - Add LiveData to GameViewModel
         */
        // Observer - scoreText
        viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
            // binding.scoreText.text = viewModel.score.value.toString()
            binding.scoreText.text = newScore.toString()
        })

        // Observer -
        // viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
            // binding.wordText.text = viewModel.word.value
        //    binding.wordText.text = newWord
        // })

        // updateScoreText()
        // updateWordText()
        return binding.root

    }


    /** Methods for buttons presses **/

    // private fun onSkip() {
    // score--
    // nextWord()
    // viewModel.onSkip()
    // updateScoreText()
    // updateWordText()
    // }

    // private fun onCorrect() {
    // score++
    // nextWord()
    // viewModel.onCorrect()
    // updateScoreText()
    // updateWordText()
    // }

    /** Methods for updating the UI **/

    // private fun updateWordText() {
    // binding.wordText.text = viewModel.word.value
    // }

    // private fun updateScoreText() {
    // binding.scoreText.text = viewModel.score.value.toString()
    // }

    // End Game - Call
    // private fun onEndGame() {
    //     gameFinished()
    // }

    // End Game - Content
    private fun gameFinished() {
        Toast.makeText(activity, "Game has just finished", Toast.LENGTH_SHORT).show()

        // Define the target, that will receive our DATA
        val action = GameFragmentDirections.actionGameToScore()

        // Will will fetch data from the "score" variable stored inside the GameViewMode.kt file
        // And forward it to our targeted Fragment file, that will be ScoreFragment.kt
        action.score = viewModel.score.value ?: 0

        // Here we are asking the app to navigation to the targeted Fragment WITH our "score" data
        NavHostFragment.findNavController(this).navigate(action)

        viewModel.onGameFinishComplete()
    }
}
