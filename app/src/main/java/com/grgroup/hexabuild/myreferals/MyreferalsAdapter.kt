package com.grgroup.hexabuild.myreferals

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.grgroup.hexabuild.R
import com.grgroup.hexabuild.databinding.MyreferalDetailsBinding
import com.grgroup.hexabuild.venturelist.VentureListResponse

class MyreferalsAdapter(private var myReferralResponse: ArrayList<MyReferralResponse>?, private val context: Context?) : RecyclerView.Adapter<MyreferalsAdapter.ViewHolder>() {

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

        val entity: MyReferralResponse = myReferralResponse!![position]
        holder.itemBinding?.setModel(entity)
    }

    override fun getItemCount(): Int {
        return myReferralResponse!!.size
    }

    class ViewHolder(itemBinding: MyreferalDetailsBinding) :
        RecyclerView.ViewHolder(itemBinding.getRoot()) {
        var itemBinding: MyreferalDetailsBinding? = null

        init {
            this.itemBinding = itemBinding
        }
    }


}