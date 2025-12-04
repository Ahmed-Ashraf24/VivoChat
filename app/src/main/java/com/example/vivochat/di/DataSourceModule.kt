package com.example.vivochat.di

import android.provider.MediaStore
import com.example.vivochat.data.dataSource.MediaDatasource.CloudinaryDataSource
import com.example.vivochat.data.dataSource.MediaDatasource.MediaDataSource
import com.example.vivochat.data.dataSource.RemoteDataSource
import com.example.vivochat.data.dataSource.firebase_remote_datasource.FirebaseRemoteDataSource
import com.example.vivochat.data.repository.MessageRepository
import com.example.vivochat.data.repository.UserRepository
import com.example.vivochat.domain.repository.IMessageRep
import com.example.vivochat.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindRemoteDataSource(dataSource: FirebaseRemoteDataSource): RemoteDataSource

    @Binds
    abstract fun bindMediaDataSource(mediaDataSource: CloudinaryDataSource): MediaDataSource

}