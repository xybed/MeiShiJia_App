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

    /**
     * 是否两个字符串是否匹配，即匹配字符串是否是元字符串的开头/拼音小写的开头
     */
    public static boolean isMatching(String filterRes, String filterString) {
        if (filterRes == null || filterString == null) {
            return false;
        }
        CharacterParser characterParser = CharacterParser.getInstance();
        //过滤词是开头
        if (filterRes.contains(filterString)) {
            return true;
        }
        //过滤词是原字符串的拼音小写开头
        if (characterParser.getSelling(filterRes).toLowerCase().contains(filterString.toLowerCase())) {
            return true;
        }
        return false;
    }
}
