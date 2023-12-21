package com.project.rickandmorty.data.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.project.rickandmorty.data.api.RMApi
import com.project.rickandmorty.data.db.RMDatabase
import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.domain.models.Episode
import com.project.rickandmorty.domain.models.mappers.toEpisodeEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class EpisodeRemoteMediator(
    private val rmDb: RMDatabase, private val rmApi: RMApi
) : RemoteMediator<Int, EpisodeEntity>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, EpisodeEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1

                    }
                }
            }
            delay(3000L)
            val episodes = rmApi.getEpisodesList(
                page = loadKey
            )

            rmDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    rmDb.dao.clearAllEpisodes()
                }
                val episodeEntitiesList = mutableListOf<Episode>()
                episodes.results.forEach {
                    episodeEntitiesList.add(rmApi.getEpisodeById(it.id))
                }
                val episodeEntities = episodeEntitiesList.map { it.toEpisodeEntity() }
                rmDb.dao.upsertAllEpisodes(episodeEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = episodes.results.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}