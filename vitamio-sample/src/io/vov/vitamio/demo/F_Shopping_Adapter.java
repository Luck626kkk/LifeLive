package io.vov.vitamio.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;

public class F_Shopping_Adapter extends ArrayAdapter<Actors> {

    ArrayList<Actors> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;


    public F_Shopping_Adapter(Context context, int resource, ArrayList<Actors> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        actorList = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new F_Shopping_Adapter.ViewHolder();
            v = vi.inflate(Resource, null);
            //holder.imageview = (ImageView) v.findViewById(R.id.imageView);
            holder.product = (TextView) v.findViewById(R.id.Product);
            holder.sellname = (TextView) v.findViewById(R.id.SellName);
            holder.sellphone = (TextView) v.findViewById(R.id.Phone);
            holder.total = (TextView) v.findViewById(R.id.Price);
            holder.date = (TextView) v.findViewById(R.id.gotdate);
            v.setTag(holder);
        } else {
            holder = (F_Shopping_Adapter.ViewHolder) v.getTag();
        }
    /*holder.imageview.setImageResource(R.drawable.ic_launcher);
    new DownloadImageTask(holder.imageview).execute(actorList.get(position).getImage());*/
       // holder.imageview.setImageResource(R.drawable.ic_launcher);
        // new ImageDownloadTask(holder.imageview).execute(actorList.get(position).getMerPicture());



        holder.product.setText( actorList.get(position).getProName2());
        holder.total.setText(actorList.get(position).getTotal());
        holder.sellname.setText(actorList.get(position).getSellName());
        holder.sellphone.setText(actorList.get(position).getSellPhone());
        holder.date.setText(actorList.get(position).getDate());
       // new F_Search_Live_Adapter.ImageDownloadTask(holder.imageview).execute(actorList.get(position).getImage());
        return v;

    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView product;
        public TextView total;
        public TextView date;
        public TextView sellname;
        public TextView sellphone;
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
   /* public static class ImageDownloadTask extends AsyncTask<String, Void, Bitmap> {
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
    }*/

}
