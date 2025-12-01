package com.example.vivochat.data.dataSource.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class LocalDataSource (
    val context: Context
){
    private val Context.dataStore by preferencesDataStore("local")


    suspend fun saveData(userId : String) {
        context.dataStore.edit {
            it[stringPreferencesKey("userId")] = userId
        }
    }


    suspend fun getUserId(): String? {
        return context.dataStore.data.map {
            it[stringPreferencesKey("userId")]
        }.first()
    }
    suspend fun clearData(){
        //we will use it when user logout or delete hisss account
        //will be called in settings screen
        context.dataStore.edit { it.clear() }
    }
}