package lib.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by 7mu on 2016/6/29.
 */
public class MD5Util {
    private static char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    public static String MD5(String s){
        if(s == null)
            return null;
        try {
            //获得MD5摘要算法的MessageDigest对象
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //使用指定的字节更新摘要
            messageDigest.update(s.getBytes());
            //获得密文
            byte[] md = messageDigest.digest();
            //把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for(int i=0;i<j;i++){
                byte b = md[i];
                str[k++] = hexDigits[b >>> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createParamSign(Map<String, String> param, String tokenKey){
        List<String> keyList = new ArrayList<>();
        for(Map.Entry<String, String> entry : param.entrySet()){
            keyList.add(entry.getKey());
        }
        Collections.sort(keyList);
        StringBuilder builder = new StringBuilder();
        builder.append(tokenKey);
        for(int i=0;i<keyList.size();i++){
            builder.append(keyList.get(i));
        }
        builder.append(tokenKey);
        return MD5(builder.toString());
    }
}
