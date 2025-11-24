package database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.closetapp_v2.ClosetItemViewModel

class ClosetItemViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClosetItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ClosetItemViewModel(itemDao) as T
        }
        //look at me im the exception to everyone else
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
