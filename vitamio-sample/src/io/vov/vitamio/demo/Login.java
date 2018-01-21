package io.vov.vitamio.demo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

//import android.support.v7.app.AppCompatActivity;

//以上是我自己 import 的程式庫

public class Login extends Activity {

    String serverUrl = "http://140.131.114.155/login.php"; //檢查帳號的 php
    String account = "";
    String password = "";
    // String for LogCat documentation
   // final TableLayout tableLayout = (TableLayout) findViewById(R.id.tabLayout);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

    }

   public void BtnGotoSignIn(View view){
        //加入會員 AddMember

        Intent intent = new Intent();
        intent.setClass(Login.this, AddMember.class);
        startActivity(intent);


    }

    public void BtnGotoIndex(View view){
        //"檢查帳號" 的按鈕
        Button button = (Button)view;
        TextView accountView = (TextView)findViewById(R.id.Edtemail);    //尋找帳號的TextView
        TextView passwordView = (TextView)findViewById(R.id.EdtPassword);  //尋找密碼的TextView
        account = accountView.getText().toString().trim();      //帳號的字串
        password = passwordView.getText().toString().trim();      //帳號的字串
        //============================================================================
               /* 測試送過去的帳號 */
        Log.v("Account_BtnGotoIndex", account);
        //============================================================================
        new HttpGetRequestAsyncTask().execute(account, password);
        //宣告SharedPreferences紀錄的name
        SharedPreferences pref = getSharedPreferences("A", MODE_PRIVATE);
        //必須有一個edit來存
        SharedPreferences.Editor preEdt = pref.edit();
        //所要記錄的資料 (也可以是int), 第一個參數是該筆資料的name,後面是value
        preEdt.putString("A", account);

        //最後要commit
        preEdt.commit();


    }

    public class HttpGetRequestAsyncTask extends AsyncTask<String, Void, String> {
        //檢查帳號的php

        @Override
        protected String doInBackground(String... userData) {

            //============================================================================
               /* 測試送過去的帳號 */
            Log.v("Account_doInBackground",userData[0]);
            //============================================================================

            HttpURLConnection connection = null;

            try {
                URL url = new URL(serverUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestProperty("Accept-Charset", "UTF-8");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                connection.setRequestProperty("User-Agent", System.getProperty("http.agent", "Android " + Build.VERSION.RELEASE));

                //Send request
                String loginInfo = String.format("id=%s&pwd=%s",
                        URLEncoder.encode(userData[0], "UTF-8"),
                        URLEncoder.encode(userData[1], "UTF-8"));
                OutputStream wr = connection.getOutputStream();
                wr.write(loginInfo.getBytes("UTF-8"));
                wr.flush();
                wr.close();

                // Status code
                int status = connection.getResponseCode();
                Log.d("NetTag", "status code:" + status);

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String str = "";
                while ((str = br.readLine()) != null) {
                    sb.append(str);
                }
                connection.disconnect();
                return sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(String result) {

            String str = result;
            String str2 = "登入成功";

            String temp = str.substring(0,str.length());

            //============================================================================
               /* 測試送過去的帳號 */
            Log.v("Account_onPostExecute",temp);
            //============================================================================

            Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
          if(temp.equals(str2)){
              // Toast.makeText(getApplicationContext(), "123456789", Toast.LENGTH_LONG).show();
                //登入成功
               //============================================================================
               /* 測試送過去的帳號 */
               Log.v("Account_equals",temp);
               //============================================================================

               Intent intent = new Intent();

               intent.setClass(Login.this,MainActivity2.class);
               Bundle bundle = new Bundle();
               bundle.putString("key_show_String", account);
               intent.putExtras(bundle);
               startActivity(intent);


          }

            //============================================================================
               /* 測試送過去的帳號 */
            Log.v("Account_equalsNoSame",temp);
            //============================================================================

        }
    }


}

