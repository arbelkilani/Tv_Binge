package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arbelkilani.binge.tv.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreSelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_genre_selection, container, false)
    }
}