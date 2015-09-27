package com.vishwanathgowdak.EpochConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import com.ikovac.timepickerwithseconds.view.MyTimePickerDialog;
import com.ikovac.timepickerwithseconds.view.TimePicker;


public class EpochMain extends Activity {


	public static TextView dateView;
	public static TextView timeView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_epoch_main);
		dateView=(TextView)findViewById(R.id.selectdate);
		final EditText epoch = (EditText)findViewById(R.id.epochText);
		timeView=(TextView)findViewById(R.id.selecttime);
		epoch.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
			@Override
			public void afterTextChanged(Editable s) {
				long newEpoch = 0;
				if((s.length()>0))
					newEpoch=Long.valueOf(epoch.getText().toString());
				epochLogic.setDateTimethroughEpoch(newEpoch);
				dateView.setText(CurrentSelectionValues.getYear()+"/"+CurrentSelectionValues.getMonth()+"/"+CurrentSelectionValues.getDate());
				timeView.setText(CurrentSelectionValues.getHour()+":"+CurrentSelectionValues.getMinute()+":"+CurrentSelectionValues.getSecond());
			}
		});
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.epoch_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public void showTimePickerDialog(View v) {
		Calendar now= new GregorianCalendar();
		MyTimePickerDialog mTimePicker = new MyTimePickerDialog(this, new MyTimePickerDialog.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds) {

				CurrentSelectionValues.setHour(hourOfDay);
				CurrentSelectionValues.setMinute(minute);
				CurrentSelectionValues.setSecond(seconds);
				TextView time= (TextView)findViewById(R.id.selecttime);
				time.setText(CurrentSelectionValues.getHour()+":"+CurrentSelectionValues.getMinute()+":"+CurrentSelectionValues.getSecond());
				TextView epoch= (TextView)findViewById(R.id.epochText);
				long epochTime=epochLogic.getEpoch();
				if(epochTime >0)
					epoch.setText(Long.valueOf(epochTime).toString());
			}
		}, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), true);
		mTimePicker.show();
	}

	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
			dpd.getDatePicker().setMinDate(0);
			// Create a new instance of DatePickerDialog and return it
			return dpd;
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {

			CurrentSelectionValues.setYear(year);
			CurrentSelectionValues.setMonth(++month);
			CurrentSelectionValues.setDate(day);
			TextView date= (TextView)getActivity().findViewById(R.id.selectdate);
			date.setText(CurrentSelectionValues.getYear()+"/"+CurrentSelectionValues.getMonth()+"/"+CurrentSelectionValues.getDate());
			long epochTime=epochLogic.getEpoch();
			TextView epoch= (TextView)getActivity().findViewById(R.id.epochText);
			if(epochTime >0)
				epoch.setText(Long.valueOf(epochTime).toString());
		}
	}

	public static class epochLogic {

		public static long getEpoch(){
			long epoch = -1;
			String year=CurrentSelectionValues.getYear();
			String month=CurrentSelectionValues.getMonth();
			String date=CurrentSelectionValues.getDate();
			String hour=CurrentSelectionValues.getHour();
			String minute=CurrentSelectionValues.getMinute();
			String second=CurrentSelectionValues.getSecond();
			String timeStamp=year+"-"+month+"-"+date+"T"+hour+":"+minute+":"+second;
			SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
			isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			try {
				Date objdate = isoFormat.parse(timeStamp);
				epoch=objdate.getTime()/1000;
			} catch (ParseException e) {
				//Toast.makeText(activity, "parse exception", Toast.LENGTH_SHORT).show();
			}

			return epoch;

		}
		public static void setDateTimethroughEpoch(long epoch){
			Date date= new Date(epoch * 1000L);
			Calendar cal = Calendar.getInstance();
			cal.setTimeZone(TimeZone.getTimeZone("UTC"));
			cal.setTime(date);
			cal.get(Calendar.YEAR);
			CurrentSelectionValues.setDate(cal.get(Calendar.DATE));
			CurrentSelectionValues.setHour(cal.get(Calendar.HOUR));
			CurrentSelectionValues.setMinute(cal.get(Calendar.MINUTE));
			CurrentSelectionValues.setSecond(cal.get(Calendar.SECOND));
			CurrentSelectionValues.setYear(cal.get(Calendar.YEAR));
			int month=cal.get(Calendar.MONTH);
			CurrentSelectionValues.setMonth(++month);
		}
	}
}
