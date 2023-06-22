package rs.raf.vezbe11.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.vezbe11.data.models.*
import rs.raf.vezbe11.data.models.entities.AreaEntity
import rs.raf.vezbe11.data.models.entities.CategoryEntity
import rs.raf.vezbe11.data.models.entities.FoodByParameterEntity
import rs.raf.vezbe11.data.models.entities.FoodEntity
import rs.raf.vezbe11.data.models.entities.IngredientEntity

@Dao
abstract class FoodDao {

    //U bazi se cuva hrana samo onu koju korisnik izabere u svoj meni

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: FoodEntity): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<FoodEntity>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllFoodsByParameter(entities: List<FoodByParameterEntity>): Completable

    @Query("SELECT * FROM ingredients")
    abstract fun getAllIngredients(): Observable<List<IngredientEntity>>
    @Query("SELECT * FROM categories")
    abstract fun getAll(): Observable<List<CategoryEntity>>
    @Query("SELECT * FROM areas")
    abstract fun getAllAreas(): Observable<List<AreaEntity>>

    @Query("SELECT * FROM foods where id = :id")
    abstract fun getById(id: Long): Observable<FoodEntity>

    @Query ("DELETE FROM foods")
    abstract fun deleteAll()

    @Query ("DELETE FROM foodsByParameter")
    abstract fun deleteAllFoodsByParameter()
    @Query ("DELETE FROM areas")
    abstract fun deleteAllAreas()
    @Query ("DELETE FROM ingredients")
    abstract fun deleteAllIngredients()

    @Query("SELECT * FROM foods WHERE name LIKE :name || '%'")
    abstract fun getByName(name: String): List<FoodEntity>

    @Query("SELECT * FROM foodsByParameter ORDER BY id LIMIT :limit OFFSET :offset")
    abstract fun getFoodsByParameter(limit: Int, offset: Int): Observable<List<FoodByParameterEntity>>

    @Query("SELECT * FROM ingredients WHERE strIngredient LIKE :name || '%'")
    abstract fun getByIngredient(name: String): List<IngredientEntity>

    @Query("SELECT * FROM areas WHERE strArea LIKE :name || '%'")
    abstract fun getByArea(name: String): List<AreaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllCategories(entities: List<CategoryEntity>): Completable
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllAreas(entities: List<AreaEntity>): Completable
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAllIngredients(entities: List<IngredientEntity>): Completable

    @Transaction
    open fun deleteAndInsertAllCategories(entities: List<CategoryEntity>) {
        deleteAll()
        insertAllCategories(entities).blockingAwait() // po defaultu je async, ali mi zelimo da bude sync
    }

    @Transaction
    open fun deleteAndInsertFoodsByParameter(entities: List<FoodByParameterEntity>) {
        deleteAllFoodsByParameter()
        insertAllFoodsByParameter(entities).blockingAwait()
    }
    @Transaction
    open fun deleteAndInsertAreas(entities: List<AreaEntity>) {
        deleteAllAreas()
        insertAllAreas(entities).blockingAwait()
    }
    @Transaction
    open fun deleteAndInsertIngredients(entities: List<IngredientEntity>) {
        deleteAllIngredients()
        insertAllIngredients(entities).blockingAwait()
    }




}