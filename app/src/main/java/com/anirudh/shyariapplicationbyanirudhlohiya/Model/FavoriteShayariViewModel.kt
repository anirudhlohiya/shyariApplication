package com.anirudh.shyariapplicationbyanirudhlohiya

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anirudh.shyariapplicationbyanirudhlohiya.Model.ShayariModel

class FavoriteShayariViewModel : ViewModel() {

    private val _favoriteShayaris = MutableLiveData<List<ShayariModel>>()
    val favoriteShayaris: LiveData<List<ShayariModel>> = _favoriteShayaris

    fun fetchFavoriteShayaris(context: Context) {
        // Create an instance of the shared preferences
        val sharedPreferences = context.getSharedPreferences("FavoriteShayari", Context.MODE_PRIVATE)

        // Get all entries in the shared preferences
        val allEntries = sharedPreferences.all

        // Create a new list to hold the favorite shayaris
        val favoriteShayaris = ArrayList<ShayariModel>()

        // Loop through all entries in the shared preferences
        for ((key, value) in allEntries) {
            // If the value is true, then the shayari is a favorite
            if (value as Boolean) {
                // Create a new ShayariModel object and add it to the list
                favoriteShayaris.add(ShayariModel(key, key, true))
            }
        }

        // Update the _favoriteShayaris LiveData object with the list of favorite shayaris
        _favoriteShayaris.value = favoriteShayaris
    }
}