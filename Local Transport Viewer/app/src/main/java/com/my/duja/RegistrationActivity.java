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
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;
import android.graphics.Typeface;

public class RegistrationActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> user_data = new HashMap<>();
	private String var_str_dj_lat = "";
	private String var_str_dj_lng = "";
	
	private ArrayList<String> string_list = new ArrayList<>();
	
	private LinearLayout linear1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private Button submit;
	private Spinner vehicles;
	private ImageView loading_bike;
	private TextView title;
	private TextView name;
	private EditText name_value;
	private TextView email;
	private EditText email_value;
	private TextView password;
	private EditText password_value;
	private TextView vehicle;
	
	private FirebaseAuth fb_auth;
	private OnCompleteListener<AuthResult> _fb_auth_create_user_listener;
	private OnCompleteListener<AuthResult> _fb_auth_sign_in_listener;
	private OnCompleteListener<Void> _fb_auth_reset_password_listener;
	private DatabaseReference fb_db = _firebase.getReference("user_details");
	private ChildEventListener _fb_db_child_listener;
	private Intent intent = new Intent();
	private AlertDialog.Builder dialog;
	private TimerTask timer;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.registration);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		submit = (Button) findViewById(R.id.submit);
		vehicles = (Spinner) findViewById(R.id.vehicles);
		loading_bike = (ImageView) findViewById(R.id.loading_bike);
		title = (TextView) findViewById(R.id.title);
		name = (TextView) findViewById(R.id.name);
		name_value = (EditText) findViewById(R.id.name_value);
		email = (TextView) findViewById(R.id.email);
		email_value = (EditText) findViewById(R.id.email_value);
		password = (TextView) findViewById(R.id.password);
		password_value = (EditText) findViewById(R.id.password_value);
		vehicle = (TextView) findViewById(R.id.vehicle);
		fb_auth = FirebaseAuth.getInstance();
		dialog = new AlertDialog.Builder(this);
		
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((name_value.getText().toString().equals("") || email_value.getText().toString().equals("")) || (password_value.getText().toString().equals("") || string_list.get((int)(vehicles.getSelectedItemPosition())).equals("Choose the Vehicle"))) {
					dialog.setMessage("Fill the given Details");
					dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface _dialog, int _which) {
							
						}
					});
					dialog.create().show();
				}
				else {
					fb_auth.createUserWithEmailAndPassword(email_value.getText().toString(), password_value.getText().toString()).addOnCompleteListener(RegistrationActivity.this, _fb_auth_create_user_listener);
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
		
		_fb_db_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		fb_db.addChildEventListener(_fb_db_child_listener);
		
		_fb_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					var_str_dj_lat = "9.3417639";
					var_str_dj_lng = "78.9224439";
					user_data.put("User ID", FirebaseAuth.getInstance().getCurrentUser().getUid());
					user_data.put("Name", name_value.getText().toString());
					user_data.put("Vehicle", string_list.get((int)(vehicles.getSelectedItemPosition())));
					user_data.put("Email", email_value.getText().toString());
					user_data.put("Password", password_value.getText().toString());
					user_data.put("Current Latitude", var_str_dj_lat);
					user_data.put("Current Longitude", var_str_dj_lng);
					fb_db.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(user_data);
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
		
		_fb_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
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
		title.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/algerian.ttf"), 0);
		name.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/algerian.ttf"), 0);
		email.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/algerian.ttf"), 0);
		password.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/algerian.ttf"), 0);
		vehicle.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/algerian.ttf"), 0);
		submit.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/algerian.ttf"), 0);
		string_list.add("Choose the Vehicle");
		string_list.add("BIKE");
		string_list.add("CAR");
		string_list.add("AUTO");
		string_list.add("LORRY");
		string_list.add("BUS");
		vehicles.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, string_list));
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
