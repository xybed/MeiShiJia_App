package lib.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by 7mu on 2016/5/23.
 * 与图片选择和拍照有关的工具类
 */
public class PhotoUtil {
    //为了区分于经常用的1和2，就定义大一些
    private static final int PHOTO_CHOICE_REQUEST_CODE = 19;//选择图片
    private static final int PHOTO_TAKE_REQUEST_CODE = 20;//拍照

    /**
     * 调用系统的选择图片功能
     * @param activity
     */
    public static void selectPhoto(Activity activity){
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
        activity.startActivityForResult(intent, PHOTO_CHOICE_REQUEST_CODE);
    }

    //静态的原因是为了防止Activity回来的时候被销毁
    public static File photoFile = null;

    /**
     * 调用系统的拍照功能
     * @param activity
     */
    public static void takePhoto(Activity activity){
        //有相机app并且有sd卡
        if(hasCamera(activity) && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            photoFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".png");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri fileUri = Uri.fromFile(photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            activity.startActivityForResult(intent, PHOTO_TAKE_REQUEST_CODE);
        }else{
            ToastUtil.show("缺少相机功能或没有内存卡");
        }
    }

    /**
     * 判断手机是否带有相机app
     * @param activity
     * @return
     */
    private static boolean hasCamera(Activity activity){
        PackageManager packageManager = activity.getPackageManager();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfos.size() > 0;
    }

    public static void onActivityResult(Activity activity, int requestCode, Intent data, PhotoResultListener photoResultListener){
        //resultCode在外部已经判断过了，就不传进来了
        switch (requestCode){
            case PHOTO_CHOICE_REQUEST_CODE:
                if(data == null){
                    ToastUtil.show("选择图片文件出错");
                    return;
                }
                Uri uri = data.getData();
                if(uri == null){
                    ToastUtil.show("选择图片文件出错");
                    return;
                }
                selectPhotoResult(activity, uri, photoResultListener);
                break;
            case PHOTO_TAKE_REQUEST_CODE:
                if(photoResultListener != null)
                    photoResultListener.photoResultSuccess(photoFile.getPath());
                break;
        }
    }

    private static void selectPhotoResult(Activity activity, Uri photoUri, PhotoResultListener photoResultListener){
        ContentResolver cr = activity.getContentResolver();
        String[] pojo = {MediaStore.Images.Media.DATA};
        Cursor cursor = cr.query(photoUri, pojo, null, null, null);
        String imagePath = null;
        if(cursor != null){
            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
            cursor.moveToFirst();
            imagePath = cursor.getString(columnIndex);
            cursor.close();
        }
        if(photoResultListener != null){
            if(imagePath != null && (imagePath.endsWith(".png") || imagePath.endsWith(".PNG") || imagePath.endsWith(".jpg") || imagePath.endsWith(".JPG"))){
                photoResultListener.photoResultSuccess(imagePath);
            }else{
                photoResultListener.photoResultFail("选择图片文件出错");
            }
        }
    }

    /**
     * 根据图片路径把图片转为位图
     * @param imagePath 图片路径
     * @param scaleWidth 压缩后的图片宽度
     * @return 位图
     */
    public static Bitmap file2Bitmap(String imagePath, int scaleWidth){
        if (!StringUtil.isEmpty(imagePath)) {
            File b = new File(imagePath);
            //存在此文件并且文件长度大于0
            if (b.exists() && b.length() > 0) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                try {
                    ExifInterface exif = new ExifInterface(imagePath);
                    //获取原图片宽度
                    int picwidth = exif.getAttributeInt(
                            ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);
                    //计算压缩比？，或者叫做采样率
                    int be = picwidth / scaleWidth;
                    if (be <= 0) {
                        be = 1;
                    }
                    options.inSampleSize = be;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return BitmapFactory.decodeFile(imagePath, options);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static Bitmap file2Bitmap(String aImageName) {
        return file2Bitmap(aImageName, 300);
    }

    /**
     * 根据图片路径把图片转为位图
     * 从别人微信文章里拷过来的代码，觉得也是一种不错的转化方式
     * @param imagePath 图片路径
     * @param requestWidth 压缩后想得到的图片宽度
     * @param requestHeight 压缩后想得到的图片高度
     * @return 位图
     */
    public static Bitmap file2Bitmap(String imagePath, int requestWidth, int requestHeight){
        if(!StringUtil.isEmpty(imagePath)){
            if(requestWidth <= 0 || requestHeight <= 0){
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                return bitmap;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;//不加载图片到内存，仅获得图片宽高
            BitmapFactory.decodeFile(imagePath, options);
            if(options.outHeight == -1 || options.outWidth == -1){
                try {
                    ExifInterface exifInterface = new ExifInterface(imagePath);
                    //获取图片的高度
                    int height = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_LENGTH, ExifInterface.ORIENTATION_NORMAL);
                    //获取图片的宽度
                    int width = exifInterface.getAttributeInt(ExifInterface.TAG_IMAGE_WIDTH, ExifInterface.ORIENTATION_NORMAL);
                    options.outHeight = height;
                    options.outWidth = width;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            options.inSampleSize = calculateInSampleSize(options, requestWidth, requestHeight);
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(imagePath, options);
        }else{
            return null;
        }
    }

    /**
     * 仅接上面方法配合获得图片采样率，是个不错的方案就拷过来了
     * @param options
     * @param requestWidth
     * @param requestHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int requestWidth, int requestHeight){
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if(height > requestHeight || width > requestWidth){
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            //计算采样率
            while ((halfHeight / inSampleSize) > requestHeight
                    && (halfWidth / inSampleSize) > requestWidth){
                inSampleSize *= 2;
            }

            long totalPixels = width * height / inSampleSize;

            final long totalReqPixelsCap = requestWidth * requestHeight * 2;

            while (totalPixels > totalReqPixelsCap){
                inSampleSize *= 2;
                totalPixels /= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 获取拍照后，图片的旋转角度
     * @param path 图片的绝对路径
     * @return  图片的旋转角度
     */
    public static int getBitmapDegree(String path){
        int degree = 0;
        try {
            //从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            //获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照指定的角度进行旋转
     * @param bitmap 需要旋转的图片
     * @param degree 指定的旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bitmap, int degree){
        //根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        //将原始图片按照旋转矩阵进行旋转，并得到新的图片
        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if(bitmap != null && !bitmap.isRecycled()){
            bitmap.recycle();
        }
        return newBitmap;
    }

    public interface PhotoResultListener{
        void photoResultSuccess(String path);
        void photoResultFail(String errMsg);
    }
}
