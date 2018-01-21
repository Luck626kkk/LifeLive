package io.vov.vitamio.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Looper;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URLConnection;
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

public class F_Shopping_Buyer_Adapter extends ArrayAdapter<Actors> {
    public boolean isEnabled;
    ArrayList<Actors> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;
    String aa;

    public F_Shopping_Buyer_Adapter(Context context, int resource, ArrayList<Actors> objects) {
        super(context, resource, objects);

        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        actorList = objects;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new F_Shopping_Buyer_Adapter.ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageview = (ImageView) v.findViewById(R.id.imageView);
            holder.OrderID = (TextView) v.findViewById(R.id.OrderID);
            holder.OrdierDate = (TextView) v.findViewById(R.id.OdrerDate);
            holder.SellerName = (TextView) v.findViewById(R.id.SellerName);
            holder.SellerPhone = (TextView) v.findViewById(R.id.SellerPhone);
            holder.Finish = (TextView) v.findViewById(R.id.YesNo);
            holder.Price = (TextView) v.findViewById(R.id.Price);
            holder.Pro = (TextView) v.findViewById(R.id.ProName);
            holder.MakeSure =(Button) v.findViewById(R.id.BtnOK);


            v.setTag(holder);
        } else {
            holder = (F_Shopping_Buyer_Adapter.ViewHolder) v.getTag();
        }
             holder.imageview.setImageResource(R.drawable.ic_launcher);
            //new DownloadImageTask(holder.imageview).execute(actorList.get(position).getImage());
            // holder.imageview.setImageResource(R.drawable.ic_launcher);


        holder.OrderID.setText("訂單編號:"+actorList.get(position).getOrdierID());
        holder.Pro.setText("商品名:"+actorList.get(position).getPro());
        holder.Price.setText("總金額:"+actorList.get(position).getPrice());
        holder.SellerName.setText("賣家名稱:"+actorList.get(position).getSellerName());
        holder.SellerPhone.setText("賣家電話:"+actorList.get(position).getSellerPhone());
        holder.OrdierDate.setText("訂單日期:"+actorList.get(position).getOrderDate());
        holder.Finish.setText("狀態:"+actorList.get(position).getYesNo());
        holder.MakeSure.setText(actorList.get(position).getYesNo());




       // new F_Search_Live_Adapter.ImageDownloadTask(holder.imageview).execute(actorList.get(position).getImage());
        holder.MakeSure.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                aa = actorList.get(position).getOrdierID();

               // Toast.makeText(getContext(), aa, Toast.LENGTH_LONG).show();
              new Thread(new Runnable(){

                    @Override
                    public void run() {
                        Looper.prepare();
                        // TODO Auto-generated method stub
                        HttpClient client = new DefaultHttpClient();

                        HttpPost myPost = new HttpPost("http://140.131.114.155/finish.php");
                        try {
                            List<NameValuePair> params = new ArrayList<NameValuePair>();
                            params.add(new BasicNameValuePair("orderID",aa));
                            params.add(new BasicNameValuePair("finish","1"));
                            myPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
                            HttpResponse response = new DefaultHttpClient().execute(myPost);
                           // Toast.makeText(getContext(), aa, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Looper.loop();
                    }}).start();

                holder.MakeSure.setText(actorList.get(position).getYesNo());

            }



        });

        new ImageDownloadTask(holder.imageview).execute(actorList.get(position).getImage());
        return v;

    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView OrderID;
        public TextView Pro;
        public TextView Price;
        public TextView SellerName;
        public TextView SellerPhone;
        public TextView OrdierDate;
        public TextView Finish;
        public Button MakeSure;
   /* public TextView tvHeight;
    public TextView tvSpouse;
    public TextView tvChildren;*/

    }



    /* private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }

    }*/
    public static class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
        //    private static final String LOG_TAG = ImageDownloadTask.class.getName();
        private ImageView bmImage;
        private static LruCache imageCache;
        private static final int CACHE_SIZE = 1; //1MB

        public ImageDownloadTask(ImageView bmImage) {
            this.bmImage = bmImage;

            // 設�?????快�??
            if (imageCache == null) {
                imageCache =  new LruCache(CACHE_SIZE * 1024 * 1024);
            }

        }

        protected Bitmap doInBackground(String... params) {
            if (imageCache.get(params[0]) == null) {
                Bitmap mIcon11 = null;
                URLConnection urlConnection = null;
                InputStream in = null;
                try {
                    urlConnection = new java.net.URL(params[0]).openConnection();
                    urlConnection.setConnectTimeout(3 * 1000);
                    urlConnection.setReadTimeout(10 * 1000);
                    in = urlConnection.getInputStream();
                    mIcon11 = BitmapFactory.decodeStream(in);

                    // �???已�?�???????快�??
                    imageCache.put(params[0], mIcon11);
                    in.close();
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }

                return mIcon11;
            } else {
                return (Bitmap) imageCache.get(params[0]);

            }

        }

        protected void onPostExecute(Bitmap result) {
            super.onPreExecute();

            // 設�??????�件
            bmImage.setImageBitmap(result);
            this.bmImage = null;
        }
    }


}
