package com.example.solarease.di

import com.example.solarease.data.remote.WeatherApiService
import com.example.solarease.data.repository.FakeEnergyRepository
import com.example.solarease.data.repository.WeatherRepositoryImpl
import com.example.solarease.domain.repository.EnergyRepository
import com.example.solarease.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideEnergyRepository(): EnergyRepository = FakeEnergyRepository()

    @Provides
    @Singleton
    fun provideWeatherRepository(apiService: WeatherApiService): WeatherRepository =
        WeatherRepositoryImpl(apiService)
}
// TODO: Switch to real implementation when ready
