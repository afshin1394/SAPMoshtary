package com.saphamrah.WebService.ServiceResponse;

public interface GetLoginInfoCallback
{

    void onSuccess(boolean validDiffTime, String serverDateTime, String deviceDateTime, long diff);
    void onFailure(String error);

}
