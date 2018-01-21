/*
 * Copyright (C) 2013 yixia.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.vov.vitamio.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.MediaPlayer.OnBufferingUpdateListener;
import io.vov.vitamio.MediaPlayer.OnCompletionListener;
import io.vov.vitamio.MediaPlayer.OnPreparedListener;
import io.vov.vitamio.MediaPlayer.OnVideoSizeChangedListener;
import io.vov.vitamio.Vitamio;

import static com.loopj.android.http.AsyncHttpClient.log;

	public class MediaPlayerDemo_Video extends Activity implements OnBufferingUpdateListener, OnCompletionListener,
			OnPreparedListener, OnVideoSizeChangedListener, SurfaceHolder.Callback , ValueEventListener {

	private static final String TAG = "MediaPlayerDemo";
	private int mVideoWidth;
	private int mVideoHeight;
	private MediaPlayer mMediaPlayer;
	private SurfaceView mPreview;
	private SurfaceHolder holder;
	private String path;
	private Bundle extras;
	private static final String MEDIA = "media";
	private static final int LOCAL_AUDIO = 1;
	private static final int STREAM_AUDIO = 2;
	private static final int RESOURCES_AUDIO = 3;
	private static final int LOCAL_VIDEO = 4;
	private static final int STREAM_VIDEO = 5;
	private boolean mIsVideoSizeKnown = false;
	private boolean mIsVideoReadyToBePlayed = false;

	private Button btnOK;
	private EditText edtOK;
	private TextView Price;
	private TextView Time;
	String StartPrice;
	String Total;
	long a;
	String ip;
	String Liveid ;
	String url = "http://140.131.114.155/liveTime.php";
	String val;
	String userid;
	String Lp;

	private TextView Show,Count;
	private EditText A;
	private Button Goon;
	private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
	private DatabaseReference mRootReference = firebaseDatabase.getReference();
	private DatabaseReference  MyPirce = mRootReference.child("price");
	private DatabaseReference  GetProid = mRootReference.child("GetProid");
	DatabaseReference rootRef,demoRef;
	String GetProName;
	String endPrice;
	/**
	 *
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		Vitamio.isInitialized(getApplicationContext());
		setContentView(R.layout.mediaplayer_2);
		userid = getSharedPreferences("A", MODE_PRIVATE).getString("A", "");
		Bundle bundle0311 =this.getIntent().getExtras();
		ip = bundle0311.getString("ip");
		Liveid =bundle0311.getString("liveID");
		Lp =bundle0311.getString("Lp");
		StartPrice =bundle0311.getString("StartPrice");
		//Toast.makeText(getApplicationContext(),Liveid,Toast.LENGTH_LONG).show();
		userid = getSharedPreferences("A", MODE_PRIVATE).getString("A", "");
		mPreview = (SurfaceView) findViewById(R.id.surface);
		holder = mPreview.getHolder();
		holder.addCallback(this);
		holder.setFormat(PixelFormat.RGBA_8888);
		extras = getIntent().getExtras();
		/*GlobalVariable gv = (GlobalVariable)getApplicationContext();
		rtmpUrl=gv.getTest();
		Toast.makeText(MediaPlayerDemo_Video.this, rtmpUrl, Toast.LENGTH_LONG).show();*/
		Show = (TextView) findViewById(R.id.txtPrice);
		Count = (TextView) findViewById(R.id.txtTime);
		A = (EditText)findViewById(R.id.edtGoPrice);
		Goon = (Button)findViewById(R.id.btnGoPrice);

		rootRef = FirebaseDatabase.getInstance().getReference();
		demoRef = rootRef.child(Liveid);
		Show.setText("$"+StartPrice);
		Total = StartPrice;
		new Thread(runnable2).start();//啟動執行序runnable

	}

	private void playVideo(Integer Media) {
		doCleanUp();

		try {

			switch (Media) {
			case LOCAL_VIDEO:
				path = "";

				/*
				 * TODO: Set the path variable to a local media file path.
				 */
				if (path == "") {
					// Tell the user to provide a media file URL.
					Toast.makeText(MediaPlayerDemo_Video.this, "Please edit MediaPlayerDemo_Video Activity, " + "and set the path variable to your media file path." + " Your media file must be stored on sdcard.", Toast.LENGTH_LONG).show();
					return;
				}
				break;
			case STREAM_VIDEO:
				/*
				 * TODO: Set path variable to progressive streamable mp4 or
				 * 3gpp format URL. Http protocol should be used.
				 * Mediaplayer can only play "progressive streamable
				 * contents" which basically means: 1. the movie atom has to
				 * precede all the media data atoms. 2. The clip has to be
				 * reasonably interleaved.
				 * 
				 */
				//path="http://gslb.miaopai.com/stream/3D~8BM-7CZqjZscVBEYr5g__.mp4";

					path=ip;

				if (path == "") {
					// Tell the user to provide a media file URL.
					Toast.makeText(MediaPlayerDemo_Video.this, "Please edit MediaPlayerDemo_Video Activity," + " and set the path variable to your media file URL.", Toast.LENGTH_LONG).show();
					return;
				}

				break;

			}

			// Create a new media player and set the listeners
			mMediaPlayer = new MediaPlayer(this);
			mMediaPlayer.setDataSource(path);
			mMediaPlayer.setDisplay(holder);
			mMediaPlayer.prepareAsync();
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
			mMediaPlayer.setOnVideoSizeChangedListener(this);
			setVolumeControlStream(AudioManager.STREAM_MUSIC);





		} catch (Exception e) {
			Log.e(TAG, "error: " + e.getMessage(), e);
		}
	}

	public void onBufferingUpdate(MediaPlayer arg0, int percent) {
		// Log.d(TAG, "onBufferingUpdate percent:" + percent);

	}

	public void onCompletion(MediaPlayer arg0) {
		Log.d(TAG, "onCompletion called");
	}

	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		Log.v(TAG, "onVideoSizeChanged called");
		if (width == 0 || height == 0) {
			Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
			return;
		}
		mIsVideoSizeKnown = true;
		mVideoWidth = width;
		mVideoHeight = height;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void onPrepared(MediaPlayer mediaplayer) {
		Log.d(TAG, "onPrepared called");
		mIsVideoReadyToBePlayed = true;
		if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
			startVideoPlayback();
		}
	}

	public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
		Log.d(TAG, "surfaceChanged called");

	}

	public void surfaceDestroyed(SurfaceHolder surfaceholder) {
		Log.d(TAG, "surfaceDestroyed called");
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated called");
		playVideo(extras.getInt(MEDIA));

	}

	@Override
	protected void onPause() {
		super.onPause();
		releaseMediaPlayer();
		doCleanUp();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseMediaPlayer();
		doCleanUp();
	}

	private void releaseMediaPlayer() {
		if (mMediaPlayer != null) {
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

	private void doCleanUp() {
		mVideoWidth = 0;
		mVideoHeight = 0;
		mIsVideoReadyToBePlayed = false;
		mIsVideoSizeKnown = false;
	}

	private void startVideoPlayback() {
		Log.v(TAG, "startVideoPlayback");
		holder.setFixedSize(mVideoWidth, mVideoHeight);
		mMediaPlayer.start();
	}

	public void upload (View view){
		String Price = A.getText().toString();

		int x = Integer.valueOf(Price);
		int z = Integer.valueOf(Lp);
		if (x < z){
			Toast.makeText(getApplicationContext(),"出價金額小於$"+z,Toast.LENGTH_LONG).show();
		}else{

			int b =  Integer.valueOf(Total)+x;
			Price = Integer.toString(b);
			demoRef.child("GetProid").setValue(userid);
			demoRef.child("Price").setValue(Price);

			demoRef.child("Price").addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					String value = dataSnapshot.getValue(String.class);
					Total=value;
					Show.setText("$"+Total);
				}
				@Override
				public void onCancelled(DatabaseError databaseError) {
				}
			});
			demoRef.child("GetProid").addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					String value = dataSnapshot.getValue(String.class);
					GetProName = value;
				}
				@Override
				public void onCancelled(DatabaseError databaseError) {
				}
			});
			/*MyPirce.setValue(Price);
			GetProid.setValue(userid);
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(findViewById(R.id.btnGoPrice).getWindowToken(), 0);*/

		}
		//Toast.makeText(getApplicationContext(),Lp,Toast.LENGTH_LONG).show();
		A.setText("");

	}

	@Override
	public void onDataChange(DataSnapshot dataSnapshot) {
		/*if(dataSnapshot.getValue(String.class)!=null)
		{
			String key = dataSnapshot.getKey();
			if (key.equals("price")){
				String Price = dataSnapshot.getValue(String.class);
				endPrice=Price;
				Show.setText("$"+Price);
				Total=Price;

			}
			if (key.equals("GetProid")){
				String getproid = dataSnapshot.getValue(String.class);
				GetProName = getproid;


			}

		}*/

	}

	@Override
	public void onCancelled(DatabaseError databaseError) {

	}

	@Override
	protected void onStart() {
		super.onStart();
		MyPirce.addValueEventListener(this);
		GetProid.addValueEventListener(this);
	}

		Handler handler_Success2 = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle data = msg.getData();
				val = data.getString("key");//取出key中的字串存入val
				//Toast.makeText(getApplicationContext(), val, Toast.LENGTH_LONG).show();
				int aaa= Integer.valueOf(val)*1000;
				Toast.makeText(getApplicationContext(),val,Toast.LENGTH_LONG).show();

				new CountDownTimer(aaa,1000){
					@Override
					public void onFinish() {
						Count.setText("Done!");
						Toast.makeText(getApplicationContext(),"直播已結束囉!",Toast.LENGTH_LONG).show();
					//	new Thread(runnable).start();//啟動執行序runnable
						new Thread(new Runnable(){

							@Override
							public void run() {


								Looper.prepare();
								// TODO Auto-generated method stub
								HttpClient client = new DefaultHttpClient();
								HttpPost myPost = new HttpPost("http://140.131.114.155/insrtDail.php");
								String cc =Show.getText().toString();
								try {
									List<NameValuePair> params = new ArrayList<NameValuePair>();
									params.add(new BasicNameValuePair("id",Liveid));
									params.add(new BasicNameValuePair("buyer",GetProName));
									params.add(new BasicNameValuePair("endPrice",Total));
									myPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
									HttpResponse response = new DefaultHttpClient().execute(myPost);
									Log.v("AB","訂單呢");

									// Toast.makeText(getApplicationContext(), aa, Toast.LENGTH_LONG).show();
								} catch (Exception e) {
									e.printStackTrace();
								}
								Looper.loop();

							}}).start();
						finish();

					}

					@Override
					public void onTick(long millisUntilFinished) {
						Count.setText("Time:"+((millisUntilFinished/1000)/60)+":"+((millisUntilFinished/1000)-(((millisUntilFinished/1000)/60)*60)));
					}
				}.start();
			}
		};



		Handler handler_Error2 = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle data = msg.getData();
				val = data.getString("key");
			//	Toast.makeText(getApplicationContext(), val, Toast.LENGTH_LONG).show();
				Log.v("AC",val);
			}
		};

		Handler handler_Nodata2 = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle data = msg.getData();
				val = data.getString("key");
				Toast.makeText(getApplicationContext(), val, Toast.LENGTH_LONG).show();
			}
		};


		Runnable runnable2 = new Runnable(){
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
					vars.add(new BasicNameValuePair("liveID",Liveid));
					//vars.add(new BasicNameValuePair("liveID",Liveid));

					method.setEntity(new UrlEncodedFormEntity(vars, HTTP.UTF_8));

					//接收PHP回傳的資料
					HttpResponse response = httpclient.execute(method);
					HttpEntity entity = response.getEntity();


					if(entity != null){
						data.putString("key", EntityUtils.toString(entity, "utf-8"));//如果成功將網頁內容存入key
						handler_Success2.sendMessage(msg);

					}
					else{
						data.putString("key","無資料");
						handler_Nodata2.sendMessage(msg);
					}
				}
				catch(Exception e){
					data.putString("key","連線失敗");
					handler_Error2.sendMessage(msg);
				}

			}
		};



		//--------------------------------------------------------------------------------------------------------
	/*	Handler handler_Success = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle data = msg.getData();
				String val = data.getString("A");//取出key中的字串存入val

			}



		};

		Handler handler_Error = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle data = msg.getData();
				String val = data.getString("A");
				//  Toast.makeText(getApplicationContext(), val, Toast.LENGTH_LONG).show();
			}
		};

		Handler handler_Nodata = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle data = msg.getData();
				String val = data.getString("A");
				// Toast.makeText(getApplicationContext(), val, Toast.LENGTH_LONG).show();
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
					HttpPost method = new HttpPost("http://140.131.114.155/insrtDail.php");

					//傳值給PHP
					List<NameValuePair> vars=new ArrayList<NameValuePair>();

					vars.add(new BasicNameValuePair("id",GetProName));
					vars.add(new BasicNameValuePair("endPrice",Total));
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
