package com.my.duja;

import androidx.appcompat.app.AppCompatActivity;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;
import android.graphics.Typeface;

public class LoginActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	
	private LinearLayout full_page_linear;
	private ImageView loading_bike;
	private LinearLayout heading_linear;
	private LinearLayout login_detail_linear;
	private LinearLayout login_button_linear;
	private TextView heading;
	private LinearLayout password_linear;
	private LinearLayout linear1;
	private TextView password_text;
	private EditText password_value;
	private TextView mail_text;
	private EditText mail_value;
	private Button login_button;
	
	private Intent intent = new Intent();
	private FirebaseAuth fb_auth;
	private OnCompleteListener<AuthResult> _fb_auth_create_user_listener;
	private OnCompleteListener<AuthResult> _fb_auth_sign_in_listener;
	private OnCompleteListener<Void> _fb_auth_reset_password_listener;
	private AlertDialog.Builder dialog;
	private TimerTask timer;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.login);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		full_page_linear = (LinearLayout) findViewById(R.id.full_page_linear);
		loading_bike = (ImageView) findViewById(R.id.loading_bike);
		heading_linear = (LinearLayout) findViewById(R.id.heading_linear);
		login_detail_linear = (LinearLayout) findViewById(R.id.login_detail_linear);
		login_button_linear = (LinearLayout) findViewById(R.id.login_button_linear);
		heading = (TextView) findViewById(R.id.heading);
		password_linear = (LinearLayout) findViewById(R.id.password_linear);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		password_text = (TextView) findViewById(R.id.password_text);
		password_value = (EditText) findViewById(R.id.password_value);
		mail_text = (TextView) findViewById(R.id.mail_text);
		mail_value = (EditText) findViewById(R.id.mail_value);
		login_button = (Button) findViewById(R.id.login_button);
		fb_auth = FirebaseAuth.getInstance();
		dialog = new AlertDialog.Builder(this);
		
		login_button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (mail_value.getText().toString().equals("") || password_value.getText().toString().equals("")) {
					SketchwareUtil.showMessage(getApplicationContext(), "Fill the given details");
				}
				else {
					fb_auth.signInWithEmailAndPassword(mail_value.getText().toString(), password_value.getText().toString()).addOnCompleteListener(LoginActivity.this, _fb_auth_sign_in_listener);
					timer = new TimerTask() {
						@Override
						public void run() {
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									loading_bike.setTranslationX((float)(loading_bike.getTranslationX() + 10));
								}
							});
						}
					};
					_timer.scheduleAtFixedRate(timer, (int)(0), (int)(100));
				}
			}
		});
		
		_fb_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_fb_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					intent.setClass(getApplicationContext(), MainMapActivity.class);
					startActivity(intent);
					finish();
				}
				else {
					dialog.setMessage(_errorMessage);
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.create().show();
					timer.cancel();
					loading_bike.setTranslationX((float)(200));
				}
			}
		};
		
		_fb_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	private void initializeLogic() {
		heading.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/algerian.ttf"), 0);
		password_text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/algerian.ttf"), 0);
		mail_text.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/algerian.ttf"), 0);
		login_button.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/algerian.ttf"), 0);
		loading_bike.setTranslationX((float)(0));
		timer = new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						loading_bike.setTranslationX((float)(loading_bike.getTranslationX() + 5));
						if (loading_bike.getTranslationX() == 200) {
							timer.cancel();
						}
					}
				});
			}
		};
		_timer.scheduleAtFixedRate(timer, (int)(0), (int)(100));
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	@Override
	public void onBackPressed() {
		intent.setClass(getApplicationContext(), LoginOrRegistratioActivity.class);
		startActivity(intent);
		finish();
	}
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
