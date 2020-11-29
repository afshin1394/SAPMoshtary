package com.saphamrah.Network;

import java.util.ArrayList;


public interface RetrofitResponse
{

    void onSuccess(ArrayList arrayListData);

    void onFailed(String type , String error);

}
