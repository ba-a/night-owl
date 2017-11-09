package bandi.nightowl.inject

import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module


@Module
internal abstract class ViewModelModule {
    // Binds is a replacement for provides. A bit more efficient than Provides
    @Binds
    internal abstract fun bindViewModelFactory(factory: NightOwlViewModelFactory): ViewModelProvider.Factory

}