package com.json;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoFullViewActivity extends Activity {

	private ImageView imageView;
	private TextView fname;
	private String image, f_name, l_name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photos_fullview_activity);

		imageView = (ImageView) findViewById(R.id.photofullview_activity_imgView);
		fname = (TextView) findViewById(R.id.photofullview_activity_txt);

		image = getIntent().getStringExtra("IMAGE");
		f_name = getIntent().getStringExtra("FNAME");
		l_name = getIntent().getStringExtra("LNAME");

		Drawable image1 = LoadImageFromWebOperations(image);
		imageView.setImageDrawable(image1);
		fname.setText(f_name + "  " + l_name);
	}

	private Drawable LoadImageFromWebOperations(String url) {
		try {
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src name");
			return d;
		} catch (Exception e) {
			System.out.println("Exc=" + e);
			return null;
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}
