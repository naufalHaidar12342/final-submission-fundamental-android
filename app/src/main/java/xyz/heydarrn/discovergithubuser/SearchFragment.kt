package xyz.heydarrn.discovergithubuser

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.heydarrn.discovergithubuser.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private var _bindingSearchFragment:FragmentSearchBinding?=null
    private val bindingSearch get() = _bindingSearchFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingSearchFragment= FragmentSearchBinding.inflate(inflater,container,false)
        return bindingSearch?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingSearch?.inspectocatAtHome?.setImageResource(R.drawable.github_octocat_png_github_inspectocat_896)

    }
}