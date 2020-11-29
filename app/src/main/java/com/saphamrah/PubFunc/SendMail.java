package com.saphamrah.PubFunc;

import com.saphamrah.Network.AsyncSendMail;
import com.saphamrah.Network.AsyncTaskResponse;

public class SendMail
{


    public void sendGmail(String sender, String password, String subject, String body, String receiver, AsyncTaskResponse callback)
    {
        AsyncSendMail asyncSendMail = new AsyncSendMail(sender, password, subject, body, receiver);
        asyncSendMail.delegate = callback;
        asyncSendMail.execute();
    }

}
