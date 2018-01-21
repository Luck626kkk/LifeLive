package io.vov.vitamio.demo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class F_Member extends Fragment {
    Button logout,editpassword;
    ImageView bkind2;

    Button GetImageFromGalleryButton, UploadImageOnServerButton, UseCamara;

    ImageView ShowSelectedImage;

    EditText GetImageName;

    Bitmap FixBitmap;

    String ImageTag = "image_tag" ;

    String ImageName = "image_data" ;

    String ServerUploadPath ="http://140.131.114.155/updaMberPic.php" ;

    ProgressDialog progressDialog ;

    ByteArrayOutputStream byteArrayOutputStream ;

    byte[] byteArray ;

    String ConvertImage ;

    String GetImageNameFromEditText;

    HttpURLConnection httpURLConnection ;

    URL url;

    OutputStream outputStream;

    BufferedWriter bufferedWriter ;

    int RC ;

    BufferedReader bufferedReader ;

    StringBuilder stringBuilder;

    boolean check = true;

    String id;
    Uri outputFileUri;
    Camera camera;
    String userid ;
    ImageView img;
    TextView name;
    String url2 = "http://140.131.114.155/memberPage.php";
    String val;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vw=inflater.inflate(R.layout.fragment4, container, false);


        userid = getActivity().getSharedPreferences("A", MODE_PRIVATE).getString("A", "");
       // Toast.makeText(getActivity(), userid, Toast.LENGTH_LONG).show();
        logout=(Button)vw.findViewById(R.id.btnLogout);
        name =(TextView) vw.findViewById(R.id.txtName);
        logout.setOnClickListener(mlogoutListener);
        bkind2 = (ImageView) vw.findViewById(R.id.imageView);
        editpassword=(Button)vw.findViewById(R.id.BtnEditPassword);




        byteArrayOutputStream = new ByteArrayOutputStream();



        camera = null;

        bkind2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    dialog = new AlertDialog.Builder(getContext());
                }
                dialog.setTitle("更換大頭照");
                dialog.setMessage("選擇相機/相簿");
                dialog.setNegativeButton("相簿",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub

                        Intent intent = new Intent();

                        intent.setType("image/*");

                        intent.setAction(Intent.ACTION_GET_CONTENT);

                        F_Member.this.startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);


                    }

                });
                dialog.setPositiveButton("相機",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        File tmpFile = new File( Environment.getExternalStorageDirectory(), "image.jpg");
                        outputFileUri = Uri.fromFile(tmpFile);
                        // Intent intent =  new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE_SECURE);    //利用intent去開啟android本身的照相介面
                        Intent intent =  new Intent( android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        // intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, outputFileUri);
                        startActivityForResult(intent, 5);
                       // Toast.makeText(getActivity(), "相機",Toast.LENGTH_SHORT).show();
                    }

                });
                dialog.setNeutralButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getActivity(), "取消",Toast.LENGTH_SHORT).show();
                    }

                });
                dialog.show();
            }});

        editpassword.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(getContext(),EditPassword.class);
                startActivity(intent);

            }



        });



        new Thread(runnable).start();//啟動執行序runnable
        return vw;

    }
    private View.OnClickListener mlogoutListener = new View.OnClickListener() {
        public void onClick(View v) {

                SharedPreferences aa=getActivity().getSharedPreferences("A",MODE_PRIVATE);
                SharedPreferences.Editor e=aa.edit();
                e.clear();
                e.commit();

            Intent intent = new Intent(getContext(),Login.class);

            startActivity(intent);
            getActivity().finish();
        }
    };


    @Override
    public void onActivityResult(int RC, int RQC, Intent I) {

        super.onActivityResult(RC, RQC, I);
      //  Toast.makeText(getActivity(), "9453",Toast.LENGTH_SHORT).show();
        if ((RC == 1) && RQC == RESULT_OK && I != null && I.getData() != null) {

            Uri uri = I.getData();

            try {

                FixBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                bkind2.setImageBitmap(FixBitmap);

                UploadImageToServer();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        if(RC == 5 ){
            FixBitmap= (Bitmap) I.getExtras().get("data");
            bkind2.setImageBitmap(FixBitmap);
            //Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
            UploadImageToServer();


        }
    }



    public void UploadImageToServer(){

        FixBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        byteArray = byteArrayOutputStream.toByteArray();

        ConvertImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

                progressDialog = ProgressDialog.show(getActivity(),"Image is Uploading","Please Wait",false,false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                progressDialog.dismiss();

                Toast.makeText(getActivity(),string1,Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String,String> HashMapParams = new HashMap<String,String>();

                HashMapParams.put(ImageTag, userid);

                HashMapParams.put(ImageName, ConvertImage);

                String FinalData = imageProcessClass.ImageHttpRequest(ServerUploadPath, HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass{

        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {
                url = new URL(requestURL);

                httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(20000);

                httpURLConnection.setConnectTimeout(20000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(bufferedWriterDataFN(PData));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                RC = httpURLConnection.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReader.readLine()) != null){

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            stringBuilder = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (check)
                    check = false;
                else
                    stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilder.toString();
        }

    }



    Handler handler_Success = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            val = data.getString("key");//取出key中的字串存入val
          //  Toast.makeText(getContext(), val, Toast.LENGTH_LONG).show();
            String name2 ,agg;
            try {
                // Create the root JSONObject from the JSON string.
                JSONObject jsonRootObject = new JSONObject(val);

                //Get the instance of JSONArray that contains JSONObjects
                JSONArray jsonArray = jsonRootObject.optJSONArray("Text");

                //Iterate the jsonArray and print the info of JSONObjects
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);


                    name2 = jsonObject.optString("name").toString();
                    agg = jsonObject.optString("photo").toString();
                    //Int = jsonObject.optString("Int").toString();
                    name.setText(name2);
                    GetXMLTask task = new GetXMLTask();
                    // Execute the task
                    task.execute(new String(agg));

                }


                //tv3.setText(val);
                //  adapter.notifyDataSetChanged();
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
            val = data.getString("key");
            //	Toast.makeText(getApplicationContext(), val, Toast.LENGTH_LONG).show();
        }
    };

    Handler handler_Nodata = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            val = data.getString("key");
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
                HttpPost method = new HttpPost(url2);

                //傳值給PHP
                List<NameValuePair> vars=new ArrayList<NameValuePair>();
                vars.add(new BasicNameValuePair("id",userid));
                method.setEntity(new UrlEncodedFormEntity(vars, HTTP.UTF_8));

                //接收PHP回傳的資料
                HttpResponse response = httpclient.execute(method);
                HttpEntity entity = response.getEntity();


                if(entity != null){
                    data.putString("key", EntityUtils.toString(entity, "utf-8"));//如果成功將網頁內容存入key
                    handler_Success.sendMessage(msg);

                }
                else{
                    data.putString("key","無資料");
                    handler_Nodata.sendMessage(msg);
                }
            }
            catch(Exception e){
                data.putString("key","連線失敗");
                handler_Error.sendMessage(msg);
            }

        }
    };
    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            bkind2.setImageBitmap(result);
        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.
                        decodeStream(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }

    }
}




