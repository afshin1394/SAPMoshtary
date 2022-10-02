package com.saphamrah.customer.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsHandle {


    public String getOtpMessageBody(String messageBody) {

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(messageBody);

        int otp = 0;
        if (matcher.find()) {
            otp = Integer.parseInt(matcher.group(0));
        }

        if (otp < 999) {
           return  "";
        } else {
            return String.valueOf(otp);
        }

    }
}
