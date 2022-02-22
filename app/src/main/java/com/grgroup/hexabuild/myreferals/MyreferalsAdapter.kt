package com.grgroup.hexabuild.myreferals

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.grgroup.hexabuild.R
import com.grgroup.hexabuild.databinding.MyreferalDetailsBinding

class MyreferalsAdapter(private val context: Context?) : RecyclerView.Adapter<MyreferalsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemBinding: MyreferalDetailsBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.myreferal_details,
                parent,
                false
            )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class ViewHolder(itemBinding: MyreferalDetailsBinding) :
        RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemBinding: MyreferalDetailsBinding? = null

        init {
            this.itemBinding = itemBinding
        }
    }


}