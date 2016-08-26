package ts.subject.logcatcher.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Log;

public class LogcatcherClient {

	public static final String TAG = "Logcatcher";
	//Log
	public static final String CMD_LOGCAT_MAIN = "logcat -b main -v time";
	public static final String CMD_LOGCAT_RADIO = "logcat -b radio -v time";
	public static final String CMD_LOGCAT_SYSTEM = "logcat -b system -v time";
	public static final String CMD_LOGCAT_EVENT = "logcat -b event -v time";
	public static final String CMD_LOGCAT_DUMPSTATE = "dumpstate";
	public static final String CMD_LOGCAT_GETEVENT = "getevent";
	
	public static final String CMD_QXDM = "";
	public static final String CMD_KERNAL_KMSG = "cat /proc/kmsg";
	
	private static LocalSocket sSocket;
	private static OutputStream sOutputStream;
	private static InputStream sInputStream;
	
	private static boolean openSocket(){
		try {
			sSocket = new LocalSocket(LocalSocket.SOCKET_SEQPACKET);
			sSocket.connect(new LocalSocketAddress("logcatcher", LocalSocketAddress.Namespace.RESERVED));
			sOutputStream = sSocket.getOutputStream();
			sInputStream = sSocket.getInputStream();
			//get
		} catch (IOException e) {
			// TODO: handle exception
			Log.d(TAG, "logcatcher socket opened failed : " + e.toString());
			e.printStackTrace();
			sSocket = null;
			return false;
		}
		return true;
	}
	
	private static boolean writeLogcather(byte[] buf){
		for (int i = 0; i < 3; i++) {
			if (sSocket == null) {
				if (openSocket() == false) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO: handle exception
					}
					continue;
				}
			}
			try {
				sOutputStream.write(buf,0,buf.length);
				sOutputStream.flush();
				Log.d(TAG, "sOutputStream.write");
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					sSocket.close();
				} catch (IOException e2) {
					// TODO: handle exception
				}
                sSocket = null;
                Log.e("TctFeedback","sSMCSocket = null");
                return false;
			}
		}
		return false;
	}
	
	private static boolean sendCommand(byte[] cmd){
		
		return false;
	}
	
}
