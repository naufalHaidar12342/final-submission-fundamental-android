package xyz.heydarrn.discovergithubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import xyz.heydarrn.discovergithubuser.databinding.FragmentFollowerBinding
import xyz.heydarrn.discovergithubuser.model.FollowerListAdapter
import xyz.heydarrn.discovergithubuser.viewmodel.FollowerViewModel

class FollowerFragment : Fragment() {
    private var _bindingFollower:FragmentFollowerBinding?=null
    private val bindingFollower get() = _bindingFollower
    private val viewModelFollower by viewModels<FollowerViewModel>()
    private var gettingBundle=Bundle()
    private val adapterFollower by lazy { FollowerListAdapter() }
    private var getFromListener:String="naufalHaidar"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingFollower= FragmentFollowerBinding.inflate(inflater,container,false)
        return bindingFollower?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("USERNAME_LISTEN"){_,bundle ->
            getFromListener=bundle.getString("USER","naufalHaidar")
            Log.d("CHECK FRAG LISTEN", "onViewCreated: $getFromListener")
            monitorViewModelFollower(getFromListener)
            Log.d("CHECK STRING", "onViewCreated: getFromListener = $getFromListener")
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
    companion object {

    }
}