package com.example.finlis_20_app;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQLTestActivity extends AppCompatActivity {

    Context context = this;
    Button btn1, btn2, btn3;
    EditText et1, et2, et3, et4;
    TextView textview0, textview1, textview2, textview3, textview4;
    WebView webView;
    String url = "http://localhost:3000/";
    CookieManager cookieManager;
    String cookieStr, id_text;
    ListView LV1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysql_test);

        //TODO:StrictMode是一款開發人員工具，可以檢測出可能意外執行的錯誤並給予顯示，以便開發者修復錯誤
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().
                detectDiskReads().detectDiskWrites().detectNetwork().
                penaltyLog().build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().
                detectLeakedClosableObjects().detectLeakedSqlLiteObjects().
                penaltyDeath().penaltyLog().build());

        Wcookie(context);

        //TODO:時間處理器Handler, Runnable（下方副函式）對象名稱爲runTimerStop, postDelayed用來指定時間結束時執行Runnable之對象(runTimerStop)
        Handler myHandler = new Handler();
        myHandler.postDelayed(runTimerStop, 15000);     //TODO:15秒

        if(cookieStr!=null){
        //TODO:removeCallbacks方法旨在刪除指定的Runnable對象，使執行緒對象停止運行
            myHandler.removeCallbacks(runTimerStop);
        }

        LV1 = (ListView)findViewById(R.id.LV1);
        et1 = (EditText)findViewById(R.id.et1);
        et2 = (EditText)findViewById(R.id.et2);
        et3 = (EditText)findViewById(R.id.et3);
        et4 = (EditText)findViewById(R.id.et4);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        //按鈕動作：新增資料
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] et0 = new String[]{et1.getText().toString(), et2.getText().toString(),
                        et3.getText().toString(), et4.getText().toString()};
                //TODO:進入Class: InsertPhpClass
                InsertPhpClass.inserting(et0, cookieStr, url);
                select(null);
            }
        });

        //TODO:選取資料（必須選取資料才能更新資料！！）
        LV1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent,View view, int position, long id){

                textview0 = (TextView) view.findViewById(R.id.text0);
                id_text = textview0.getText().toString();

                textview1 = (TextView) view.findViewById(R.id.text1);
                et1.setText(textview1.getText());

                textview2 = (TextView) view.findViewById(R.id.text2);
                et2.setText(textview2.getText());

                textview3 = (TextView) view.findViewById(R.id.text3);
                et3.setText(textview3.getText());

                textview4 = (TextView) view.findViewById(R.id.text4);
                et4.setText(textview4.getText());

            }
        });

        //更新資料
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] et0 = new String[] {id_text, et1.getText().toString(), et2.getText().toString(),
                        et3.getText().toString(),et4.getText().toString()};

                //TODO:進入Class: UpdatePhpClass
                UpdatePhpClass.updating(et0, cookieStr, url);
                select(null);

            }
        });

        //刪除資料
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] et0 = new String[] {id_text};

                //TODO:進入Class: DeletePhpClass
                DeletePhpClass.deleting(et0, cookieStr, url);
                select(null);
            }
        });


    }

    //Wcookies
    @SuppressLint("SetJavaScriptEnabled")
    private void Wcookie(Context context) {
        CookieSyncManager.createInstance(context);
        cookieManager = CookieManager.getInstance();
        webView = new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                cookieManager.setAcceptCookie(true);
                cookieStr = cookieManager.getCookie(url);
            }
        });

        webView.loadUrl(url);
        webView.clearHistory();
        webView.clearCache(true);

        cookieManager.removeAllCookie();
        cookieManager.removeSessionCookie();

    }

    //Runnable
    private Runnable runTimerStop = new Runnable() {
        @Override
        public void run() {

        //TODO:顯示資料文法：
        // select(null)
        // null爲查詢值
        // 不一定要此寫法，請配合網頁POST或者GET內容做對應
            select(null);
        }
    };

    //顯示資料
    public void select(String id){
        try{
            String r= DatabasePhpClass.DBstring(id, cookieStr, url);

            JSONArray jsonArray = new JSONArray(r);
            List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonData = jsonArray.getJSONObject(i);
                Map<String, Object> item = new HashMap<String, Object>();
                item.put("id", jsonData.getString("id"));
                item.put("name", jsonData.getString("name"));
                item.put("content1", jsonData.getString("content1"));
                item.put("content2", jsonData.getString("content2"));
                item.put("content3", jsonData.getString("content3"));
                items.add(item);        //TODO:新增到items
            }
            SimpleAdapter SA = new SimpleAdapter(context, items, R.layout.list_text,
                    new String[]{"id", "name", "content1", "content2", "content3", "content4"},
                    new int[]{R.id.text0, R.id.text0, R.id.text2, R.id.text3, R.id.text4});

            LV1.setAdapter(SA);

        }catch (Exception e){
            Log.e("log_tag",e.toString());
        }
    }
}





