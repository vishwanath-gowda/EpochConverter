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
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.ikovac.timepickerwithseconds.view.MyTimePickerDialog;
import com.ikovac.timepickerwithseconds.view.TimePicker;


public class EpochMain extends Activity {


	public static TextView dateView;
	public static TextView timeView;
	public static TextView curTime;
	public static TextView curEpoch;
	public static CheckBox localGMT;
	CountDownTimer newtimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_epoch_main);
		AdView mAdView = (AdView) findViewById(R.id.adView);
		Builder b = new AdRequest.Builder();
		//b.addTestDevice("C326E3E6AD7D47FDF89D8B2D1C924E08");
		AdRequest adRequest = b.build();
		mAdView.loadAd(adRequest);
		localGMT=(CheckBox)findViewById(R.id.LocalGMTCheck);
		curTime=(TextView)findViewById(R.id.CurTime);
		curEpoch=(TextView)findViewById(R.id.CurEpoch);
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
				dateView.setText(MonthMap.getInstance().getShortMonth(Integer.parseInt(CurrentSelectionValues.getMonth()))+"/"+CurrentSelectionValues.getDate()+"/"+CurrentSelectionValues.getYear());
				timeView.setText(CurrentSelectionValues.getHour()+":"+CurrentSelectionValues.getMinute()+":"+CurrentSelectionValues.getSecond());
			}
		});
		newtimer = new CountDownTimer(1000000000, 1000) { 

			public void onTick(long millisUntilFinished) {

				Calendar c = Calendar.getInstance();
				String timezone= "Local";
				if(localGMT.isChecked()){
					c.setTimeZone(TimeZone.getTimeZone("UTC"));
					timezone="GMT";
				}
				curTime.setText(c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND)+" "+timezone);
				curEpoch.setText(epochLogic.getEpochByCal(c));

			}
			public void onFinish() {

			}
		};
		newtimer.start();
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
				if(epochTime >=0)
					epoch.setText(Long.valueOf(epochTime).toString());
			}
		}, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), true);
		mTimePicker.show();
	}

	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);
			dpd.getDatePicker().setMinDate(0);
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
			if(epochTime >=0)
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
		public static String getEpochByCal(Calendar c){

			return (c==null)? null : String.valueOf((c.getTimeInMillis()/1000));
		}
		public static void setDateTimethroughEpoch(long epoch){
			Date date= new Date(epoch * 1000L);
			Calendar cal = Calendar.getInstance();
			cal.setTimeZone(TimeZone.getTimeZone("UTC"));
			cal.setTime(date);
			cal.get(Calendar.YEAR);
			CurrentSelectionValues.setDate(cal.get(Calendar.DATE));
			CurrentSelectionValues.setHour(cal.get(Calendar.HOUR_OF_DAY));
			CurrentSelectionValues.setMinute(cal.get(Calendar.MINUTE));
			CurrentSelectionValues.setSecond(cal.get(Calendar.SECOND));
			CurrentSelectionValues.setYear(cal.get(Calendar.YEAR));
			int month=cal.get(Calendar.MONTH);
			CurrentSelectionValues.setMonth(++month);
		}
	}
}
