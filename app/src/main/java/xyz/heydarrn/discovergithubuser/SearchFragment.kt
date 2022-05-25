package xyz.heydarrn.discovergithubuser

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import xyz.heydarrn.discovergithubuser.databinding.FragmentSearchBinding
import xyz.heydarrn.discovergithubuser.model.SearchUserListAdapter
import xyz.heydarrn.discovergithubuser.model.ThemeSettingPreference
import xyz.heydarrn.discovergithubuser.viewmodel.SearchUserViewModel
import xyz.heydarrn.discovergithubuser.viewmodel.ThemeSettingViewModel
import xyz.heydarrn.discovergithubuser.viewmodel.ThemeSettingViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
class SearchFragment : Fragment() {
    private var _bindingSearchFragment:FragmentSearchBinding?=null
    private val bindingSearch get() = _bindingSearchFragment
    private val viewModelSearch by viewModels<SearchUserViewModel>()
    private val searchAdapter by lazy { SearchUserListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingSearchFragment= FragmentSearchBinding.inflate(inflater,container,false)
        return bindingSearch?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingSearch?.inspectocatAtHome?.setImageResource(
            R.drawable.github_octocat_png_github_inspectocat_896)

        val switch=bindingSearch?.switchThemeSearchFragment
        if (switch != null) {
            setThisAppTheme(switch)
        }
        setOptionMenuForSearchFragment()
        monitorViewModel()
        getTextFromSearchView()

    }


    private fun setOptionMenuForSearchFragment(){
        bindingSearch?.toolbar?.apply {
            inflateMenu(R.menu.option_menu)
            setOnMenuItemClickListener { itemMenu ->
                when(itemMenu.itemId){
                    R.id.favourite_user_option ->{
                        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToFavouriteUserFragment())
                        true
                    }
                    R.id.theme_settings_option ->{
                        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToThemeSettingFragment())
                        true
                    }
                    else -> {false}
                }
            }
        }
    }
    private fun getTextFromSearchView(){
        bindingSearch?.searchViewSearchUser?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    viewModelSearch.searchUserOnSubmittedText(p0!!)
                    clearFocus()
                    false.showLoadingProgress()
                    return true
                }

                override fun onQueryTextChange(p0: String?): Boolean {
                    true.showLoadingProgress()
                    false.showInspectocat()
                    return false
                }

            })
        }
    }

    private fun monitorViewModel(){
        bindingSearch?.recyclerViewSearchUser?.apply {
            this.setHasFixedSize(true)
            this.layoutManager=LinearLayoutManager(context)
            this.adapter=searchAdapter
        }
        searchAdapter.setThisUserForSending(object : SearchUserListAdapter.ClickThisUser {
            override fun selectThisUser(selectedUser: String, idSelected:Int, profilePicture:String, profileLink:String) {
                findNavController().navigate(
                    SearchFragmentDirections
                        .actionSearchFragmentToDetailOfSelectedUserFragment()
                        .setUsernameSelected(selectedUser)
                        .setIdOfUsernameSelected(idSelected)
                        .setAvatarOfUsernameSelected(profilePicture)
                        .setProfileOfUsernameSelected(profileLink))
            }
        })

        viewModelSearch.setResultForViewModel().observe(viewLifecycleOwner){
            if (it!=null){
                searchAdapter.submitList(it)
                false.showLoadingProgress()
            }

        }
    }

    private fun Boolean.showLoadingProgress(){
        when(this){
            true -> bindingSearch?.progressBarSearchUser?.visibility=View.VISIBLE
            false -> bindingSearch?.progressBarSearchUser?.visibility=View.GONE
        }
    }

    private fun Boolean.showInspectocat(){
        when(this){
            true ->{
                bindingSearch?.apply {
                    inspectocatAtHome.visibility=View.VISIBLE
                    textviewInspectocat.visibility=View.VISIBLE
                    textViewInspectocatSubtitle.visibility=View.VISIBLE
                }
            }
            false -> {
                bindingSearch?.apply {
                    inspectocatAtHome.visibility=View.GONE
                    textviewInspectocat.visibility=View.GONE
                    textViewInspectocatSubtitle.visibility=View.GONE
                }
            }
        }
    }

    private fun setThisAppTheme(switch: SwitchCompat){
        val preference=ThemeSettingPreference.getThemeInstance(requireContext().dataStore)
        val viewModelTheme= ViewModelProvider(this,ThemeSettingViewModelFactory(preference))[ThemeSettingViewModel::class.java]

        viewModelTheme.getThemeSettingViewModel().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            bindingSearch?.switchThemeSearchFragment?.apply {

                if (isDarkModeActive){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switch.isChecked=true
                    text = textOn
                    bindingSearch?.iconThemeSearchFragment?.setImageResource(R.drawable.ic_baseline_dark_mode_24)

                }else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switch.isChecked=false
                    text = textOff
                    bindingSearch?.iconThemeSearchFragment?.setImageResource(R.drawable.ic_baseline_light_mode_24)
                }
                //listen or watch to the change of switch value (false when not clicked, true when clicked)
                setOnCheckedChangeListener { _, switchIsClicked ->
                    viewModelTheme.saveSelectedTheme(switchIsClicked)
                }
            }
        }
    }

}