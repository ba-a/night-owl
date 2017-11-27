package bandi.nightowl.inject

import bandi.nightowl.NightOwlApp
import bandi.nightowl.main.MainBuilder
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by abauer on 05.11.17.
 */
@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        ViewModelModule::class,
        MainBuilder::class,
        AppModule::class))
interface AppComponent : AndroidInjector<NightOwlApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<NightOwlApp>()



}