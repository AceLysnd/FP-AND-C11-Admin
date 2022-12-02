package com.ace.c11flightadmin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ace.c11flightadmin.data.local.user.AccountDao
import com.ace.c11flightadmin.data.local.user.AccountEntity

@Database(entities = [AccountEntity::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val accountDao : AccountDao
}