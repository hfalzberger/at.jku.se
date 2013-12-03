package at.jku.stockticker.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;
import at.jku.stockticker.pojo.Prize;

public class PrizesService extends AbstractArrayService<Prize> {

	private static final String URL = "http://127.0.0.1:1337/prize";
	private static final String TAG_ID = "id";
	private static final String TAG_PRIZE = "prize";
	private static final String TAG_TIME = "time";

	@Override
	protected List<Prize> doInBackground(Object... o) {
		List<Prize> prizes = new ArrayList<Prize>();

		try {
			String url = URL;
			url += "?id=" + o[1] + "&start=" + o[2] + "&end=" + o[3];
			super.initializeReader(url);
			
			reader.beginArray();
			while (reader.hasNext()) {
				prizes.add(readStock());
			}
			reader.endArray();
		} catch (Exception e) {
			Log.e(this.getClass().getName(), e.getMessage());
		}

		return prizes;
	}

	private Prize readStock() throws IOException {
		Prize p = new Prize();

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(TAG_ID)) {
				p.setId(reader.nextString());
			} else if (name.equals(TAG_PRIZE)) {
				p.setPrice(reader.nextDouble());
			} else if (name.equals(TAG_TIME)) {
				p.setTime(new Date(reader.nextLong()));
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();

		return p;
	}

}
