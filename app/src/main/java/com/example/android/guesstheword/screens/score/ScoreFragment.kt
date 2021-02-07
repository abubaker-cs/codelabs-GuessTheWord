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

package com.example.android.guesstheword.screens.score

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.guesstheword.R
import com.example.android.guesstheword.databinding.ScoreFragmentBinding
import com.example.android.guesstheword.screens.game.GameViewModel

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {

    // We need to CONNECT our UI-Controller (this file) with the ViewModel (GameViewModel.kt)
    private lateinit var viewModel: ScoreViewModel
    private lateinit var viewModelFactory: ScoreViewModelFactory

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.score_fragment,
                container,
                false
        )


        viewModelFactory = ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(requireArguments()).score) // arguments!!
        viewModel = ViewModelProvider(this, viewModelFactory).get(ScoreViewModel::class.java)
        // binding.scoreText.text = viewModel.score.toString()

        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates.
        binding.lifecycleOwner = viewLifecycleOwner

        // Binding with the XML File
        // Set the viewModel for data binding - this allows the bound layout access to all the
        // data in teh ViewModel
        binding.scoreViewModel = viewModel

        // Observer - Add observer for score
        // viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
        //     binding.scoreText.text = newScore.toString()
        // })

        // Click Listener - Play Again Button
        // binding.playAgainButton.setOnClickListener {
        // Sets the boolean value of Play Again to TRUE
        //    viewModel.onPlayAgain()
        //}

        // Observer - EventPlayAgain
        viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain) {

                // Restart the game
                findNavController().navigate(ScoreFragmentDirections.actionRestart())

                // Sets the boolean value of Play Again to FALSE
                viewModel.onPlayAgainComplete()
            }
        })

        return binding.root
    }
}
