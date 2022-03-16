package com.grgroup.hexabuild.myreferals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.grgroup.hexabuild.R
import com.grgroup.hexabuild.databinding.MyreferalsFragmentBinding
import com.grgroup.hexabuild.venturelist.VentureAdapter
import com.unnamed.b.atv.model.TreeNode
import com.unnamed.b.atv.model.TreeNode.TreeNodeClickListener
import com.unnamed.b.atv.model.TreeNode.TreeNodeLongClickListener
import com.unnamed.b.atv.sample.holder.IconTreeItemHolder
import com.unnamed.b.atv.view.AndroidTreeView


class MyreferalsFragment : Fragment() {

    private var binding: MyreferalsFragmentBinding? = null

    private var loginuserdata: MyReferralResponse? = null
    private var parentuserdata: MyReferralResponse? = null
    private var childusedata: ArrayList<MyReferralResponse>? = ArrayList()
    var root: TreeNode = TreeNode.root()
    private var tView: AndroidTreeView? = null
    private var mAdapter: MyreferalsAdapter? = null


//    private var tView: AndroidTreeView? = null

    companion object {
        fun newInstance() = MyreferalsFragment()
    }

    private lateinit var viewModel: MyreferalsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.myreferals_fragment,
                container,
                false
            )
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()

        (activity as AppCompatActivity).supportActionBar?.title = "My Referrals"
        binding?.fragment = this
        setHasOptionsMenu(true)


        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyreferalsViewModel::class.java)

        viewModel.getReferalList("0")

        viewModel.referallist.observe(viewLifecycleOwner, {

            if (it.isSuccessful) {
                val response: ArrayList<MyReferralResponse> = it.body()!!
                val contactid = 5
                for (i in 0..response.size - 1) {
                    if (contactid == response.get(i).contactId) {
                        loginuserdata = response.get(i)
                        binding?.introducer?.text = loginuserdata!!.referralNameTree
                        binding!!.imagearrow.visibility=View.VISIBLE
                        binding!!.imagearrow1.visibility=View.VISIBLE
                        binding!!.textview.visibility=View.VISIBLE
                        binding!!.cardLogin.visibility=View.VISIBLE
                        binding!!.cardParent.visibility=View.VISIBLE

                    }
                }
                for (i in 0..response.size - 1) {

                    if (loginuserdata?.introducedId.equals(response[i].tblMstReferralId.toString())) {
                        parentuserdata = response.get(i)
                        binding?.managername?.text = parentuserdata!!.referralNameTree
                        binding!!.imagearrow.visibility=View.VISIBLE
                        binding!!.imagearrow1.visibility=View.VISIBLE
                        binding!!.textview.visibility=View.VISIBLE
                        binding!!.cardLogin.visibility=View.VISIBLE
                        binding!!.cardParent.visibility=View.VISIBLE

                    }
                    val introducedId = 5
//                    if (loginuserdata.tblMstReferralId.toString() == response[i].introducedId.toString()) {
                    if (loginuserdata?.tblMstReferralId == introducedId) {

                        childusedata?.add(response[i]);


                    }else{
                        binding?.child?.text = "No data"

                    }
                }

                mAdapter = MyreferalsAdapter(childusedata, context)
                binding?.childlist?.layoutManager = LinearLayoutManager(context)
                binding?.childlist?.adapter = (mAdapter)
                mAdapter?.notifyDataSetChanged()


     //           tView = AndroidTreeView(activity, root)
       //         tView!!.setDefaultAnimation(true)
         //       tView!!.setDefaultContainerStyle(R.style.TreeNodeStyleCustom)
//                tView!!.setDefaultViewHolder(IconTreeItemHolder::class.java)
//                tView!!.setDefaultNodeClickListener(nodeClickListener)
//                tView!!.setDefaultNodeLongClickListener(nodeLongClickListener)

           //     binding?.treeView?.addView(tView!!.getView())
                Toast.makeText(activity, "success", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()

            }
        })


    }

    private val nodeClickListener =
        TreeNodeClickListener { node, value ->
            val item = value as IconTreeItemHolder.IconTreeItem
//            statusBar.setText("Last clicked: " + item.text)
        }

    private val nodeLongClickListener =
        TreeNodeLongClickListener { node, value ->
            val item = value as IconTreeItemHolder.IconTreeItem
            Toast.makeText(activity, "Long click: " + item.text, Toast.LENGTH_SHORT).show()
            true
        }

}