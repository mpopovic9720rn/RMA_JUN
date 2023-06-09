package rs.raf.vezbe11.modules

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rs.raf.vezbe11.BuildConfig
import java.util.Date
import java.util.concurrent.TimeUnit

val coreModule= module {

    single {
        androidApplication().getSharedPreferences(
            androidApplication().packageName,
            Context.MODE_PRIVATE
        )
    }
    single { Room.databaseBuilder(androidContext(), rs.raf.vezbe11.data.database.Database::class.java, "FoodDb")
        .fallbackToDestructiveMigration()
        .build() }


    //ne moze biti single,zato sto moramo da imamo 2 razlicita retrofita.Jer gadjamo
    //2 apija
    single(named("mealDbRetrofit")) { createMealDbRetrofit(moshi = get(), httpClient = get()) }

    single(named("nutritionRetrofit")) { createNutritionRetrofit(moshi = get(), httpClient = get()) }

    single { createMoshi() }

    single { createOkHttpClient() }
}
    fun createMoshi(): Moshi {
        return Moshi.Builder()
            .add(Date::class.java, Rfc3339DateJsonAdapter())
            .build()
    }

    //2 retrofita,za 2 razlicite rute.
    fun createMealDbRetrofit(moshi: Moshi, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://themealdb.com/api/json/v1/1/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .client(httpClient)
            .build()
    }

    fun createNutritionRetrofit(moshi: Moshi, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
            .client(httpClient)
            .build()
    }


    fun createOkHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)
        httpClient.writeTimeout(60, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        return httpClient.build()
    }

    // Metoda koja kreira servis
    inline fun <reified T> create(retrofit: Retrofit): T  {
        return retrofit.create(T::class.java)
    }


