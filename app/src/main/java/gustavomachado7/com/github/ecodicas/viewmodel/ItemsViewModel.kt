package gustavomachado7.com.github.ecodicas.viewmodel

import android.app.Application
import android.content.ClipData.Item
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import gustavomachado7.com.github.ecodicas.data.ItemDao
import gustavomachado7.com.github.ecodicas.data.ItemDatabase
import gustavomachado7.com.github.ecodicas.model.ItemModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemsViewModel(application: Application) : AndroidViewModel(application) {


    private val itemDao: ItemDao


    val itemsLiveData: LiveData<List<ItemModel>>

    init {


        val database = Room.databaseBuilder(
            getApplication(),
            ItemDatabase::class.java,
            "items_database"
        ).build()


        itemDao = database.itemDao()


        itemsLiveData = itemDao.getAll()
    }

    fun addItem(newItemModel: ItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItem =
                ItemModel(titulo = newItemModel.titulo, descricao = newItemModel.descricao)

            itemDao.insert(newItem)
        }
    }

    fun removeItem(item: ItemModel) {

        viewModelScope.launch(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }


}