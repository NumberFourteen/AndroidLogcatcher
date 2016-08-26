package ts.subject.logcatcher.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class QxdmFragment extends Fragment {

	public static QxdmFragment sQxdmFragment = null;
	public static QxdmFragment instance(){
		//return sQxdmFragment != null ? sQxdmFragment : (sQxdmFragment = new QxdmFragment());
		return new QxdmFragment();
	}
	// ******************************Created********************************************
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
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
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

	// ******************************Started************************************
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	// ******************************Resumed************************************
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
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
}
