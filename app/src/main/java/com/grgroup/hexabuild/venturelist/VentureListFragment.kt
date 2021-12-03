package com.grgroup.hexabuild.venturelist

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.grgroup.hexabuild.R
import com.grgroup.hexabuild.databinding.VentureListFragmentBinding
import com.grgroup.hexabuild.utils.Utils

class VentureListFragment : Fragment() {


    private var mAdapter: VentureAdapter? = null
    private var binding: VentureListFragmentBinding? = null
    private var queryString: String? = null
    private var data: String? = null

    companion object {
        fun newInstance() = VentureListFragment()
    }

    private lateinit var viewModel: VentureListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.venture_list_fragment,
            container,
            false
        )

        (activity as AppCompatActivity).supportActionBar?.title = "Layouts"

        setHasOptionsMenu(true)

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(VentureListViewModel::class.java)

        viewModel.getList()
        Utils.showProgressBar(activity)
        viewModel.response.observe(viewLifecycleOwner, {
            Utils.closeProgressBar()

            if (it.isSuccessful) {

                 data= it.body().toString()

                if (it.body() != null && it.body()!!.isNotEmpty()) {
                    mAdapter = VentureAdapter(it.body(), context)
                    binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
                    binding?.recyclerView?.adapter = (mAdapter)
                    binding!!.noDataFound.visibility = View.GONE

                } else {

                    binding!!.noDataFound.visibility = View.VISIBLE
                }


            } else {
                Toast.makeText(getActivity(), "Someting went wrong", Toast.LENGTH_SHORT).show();

            }

        })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu_search, menu);
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(
            searchManager.getSearchableInfo(requireActivity().componentName)
        )

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextChange(qString: String): Boolean {

                if(data?.contains(qString)!!){
                    queryString = qString
                    mAdapter!!.getFilter()!!.filter(qString)
                    binding!!.noDataFound.visibility = View.GONE
                    binding!!.recyclerView.visibility=View.VISIBLE

                } else{
                    binding!!.noDataFound.visibility = View.VISIBLE
                    binding!!.recyclerView.visibility=View.GONE

                }
                return false

            }

            override fun onQueryTextSubmit(qString: String): Boolean {
//                showProgress()

                if(data?.contains(qString)!!){
                    queryString = qString
                    mAdapter!!.getFilter()!!.filter(qString)
                    binding!!.noDataFound.visibility = View.GONE
                    binding!!.recyclerView.visibility=View.VISIBLE

                } else{
                    binding!!.noDataFound.visibility = View.VISIBLE
                    binding!!.recyclerView.visibility=View.GONE

                }
                return false


            }
        })



    }


    override fun onResume() {
        super.onResume()
        if (mAdapter != null) {
            if (!TextUtils.isEmpty(queryString)) mAdapter!!.getFilter()!!.filter(queryString)
        }
    }


}