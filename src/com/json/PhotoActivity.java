package com.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.json.adapter.PhotosActivityAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class PhotoActivity extends Activity {
	/** Called when the activity is first created. */
	public static List<JSONitem> list1 = new ArrayList<JSONitem>();
	private ListView listView;
	private String fname, lname, designation, image;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photos_activity);
		
		listView = (ListView) findViewById(R.id.lstdata);
		
		String readTwitterFeed = readTwitterFeed();
		try {
			JSONArray jsonArray = new JSONArray(readTwitterFeed);
			Log.i(PhotoActivity.class.getName(), "Number of entries "
					+ jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				fname = jsonObject.getString("first_name");
				lname = jsonObject.getString("last_name");
				designation = jsonObject.getString("designation");
				image = jsonObject.optString("picture-path");

				JSONitem item = new JSONitem();
				item.setId(fname);
				item.setTitle(lname);
				item.setDesignation(designation);
				item.setLargeimage(image);
				list1.add(item);

			}
			PhotosActivityAdapter adapter = new PhotosActivityAdapter(this,
					list1);
			listView.setAdapter(adapter);
		} catch (Exception e) {
			e.printStackTrace();
		}

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PhotoActivity.this,
						PhotoGridActivity.class);

				startActivity(intent);

			}
		});

	}

	public String readTwitterFeed() {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(
				"http://financemyhome.com/webservice/bio.php");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(PhotoActivity.class.toString(), "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
}
