package bandi.nightowl.inject

import android.content.Context
import android.content.SharedPreferences
import bandi.nightowl.NightOwlApp
import bandi.nightowl.data.usecase.LocationUseCase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor



/**
 * Created by abauer on 05.11.17.
 */
@Module
class AppModule {
    @Provides
    fun provideContext(application: NightOwlApp): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideAuthSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences("SHARED_PREFS", Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit.Builder()
                .baseUrl("https://googleapi.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Singleton
    @Provides
    fun provideLocationUseCase(context: Context) : LocationUseCase {
        return LocationUseCase(context)
    }
}
