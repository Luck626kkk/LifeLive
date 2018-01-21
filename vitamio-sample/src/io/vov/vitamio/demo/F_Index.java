package io.vov.vitamio.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.HttpGet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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


public class F_Index extends Fragment {
    ListView lv;
    ArrayList<Actors> actorsList;
    F_Index_Adapter adapter;
    private static final String MEDIA = "media";
    private static final int STREAM_VIDEO = 5;
    String gg;
    String userid ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vw=inflater.inflate(R.layout.fragment1, container, false);
        lv=(ListView) vw.findViewById(R.id.list);

        actorsList = new ArrayList<Actors>();
        new JSONAsynTask().execute("http://140.131.114.155/main.php");
        adapter = new F_Index_Adapter(getActivity(), R.layout.fragment1_lv, actorsList);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                //Toast.makeText(getActivity(), "i click", Toast.LENGTH_LONG).show();
                //Log.d("123","i click");
               /* Intent in = new Intent(getActivity(),
                        SingleContactActivity.class);
                in.putExtra("title", actorsList.get(position).getName());
                in.putExtra("author", actorsList.get(position).getCountry());
                in.putExtra("data", actorsList.get(position).getDob());
                in.putExtra("content", actorsList.get(position).getDescription());
                in.putExtra("category", actorsList.get(position).getHeight());
                //in.putExtra("image", actorsList.get(position).getImage());
                startActivity(in);
                String Ip = actorList.get(position).getName();*/

                Intent intent = new Intent(getActivity(), MediaPlayerDemo_Video.class);
                intent.putExtra("ip", actorsList.get(position).getIp());
                intent.putExtra("liveID", actorsList.get(position).getLiveid());
                intent.putExtra("Lp", actorsList.get(position).getLp());
                intent.putExtra("StartPrice", actorsList.get(position).getStartPrice());
                intent.putExtra(MEDIA, STREAM_VIDEO);
                startActivity(intent);

            }
        });

        return  vw;
    }

      //  return inflater.inflate(R.layout.fragment1, container, false);
      class JSONAsynTask extends AsyncTask<String, Void, Boolean> {
          String result;
          ProgressDialog dialog;


          @Override
          protected void onPreExecute() {
              super.onPreExecute();
              dialog = new ProgressDialog(getActivity());
              dialog.setMessage("Loading, please wait");
              dialog.setTitle("Connecting server");
              dialog.show();
              dialog.setCancelable(false);
          }

          @Override
          protected Boolean doInBackground(String... urls) {


              try {


                  HttpGet httppost = new HttpGet(urls[0]);
                  HttpClient httpclient = new DefaultHttpClient();
                  HttpResponse response = httpclient.execute(httppost);


                  int status = response.getStatusLine().getStatusCode();

                  if (status == 200) {
                      HttpEntity entity = response.getEntity();
                      String data = EntityUtils.toString(entity);


                      JSONObject jsono = new JSONObject(data);
                      JSONArray jarray = jsono.getJSONArray("Text");

                      for (int i = 0; i < jarray.length(); i++) {
                          JSONObject object = jarray.getJSONObject(i);

                          Actors actor = new Actors();

                         /* actor.setName(object.getString("name"));
                          actor.setDescription(object.getString("description"));
                          actor.setDob(object.getString("dob"));
                          actor.setCountry(object.getString("country"));
                          actor.setHeight(object.getString("height"));
                          actor.setSpouse(object.getString("spouse"));
                          actor.setChildren(object.getString("children"));*/
                          actor.setLiveid(object.getString("liveID"));
                          actor.setTitle(object.getString("title"));
                          actor.setProName(object.getString("proName"));
                          actor.setName(object.getString("name"));
                          actor.setImage(object.getString("photo"));
                          actor.setSort(object.getString("className"));
                          actor.setIp(object.getString("ip"));
                          actor.setLp(object.getString("minPrice"));
                          actor.setStartPrice(object.getString("startPrice"));


                          actorsList.add(actor);
                      }
                      return true;
                  }



              } catch (ParseException e1) {
                  e1.printStackTrace();
              } catch (IOException e) {
                  e.printStackTrace();
              } catch (JSONException e) {
                  e.printStackTrace();
              }
              return false;

          }

          protected void onPostExecute(Boolean result) {

              dialog.dismiss();
              adapter.notifyDataSetChanged();
              if(result == false)
                  Toast.makeText(getActivity(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

          }
      }

}
