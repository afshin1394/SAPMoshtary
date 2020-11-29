package com.saphamrah.Network;

import android.os.AsyncTask;
import android.util.Log;

import com.saphamrah.Utils.GMailSender;

public class AsyncSendMail extends AsyncTask<Void , Void , Boolean>
{

    private String sender;
    private String password;
    private String subject;
    private String body;
    private String receiver;
    public AsyncTaskResponse delegate = null;

    public AsyncSendMail(String sender, String password, String subject, String body, String receiver)
    {
        this.sender = sender;
        this.password = password;
        this.subject = subject;
        this.body = body;
        this.receiver = receiver;
    }


    @Override
    protected Boolean doInBackground(Void... voids)
    {
        try
        {
            GMailSender gMailSender = new GMailSender(sender , password);
            return gMailSender.sendMail(subject, body, sender, receiver);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Log.e("SendMail", e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result)
    {
        super.onPostExecute(result);
        Log.d("mail" , "result in async : " + result);
        delegate.processFinished(result);
    }

}
