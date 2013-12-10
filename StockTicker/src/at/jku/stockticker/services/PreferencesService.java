package at.jku.stockticker.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import at.jku.stockticker.pojo.Stock;

public class PreferencesService extends OptionsService {
	protected static String URL = "http://10.0.2.2:1337/preferences";

	@Override
	public List<Stock> get(Object... object) throws Exception {
		super.initializeReader(URL);
		return readStocks();
	}
	
	public String set(List<Stock> stocks) throws Exception {
		RESTfulHttpHandler handler = RESTfulHttpHandler.getInstance();
		HttpPost post = new HttpPost(URL);
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(stocks.size());
		
		for(Stock stock : stocks)
			nameValuePairs.add(new BasicNameValuePair(TAG_ID, stock.getId()));
		
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		AsyncTask<HttpUriRequest, Void, HttpResponse> task = handler.request(post);
		
		HttpResponse res = task.get();
		if(res.getStatusLine().getStatusCode() == 201) {
			return "Portfolio gespeichert";
		}
		
		return Util.toString(res);
	}

}
