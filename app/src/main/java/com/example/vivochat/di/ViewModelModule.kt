package com.example.vivochat.di

import com.example.vivochat.data.repository.CloudinaryRepository
import com.example.vivochat.data.repository.MessageRepository
import com.example.vivochat.data.repository.UserRepository
import com.example.vivochat.domain.repository.IMediaRepository
import com.example.vivochat.domain.repository.IMessageRep
import com.example.vivochat.domain.repository.IUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class ViewModelModule {
    @Binds
    abstract fun bindMessageRepo(messageRepo: MessageRepository): IMessageRep
    @Binds
    abstract fun bindMediaRepo(mediaRepository: CloudinaryRepository): IMediaRepository

    @Binds
    abstract fun bindUserRepo(userRepo: UserRepository): IUserRepository

}