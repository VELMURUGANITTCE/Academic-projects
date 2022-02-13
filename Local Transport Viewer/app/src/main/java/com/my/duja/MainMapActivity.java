package com.my.duja;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.widget.LinearLayout;
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
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import android.widget.TextView;
import android.widget.Switch;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View;
import android.widget.CompoundButton;
import java.text.DecimalFormat;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.pm.PackageManager;

public class MainMapActivity extends AppCompatActivity {
	
	private Timer _timer = new Timer();
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private Toolbar _toolbar;
	private FloatingActionButton _fab;
	private DrawerLayout _drawer;
	private HashMap<String, Object> map = new HashMap<>();
	private String var_str_myvehicle = "";
	private double count = 0;
	private String var_str_map_zoom = "";
	private double my_cur_lac = 0;
	private double my_cur_lng = 0;
	private double my_loc_acc = 0;
	private String username = "";
	
	private ArrayList<String> List_location = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> list_map = new ArrayList<>();
	private ArrayList<String> other_users_data = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> other_user_datas = new ArrayList<>();
	
	private MapView mapview1;
	private GoogleMapController _mapview1_controller;
	private LinearLayout _drawer_full_layer;
	private LinearLayout _drawer_user_details_linear;
	private LinearLayout _drawer_cancel_logo_linear;
	private LinearLayout _drawer_selecting_linear;
	private LinearLayout _drawer_user_logo_linear;
	private LinearLayout _drawer_linear18;
	private TextView _drawer_textview8;
	private TextView _drawer_name;
	private LinearLayout _drawer_title_linear;
	private LinearLayout _drawer_bike_linear;
	private LinearLayout _drawer_car_linear;
	private LinearLayout _drawer_auto_linear;
	private LinearLayout _drawer_bus_linear;
	private LinearLayout _drawer_lorry_linear;
	private TextView _drawer_textview1;
	private LinearLayout _drawer_linear2;
	private LinearLayout _drawer_linear8;
	private LinearLayout _drawer_linear13;
	private TextView _drawer_textview2;
	private Switch _drawer_bike_switch;
	private LinearLayout _drawer_linear4;
	private LinearLayout _drawer_linear9;
	private LinearLayout _drawer_linear14;
	private TextView _drawer_textview4;
	private Switch _drawer_car_switch;
	private LinearLayout _drawer_linear5;
	private LinearLayout _drawer_linear10;
	private LinearLayout _drawer_linear15;
	private TextView _drawer_textview5;
	private Switch _drawer_auto_switch;
	private LinearLayout _drawer_linear6;
	private LinearLayout _drawer_linear11;
	private LinearLayout _drawer_linear16;
	private TextView _drawer_textview6;
	private Switch _drawer_bus_switch;
	private LinearLayout _drawer_linear7;
	private LinearLayout _drawer_linear12;
	private LinearLayout _drawer_linear17;
	private TextView _drawer_textview7;
	private Switch _drawer_lorry_switch;
	
	private LocationManager location;
	private LocationListener _location_location_listener;
	private AlertDialog.Builder dialog;
	private Intent intent = new Intent();
	private DatabaseReference fb_db = _firebase.getReference("user_details");
	private ChildEventListener _fb_db_child_listener;
	private FirebaseAuth fb_auth;
	private OnCompleteListener<AuthResult> _fb_auth_create_user_listener;
	private OnCompleteListener<AuthResult> _fb_auth_sign_in_listener;
	private OnCompleteListener<Void> _fb_auth_reset_password_listener;
	private TimerTask timer;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.main_map);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1000);
		}
		else {
			initializeLogic();
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1000) {
			initializeLogic();
		}
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		_toolbar = (Toolbar) findViewById(R.id._toolbar);
		setSupportActionBar(_toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _v) {
				onBackPressed();
			}
		});
		_fab = (FloatingActionButton) findViewById(R.id._fab);
		
		_drawer = (DrawerLayout) findViewById(R.id._drawer);ActionBarDrawerToggle _toggle = new ActionBarDrawerToggle(MainMapActivity.this, _drawer, _toolbar, R.string.app_name, R.string.app_name);
		_drawer.addDrawerListener(_toggle);
		_toggle.syncState();
		
		LinearLayout _nav_view = (LinearLayout) findViewById(R.id._nav_view);
		
		mapview1 = (MapView) findViewById(R.id.mapview1);
		mapview1.onCreate(_savedInstanceState);
		
		_drawer_full_layer = (LinearLayout) _nav_view.findViewById(R.id.full_layer);
		_drawer_user_details_linear = (LinearLayout) _nav_view.findViewById(R.id.user_details_linear);
		_drawer_cancel_logo_linear = (LinearLayout) _nav_view.findViewById(R.id.cancel_logo_linear);
		_drawer_selecting_linear = (LinearLayout) _nav_view.findViewById(R.id.selecting_linear);
		_drawer_user_logo_linear = (LinearLayout) _nav_view.findViewById(R.id.user_logo_linear);
		_drawer_linear18 = (LinearLayout) _nav_view.findViewById(R.id.linear18);
		_drawer_textview8 = (TextView) _nav_view.findViewById(R.id.textview8);
		_drawer_name = (TextView) _nav_view.findViewById(R.id.name);
		_drawer_title_linear = (LinearLayout) _nav_view.findViewById(R.id.title_linear);
		_drawer_bike_linear = (LinearLayout) _nav_view.findViewById(R.id.bike_linear);
		_drawer_car_linear = (LinearLayout) _nav_view.findViewById(R.id.car_linear);
		_drawer_auto_linear = (LinearLayout) _nav_view.findViewById(R.id.auto_linear);
		_drawer_bus_linear = (LinearLayout) _nav_view.findViewById(R.id.bus_linear);
		_drawer_lorry_linear = (LinearLayout) _nav_view.findViewById(R.id.lorry_linear);
		_drawer_textview1 = (TextView) _nav_view.findViewById(R.id.textview1);
		_drawer_linear2 = (LinearLayout) _nav_view.findViewById(R.id.linear2);
		_drawer_linear8 = (LinearLayout) _nav_view.findViewById(R.id.linear8);
		_drawer_linear13 = (LinearLayout) _nav_view.findViewById(R.id.linear13);
		_drawer_textview2 = (TextView) _nav_view.findViewById(R.id.textview2);
		_drawer_bike_switch = (Switch) _nav_view.findViewById(R.id.bike_switch);
		_drawer_linear4 = (LinearLayout) _nav_view.findViewById(R.id.linear4);
		_drawer_linear9 = (LinearLayout) _nav_view.findViewById(R.id.linear9);
		_drawer_linear14 = (LinearLayout) _nav_view.findViewById(R.id.linear14);
		_drawer_textview4 = (TextView) _nav_view.findViewById(R.id.textview4);
		_drawer_car_switch = (Switch) _nav_view.findViewById(R.id.car_switch);
		_drawer_linear5 = (LinearLayout) _nav_view.findViewById(R.id.linear5);
		_drawer_linear10 = (LinearLayout) _nav_view.findViewById(R.id.linear10);
		_drawer_linear15 = (LinearLayout) _nav_view.findViewById(R.id.linear15);
		_drawer_textview5 = (TextView) _nav_view.findViewById(R.id.textview5);
		_drawer_auto_switch = (Switch) _nav_view.findViewById(R.id.auto_switch);
		_drawer_linear6 = (LinearLayout) _nav_view.findViewById(R.id.linear6);
		_drawer_linear11 = (LinearLayout) _nav_view.findViewById(R.id.linear11);
		_drawer_linear16 = (LinearLayout) _nav_view.findViewById(R.id.linear16);
		_drawer_textview6 = (TextView) _nav_view.findViewById(R.id.textview6);
		_drawer_bus_switch = (Switch) _nav_view.findViewById(R.id.bus_switch);
		_drawer_linear7 = (LinearLayout) _nav_view.findViewById(R.id.linear7);
		_drawer_linear12 = (LinearLayout) _nav_view.findViewById(R.id.linear12);
		_drawer_linear17 = (LinearLayout) _nav_view.findViewById(R.id.linear17);
		_drawer_textview7 = (TextView) _nav_view.findViewById(R.id.textview7);
		_drawer_lorry_switch = (Switch) _nav_view.findViewById(R.id.lorry_switch);
		location = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		dialog = new AlertDialog.Builder(this);
		fb_auth = FirebaseAuth.getInstance();
		
		_mapview1_controller = new GoogleMapController(mapview1, new OnMapReadyCallback() {
			@Override
			public void onMapReady(GoogleMap _googleMap) {
				_mapview1_controller.setGoogleMap(_googleMap);
				
			}
		});
		
		_mapview1_controller.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker _param1) {
				final String _id = _param1.getTag().toString();
				
				return false;
			}
		});
		
		_fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (ContextCompat.checkSelfPermission(MainMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
					location.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, _location_location_listener);
				}
			}
		});
		
		_location_location_listener = new LocationListener() {
			@Override
			public void onLocationChanged(Location _param1) {
				final double _lat = _param1.getLatitude();
				final double _lng = _param1.getLongitude();
				final double _acc = _param1.getAccuracy();
				_mapview1_controller.moveCamera(_lat, _lng);
				_mapview1_controller.zoomTo(14);
				map.put("Current Longitude", String.valueOf(_lng));
				map.put("Current Latitude", String.valueOf(_lat));
				fb_db.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
				timer = new TimerTask() {
					@Override
					public void run() {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								map.put("Current Longitude", String.valueOf(_lng));
								map.put("Current Latitude", String.valueOf(_lat));
								fb_db.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
							}
						});
					}
				};
				_timer.scheduleAtFixedRate(timer, (int)(0), (int)(10000));
			}
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {}
			@Override
			public void onProviderEnabled(String provider) {}
			@Override
			public void onProviderDisabled(String provider) {}
		};
		
		_fb_db_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					var_str_myvehicle = _childValue.get("Vehicle").toString();
				}
				count = 0;
				fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						list_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								list_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
							_mapview1_controller.addMarker("My Position", Double.parseDouble(_childValue.get("Current Latitude").toString()), Double.parseDouble(_childValue.get("Current Longitude").toString()));
							_mapview1_controller.setMarkerInfo("My Position", "My Position", _childValue.get("Current Latitude").toString().concat(",".concat(_childValue.get("Current Longitude").toString())));
							_mapview1_controller.setMarkerIcon("My Position", R.drawable.loc_logo_1);
						}
						else {
							for(int _repeat20 = 0; _repeat20 < (int)(list_map.size()); _repeat20++) {
								_mapview1_controller.addMarker(list_map.get((int)count).get("Vehicle").toString(), Double.parseDouble(list_map.get((int)count).get("Current Latitude").toString()), Double.parseDouble(list_map.get((int)count).get("Current Longitude").toString()));
								_mapview1_controller.setMarkerInfo(list_map.get((int)count).get("Vehicle").toString(), list_map.get((int)count).get("Vehicle").toString(), list_map.get((int)count).get("Current Latitude").toString().concat(",".concat(list_map.get((int)count).get("Current Longitude").toString())));
								if (list_map.get((int)count).get("Vehicle").toString().equals("BIKE")) {
									_mapview1_controller.setMarkerIcon("BIKE", R.drawable.marker_bike);
								}
								if (list_map.get((int)count).get("Vehicle").toString().equals("CAR")) {
									_mapview1_controller.setMarkerIcon("CAR", R.drawable.marker_car);
								}
								if (list_map.get((int)count).get("Vehicle").toString().equals("AUTO")) {
									_mapview1_controller.setMarkerIcon("AUTO", R.drawable.marker_auto);
								}
								if (list_map.get((int)count).get("Vehicle").toString().equals("BUS")) {
									_mapview1_controller.setMarkerIcon("BUS", R.drawable.marker_bus);
								}
								if (list_map.get((int)count).get("Vehicle").toString().equals("LORRY")) {
									_mapview1_controller.setMarkerIcon("LORRY", R.drawable.marker_lorry);
								}
								count++;
							}
						}
						list_map.clear();
						count = 0;
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				list_map.clear();
				count = 0;
				if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
					username = _childValue.get("Name").toString();
				}
				_drawer_name.setText(username);
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				_mapview1_controller.setMarkerVisible("My Position", false);
				count = 0;
				fb_db.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						list_map = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								list_map.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						if (_childKey.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
							_mapview1_controller.addMarker("My Position", Double.parseDouble(_childValue.get("Current Latitude").toString()), Double.parseDouble(_childValue.get("Current Longitude").toString()));
							_mapview1_controller.setMarkerInfo("My Position", "My Position", _childValue.get("Current Latitude").toString().concat(",".concat(_childValue.get("Current Longitude").toString())));
							_mapview1_controller.setMarkerIcon("My Position", R.drawable.loc_logo_1);
						}
						else {
							for(int _repeat15 = 0; _repeat15 < (int)(list_map.size()); _repeat15++) {
								_mapview1_controller.addMarker(list_map.get((int)count).get("Vehicle").toString(), Double.parseDouble(list_map.get((int)count).get("Current Latitude").toString()), Double.parseDouble(list_map.get((int)count).get("Current Longitude").toString()));
								_mapview1_controller.setMarkerInfo(list_map.get((int)count).get("Vehicle").toString(), list_map.get((int)count).get("Vehicle").toString(), list_map.get((int)count).get("Current Latitude").toString().concat(",".concat(list_map.get((int)count).get("Current Longitude").toString())));
								if (list_map.get((int)count).get("Vehicle").toString().equals("BIKE")) {
									_mapview1_controller.setMarkerIcon("BIKE", R.drawable.marker_bike);
								}
								if (list_map.get((int)count).get("Vehicle").toString().equals("CAR")) {
									_mapview1_controller.setMarkerIcon("CAR", R.drawable.marker_car);
								}
								if (list_map.get((int)count).get("Vehicle").toString().equals("AUTO")) {
									_mapview1_controller.setMarkerIcon("AUTO", R.drawable.marker_auto);
								}
								if (list_map.get((int)count).get("Vehicle").toString().equals("BUS")) {
									_mapview1_controller.setMarkerIcon("BUS", R.drawable.marker_bus);
								}
								if (list_map.get((int)count).get("Vehicle").toString().equals("LORRY")) {
									_mapview1_controller.setMarkerIcon("LORRY", R.drawable.marker_lorry);
								}
								count++;
							}
						}
						list_map.clear();
						count = 0;
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
				list_map.clear();
				count = 0;
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
		
		_drawer_cancel_logo_linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_drawer.closeDrawer(GravityCompat.START);
			}
		});
		
		_drawer_user_logo_linear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				intent.setClass(getApplicationContext(), UserProfileActivity.class);
				startActivity(intent);
			}
		});
		
		_drawer_bike_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					_mapview1_controller.setMarkerVisible("CAR", false);
					_mapview1_controller.setMarkerVisible("AUTO", false);
					_mapview1_controller.setMarkerVisible("BUS", false);
					_mapview1_controller.setMarkerVisible("LORRY", false);
				}
			}
		});
		
		_drawer_car_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					_mapview1_controller.setMarkerVisible("BIKE", false);
					_mapview1_controller.setMarkerVisible("AUTO", false);
					_mapview1_controller.setMarkerVisible("BUS", false);
					_mapview1_controller.setMarkerVisible("LORRY", false);
				}
			}
		});
		
		_drawer_auto_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					_mapview1_controller.setMarkerVisible("BIKE", false);
					_mapview1_controller.setMarkerVisible("CAR", false);
					_mapview1_controller.setMarkerVisible("BUS", false);
					_mapview1_controller.setMarkerVisible("LORRY", false);
				}
			}
		});
		
		_drawer_bus_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					_mapview1_controller.setMarkerVisible("BIKE", false);
					_mapview1_controller.setMarkerVisible("CAR", false);
					_mapview1_controller.setMarkerVisible("AUTO", false);
					_mapview1_controller.setMarkerVisible("LORRY", false);
				}
			}
		});
		
		_drawer_lorry_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton _param1, boolean _param2)  {
				final boolean _isChecked = _param2;
				if (_isChecked) {
					_mapview1_controller.setMarkerVisible("BIKE", false);
					_mapview1_controller.setMarkerVisible("CAR", false);
					_mapview1_controller.setMarkerVisible("AUTO", false);
					_mapview1_controller.setMarkerVisible("BUS", false);
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
		if (ContextCompat.checkSelfPermission(MainMapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			location.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 1, _location_location_listener);
		}
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
		dialog.setMessage("Confirm your response");
		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				finishAffinity();
				finish();
			}
		});
		dialog.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface _dialog, int _which) {
				
			}
		});
		dialog.create().show();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapview1.onDestroy();
	}
	
	@Override
	public void onStart() {
		super.onStart();
		mapview1.onStart();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		mapview1.onPause();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		mapview1.onResume();
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mapview1.onStop();
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
