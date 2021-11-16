package com.example.kapilhomes.dashboard

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.kapilhomes.R
import com.example.kapilhomes.databinding.DashboardFragmentBinding
import com.example.kapilhomes.utils.SharedPref


class DashboardFragment : Fragment()  {

    var binding: DashboardFragmentBinding? = null;

    companion object {
        fun newInstance() = DashboardFragment()
    }

    private lateinit var viewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, R.layout.dashboard_fragment, container, false)
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        (activity as AppCompatActivity).supportActionBar?.title = "Dashboard"
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)


        viewModel.data.observe(viewLifecycleOwner, {
            binding?.view = it

        })

        onClick()

    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.dashboard_menu, menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        setHasOptionsMenu(true)
//        super.onCreate(savedInstanceState)
//    }


    fun onClick() {
        binding?.ventures?.setOnClickListener { view ->
            val pref: SharedPref? = context?.let { SharedPref(it).getmSharedPrefInstance() }
            Navigation.findNavController(view)
                .navigate(R.id.action_dashboardFragment_to_plotsListFragment)

        }



    }

    fun goNext(){
        binding?.ventures?.performClick()
    }








}