package com.grgroup.kapilhomes.layoutchart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.grgroup.kapilhomes.R
import com.grgroup.kapilhomes.databinding.LayoutChartFragmentBinding
import com.grgroup.kapilhomes.utils.Utils
import kotlin.collections.ArrayList


class LayoutChartFragment : Fragment(), RadioGroup.OnCheckedChangeListener, ListSizeInterface {

    private var mAdapter: LayoutChartAdapter? = null
    private var binding: LayoutChartFragmentBinding? = null

    lateinit var layoutid: String
    lateinit var name: String
    lateinit var plotno: String
    private var data: String? = null


    companion object {
        fun newInstance() = LayoutChartFragment()
    }

    private lateinit var viewModel: LayoutChartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.layout_chart_fragment,
            container,
            false
        )


        (activity as AppCompatActivity).supportActionBar?.title = "Layout Details"
        val bundle = arguments
        if (bundle != null) {
            layoutid = bundle.getString("id", "0")
            name = bundle.getString("name", "")

        }



        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(LayoutChartViewModel::class.java)
        if (::layoutid.isInitialized) {

            binding?.buttonLayout?.setOnCheckedChangeListener(this)
            binding?.buttonLayout1?.setOnCheckedChangeListener(this)
            viewModel.getChart(layoutid)

        }
        Utils.showProgressBar(activity)
        binding!!.cardView.visibility = View.GONE
        binding!!.cardViewRecycle.visibility = View.GONE


        viewModel.data.observe(viewLifecycleOwner, {
            Utils.closeProgressBar()
            binding!!.cardView.visibility = View.VISIBLE
            binding!!.cardViewRecycle.visibility = View.VISIBLE
            if (it.isSuccessful) {
                it.body()?.sortBy { it.plotno }

                getCountAndAsign(it.body())
                data = it.body().toString()

                val fm: FragmentManager? = fragmentManager
                binding?.uimodel = it.body()?.get(0)
                mAdapter = LayoutChartAdapter(it.body(), context, fm, this)
                binding?.gridView?.setHasFixedSize(true)
                binding?.gridView?.layoutManager = GridLayoutManager(context, 4)
                binding?.gridView?.adapter = (mAdapter)
            } else {

                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }

        })

    }

    private fun getCountAndAsign(it: ArrayList<LayoutChartResponse>?) {
        var bookedCount = 0
        var vacentCount = 0
        var registerCount = 0
        for (response in it!!) {
            // name match condition. this might differ depending on your requirement
            // here we are looking for name or phone number match\
            if (response.plotbookingstatus.equals("true")) {
                ++bookedCount
            } else if (response.plotbookingstatus.equals("false")) {
                ++vacentCount
            } else if (response.plotbookingstatus.equals("register")) {
                ++registerCount
            }


        }

        binding?.booked?.text = (" Booked  (" + bookedCount + ")")
        binding?.register?.text = (" Registered  (" + registerCount + ")")
        binding?.vacent?.text = (" Vacant  (" + vacentCount + ")")
        binding?.All?.text = (" All  (" + it.size + ")")

    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

        binding?.buttonLayout?.setOnCheckedChangeListener(null)
        binding?.buttonLayout1?.setOnCheckedChangeListener(null)
        if (checkedId == R.id.booked) {

            (mAdapter!!.getFilter().filter("true"))

            binding?.All?.isChecked = false
            binding?.vacent?.isChecked = false
            binding?.register?.isChecked = false
            binding?.booked?.isChecked = true


        } else if (checkedId == R.id.vacent) {


            mAdapter!!.getFilter().filter("false")
            binding?.booked?.isChecked = false
            binding?.register?.isChecked = false
            binding?.All?.isChecked = false
            binding?.vacent?.isChecked = true


        } else if (checkedId == R.id.register) {

            mAdapter!!.getFilter().filter("register")
            binding?.All?.isChecked = false
            binding?.vacent?.isChecked = false
            binding?.booked?.isChecked = false
            binding?.register?.isChecked = true


        } else if (checkedId == R.id.All) {

            mAdapter!!.getFilter().filter("")
            binding?.booked?.isChecked = false
            binding?.register?.isChecked = false
            binding?.vacent?.isChecked = false
            binding?.All?.isChecked = true

        }
        binding?.buttonLayout?.setOnCheckedChangeListener(this)
        binding?.buttonLayout1?.setOnCheckedChangeListener(this)


        /*  binding?.buttonLayout?.clearCheck()
          binding?.buttonLayout1?.clearCheck()

          binding?.buttonLayout?.setOnCheckedChangeListener(null)
          binding?.buttonLayout1?.setOnCheckedChangeListener(null)
          if (checkedId == R.id.All) {
              run {
                  mAdapter!!.getFilter()!!.filter("all")
                  binding?.All?.isChecked = true
              }

          }else if (checkedId == R.id.booked) {
              run {
                  mAdapter!!.getFilter()!!.filter("true")
                  binding?.booked?.isChecked = true
              }

          }else if (checkedId == R.id.vacent) {
              run {
                  mAdapter!!.getFilter()!!.filter("false")
                  binding?.vacent?.isChecked = true
              }

          }else if (checkedId == R.id.register) {
              run {
                  binding?.register?.isChecked = true
              }

          }

          binding?.buttonLayout?.setOnCheckedChangeListener(this)
          binding?.buttonLayout1?.setOnCheckedChangeListener(this)
  */


    }

    override fun getListSize(Size: Int?) {


        if (Size != null && Size > 0) {

            binding?.gridView?.visibility = View.VISIBLE

            binding?.noDataFound?.visibility = View.GONE

        } else {

            binding?.gridView?.visibility = View.GONE

            binding?.noDataFound?.visibility = View.VISIBLE


        }


    }


}