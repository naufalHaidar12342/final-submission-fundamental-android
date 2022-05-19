package xyz.heydarrn.discovergithubuser

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import xyz.heydarrn.discovergithubuser.databinding.FragmentThemeSettingBinding
import xyz.heydarrn.discovergithubuser.model.ThemeSettingPreference
import xyz.heydarrn.discovergithubuser.viewmodel.ThemeSettingViewModel
import xyz.heydarrn.discovergithubuser.viewmodel.ThemeSettingViewModelFactory

private val Context.dataStore:DataStore<Preferences> by preferencesDataStore(name = "setting")
class ThemeSettingFragment : Fragment() {
    private var _bindingThemeSetting:FragmentThemeSettingBinding?=null
    private val bindingThemeSetting get() = _bindingThemeSetting

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingThemeSetting=FragmentThemeSettingBinding.inflate(inflater,container,false)
        return bindingThemeSetting?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingThemeSetting?.imageViewThemeIcon?.setImageResource(R.drawable.ic_baseline_light_mode_24)
        setThisAppTheme()

    }

    private fun setThisAppTheme(){
        val preference=ThemeSettingPreference.getThemeInstance(requireContext().dataStore)
        val viewModelTheme= ViewModelProvider(this,ThemeSettingViewModelFactory(preference))[ThemeSettingViewModel::class.java]

        viewModelTheme.getThemeSettingViewModel().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            bindingThemeSetting?.switchDarkMode?.apply {

                if (isDarkModeActive){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    text = textOn
                    bindingThemeSetting?.imageViewThemeIcon?.setImageResource(R.drawable.ic_baseline_dark_mode_24)

                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    text = textOff
                    bindingThemeSetting?.imageViewThemeIcon?.setImageResource(R.drawable.ic_baseline_light_mode_24)
                }
                //listen or watch to the change of switch value (false when not clicked, true when clicked)
                setOnCheckedChangeListener { _, switchIsClicked ->
                    viewModelTheme.saveSelectedTheme(switchIsClicked)
                }
            }
        }
    }

}