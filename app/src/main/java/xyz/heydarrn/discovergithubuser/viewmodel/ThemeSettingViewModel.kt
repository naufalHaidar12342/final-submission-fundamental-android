package xyz.heydarrn.discovergithubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import xyz.heydarrn.discovergithubuser.model.ThemeSettingPreference

class ThemeSettingViewModel(private val preference:ThemeSettingPreference):ViewModel() {
    fun getThemeSettingViewModel():LiveData<Boolean>{
        return preference.getThemeSetting().asLiveData()
    }

    fun saveSelectedTheme(isDarkMode:Boolean){
        viewModelScope.launch {
            preference.saveThemeSetting(isDarkMode)
        }
    }
}