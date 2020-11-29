package com.saphamrah.MVP.Presenter;

import android.content.Context;
import android.util.Log;

import com.saphamrah.BaseMVP.AddPorseshnameInfoMVP;
import com.saphamrah.MVP.Model.AddPorseshnameInfoModel;
import com.saphamrah.Model.GorohModel;
import com.saphamrah.Model.ListMoshtarianModel;
import com.saphamrah.Model.MahalModel;
import com.saphamrah.Model.NoeTablighatModel;
import com.saphamrah.Model.PorseshnamehModel;
import com.saphamrah.Model.PorseshnamehTablighatModel;
import com.saphamrah.Model.PorseshnamehTozihatModel;
import com.saphamrah.R;
import com.saphamrah.Utils.Constants;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AddPorseshnameInfoPresenter implements AddPorseshnameInfoMVP.PresenterOps, AddPorseshnameInfoMVP.RequiredPresenterOps
{

    private WeakReference<AddPorseshnameInfoMVP.RequiredViewOps> mView;
    private AddPorseshnameInfoMVP.ModelOps mModel;
    private boolean mIsChangingConfig;


    public AddPorseshnameInfoPresenter(AddPorseshnameInfoMVP.RequiredViewOps viewOps)
    {
        this.mView = new WeakReference<>(viewOps);
        mModel = new AddPorseshnameInfoModel(this);
    }


    /////////////////////////// PresenterOps ///////////////////////////

    @Override
    public void onConfigurationChanged(AddPorseshnameInfoMVP.RequiredViewOps view)
    {
        mView = new WeakReference<>(view);
    }

    @Override
    public void saveInfo()
    {
        mModel.saveInfo();
    }

    @Override
    public void onGetMoshtary(ListMoshtarianModel listMoshtarianModel)
    {
        if (listMoshtarianModel != null)
        {
            mView.get().showMoshtaryInfo(listMoshtarianModel);
        }
    }

    @Override
    public void onGetPorseshnamehInfo(PorseshnamehModel porseshnamehModel)
    {
        if (porseshnamehModel != null && porseshnamehModel.getCcPorseshnameh() > -1)
        {
            mView.get().onGetPorseshnamehInfo(porseshnamehModel);
        }
    }

    @Override
    public void onGetStatesOfMantaghe(MahalModel mantagheModel, MahalModel shahrModel, MahalModel bakhshModel, MahalModel shahrestanModel, MahalModel ostanModel)
    {
        mView.get().showStatesOfMantaghe(mantagheModel, shahrModel, bakhshModel, shahrestanModel, ostanModel);
    }

    @Override
    public void onGetAdsOfPorseshnameh(List<PorseshnamehTablighatModel> porseshnamehTablighatModels)
    {
        if (porseshnamehTablighatModels.size() > 0)
        {
            mView.get().showAdsOfPorseshnameh(porseshnamehTablighatModels);
        }
    }

    @Override
    public void onGetNameSenfMoshtary(String nameGoroh)
    {
        mView.get().showNameSenfMoshtary(nameGoroh);
    }

    @Override
    public void getMoshtary(int ccMoshtary, String codeMoshtary)
    {
        if (ccMoshtary != -1 && codeMoshtary != null && !codeMoshtary.trim().equals(""))
        {
            Log.d("AddPorsesh" , "in if presenter");
            mModel.getMoshtary(ccMoshtary, codeMoshtary);
        }
    }

    @Override
    public void getPorseshnamehInfo(Integer ccPorseshname)
    {
        if (ccPorseshname > -1)
        {
            mModel.getPorseshnamehInfo(ccPorseshname);
        }
    }

    @Override
    public void getAdsOfPorseshnameh(int ccPorseshnameh)
    {
        mModel.getAdsOfPorseshnameh(ccPorseshnameh);
    }

    @Override
    public void getNameSenfMoshtary(int ccSenfMoshtary)
    {
        mModel.getNameSenfMoshtary(ccSenfMoshtary);
    }

    @Override
    public void getStatesOfMantaghe(int ccMahal)
    {
        mModel.getStatesOfMantaghe(ccMahal);
    }

    @Override
    public void getProductsItem()
    {
        mModel.getProductsItem();
    }

    @Override
    public void getAnbarItems()
    {
        mModel.getAnbarItems();
    }

    @Override
    public void getAds()
    {
        mModel.getAds();
    }

    @Override
    public void getDescription()
    {
        mModel.getDescription();
    }

    @Override
    public void getOstanItems()
    {
        mModel.getOstanItems();
    }

    @Override
    public void getAllNoeFaaliat()
    {
        mModel.getAllNoeFaaliat();
    }

    @Override
    public void getNoeSenf(Integer selectedNoeFaaliatId)
    {
        if (selectedNoeFaaliatId == null)
        {
            mView.get().showErrorNotSelectedNoeFaaliat();
        }
        else
        {
            mModel.getNoeSenf(selectedNoeFaaliatId);
        }
    }

    @Override
    public void getShahrestanItems(Integer selectedOstanId)
    {
        if (selectedOstanId != null)
        {
            mModel.getShahrestanItems(selectedOstanId);
        }
        else
        {
            mView.get().showErrorNotSelectedOstan();
        }
    }

    @Override
    public void getBakhshItems(Integer selectedShahrestanId)
    {
        if (selectedShahrestanId != null)
        {
            mModel.getBakhshItems(selectedShahrestanId);
        }
        else
        {
            mView.get().showErrorNotSelectedShahrestan();
        }
    }

    @Override
    public void getShahrItems(Integer selectedBakhshId)
    {
        if (selectedBakhshId != null)
        {
            mModel.getShahrItems(selectedBakhshId);
        }
        else
        {
            mView.get().showErrorNotSelectedBakhsh();
        }
    }

    @Override
    public void getMantagheItems(Integer selectedShahrId)
    {
        if (selectedShahrId != null)
        {
            mModel.getMantagheItems(selectedShahrId);
        }
        else
        {
            mView.get().showErrorNotSelectedShahr();
        }
    }

    @Override
    public void insertPorseshname(int ccPorseshname, int ccMoshtary, Integer selectedProductId, List<Integer> selectedAdsId, String fname, String lname, String nationalCode, String nameMaghazeh, String masahateMaghazeh, Integer selectedccMasir, Integer selectedAnbarId, Integer selectedNoeFaaliat, Integer selectedNoeSenf, String ostan, String shahrestan, String bakhsh, String shahr, String mantaghe, Integer selectedMantagheId, String nameMahale, String telephone, String oldTelephone, String mobile, String oldMobile, String postalCode, String khiabanAsli, String khiabanFaree1, String khiabanFaree2, String koocheAsli, String koocheFaree, String pelak, Integer selectedDescId)
    {
        boolean validData = true;

        if (fname == null || fname.trim().equals(""))
        {
            mView.get().showErrorFname();
            validData = false;
        }

        if (lname == null || lname.trim().equals(""))
        {
            mView.get().showErrorLname();
            validData = false;
        }

        if (nameMaghazeh == null || nameMaghazeh.trim().equals(""))
        {
            mView.get().showErrorNameMaghazeh();
            validData = false;
        }

        int intMasahateMaghazeh = 0;
        if (masahateMaghazeh == null || masahateMaghazeh.trim().equals(""))
        {

            mView.get().showErrorMasahateMaghazeh();
            validData = false;
        }
        else
        {
            try
            {
                intMasahateMaghazeh = Integer.parseInt(masahateMaghazeh);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                mView.get().showErrorMasahateMaghazeh();
            }
        }

        if (selectedNoeFaaliat == null)
        {
            mView.get().showErrorNoeFaaliat();
            validData = false;
        }

        if (selectedNoeSenf == null)
        {
            mView.get().showErrorNoeSenf();
            validData = false;
        }

        if (ostan == null || ostan.trim().equals(""))
        {
            mView.get().showErrorOstan();
            validData = false;
        }

        if (shahrestan == null || shahrestan.trim().equals(""))
        {
            mView.get().showErrorShahrestan();
            validData = false;
        }

        if (bakhsh == null || bakhsh.trim().equals(""))
        {
            mView.get().showErrorBakhsh();
            validData = false;
        }

        if (shahr == null || shahr.trim().equals(""))
        {
            mView.get().showErrorShahr();
            validData = false;
        }

        if (mantaghe == null || mantaghe.trim().equals("") || selectedMantagheId == null)
        {
            mView.get().showErrorMantaghe();
            validData = false;
        }

        if (nameMahale == null || nameMahale.trim().equals(""))
        {
            mView.get().showErrorNameMahale();
            validData = false;
        }

        if (khiabanAsli == null || khiabanAsli.trim().equals(""))
        {
            mView.get().showErrorKhiabanAsli();
            validData = false;
        }

        if (koocheAsli == null || koocheAsli.trim().equals(""))
        {
            mView.get().showErrorKoocheAsli();
            validData = false;
        }

        if (pelak == null || pelak.trim().equals(""))
        {
            mView.get().showErrorPelak();
            validData = false;
        }

        if (mobile == null || mobile.trim().length() != 11 || !mobile.startsWith("09"))
        {
            mView.get().showErrorWrongMobile();
        }

        oldTelephone = (oldTelephone == null) ? "" : oldTelephone;
        telephone = (telephone == null) ? "" : telephone;
        oldMobile = (oldMobile == null) ? "" : oldMobile;
        mobile = (mobile == null) ? "" : mobile;
        boolean changedPhone = false;
        if (!oldTelephone.equals(telephone) || !oldMobile.equals(mobile))
        {
            changedPhone = true;
        }

        String address = String.format("%1$s - %2$s - %3$s - %4$s - %5$s - %6$s - %7$s", ostan, shahrestan, bakhsh, shahr, mantaghe, nameMahale, khiabanAsli);
        if (khiabanFaree1 != null && !khiabanFaree1.trim().equals(""))
        {
            address += " - " + khiabanFaree1;
        }
        if (khiabanFaree2 != null && !khiabanFaree2.trim().equals(""))
        {
            address += " - " + khiabanFaree2;
        }
        address += " - " + koocheAsli;
        if (koocheFaree != null && !koocheFaree.trim().equals(""))
        {
            address += " - " + koocheFaree;
        }
        address += " - " + pelak;

        if (address.trim().replace("-","").equals(""))
        {
            mView.get().showErrorAddress();
            validData = false;
        }

        if (validData)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT());
            String currentDate = sdf.format(new Date());
            int isMoshtary = 0;
            int codeVazeiat = 0;
            int ccRotbeh = 0;
            if (ccMoshtary == -1)
            {
                ccMoshtary = 0;
                isMoshtary = 0;
                codeVazeiat = 0;

            }
            else
            {
                isMoshtary = 1;
                codeVazeiat = 2;
            }

            if (intMasahateMaghazeh >100 )
            {
                ccRotbeh = 0;
            }
            else if ((intMasahateMaghazeh <= 100 && intMasahateMaghazeh > 50))
            {
                ccRotbeh = 1;
            }
            else if ((intMasahateMaghazeh <= 50 && intMasahateMaghazeh > 20) )
            {
                ccRotbeh = 2;
            }
            else if (intMasahateMaghazeh <=20)
            {
                ccRotbeh = 3;
            }
            telephone = telephone.trim().equals("") ? oldTelephone : telephone;

            Log.d("AddPorsesh" , "oldTelephone : " + oldTelephone + " , telephone : " + telephone);

            PorseshnamehModel porseshnamehModel = new PorseshnamehModel();
            porseshnamehModel.setHasDelpazir(selectedProductId);
            porseshnamehModel.setFNameMoshtary(fname);
            porseshnamehModel.setLNameMoshtary(lname);
            porseshnamehModel.setNameMaghazeh(nameMaghazeh);
            porseshnamehModel.setHasAnbar(selectedAnbarId);
            porseshnamehModel.setCcNoeMoshtary(selectedNoeFaaliat);
            porseshnamehModel.setCcSenfMoshtary(selectedNoeSenf);
            porseshnamehModel.setNameMahaleh(nameMahale);
            porseshnamehModel.setTelephone(telephone);
            porseshnamehModel.setMobile(mobile);
            porseshnamehModel.setCodePosty(postalCode);
            porseshnamehModel.setKhiabanAsli(khiabanAsli);
            porseshnamehModel.setKhiabanFarei1(khiabanFaree1);
            porseshnamehModel.setKhiabanFarei2(khiabanFaree2);
            porseshnamehModel.setKoocheAsli(koocheAsli);
            porseshnamehModel.setKoocheFarei1(koocheFaree);
            porseshnamehModel.setCcMahal(selectedMantagheId);
            porseshnamehModel.setPelak(pelak);
            porseshnamehModel.setAddress(address);
            porseshnamehModel.setMasahatMaghazeh(intMasahateMaghazeh);
            porseshnamehModel.setCcMasir(selectedccMasir);
            porseshnamehModel.setCcPorseshnamehTozihat(selectedDescId);
            porseshnamehModel.setTarikhPorseshnameh(currentDate);
            porseshnamehModel.setZamanSabt(currentDate);
            porseshnamehModel.setNameMoshtary(fname + " " + lname);
            porseshnamehModel.setIsMoshtary(isMoshtary);
            porseshnamehModel.setCcMoshtary(ccMoshtary);
            porseshnamehModel.setCodeVazeiat(codeVazeiat);
            porseshnamehModel.setCcRotbeh(ccRotbeh);
            porseshnamehModel.setCodeMely(nationalCode);

            if (ccPorseshname == -1)
            {
                mModel.insertPorseshname(porseshnamehModel, selectedAdsId, changedPhone);
            }
            else
            {
                mModel.updatePorseshname(ccPorseshname, porseshnamehModel, selectedAdsId);
            }
        }

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
    public void onGetProductsItem(Map<String,Integer> mapProducts)
    {
        if (mapProducts.size() > 0)
        {
            mView.get().setProductItems(mapProducts);
        }
        else
        {
            mView.get().showErrorGetProductItems();
        }
    }

    @Override
    public void onGetAnbarItems(Map<String, Integer> mapAnbarItems)
    {
        if (mapAnbarItems.size() > 0)
        {
            mView.get().setAnbarItems(mapAnbarItems);
        }
        else
        {
            mView.get().showErrorGetAnbarItems();
        }
    }

    @Override
    public void onGetAds(List<NoeTablighatModel> noeTablighatModels)
    {
        if (noeTablighatModels.size() > 0)
        {
            List<String> adsTitles = new ArrayList<>();
            for (NoeTablighatModel model : noeTablighatModels)
            {
                adsTitles.add(model.getNameNoeTablighat());
            }
            mView.get().setAds(noeTablighatModels, adsTitles);
        }
        else
        {
            mView.get().showErrorNotFoundAds();
        }
    }

    @Override
    public void onGetDescription(List<PorseshnamehTozihatModel> porseshnamehTozihatModels)
    {
        if (porseshnamehTozihatModels.size() > 0)
        {
            List<String> descTitles = new ArrayList<>();

            PorseshnamehTozihatModel porseshnamehTozihatModel = new PorseshnamehTozihatModel();
            porseshnamehTozihatModel.setId(0);
            porseshnamehTozihatModel.setSharh(getAppContext().getResources().getString(R.string.pick));
            porseshnamehTozihatModel.setCcPorseshnamehTozihat(0);
            porseshnamehTozihatModels.add(0, porseshnamehTozihatModel);

            for (PorseshnamehTozihatModel model : porseshnamehTozihatModels)
            {
                descTitles.add(model.getSharh());
            }
            mView.get().setDescrption(porseshnamehTozihatModels, descTitles);
        }
        else
        {
            mView.get().showErrorGetDescrption();
        }
    }

    @Override
    public void onGetAllNoeFaaliat(ArrayList<GorohModel> noeFaaliatItems)
    {
        if (noeFaaliatItems.size() > 0)
        {
            List<String> titles = new ArrayList<>();
            for (GorohModel model : noeFaaliatItems)
            {
                titles.add(model.getNameGoroh());
            }
            mView.get().setNoeFaaliat(noeFaaliatItems, titles);
        }
        else
        {
            mView.get().showErrorGetNoeFaaliat();
        }
    }

    @Override
    public void onGetNoeSenfItems(ArrayList<GorohModel> noeSenfItems)
    {
        if (noeSenfItems.size() > 0)
        {
            List<String> titles = new ArrayList<>();
            for (GorohModel model : noeSenfItems)
            {
                titles.add(model.getNameGoroh());
            }
            mView.get().setNoeSenf(noeSenfItems, titles);
        }
        else
        {
            mView.get().showErrorGetNoeSenf();
        }
    }

    @Override
    public void onGetOstanItems(ArrayList<MahalModel> mahalModels)
    {
        List<String> ostanTitles = new ArrayList<>();
        for (MahalModel model : mahalModels)
        {
            ostanTitles.add(model.getNameMahal());
        }
        mView.get().setOstanItems(mahalModels, ostanTitles);
    }

    @Override
    public void onGetShahrestanItems(ArrayList<MahalModel> mahalModels)
    {
        List<String> shahrestanTitles = new ArrayList<>();
        for (MahalModel model : mahalModels)
        {
            shahrestanTitles.add(model.getNameMahal());
        }
        mView.get().setShahrestanTitlesItems(mahalModels, shahrestanTitles);
    }

    @Override
    public void onGetBakhshItems(ArrayList<MahalModel> mahalModels)
    {
        List<String> bakhshTitles = new ArrayList<>();
        for (MahalModel model : mahalModels)
        {
            bakhshTitles.add(model.getNameMahal());
        }
        mView.get().setBakhshTitlesItems(mahalModels, bakhshTitles);
    }

    @Override
    public void onGetShahrItems(ArrayList<MahalModel> mahalModels)
    {
        List<String> shahrTitles = new ArrayList<>();
        for (MahalModel model : mahalModels)
        {
            shahrTitles.add(model.getNameMahal());
        }
        mView.get().setShahrTitlesItems(mahalModels, shahrTitles);
    }

    @Override
    public void onGetMantagheItems(ArrayList<MahalModel> mahalModels)
    {
        List<String> mantagheTitles = new ArrayList<>();
        for (MahalModel model : mahalModels)
        {
            mantagheTitles.add(model.getNameMahal());
        }
        mView.get().setMantagheTitlesItems(mahalModels, mantagheTitles);
    }

    @Override
    public void onInsertPorseshname(int ccPorseshname, boolean changedPhone)
    {
        if (ccPorseshname > 0)
        {
            if (changedPhone)
            {
                mView.get().showAlertChangedPhone(ccPorseshname);
            }
            else
            {
                mView.get().openKalaAmargarActivity(ccPorseshname);
            }
        }
        else
        {
            mView.get().showErrorInsertPorseshname();
        }
    }

    @Override
    public void onUpdatePorseshnameh(int ccPorseshname)
    {
        mView.get().openKalaAmargarActivity(ccPorseshname);
    }

    @Override
    public void onGetAddress(MahalModel ostanModel, MahalModel shahrestanModel, MahalModel bakhshModel, MahalModel shahrModel, MahalModel mantagheModel)
    {
        mView.get().showCustomerAddress(ostanModel, shahrestanModel, bakhshModel, shahrModel, mantagheModel);


    }
}
