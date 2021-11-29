package com.grgroup.kapilhomes.login

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.grgroup.kapilhomes.BuildConfig
import com.grgroup.kapilhomes.R
import com.grgroup.kapilhomes.databinding.LoginnFragmentBinding
import com.grgroup.kapilhomes.utils.InternetConnection
import com.grgroup.kapilhomes.utils.SharedPref
import com.grgroup.kapilhomes.utils.Utils

class LoginFragment : Fragment() {
    var binding: LoginnFragmentBinding? = null
    var versionName: String = BuildConfig.VERSION_NAME
    lateinit var navHostFragment: NavHostFragment


    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.loginn_fragment, container, false)

        binding?.model = this

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()

        binding?.version?.setText(" Version  $versionName")


        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.response.observe(viewLifecycleOwner, {
            Utils.closeProgressBar()

            if (it.isSuccessful) {
                val response: LoginResponse = it.body()!!

                if (response.toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();

                } else {
                    viewModel.getId( binding?.usernameEditText?.getText().toString())

                    saveResponseInSharedPref(response, view)

                    /*  Navigation.findNavController(view)
                        .navigate(R.id.action_loginFragment_to_dashboardFragment)*/
//                    Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show()
                }


            } else {
                Toast.makeText(getActivity(), "Unauthorized User", Toast.LENGTH_SHORT).show();

            }
        })


        viewModel.data.observe(viewLifecycleOwner, {
//            Utils.closeProgressBar()
            val pref: SharedPref? = context?.let { SharedPref(it).getmSharedPrefInstance() }

            if (it.isSuccessful) {
                val response: ArrayList<GetUserIdResponse> = it.body()!!

                if(response.size>0){
                    pref?.setCreatedbyId(response.get(0).pUserID)
                    pref?.setIntrocudedId(response.get(0).pContactID)

                }

                navHostFragment =
                    (activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
                val inflater = navHostFragment.navController.navInflater
                val graph = inflater.inflate(R.navigation.nav_graph)


                graph.startDestination = R.id.dashboardFragment



                navHostFragment.navController.graph = graph


                if (response.toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();

                }


            } else {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        })

    }


    private fun saveResponseInSharedPref(response: LoginResponse, view: View) {

        val pref: SharedPref? = context?.let { SharedPref(it).getmSharedPrefInstance() }

        pref?.setLoginUserName(response.pUserName)
        pref?.setUserId(response.pUserID)
        pref?.setAccessKey(response.pToken)



//        pref?.setValue(SharedPref().,response.pUserName)
//        getActivity()?.getViewModelStore()?.clear()




//        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_dashboardFragment)
    }




    fun validateLogin(view: View) {

        view.hideKeyboard()
        if (!TextUtils.isEmpty(binding?.usernameEditText?.getText())) {
            if (!TextUtils.isEmpty(binding?.passwordEditText?.getText())) {

                Utils.showProgressBar(activity)

                val request = LoginRequest(
                    binding?.usernameEditText?.getText().toString(),
                    binding?.passwordEditText?.getText().toString()
                )

                if (InternetConnection().isNetworkConnected(requireActivity())) {
//            binding?.signUpButton?.setVisibility(View.INVISIBLE)
//                    Utils.showProgressBar(context)

                    viewModel.doLogin(request)

                } else {
                    Utils.closeProgressBar()
                    Toast.makeText(activity, "No Internet Please try later", Toast.LENGTH_SHORT).show()
                }



            } else {
                Toast.makeText(activity, "Enter Password", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "Enter Username ", Toast.LENGTH_SHORT).show();
        }
    }
    override fun onResume() {
        super.onResume()
//        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }


    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


}


