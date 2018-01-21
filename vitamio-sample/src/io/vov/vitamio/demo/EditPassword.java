package io.vov.vitamio.demo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;

/**
 * Created by Luck on 2017/11/1.
 */

public class EditPassword extends Activity {
    String userid ;
    String ePassword,ePassword2,ePassword3;
    String yoururl = "http://140.131.114.155/updaMber.php";
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editpassword);


        Button BtnOk22 =  (Button) findViewById(R.id.BtnPassword);
        userid = getSharedPreferences("A", MODE_PRIVATE).getString("A", "");

        BtnOk22.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {

                EditText Old = (EditText) findViewById(R.id.EdtOld);    //尋找帳號的TextView
                ePassword = Old.getText().toString().trim();      //帳號的字串

                EditText New = (EditText) findViewById(R.id.EdtNew);  //尋找密碼的TextView
                ePassword2 = New.getText().toString().trim();

                EditText New2 = (EditText) findViewById(R.id.EdtNew2);
                ePassword3 = New2.getText().toString().trim();
              //  Toast.makeText(getApplicationContext(), ePassword+ePassword2+ePassword3, Toast.LENGTH_LONG).show();
                // TODO Auto-generated method stub
              /*  new Thread(new Runnable(){
                    @Override
                    public void run() {
                        Looper.prepare();
                        // TODO Auto-generated method stub
                        HttpClient client = new DefaultHttpClient();

                        HttpPost myPost = new HttpPost("http://140.131.114.155/updaMber.php");
                        try {
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("id",userid));
                            params.add(new BasicNameValuePair("opwd",ePassword));
                            params.add(new BasicNameValuePair("npwd1",ePassword2));
                            params.add(new BasicNameValuePair("npwd2",ePassword3));
                            myPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                            HttpResponse response = new DefaultHttpClient().execute(myPost);
                            Toast.makeText(getApplicationContext(), "ggg", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Looper.loop();
                    }}).start();*/
               new AddMemberAsyncTask().execute(userid,ePassword2,ePassword3,ePassword);

            }
        });



    }
    public class AddMemberAsyncTask extends AsyncTask<String, Void, String> {
        //檢查帳號的php

        @Override
        protected String doInBackground(String... userData) {

            HttpURLConnection connection = null;

            //============================================================================
               /* 測試送過去的帳號 */
            //Log.v("Addmember_BtnGotoLogin", userData[0]);
            //============================================================================

            try {
                URL url = new URL(yoururl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestProperty("Accept-Charset", "UTF-8");
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                connection.setRequestProperty("User-Agent", System.getProperty("http.agent", "Android " + Build.VERSION.RELEASE));
                //Send request

                String loginInfo = String.format("id=%s&npwd1=%s&npwd2=%s&opwd=%s",
                        URLEncoder.encode(userData[0], "UTF-8"),
                        URLEncoder.encode(userData[1], "UTF-8"),
                        URLEncoder.encode(userData[2], "UTF-8"),
                        URLEncoder.encode(userData[3], "UTF-8"));

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

            //============================================================================
               /* 測試送過去的帳號 */
            //  Log.v("Addmember_Post_res",result);
            //============================================================================
            str = result;
            //============================================================================
               /* 測試送過去的帳號 */
            // Log.v("Addmember_Post_str",str);
            //============================================================================
            //String temp = str.substring(1,str.length());
            //============================================================================
               /* 測試送過去的帳號 */
            // Log.v("Addmember_Post_temp",temp);
            //============================================================================
            Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
        }
    }




}
