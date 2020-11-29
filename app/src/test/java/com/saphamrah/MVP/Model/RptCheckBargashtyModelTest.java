package com.saphamrah.MVP.Model;

import com.saphamrah.BaseMVP.RptCheckBargashtyMVP;
import com.saphamrah.Model.BargashtyModel;
import com.saphamrah.PubFunc.PubFunc;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RptCheckBargashtyModelTest
{

    @Test
    public void getListBargashty()
    {
        RptCheckBargashtyMVP.RequiredPresenterOps mPresenter = mock(RptCheckBargashtyMVP.RequiredPresenterOps.class);
        RptCheckBargashtyMVP.ModelOps model = new RptCheckBargashtyModel(mPresenter);
        model.getListBargashty();
        verify(mPresenter).onGetListBargashty(new ArrayList<BargashtyModel>());
    }

    @Test
    public void updateListBargashty()
    {
        RptCheckBargashtyMVP.RequiredPresenterOps mPresenter = mock(RptCheckBargashtyMVP.RequiredPresenterOps.class);
        RptCheckBargashtyMVP.ModelOps model = new RptCheckBargashtyModel(mPresenter);
        model.updateListBargashty();
        verify(mPresenter).onErrorUpdateListBargashty();
    }
}