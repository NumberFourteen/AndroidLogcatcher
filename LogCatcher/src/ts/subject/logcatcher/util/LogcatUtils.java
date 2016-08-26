package ts.subject.logcatcher.util;

import android.app.Activity;
import ts.subject.logcatcher.LogcatcherInterface;
import ts.subject.logcatcher.R;

public class LogcatUtils implements LogcatcherInterface{
	public static  CharSequence formatStateString(Activity activity,CharSequence stateString){
		return activity.getString(R.string.running_state) + stateString;
		
	}
}
