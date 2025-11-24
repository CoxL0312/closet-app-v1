package com.example.closetapp_v2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import database.Item
import database.ItemDao
import kotlinx.coroutines.launch

class ClosetItemViewModel(private val itemDao: ItemDao) : ViewModel() {

    //list of items from database
    val items: LiveData<List<Item>> = liveData {
        emitSource(itemDao.getAllItems().asLiveData())
    }

    //livedata for current item
    private val _currentItem = MutableLiveData<Item?>(null)
    val currentItem: LiveData<Item?>
        get() = _currentItem

    fun setCurrentItem(itemId: Int) {
        viewModelScope.launch {
            _currentItem.value = itemDao.getItemById(itemId)
        }
    }

    fun addItem(item: Item) {
        viewModelScope.launch {
            itemDao.insertItem(item)
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch {
            itemDao.updateItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            itemDao.deleteItem(item)
        }
    }

    //optional, only inserts if database is empty
    fun populateInitialData() {
        viewModelScope.launch {
            if (itemDao.getItemCount() == 0) {
                val initialItems = listOf(
                    Item(name = "Hollister Tank", style = "Casual", material = "Cotton/Polyester", color = "Sapphire", comfort = 10, occasion = "Everyday"),
                    Item(name = "Popflex Tank", style = "Athleisure", material = "Nylon/Polyester", color = "White", comfort = 8, occasion = "Workout/Going Out"),
                    Item(name = "Popflex Shirt", style = "Athleisure", material = "Nylon/Polyester", color = "Lilac", comfort = 6, occasion = "Everyday"),
                    Item(name = "Fav jean shorts", style = "Casual", material = "Jean", color = "Light blue", comfort = 10, occasion = "Everyday"),
                    Item(name = "Skater Skirt", style = "Cute", material = "Nylon/Polyester", color = "Black", comfort = 10, occasion = "Work/Going out"),
                    Item(name = "Rungne Pants", style = "Athleisure", material = "Polyamide/Elastane", color = "Brown", comfort = 9, occasion = "Climbing")
                )
                initialItems.forEach { itemDao.insertItem(it) }
            }
        }

    }
}


/*
class ClosetItemViewModel : ViewModel() {


    private val _items = MutableLiveData<List<ClothingItem>>()
    val items: LiveData<List<ClothingItem>> get() = _items

    private val _currentIndex = MutableLiveData<Int>()
    private val _currentName = MutableLiveData<String>()
    private val _currentStyle = MutableLiveData<String>()
    private val _currentMaterial = MutableLiveData<String>()
    private val _currentColor = MutableLiveData<String>()
    private val _currentComfort = MutableLiveData<Int>()
    private val _currentOccasion = MutableLiveData<String>()

    val currentName: LiveData<String> get() = _currentName
    val currentStyle: LiveData<String> get() = _currentStyle
    val currentMaterial: LiveData<String> get() = _currentMaterial
    val currentColor: LiveData<String> get() = _currentColor
    val currentComfort: LiveData<Int> get() = _currentComfort
    val currentOccasion: LiveData<String> get() = _currentOccasion


    init {
        initializeItems()
    }

    fun setCurrentItem(index: Int) {
        val item = _items.value?.get(index) ?: return
        _currentIndex.value = index
        _currentName.value = item.name
        _currentStyle.value = item.style
        _currentMaterial.value = item.material
        _currentColor.value = item.color
        _currentComfort.value = item.comfort
        _currentOccasion.value = item.occasion
    }

    fun updateItem(
        name: String,
        style: String,
        material: String,
        color: String,
        comfort: Int,
        occasion: String
    ): Boolean {
        val index = _currentIndex.value ?: return false
        val updatedItem = ClothingItem(name, style, material, color, comfort, occasion)
        val newList = _items.value?.toMutableList() ?: return false
        if (index in newList.indices) {
            newList[index] = updatedItem
            _items.value = newList

            // Also update LiveData fields
            setCurrentItem(index)
            return true
        }
        return false
    }

    //initialize the items
    private fun initializeItems() {
        _items.value = mutableListOf(
            ClothingItem(
                "Hollister Tank",
                "Casual",
                "Cotten/Polyester",
                "Sapphire",
                10,
                "Everyday"
            ),
            ClothingItem(
                "Popflex Tank",
                "Athleisure",
                "Nylon/Polyester",
                "White",
                8,
                "Workout/Going Out"
            ),
            ClothingItem(
                "Popflex Shirt",
                "Athleisure",
                "Nylon/Polyester",
                "Lilac",
                6,
                "Everyday"
            ),
            ClothingItem(
                "Fav jean shorts",
                "Casual",
                "Jean",
                "Light blue",
                10,
                "Everyday"
            ),
            ClothingItem(
                "Skater Skirt",
                "Cute",
                "Nylon/Polyester",
                "Black",
                10,
                "Work/Going out"
            ),
            ClothingItem(
                "Rungne Pants",
                "Athleisure",
                "Polyamide/Elastane",
                "Brown",
                9,
                "Climbing"
            )
        )
    }

    //originally searched for item via index, but jic the item names aren't unique, changed
    //to use index val instead:::old version in notes jic i want to use it later
    /**
     *     fun editItem(name: String, updatedItem: ClothingItem): Boolean {
     *         val index = items.indexOfFirst { it.name == name }
     *         //find the item in the list, make it be the new item, and return, if we don't find it,r eturn false
     *         return if (index != -1) {
     *             items[index] = updatedItem
     *             true
     *         } else {
     *             false //out of bounds
     *         }
     *     }
     */
    fun editItem(index: Int, updatedItem: ClothingItem): Boolean {
        val currentList = _items.value?.toMutableList() ?: return false
        return if (index in currentList.indices) {
            currentList[index] = updatedItem
            _items.value = currentList
            true
        } else {
            false
        }
    }

    fun addItem(item: ClothingItem) {
        val currentList = _items.value?.toMutableList() ?: mutableListOf()
        currentList.add(item)
        _items.value = currentList
    }

    //return the list of items
    fun getItems(): List<ClothingItem> = _items.value ?: emptyList()
}
 */
