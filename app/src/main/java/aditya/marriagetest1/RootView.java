package aditya.marriagetest1;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import aditya.marriagetest1.R;

public class RootView extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	public static FragmentManager fragmentManager;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] mEventTitles;
	public int selectedPos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_root_view);
		fragmentManager = getSupportFragmentManager();
		mTitle = mDrawerTitle = getTitle();
		mEventTitles = getResources().getStringArray(R.array.events_array);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mEventTitles));
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer,R.string.open_drawer,R.string.close_drawer) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}
			public void onDrawerOpened(View view) {
				getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if(savedInstanceState == null); {
			selectedItem(0);
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.root_view, menu);
		return true;
	}
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_rsvp).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if(mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		if(id == R.id.action_rsvp) {
			rsvpForm();
		}
		return super.onOptionsItemSelected(item);
	}
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectedItem(position);
		}
	}

	public void rsvpForm() {
		Intent intent = new Intent(getBaseContext(), rsvp_form.class);
		startActivity(intent);
	}
	private void selectedItem(int position) {
		Fragment fragment = new EventFragment();
		Bundle args = new Bundle();
		args.putInt(EventFragment.ARG_EVENT_NUMBER, position);
		fragment.setArguments(args);
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame,fragment).commit();
		selectedPos = position;
		mDrawerList.setItemChecked(position,true);
		setTitle(mEventTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(title);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newconf) {
		super.onConfigurationChanged(newconf);
		mDrawerToggle.onConfigurationChanged(newconf);
	}

	public void launchMap(View view) {
		String url;
		if(selectedPos == 2) {
			url = "https://www.google.com/maps/place/I11/@28.5684447,77.2394163,18z/data=!4m2!3m1!1s0x390ce24d4ec88383:0x736cdf02835ab072";
		}
		else if (selectedPos == 0) {
			return;
		}
		else {
			url = "https://www.google.com/maps/place/Mapple+Emerald/@28.5134674,77.1137146,14z/data=!4m5!1m2!2m1!1sMaple+hotel+jaipur+highway!3m1!1s0x0:0x34beed0a9de74590";
		}
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(url));
		intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
		startActivity(intent);
	}

	public static class EventFragment extends Fragment {
		public static final String ARG_EVENT_NUMBER = "event_number";
		/*public static GoogleMap googleMap;
		public static double Latitude, Longitude;
		public static View view;*/
		public EventFragment() {

		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle sis) {
			int i = getArguments().getInt(ARG_EVENT_NUMBER);
			String event = getResources().getStringArray(R.array.events_array)[i];
			getActivity().setTitle(event);
			View rootView = inflater.inflate(R.layout.fragment_event, container, false);
			/*MapsInitializer.initialize(this.getActivity());
			Latitude = 26;
			Longitude = 72;*/
			//setupMapIfNeeded();
			//((ImageView) rootView.findViewById(R.id.image)).setImageResource(R.drawable.ddw);
			return rootView;

		}
		/*public void setupMapIfNeeded() {
			if(googleMap == null) {
				googleMap = ((SupportMapFragment) RootView.fragmentManager.findFragmentById(R.id.location_map)).getMap();
				googleMap.setMyLocationEnabled(true);
				googleMap.addMarker(new MarkerOptions().position(new LatLng(Latitude,Longitude)));
				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Latitude, Longitude), 12.0f));
			}
		}
		@Override
		public void onResume() {
			super.onResume();
			setupMapIfNeeded();
		}*/
	}
}
