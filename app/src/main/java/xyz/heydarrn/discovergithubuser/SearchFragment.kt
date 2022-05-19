package xyz.heydarrn.discovergithubuser

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.heydarrn.discovergithubuser.databinding.FragmentSearchBinding
import xyz.heydarrn.discovergithubuser.model.SearchUserListAdapter
import xyz.heydarrn.discovergithubuser.model.api.ItemsItem
import xyz.heydarrn.discovergithubuser.viewmodel.SearchUserViewModel

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
            override fun selectThisUser(selectedUser: String) {
                findNavController().navigate(
                    SearchFragmentDirections
                        .actionSearchFragmentToDetailOfSelectedUserFragment()
                        .setUsernameSelected(selectedUser))
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

}