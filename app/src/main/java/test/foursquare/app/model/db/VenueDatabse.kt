package test.foursquare.app.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import test.foursquare.app.model.db.entities.CategoryEntity
import test.foursquare.app.model.db.entities.VenueDetailEntity
import test.foursquare.app.model.db.entities.VenueEntitiy

@Database(
    entities = [VenueEntitiy::class, VenueDetailEntity::class, CategoryEntity::class],
    version = 1
)
abstract class VenueDatabse : RoomDatabase() {

    abstract fun getVenueDao(): VenueDao
    abstract fun getVenueDetailDao(): VenueDetailDao
    abstract fun getCategoryDao(): CategoryDao

    companion object {
        @Volatile
        private var instance: VenueDatabse? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                VenueDatabse::class.java,
                "Markopolo,db"
            ).build()
    }
}