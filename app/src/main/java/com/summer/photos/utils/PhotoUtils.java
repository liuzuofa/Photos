/*
 *          Copyright (C) 2016 jarlen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.summer.photos.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 图片基本操作
 *
 * @author jarlen
 */

public class PhotoUtils {

    public static String SAVE_PATH = Environment.getExternalStorageDirectory() + "/DCIM/Photos/";

    /**
     * @param bitmap 要保存的bitmap
     * @return name 以“.jpg”格式保存至相册的路径
     */
    public static String saveBitmap(Bitmap bitmap) {

        File photosFile = new File(Environment.getExternalStorageDirectory(),"/DCIM/Photos");
        File file = null;
        if (!photosFile.exists()) {
            photosFile.mkdirs();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String name = "IMG_" + formatter.format(date) + ".jpg";
        file = new File(SAVE_PATH, name);
        if (file.exists()) {
            file.delete();
        }
        String path = file.getAbsolutePath();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("path = " +path);
            return path;
        }
    }

    /**
     * 图片旋转
     *
     * @param bit     旋转原图像
     * @param degrees 旋转度数
     * @return 旋转之后的图像
     */
    public static Bitmap rotateImage(Bitmap bit, int degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        Bitmap tempBitmap = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(),
                bit.getHeight(), matrix, true);
        return tempBitmap;
    }

    /**
     * 翻转图像
     *
     * @param bit 翻转原图像
     * @param x   翻转X轴
     * @param y   翻转Y轴
     * @return 翻转之后的图像
     * <p>
     * 说明:
     * (1,-1)上下翻转
     * (-1,1)左右翻转
     */
    public static Bitmap reverseImage(Bitmap bit, int x, int y) {
        Matrix matrix = new Matrix();
        matrix.postScale(x, y);

        Bitmap tempBitmap = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(),
                bit.getHeight(), matrix, true);
        return tempBitmap;
    }

    /**
     * 图片按照给定的宽高等比缩放
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap bitmap, int width, int height) {
        float scale = 0f;
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();
        scale = bitmapHeight > bitmapWidth
                ? height / (bitmapHeight * 1f)
                : width / (bitmapWidth * 1f);
        Bitmap resizeBmp;
        if (scale != 0) {
            int bitmapheight = bitmap.getHeight();
            int bitmapwidth = bitmap.getWidth();
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale); // 长和宽放大缩小的比例
            resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bitmapwidth,
                    bitmapheight, matrix, true);
        } else {
            resizeBmp = bitmap;
        }
        return resizeBmp;
    }
}
