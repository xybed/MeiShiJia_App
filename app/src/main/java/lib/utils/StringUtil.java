package lib.utils;

/**
 * Created by 7mu on 2016/5/24.
 * 关于字符串的工具类
 */
public class StringUtil {

    /**
     * 判断字符串是否为空
     * @param s 字符串
     * @return true代表空，false代表不为空
     */
    public static boolean isEmpty(String s){
        return s == null || s.length() == 0 || s.trim().length() == 0;
    }

    /**
     * 替换\n
     *
     * @param str
     * @return
     */
    public static String replaceWrap(String str) {
        if(!isEmpty(str)){
            str = str.replaceAll("\\r\\n", "\n").replaceAll("\\\\n", "\n").replaceAll("\\r\\r","\n");
        }
        return str;
    }
}
