package rs.raf.vezbe11.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import rs.raf.vezbe11.data.database.converters.DateConverter
import rs.raf.vezbe11.data.database.converters.MapConverter
import rs.raf.vezbe11.data.database.converters.StringListConverter
import rs.raf.vezbe11.data.datasources.local.FoodDao
import rs.raf.vezbe11.data.datasources.local.NutritionDao
import rs.raf.vezbe11.data.models.entities.*

@Database(
    entities = [CategoryEntity::class, FoodByParameterEntity::class, FoodEntity::class, IngredientEntity::class, AreaEntity::class, NutritionEntity::class],
    version = 5,
    exportSchema = false)
@TypeConverters(StringListConverter::class, DateConverter::class, MapConverter::class)//Imamo liste i date,koji nisu primitivni tipovi,pa mu zadajemo
//kako da ih on prevede na primitivne tipove
abstract class Database : RoomDatabase(){

//    // Getteri za sve DAO-e moraju biti navedeni ovde
    abstract fun getFoodDao(): FoodDao
    abstract fun getNutritionDao(): NutritionDao
}