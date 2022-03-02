package com.grgroup.hexabuild.sitevisit

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.text.TextUtils
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.DatePicker
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
import com.grgroup.hexabuild.newreferal.*
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
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.xml.datatype.DatatypeConstants.MONTHS
import kotlin.collections.ArrayList


class SiteVisiting : Fragment() ,LocationCallback{

    var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var binding: SiteVisitingFragmentBinding? = null
    val REQUEST_IMAGE = 100


    var siteLocation: SiteLocation? = null
    var lat:Double = 0.0
    var lng:Double = 0.0
    var imageBase64String: String? = null
    var filename: String? = null
    var cal = Calendar.getInstance()

//    var request: Locationinput? = null

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


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())


//        request = Locationinput()


        siteLocation = SiteLocation(requireContext(), this)
        return binding?.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SiteVisitingViewModel::class.java)

        binding?.location?.setOnClickListener {
            siteLocation?.locationPermissions(requireActivity())
            siteLocation?.statusCheck(requireActivity())

        }




        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }


        binding?.confirmPayChequeDate!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    context!!,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
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
                val response:String= it.body()!!

                if (response.equals("true")) {
                    Toast.makeText(getActivity(), "Saved Successfully", Toast.LENGTH_SHORT).show()



//                    binding?.mobileEditText?.getText()?.clear()
//                    binding?.surnameEditText?.getText()?.clear()
//                    binding?.usernameEditText?.getText()?.clear()
//                    binding?.aadharEditText?.getText()?.clear()
//                    binding?.panEditText?.getText()?.clear()
//                    //                                    binding.editTextTicketNo.setText("");
//                    binding?.titlespinner?.setSelection(0)


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
                filename=response[1]

//                if (response.equals("0")) {
//                    sendAllDataToAPI()

                Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show()

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
        binding?.confirmPayChequeDate!!.text = sdf.format(cal.getTime())
    }

    fun saveData(view: View) {


        view.hideKeyboard()

        if (!TextUtils.isEmpty(binding?.address?.getText().toString().trim())) {

            if (!TextUtils.isEmpty(binding?.usernameEditText?.getText().toString().trim())) {
                if (!TextUtils.isEmpty(binding?.mobileEditText?.getText()?.trim())) {
//                    if (!TextUtils.isEmpty(binding?.panEditText?.getText())) {
                    if (!TextUtils.isEmpty(binding?.confirmPayChequeDate?.getText().toString().trim())) {

                    if (!(binding?.mobileEditText?.text.toString().trim().length < 10)) {

                        if (!(binding?.usernameEditText?.text.toString().trim().length < 2)) {

                            sendAllDataToAPI()
                        } else {



                            Toast.makeText(activity, "Enter valid Name", Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(activity, "Enter Mobile Number", Toast.LENGTH_SHORT).show()
                }


            } else {
                Toast.makeText(activity, "Enter Name", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(activity, "Enter Adress", Toast.LENGTH_SHORT).show();
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

            longitude =lng.toString(),


            imagepath= filename.toString(),
            customername = binding?.usernameEditText?.text.toString(),
            contactno = binding?.mobileEditText?.text.toString(),
            address = binding?.address?.text.toString(),

createdby =createdid,
            transdate=binding?.confirmPayChequeDate?.text.toString()
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
        ImagePickerActivity.showImagePickerOptions(requireContext(), object :
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
                    val selectedFilePath = FilePath.getPath1(requireContext(), uri!!)
                    val file = File(selectedFilePath)

                    binding?.captureImage?.setImageURI(uri)
//
                    val filePart: MultipartBody.Part = MultipartBody.Part.createFormData("file", file.getName(),
                        RequestBody.create("image/*".toMediaTypeOrNull(), file));
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






    override fun onNewLocationAvailable(location: Location?) {
        if (location != null) {
            lat = location.latitude
            binding?.lat?.text=lat.toString()

        }
        if (location != null) {
            lng = location.longitude
            binding?.lang?.text=lng.toString()

        }
        if (lat != 0.0 && lng != 0.0) {
            siteLocation?.removeLocation()
        }    }

}