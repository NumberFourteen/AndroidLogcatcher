package ts.subject.logcatcher;

import java.util.ArrayList;

import ts.subject.logcatcher.fragment.KmsgFragment;
import ts.subject.logcatcher.fragment.LogcatFragment;
import ts.subject.logcatcher.fragment.OthersFragment;
import ts.subject.logcatcher.fragment.QxdmFragment;
import ts.subject.logcatcher.preference.SettingsPreference;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

public class LogcatcherActivity extends FragmentActivity implements OnItemClickListener{

	private DrawerLayout mDrawerLayout;
	private FrameLayout mFrameLayout;
	private ListView mListView;
	private ActionBarDrawerToggle mDrawerToggle;
	
	private String[] mCmdStrings;
	private CharSequence mTitle;
	private CharSequence mDrawerTitle;
	
	private LogcatFragment mLogcatFragment;
	private KmsgFragment mKmsgFragment;
	private QxdmFragment mQxdmFragment;
	private OthersFragment mOthersFragment;
	
	private ArrayList<Fragment> mFragments;
	
	private int mCurPosition;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mTitle = mDrawerTitle = getTitle();
		initView();
		initDefaultFragment();
	}

	private void initDefaultFragment(){
		mFragments = new ArrayList<Fragment>();
		mLogcatFragment = LogcatFragment.instance();
		mKmsgFragment = KmsgFragment.instance();
		mQxdmFragment = QxdmFragment.instance();
		mOthersFragment = OthersFragment.instance();
		mFragments.add(mLogcatFragment);
		mFragments.add(mKmsgFragment);
		mFragments.add(mQxdmFragment);
		mFragments.add(mOthersFragment);
		
		mCurPosition = 0;
		changeContentFragment(mLogcatFragment);
	}

	private void changeContentFragment(Fragment fragment){
		FragmentManager fManager = getSupportFragmentManager();
		FragmentTransaction tx =  fManager.beginTransaction();
		tx.replace(R.id.content_frame, fragment);
		//tx.addToBackStack(null);
		tx.commit();
	}
	
	private void initView(){
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
		mListView = (ListView) findViewById(R.id.left_drawer);
		mDrawerToggle = new MyActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_launcher, R.string.drawer_open, R.string.drawer_closed);
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setDisplayUseLogoEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(false);

		mCmdStrings = getResources().getStringArray(R.array.cmd_list);
		mListView.setAdapter(new ArrayAdapter<String>(this, R.layout.cmd_listview_item,mCmdStrings));
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> contentView, View parentView, int position, long id) {
		// TODO Auto-generated method stub
		mDrawerTitle = mCmdStrings[position];
		onSelectedItem(position);
	}
	
	private void onSelectedItem(int position){
		mDrawerLayout.closeDrawers();
		mCurPosition = position;
		changeContentFragment(mFragments.get(position));
	}
	
	//just for the adjustment between DrawerLayout and actionbar
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onPrepareOptionsMenu(menu);
	}
	
	private class MyActionBarDrawerToggle extends ActionBarDrawerToggle{

		public MyActionBarDrawerToggle(Activity activity,
				DrawerLayout drawerLayout, int drawerImageRes,
				int openDrawerContentDescRes, int closeDrawerContentDescRes) {
			super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes,
					closeDrawerContentDescRes);
			// TODO Auto-generated constructor stub
			
		}
		
		@Override
		public void onDrawerClosed(View drawerView) {
			// TODO Auto-generated method stub
			super.onDrawerClosed(drawerView);
			getActionBar().setTitle(mDrawerTitle);
			invalidateOptionsMenu();
		}
		
		@Override
		public void onDrawerOpened(View drawerView) {
			// TODO Auto-generated method stub
			super.onDrawerOpened(drawerView);
			getActionBar().setTitle(mTitle);
			invalidateOptionsMenu();
		}
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		if (item.getItemId() == R.id.action_settings) {
			Intent intent = new Intent(this,SettingsPreference.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(null != LogcatFragment.sLogcatFragment){
			LogcatFragment.sLogcatFragment = null;
		}
		//clear the static values to avoiding OOM
		
	}
}
