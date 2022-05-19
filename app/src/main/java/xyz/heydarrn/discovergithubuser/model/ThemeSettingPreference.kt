package xyz.heydarrn.discovergithubuser.model

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.preference.Preference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeSettingPreference private constructor(private val dataStore:DataStore<Preferences>){

    private val THEME_SETTING_KEY= booleanPreferencesKey("theme_setting")

    fun getThemeSetting():Flow<Boolean>{
        return dataStore.data.map { preference ->
            preference[THEME_SETTING_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive:Boolean){
        dataStore.edit { editPreference ->
            editPreference[THEME_SETTING_KEY]=isDarkModeActive
        }
    }


    companion object{
        private var THEME_INSTANCE:ThemeSettingPreference?=null

        fun getThemeInstance(dataStore: DataStore<Preferences>): ThemeSettingPreference{
            return THEME_INSTANCE ?: synchronized(this){
                val themeInstance = ThemeSettingPreference(dataStore)
                THEME_INSTANCE=themeInstance
                themeInstance
            }
        }
    }
}