package com.project.rickandmorty.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.project.rickandmorty.data.api.RMApi
import com.project.rickandmorty.data.db.RMDatabase
import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.data.db.entities.LocationEntity
import com.project.rickandmorty.data.mediators.CharacterRemoteMediator
import com.project.rickandmorty.data.mediators.EpisodeRemoteMediator
import com.project.rickandmorty.data.mediators.LocationRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
@OptIn(ExperimentalPagingApi::class)
object AppModule {
    @Provides
    @Singleton
    fun provideCharacterPager(rmDb: RMDatabase, rmApi: RMApi): Pager<Int, CharacterEntity> {
        return Pager(config = PagingConfig(pageSize = 20), remoteMediator = CharacterRemoteMediator(
            rmDb = rmDb, rmApi = rmApi
        ), pagingSourceFactory = {
            rmDb.dao.characterPagingSource()
        })
    }


    @Provides
    @Singleton
    fun provideEpisodePager(rmDb: RMDatabase, rmApi: RMApi): Pager<Int, EpisodeEntity> {
        return Pager(config = PagingConfig(pageSize = 20), remoteMediator = EpisodeRemoteMediator(
            rmDb = rmDb, rmApi = rmApi
        ), pagingSourceFactory = {
            rmDb.dao.episodePagingSource()
        })
    }


    @Provides
    @Singleton
    fun provideLocationPager(rmDb: RMDatabase, rmApi: RMApi): Pager<Int, LocationEntity> {
        return Pager(config = PagingConfig(pageSize = 20), remoteMediator = LocationRemoteMediator(
            rmDb = rmDb, rmApi = rmApi
        ), pagingSourceFactory = {
            rmDb.dao.locationPagingSource()
        })
    }

    @Provides
    @Singleton
    fun provideRMApi(): RMApi {
        return Retrofit.Builder().baseUrl(RMApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(RMApi::class.java)

    }

    @Provides
    @Singleton
    fun provideRMDatabase(@ApplicationContext context: Context): RMDatabase {
        return Room.databaseBuilder(
            context, RMDatabase::class.java, "rm.db"
        ).build()
    }


}