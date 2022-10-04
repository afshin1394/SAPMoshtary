package com.saphamrah.customer.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileManager {

    public static void writeToFile(File file,  String content) {

        try {

            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fileOutputStream =new FileOutputStream(file);
            fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
            fileOutputStream.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public static String readFileContent(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        if (file.exists()) {

            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String stringLine = bufferedReader.readLine();
                while (stringLine != null) {
                    stringBuilder.append(stringLine).append("\n");
                    stringLine = bufferedReader.readLine();
                }

                bufferedReader.close();
                fileReader.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            return "";
        }

        return stringBuilder.toString();
    }


}
