package com.example.doodlekong.di

import android.app.Application
import android.content.Context
import com.example.doodlekong.data.remote.api.SetupApi
import com.example.doodlekong.data.remote.ws.CustomGsonMessageAdapter
import com.example.doodlekong.data.remote.ws.DrawingApi
import com.example.doodlekong.data.remote.ws.FlowStreamAdapter
import com.example.doodlekong.repository.DefaultSetupRepository
import com.example.doodlekong.repository.DefaultSetupRepository_Factory
import com.example.doodlekong.repository.SetupRepository
import com.example.doodlekong.util.Constants.HTTP_BASE_URL
import com.example.doodlekong.util.Constants.HTTP_BASE_URL_LOCALHOST
import com.example.doodlekong.util.Constants.RECONNECT_INTERVAL
import com.example.doodlekong.util.Constants.USE_LOCALHOST
import com.example.doodlekong.util.Constants.WS_BASE_URL
import com.example.doodlekong.util.Constants.WS_BASE_URL_LOCALHOST
import com.example.doodlekong.util.DispatcherProvider
import com.example.doodlekong.util.clientId
import com.example.doodlekong.util.dataStore
import com.google.gson.Gson
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.retry.BackoffStrategy
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(clientId: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val url = chain.request().url.newBuilder()
                    .addQueryParameter("client_id", clientId)
                    .build()
                val request = chain.request().newBuilder()
                    .url(url)
                    .build()

                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Singleton
    @Provides
    fun provideClientId(@ApplicationContext context: Context): String {
        return runBlocking {
            context.dataStore.clientId()
        }
    }

    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context) = context

    @Singleton
    @Provides
    fun provideGsonInstance(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return object : DispatcherProvider {
            override val main: CoroutineDispatcher
                get() = Dispatchers.Main
            override val io: CoroutineDispatcher
                get() = Dispatchers.IO
            override val default: CoroutineDispatcher
                get() = Dispatchers.Default
        }
    }
}