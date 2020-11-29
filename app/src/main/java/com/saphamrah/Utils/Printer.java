package com.saphamrah.Utils;

import android.content.Intent;

abstract public class Printer
{

    final static int brightness = 100;
    final static int compress = 1;
    protected long ccDarkhastFaktor;

    Printer(long ccDarkhastFaktor)
    {
        this.ccDarkhastFaktor = ccDarkhastFaktor;
    }


    /**
     * این متد برای پرینترهای bixolon استفاده می شود.
     * بعد از اتصال به پرینتر از طریق بلوتوث این متد فراخوانی می شود برای مقداردهی اولیه
     * @param data اینتنت بازگشتی از فرم انتخاب پرینتر
     * @return name of printer that we are connected
     */
    public abstract String setPrinter(Intent data);


    /**
     * این متد برای پرینترهای urovo استفاده می شود و وضعیت پرینتر(مثل : سالم بودن، نداشتن کاغذ، پایین بودن باتری و ...) را بر میگرداند.
     * @return message that show status of printer
     */
    public abstract String getPrinterStateMessage();


    /**
     * این متد برای پرینترهای مختلف می باشد و عملیات پرینت را انجام می دهد.
     * @param filePath محل ذخیره سازی عکس موردنظر برای پرینت
     */
    public abstract void print(String filePath);

}
