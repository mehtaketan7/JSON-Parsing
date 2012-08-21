package com.json;

import java.util.List;

import com.json.adapter.PhotoGridAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class PhotoGridActivity extends Activity {

	private List<JSONitem> myList;
	private JSONitem item;
	private GridView gridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myList = PhotoActivity.list1;
		setContentView(R.layout.photos_grid_activity);

		gridView = (GridView) findViewById(R.id.photogrid_activity_gridview);
		gridView.setAdapter(new PhotoGridAdapter(this, myList));

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(PhotoGridActivity.this,
						PhotoFullViewActivity.class);
				item = myList.get(arg2);
				intent.putExtra("IMAGE", item.getLargeimage());
				intent.putExtra("FNAME", item.getId());
				intent.putExtra("LNAME", item.getTitle());
				startActivity(intent);
			}
		});
	}

}
