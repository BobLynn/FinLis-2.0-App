package com.example.finlis_20_app;


import android.support.v4.app.INotificationSideChannel;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DatabasePhpClass {

    private static HttpClient hC;
    private static HttpPost hP;

    public static String DBstring(String i, String Wcook, String url){
        String result = "Error";

        try{
            hC = new DefaultHttpClient();
            hP = new HttpPost();
            hP.addHeader("Cookie", Wcook +
                    ";expires = Mon, 02-Feb-112 17:18:19 GMT; path = / ");

            ArrayList<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("S1", i));
            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

            HttpResponse hR = hC.execute(hP);
            HttpEntity hE = hR.getEntity();
            InputStream inputStream = hE.getContent();
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream, HTTP.UTF_8), 8);
            StringBuilder builder = new StringBuilder();          //StringBuilder是一個動態物件，可以指定它容納的字元數上限值
            String line = null;
            while ((line = bufReader.readLine()) != null){
                builder.append(line + "\n");
            }
            inputStream.close();
            result = builder.toString();

        }catch (Exception e){
            result = e.toString();
        }finally{
            hC.getConnectionManager().shutdown();           //getConnectionManager().shutdown()是連結客戶端關閉的方法
        }

        return result;
    }

}

