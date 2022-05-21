package xyz.heydarrn.discovergithubuser.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import xyz.heydarrn.discovergithubuser.model.ThemeSettingPreference

@Suppress("UNCHECKED_CAST")
class ThemeSettingViewModelFactory(private val preference: ThemeSettingPreference):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeSettingViewModel::class.java)){
            return ThemeSettingViewModel(preference) as T
        }
        throw IllegalArgumentException("unknown viewmodel class : " +modelClass.name)
    }
}