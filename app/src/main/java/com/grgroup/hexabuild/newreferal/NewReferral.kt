package com.grgroup.hexabuild.newreferal

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.grgroup.hexabuild.R
import com.grgroup.hexabuild.databinding.NewReferralFragmentBinding
import com.grgroup.hexabuild.utils.InternetConnection
import com.grgroup.hexabuild.utils.SharedPref
import com.grgroup.hexabuild.utils.Utils
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class NewReferral : Fragment() ,TextWatcher{
    private var binding: NewReferralFragmentBinding? = null


    private lateinit var viewModel: NewReferralViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.new_referral_fragment,
                container,
                false
            )
        (activity as AppCompatActivity).supportActionBar?.title = "New Referrals"
        binding?.fragment = this
        setHasOptionsMenu(true)

        binding?.usernameEditText?.addTextChangedListener(this)
        binding?.surnameEditText?.addTextChangedListener(this)


        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewReferralViewModel::class.java)
//        binding?.titlespinner?.setSelection(0)

        viewModel.getContactTitles()
        viewModel.titles.observe(viewLifecycleOwner, {

            if (it.isSuccessful) {
                val response: ArrayList<TitlesResponse> = it.body()!!

                val user: ArrayList<String> = ArrayList()

                for (i in 0..response.size-1) {
                    user.add(response.get(i).pTitleName)
                }
//                binding?.titlespinner?.


//                        binding?.model2?.text=response.toString()
                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    user
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                binding?.titlespinner?.setAdapter(adapter)

            }
        })


        viewModel.data.observe(viewLifecycleOwner, {
            Utils.closeProgressBar()

            if (it.isSuccessful) {
                val response: String = it.body()!!

                if (response.equals("0")) {


                    if (binding?.panEditText?.text.toString().length > 0) {
                            panRequestApi()
                    } else {
                        sendAllDataToAPI()

                    }

                } else {

                    Toast.makeText(
                        getActivity(),
                        "Mobile number already exists",
                        Toast.LENGTH_SHORT
                    ).show()

                }


            } else {
                Toast.makeText(getActivity(), "Unauthorized User", Toast.LENGTH_SHORT).show();

            }
        })


        viewModel.pan.observe(viewLifecycleOwner, {
            Utils.closeProgressBar()



            if (it.isSuccessful) {
                val response: String = it.body()!!

                if (response.equals("0")) {
                    sendAllDataToAPI()

//                    Toast.makeText(getActivity(), "Mobile number updated", Toast.LENGTH_SHORT).show()

                } else {

                    Toast.makeText(
                        getActivity(),
                        "Pan number already exists",
                        Toast.LENGTH_SHORT
                    ).show()

                }


            } else {
                Toast.makeText(getActivity(), "Unauthorized User", Toast.LENGTH_SHORT).show();

            }
        })





        viewModel.response.observe(viewLifecycleOwner, {
            Utils.closeProgressBar()


            if (it.isSuccessful) {
                val response: ArrayList<NewReferralResponse> = it.body()!!

                if (response.get(0).pstatus.equals("TRUE")) {
                    Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show()
                    binding?.mobileEditText?.getText()?.clear()
                    binding?.surnameEditText?.getText()?.clear()
                    binding?.usernameEditText?.getText()?.clear()
                    binding?.aadharEditText?.getText()?.clear()
                    binding?.panEditText?.getText()?.clear()
                    //                                    binding.editTextTicketNo.setText("");
                    binding?.titlespinner?.setSelection(0)


                } else {
                    Toast.makeText(getActivity(), "Something went  wrong", Toast.LENGTH_SHORT)
                        .show()

                }


            } else {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()

            }
        })

    }

    private fun sendAllDataToAPI() {
        val createdid: String = SharedPref(requireContext()).getCreatedbyId().toString()
        val introducedid: String = SharedPref(requireContext()).getIntrocudedId().toString()

        val request = NewReferralRequest(

            pName = binding?.usernameEditText?.getText().toString(),

            pTitleName = binding?.titlespinner?.selectedItem.toString(),

            pSurName = binding?.surnameEditText?.getText()
                .toString(),
            pBusinessEntitycontactNo = binding?.mobileEditText?.getText()
                .toString(),
            pCreatedby = createdid,
            pContactName = binding?.usernameEditText?.getText()
                .toString()
                    + " " +
                    binding?.surnameEditText?.getText().toString(),
            pPanNumber = binding?.panEditText?.getText().toString().toUpperCase(Locale.ROOT),
            pintroducedid = introducedid
        )
        viewModel.saveData(request)
    }

    private fun panRequestApi() {
        val request1 = PanValidateRequest(
            "3",
            binding?.panEditText?.getText().toString()


        )
        viewModel.panValidation("3",binding?.panEditText?.getText().toString())
    }


    fun saveData(view: View) {


        val s = binding?.panEditText?.getText().toString().toUpperCase(Locale.ROOT) // get your editext value here

        val pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}")

        val panMatcher: Matcher = pattern.matcher(s)


        view.hideKeyboard()
        if (!TextUtils.isEmpty(binding?.usernameEditText?.getText().toString().trim())) {
            if (!TextUtils.isEmpty(binding?.surnameEditText?.getText()?.trim())) {
                if (!TextUtils.isEmpty(binding?.mobileEditText?.getText()?.trim())) {
//                    if (!TextUtils.isEmpty(binding?.panEditText?.getText())) {

                    if (!(binding?.mobileEditText?.text.toString().trim().length < 10)) {

//                            if (!(binding?.panEditText?.text.toString().trim().length < 10)) {
                        if (!(binding?.usernameEditText?.text.toString().trim().length < 2)) {
                            if (!(binding?.surnameEditText?.text.toString().trim().length < 1)) {
                                if (!TextUtils.isEmpty(binding?.panEditText?.getText()?.trim())) {
                                    if (!panMatcher.matches()) {

                                        Toast.makeText(
                                            activity,
                                            "Enter valid Pan number",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        return

                                    }

                                }

                                if (InternetConnection().isNetworkConnected(requireActivity())) {


                                    val request = MobileValidateRequest(

                                        binding?.mobileEditText?.getText().toString()

                                    )


                                    viewModel.mobileValidation(request)
//                                    viewModel.panValidation(request1)


                                } else {
                                    Utils.closeProgressBar()
                                    Toast.makeText(
                                        activity,
                                        "No Internet Please try later",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }


                            } else {
                                Toast.makeText(activity, "Enter valid Sur Name", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        } else {
                            Toast.makeText(activity, "Enter valid Name", Toast.LENGTH_SHORT).show()
                        }
//                            } else {
//                                Toast.makeText(
//                                    activity,
//                                    "Pan number should be 10 chars",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            }
//
                    } else {
                        Toast.makeText(
                            activity,
                            "Enter valid mobile number",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
//                    } else {
//                        Toast.makeText(activity, "Enter Pan Number", Toast.LENGTH_SHORT).show()
//                    }

                } else {
                    Toast.makeText(activity, "Enter Mobile Number", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(activity, "Enter SurName", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "Enter Name", Toast.LENGTH_SHORT).show();
        }

    }

    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


    }
    override fun afterTextChanged(s: Editable?) {
        if(s.toString().equals(" "))
        {
            if(binding?.usernameEditText?.hasFocus()!!){
                binding?.usernameEditText!!.setText("");
            }else if(binding!!.surnameEditText.hasFocus()){
                binding?.surnameEditText!!.setText("");
            }
        }
    }

}