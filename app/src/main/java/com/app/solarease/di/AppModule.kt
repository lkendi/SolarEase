package com.app.solarease.di

import com.app.solarease.data.repository.AuthRepositoryImpl
import com.app.solarease.data.repository.FakeEnergyRepository
import com.app.solarease.domain.repository.AuthRepository
import com.app.solarease.domain.repository.EnergyRepository
import com.google.firebase.auth.FirebaseAuth
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
}
