package com.grgroup.kapilhomes.plotdetails

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.grgroup.kapilhomes.R
import com.grgroup.kapilhomes.databinding.PlotDetailsFragmentBinding
import com.grgroup.kapilhomes.utils.Utils


class PlotDetailsFragment : DialogFragment() {

    private var binding: PlotDetailsFragmentBinding? = null

    lateinit var layoutid: String
    lateinit var plotno: String


    companion object {
        fun newInstance() = PlotDetailsFragment()
    }

    private lateinit var viewModel: PlotDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.plot_details_fragment, container, false)

        binding?.fragment = this

        val bundle = arguments
        if (bundle != null) {
            layoutid = bundle.getString("layoutid", "0")
            plotno = bundle.getString("plotno", "")
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlotDetailsViewModel::class.java)

        if (::layoutid.isInitialized && ::plotno.isInitialized) {
            viewModel.getPlotDetails(plotno, layoutid)
        }

        Utils.showProgressBar(context)
        viewModel.data.observe(viewLifecycleOwner, {
            Utils.closeProgressBar()

            if (it.body()?.get(0)?.pBookingstatus.equals("BOOKED")) {
                binding?.status?.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(), R.color.green
                    )
                );
            } else if (it.body()?.get(0)?.pBookingstatus.equals("VACANT")) {
                binding?.status?.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.vacent
                    )
                );


            } else if (it.body()?.get(0)?.pBookingstatus.equals("Register")) {
                binding?.status?.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.blue
                    )
                );

            }


            if (it.body()?.get(0)?.pBookingstatus.equals("BOOKED")) {

                binding?.bookingName?.visibility = View.VISIBLE
                binding?.bookingDate?.visibility = View.VISIBLE

            } else {
                binding?.bookingName?.visibility = View.GONE
                binding?.bookingDate?.visibility = View.GONE

            }


            if (it.isSuccessful) {


                if (!it.body().isNullOrEmpty()) {
                    binding?.uimodel = it.body()?.get(0)


                } else {

                    binding?.sdArea?.setText("0")
                    binding?.sqYards?.setText("0")
                    binding?.salArea?.setText("0")
                    binding?.commomAreaVal?.setText("0")
                    binding?.status?.setText("NA")
                    binding?.name?.setText("NA")
                    binding?.date?.setText("NA")

                }

            } else {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()
                Utils.closeProgressBar()

                binding?.errorlayout?.visibility = View.VISIBLE
                binding?.totallayout?.visibility = View.GONE
//                binding?.topLayout?.visibility=View.GONE
//                binding?.sdArea?.setText("0")
//                binding?.sqYards?.setText("0")
//                binding?.salArea?.setText("0")
//                binding?.commomAreaVal?.setText("0")
//                binding?.status?.setText("NA")
//                binding?.name?.setText("NA")
//                binding?.date?.setText("NA")

            }
        })
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window = dialog!!.window
        window?.setGravity(Gravity.CENTER)
        dialog!!.setCanceledOnTouchOutside(false)
    }

    fun closeDialog() {
        dialog!!.dismiss()
    }


}