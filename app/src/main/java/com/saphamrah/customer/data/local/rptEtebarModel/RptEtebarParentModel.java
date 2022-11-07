package com.saphamrah.customer.data.local.rptEtebarModel;

public abstract class RptEtebarParentModel {
    protected String mEtebarTitle;
    protected EtebarType mtebarType;
    protected RptEtebarType rptEtebarType;

    public EtebarType getEtebarType() {
        return mtebarType;
    }

    public void setEtebarType(EtebarType mEtebarType) {
        this.mtebarType = mEtebarType;
    }

    public abstract boolean isMandeLessThanZero();

    public abstract String getMandeAbsuluteValue();

    public RptEtebarType getRptEtebarType() {
        return rptEtebarType;
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
