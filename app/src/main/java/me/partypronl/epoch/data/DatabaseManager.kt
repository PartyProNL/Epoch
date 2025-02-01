package me.partypronl.epoch.data

import android.content.Context
import androidx.room.Room

class DatabaseManager(context: Context) {

    init {
        setupDatabase(context)
        instance = this
    }

    lateinit var db: EpochDatabase

    private fun setupDatabase(context: Context) {
        db = Room.databaseBuilder(
            context.applicationContext,
            EpochDatabase::class.java,
            "epoch"
        )
            .fallbackToDestructiveMigration() // Only do this for local development!!! Not for production. Then you'll need to create a Migration instead
            .build()
    }

    companion object {
        lateinit var instance: DatabaseManager
    }
}