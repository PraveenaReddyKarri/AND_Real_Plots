package com.grgroup.hexabuild.venturelist

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.grgroup.hexabuild.R
import com.google.gson.annotations.SerializedName

data class VentureListResponse(

    @SerializedName("layoutid") var layoutid : Int,
    @SerializedName("itemtypeID") var itemtypeID : Int,
    @SerializedName("landbankid") var landbankid : Int,
    @SerializedName("landname") var landname : String,
    @SerializedName("landpurchasetype") var landpurchasetype : String,
    @SerializedName("launchdate") var launchdate : String,
    @SerializedName("layoutname") var layoutname : String,
    @SerializedName("permitno") var permitno : String,
    @SerializedName("rerAno") var rerAno : String,
    @SerializedName("primaryprojectarea") var primaryprojectarea : Int,
    @SerializedName("primaryprojectareauomid") var primaryprojectareauomid : String,
    @SerializedName("primaryprojectareauomname") var primaryprojectareauomname : String,
    @SerializedName("secondaryprojectarea") var secondaryprojectarea : Double,
    @SerializedName("secondaryprojectareauomid") var secondaryprojectareauomid : String,
    @SerializedName("standardunitarea") var standardunitarea : Double,
    @SerializedName("standardunitareauomid") var standardunitareauomid : Int,
    @SerializedName("secondaryprojectareauomname") var secondaryprojectareauomname : String,
    @SerializedName("standardrate") var standardrate : Double,
    @SerializedName("standardrateuomid") var standardrateuomid : Int,
    @SerializedName("totalnoofplotunits") var totalnoofplotunits : Int,
    @SerializedName("pbranchname") var pbranchname : String,
    @SerializedName("pBranchId") var pBranchId : Int,
    @SerializedName("layoutareainAcres") var layoutareainAcres : Double,
    @SerializedName("layoutareainsquareYards") var layoutareainsquareYards : Double,
    @SerializedName("minbookingamount") var minbookingamount : String,
    @SerializedName("plotno") var plotno : Int,
    @SerializedName("plotbookingstatus") var plotbookingstatus : Boolean,
    @SerializedName("chargestypesdetailsDto") var chargestypesdetailsDto : String,
    @SerializedName("extratypesdetailsDto") var extratypesdetailsDto : String,
    @SerializedName("createdby") var createdby : Int,
    @SerializedName("modifiedby") var modifiedby : Int,
    @SerializedName("pCreatedby") var pCreatedby : Int,
    @SerializedName("pModifiedby") var pModifiedby : Int,
    @SerializedName("pStatusid") var pStatusid : String,
    @SerializedName("pStatusname") var pStatusname : String,
    @SerializedName("pEffectfromdate") var pEffectfromdate : String,
    @SerializedName("pEffecttodate") var pEffecttodate : String,
    @SerializedName("ptypeofoperation") var ptypeofoperation : String,
    @SerializedName("projectid") var projectid : Int,
    @SerializedName("priority") var priority : String,
    @SerializedName("status") var status : String

){

    fun gotoLayoutChart(view: View){

        val bundle = Bundle()
        bundle.putString("id", layoutid.toString())
        bundle.putString("name", layoutname)

        Navigation.findNavController(view).navigate(R.id.action_plotsListFragment_to_layoutChartFragment,bundle)
    }
}


