package lib.utils;

import java.text.DecimalFormat;

/**
 * 处理此类，出现无法转换的问题
 *
 * @author gsma
 * @date 2015-6-29
 */
public class NumberUtil {

    /**
     * 判定，如果转换成功，返回成功值，失败返回默认值
     *
     * @param default_value
     * @param value
     * @return
     */
    public static int parseInt(String value, int default_value) {
        int result = default_value;
        try {
            result = Integer.parseInt(value);
        } catch (Exception e) {
            result = default_value;
            e.printStackTrace();
        }
        return result;
    }

    public static double parseDouble(String value, double default_value) {
        double result = default_value;
        try {
            result = new DecimalFormat().parse(value).doubleValue();
        } catch (Exception e) {
            result = default_value;
            e.printStackTrace();
        }
        return result;
    }

    public static long parseLong(String value, long default_value) {
        long result = default_value;
        try {
            result = Long.parseLong(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
