package com.saphamrah.BaseMVP;

import android.content.Context;

import com.saphamrah.Model.ElatAdamDarkhastModel;
import com.saphamrah.Model.KalaModel;
import com.saphamrah.UIModel.KalaFilterUiModel;
import com.saphamrah.UIModel.KalaMojodiGiriModel;

import java.util.ArrayList;

public interface MojodiGiriMVP
{

    interface RequiredViewOps
    {
        Context getAppContext();
		void hideNoRequestButton();
        void openDarkhastActivity();
        void openBarkhordAvalieActivity();
        void onGetKala(ArrayList<KalaModel> kalaModels);
        void onGetInsertedKalaMojodi(ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels);
        void onSearchInsertedKalaMojodi(ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels);
        void onErrorInputCountForAddNewKala(int errorResId);
        void onErrorForAddNewKala(int errorResId);
        void onSuccessAddNewKala();
        void onSuccessRemoveKalaMojodiGiri(int position);
        void onGetElatAdamDarkhast(ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels , ArrayList<String> elatAdamDarkhastTitles);
        void showTakeImageAlert(ElatAdamDarkhastModel elatAdamDarkhastModel);
        void showDuplicatedCustomerCodeAlert(ElatAdamDarkhastModel elatAdamDarkhastModel);
        void onSuccessInsertAdamDarkhast();
        void showToast(int resId, int messageType , int duration);
        void onKalaFilter(ArrayList<KalaFilterUiModel> kalaFilterUiModels, ArrayList<String> itemsKalaFilter);
    }


    interface PresenterOps
    {
        void onConfigurationChanged(MojodiGiriMVP.RequiredViewOps view);
        void updateHaveMojodiGiri();
        void getNoeMasouliat();
        void checkBottomBarClick(int position);
        void getKala(int ccMoshtary);
        void getInsertedKalaMojodi(int ccMoshtary);
        void searchInsertedKalaMojodi(String searchWord , ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels);
        void checkAddNewKala(ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels , ArrayList<KalaModel> selectedKalaModel , int ccMoshtary , String countMojodi);
        void checkRemoveKalaMojodiGiri(int ccKalaMojodiGiri , int position);
        void getElatAdamDarkhast(int ccMoshtary);
        void checkSeletedAdamDarkhastItem(int ccMoshtary , ElatAdamDarkhastModel elatAdamDarkhastModel);
        void checkAdamDarkhastForInsert(int ccMoshtary , ElatAdamDarkhastModel elatAdamDarkhastModel , byte[] imageAdamDarkhast , String codeMoshtaryTekrari);
        void checkInsertLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy(boolean isChangingConfig);
        void getKalaFilter();
    }


    interface RequiredPresenterOps
    {
        Context getAppContext();
        void onConfigurationChanged(MojodiGiriMVP.RequiredViewOps view);
		void onGetNoeMasouliat(int noeMasouliat);
        void onGetKala(ArrayList<KalaModel> kalaModels);
        void onGetInsertedKalaMojodi(ArrayList<KalaMojodiGiriModel> kalaMojodiGiriModels);
        void onSuccessAddNewKala();
        void onErrorAddNewKala();
        void onSuccessRemoveKalaMojodiGiri(int position);
        void onGetElatAdamDarkhast(ArrayList<ElatAdamDarkhastModel> elatAdamDarkhastModels);
        void onFailedRemoveKalaMojodiGiri();
        void onSuccessInsertAdamDarkhast();
        void onFailedInsertAdamDarkhast();
        void onErrorAccessToLocation();
        void onKalaFilter(ArrayList<KalaFilterUiModel> kalaFilterUiModels);
    }


    interface ModelOps
    {
        void updateHaveMojodiGiri();
        void getNoeMasouliat();
        void getKala(int ccMoshtary);
        void getAllInsertedKalaMojodi(int ccMoshtary);
        void addNewKala(int ccMoshtary , int ccKalaCode , float countMojodi);
        void removeKalaMojodiGiri(int ccKalaMojodiGiri , int position);
        void getElatAdamDarkhast(int ccMoshtary);
        void insertAdamDarkhast(int ccMoshtary , int ccElatAdamDarkhast , byte[] imageAdamDarkhast , String codeMoshtaryTekrari);
        void setLogToDB(int logType, String message, String logClass, String logActivity, String functionParent, String functionChild);
        void onDestroy();
        void getKalaFilter();
    }

}
