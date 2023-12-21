package com.project.rickandmorty.data.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.project.rickandmorty.data.api.RMApi
import com.project.rickandmorty.data.db.RMDatabase
import com.project.rickandmorty.data.db.entities.LocationEntity
import com.project.rickandmorty.domain.models.mappers.toLocationEntity
import com.project.rickandmorty.domain.models.Location
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class LocationRemoteMediator(
    private val rmDb: RMDatabase, private val rmApi: RMApi
) : RemoteMediator<Int, LocationEntity>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, LocationEntity>
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
            val locations = rmApi.getLocationsList(
                page = loadKey
            )

            rmDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    rmDb.dao.clearAllLocations()
                }
                val locationEntitiesList = mutableListOf<Location>()
                locations.results.forEach {
                    locationEntitiesList.add(rmApi.getLocationById(it.id))
                }
                val locationEntities = locationEntitiesList.map { it.toLocationEntity() }
                rmDb.dao.upsertAllLocations(locationEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = locations.results.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}