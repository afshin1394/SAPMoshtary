package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.AddCustomerAddressMVP;
import com.saphamrah.MVP.Model.AddCustomerAddressModel;
import com.saphamrah.Model.AddCustomerInfoModel;
import com.saphamrah.Model.MahalCodePostiModel;
import com.saphamrah.Model.MahalModel;
import com.saphamrah.Model.MoshtaryAddressModel;
import com.saphamrah.Model.PolygonForoshSatrModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class AddCustomerAddressPresenter implements AddCustomerAddressMVP.PresenterOps , AddCustomerAddressMVP.RequiredPresenterOps
{

    private WeakReference<AddCustomerAddressMVP.RequiredViewOps> mView;
    private AddCustomerAddressMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public AddCustomerAddressPresenter(AddCustomerAddressMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new AddCustomerAddressModel(this);
    }

    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(AddCustomerAddressMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

	@Override
    public void getConfig()
    {
        mModel.getConfig();
    }
	
    @Override
    public void getOstanItems()
    {
        mModel.getOstanItems();
    }

    @Override
    public void getShahrestanItems(int ostanId)
    {
        if (ostanId > 0)
        {
            mModel.getShahrestanItems(ostanId);
        }
        else
        {
            mView.get().showResourceError(false, R.string.error, R.string.errorOstan, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void getBakhshItems(int shahrestanId)
    {
        if (shahrestanId > 0)
        {
            mModel.getBakhshItems(shahrestanId);
        }
        else
        {
            mView.get().showResourceError(false, R.string.error, R.string.errorShahrestan, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void getShahrItems(int bakhshId)
    {
        if (bakhshId > 0)
        {
            mModel.getShahrItems(bakhshId);
        }
        else
        {
            mView.get().showResourceError(false, R.string.error, R.string.errorShahr, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void getMantagheItems(int shahrId)
    {
        if (shahrId > 0)
        {
            mModel.getMantagheItems(shahrId);
        }
        else
        {
            mView.get().showResourceError(false, R.string.error, R.string.errorShahr, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void getNoeAddressItems()
    {
        mModel.getNoeAddressItems();
    }

    @Override
    public void getSellPolygon()
    {
        mModel.getSellPolygon();
    }

    @Override
    public void checkNewAddress(MoshtaryAddressModel moshtaryAddressModel , ArrayList<MoshtaryAddressModel> moshtaryAddressModels, boolean requireTelephone, boolean requireCodePosti, ArrayList<MahalCodePostiModel> mahalCodePostiModelArrayList)
    {
        if (moshtaryAddressModel != null)
        {
            boolean validData = true;

            int countDarkhastAddress = 0;
            int countDarkhastTahvilAddress = 0;
            int countTahvilAddress = 0;

            for (MoshtaryAddressModel address : moshtaryAddressModels)
            {
                if (address != null && address.getCcNoeAddress() > 0)
                {
                    if (address.getCcNoeAddress() == Constants.ADDRESS_TYPE_DARKHAST())
                    {
                        countDarkhastAddress++;
                    }
                    if (address.getCcNoeAddress() == Constants.ADDRESS_TYPE_DARKHAST_TAHVIL())
                    {
                        countDarkhastTahvilAddress++;
                    }
                    if (address.getCcNoeAddress() == Constants.ADDRESS_TYPE_TAHVIL())
                    {
                        countTahvilAddress++;
                    }
                }
            }

            if (countDarkhastTahvilAddress > 0)
            {
                mView.get().onErrorExistDarkhastTahvil();
                validData = false;
            }
            if (countDarkhastAddress > 0 && countTahvilAddress > 0)
            {
                mView.get().onErrorExistDarkhastTahvil();
                validData = false;
            }
            else
            {
                if (countDarkhastAddress > 0 && moshtaryAddressModel.getCcNoeAddress() != Constants.ADDRESS_TYPE_TAHVIL())
                {
                    mView.get().onErrorExistDarkhast();
                    validData = false;
                }
                if (countTahvilAddress > 0 && moshtaryAddressModel.getCcNoeAddress() != Constants.ADDRESS_TYPE_DARKHAST())
                {
                    mView.get().onErrorExistTahvil();
                    validData = false;
                }
            }

            if (validData)
            {
                if (moshtaryAddressModel.getOstanId() <= 0 || moshtaryAddressModel.getOstanName() == null || moshtaryAddressModel.getOstanName().trim().equals(""))
                {
                    validData = false;
                    mView.get().onErrorOstan();
                }
                if (moshtaryAddressModel.getShahrestanId() <= 0 || moshtaryAddressModel.getShahrestanName() == null || moshtaryAddressModel.getShahrestanName().trim().equals(""))
                {
                    validData = false;
                    mView.get().onErrorShahrestan();
                }
                if (moshtaryAddressModel.getBakhshId() <= 0 || moshtaryAddressModel.getBakhshName() == null || moshtaryAddressModel.getBakhshName().trim().equals(""))
                {
                    validData = false;
                    mView.get().onErrorBakhsh();
                }
                if (moshtaryAddressModel.getShahrId() <= 0 || moshtaryAddressModel.getShahrName() == null || moshtaryAddressModel.getShahrName().trim().equals(""))
                {
                    validData = false;
                    mView.get().onErrorShahr();
                }
                if (moshtaryAddressModel.getMantagheId() <= 0 || moshtaryAddressModel.getMantagheName() == null || moshtaryAddressModel.getMantagheName().trim().equals(""))
                {
                    validData = false;
                    mView.get().onErrorMantaghe();
                }
                if (moshtaryAddressModel.getCcNoeAddress() <= 0 || moshtaryAddressModel.getNameNoeAddress() == null || moshtaryAddressModel.getNameNoeAddress().trim().equals(""))
                {
                    validData = false;
                    mView.get().onErrorNoeAddress();
                }
                if (requireTelephone && (moshtaryAddressModel.getTelephone() == null || moshtaryAddressModel.getTelephone().length() != 11))
                {
                    validData = false;
                    mView.get().onErrorTelephone();
                }
				else if (!requireTelephone)
                {
                    if (moshtaryAddressModel.getTelephone().length() != 8 && moshtaryAddressModel.getTelephone().length() != 11)
                    {
                        validData = false;
                        mView.get().onErrorTelephone();
                    }
                }
				/*if (requireCodePosti && (moshtaryAddressModel.getCodePosty() == null || moshtaryAddressModel.getCodePosty().length() != 10))
                {
                    validData = false;
                    mView.get().onErrorCodePosti();
                }
                else if (!requireCodePosti)
                {
                    if (moshtaryAddressModel.getCodePosty().length() > 0 && moshtaryAddressModel.getCodePosty().length() != 10)
                    {
                        validData = false;
                        mView.get().onErrorCodePosti();
                    }
                }*/

                /*
                 * if codePosti is null or Less than 10 , boolean is false , so codePosti is 10 boolean is true
                 * isValiDateCodePosti check it in mahalCodePostiModelArrayList
                 */
                if (requireCodePosti)
                {
                    if (moshtaryAddressModel.getCodePosty().equals("") || moshtaryAddressModel.getCodePosty() == null || moshtaryAddressModel.getCodePosty().length() != 10){
                        validData = false;
                        mView.get().onErrorCodePosti(false);
                    }
                    if (moshtaryAddressModel.getCodePosty().length() == 10){
                        assert moshtaryAddressModel.getCodePosty() != null;
                        String codePostiFive = moshtaryAddressModel.getCodePosty().substring(0 , 5);
                        Long codePosti = Long.parseLong(codePostiFive);
                        boolean isValiDateCodePosti = false;
                        for (int i = 0; i < mahalCodePostiModelArrayList.size(); i++) {
                            int codePostiFrom = Integer.parseInt(mahalCodePostiModelArrayList.get(i).getCodePostiFrom());
                            int codePostiTo = Integer.parseInt(mahalCodePostiModelArrayList.get(i).getCodePostiTo());
                            if (codePosti >= codePostiFrom && codePosti <= codePostiTo) {
                                isValiDateCodePosti = true;
                                Log.i("AddCustomerAddres" , "codePostiFrom : " + codePostiFrom + "   codePostiTo : " + codePostiTo + "\n");
                            }
                        }
                        if (!isValiDateCodePosti){
                            validData = false;
                            mView.get().onErrorCodePosti(true);
                        }

                    }

                }
                else {

                    if (moshtaryAddressModel.getCodePosty().length() > 0 && moshtaryAddressModel.getCodePosty().length() != 10)
                    {
                        validData = false;
                        mView.get().onErrorCodePosti(false);
                    }
                }



                if (moshtaryAddressModel.getKhiabanAsli() == null || moshtaryAddressModel.getKhiabanAsli().trim().equals(""))
                {
                    validData = false;
                    mView.get().onErrorKhiabanAsli();
                }
                if (moshtaryAddressModel.getKoocheAsli() == null || moshtaryAddressModel.getKoocheAsli().trim().equals(""))
                {
                    validData = false;
                    mView.get().onErrorKoocheAsli();
                }
                if (moshtaryAddressModel.getPelak() == null || moshtaryAddressModel.getPelak().trim().equals(""))
                {
                    validData = false;
                    mView.get().onErrorEmptyPelak();
                }
                if (moshtaryAddressModel.getPelak() != null && moshtaryAddressModel.getPelak().length() > 5)
                {
                    validData = false;
                    mView.get().onErrorLengthPelak();
                }
                if (moshtaryAddressModel.getLatitude_y() == 0.0 || moshtaryAddressModel.getLongitude_x() == 0.0)
                {
                    validData = false;
                    mView.get().showResourceError(false, R.string.error, R.string.errorGetLocation, Constants.FAILED_MESSAGE(), R.string.apply);
                }

                if (validData)
                {
                    String address = moshtaryAddressModel.getOstanName() + " - " + moshtaryAddressModel.getShahrestanName() + " - "
                            + moshtaryAddressModel.getShahrName() + " - " + moshtaryAddressModel.getKhiabanAsli() + " - ";
                    if (!moshtaryAddressModel.getKhiabanFarei1().trim().equals(""))
                    {
                        address = address + moshtaryAddressModel.getKhiabanFarei1() + " - ";
                    }
                    if (!moshtaryAddressModel.getKhiabanFarei2().trim().equals(""))
                    {
                        address = address + moshtaryAddressModel.getKhiabanFarei2() + " - ";
                    }
                    if (!moshtaryAddressModel.getKoocheAsli().trim().equals(""))
                    {
                        address = address + moshtaryAddressModel.getKoocheAsli() + " - ";
                    }
                    if (!moshtaryAddressModel.getKoocheFarei1().trim().equals(""))
                    {
                        address = address + moshtaryAddressModel.getKoocheFarei1() + " - ";
                    }
                    address = address + getAppContext().getResources().getString(R.string.pelak) + " " + moshtaryAddressModel.getPelak();
                    moshtaryAddressModel.setAddress(address);
                    mModel.saveNewAddress(moshtaryAddressModel);
                }
            }
        }
        else
        {
            mView.get().showResourceError(false, R.string.error, R.string.errorAddress, Constants.FAILED_MESSAGE(), R.string.apply);
        }
    }

    @Override
    public void checkDeleteAddress(int position)
    {
        if (position >= 0)
        {
            mModel.deleteAddress(position);
        }
    }

    @Override
    public void getAddCustomerInfo()
    {
        mModel.getAddCustomerInfo();
    }

    @Override
    public void getMahalCodePosti(int ccMahal) {
        mModel.getMahalCodePosti(ccMahal);
    }

    @Override
    public void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild)
    {
        mModel.setLogToDB(logType, message, logClass, logActivity, functionParent, functionChild);
    }

    @Override
    public void onDestroy(boolean isChangingConfig)
    {

    }


    /////////////////////////// RequiredPresenterOps ///////////////////////////


    @Override
    public Context getAppContext()
    {
        return mView.get().getAppContext();
    }

    @Override
    public void onGetConfig(boolean requireCodePosti, boolean requireTelephone)
    {
        mView.get().onGetConfig(requireCodePosti, requireTelephone);
    }

    @Override
    public void onGetOstanItems(ArrayList<MahalModel> ostans)
    {
        mView.get().onGetOstanItems(ostans);
    }

    @Override
    public void onGetShahrestanItems(ArrayList<MahalModel> shahrestans)
    {
        mView.get().onGetShahrestanItems(shahrestans);
    }

    @Override
    public void onGetBakhshItems(ArrayList<MahalModel> bakhshes)
    {
        mView.get().onGetBakhshItems(bakhshes);
    }

    @Override
    public void onGetShahrItems(ArrayList<MahalModel> shahres)
    {
        mView.get().onGetShahrItems(shahres);
    }

    @Override
    public void onGetMantagheItems(ArrayList<MahalModel> mantaghes)
    {
        mView.get().onGetMantagheItems(mantaghes);
    }

    @Override
    public void onGetNoeAddressItems(ArrayList<Integer> itemId, ArrayList<String> itemTitles)
    {
        mView.get().onGetNoeAddressItems(itemId , itemTitles);
    }

    @Override
    public void onGetSellPolygon(ArrayList<PolygonForoshSatrModel> polygonForoshSatrModels, String polygonColor)
    {
        if (polygonForoshSatrModels != null && polygonForoshSatrModels.size() != 0)
        {
            mView.get().drawSellPolygon(polygonForoshSatrModels , polygonColor);
        }
    }

    @Override
    public void onSuccessfullySavedMoshtaryAddress(MoshtaryAddressModel moshtaryAddressModel)
    {
        mView.get().onSuccessfullySaveNewAddress(moshtaryAddressModel);
    }

    @Override
    public void onFailedSaveMoshtaryAddress(int errorMessageResId)
    {
        mView.get().showResourceError(false, R.string.error, errorMessageResId, Constants.FAILED_MESSAGE(), R.string.apply);
    }

    @Override
    public void onSuccessDeleteAddress(int deletedPosition)
    {
        mView.get().onSuccessDeleteAddress(deletedPosition);
    }

    @Override
    public void onFailedDeleteAddress()
    {
        mView.get().showToast(R.string.errorDeleteAddress, Constants.FAILED_MESSAGE(), Constants.DURATION_LONG());
    }

    @Override
    public void onGetAddCustomerInfo(AddCustomerInfoModel addCustomerInfoModel)
    {
        mView.get().onSuccessGetAddCustomerInfo(addCustomerInfoModel);
    }

    @Override
    public void onNetworkError(boolean closeActivity)
    {

    }

    @Override
    public void onMahalCodePosti(ArrayList<MahalCodePostiModel> mahalCodePostiModels) {
        mView.get().onGetMahalCodePosti(mahalCodePostiModels);
    }

}
