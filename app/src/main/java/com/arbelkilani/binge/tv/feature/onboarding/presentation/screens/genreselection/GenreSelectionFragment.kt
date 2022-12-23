package com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.arbelkilani.binge.tv.common.base.BaseFragment
import com.arbelkilani.binge.tv.common.domain.model.GenreEntity
import com.arbelkilani.binge.tv.databinding.FragmentGenreSelectionBinding
import com.arbelkilani.binge.tv.feature.onboarding.OnBoardingContract
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection.adapter.GenreSelectionAdapter
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection.listener.GenreSelectionListener
import com.arbelkilani.binge.tv.feature.onboarding.presentation.screens.genreselection.model.GenreSelectionViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class GenreSelectionFragment :
    OnBoardingContract.GenreSelectionViewCapabilities,
    BaseFragment<FragmentGenreSelectionBinding>(),
    GenreSelectionListener {

    val viewModel: GenreSelectionViewModel by viewModels()
    private val genreSelectionAdapter: GenreSelectionAdapter by lazy { GenreSelectionAdapter(this) }

    override fun bindView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentGenreSelectionBinding {
        return FragmentGenreSelectionBinding.inflate(inflater, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.load()
    }

    override suspend fun initViewModelObservation() {
        super.initViewModelObservation()
        viewModel.viewState.collectLatest { viewState ->
            when (viewState) {
                is GenreSelectionViewState.Loaded -> populate(viewState.list)
                else -> Unit
            }
        }
    }

    override fun initViews() {
        super.initViews()
        binding.rvNetworks.apply {
            adapter = genreSelectionAdapter
        }
    }

    override fun populate(list: List<GenreEntity>) {
        genreSelectionAdapter.submitList(list)
    }

    override fun onDestroyView() {
        binding.rvNetworks.adapter = null
        super.onDestroyView()
    }

    override fun onGenreClicked(genre: GenreEntity) {
        viewModel.updateGenre(genre)
    }
}