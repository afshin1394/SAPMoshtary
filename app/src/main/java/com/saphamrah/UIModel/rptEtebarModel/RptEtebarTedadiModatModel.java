package com.saphamrah.UIModel.rptEtebarModel;

import android.content.Context;

import com.saphamrah.R;

import java.text.DecimalFormat;

/**
 * This class is defined for both etebar tedadi and emodat etebar
 */
public class RptEtebarTedadiModatModel extends RptEtebarParentModel {
    private int mEtebar;
    private int mMasrafShode;
    private DecimalFormat formatter = new DecimalFormat("#,###,###");

    public RptEtebarTedadiModatModel(int etebar, int masrafShode, RptEtebarType rptEtebarType , EtebarType etebarType) {
        mRptEtebarType = rptEtebarType;
        mEtebarType = etebarType;
        this.mEtebar = etebar;
        this.mMasrafShode = masrafShode;

    }

    public int getEtebar() {
        return mEtebar;
    }

    public int getMasrafShode() {
        return mMasrafShode;
    }

    public int getMande() {
        return mEtebar - mMasrafShode;
    }

    @Override
    public boolean isMandeLessThanZero() {
        return getMande()<=0 ;
    }

    @Override
    public String getMandeAbsuluteValue() {
        if(mEtebar - mMasrafShode==0)
            return "0";
        return formatter.format( Math.abs(mEtebar - mMasrafShode))+" - ";
    }

}
