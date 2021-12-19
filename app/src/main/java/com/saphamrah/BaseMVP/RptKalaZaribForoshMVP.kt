package com.saphamrah.BaseMVP

import com.saphamrah.Model.MoshtaryGharardadModel
import com.saphamrah.Model.MoshtaryModel
import com.saphamrah.UIModel.RptMoshtaryGharardadUiModel
import com.saphamrah.UIModel.kalaZaribForosh.KalaZaribForoshUiModel
import java.util.*

interface RptKalaZaribForoshMVP {

    interface RequiredViewOps {
        fun onGetMoshtary(moshtaryModels: ArrayList<MoshtaryModel>, title: ArrayList<String>)
        fun onGetKala(models : ArrayList<KalaZaribForoshUiModel>, noeSenf :String , noeMoshtary:String)
        fun showToast(resId: Int, messageType: Int, duration: Int)
    }



    interface PresenterOps {
        fun onConfigurationChanged(view: RequiredViewOps?)
        fun getMoshtary()
        fun getKala(model : MoshtaryModel)
    }


    interface RequiredPresenterOps {
        fun onGetMoshtary(moshtaryModels: ArrayList<MoshtaryModel>, title: ArrayList<String>)
        fun onGetKala(models : ArrayList<KalaZaribForoshUiModel> , noeSenf :String , noeMoshtary:String)
        fun onError(resId: Int, isFinish: Boolean)
    }

    interface ModelOps {
        fun getMoshtary()
        fun getKala(model : MoshtaryModel)
    }
}