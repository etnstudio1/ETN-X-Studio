package com.etnstudio.user.di

import android.content.Context
import androidx.room.Room
import com.etnstudio.user.data.cache.AppDatabase
import com.etnstudio.user.data.cache.ContentDao
import com.etnstudio.user.data.cache.HistoryDao
import com.etnstudio.user.data.network.ContentApi
import com.etnstudio.user.data.network.RegistryApi
import com.etnstudio.user.data.repository.ContentRepositoryImpl
import com.etnstudio.user.data.repository.RegistryRepositoryImpl
import com.etnstudio.user.domain.repository.ContentRepository
import com.etnstudio.user.domain.repository.RegistryRepository
import com.etnstudio.user.domain.usecase.LockVerifier
import com.etnstudio.user.domain.usecase.SearchUseCase
import com.etnstudio.user.utils.JsonNormalizer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .connectTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    fun provideRegistryApi(client: OkHttpClient): RegistryApi =
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()

    @Provides
    @Singleton
    fun provideContentApi(client: OkHttpClient): ContentApi =
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "etn_user.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideContentDao(db: AppDatabase): ContentDao = db.contentDao()

    @Provides
    fun provideHistoryDao(db: AppDatabase): HistoryDao = db.historyDao()

    @Provides
    @Singleton
    fun provideRegistryRepository(api: RegistryApi): RegistryRepository =
        RegistryRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideContentRepository(
        api: ContentApi,
        dao: ContentDao,
        normalizer: JsonNormalizer
    ): ContentRepository = ContentRepositoryImpl(api, dao, normalizer)

    @Provides
    @Singleton
    fun provideLockVerifier() = LockVerifier()

    @Provides
    @Singleton
    fun provideSearchUseCase(repo: ContentRepository) = SearchUseCase(repo)

    @Provides
    @Singleton
    fun provideJsonNormalizer() = JsonNormalizer(Json { ignoreUnknownKeys = true })
}
