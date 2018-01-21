package io.vov.vitamio.demo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class F_Create extends Fragment {
    private Button mlocalvideo;
    private Button mlocalvideoSurface;
    private Button mstreamvideo;
    private Button mlocalaudio;
    private Button mresourcesaudio;
    private static final String MEDIA = "media";
    private static final int LOCAL_AUDIO = 1;
    private static final int STREAM_AUDIO = 2;
    private static final int RESOURCES_AUDIO = 3;
    private static final int LOCAL_VIDEO = 4;
    private static final int STREAM_VIDEO = 5;
    private static final int RESOURCES_VIDEO = 6;
    private static final int LOCAL_VIDEO_SURFACE = 7;

    private EditText edtTitle;
    private EditText edtProName;
    private EditText edtClasss;
    private EditText edtTime;
    private EditText edtAbout;
    private EditText edtStart;
    private EditText edtLowPrice;
    String yoururl = "http://140.131.114.155/live.php";
    String userid;
    String aClass;
    String eTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vw=inflater.inflate(R.layout.fragment5, container, false);
        mstreamvideo = (Button) vw.findViewById(R.id.btnCreate);
        mstreamvideo.setOnClickListener(mStreamVideoListener);

        mlocalvideo = (Button) vw.findViewById(R.id.btnWatch);
        mlocalvideo.setOnClickListener(mLocalVideoListener);

        edtTitle = (EditText)vw.findViewById(R.id.edtTtile);
        edtProName = (EditText)vw.findViewById(R.id.edtProName);
        //edtClasss = (EditText)vw.findViewById(R.id.edtClass);
       // edtTime = (EditText)vw.findViewById(R.id.edtTime);
        edtAbout = (EditText)vw.findViewById(R.id.edtAbout);
        edtStart = (EditText)vw.findViewById(R.id.edtStart);
        edtLowPrice = (EditText)vw.findViewById(R.id.edtLowPrice);
        userid = getActivity().getSharedPreferences("A", MODE_PRIVATE).getString("A", "");

        Spinner spinner = (Spinner)vw.findViewById(R.id.spinner);
        final String[] lunch = {"通訊類", "數位類", "生活類", "時尚類", "食品類","3C類","家電類"};
        ArrayAdapter<String> lunchList = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                lunch);
        spinner.setAdapter(lunchList);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), "你選的是" + lunch[position], Toast.LENGTH_SHORT).show();
                aClass = lunch[position].toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner spinner2 = (Spinner)vw.findViewById(R.id.spinner2);
        final String[] lunch2 = {"1","10", "20", "30", "40", "50"};
        ArrayAdapter<String> lunchList2 = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                lunch2);
        spinner2.setAdapter(lunchList2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                eTime = lunch2[position].toString();
               // Toast.makeText(getActivity(), "你選的是" + eTime, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return vw;



    }
    private View.OnClickListener mStreamVideoListener = new View.OnClickListener() {
        public void onClick(View v) {
            String ccc= "";
            if(aClass.equals("通訊類")){
                ccc="1";
            }else if (aClass.equals("數位類")){
                ccc="2";
            }else if (aClass.equals("生活類")){
                ccc="3";
            }else if (aClass.equals("時尚類")) {
                ccc = "4";
            }else if (aClass.equals("食品類")) {
                ccc = "5";
            }else if (aClass.equals("3C類")) {
                ccc = "6";
            }else if (aClass.equals("家電類")) {
                ccc = "7";
            }

            String eTitle = edtTitle.getText().toString().trim();  //帳號的字串
            String eProName = edtProName.getText().toString().trim();  //帳號的字串
            //String eClasss  = edtClasss.getText().toString().trim();  //帳號的字串
            String eClasss  = ccc;
            String endTime  ="10";
            //String eTime = edtTime.getText().toString().trim();  //帳號的字串
            String eAbout = edtAbout.getText().toString().trim();  //帳號的字串
            String eStart = edtStart.getText().toString().trim();  //帳號的字串
            String eLp = edtLowPrice.getText().toString().trim();  //帳號的字串
            edtTitle.setText("");
            edtProName.setText("");
            edtAbout.setText("");
            edtStart.setText("");
            edtLowPrice.setText("");
            new F_Create.AddMemberAsyncTask().execute(userid, eTitle,eProName,eClasss, eTime,eStart, eLp);
            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.putExtra("time", eTime);
            intent.putExtra("lp", eLp);
            intent.putExtra("start", eStart);
            //intent.putExtra(MEDIA, STREAM_VIDEO);
            startActivity(intent);

        }
    };
    private View.OnClickListener mLocalVideoListener = new View.OnClickListener() {
		public void onClick(View v) {


			/*Intent intent = new Intent(getContext(), MediaPlayerDemo_Video.class);
            intent.putExtra(MEDIA, STREAM_VIDEO);
			startActivity(intent);*/

		}
	};
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

                String loginInfo = String.format("id=%s&title=%s&proName=%s&classID=%s&countTime=%s&startPrice=%s&minPrice=%s",
                        URLEncoder.encode(userData[0], "UTF-8"),
                        URLEncoder.encode(userData[1], "UTF-8"),
                        URLEncoder.encode(userData[2], "UTF-8"),
                        URLEncoder.encode(userData[3], "UTF-8"),
                        URLEncoder.encode(userData[4], "UTF-8"),
                        URLEncoder.encode(userData[5], "UTF-8"),
                        URLEncoder.encode(userData[6], "UTF-8"));

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
            String str = result;
            //============================================================================
               /* 測試送過去的帳號 */
            // Log.v("Addmember_Post_str",str);
            //============================================================================
            //String temp = str.substring(1,str.length());
            //============================================================================
               /* 測試送過去的帳號 */
            // Log.v("Addmember_Post_temp",temp);
            //============================================================================
            //Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
        }
    }

}
