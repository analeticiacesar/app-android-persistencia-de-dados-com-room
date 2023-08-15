package br.com.alura.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.orgs.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product")
    fun searchAll() : List<Product>

    @Insert
    fun save(vararg product: Product)

    @Delete
    fun delete(product: Product)

}