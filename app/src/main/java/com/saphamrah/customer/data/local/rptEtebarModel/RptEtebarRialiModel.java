package com.saphamrah.customer.data.local.rptEtebarModel;


import java.text.DecimalFormat;

public class RptEtebarRialiModel extends RptEtebarParentModel {
    private long mEtebarRiali;
    private long mRialMasrafShode;
    private DecimalFormat formatter = new DecimalFormat("#,###,###");

    public RptEtebarRialiModel(long etebarRiali, long rialMasrafShode, RptEtebarType rptEtebarType) {
        super();
        mtebarType = EtebarType.Riali;
        mEtebarRiali = etebarRiali;
        mRialMasrafShode = rialMasrafShode;
        this.rptEtebarType = rptEtebarType;
    }

    public long getEtebarRiali() {
        return mEtebarRiali;
    }

    public long getRialMasrafShode() {
        return mRialMasrafShode;
    }


    public long getEtebar() {
        return mEtebarRiali;
    }


    public long getMasrafShode() {
        return mRialMasrafShode;
    }


    public long getMande() {
        return mEtebarRiali - mRialMasrafShode;
    }

    @Override
    public boolean isMandeLessThanZero() {
        return getMande()<=0 ;
    }

    @Override
    public String getMandeAbsuluteValue() {
        if((mEtebarRiali - mRialMasrafShode) == 0)
            return  "0";
        return  formatter.format(Math.abs(mEtebarRiali - mRialMasrafShode))+" - ";
    }
}
