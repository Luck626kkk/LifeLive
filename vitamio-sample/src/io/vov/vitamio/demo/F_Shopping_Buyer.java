package io.vov.vitamio.demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by User on 2017/8/3.
 */

public class F_Shopping_Buyer extends Activity {

    ArrayList<Actors> actorsList;
    F_Shopping_Buyer_Adapter adapter;
    String url = "http://140.131.114.155/dailNO.php";
    String liveid;
    String userid;
    Button MakerSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer);


        actorsList = new ArrayList<Actors>();
        ListView listview = (ListView)findViewById(R.id.list);
        adapter = new F_Shopping_Buyer_Adapter(getApplicationContext(), R.layout.buyer_v, actorsList);
        listview.setAdapter(adapter);
        MakerSure = (Button) findViewById(R.id.btnOk);



        userid = getSharedPreferences("A", MODE_PRIVATE).getString("A", "");
        new Thread(runnable).start();//啟動執行序runnable



    }
    Handler handler_Success = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("A");//取出key中的字串存入val
            //
          //  Toast.makeText(getApplication(), val, Toast.LENGTH_LONG).show();

            try {
                // Create the root JSONObject from the JSON string.
                JSONObject jsonRootObject = new JSONObject(val);

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = jsonRootObject.optJSONArray("Text");

                //Iterate the jsonArray and print the info of JSONObjects
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    Actors actor = new Actors();
                    ///actor.setMerPicture(jsonObject.getString("MerPicture"));
                    actor.setOrderID(jsonObject.getString("orderID"));
                    actor.setPro(jsonObject.getString("proName"));
                    actor.setPrice(jsonObject.getString("endPrice"));
                    actor.setSellerName(jsonObject.getString("sellerName"));
                    actor.setSellerPhone(jsonObject.getString("sellerPhone"));
                    actor.setImage(jsonObject.getString("photo"));
                    actor.setOrderDate(jsonObject.getString("sellDate"));
                    actor.setYesNo(jsonObject.getString("finish"));



                    actorsList.add(actor);
                    //String a =actorList.get(position).getOrdierID();
                }
                //tv3.setText(val);
                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



    };

    Handler handler_Error = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("A");
          //  Toast.makeText(getApplicationContext(), val, Toast.LENGTH_LONG).show();
        }
    };

    Handler handler_Nodata = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("A");
           // Toast.makeText(getApplicationContext(), val, Toast.LENGTH_LONG).show();
        }
    };

    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            //
            // TODO: http request.
            //

            Message msg = new Message();
            Bundle data = new Bundle();
            msg.setData(data);
            try
            {
                //連線到 url網址
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost method = new HttpPost(url);

                //傳值給PHP
                List<NameValuePair> vars=new ArrayList<NameValuePair>();
                vars.add(new BasicNameValuePair("id",userid));
                method.setEntity(new UrlEncodedFormEntity(vars, HTTP.UTF_8));

                //接收PHP回傳的資料
                HttpResponse response = httpclient.execute(method);
                HttpEntity entity = response.getEntity();


                if(entity != null){
                    data.putString("A", EntityUtils.toString(entity, "utf-8"));//如果成功將網頁內容存入key
                    handler_Success.sendMessage(msg);

                }
                else{
                    data.putString("A","無資料");
                    handler_Nodata.sendMessage(msg);
                }
            }
            catch(Exception e){
                data.putString("A","連線失敗");
                handler_Error.sendMessage(msg);
            }

        }
    };


}