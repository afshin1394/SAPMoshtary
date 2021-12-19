package com.saphamrah.MVP.Model

import android.util.Log
import com.saphamrah.Application.BaseApplication
import com.saphamrah.BaseMVP.RptKalaZaribForoshMVP
import com.saphamrah.DAO.GorohDAO
import com.saphamrah.DAO.KalaZaribForoshDAO
import com.saphamrah.DAO.MoshtaryDAO
import com.saphamrah.Model.MoshtaryModel
import java.util.ArrayList


class RptKalaZaribForoshModel(mp : RptKalaZaribForoshMVP.RequiredPresenterOps) : RptKalaZaribForoshMVP.ModelOps{
    private var mPresenter: RptKalaZaribForoshMVP.RequiredPresenterOps = mp
    private var moshtaryDAO = MoshtaryDAO(BaseApplication.getContext())
    private var kalaZaribForoshDao = KalaZaribForoshDAO(BaseApplication.getContext())
    private var gorohDAO = GorohDAO(BaseApplication.getContext())


    override fun getMoshtary() {
       val moshtarys =  moshtaryDAO.all
        var titles = ArrayList<String>()
        if (moshtarys.size > 0){
            for (model in moshtarys) {
                titles.add(model.nameMoshtary)
            }
        }

        mPresenter.onGetMoshtary(moshtarys, titles)
    }

    override fun getKala(model: MoshtaryModel) {
      val kala =   kalaZaribForoshDao.getKalaZaribForoshKala(model.darajeh , model.ccNoeMoshtary)
        val noeSenf: String = gorohDAO.getByccGoroh(model.ccNoeSenf)[0].nameGoroh
        val noeMoshtary: String = gorohDAO.getByccGoroh(model.ccNoeMoshtary)[0].nameGoroh
        mPresenter.onGetKala(kala , noeSenf , noeMoshtary)

    }
}