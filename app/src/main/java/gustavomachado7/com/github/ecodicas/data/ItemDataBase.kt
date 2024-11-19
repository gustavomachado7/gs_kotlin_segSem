package gustavomachado7.com.github.ecodicas.data

import androidx.room.Database
import androidx.room.RoomDatabase
import gustavomachado7.com.github.ecodicas.model.ItemModel

@Database(entities = [ItemModel::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao
}