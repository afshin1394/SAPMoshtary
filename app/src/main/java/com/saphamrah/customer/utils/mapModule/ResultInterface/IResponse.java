package com.saphamrah.customer.utils.mapModule.ResultInterface;

import java.util.ArrayList;


public interface IResponse
{

    void onSuccess(ArrayList arrayListData);

    void onFailed(String type , String error);

}
