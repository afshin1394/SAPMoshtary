package com.saphamrah.PubFunc;

import android.content.Context;
import android.util.Base64;

import com.saphamrah.Utils.Constants;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encryption
{

    SecretKey generateKey(Context context)
    {
        try
        {
            final int KEY = 65464;
            return new SecretKeySpec((Constants.ENC_KEY() + KEY).getBytes() , "AES");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "Encryption", "", "generateKey", "");
            return null;
        }
    }

    public String encrypt(Context context, String plain)
    {
        try
        {
            SecretKey key = generateKey(context);
            if (key != null)
            {
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] cipherText = cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8));
                return Base64.encodeToString(cipherText, Base64.DEFAULT);
            }
            else
            {
                return "";
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "Encryption", "", "encrypt", "");
            return "";
        }
    }

    public String decrypt(Context context, String encoded)
    {
        try
        {
            SecretKey key = generateKey(context);
            if (key != null)
            {
                byte[] encryted_bytes = Base64.decode(encoded , Base64.DEFAULT);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] decrypted = cipher.doFinal(encryted_bytes);
                return new String(decrypted , StandardCharsets.UTF_8);
            }
            else
            {
                return "";
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            PubFunc.Logger logger = new PubFunc().new Logger();
            logger.insertLogToDB(context,Constants.LOG_EXCEPTION(), exception.toString(), "Encryption", "", "decrypt", "");
            return "";
        }
    }

}
