package io.vov.vitamio.demo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;


/**
 * Created by 104509 on 2015/11/20.
 */
public class AddMember extends Activity {
    public LinearLayout radioButton;
    String yoururl = "http://140.131.114.155/registered.php";
    String str;
    private int year, monthOfYear, dayOfMonth, hourOfDay, minute;
    private DatePickerDialog dateDialog;
    String text="";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmember3);


        Button dateButton = (Button) findViewById(R.id.btnDate);
        final TextView aa = (TextView) findViewById(R.id.txtDate);


        Calendar calendar = Calendar.getInstance();
        year = calendar.get(calendar.YEAR);
        monthOfYear = calendar.get(calendar.MONTH);
        dayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
        dateDialog = new DatePickerDialog(this, R.style.AlertDialogTheme, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker arg0, int year, int monthOfYear,
                                  int dayOfMonth) {
                // 把获取的日期显示在文本框内，月份从0开始计数，所以要加1
                text = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                aa.setText(text);
            }
        }, year, monthOfYear, dayOfMonth); // 后面的三个参数对应于上面的年、月、日
        /**
         * 对日期选择器按钮设置监听事件
         */
        dateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // 点击日期选择器按钮时显示出日期对话框
                dateDialog.show();
            }
        });

    }
    public void BtnGotoLogin(View view){
        EditText edtEmail = (EditText)findViewById(R.id.edtEmail);    //尋找帳號的TextView
        String eMail = edtEmail.getText().toString().trim();  //帳號的字串

        EditText edtPassword = (EditText)findViewById(R.id.EdtPassword);    //尋找 EdtPassword
        String ePassword = edtPassword.getText().toString().trim();      //帳號的字串

        EditText edtPassword2 = (EditText)findViewById(R.id.EdtPassword2);    //尋找 EdtPassword
        String ePassword2 = edtPassword.getText().toString().trim();      //帳號的字串

        EditText edtName = (EditText)findViewById(R.id.EdtName);    //尋找 EdtID
        String eName = edtName.getText().toString().trim();      //帳號的字串

        EditText edtAccount = (EditText)findViewById(R.id.EdtAccount);    //尋找 EdtID
        String eAccount = edtAccount.getText().toString().trim();      //帳號的字串

        EditText edtAddress = (EditText)findViewById(R.id.EdtAddress);    //尋找 EdtID
        String eAddress = edtAddress.getText().toString().trim();      //帳號的字串

        EditText edtBirthday = (EditText)findViewById(R.id.EdtBirthday);    //尋找 EdtBirthday
        String eBirthday = text;     //帳號的字串

        EditText edtPhone = (EditText)findViewById(R.id.EdtPhone);    //尋找 EdtPhonew
        String ePhone = edtPhone.getText().toString().trim();      //帳號的字串

        EditText edtText = (EditText)findViewById(R.id.EdtText);    //尋找 EdtPhonew
        String eText = edtText.getText().toString().trim();      //帳號的字串
/*
        RadioButton rdbMan = (RadioButton)findViewById(R.id.RdbMan);    //尋找 RdbMan
        String rMan = "";      //帳號的字串
        if(rdbMan.isChecked()){
            rMan = "男";
        }else{
            rMan = "女";
        }
*/
       /* String rMan = "";      //帳號的字串
        rMan = "男";*/

        //============================================================================
               /* 測試送過去的帳號 */
        Log.v("Addmember_BtnGotoLogin",eMail);
        //============================================================================

        new AddMemberAsyncTask().execute(eMail, ePassword,ePassword2,eName,eAccount,eAddress, eBirthday, ePhone,eText);
        //if(str=="註冊成功") {
            Intent intent = new Intent();
            intent.setClass(AddMember.this, TestCamera.class);
            Bundle bundle = new Bundle();
            bundle.putString("key_show_String", eMail);
            intent.putExtras(bundle);
            startActivity(intent);
        //}
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

                String loginInfo = String.format("id=%s&pwd=%s&pwd2=%s&name=%s&account=%s&address=%s&birthday=%s&phone=%s&text=%s",
                        URLEncoder.encode(userData[0], "UTF-8"),
                        URLEncoder.encode(userData[1], "UTF-8"),
                        URLEncoder.encode(userData[2], "UTF-8"),
                        URLEncoder.encode(userData[3], "UTF-8"),
                        URLEncoder.encode(userData[4], "UTF-8"),
                        URLEncoder.encode(userData[5], "UTF-8"),
                        URLEncoder.encode(userData[6], "UTF-8"),
                        URLEncoder.encode(userData[7], "UTF-8"),
                        URLEncoder.encode(userData[8], "UTF-8"));

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
