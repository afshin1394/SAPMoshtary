package com.saphamrah.MVP.View

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.saphamrah.Adapter.RptKalaZaribForoshAdapter
import com.saphamrah.Application.BaseApplication
import com.saphamrah.BaseMVP.RptKalaZaribForoshMVP
import com.saphamrah.CustomView.CustomSpinner
import com.saphamrah.MVP.Presenter.RptKalaZaribForoshPresenter
import com.saphamrah.Model.MoshtaryModel
import com.saphamrah.PubFunc.PubFunc
import com.saphamrah.R
import com.saphamrah.UIModel.kalaZaribForosh.KalaZaribForoshUiModel
import com.saphamrah.Utils.CustomAlertDialog
import com.saphamrah.Utils.CustomSpinnerResponse
import kotlinx.android.synthetic.main.btmsht_rpt_kala_zarib_forosh.*
import kotlinx.android.synthetic.main.rpt_kala_zarib_forosh.*
import me.anwarshahriar.calligrapher.Calligrapher
import java.util.ArrayList


class RptKalaZaribForoshActivity : AppCompatActivity(), RptKalaZaribForoshMVP.RequiredViewOps {
    private lateinit var customSpinner: CustomSpinner
    private lateinit var customAlertDialog: CustomAlertDialog
    private lateinit var mPresenter: RptKalaZaribForoshPresenter
    private var bottomSheetBehavior: BottomSheetBehavior<*>? = null
    private lateinit var moshtaryTitle: ArrayList<String>
    private var modelsMoshtary = ArrayList<MoshtaryModel>()
    private var modelsSreachKala = ArrayList<KalaZaribForoshUiModel>()
    private var modelsResponceKala = ArrayList<KalaZaribForoshUiModel>()
    private var selectedPosition = 0
    private lateinit var adapter: RptKalaZaribForoshAdapter
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rpt_kala_zarib_forosh)
        customAlertDialog = CustomAlertDialog(this@RptKalaZaribForoshActivity)
        customSpinner = CustomSpinner()
        val calligrapher = Calligrapher(this@RptKalaZaribForoshActivity)
        calligrapher.setFont(
            this@RptKalaZaribForoshActivity,
            resources.getString(R.string.fontPath),
            true
        )
        mPresenter = RptKalaZaribForoshPresenter(this@RptKalaZaribForoshActivity)
        bottomSheetBehavior = BottomSheetBehavior.from(lnrlayRoot)
        mPresenter.getMoshtary()
        clickListener()
    }

    override fun onGetMoshtary(moshtaryModels: ArrayList<MoshtaryModel>, title: ArrayList<String>) {
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        moshtaryTitle = title
        modelsMoshtary = moshtaryModels
    }

    @SuppressLint("SetTextI18n")
    override fun onGetKala(models: ArrayList<KalaZaribForoshUiModel>, noeSenf :String, noeMoshtary:String) {
        lblCustomerNoeSenf.text = noeMoshtary + " - " + noeSenf
        setupRecycler(models)
    }

    override fun showToast(resId: Int, messageType: Int, duration: Int) {
        TODO("Not yet implemented")
    }

    private fun setupRecycler(models: ArrayList<KalaZaribForoshUiModel>){
        modelsResponceKala.addAll(models)
        modelsSreachKala.addAll(models)
        adapter = RptKalaZaribForoshAdapter(this@RptKalaZaribForoshActivity ,modelsSreachKala )
        recyclerView.layoutManager = LinearLayoutManager(BaseApplication.getContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(DividerItemDecoration(BaseApplication.getContext(), 0))
        recyclerView.adapter = adapter
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun clickListener() {
        txtMoshtary.setOnFocusChangeListener { v: View?, hasFocus: Boolean ->
            changeDrawableLeftTint(txtMoshtary, hasFocus)
            if (hasFocus) {
                openMoshtarySpinner()
            }
        }
        txtMoshtary.setOnClickListener {
            openMoshtarySpinner()
        }
        btnApply.setOnClickListener { selectMoshtary(true) }

        fabChangeCustomer.setOnClickListener{
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
            fabMenu.close(true)
        }

        fabFilter.setOnClickListener{
            searchKala()
        }
    }

    private fun searchKala() {
        linTopLevel.visibility = View.VISIBLE
        fabMenu.close(true)
        searchKalaName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                adapter.notifyDataSetChanged()
                if (s.isNotEmpty()) {
                    val searchWord = PubFunc().LanguageUtil().convertFaNumberToEN(s.toString())
                    modelsSreachKala.clear()
                    adapter.notifyDataSetChanged()
                    for (i in 0 until modelsResponceKala.size) {
                        val nameKala = PubFunc().LanguageUtil().convertFaNumberToEN(
                            modelsResponceKala[i].NameKala + modelsResponceKala[i].CodeKala
                        )
                        if (nameKala.contains(searchWord)) {
                            modelsSreachKala.add(modelsResponceKala[i])
                            adapter.notifyDataSetChanged()
                        }
                    }

                } else {
                    modelsSreachKala.clear()
                    modelsSreachKala.addAll(modelsResponceKala)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    @SuppressLint("SetTextI18n")
    private fun selectMoshtary(isVisibility: Boolean) {
        if (modelsMoshtary.size > 0) {
            if (isVisibility) {
                lblCustomerDarajeh.text = modelsMoshtary[selectedPosition].nameDarajeh
                lblCustomerFullNameCode.text = modelsMoshtary[selectedPosition].nameMoshtary + " - " + modelsMoshtary[selectedPosition].codeMoshtary
                txtMoshtary.setText(moshtaryTitle[selectedPosition])
                layTitleCustomer.visibility = View.VISIBLE
                mPresenter.getKala(modelsMoshtary[selectedPosition])
            } else {
                layTitleCustomer.visibility = View.GONE
                recyclerView.adapter = null
                linTopLevel.visibility = View.GONE
            }
        }
    }

    private fun openMoshtarySpinner() {
        customSpinner.showSpinner(this, moshtaryTitle, object : CustomSpinnerResponse {
            override fun onApplySingleSelection(selectedIndex: Int) {
                selectMoshtary(false)
                txtMoshtary.setText(moshtaryTitle[selectedIndex])
                selectedPosition = selectedIndex
                searchKalaName.text.clear()
            }
            override fun onApplyMultiSelection(selectedIndexes: ArrayList<Int>?) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun changeDrawableLeftTint(editText: EditText, hasFocus: Boolean) {
        if (hasFocus) {
            try {
                val unwrappedDrawable =
                    AppCompatResources.getDrawable(this, R.drawable.ic_arrow_down)
                val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
                DrawableCompat.setTint(
                    wrappedDrawable,
                    resources.getColor(R.color.colorTextPrimary)
                )
                editText.setCompoundDrawablesWithIntrinsicBounds(
                    wrappedDrawable, null,
                    editText.compoundDrawables[2], null
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        } else {
            try {
                val unwrappedDrawable =
                    AppCompatResources.getDrawable(this, R.drawable.ic_arrow_down)
                val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
                DrawableCompat.setTint(
                    wrappedDrawable,
                    resources.getColor(R.color.colorTextPrimary)
                )
                editText.setCompoundDrawablesWithIntrinsicBounds(
                    resources.getDrawable(R.drawable.ic_arrow_down), null,
                    editText.compoundDrawables[2], null
                )
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }
}
