package com.websarva.wings.android.gamecheatpinning.di

import com.websarva.wings.android.gamecheatpinning.repository.EncodeResultRepository
import com.websarva.wings.android.gamecheatpinning.repository.EncodeResultRepositoryClient
import com.websarva.wings.android.gamecheatpinning.repository.HttpConnectRepository
import com.websarva.wings.android.gamecheatpinning.repository.HttpConnectRepositoryClient
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Module {
    @Singleton
    @Binds
    abstract fun bindHttpConnectRepository(httpConnectRepositoryClient: HttpConnectRepositoryClient): HttpConnectRepository

    @Singleton
    @Binds
    abstract fun bindEncodeResultRepository(encodeResultRepositoryClient: EncodeResultRepositoryClient): EncodeResultRepository
}