package br.com.alura.orgs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alura.orgs.database.dao.ProductDao
import br.com.alura.orgs.model.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
}