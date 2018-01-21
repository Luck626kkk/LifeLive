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

import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;

public class F_Search_Live_Adapter extends ArrayAdapter<Actors> {

    ArrayList<Actors> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public F_Search_Live_Adapter(Context context, int resource, ArrayList<Actors> objects) {

        super(context, resource, objects);
        vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        actorList = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageview = (ImageView) v.findViewById(R.id.imageView);
            holder.tvTitle = (TextView) v.findViewById(R.id.Title);
            holder.tvClassName = (TextView) v.findViewById(R.id.Class);
            holder.tvName = (TextView) v.findViewById(R.id.Name);
            holder.tvProName = (TextView) v.findViewById(R.id.ProName);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
    /*holder.imageview.setImageResource(R.drawable.ic_launcher);
    new DownloadImageTask(holder.imageview).execute(actorList.get(position).getImage());*/
        holder.imageview.setImageResource(R.drawable.ic_launcher);
       // new ImageDownloadTask(holder.imageview).execute(actorList.get(position).getMerPicture());



        holder.tvTitle.setText("賣家標題: " + actorList.get(position).getTitle());
        holder.tvProName.setText("商品名: "+actorList.get(position).getProName());
        holder.tvClassName.setText("分類: "+actorList.get(position).getSort());
        holder.tvName.setText("賣家名稱: "+actorList.get(position).getName());
        new ImageDownloadTask(holder.imageview).execute(actorList.get(position).getImage());
        return v;

    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView tvTitle;
        public TextView tvProName;
        public TextView tvName;
        public TextView tvClassName;
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