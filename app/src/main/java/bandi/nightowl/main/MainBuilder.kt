package bandi.nightowl.main

import android.arch.lifecycle.ViewModel
import bandi.nightowl.inject.ViewModelKey
import bandi.nightowl.ui.maps.PlacesMapFragment
import bandi.nightowl.ui.MainActivity
import bandi.nightowl.ui.maps.PlacesViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
internal abstract class MainBuilder {

    @ContributesAndroidInjector()
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector()
    internal abstract fun customMapFragment(): PlacesMapFragment

    @Binds
    @IntoMap
    @ViewModelKey(PlacesViewModel::class)
    abstract fun bindHomeActivityViewModel(viewModel: PlacesViewModel): ViewModel

}