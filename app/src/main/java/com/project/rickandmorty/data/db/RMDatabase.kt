package com.project.rickandmorty.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.rickandmorty.data.db.entities.CharacterEntity
import com.project.rickandmorty.data.db.entities.EpisodeEntity
import com.project.rickandmorty.data.db.entities.LocationEntity

@Database(
    entities = [CharacterEntity::class, LocationEntity::class, EpisodeEntity::class], version = 1
)
abstract class RMDatabase : RoomDatabase() {

    abstract val dao: RMDao
}