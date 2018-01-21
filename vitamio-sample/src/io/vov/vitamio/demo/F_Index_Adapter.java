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

public class F_Index_Adapter extends ArrayAdapter<Actors> {

    ArrayList<Actors> actorList;
    LayoutInflater vi;
    int Resource;
    ViewHolder holder;

    public F_Index_Adapter(Context context, int resource, ArrayList<Actors> objects) {
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
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageview = (ImageView) v.findViewById(R.id.imageView);
            holder.tvTitle = (TextView) v.findViewById(R.id.Title);
            holder.tvClassName = (TextView) v.findViewById(R.id.Class);
            holder.tvName = (TextView) v.findViewById(R.id.Name);
            holder.tvProName = (TextView) v.findViewById(R.id.ProName);
         /*   holder.tvName = (TextView) v.findViewById(R.id.tvName);
            holder.tvDescription = (TextView) v.findViewById(R.id.tvDescriptionn);
            holder.tvDOB = (TextView) v.findViewById(R.id.tvDateOfBirth);
            holder.tvCountry = (TextView) v.findViewById(R.id.tvCountry);
            holder.tvHeight = (TextView) v.findViewById(R.id.tvHeight);
            holder.tvSpouse = (TextView) v.findViewById(R.id.tvSpouse);
            holder.tvChildren = (TextView) v.findViewById(R.id.tvChildren);*/
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.imageview.setImageResource(R.drawable.ic_launcher);
        new DownloadImageTask(holder.imageview).execute(actorList.get(position).getImage());
        holder.tvTitle.setText("賣家標題: " + actorList.get(position).getTitle());
        holder.tvProName.setText("商品名: "+actorList.get(position).getProName());
        holder.tvClassName.setText("分類: "+actorList.get(position).getSort());
        holder.tvName.setText("賣家名稱: "+actorList.get(position).getName());
        /*holder.tvName.setText(actorList.get(position).getName());
        holder.tvDescription.setText(actorList.get(position).getDescription());
        holder.tvDOB.setText("B'day: " + actorList.get(position).getDob());
        holder.tvCountry.setText(actorList.get(position).getCountry());
        holder.tvHeight.setText("Height: " + actorList.get(position).getHeight());
        holder.tvSpouse.setText("Spouse: " + actorList.get(position).getSpouse());
        holder.tvChildren.setText("Children: " + actorList.get(position).getChildren());*/
        return v;

    }

    static class ViewHolder {
        public ImageView imageview;
        public TextView tvTitle;
        public TextView tvProName;
        public TextView tvName;
        public TextView tvClassName;
       /* public TextView tvName;
        public TextView tvDescription;
        public TextView tvDOB;
        public TextView tvCountry;
        public TextView tvHeight;
        public TextView tvSpouse;
        public TextView tvChildren;*/

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
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

    }
}
