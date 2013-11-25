package at.jku.stockticker.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.jku.stockticker.pojo.Stock;

public class OptionsService extends AbstractArrayService<Stock> {

	private static final String URL = "http://127.0.0.1:1337/options";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";

	@Override
	public List<Stock> getData() throws IOException {
		super.initializeReader(URL);
		List<Stock> stocks = new ArrayList<Stock>();

		reader.beginArray();
		while (reader.hasNext()) {
			stocks.add(readStock());
		}
		reader.endArray();

		return stocks;
	}

	private Stock readStock() throws IOException {
		Stock s = new Stock();
		
		reader.beginObject();		
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(TAG_ID)) {
				s.setId(reader.nextInt());
			} else if (name.equals(TAG_NAME)) {
				s.setName(reader.nextString());
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		
		return s;
	}
}
