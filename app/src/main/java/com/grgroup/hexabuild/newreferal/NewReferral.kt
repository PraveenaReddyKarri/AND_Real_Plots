package com.grgroup.hexabuild.newreferal

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.grgroup.hexabuild.FilePath.getPath1
import com.grgroup.hexabuild.R
import com.grgroup.hexabuild.databinding.NewReferralFragmentBinding
import com.grgroup.hexabuild.sitevisit.ImagePickerActivity
import com.grgroup.hexabuild.utils.InternetConnection
import com.grgroup.hexabuild.utils.SharedPref
import com.grgroup.hexabuild.utils.Utils
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class NewReferral : Fragment() ,TextWatcher{
    private var binding: NewReferralFragmentBinding? = null
    private val RESULT_LOAD_IMAGE = 1

    val REQUEST_IMAGE = 100
    var imageBase64String: String? = null
    var filename: String? = null

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



        binding?.profiePic?.setOnClickListener { view ->
//            val i = Intent(
//                Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//            )
//            registerForActivityResult(i, RESULT_LOAD_IMAGE)

            onProfileImageClick()
        }
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

                for (i in 0..response.size - 1) {
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

                    Toast.makeText(getActivity(), "Pan updated", Toast.LENGTH_SHORT).show()

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


        viewModel.image.observe(viewLifecycleOwner, {
            Utils.closeProgressBar()
            if (it.isSuccessful) {
                val response: ArrayList<String> = it.body()!!
                filename = response[1]

//                if (response.equals("0")) {
//                    sendAllDataToAPI()

                Toast.makeText(getActivity(), "Image uploaded", Toast.LENGTH_SHORT).show()

            } else {

                Toast.makeText(
                    getActivity(),
                    "ERROR",
                    Toast.LENGTH_SHORT
                ).show()

            }


//            } else {
//                Toast.makeText(getActivity(), "Unauthorized User", Toast.LENGTH_SHORT).show();
//
//            }
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
                    binding?.profiePic?.setImageResource(R.drawable.ic_baseline_perm_identity_24)
                    binding?.titlespinner?.setSelection(0)
                    filename = null

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

            pName = binding?.usernameEditText?.text.toString(),

            pTitleName = binding?.titlespinner?.selectedItem.toString(),

            pSurName = binding?.surnameEditText?.text
                .toString(),
            pBusinessEntitycontactNo = binding?.mobileEditText?.text
                .toString(),
            pCreatedby = createdid,
            pContactName = binding?.usernameEditText?.text
                .toString()
                    + " " +
                    binding?.surnameEditText?.text.toString(),
            pPanNumber = binding?.panEditText?.text.toString().toUpperCase(Locale.ROOT),
            pintroducedid = introducedid,
            pPhoto = filename.toString()

        )
        viewModel.saveData(request)
    }

    private fun panRequestApi() {
        val request1 = PanValidateRequest(
            "3",
            binding?.panEditText?.text.toString()


        )
        viewModel.panValidation("3", binding?.panEditText?.text.toString())
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

//

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
                                if(filename != null) {


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

                                }else{
                                    Toast.makeText(activity, "Upload Image", Toast.LENGTH_SHORT).show()


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
                        Toast.makeText(activity, "Enter valid mobile number", Toast.LENGTH_SHORT).show()
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


    fun onProfileImageClick() {
        Dexter.withActivity(activity)
            .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        showImagePickerOptions()
                    }
                    if (report.isAnyPermissionPermanentlyDenied) {
                        showSettingsDialog()
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).check()
    }

    private fun showImagePickerOptions() {
        ImagePickerActivity.showbothImagePickerOptions(requireContext(), object :
            ImagePickerActivity.PickerOptionListener {
            override fun onTakeCameraSelected() {
                launchCameraIntent()
            }

            override fun onChooseGallerySelected() {
                launchGalleryIntent()
            }
        })
    }


    private fun launchCameraIntent() {
        val intent = Intent(activity, ImagePickerActivity::class.java)
        intent.putExtra(
            ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION,
            ImagePickerActivity.REQUEST_IMAGE_CAPTURE
        )

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000)
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000)
        startActivityForResult(intent, REQUEST_IMAGE)
    }
    private fun launchGalleryIntent() {
        val intent = Intent(activity, ImagePickerActivity::class.java)
        intent.putExtra(
            ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION,
            ImagePickerActivity.REQUEST_GALLERY_IMAGE
        )

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)
        startActivityForResult(intent, REQUEST_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === REQUEST_IMAGE) {
            if (resultCode === Activity.RESULT_OK) {
                val uri: Uri? = data!!.getParcelableExtra("path")
                try {

                //    val uri = data.data
                    val selectedFilePath = getPath1(requireContext(), uri!!)
                    val file = File(selectedFilePath)

                    binding?.profiePic?.setImageURI(uri)
//
                    val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "file", file.getName(),
                        RequestBody.create("image/*".toMediaTypeOrNull(), file)
                    );
//
//                    Log.e("hell",file.toString())
                    viewModel.imageUpload(filePart)
                    // You can update this bitmap to your server
//                    val bitmap = MediaStore.Images.Media.getBitmap(
//                        requireContext().contentResolver,
//                        uri
//                    )
//
//                    // loading profile image from local cache
//                    binding!!.profiePic.setImageBitmap(bitmap)
//                   imageBase64String = convert(bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }    }


    fun getRealPathFromURI(context: Context?, contentUri: Uri?): String? {
        Log.d("imin", "onClick: in image conversion")
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = context?.contentResolver?.query(contentUri!!, proj, null, null, null)
            val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor.moveToFirst()
            Log.d("imin", "onClick: in image conversion try")
            cursor.getString(column_index)
        } finally {
            Log.d("imin", "onClick: in image conversion finally")
            if (cursor != null) {
                cursor.close()
            }
        }
    }

//   public final fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent) {
//        if (requestCode == REQUEST_IMAGE) {
//            if (resultCode == Activity.RESULT_OK) {
//                val uri: Uri = data.getParcelableExtra("path")
//                try {
//                    // You can update this bitmap to your server
//                    val bitmap = MediaStore.Images.Media.getBitmap(context!!.contentResolver, uri)
//
//                    // loading profile image from local cache
//                    binding!!.captureImage.setImageBitmap(bitmap)
//                    mViewModel.imageBase64String = convert(bitmap)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        }
//    }

    fun convert(bitmap: Bitmap): String? {

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream)
        val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.
        //        JPEG, 70, outputStream);
//        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }


    private fun showSettingsDialog() {

        val builder = AlertDialog.Builder(
            requireContext()
        )
        builder.setTitle(getString(R.string.dialog_permission_title))
        builder.setMessage(getString(R.string.dialog_permission_message))
        builder.setPositiveButton(getString(R.string.go_to_settings)) { dialog, which ->
            dialog.cancel()
            openSettings()
        }
        builder.setNegativeButton(getString(android.R.string.cancel)) { dialog, which -> dialog.cancel() }
        builder.show()
    }

    // navigating user to app settings
    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", requireContext().packageName, null)
        intent.data = uri
        startActivityForResult(intent, 101)
    }




}