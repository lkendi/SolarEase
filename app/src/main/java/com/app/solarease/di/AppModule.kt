package com.app.solarease.di

import com.app.solarease.data.remote.FirestoreService
import com.app.solarease.data.remote.WeatherApiService
import com.app.solarease.data.repository.AuthRepositoryImpl
import com.app.solarease.data.repository.DeviceRepositoryImpl
import com.app.solarease.data.repository.FakeEnergyRepository
import com.app.solarease.data.repository.WeatherRepositoryImpl
import com.app.solarease.domain.repository.AuthRepository
import com.app.solarease.domain.repository.DeviceRepository
import com.app.solarease.domain.repository.EnergyRepository
import com.app.solarease.domain.repository.WeatherRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth, ): AuthRepository = AuthRepositoryImpl(auth)

    @Provides
    @Singleton
    fun provideEnergyRepository(): EnergyRepository {
        return FakeEnergyRepository()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideDeviceRepository(firestore: FirestoreService): DeviceRepository {
        return DeviceRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApiService): WeatherRepository {
        return WeatherRepositoryImpl(api)
    }
}
