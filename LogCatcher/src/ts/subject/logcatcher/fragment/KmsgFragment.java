package ts.subject.logcatcher.fragment;

import ts.subject.logcatcher.LogcatcherInterface;
import ts.subject.logcatcher.R;
import ts.subject.logcatcher.util.LogcatUtils;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class KmsgFragment extends Fragment implements OnClickListener,LogcatcherInterface{

	public static KmsgFragment sKmsgFragment = null;
	public static KmsgFragment instance(){
		return sKmsgFragment != null ? sKmsgFragment : (sKmsgFragment = new KmsgFragment());
		//return new KmsgFragment();
	}
	
	private Activity mActivity;
	
	private Button mCtrlButton;
	private TextView mStateTextView;
	
	private int mCurState = STATE_STOP;
	//******************************Created********************************************
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mActivity = activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (savedInstanceState != null) {
			mCurState = savedInstanceState.getInt("state");
		}

		View view = null;
		view = inflater.inflate(R.layout.fragment_kmsg, null);
		mStateTextView = (TextView) view.findViewById(R.id.model_running_state);
		mCtrlButton = (Button) view.findViewById(R.id.model_start);
		mCtrlButton.setOnClickListener(this);
		return view;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initDefaultState();
	}
	
	private void initDefaultState(){
		if (mCurState == STATE_RUNNING) {
			mStateTextView.setText(LogcatUtils.formatStateString(mActivity,getString(R.string.kmsg)));
			mCtrlButton.setText(getText(R.string.stop));
		}else if (mCurState == STATE_STOP) {
			mStateTextView.setText(LogcatUtils.formatStateString(mActivity,getString(R.string.stop)));
			mCtrlButton.setText(getString(R.string.start));
		}
	}
	
	//******************************Started************************************
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	//******************************Resumed************************************
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt("state", mCurState);
	}
	//******************************onPaused************************************
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	//******************************onStopped************************************
	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	//******************************onDestroyed************************************
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

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.model_start:
			if (mCurState == STATE_STOP) {
				//state changed
				mCurState = STATE_RUNNING;
				mCtrlButton.setText(getString(R.string.stop));
				mStateTextView.setText(LogcatUtils.formatStateString(mActivity,getString(R.string.kmsg)));
				//handle some
				Toast.makeText(mActivity, LogcatUtils.formatStateString(mActivity,getString(R.string.kmsg)), Toast.LENGTH_SHORT).show();
			}else if (mCurState == STATE_RUNNING) {
				//state changed
				mCurState = STATE_STOP;
				mCtrlButton.setText(getString(R.string.start));
				mStateTextView.setText(LogcatUtils.formatStateString(mActivity,getText(R.string.stop)));
				//handle some
				Toast.makeText(mActivity,"stop", Toast.LENGTH_SHORT).show();
			}
			break;

		default:
			break;
		}
	}
}
