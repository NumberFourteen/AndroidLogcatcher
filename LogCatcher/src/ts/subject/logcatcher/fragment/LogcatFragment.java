package ts.subject.logcatcher.fragment;

import java.util.HashMap;

import ts.subject.logcatcher.LogcatcherInterface;
import ts.subject.logcatcher.R;
import ts.subject.logcatcher.util.*;
import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;



public class LogcatFragment extends Fragment implements OnClickListener,OnCheckedChangeListener,LogcatcherInterface{

	private Activity mActivity;
	
	private View mRootView;
	private RadioGroup mRadioGroup;
	private RadioButton mMainButton;
	private RadioButton mRadioButton;
	private RadioButton mSystemButton;
	private RadioButton mEventButton;
	private Button mCtrlButton;
	private TextView mStateTextView;
	
	
	private int mCurChecked = -1;
	
	private int mCurState = STATE_STOP;
	
	private String mCurCommand = null;
	private HashMap<Integer, String> mCmdMap;
	
	private LogcatFragment(){
	}
	
	// ******************************Created********************************************
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = activity;
		Log.d("ptt", "onAttach");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d("ptt", "onCreate");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (savedInstanceState != null) {
			mCurCommand = savedInstanceState.getString("cmd");
			mCurChecked = savedInstanceState.getInt("id");
			mCurState = savedInstanceState.getInt("state");
		}
		View mRootView = null;
		if(mRootView == null){
			mRootView = inflater.inflate(R.layout.fragment_logcat, null);
			mRadioGroup = (RadioGroup) mRootView.findViewById(R.id.logcat_radio_group);
			mMainButton = (RadioButton) mRootView.findViewById(R.id.logcat_main);
			mRadioButton = (RadioButton) mRootView.findViewById(R.id.logcat_radio);
			mSystemButton = (RadioButton) mRootView.findViewById(R.id.logcat_system);
			mEventButton = (RadioButton) mRootView.findViewById(R.id.logcat_event);
			
			mMainButton.setOnClickListener(this);
			mRadioButton.setOnClickListener(this);
			mSystemButton.setOnClickListener(this);
			mEventButton.setOnClickListener(this);
			
			mCtrlButton = (Button) mRootView.findViewById(R.id.model_start);
			mCtrlButton.setOnClickListener(this);
			
			mStateTextView = (TextView) mRootView.findViewById(R.id.model_running_state);
		}
		Log.d("ptt", "onCreateview");
		return mRootView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initCmdArray();
		initDefaultState();
		Log.d("ptt", "onActivityCreated");
	}

	// ******************************Started************************************
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("ptt", "onStart");
	}

	// ******************************Resumed************************************
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putString("cmd", mCurCommand);
		outState.putInt("id", mCurChecked);
		outState.putInt("state", mCurState);
	}
	// ******************************onPaused************************************
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	// ******************************onStopped************************************
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	// ******************************onDestroyed************************************
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
	}
	
	public static LogcatFragment sLogcatFragment = null;

	public static LogcatFragment instance(){
		if (sLogcatFragment == null) {
			return new LogcatFragment();
		}
		return sLogcatFragment;
	}
	
	private void initDefaultState(){
		int id = getCheckedId();
		if (id == -1) {
			if (mCurChecked == -1 && mCurCommand == null) {
				id = R.id.logcat_main;
			}else {
				id = mCurChecked;
			}
		}
		Log.d("ptt", "id = " + id);
		mCurCommand = mCmdMap.get(id);
		mCurChecked = id;
		mRadioGroup.check(id);
		
		//
		if (mCurState == STATE_RUNNING) {
			mStateTextView.setText(LogcatUtils.formatStateString(mActivity,mCurCommand));
			mCtrlButton.setText(getText(R.string.stop));
		}else if (mCurState == STATE_STOP) {
			mStateTextView.setText(LogcatUtils.formatStateString(mActivity,getString(R.string.stop)));
			mCtrlButton.setText(getString(R.string.start));
		}
	}

	private int getCheckedId(){
		return mRadioGroup.getCheckedRadioButtonId();
	}
	
	private void initCmdArray(){
		mCmdMap = new HashMap<Integer,String>();
		mCmdMap.put(R.id.logcat_main,getString(R.string.logcat_main));
		mCmdMap.put(R.id.logcat_radio,getString(R.string.logcat_radio));
		mCmdMap.put(R.id.logcat_system,getString(R.string.logcat_system));
		mCmdMap.put(R.id.logcat_event,getString(R.string.logcat_event));
	}
	
	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.model_start:
			if (mCurState == STATE_STOP) {
				//state changed
				mCurState = STATE_RUNNING;
				mCtrlButton.setText(getString(R.string.stop));
				mStateTextView.setText(LogcatUtils.formatStateString(mActivity,mCurCommand));
				//handle some
				Toast.makeText(mActivity, mCurCommand + mCurChecked, Toast.LENGTH_SHORT).show();
			}else if (mCurState == STATE_RUNNING) {
				//state changed
				mCurState = STATE_STOP;
				mCtrlButton.setText(getString(R.string.start));
				mStateTextView.setText(LogcatUtils.formatStateString(mActivity,getText(R.string.stop)));
				//handle some
				Toast.makeText(mActivity, "stop", Toast.LENGTH_SHORT).show();
			}
			break;
		//radio button
		case R.id.logcat_main:
		case R.id.logcat_radio:
		case R.id.logcat_system:
		case R.id.logcat_event:
			mCurCommand = mCmdMap.get(view.getId());
			mCurChecked = view.getId();
			break;
		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		// TODO Auto-generated method stub
		mCurCommand = mCmdMap.get(checkedId);
		mCurChecked = checkedId;
	}
}
