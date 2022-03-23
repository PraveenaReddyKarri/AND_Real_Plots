package com.grgroup.hexabuild.venturelist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.grgroup.hexabuild.R
import com.grgroup.hexabuild.databinding.VentureDetailsBinding
import java.util.*
import kotlin.collections.ArrayList

class VentureAdapter(private var dataModels: ArrayList<VentureListResponse>?, private val context: Context?) :RecyclerView.Adapter<VentureAdapter.ViewHolder>() {

    private var searchstFiltered: ArrayList<VentureListResponse>? = null

    init{
        searchstFiltered=dataModels

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding: VentureDetailsBinding =
            DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.venture_details, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity: VentureListResponse = dataModels!![position]
        holder.itemBinding?.setModel(entity)
    }

    override fun getItemCount(): Int {
        return if (dataModels?.size!! > 0) dataModels!!.size else 0
    }

    class ViewHolder(itemBinding: VentureDetailsBinding) :
        RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemBinding: VentureDetailsBinding? = null

        init {
            this.itemBinding = itemBinding
        }
    }

    fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                var filteredList: ArrayList<VentureListResponse>? =
                    ArrayList()
                if (charString.isEmpty()) {
                    filteredList = searchstFiltered
                } else {
                    for (row in searchstFiltered!!) {

                        if (row.layoutname.toLowerCase(Locale.ROOT).trim().contains(charString.toLowerCase(Locale.ROOT)
                            )
                        ) {
                            filteredList?.add(row)
                        }

                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                dataModels = filterResults.values as ArrayList<VentureListResponse>?
                notifyDataSetChanged()
            }
        }
    }





}