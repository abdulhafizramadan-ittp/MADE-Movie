package com.ahr.movie.core_data.di

import android.content.Context
import com.ahr.movie.core_data.remote.services.MovieService
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    @Singleton
    fun provideCertificatePinner(): CertificatePinner {
        val hostname = "api.themoviedb.org"
        return CertificatePinner.Builder()
            .add(hostname, "sha256/oD/WAoRPvbez1Y2dfYfuo4yujAcYHXdv1Ivb2v2MOKk=")
            .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .build()
    }

    @Provides
    @Singleton
    fun provideChuckerCollector(
        @ApplicationContext context: Context
    ): ChuckerCollector =
        ChuckerCollector(
            context = context,
            showNotification = true,
            RetentionManager.Period.ONE_HOUR
        )

    @Provides
    @Singleton
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context,
        chuckerCollector: ChuckerCollector,
    ): ChuckerInterceptor =
        ChuckerInterceptor.Builder(context)
            .collector(chuckerCollector)
            .maxContentLength(250_000L)
            .redactHeaders("Auth-Token", "Bearer")
            .alwaysReadResponseBody(true)
            .build()

    @Provides
    @Singleton
    fun provideChucker(
        chuckerInterceptor: ChuckerInterceptor,
        certificatePinner: CertificatePinner
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(chuckerInterceptor)
            .certificatePinner(certificatePinner)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        chuckerInterceptor: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(chuckerInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideMovieService(
        retrofit: Retrofit
    ): MovieService = retrofit.create(MovieService::class.java)

}