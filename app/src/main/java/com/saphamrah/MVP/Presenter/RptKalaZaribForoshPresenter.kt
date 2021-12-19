package com.saphamrah.MVP.Presenter

import com.saphamrah.BaseMVP.RptKalaZaribForoshMVP
import com.saphamrah.BaseMVP.RptMarjoeeMVP
import com.saphamrah.BaseMVP.RptMoshtaryGharardadMVP
import com.saphamrah.MVP.Model.RptKalaZaribForoshModel
import com.saphamrah.MVP.Model.RptMoshtaryGharardadModel
import com.saphamrah.Model.MoshtaryModel
import com.saphamrah.UIModel.kalaZaribForosh.KalaZaribForoshUiModel
import java.lang.ref.WeakReference
import java.util.ArrayList

class RptKalaZaribForoshPresenter(viewOps: RptKalaZaribForoshMVP.RequiredViewOps) : RptKalaZaribForoshMVP.PresenterOps , RptKalaZaribForoshMVP.RequiredPresenterOps{
    private  var mView: WeakReference<RptKalaZaribForoshMVP.RequiredViewOps>
    private  var mModel : RptKalaZaribForoshMVP.ModelOps

    init {
        mView = WeakReference(viewOps)
        mModel = RptKalaZaribForoshModel(this)
    }


    override fun onConfigurationChanged(view: RptKalaZaribForoshMVP.RequiredViewOps?) {
        mView = WeakReference<RptKalaZaribForoshMVP.RequiredViewOps>(view)
    }

    override fun getMoshtary() {
        mModel.getMoshtary()
    }

    override fun getKala(model: MoshtaryModel) {
        mModel.getKala(model)
    }

    override fun onGetMoshtary(
        moshtaryModels: ArrayList<MoshtaryModel>,
        title: ArrayList<String>) {
        mView.get()?.onGetMoshtary(moshtaryModels, title)
    }

    override fun onGetKala(models: ArrayList<KalaZaribForoshUiModel>, noeSenf :String , noeMoshtary:String) {
        mView.get()?.onGetKala(models,noeSenf,noeMoshtary)
    }

    override fun onError(resId: Int, isFinish: Boolean) {
        TODO("Not yet implemented")
    }
}