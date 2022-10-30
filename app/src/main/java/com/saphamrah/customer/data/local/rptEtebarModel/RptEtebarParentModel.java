package com.saphamrah.customer.data.local.rptEtebarModel;

public abstract class RptEtebarParentModel {
    protected String mEtebarTitle;
    protected EtebarType mEtebarType;
    protected RptEtebarType mRptEtebarType;

    public EtebarType getEtebarType() {
        return mEtebarType;
    }

    public void setEtebarType(EtebarType mEtebarType) {
        this.mEtebarType = mEtebarType;
    }

    public abstract boolean isMandeLessThanZero();

    public abstract String getMandeAbsuluteValue();

    public RptEtebarType getRptEtebarType() {
        return mRptEtebarType;
    }

    //    public  String getTitleString(Context context){
//        String title = "";
//        switch (mRptEtebarType){
//            case Saghf:
//                title = context.getResources().getString(R.string.saghfEtebar);
//                break;
//            case Moavagh:
//                title = context.getResources().getString(R.string.etebarMoavagh);
//                break;
//            case Asnad:
//                title = context.getResources().getString(R.string.etebarAsnad);
//                break;
//            case AsnadBargashti:
//                title = context.getResources().getString(R.string.etebarBargashti);
//                break;
//
//        }
//        return title;
//    }
}
