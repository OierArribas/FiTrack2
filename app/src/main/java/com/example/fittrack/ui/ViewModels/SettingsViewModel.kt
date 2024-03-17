package com.example.fittrack.ui.ViewModels

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.ui.DataStore.Language
import com.example.fittrack.ui.DataStore.MyPreferencesDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val myPreferencesDataStore: MyPreferencesDataStore
) : ViewModel(){


    var idiomaActual: Language = when (Locale.getDefault().language.lowercase()){
        "es" -> Language.Spanish
        "en" -> Language.English

        else -> {
            Language.Spanish}
    }


    val theme = myPreferencesDataStore.themeFlow.map {
        it.theme
    }

    val lang = myPreferencesDataStore.langFlow.map {
        it.lang
    }

    var firstTime = 1

    fun updateTheme(theme: Boolean){
        viewModelScope.launch {
            myPreferencesDataStore.updateTheme(theme)
        }
    }

    fun updateLanguage(lang: Language){

        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(lang.lKey))

        viewModelScope.launch {
            myPreferencesDataStore.updateLang(lang)
        }
    }

    @Composable
    fun setLanguague() {


        val lang = this.lang.collectAsState(initial = idiomaActual).value.lKey

        if (idiomaActual.lKey != lang){
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(lang))
        }





    }

    @Composable
    fun getLanguage(): String {
        val vuelta = this.lang.collectAsState(initial = Language.English)
        return vuelta.value.lKey
    }


}