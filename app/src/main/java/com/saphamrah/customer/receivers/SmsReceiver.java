package com.saphamrah.customer.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

import com.saphamrah.customer.Application;
import com.saphamrah.customer.listeners.SmsListener;
import com.saphamrah.customer.utils.SmsHandle;

import java.util.Objects;

public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG =
            SmsReceiver.class.getSimpleName();
    public static final String pdu_type = "pdus";

    private SmsListener smsListener;
    private SmsHandle smsHandle;



    @Override
    public void onReceive(Context context, Intent intent) {

        try {

            smsListener = ((Application)context.getApplicationContext()).getSmsListener();
            smsHandle = new SmsHandle();

        } catch (Exception ex) {
            ex.printStackTrace();

        }

        if (smsListener != null){

            String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
            String BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";

            if (null != intent && null != intent.getAction() &&
                    Objects.equals(intent.getAction(), Telephony.Sms.Intents.SMS_RECEIVED_ACTION) ||
                    null != intent && null != intent.getAction() && Objects.equals(intent.getAction(), SMS_RECEIVED) ||
                    null != intent && null != intent.getAction() && Objects.equals(intent.getAction(), BOOT_COMPLETED)
            ) {
                try {
                    // Get the SMS message.
                    Bundle bundle = intent.getExtras();
                    SmsMessage[] msgs;
                    String strMessage = "";
                    String format = bundle.getString("format");
                    // Retrieve the SMS message received.
                    Object[] pdus = (Object[]) bundle.get(pdu_type);
                    if (pdus != null) {

                        // Fill the msgs array.
                        msgs = new SmsMessage[pdus.length];
                        for (int i = 0; i < msgs.length; i++) {
                            // Check Android version and use appropriate createFromPdu.
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                // If Android version M or newer:
                                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
                            } else {
                                // If Android version L or older:
                                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                            }
                            // Build the message to show.
                            strMessage += "SMS from " + msgs[i].getOriginatingAddress();
                            strMessage += " :" + msgs[i].getMessageBody() + "\n";
                            // Log and display the SMS message.
                            Log.d(TAG, "onReceive: " + strMessage);
                        }
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }
}
