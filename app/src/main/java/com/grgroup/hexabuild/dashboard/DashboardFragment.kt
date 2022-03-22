package com.grgroup.hexabuild.dashboard

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.grgroup.hexabuild.R
import com.grgroup.hexabuild.databinding.DashboardFragmentBinding
import com.grgroup.hexabuild.utils.SharedPref


class DashboardFragment : Fragment() {

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


        binding?.ventures?.setBackgroundResource(R.drawable.card_bg)
        binding?.newreferral?.setBackgroundResource(R.drawable.card_referral)
        binding?.myreferals?.setBackgroundResource(R.drawable.card_myreferral)
        binding?.sitevisite?.setBackgroundResource(R.drawable.card_sitevisit)


        viewModel.data.observe(viewLifecycleOwner, {
            binding?.view = it

        })
        gotoRefferals()
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
//            val pref: SharedPref? = context?.let { SharedPref(it).getmSharedPrefInstance() }
            Navigation.findNavController(view)
                .navigate(R.id.action_dashboardFragment_to_plotsListFragment)

        }
        binding?.myreferals?.setOnClickListener { view ->
//            val pref: SharedPref? = context?.let { SharedPref(it).getmSharedPrefInstance() }
            Navigation.findNavController(view)
                .navigate(R.id.action_dashboardFragment_to_myreferalsFragment)

        }
        binding?.sitevisite?.setOnClickListener { view ->
//            val pref: SharedPref? = context?.let { SharedPref(it).getmSharedPrefInstance() }
            Navigation.findNavController(view)
                .navigate(R.id.action_dashboardFragment_to_siteVisiting)

        }


    }


    fun gotoRefferals() {

        binding?.newreferral?.setOnClickListener { view ->
            Navigation.findNavController(view)
                .navigate(R.id.action_dashboardFragment_to_newReferral)

        }
    }


}