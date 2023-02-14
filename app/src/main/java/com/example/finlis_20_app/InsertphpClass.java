package com.example.finlis_20_app;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.protocol.HTTP;

import java.util.ArrayList;

public class InsertphpClass {

    private static HttpClient hC;
    private static HttpPost hP;

    public static void inserting(String[] i, String Wcook, String url){
        String result = "Error";

        try{
            hC = new DefaultHttpClient();
            hP = new HttpPost(url + "/Intert_text1.php");
            System.out.println("是否取得cookie = " + Wcook);
            hP.addHeader("Cookie", Wcook +
                    ";expires = Mon, 02-Feb-112 17:18:19 GMT; path = / ");

            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("S1", i[0]));
            params.add(new BasicNameValuePair("S2", i[1]));
            params.add(new BasicNameValuePair("S3", i[2]));
            params.add(new BasicNameValuePair("S4", i[3]));

            hP.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            HttpResponse hR = hC.execute(hP);
            hR.getEntity();

        }catch (Exception e){
            System.out.print(e.toString());
        }finally{
            hC.getConnectionManager().shutdown();           //getConnectionManager().shutdown()是連結客戶端關閉的方法
        }
    }

}
