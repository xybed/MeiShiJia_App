package lib.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BugUtil {
	public static void stringToSdcard(String bug){
		String pathSD = FileUtil.getSdCardPath();
		if(pathSD == null){
			return;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		FileUtil.stringToFile(sdf.format(new Date())+":"+bug+"\n"
				,FileUtil.newFile(pathSD+"/AA/RealMadrid/", "bug.txt"),true);
	}
}
