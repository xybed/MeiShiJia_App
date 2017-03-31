package lib.utils;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class SystemUtil {

	public static String getAppPackageName(Context context) {
		String packageNames = null;
		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			packageNames = info.packageName;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TextUtils.isEmpty(packageNames)?"":packageNames;
	}

	/** 获取设备唯一标识 */
	public static String getDeviceIMEI(Context context){
		String Imei = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
				.getDeviceId();
		return Imei;
	}

	/**
	 * 判断是否是最前台的活动
	 * @return
	 */
	public static boolean isTopActivity(Context congtext, String activityName)
	{
		boolean isTop = false;
		ActivityManager am = (ActivityManager)congtext.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		if (cn.getClassName().contains(activityName)){
			isTop = true;
		}
		return isTop;
	}
	/**
	 * 在栈中，是否有某个活动
	 *
	 * @param context
	 * @param classPath
	 *            完整类路径
	 * @return
	 */
	public static boolean isLuncher(Context context, String classPath) {
		ActivityManager manager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningTaskInfo runInfo : manager.getRunningTasks(100)) {
			// 获取最底层的Activity。我们最底层应该是MainActivity
			String name = runInfo.baseActivity.getClassName();
			if (name.equals(classPath)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断某个应用是否在前台运行
	 * @param packageName  应用包名com.yiping.eping
	 * @return  true为在前台
	 */
	public static boolean isFrontRunning(Context context, String packageName) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
		if (tasksInfo.size() > 0) {
			//应用程序位于堆栈的顶层
			if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
				//在前台
				return true;
			}
		}
		return false;
	}
	/**
	 * 获取手机运营商编码
	 * @param context
	 * @return
	 */
	public static String getProvidersCode(Context context) {
		try{
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String ProvidersName = null;
			// 返回唯一的用户ID;就是这张卡的编号   , 国际移动用户识别码  IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
			String IMSI = telephonyManager.getSubscriberId();
			if (IMSI.startsWith("46000")) {
				ProvidersName = "46000";
			} else if (IMSI.startsWith("46002")) {
				ProvidersName = "46002";
			} else if (IMSI.startsWith("46001")) {
				ProvidersName = "46001";
			} else if (IMSI.startsWith("46003")) {
				ProvidersName = "46003";
			}
			return ProvidersName;
		}catch(Exception e){
			Log.w("在SystemUtil  65行", "");
			return "";
		}
//        BugUtil.stringToSdcard(IMSI);
	}


	/**
	 * 判断是否安装
	 */
	public static boolean isInstalled(Context context, String packageName){
		//获取packagemanager
		final PackageManager packageManager = context.getPackageManager();
		//获取所有已安装程序的包信息
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
		//用于存储所有已安装程序的包名
		List<String> packageNames = new ArrayList<String>();
		//从pinfo中将包名字逐一取出，压入pName list中
		if(packageInfos != null){
			for(int i = 0; i < packageInfos.size(); i++){
				String packName = packageInfos.get(i).packageName;
				packageNames.add(packName);
			}
		}
		//判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
		return packageNames.contains(packageName);
	}

//	/**
//	 * 获取手机运营商
//	 * @param context
//	 * @return
//	 */
//	public static String getProvidersName(Context context) {
//		try{
//			TelephonyManager telephonyManager = (TelephonyManager) context
//					.getSystemService(Context.TELEPHONY_SERVICE);
//			String ProvidersName = null;
//			// 返回唯一的用户ID;就是这张卡的编号   , 国际移动用户识别码  IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
//			String IMSI = telephonyManager.getSubscriberId();
//			if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
//				ProvidersName = "中国移动";
//			} else if (IMSI.startsWith("46001")) {
//				ProvidersName = "中国联通";
//			} else if (IMSI.startsWith("46003")) {
//				ProvidersName = "中国电信";
//			}
//			return ProvidersName;
//		}catch(Exception e){
//			Log.w("在SystemUtil  65行", "");
//			return "";
//		}
////        BugUtil.stringToSdcard(IMSI);
//	}
	// /**
	// * 判断是否开启某个权限....这个方法没用
	// * @param context
	// * @param permission
	// *
	// * "android.permission.RECORD_AUDIO"语音
	// * "android.permission.CAMERA" 摄像头
	// * @return
	// */
	// public static boolean hasPermission(Context context,String permission){
	// PackageManager pm = context.getPackageManager();
	// boolean hasPermission = (PackageManager.PERMISSION_GRANTED ==
	// pm.checkPermission(permission, "com.yiping.eping"));
	// return hasPermission;
	// }
	// public static void showPermission(Context context){
	// // try {
	// PackageManager pm = context.getPackageManager();
	// boolean hasPermission = (PackageManager.PERMISSION_DENIED ==
	// pm.checkPermission("android.permission.RECORD_AUDIO",
	// "com.yiping.eping"));
	// Log.e("masen","权限清单被禁止了--->" + hasPermission);
	// // PackageInfo pack =
	// pm.getPackageInfo("com.yiping.eping",PackageManager.GET_PERMISSIONS);
	// // String[] permissionStrings = pack.requestedPermissions;
	// // for(int i=0;i<permissionStrings.length;i++)
	// // Log.e("masen","权限清单--->" + permissionStrings[i]);
	// // } catch (NameNotFoundException e) {
	// // e.printStackTrace();
	// // }
	// }
	/**
	 * 获取手机语言
	 *
	 * @param context
	 * @return
	 */
	public static String getLanguage(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		return language;
	}

	/**
	 * 获取手机所在时区
	 *
	 * @param context
	 * @return
	 */
	public static String getTimeZone(Context context) {
		TimeZone tz = TimeZone.getDefault();
		return tz.getDisplayName(false, TimeZone.SHORT);
	}

	/**
	 * 判断WIFI是否连接
	 *
	 * @param context
	 * @return
	 */
	public static String getNetState(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		// Log.d("Network",wifi.toString());
		if (wifi == State.CONNECTED || wifi == State.CONNECTING) {
			return "wifi";
		}
		return "";
	}

	/**
	 * 获取网络类型
	 *
	 * @param context
	 * @return
	 */
	public static String getNetWorkName(Context context) {
		try{
			ConnectivityManager conMan = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			State stateWiFi = wifi.getState();
			if (stateWiFi == State.CONNECTED || stateWiFi == State.CONNECTING) {
				return "wifi";
			}
			// mobile 3G Network
			NetworkInfo mobile = conMan
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			State stateMob = mobile.getState();
			if (stateMob == State.CONNECTED || stateMob == State.CONNECTING) {
				int mobileType = mobile.getSubtype();
				return getCurrentNetworkType(mobileType);
			}
			return "";

		}catch(Exception e){
			Log.w("在SystemUtil  163行", "");
			return "";
		}
	}

	private static final int NETWORK_TYPE_UNAVAILABLE = -1;
	private static final int NETWORK_TYPE_WIFI = -101;
	private static final int NETWORK_CLASS_WIFI = -101;
	private static final int NETWORK_CLASS_UNAVAILABLE = -1;
	/** Unknown network class. */
	private static final int NETWORK_CLASS_UNKNOWN = 0;
	/** Class of broadly defined "2G" networks. */
	private static final int NETWORK_CLASS_2_G = 1;
	/** Class of broadly defined "3G" networks. */
	private static final int NETWORK_CLASS_3_G = 2;
	/** Class of broadly defined "4G" networks. */
	private static final int NETWORK_CLASS_4_G = 3;
	// 适配低版本手机
	/** Network type is unknown */
	public static final int NETWORK_TYPE_UNKNOWN = 0;
	/** Current network is GPRS */
	public static final int NETWORK_TYPE_GPRS = 1;
	/** Current network is EDGE */
	public static final int NETWORK_TYPE_EDGE = 2;
	/** Current network is UMTS */
	public static final int NETWORK_TYPE_UMTS = 3;
	/** Current network is CDMA: Either IS95A or IS95B */
	public static final int NETWORK_TYPE_CDMA = 4;
	/** Current network is EVDO revision 0 */
	public static final int NETWORK_TYPE_EVDO_0 = 5;
	/** Current network is EVDO revision A */
	public static final int NETWORK_TYPE_EVDO_A = 6;
	/** Current network is 1xRTT */
	public static final int NETWORK_TYPE_1xRTT = 7;
	/** Current network is HSDPA */
	public static final int NETWORK_TYPE_HSDPA = 8;
	/** Current network is HSUPA */
	public static final int NETWORK_TYPE_HSUPA = 9;
	/** Current network is HSPA */
	public static final int NETWORK_TYPE_HSPA = 10;
	/** Current network is iDen */
	public static final int NETWORK_TYPE_IDEN = 11;
	/** Current network is EVDO revision B */
	public static final int NETWORK_TYPE_EVDO_B = 12;
	/** Current network is LTE */
	public static final int NETWORK_TYPE_LTE = 13;
	/** Current network is eHRPD */
	public static final int NETWORK_TYPE_EHRPD = 14;
	/** Current network is HSPA+ */
	public static final int NETWORK_TYPE_HSPAP = 15;

	/**
	 * 获取网络类型
	 *
	 * @return
	 */
	public static String getCurrentNetworkType(int networkType) {
		int networkClass = getNetworkClassByType(networkType);
		String type = "";
		switch (networkClass) {
			case NETWORK_CLASS_UNAVAILABLE:
				type = "";
				break;
			case NETWORK_CLASS_WIFI:
				type = "wifi";
				break;
			case NETWORK_CLASS_2_G:
				type = "2G";
				break;
			case NETWORK_CLASS_3_G:
				type = "3G";
				break;
			case NETWORK_CLASS_4_G:
				type = "4G";
				break;
			case NETWORK_CLASS_UNKNOWN:
				type = "";
				break;
			default:
				type = "";
				break;
		}
		return type;
	}

	private static int getNetworkClassByType(int networkType) {
		switch (networkType) {
			case NETWORK_TYPE_UNAVAILABLE:
				return NETWORK_CLASS_UNAVAILABLE;
			case NETWORK_TYPE_WIFI:
				return NETWORK_CLASS_WIFI;
			case NETWORK_TYPE_GPRS:
			case NETWORK_TYPE_EDGE:
			case NETWORK_TYPE_CDMA:
			case NETWORK_TYPE_1xRTT:
			case NETWORK_TYPE_IDEN:
				return NETWORK_CLASS_2_G;
			case NETWORK_TYPE_UMTS:
			case NETWORK_TYPE_EVDO_0:
			case NETWORK_TYPE_EVDO_A:
			case NETWORK_TYPE_HSDPA:
			case NETWORK_TYPE_HSUPA:
			case NETWORK_TYPE_HSPA:
			case NETWORK_TYPE_EVDO_B:
			case NETWORK_TYPE_EHRPD:
			case NETWORK_TYPE_HSPAP:
				return NETWORK_CLASS_3_G;
			case NETWORK_TYPE_LTE:
				return NETWORK_CLASS_4_G;
			default:
				return NETWORK_CLASS_UNKNOWN;
		}
	}
}
