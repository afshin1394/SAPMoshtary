package com.saphamrah.Network;

public interface AsyncDownloadFileResponse
{

    void updateProgressBar(int progress);
    void downloadCompleted();
    void downloadFailed(int resId);

}
