package com.example.fittrack.ui.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

val Context.myPreferencesDataStore: DataStore<Preferences> by preferencesDataStore("settings")

enum class Theme(val themeName: String) {
    Light(themeName = "LightColorScheme"),
    Dark(themeName = "DarkColorScheme")
}

enum class Language(val lName: String, val lKey: String) {
    English(lName = "English", lKey = "en"),
    Spanish(lName = "Spanish", lKey = "es")
}

data class LangugagueStatus (
    val lang: Language
)


data class DarkModeStatus(
    val theme: Boolean
)

@Singleton
class MyPreferencesDataStore @Inject constructor(
    @ApplicationContext context: Context
) {
    private val myPreferencesDataStore = context.myPreferencesDataStore

    private object PreferencesKeys{
        val THEME_KEY = booleanPreferencesKey("theme")

    }

    private object LangPreferencesKeys {
        val LANG_KEY = stringPreferencesKey("LangName")

    }

    val themeFlow = myPreferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            val theme =  preferences[PreferencesKeys.THEME_KEY] ?: false

            DarkModeStatus(theme)

        }

    val langFlow = myPreferencesDataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->

            val lang = //Language.Spanish
                Language.valueOf(
                    preferences[LangPreferencesKeys.LANG_KEY] ?: Language.Spanish.lName
                )



            LangugagueStatus(lang)

        }

    suspend fun updateTheme(theme: Boolean) {
        myPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_KEY] = theme
        }
    }

    suspend fun updateLang(lang: Language) {
        myPreferencesDataStore.edit { preferences ->
            preferences[LangPreferencesKeys.LANG_KEY] = lang.lName         }
    }


    /*
    suspend fun updateTheme(theme: Theme) {
        myPreferencesDataStore.edit { preferences ->
            preferences[PreferencesKeys.THEME_KEY] = theme.name
        }
    }
*/
}