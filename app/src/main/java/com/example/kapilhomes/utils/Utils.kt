package com.example.kapilhomes.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import com.example.kapilhomes.R
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Utils {
    private var alertDialog: AlertDialog? = null

    fun formatAmout(amount: String?): String? {
            return try {
                if (amount == null || amount.trim { it <= ' ' }.isEmpty()) {
                    "\u20B9 " + " 0"
                } else {
                    val S_amount = amount.replace(".00", "")
                    "\u20B9 " + format(S_amount)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("exceptions in amount", "exceptions" + e.message)
                ""
            }
        }

        private fun format(value1: String): String? {
            val value = value1.replace(",", "").toDouble()
            return if (value < 1000) {
                format("###", value)
            } else {
                val hundreds = value % 1000
                val other = (value / 1000).toInt()
                format(
                    ",##",
                    other
                ) + ',' +format("000", hundreds)
            }
        }
        private fun format(pattern: String, value: Any): String? {
            return DecimalFormat(pattern).format(value)
        }


    fun showProgressBar(context: Context?) {
        val builder = AlertDialog.Builder(
            context!!
        )
        builder.setView(R.layout.progressbar_layout)
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog!!.show()
    }
    fun closeProgressBar() {
        if (alertDialog != null){

            alertDialog!!.dismiss()

        }
    }

//    fun LogoutDialog(view: View) {
//        AlertDialog.Builder(view.context)
//            .setTitle("Logout")
//            .setMessage("Do you want to logout?")
//            .setPositiveButton(
//                "Logout"
//            ) { dialog: DialogInterface?, which: Int -> logout(view) }
//            .setNegativeButton(
//                "Cancel"
//            ) { dialog: DialogInterface, which: Int -> dialog.dismiss() }
//            .create().show()
//    }


    fun getformatDate(): String? {
        val formatter = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        return formatter.format(Date())
    }
    fun getDate() {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormat = SimpleDateFormat("dd-MMM-yyyy")
        val date = inputFormat.parse("2021-10-27T00:00:00")
        val formattedDate = outputFormat.format(date)
        println(formattedDate)
    }

    fun getDate(pattern: String?): String? {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(Date())
    }
    fun DateFormat(Date: String?): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return try {
            val date = dateFormat.parse(Date)
            val outputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
            assert(date != null)
            outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            Date
        }
    }

    fun dateFormat(Date: String?): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return try {
            val date = dateFormat.parse(Date)
            val outputFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.US)
            assert(date != null)
            outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
            Date
        }
    }

    fun datetimeFormat(Date: String?): String? {

        if (Date!=null){
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val time = Date?.replace("T", " ")
            return try {
                val date = dateFormat.parse(time)
                val outputFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.US)
                assert(date != null)
                outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
                Date
            }
        }
        return null
    }

    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}


