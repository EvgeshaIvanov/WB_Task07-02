package com.example.superheroeswiki

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.superheroeswiki.data.FileManager
import com.example.superheroeswiki.data.FileManager.PREF_HEROES_VALUE
import com.example.superheroeswiki.data.HeroData
import com.example.superheroeswiki.network.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.File

class MainViewModel(private val repository: Repository) : ViewModel() {

    val heroesList: MutableLiveData<Response<List<HeroData>>> = MutableLiveData()

    val heroesListFromStorage: MutableLiveData<List<HeroData>> = MutableLiveData()

    private fun getHeroesDataFromRemoteStorage() {
        viewModelScope.launch {
            val list = repository.getCharacter()
            heroesList.value = list
        }
    }

    private fun getHeroesDataFromLocalStorage() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = FileManager.getHeroDataFromStorage(PREF_HEROES_VALUE)
            heroesListFromStorage.postValue(list)
        }
    }

    fun downloadDataHeroToLocalStorage(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            FileManager.setHeroDataToStorage(PREF_HEROES_VALUE, value)
        }
    }


    fun storageType(): Int {
        val result: Int
        val file = File(FILE_PATH)
        result = if (!file.exists()) {
            REMOTE_STORAGE
        } else {
            LOCAL_STORAGE
        }
        when (result) {
            LOCAL_STORAGE -> getHeroesDataFromLocalStorage()
            REMOTE_STORAGE -> getHeroesDataFromRemoteStorage()
        }
        return result
    }

    companion object {
        @SuppressLint("SdCardPath")
        const val FILE_PATH =
            "/data/data/com.example.superheroeswiki/shared_prefs/HeroesDataStorageSP.xml"
        const val LOCAL_STORAGE = 0
        const val REMOTE_STORAGE = 1
    }
}