package rs.raf.vezbe11.data.models.entities

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "savedFoods")
class SavedFoodEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val strInstructions: String,
    //val date:Long,
    var dayOfMonth: Int,
    var month: Int,
    var year: Int,
    val calories: Double,
    val strCategory: String,
    val strMealType: String,
    val strMealThumb: String,
    val strYoutube: String,
    val strIngredient1: String?,
    val strIngredient2: String?,
    val strIngredient3: String?,
    val strIngredient4: String?,
    val strIngredient5: String?,
    val strMeasure1: String?,
    val strMeasure2: String?,
    val strMeasure3: String?,
    val strMeasure4: String?,
    val strMeasure5: String?,
    var customImagePath: String?


)

