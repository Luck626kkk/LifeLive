package io.vov.vitamio.demo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class F_Search extends Fragment {
    ListView lv;
    ArrayList<Actors> actorsList;
    F_Search_Adapter adapter;
    String liveid;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vw=inflater.inflate(R.layout.fragment2, container, false);
        lv=(ListView) vw.findViewById(R.id.list);

        actorsList = new ArrayList<Actors>();
        new F_Search.JSONAsynTask().execute("http://140.131.114.155/sort.php");
        adapter = new F_Search_Adapter(getActivity(), R.layout.fragment2_lv, actorsList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
                // TODO Auto-generated method stub
                liveid = actorsList.get(position).getClasss();
                //Toast.makeText(getContext(),liveid,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), F_Search_Live.class);
                //intent.setClass(getContext(), F_Search_Live.class);
                Bundle bundle = new Bundle();
                bundle.putString("liveid", liveid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });



        return  vw;
    }
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
                        actor.setClasss(object.getString("className"));


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
