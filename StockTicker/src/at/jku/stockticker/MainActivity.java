package at.jku.stockticker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import at.jku.stockticker.pojo.Stock;
import at.jku.stockticker.services.OptionsService;

public class MainActivity extends Activity {

	private EditText fromDateText;
	private EditText toDateText;
	private Button stocksBtn;
	private Button savedStocksBtn;
	private Button showBtn;
	
	private SimpleDateFormat sdf;
	
	private Date fromDate;
	private Date toDate;
	
	private List<Stock> selectedStocks;
	private List<Stock> portfolio;
	
	private List<Stock> availableStocks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.fromDateText = (EditText) findViewById(R.id.dateFrom);
		this.toDateText = (EditText) findViewById(R.id.dateTo);
		this.stocksBtn = (Button) findViewById(R.id.buttonStocks);
		this.savedStocksBtn = (Button) findViewById(R.id.buttonSavedStocks);
		this.showBtn = (Button) findViewById(R.id.buttonShow);
		
		this.sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);	
		this.fromDate = Calendar.getInstance().getTime();
		this.toDate = Calendar.getInstance().getTime();
		this.fromDateText.setText(sdf.format(this.fromDate));
		this.toDateText.setText(sdf.format(this.toDate));
		
		this.availableStocks = this.getStocks();
		this.portfolio = this.getSavedStocks();
		this.selectedStocks = new ArrayList<Stock>(this.portfolio);

		StrictMode.ThreadPolicy policy = new StrictMode.
		ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		
		this.fromDateText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar cal = Calendar.getInstance();
				new DatePickerDialog(MainActivity.this, 
						new OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year, 
									int monthOfYear, int dayOfMonth) {
								Calendar cal = Calendar.getInstance();
								cal.set(Calendar.YEAR, year);
								cal.set(Calendar.MONTH, monthOfYear);
								cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
								
								fromDateText.setText(sdf.format(cal.getTime()));
								fromDate = cal.getTime();							
							}
						}, 
						cal.get(Calendar.YEAR),
						cal.get(Calendar.MONTH), 
						cal.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		this.toDateText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar cal = Calendar.getInstance();
				new DatePickerDialog(MainActivity.this,
						new OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker view, int year,
									int monthOfYear, int dayOfMonth) {
								Calendar cal = Calendar.getInstance();
								cal.set(Calendar.YEAR, year);
								cal.set(Calendar.MONTH, monthOfYear);
								cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

								if(cal.getTime().before(fromDate)) {
									Toast.makeText(MainActivity.this, 
											R.string.err_date_to, 
											Toast.LENGTH_LONG).show();
								} else {
									toDateText.setText(sdf.format(cal.getTime()));
									toDate = cal.getTime();
								}
							}
						},
						cal.get(Calendar.YEAR), 
						cal.get(Calendar.MONTH), 
						cal.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

				
		this.stocksBtn.setOnClickListener(new StockSelectAlert(
				availableStocks,
				selectedStocks, this, new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int which, long id) {
				CheckedTextView checkedTextView = (CheckedTextView) view;
				if(checkedTextView.isChecked())
					selectedStocks.add(availableStocks.get(which));
				else
					selectedStocks.remove(availableStocks.get(which));
			}
		}));
		
		this.savedStocksBtn.setOnClickListener(new StockSelectAlert(
				availableStocks,
				portfolio, this, new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int which, long id) {
				CheckedTextView checkedTextView = (CheckedTextView) view;
				if(checkedTextView.isChecked())
					portfolio.add(availableStocks.get(which));
				else
					portfolio.remove(availableStocks.get(which));
			}
		}));
		
		this.showBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(selectedStocks.isEmpty()) {
					Toast.makeText(MainActivity.this, R.string.err_no_stocks, Toast.LENGTH_LONG).show();
					return;
				}
				
				Intent intent = new SalesGrowthChart().execute(MainActivity.this);
				startActivity(intent);
			}
		});
	}

	private List<Stock> getSavedStocks() {
		List<Stock> stocks = new ArrayList<Stock>();
		stocks.add(new Stock(1, "test1"));
		stocks.add(new Stock(4, "test4"));
		return stocks;
	}

	/**
	 * Torben: Hier die Aktien zurueckgeben.
	 * 
	 * @return
	 * @throws IOException 
	 */
	private List<Stock> getStocks() {		
//		try {
			//return new OptionsService().getData();
		//List<Stock> stocks = new ArrayList<Stock>();
		/*stocks.add(new Stock(1, "test1"));
			stocks.add(new Stock(2, "test2"));
			stocks.add(new Stock(3, "test3"));
			stocks.add(new Stock(4, "test4"));
			return stocks;
			*/
		(new Thread(new Runnable() {
				
				@Override
				public void run() {
				OptionsService OptionsService = new OptionsService();
				List<Stock> stockoptions = new ArrayList<Stock>();
				try {
					stockoptions = OptionsService.getData();
					System.out.println("stockoptions:" + stockoptions);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("ioexception:" + e);
				
				//return stockoptions;
				
				}}}
				)).start();
			//return getResources().getStringArray(R.array.stockoptions);
			//return availableStocks;
		return availableStocks;
	}
	
	
	private void getStockPrices() {
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}