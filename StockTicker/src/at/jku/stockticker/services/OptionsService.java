package at.jku.stockticker.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import at.jku.stockticker.pojo.Stock;

public class OptionsService extends AbstractArrayService<Stock> {

	protected static String URL = "http://10.0.2.2:1337/options";
	protected static final String TAG_ID = "id";
	protected static final String TAG_NAME = "name";
	protected static final String TAG_SYMBOL = "symbol";

	@Override
	public List<Stock> get(Object... object) throws Exception {
		super.initializeReader(URL);
		return readStocks();
	}

	public List<Stock> readStocks() throws IOException {
		List<Stock> stocks = new ArrayList<Stock>();

		reader.beginArray();
		while (reader.hasNext()) {
			stocks.add(readStock());
		}
		reader.endArray();

		return stocks;
	}

	protected Stock readStock() throws IOException {
		Stock s = new Stock();

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(TAG_ID)) {
				s.setId(reader.nextString());
			} else if (name.equals(TAG_NAME)) {
				s.setName(reader.nextString());
			} else if (name.equals(TAG_SYMBOL)) {
				s.setSymbol(reader.nextString());
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();

		return s;
	}

}
