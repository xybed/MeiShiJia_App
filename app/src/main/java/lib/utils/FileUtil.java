package lib.utils;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

public class FileUtil {
	
	private static String CacheDirectory = "/meishijia/";
	
	public static InputStream getInputStreamFromFile(File file) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	public static String getSdCardPath() {
		String sdCardPath = null;
		if (isMediaMounted()) {
			sdCardPath = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
		}
		return sdCardPath;
	}

	public static String getStringFromInputStream(InputStream inputStream) {
		String str = null;
		if (inputStream == null) {
			return str;
		}
		Reader isr = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				if (sb.length() > 0) {
					sb.append("\n");
				}
				sb.append(line);
			}
			str = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static boolean isMediaMounted() {
		return Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState());
	}

	public static File newFile(String dirPath, String fileName) {
		File file = null;
		File dir = new File(dirPath);
		if (!dir.exists()) {
			if (!dir.mkdirs()) {
				return file;
			}
		}
		file = new File(dir, fileName);
		return file;
	}

	public static void stringToFile(String str, File file, boolean isAdd) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file,isAdd);
			os.write(str.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void stringToFile(String str, File file) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			os.write(str.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void delectFile(File file){
		if (file.exists()) {
			file.delete();
		}
	}
	
	/**
	 * 获取或创建Cache目录
	 * 
	 * @param bucket
	 *            临时文件目录，bucket = "/cache/" ，则放在"sdcard/linked-joyrun/cache"; 如果bucket=""或null,则放在"sdcard/linked-joyrun/"
	 */
	public static String getMyCacheDir(String bucket) {
		String dir;

		// 保证目录名称正确
		if (bucket != null) {
			if (!bucket.equals("")) {
				if (!bucket.endsWith("/")) {
					bucket = bucket + "/";
				}
			}
		}

		if (isSDCardExist()) {
			dir = Environment.getExternalStorageDirectory().toString() + CacheDirectory + bucket;
		} else {
			dir = Environment.getDownloadCacheDirectory().toString() + CacheDirectory + bucket;
		}

		File f = new File(dir);
		if (!f.exists()) {
			f.mkdirs();
		}
		return dir;
	}

	/**
	 * 判断文件是否存在
	 * @return
	 */
	public static boolean isFileExists(String apkname) {
		try {
			String dir;
			if (isSDCardExist()) {
				dir = Environment.getExternalStorageDirectory().toString()
						+ CacheDirectory;
			} else {
				dir = Environment.getDownloadCacheDirectory().toString()
						+ CacheDirectory;
			}
			File f = new File(dir + apkname);
			if (!f.exists()) {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	public static boolean isSDCardExist() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}
	
	public static String getCacheSDPath() {
		String dir;
		if (isSDCardExist()) {
			dir = Environment.getExternalStorageDirectory().toString()
					+ CacheDirectory;
		} else {
			dir = Environment.getDownloadCacheDirectory().toString()
					+ CacheDirectory;
		}

		File f = new File(dir);
		if (!f.exists()) {
			f.mkdirs();
		}
		return dir;
	}


	public static File getFileFromInputStream(InputStream is, File file) throws IOException {
		byte[] buf = new byte[2048];
		int len = 0;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			while ((len = is.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			fos.flush();
			return file;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (Exception e) {
			}
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
			}
		}
		return file;
	}
	
}
