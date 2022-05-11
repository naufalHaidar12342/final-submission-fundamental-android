package xyz.heydarrn.discovergithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.heydarrn.discovergithubuser.databinding.FragmentFollowingBinding
import xyz.heydarrn.discovergithubuser.model.FollowingListAdapter
import xyz.heydarrn.discovergithubuser.viewmodel.FollowingViewModel

class FollowingFragment : Fragment() {
    private var _bindingFollowing:FragmentFollowingBinding?=null
    private val bindingFollowing get() = _bindingFollowing
    private val viewModelFollowing by viewModels<FollowingViewModel>()
    private val adapterFollowing by lazy { FollowingListAdapter() }
    private lateinit var receiveUsername:String
    private val argsFollowing:FollowingFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingFollowing= FragmentFollowingBinding.inflate(inflater,container,false)
        return bindingFollowing?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        receiveUsername=argsFollowing.usernameToFollowing
        monitorViewModelFollowing(receiveUsername)
    }

    private fun monitorViewModelFollowing(username:String){
        viewModelFollowing.setFollowing(username)

        bindingFollowing?.recyclerViewFollowing?.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(context)
            adapter=adapterFollowing
        }

        viewModelFollowing.setNewFollowing().observe(viewLifecycleOwner){
            if (it!=null){
                adapterFollowing.submitList(it)
                false.showLoadingProgress()
            }
        }
    }


    private fun Boolean.showLoadingProgress(){
        when(this){
            true -> bindingFollowing?.progressBarFollowing?.visibility=View.VISIBLE
            false -> bindingFollowing?.progressBarFollowing?.visibility=View.GONE
        }
    }
}