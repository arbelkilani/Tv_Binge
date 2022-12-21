package com.arbelkilani.binge.tv.presentation.di

import androidx.fragment.app.Fragment
import com.arbelkilani.binge.tv.presentation.walkthrough.presentation.WalkthroughFragment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class FragmentModule {

    @Binds
    abstract fun bindFragment(fragment: WalkthroughFragment): Fragment
}