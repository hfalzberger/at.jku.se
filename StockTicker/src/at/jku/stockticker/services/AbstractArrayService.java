package at.jku.stockticker.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

public abstract class AbstractArrayService<T> {

	protected JsonReader reader = null;

	protected void initializeReader(String url) throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		InputStream content = null;

		HttpResponse response = client.execute(httpGet);
		StatusLine statusLine = response.getStatusLine();

		if (statusLine.getStatusCode() == 200) {
			HttpEntity entity = response.getEntity();
			content = entity.getContent();

		} else {
			Log.e(this.getClass().toString(), "Failed to download file");
		}
		this.reader = new JsonReader(new InputStreamReader(content));
	}

	public abstract List<T> getData() throws IOException;
}
