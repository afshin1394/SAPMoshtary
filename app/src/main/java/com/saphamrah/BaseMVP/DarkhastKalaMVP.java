package com.saphamrah.BaseMVP;

import android.content.Context;
import android.util.SparseIntArray;

import com.saphamrah.MVP.View.DarkhastKalaActivity;
import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.Model.KalaPhotoModel;
import com.saphamrah.UIModel.JayezehByccKalaCodeModel;
import com.saphamrah.UIModel.KalaDarkhastFaktorSatrModel;
import com.saphamrah.UIModel.KalaFilterUiModel;
import com.saphamrah.UIModel.KalaMojodiZaribModel;

import java.util.ArrayList;
import java.util.List;

public interface DarkhastKalaMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
        void hideNoRequestButton();
        void hideFabAddButton();
        void prepareDataForCheckInsertFaktor();
        void openBarkhordAvalieActivity();
        void onGetRequestedGoods(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorsatrModels);
        void onGetAllKalaWithMojodiZarib(ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels,  DarkhastKalaActivity.AddItemType type,int zangireiParam);
        void onErrorAddNewRequestedKala(int errorResId);
        void onErrorAddNewRequestedKala(int resId, String parameter);
        void onSuccessAddNewRequestedKala(boolean isTedadKalaAsasi , boolean isTedadKalaAsasiWithTedad);
        void closeSelectNewGoodAlert(int position , int count);
        void onSuccessRemoveKala(int position);
        void onGetElatAdamDarkhast(ArrayList<ElatAdamDarkhastModel> elatAdamSefareshModels , ArrayList<Integer> elatAdamDarkhastIds , ArrayList<String> elatAdamDarkhastTitles);
        void showTakeImageAlert(ElatAdamDarkhastModel elatAdamDarkhastModel);
        void showDuplicatedCustomerCodeAlert(ElatAdamDarkhastModel elatAdamDarkhastModel);
        void onSuccessInsertAdamDarkhast();
        void showNameListOfKalaAdamForosh(String kalaNames);
        void closeActivity();
        void showToast(int resId, int messageType , int duration);
		void showAlertDialog(int resId, int messageType, boolean closeActivity);
        void showErrorAlertLoadingMaxMojodi(List<KalaModel> kalaModels);
        void showAlertLoading();
        void closeAlertLoading();
        void onCheckJayezeh(int position);
        void onCheckJayezehParent(ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeParentModels, int tedadKala,double mablaghForosh ,int ccKalaCode ,Long ccDarkhastFaktor);
        void onGetGridRecyclerDetails(int goodsNumberEachSection, ArrayList<KalaPhotoModel> kalaPhotoModels);
        void onCheckZanjiree();
        void onKalaFilter(ArrayList<KalaFilterUiModel> kalaFilterUiModels,ArrayList<String> itemsKalaFilter);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(DarkhastKalaMVP.RequiredViewOps view);
		void getNoeMasouliat();					   
        void checkBottomBarClick(int position , int selectedGoodsCount);
        void getNameListOfKalaAdamForosh();
        void getAllRequestedGoods();
        void getAllKalaWithMojodiZarib(int ccMoshtary,  DarkhastKalaActivity.AddItemType type);
        void checkAddKala(int ccMoshtary , int position , KalaMojodiZaribModel kalaMojodiZaribModel, String cartonCount, String basteCount, String adadCount);
        void checkRemoveKala(KalaDarkhastFaktorSatrModel kalaDarkhastFaktorsatrModels , int position , int ccMoshtary);
        void getElatAdamSefaresh(int ccMoshtary);
        void checkSeletedAdamSefareshItem(int ccMoshtary , ElatAdamDarkhastModel elatAdamDarkhastModel);
        void checkAdamSefareshForInsert(int ccMoshtary , ElatAdamDarkhastModel elatAdamDarkhastModel , byte[] imageAdamSefaresh , String codeMoshtaryTekrari);
        void checkOnBackPress();
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
        void checkJayezeh(int ccJayezeh, int tedadKala, double mablaghForosh, int ccKalaCode, Long ccDarkhastFaktor,int position, int ccMoshtary);
        void checkJayezehParent(int ccKalaCode , int tedadKala , Long ccDarkhastFaktor, double mablaghForosh, int ccMoshtary);
        void getRecyclerDetails();
        void checkZanjiree();
        void getKalaFilter();

    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(DarkhastKalaMVP.RequiredViewOps view);
		void onGetNoeMasouliat(int noeMasouliat, int CanEditDarkhastForMovaze);
        void onGetRequestedGoods(ArrayList<KalaDarkhastFaktorSatrModel> kalaDarkhastFaktorsatrModels);
        void onGetAllKalaWithMojodiZarib(ArrayList<KalaMojodiZaribModel> kalaMojodiZaribModels, DarkhastKalaActivity.AddItemType type);
        void onGetElatAdamSefaresh(ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels);
        void onErrorInsertFaktor(int errorResId);
        void onSuccessInsertFaktor(int position , int count);
        void onSuccessRemoveKala(int position);
        void onErrorRemoveKala();
        void onSuccessInsertAdamDarkhast();
        void onFailedInsertAdamDarkhast(int errorResId);
        void showNameListOfKalaAdamForosh(String kalaNames);
        void onSuccessCheckData();
        void onErrorNotSelectKalaAsasi();
        void onErrorBiggerThanMaxKalaMojodi(List<KalaModel> kalaModels);
        void onError();
        void onCheckJayezeh(int position);
        void onCheckJayezehParent(ArrayList<JayezehByccKalaCodeModel> jayezehByccKalaCodeParentModels, int tedadKala, double mablaghForosh , int ccKalaCode , Long ccDarkhastFaktor);
        void onGetRecyclerDetails(int ItemCountPerScreen,ArrayList<KalaPhotoModel> kalaPhotoModels);
    void onCheckZanjiree();
    void onKalaFilter(ArrayList<KalaFilterUiModel> kalaFilterUiModels);
    }


    interface ModelOps
    {
		void getNoeMasouliat();					   
        void getNameListOfKalaAdamForosh();
        void getAllRequestedGoods();
        void getAllKalaWithMojodiZarib(int ccMoshtary, boolean calculateKalaPishnahadi, boolean calculateKalaAsasi, boolean getAllRequestedGoods, DarkhastKalaActivity.AddItemType type);
        SparseIntArray insertNewFaktorSatr(int ccMoshtary , int position , KalaMojodiZaribModel kalaMojodiZaribModel , int requestedCount);
        void removeKala(KalaDarkhastFaktorSatrModel kalaDarkhastFaktorsatrModels , int position , int ccMoshtary);
        void getElatAdamSefaresh(int ccMoshtary);
        void insertAdamSefaresh(int ccMoshtary , int ccElatAdamSefaresh , byte[] imageAdamSefaresh , String codeMoshtaryTekrari);
        void checkData();
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
        void checkJayezeh(int ccJayezeh, int tedadKala, double mablaghForosh, int ccKalaCode, Long ccDarkhastFaktor,int position, int ccMoshtary);
        void checkJayezehParent(int ccKalaCode , int tedadKala , Long ccDarkhastFaktor, double mablaghForosh, int ccMoshtary);

        //Todo
        void getRecyclerDetails();
        int getItemCountPerScreen();
        ArrayList<KalaPhotoModel> getGallery();
        void checkZanjiree();
        void getKalaFilter();
    }

}
