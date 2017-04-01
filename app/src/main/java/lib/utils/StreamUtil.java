package lib.utils;

import android.graphics.Bitmap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 7mu on 2016/3/4.
 * 关于流的工具
 */
public class StreamUtil {
    /**
     * @方法功能 InputStream 转为 byte
     */
    public static byte[] inputStream2Byte(InputStream inStream) {
        // ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        // byte[] buffer = new byte[1024];
        // int len = -1;
        // while ((len = inStream.read(buffer)) != -1) {
        // outSteam.write(buffer, 0, len);
        // }
        // outSteam.close();
        // inStream.close();
        // return outSteam.toByteArray();
        byte[] b = null;
        try {
            int count = 0;
            while (count == 0) {
                count = inStream.available();
            }
            b = new byte[count];
            inStream.read(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }

    // 将Bitmap转换成InputStream
    public static InputStream bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //Hint to the compressor, 0-100. 0 meaning compress for small size, 100 meaning compress for max quality. Some formats, like PNG which is lossless, will ignore the quality setting
        bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);//100 是压缩率，表示压缩0%; 如果不压缩是100，表示压缩率为0
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }
}
