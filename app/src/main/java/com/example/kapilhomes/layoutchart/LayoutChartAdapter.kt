package com.example.kapilhomes.layoutchart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kapilhomes.R
import com.example.kapilhomes.databinding.LayoutchartDetailsBinding
import com.example.kapilhomes.plotdetails.PlotDetailsFragment
import java.util.*
import kotlin.collections.ArrayList


class LayoutChartAdapter(
    private var layoutChartResponse: ArrayList<LayoutChartResponse>?,
    private val context: Context?, private val fm: FragmentManager?, private var listSizeInterface: ListSizeInterface

) : RecyclerView.Adapter<LayoutChartAdapter.ViewHolder>() {

    lateinit var layoutid: String
    lateinit var plotno: String
    var searchstFiltered: ArrayList<LayoutChartResponse>?

    init {
        searchstFiltered = layoutChartResponse
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding: LayoutchartDetailsBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.layoutchart_details,
                parent,
                false
            )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val res: LayoutChartResponse = searchstFiltered!![position]
        holder.itemBinding?.setModel(res)

        holder.itemBinding?.textplot?.text = (res.plotno.toString())

        if(res.plotbookingstatus.equals("true")){
            holder.itemBinding?.textplot?.setBackgroundColor(ContextCompat.getColor(context!!, R.color.green))

        }else if(res.plotbookingstatus.equals("false")){
            holder.itemBinding?.textplot?.setBackgroundColor(ContextCompat.getColor(context!!, R.color.vacent))

        }



        holder.itemBinding?.textplot?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                layoutid = res.layoutid.toString()
                plotno = res.plotno.toString()
                val alertDialogFragment = PlotDetailsFragment()

                val bundle = Bundle()
                bundle.putString("layoutid", layoutid)
                bundle.putString("plotno", plotno)
//                Navigation.findNavController(view!!).navigate(R.id.action_layoutChartFragment_to_plotDetailsFragment)

                alertDialogFragment.setArguments(bundle)

                if (fm != null) {
                    alertDialogFragment.show(fm, "alert")
                }


            }

        })
    }


    override fun getItemCount(): Int {


        return if (searchstFiltered?.size!! > 0) searchstFiltered!!.size else 0
    }

    class ViewHolder(itemBinding: LayoutchartDetailsBinding) :
        RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemBinding: LayoutchartDetailsBinding? = null

        init {
            this.itemBinding = itemBinding
        }
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                var filteredList: ArrayList<LayoutChartResponse>? =
                    ArrayList()
                if (charString.isEmpty()) {
                    filteredList = layoutChartResponse
                } else {
                    for (row in layoutChartResponse!!) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match\
                        /*if (charString.equals("all")) {
                            filteredList?.add(row)
                        } else*/ if (row.plotbookingstatus.toLowerCase(Locale.ROOT)
                                .contains(charString.toLowerCase(Locale.ROOT))) {
                            filteredList?.add(row)
                        }

                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                searchstFiltered = filterResults.values as ArrayList<LayoutChartResponse>?
                listSizeInterface.getListSize(searchstFiltered?.size)
                notifyDataSetChanged()
            }
        }
    }

}