package com.example.appvet_grupo2.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.appvet_grupo2.model.Mascota
import com.example.appvet_grupo2.model.HoraAgendada
import com.example.appvet_grupo2.model.Usuario
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(name = "app_prefs")

class DataStoreManager(private val context: Context) {
    private val gson = Gson()


    private val USERS_KEY = stringPreferencesKey("usuarios")
    private val MASCOTAS_KEY = stringPreferencesKey("mascotas")
    private val HORAS_AGENDADAS_KEY = stringPreferencesKey("horas_agendadas")


    suspend fun saveUsers(users: List<Usuario>) {
        val json = gson.toJson(users)
        context.dataStore.edit { prefs ->
            prefs[USERS_KEY] = json
        }
    }


    fun getUsers(): Flow<List<Usuario>> {
        return context.dataStore.data.map { prefs ->
            val json = prefs[USERS_KEY] ?: "[]"
            val type = object : TypeToken<List<Usuario>>() {}.type
            gson.fromJson(json, type)
        }
    }


    suspend fun saveMascotas(mascotas: List<Mascota>) {
        val json = gson.toJson(mascotas)
        context.dataStore.edit { prefs ->
            prefs[MASCOTAS_KEY] = json
        }
    }


    fun getMascotas(): Flow<List<Mascota>> {
        return context.dataStore.data.map { prefs ->
            val json = prefs[MASCOTAS_KEY] ?: "[]"
            val type = object : TypeToken<List<Mascota>>() {}.type
            gson.fromJson(json, type)
        }
    }


    suspend fun saveHorasAgendadas(horas: List<HoraAgendada>) {
        val json = gson.toJson(horas)
        context.dataStore.edit { prefs ->
            prefs[HORAS_AGENDADAS_KEY] = json
        }
    }


    fun getHorasAgendadas(): Flow<List<HoraAgendada>> {
        return context.dataStore.data.map { prefs ->
            val json = prefs[HORAS_AGENDADAS_KEY] ?: "[]"
            val type = object : TypeToken<List<HoraAgendada>>() {}.type
            gson.fromJson(json, type)
        }
    }

    suspend fun clearAll() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}