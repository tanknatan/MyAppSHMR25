package com.natan.shamilov.shmr25.app.di

import com.natan.shamilov.shmr25.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Модуль Dagger 2 для настройки сетевых зависимостей.
 * Ответственность: Предоставление и конфигурация всех необходимых компонентов
 * для работы с сетью, включая HTTP-клиент, сериализацию JSON и API-интерфейсы.
 *
 * Модуль устанавливается в SingletonComponent, обеспечивая единые экземпляры
 * сетевых компонентов на протяжении всего жизненного цикла приложения.
 */
@Module
class NetworkModule {

    /**
     * Предоставляет экземпляр Moshi для сериализации/десериализации JSON.
     * Настроен для работы с Kotlin-классами через KotlinJsonAdapterFactory.
     *
     * @return Настроенный экземпляр Moshi
     */
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * Предоставляет интерцептор для добавления токена авторизации.
     * Добавляет Bearer-токен из BuildConfig в заголовок каждого запроса.
     *
     * @return Интерцептор для авторизации
     */
    @Provides
    @Singleton
    fun provideAuthInterceptor(): Interceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${BuildConfig.API_TOKEN}")
            .build()
        chain.proceed(newRequest)
    }

    /**
     * Предоставляет настроенный клиент OkHttp.
     * Включает интерцептор авторизации и логирования для отладки.
     *
     * @param authInterceptor Интерцептор для авторизации
     * @return Настроенный клиент OkHttp
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: Interceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Предоставляет настроенный экземпляр Retrofit.
     * Конфигурирует Retrofit с базовым URL, клиентом OkHttp и конвертером Moshi.
     *
     * @param okHttpClient HTTP-клиент для выполнения запросов
     * @param moshi Конвертер JSON
     * @return Настроенный экземпляр Retrofit
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    companion object {
        /** Базовый URL для API */
        const val BASE_URL = "https://shmr-finance.ru/api/v1/"
    }
}
