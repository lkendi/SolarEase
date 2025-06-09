package com.app.solarease.di

import android.content.Context
import com.app.solarease.data.cache.DeviceCache
import com.app.solarease.data.cache.WeatherCache
import com.app.solarease.data.remote.FirestoreService
import com.app.solarease.data.remote.IoTDataApiService
import com.app.solarease.data.remote.WeatherApiService
import com.app.solarease.data.repository.AuthRepositoryImpl
import com.app.solarease.data.repository.DeviceRepositoryImpl
import com.app.solarease.data.repository.EnergyRepositoryImpl
import com.app.solarease.data.repository.IoTDataRepositoryImpl
import com.app.solarease.data.repository.LocationRepositoryImpl
import com.app.solarease.data.repository.UserRepositoryImpl
import com.app.solarease.data.repository.WeatherRepositoryImpl
import com.app.solarease.domain.repository.AuthRepository
import com.app.solarease.domain.repository.DeviceRepository
import com.app.solarease.domain.repository.EnergyRepository
import com.app.solarease.domain.repository.IoTDataRepository
import com.app.solarease.domain.repository.LocationRepository
import com.app.solarease.domain.repository.UserRepository
import com.app.solarease.domain.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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
    @Named("deviceId")
    fun provideDeviceId(): String = "esp32Device01"

    @Provides
    @Singleton
    fun provideEnergyRepository(
        apiService: IoTDataApiService,
        @Named("deviceId") deviceId: String
    ): EnergyRepository {
        return EnergyRepositoryImpl(apiService, deviceId)
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideDeviceRepository(firestore: FirestoreService): DeviceRepository {
        return DeviceRepositoryImpl(firestore = firestore, cache = DeviceCache()
        )
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApiService): WeatherRepository {
        return WeatherRepositoryImpl(api = api, cache = WeatherCache())
    }

    @Provides
    @Singleton
    fun provideFusedLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideLocationRepository(fusedLocationClient: FusedLocationProviderClient,  @ApplicationContext context: Context): LocationRepository {
        return LocationRepositoryImpl(fusedLocationClient, context)
    }

    @Provides
    @Singleton
    fun provideUserRepository(firestore: FirestoreService): UserRepository {
        return UserRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideIoTDataRepository(ioTDataApiService: IoTDataApiService): IoTDataRepository {
        return IoTDataRepositoryImpl(ioTDataApiService)
    }
}
