package com.json;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class DrawableManagerForMyPhotos {
	private final HashMap<String, Drawable> drawableMapForList;
	private BitmapDrawable bitmapDrawable = null;

	// private Bitmap bitmaparray[]=new Bitmap[5];
	int i = 0, j = 0;

	public DrawableManagerForMyPhotos() {
		drawableMapForList = new HashMap<String, Drawable>();
	}

	public synchronized Drawable fetchDrawable(String urlString) {
		if (drawableMapForList.containsKey(urlString)) {
			return drawableMapForList.get(urlString);
		}

		// Log.d(this.getClass().getSimpleName(), "image url:" + urlString);
		try {

			BitmapDrawable drawable2 = fetch(urlString);
			Drawable drawable = (Drawable) drawable2;
			if (drawable != null) {
				drawableMapForList.put(urlString, drawable);

				// Log.d(this.getClass().getSimpleName(),
				// "got a thumbnail drawable: " + drawable.getBounds()
				// + ", " + drawable.getIntrinsicHeight() + ","
				// + drawable.getIntrinsicWidth() + ", "
				// + drawable.getMinimumHeight() + ","
				// + drawable.getMinimumWidth());
				return drawable;
			} /*
			 * else { drawable = Resources.getSystem().getDrawable(
			 * android.R.drawable.gallery_thumb); return drawable; }
			 */
			return null;

		} catch (MalformedURLException e) {
			Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
			return null;
		} catch (IOException e) {
			Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
			return null;
		} catch (Exception e) {
			Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
			return null;
		}
	}

	public void fetchDrawableOnThread(final String urlString,
			final ImageView imageView, final ProgressBar progressBar) {
		// progressBar.setVisibility(ProgressBar.VISIBLE);
		if (drawableMapForList.containsKey(urlString)&&drawableMapForList.get(urlString)!=null) {
			progressBar.setVisibility(ProgressBar.INVISIBLE);
			/*
			 * Bitmap url = IOUtils.getimage(urlString);
			 * imageView.setImageBitmap(url);
			 */
			Log.e("ImageManager", "drwable" + drawableMapForList.get(urlString));
			imageView.setBackgroundDrawable(drawableMapForList.get(urlString));
			// imageView.setVisibility(ImageView.VISIBLE);
			// imageView.bringToFront();
		} else {
			final Handler handler = new Handler() {
				@Override
				public void handleMessage(Message message) {
					// Log.d("HANDLER", "" + i++);
					progressBar.setVisibility(ProgressBar.INVISIBLE);
					if (message.obj != null)
						imageView.setBackgroundDrawable((Drawable) message.obj);
					/*
					 * else
					 * imageView.setImageBitmap(IOUtils.resizeBitmap(BitmapFactory
					 * .decodeResource(HomeScreen.this.getResources(),
					 * R.drawable.noimage),50,50));
					 */
				}
			};

			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						Drawable drawable = fetchDrawable(urlString);
						Message message = handler.obtainMessage(1, drawable);
						handler.sendMessage(message);
					} catch (Exception e) {
						Log.e("DrawableManagerForMyPhotos",
								"Run method: " + e.toString());
					}
				}
			};
			thread.start();
		}
	}

	public BitmapDrawable fetch(String url) throws MalformedURLException,
			IOException {

		Log.e("ImageURL", url);
		InputStream is = null;
		Bitmap myBmp = null;
		try {
			HttpURLConnection conn = (HttpURLConnection) new URL(url)
					.openConnection();
			int response = conn.getResponseCode();
			if (response == 200) {
				is = conn.getInputStream();
				BitmapFactory.Options opts = new BitmapFactory.Options();

				opts.inSampleSize = 1;
				opts.inPurgeable = true;
				myBmp = BitmapFactory.decodeStream(is, null, opts);
				is.close();
				conn.disconnect();
				bitmapDrawable = new BitmapDrawable(myBmp);
			} else if (response == 404) {
				return null;
			}
		} catch (OutOfMemoryError e) {
			Log.e("Image Downloading", e.toString() + " \n\n" + url);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			Log.e("Image Downloading", e.toString() + " \n\n" + url);
			e.printStackTrace();
		}
		return bitmapDrawable;
	}

}
