package lib.cache;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import lib.utils.FileUtil;

/**
 * Created by 7mu on 2016/6/30.
 * 缓存json的model数据用的
 */
public class CacheJsonMgr {
    private static CacheJsonMgr cacheJsonMgr;

    private String dirPath;

    private CacheJsonMgr(Context context){
        dirPath = context.getFilesDir().getAbsolutePath();
    }

    public static CacheJsonMgr getInstance(Context context){
        if(cacheJsonMgr == null)
            cacheJsonMgr = new CacheJsonMgr(context);
        return cacheJsonMgr;
    }

    public Object getJsonObject(Class<?> clazz) {
        return getJsonObject(clazz,clazz.getSimpleName());
    }

    private <T> Object getJsonObject(Class<T> clazz, String cacheKey) {
        Object object = null;
        File file = FileUtil.newFile(dirPath, cacheKey);
        if(file.exists()){
            InputStream inputStream = FileUtil.getInputStreamFromFile(file);
            String jsonStr = FileUtil.getStringFromInputStream(inputStream);
            if (jsonStr == null) {
                return null;
            }
            if(jsonStr.startsWith("[")){
                object = JSON.parseArray(jsonStr, clazz);
            }else {
                object = JSON.parseObject(jsonStr, clazz, Feature.IgnoreNotMatch, Feature.InitStringFieldAsEmpty);
            }
        }
        return object;
    }

    public void saveJson(String jsonStr, String fileName) {
        if (jsonStr == null || jsonStr.isEmpty()) {
            return;
        }
        if (fileName == null || fileName.isEmpty()) {
            return;
        }
        File file = FileUtil.newFile(dirPath, fileName);
        FileUtil.stringToFile(jsonStr, file);
    }

    public void delectJson(String fileName){
        if (fileName == null || fileName.length() < 1) {
            return;
        }
        File file = FileUtil.newFile(dirPath, fileName);
        FileUtil.delectFile(file);
    }
}
