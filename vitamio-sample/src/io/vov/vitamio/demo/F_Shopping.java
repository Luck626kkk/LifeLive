package io.vov.vitamio.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class F_Shopping extends Fragment {
    ArrayList<Actors> actorsList;
    F_Shopping_Adapter adapter;
    String url = "http://140.131.114.155/dailNo.php";
    String buyerid="a456";
    Button buyer,seller;
    String rtmpUrl;
   /* public static F_Shopping newInstance(String A) {
        Bundle args = new Bundle();

        // Getting values from MyFragmentPagerAdapter.
        // You have to set a key for using the value

        args.putString("ARGS_TEST", A);
        F_Shopping fragment = new F_Shopping();
        fragment.setArguments(args);
        return fragment;
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vw=inflater.inflate(R.layout.fragment3, container, false);
        //email=getArguments().getString("ARGS_TEST");
        buyer=(Button) vw.findViewById(R.id.BtnBuyer);
        seller=(Button) vw.findViewById(R.id.BtnSeller);
        rtmpUrl =  getActivity().getSharedPreferences("Yasea", MODE_PRIVATE).getString("rtmpUrl", "no rtmpurl");
       // Toast.makeText(getActivity(), rtmpUrl, Toast.LENGTH_LONG).show();
        buyer.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(getContext(), F_Shopping_Buyer.class);
                startActivity(intent);

            }

        });
        seller.setOnClickListener(new Button.OnClickListener(){

            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                Intent intent = new Intent();
                intent.setClass(getContext(), F_Shopping_Seller.class);
                startActivity(intent);

            }

        });
        // actorsList = new ArrayList<Actors>();
      //  ListView listview = (ListView)vw.findViewById(R.id.list);
        //adapter = new F_Shopping_Adapter(getActivity(), R.layout.fragment3_lv, actorsList);

        //listview.setAdapter(adapter);
      //  new Thread(runnable).start();//啟動執行序runnable
        return vw;
    }





  /*  Handler handler_Success = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("A");//取出key中的字串存入val
            //
            Toast.makeText(getActivity(), val, Toast.LENGTH_LONG).show();

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
                    actor.setProName2(jsonObject.getString("proName"));
                    actor.setTotal(jsonObject.getString("endPrice"));
                    actor.setSellName(jsonObject.getString("sellerName"));
                    actor.setDate(jsonObject.getString("sellDate"));
                    actor.setSellPhone(jsonObject.getString("sellerPhone"));

                    actorsList.add(actor);
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
            Toast.makeText(getActivity(), val, Toast.LENGTH_LONG).show();
        }
    };

    Handler handler_Nodata = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("A");
            Toast.makeText(getActivity(), val, Toast.LENGTH_LONG).show();
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
                vars.add(new BasicNameValuePair("id",buyerid));
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
    };*/





}
