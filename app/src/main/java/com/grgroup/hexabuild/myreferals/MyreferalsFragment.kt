package com.grgroup.hexabuild.myreferals

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.grgroup.hexabuild.R

class MyreferalsFragment : Fragment() {

    companion object {
        fun newInstance() = MyreferalsFragment()
    }

    private lateinit var viewModel: MyreferalsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.myreferals_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyreferalsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}