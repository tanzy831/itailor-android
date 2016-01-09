package com.thea.itailor.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * Created by Thea on 2015/9/9 0009.
 */
public class LoaderImpl {
    //内存中的软应用缓存
    private final Map<String, SoftReference<Bitmap>> imageCache;

    //是否缓存图片至本地文件
    private boolean cache2FileFlag = false;

    //缓存目录,默认是/data/data/package/cache/目录
    private String cachedDir;

    public LoaderImpl(Map<String, SoftReference<Bitmap>> imageCache){
        this.imageCache = imageCache;
    }

    /**
     * 是否缓存图片至外部文件
     * @param flag
     */
    public void setCache2File(boolean flag){
        cache2FileFlag = flag;
    }

    /**
     * 设置缓存图片到外部文件的路径
     * @param cacheDir
     */
    public void setCachedDir(String cacheDir){
        this.cachedDir = cacheDir;
    }

    /**
     * 从网络端下载图片
     * @param url 网络图片的URL地址
     * @param cache2Memory 是否缓存(缓存在内存中)
     * @return bitmap 图片bitmap结构
     *
     */
    public Bitmap getBitmapFromUrl(String url, String authenticate, boolean cache2Memory){
        Bitmap bitmap;
        try{
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)u.openConnection();
            conn.setDoInput(true);
            conn.setUseCaches(true);

            conn.setRequestProperty("authenticate", authenticate);
            conn.setRequestMethod("GET");
            conn.connect();

            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);

            if(cache2Memory){
                //1.缓存bitmap至内存软引用中
                imageCache.put(url, new SoftReference<>(bitmap));
                if(cache2FileFlag){
                    //2.缓存bitmap至/data/data/packageName/cache/文件夹中
                    String fileName = getMD5Str(url);
                    String filePath = this.cachedDir + "/" +fileName;
                    FileOutputStream fos = new FileOutputStream(filePath);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                }
            }

            is.close();
            conn.disconnect();
            return bitmap;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从内存缓存中获取bitmap
     * @param url
     * @return bitmap or null.
     */
    public Bitmap getBitmapFromMemory(String url){
        Bitmap bitmap = null;
        if(imageCache.containsKey(url)){
            synchronized(imageCache){
                SoftReference<Bitmap> bitmapRef = imageCache.get(url);
                if(bitmapRef != null){
                    bitmap = bitmapRef.get();
                    return bitmap;
                }
            }
        }
        //从外部缓存文件读取
        if(cache2FileFlag){
            bitmap = getBitmapFromFile(url);
            if(bitmap != null)
                imageCache.put(url, new SoftReference<>(bitmap));
        }

        return bitmap;
    }

    /**
     * 从外部文件缓存中获取bitmap
     * @param url
     * @return
     */
    private Bitmap getBitmapFromFile(String url){
        Bitmap bitmap;
        String fileName = getMD5Str(url);
        if(fileName == null)
            return null;

        String filePath = cachedDir + "/" + fileName;

        try {
            FileInputStream fis = new FileInputStream(filePath);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            bitmap = null;
        }
        return bitmap;
    }


    /**
     * MD5 加密
     */
    private static String getMD5Str(String str) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            System.out.println("NoSuchAlgorithmException caught!");
            return null;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        byte[] byteArray = messageDigest.digest();
        StringBuilder md5StrBuff = new StringBuilder();
        for (byte aByteArray : byteArray) {
            if (Integer.toHexString(0xFF & aByteArray).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & aByteArray));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & aByteArray));
        }

        return md5StrBuff.toString();
    }
}