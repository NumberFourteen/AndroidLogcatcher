package ts.subject.logcatcher.preference;

import ts.subject.logcatcher.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.Toast;

public class SettingsPreference extends PreferenceActivity implements OnPreferenceChangeListener{
	
	private static final String KEY_SELECT_SDCARD = "logcater_settings_path_choices";
	private static final String KEY_TOTAL_SIZE = "logcater_settings_total_size";
	private static final String KEY_SEGMENT_SIZE = "logcater_settings_segment_size";
	
	private ListPreference mListPreference;
	private EditTextPreference mTotalView;
	private EditTextPreference mSegmentView;
	
	//private SharedPreferences mSp;
	//private Editor mEditor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preference_settings);
		//mSp = getPreferenceManager().getDefaultSharedPreferences(this);
		//mEditor = mSp.edit();
		initPreference();
	}
	
	private void initPreference() {
		// TODO Auto-generated method stub
		mListPreference = (ListPreference) findPreference(KEY_SELECT_SDCARD);
		mTotalView = (EditTextPreference) findPreference(KEY_TOTAL_SIZE);
		mSegmentView = (EditTextPreference) findPreference(KEY_SEGMENT_SIZE);
		mListPreference.setOnPreferenceChangeListener(this);
		mTotalView.setOnPreferenceChangeListener(this);
		mSegmentView.setOnPreferenceChangeListener(this);
		
		mTotalView.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
		mSegmentView.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
		
		initDefaultValue();
	}
	
	private void initDefaultValue(){
		mListPreference.setSummary(mListPreference.getEntry());
//		if (-1 == mSp.getInt(KEY_TOTAL_SIZE, -1)) {
//			mEditor.putInt(KEY_TOTAL_SIZE, 1024);
//			
//		}
//
//		if (-1 == mSp.getInt(KEY_SEGMENT_SIZE, -1)) {
//			mEditor.putInt(KEY_SEGMENT_SIZE, 50);
//		}
//		mEditor.commit();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		if (preference instanceof ListPreference) {
			ListPreference listPreference=(ListPreference)preference;
			CharSequence[] entries=listPreference.getEntries();
			//获取ListPreference中的实体内容的下标值
			int index=listPreference.findIndexOfValue((String)newValue);
			//把listPreference中的摘要显示为当前ListPreference的实体内容中选择的那个项目
			listPreference.setSummary(entries[index]);
		}
		return true;
	}
}
