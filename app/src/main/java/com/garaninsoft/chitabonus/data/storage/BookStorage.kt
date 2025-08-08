package com.garaninsoft.chitabonus.data.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.garaninsoft.chitabonus.data.model.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

private const val DATASTORE_NAME = "books_prefs"

val Context.bookDataStore by preferencesDataStore(name = DATASTORE_NAME)

class BookStorage(private val context: Context) {

    companion object {
        private val BOOKS_KEY = stringPreferencesKey("books_list_json")
        private val json = Json { encodeDefaults = true; ignoreUnknownKeys = true }
    }

    fun getBooks(): Flow<List<Book>> = context.bookDataStore.data.map { prefs ->
        prefs[BOOKS_KEY]?.let { s ->
            try {
                json.decodeFromString<List<Book>>(s)
            } catch (_: Exception) {
                // повреждённый JSON — вернуть пустой список
                emptyList()
            }
        } ?: emptyList()
    }

    suspend fun saveBooks(list: List<Book>) {
        context.bookDataStore.edit { prefs ->
            prefs[BOOKS_KEY] = json.encodeToString(list)
        }
    }
}