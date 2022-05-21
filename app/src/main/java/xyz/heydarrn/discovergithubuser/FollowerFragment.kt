package xyz.heydarrn.discovergithubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.heydarrn.discovergithubuser.databinding.FragmentFollowerBinding
import xyz.heydarrn.discovergithubuser.model.FollowerListAdapter
import xyz.heydarrn.discovergithubuser.viewmodel.FollowerViewModel

class FollowerFragment : Fragment() {
    private var _bindingFollower:FragmentFollowerBinding?=null
    private val bindingFollower get() = _bindingFollower
    private val viewModelFollower by viewModels<FollowerViewModel>()
    private val adapterFollower by lazy { FollowerListAdapter() }
    private lateinit var receivedUsername:String
    private val argsFollower:FollowerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingFollower= FragmentFollowerBinding.inflate(inflater,container,false)
        return bindingFollower?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //set receivedUsername value with safe argument, sent by detail user fragment
        receivedUsername=argsFollower.usernameToFollower

        monitorViewModelFollower(receivedUsername)

        //back to detail of user fragment
        bindingFollower?.toolbarFollower?.setNavigationOnClickListener {
            findNavController().navigate(FollowerFragmentDirections.actionFollowerFragmentToDetailOfSelectedUserFragment())
        }

    }

    private fun monitorViewModelFollower(getUsername:String){
        viewModelFollower.setFollower(getUsername)
        bindingFollower?.recyclerViewFollower?.apply {
            setHasFixedSize(true)
            layoutManager=LinearLayoutManager(context)
            adapter=adapterFollower
        }
        viewModelFollower.setNewFollower().observe(viewLifecycleOwner){
            if (it!=null){
                adapterFollower.submitList(it)
                false.showLoadingProgress()
            }
        }
    }
    private fun Boolean.showLoadingProgress(){
        when(this){
            true -> bindingFollower?.progressBarFollower?.visibility=View.VISIBLE
            false -> bindingFollower?.progressBarFollower?.visibility=View.GONE
        }
    }
}