package at.jku.stockticker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

public class MainActivity extends Activity {

	private EditText fromDateText;
	private EditText toDateText;
	private Button stocksBtn;
	
	private SimpleDateFormat sdf;
	
	private Date fromDate;
	private Date toDate;
	private List<String> selectedStocks;
	private String[] availableStocks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.fromDateText = (EditText) findViewById(R.id.dateFrom);
		this.toDateText = (EditText) findViewById(R.id.dateTo);
		this.stocksBtn = (Button) findViewById(R.id.buttonStocks);
		
		this.sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.GERMANY);		
		this.selectedStocks = new ArrayList<String>();
		this.availableStocks = this.getStocks();

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

								toDateText.setText(sdf.format(cal.getTime()));
								toDate = cal.getTime();
							}
						},
						cal.get(Calendar.YEAR), 
						cal.get(Calendar.MONTH), 
						cal.get(Calendar.DAY_OF_MONTH)).show();
			}
		});

		this.stocksBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder selectMulti = new AlertDialog.Builder(MainActivity.this);
				selectMulti.setIcon(R.drawable.ic_launcher);
				selectMulti.setNegativeButton(R.string.cancel, null);
				selectMulti.setPositiveButton(R.string.ok, null);
				
				selectMulti.setMultiChoiceItems(
						availableStocks, 
						null, 
						new OnMultiChoiceClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which, boolean isChecked) {
						String stock = MainActivity.this.availableStocks[which];
						if(isChecked)
							MainActivity.this.selectedStocks.add(stock);
						else
							MainActivity.this.selectedStocks.remove(stock);
					}
				});
				selectMulti.show();
			}
		});
	}

	/**
	 * Torben: Hier die Aktien zurückgeben.
	 * 
	 * @return
	 */
	private String[] getStocks() {		
		return getResources().getStringArray(R.array.test_array);
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
