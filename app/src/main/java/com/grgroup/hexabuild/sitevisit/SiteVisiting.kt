package com.grgroup.hexabuild. sitevisit

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.grgroup.hexabuild.FilePath
import com.grgroup.hexabuild.R
import com.grgroup.hexabuild.databinding.SiteVisitingFragmentBinding
import com.grgroup.hexabuild.utils.SharedPref
import com.grgroup.hexabuild.utils.Utils
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.zfdang.multiple_images_selector.ImagesSelectorActivity
import com.zfdang.multiple_images_selector.SelectorSettings

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SiteVisiting : Fragment(), LocationCallback {

    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var binding: SiteVisitingFragmentBinding? = null
    val REQUEST_IMAGE = 100


    var siteLocation: SiteLocation? = null
    var lat: Double = 0.0
    var lng: Double = 0.0
    var filename: String? = null
    var cal = Calendar.getInstance()
    val REQUEST_CODE = 200

    //    var request: Locationinput? = null
    var mResults: ArrayList<String?>? = ArrayList()

    companion object {
        fun newInstance() = SiteVisiting()
    }

    private lateinit var viewModel: SiteVisitingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.site_visiting_fragment,
                container,
                false
            )
        (activity as AppCompatActivity).supportActionBar?.title = "Site Visit"
        binding?.fragment = this
        setHasOptionsMenu(true)


        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())


//        request = Locationinput()


        siteLocation = SiteLocation(requireContext(), this)
        return binding?.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SiteVisitingViewModel::class.java)

//        binding?.location?.setOnClickListener {
//            siteLocation?.locationPermissions(requireActivity())
//            siteLocation?.statusCheck(requireActivity())
//
//        }

        binding?.current
            ?.setOnCheckedChangeListener { buttonView, isChecked ->
                run {

                    if (isChecked) {
                        siteLocation?.locationPermissions(requireActivity())
                        siteLocation?.statusCheck(requireActivity())
                    }
                }
            }


        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }


        binding?.referalDate!!.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                dateSetListener,
                // set DatePickerDialog to point to today's date when it loads up
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        binding?.captureImage?.setOnClickListener {

            onProfileImageClick()
        }

//        binding?.Save?.setOnClickListener {
//
//            sendAllDataToAPI()
//        }


        viewModel.response.observe(viewLifecycleOwner, {
            Utils.closeProgressBar()


            if (it.isSuccessful) {
                val response: String = it.body()!!

                if (response.equals("true")) {
                    Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show()


                    binding?.mobileEditText?.getText()?.clear()
                    binding?.usernameEditText?.getText()?.clear()
                    binding?.address?.getText()?.clear()
                    binding?.referalDate?.text = ""
                    binding?.current?.isChecked= false
                    filename=null
                    lat=0.0
                    lng=0.0


                } else {
                    Toast.makeText(getActivity(), "Something went  wrong", Toast.LENGTH_SHORT)
                        .show()

                }


            } else {
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()

            }
        })

        viewModel.image.observe(viewLifecycleOwner, {
            Utils.closeProgressBar()
            if (it.isSuccessful) {
                val response: ArrayList<String> = it.body()!!
                val fienameList: ArrayList<String> = ArrayList()
                val files: StringBuilder = java.lang.StringBuilder()
                for (i in 0..response.size) {
                    if (i % 2 == 1) {
                        fienameList.add(response[i])
                        if (i == response.size - 1)
                            files.append(response[i])
                        else
                            files.append(response[i]).append(",")

                    }
                }

                filename = files.toString()

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


    }


    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding?.referalDate!!.text = sdf.format(cal.getTime())
    }

    fun saveData(view: View) {


        view.hideKeyboard()


        if (!TextUtils.isEmpty(binding?.usernameEditText?.getText().toString().trim())) {
            if (!TextUtils.isEmpty(binding?.mobileEditText?.getText()?.trim())) {
//                    if (!TextUtils.isEmpty(binding?.panEditText?.getText())) {
                if (!TextUtils.isEmpty(binding?.address?.getText().toString().trim())) {

                    if (!TextUtils.isEmpty(
                            binding?.referalDate?.getText().toString().trim()
                        )
                    ) {

                        if (!(binding?.mobileEditText?.text.toString().trim().length < 10)) {

                            if (!(binding?.usernameEditText?.text.toString().trim().length < 2)) {
                                if (filename != null) {

                                    if (lat != 0.0 && lng != 0.0) {
                                        sendAllDataToAPI()

                                    } else {
                                        Toast.makeText(
                                            activity,
                                            "Share location",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                } else {
                                    Toast.makeText(activity, "Upload Images", Toast.LENGTH_SHORT)
                                        .show()
                                }

                            } else {
                                Toast.makeText(activity, "Enter valid Name", Toast.LENGTH_SHORT)
                                    .show()
                            }
//
//
                        } else {
                            Toast.makeText(
                                activity,
                                "Enter valid mobile number",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(activity, "Select Date", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Toast.makeText(activity, "Enter Address", Toast.LENGTH_SHORT).show()
                }


            } else {
                Toast.makeText(activity, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "Enter Name", Toast.LENGTH_SHORT).show();
        }
    }


    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun sendAllDataToAPI() {
        val createdid: String = SharedPref(requireContext()).getCreatedbyId().toString()
//        val introducedid: String = SharedPref(requireContext()).getIntrocudedId().toString()

        val request = SiteVisitRequest(

            latitude = lat.toString(),

            longitude = lng.toString(),


            imagepath = filename.toString(),
            customername = binding?.usernameEditText?.text.toString(),
            contactno = binding?.mobileEditText?.text.toString(),
            address = binding?.address?.text.toString(),

            createdby = createdid,
            transdate = binding?.referalDate?.text.toString()
        )
        viewModel.saveData(request)
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
//    private fun launchGalleryIntent() {
//        val intent = Intent(activity, ImagePickerActivity::class.java)
//
//
//        intent.putExtra(
//            ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION,
//            ImagePickerActivity.REQUEST_GALLERY_IMAGE
//        )
//
////
////        // setting type to select to be image
//        intent.setType("image/*");
//
//
////         setting aspect ratio
//        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true)
//        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
//        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1)
//        startActivityForResult(intent, REQUEST_IMAGE)
//
//    }

    private fun launchGalleryIntent() {
        // start multiple photos selector
        val intent = Intent(activity, ImagesSelectorActivity::class.java)
// max number of images to be selected
//        intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 5)
// min size of image which will be shown; to filter tiny images (mainly icons)
        intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100000)
// show camera or not
        intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true)
// pass current selected images as the initial value
        intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults)
// start the selector
        startActivityForResult(intent, REQUEST_CODE)
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode === REQUEST_IMAGE) {
//            if (resultCode === RESULT_OK) {
//                val uri: Uri? = data!!.getParcelableExtra("path")
//                try {
//
//                    //    val uri = data.data
//                    val selectedFilePath = FilePath.getPath1(requireContext(), uri!!)
//                    val file = File(selectedFilePath)
//
//                    binding?.captureImage?.setImageURI(uri)
////
//                    val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
//                        "file", file.getName(),
//                        RequestBody.create("image/*".toMediaTypeOrNull(), file)
//                    )
////
////                    Log.e("hell",file.toString())
//                    viewModel.imageUpload(filePart)
//                    // You can update this bitmap to your server
////                    val bitmap = MediaStore.Images.Media.getBitmap(
////                        requireContext().contentResolver,
////                        uri
////                    )
////
////                    // loading profile image from local cache
////                    binding!!.profiePic.setImageBitmap(bitmap)
////                   imageBase64String = convert(bitmap)
//                } catch (e: IOException) {
//                    e.printStackTrace()
//                }
//            }
//        }    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // get selected images from selector
        if (resultCode === RESULT_OK) {
            if (requestCode === REQUEST_CODE) {
                mResults = data!!.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS)
            } else if (requestCode === REQUEST_IMAGE) {
                //    mResults = data!!.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS)

                val uri: Uri? = data!!.getParcelableExtra("path")

                //    val uri = data.data
                val selectedFilePath = FilePath.getPath1(requireContext(), uri!!)
                val file = File(selectedFilePath)

                mResults?.add(selectedFilePath)

            }
            createMultipartSend()

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun createMultipartSend() {
        val parts: ArrayList<MultipartBody.Part> = ArrayList()
        for (i in 0 until mResults!!.size) {

            val file = File(mResults?.get(i))

            val filePart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "file", file.getName(),
                RequestBody.create("image/*".toMediaTypeOrNull(), file)
            )

            parts.add(filePart)
        }


        viewModel.imageUpload(parts)
    }


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


    override fun onNewLocationAvailable(location: Location?) {
        if (location != null) {
            lat = location.latitude
            lng = location.longitude
//            binding?.lat?.text = lat.toString()

        }else{
            binding?.current?.isChecked= false

        }

        if (lat != 0.0 && lng != 0.0) {
            siteLocation?.removeLocation()
        }
    }

}