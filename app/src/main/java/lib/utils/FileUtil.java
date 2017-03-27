package lib.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtil {

    public static String CacheDirectory = "/MeiShiJia/";
    public static String Bug = "bug";
    public static String BaiduNavi = "baidunavi";

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
        if (isSDCardExist()) {
            sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
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

    /**
     * 创建文件
     * 1.文件路径是否存在，不存在则mkdirs
     * 2.文件是否存在，不存在则createNewFile
     * 3.返回创建好的文件对象
     * @param path 文件路径
     * @param fileName 文件名
     * @return 文件对象
     */
    public static File createFile(String path, String fileName) {
        File file = null;
        File dirFile = null;
        dirFile = new File(path);
        if (!dirFile.exists()) {
            boolean is = dirFile.mkdirs();
            if(!is){
                MyLogUtil.e("file", "路径创建不成功");
            }else {
                MyLogUtil.e("file", "cnmb");
            }
        }

        file = new File(path, fileName);
        if (file == null || !file.exists()) {
            try {
                boolean isSuccess = file.createNewFile();
                if(!isSuccess){
                    MyLogUtil.e("file", "文件创建不成功");
                }else {
                    MyLogUtil.e("file", "cnmb");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    /**
     * 创建文件夹路径
     * @param path 路径
     */
    public static void mkdirs(String path){
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
    }

    public static File getFile(String path, String fileName) {
        File file = null;
        File dirFile = null;
        dirFile = new File(path);
        if (dirFile.isDirectory() && !dirFile.exists()) {
            return file;
        }
        file = new File(path, fileName);
        return file;
    }

    /**
     * 字符串写入文件
     * @param str 字符串
     * @param file 要写入的文件，需存在
     * @param isAdd 是否增加，写在文件的后面
     */
    public static void stringToFile(String str, File file, boolean isAdd) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(file, isAdd);
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
     * 保存bug文件
     * 1.格式化日期，看bug是什么时候产生的，方便确定是不是当时的bug
     * 2.字符串写入文件，调用createFile
     * @param bug bug信息
     */
    public static void saveBug(String bug){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        FileUtil.stringToFile(sdf.format(new Date())+":"+bug ,
                FileUtil.createFile(FileUtil.getMyCacheDir(Bug), "bug.txt"),
                true);
    }

    public static void deleteMyCacheFile(String bucket) {
        File fileDic = new File(FileUtil.getMyCacheDir(bucket));
        if (fileDic.exists()) {
            File[] files = fileDic.listFiles();
            for (File file : files) {
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    /**
     * 获取或创建Cache目录
     * 保证路径以"/"结尾
     * 有些机子会没有sd卡，保证能拿到一个路径
     * @param bucket 临时文件目录
     */
    public static String getMyCacheDir(String bucket) {
        String dir;

        // 保证目录名称正确
        if (!TextUtils.isEmpty(bucket)) {
            if (!bucket.endsWith("/")) {
                bucket = bucket + "/";
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
     * 是否存在sd卡
     * @return 布尔值
     */
    public static boolean isSDCardExist() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getCacheSDPath() {
        String dir;
        if (isSDCardExist()) {
            dir = Environment.getExternalStorageDirectory().toString() + CacheDirectory;
        } else {
            dir = Environment.getDownloadCacheDirectory().toString() + CacheDirectory;
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
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public static boolean saveFileFromIS(InputStream inputStream, File outFile) {
        //将返回结果转化为流，并写入文件
        int len;
        byte[] buf = new byte[2048];
        //可以在这里自定义路径
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(outFile);
            while ((len = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, len);
            }

            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static void copyFile(File src, File dest) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    public static byte[] File2byte(File file) {
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    /*
     * Java文件操作 获取不带扩展名的文件名
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /*
     * Java文件操作 获取文件扩展名 及包含.
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot);
            }
        }
        return filename;
    }

    /*
     * Java文件操作 获取文件扩展名 不包含.
     */
    public static String getExtensionNameNoDot(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

}
