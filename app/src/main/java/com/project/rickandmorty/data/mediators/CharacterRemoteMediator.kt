package com.project.rickandmorty.data.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.project.rickandmorty.data.api.RMApi
import com.project.rickandmorty.data.db.RMDatabase
import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.domain.models.Character
import com.project.rickandmorty.domain.models.mappers.toCharacterEntity
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val rmDb: RMDatabase, private val rmApi: RMApi
) : RemoteMediator<Int, CharacterEntity>() {
    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, CharacterEntity>
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
            val characters = rmApi.getCharactersList(
                page = loadKey
            )

            rmDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    rmDb.dao.clearAllCharacters()
                }
                val characterEntitiesList = mutableListOf<Character>()
                characters.results.forEach {
                    characterEntitiesList.add(rmApi.getCharacterById(it.id))

                }
                val characterEntities = characterEntitiesList.map { it.toCharacterEntity() }
                rmDb.dao.upsertAllCharacters(characterEntities)
            }

            MediatorResult.Success(
                endOfPaginationReached = characters.results.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}