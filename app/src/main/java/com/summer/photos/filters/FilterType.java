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
package com.summer.photos.filters;

/**
 * @author jarlen
 */
public class FilterType {

    /**
     * 灰效果
     */
    public static final int FILTER_GRAY = 1314;
    private float[] gray = {
            0.33F, 0.59F, 0.11F, 0.00F, 0.00F,
            0.33F, 0.59F, 0.11F, 0.00F, 0.00F,
            0.33F, 0.59F, 0.11F, 0.00F, 0.00F,
            0.00F, 0.00F, 0.00F, 1.00F, 0.00F
    };

    /**
     * 怀旧效果
     */
    public static final int FILTER_NOSTALGIC = 1316;
    private float[] nostalgic = {
            0.394F, 0.7690F, 0.189F, 0F, 0F,
            0.349F, 0.6856F, 0.168F, 0F, 0F,
            0.272F, 0.5340F, 0.131F, 0F, 0F,
            0.000F, 0.0000F, 0.000F, 1F, 0F
    };

    /**
     * 反色(底片)效果
     */
    public static final int FILTER_NEGATIVE = 1320;
    private float[] negative = {
            1.5F, 1.5F, 1.5F, 0F, -1F,
            1.5F, 1.5F, 1.5F, 0F, -1F,
            1.5F, 1.5F, 1.5F, 0F, -1F,
            1.5F, 1.5F, 1.5F, 1F, 0F
    };

    /**
     * 高饱和效果
     */
    public static final int FILTER4WHITELOG = 1325;

    public static final int FILTER_SATURATION = 1327;
    private float[] saturation = {
            1.438F, -0.122F, -0.016F, 0F, -0.03F,
            -0.062F, 1.378F, -0.016F, 0F, 0.05F,
            -0.062F, -0.122F, 1.483F, 0F, -0.02F,
            0F, 0F, 0F, 1F, 0F
    };


    /**
     * 柔化
     */

    public static final int FILTER4SOFTNESS = 1328;

    /**
     * 霓虹
     */

    public static final int FILTER4NiHong = 1329;

    /**
     * 素描
     */
    public static final int FILTER4SKETCH = 1330;

    /**
     * 浮雕
     */

    public static final int FILTER_RELIEF = 1332;
    private float[] relief = {
            1F, 0F, 0F, 0F, 0F,
            0F, 1F, 0F, 0F, 0F,
            0F, 0F, 1F, 0F, 0F,
            0F, 0F, 0F, 1F, 0F
    };

    /**
     * 锐化
     */

    public static final int FILTER4RUIHUA = 1333;

    public float[] getFilterFloat(int type) {
        switch (type) {
            case FILTER_GRAY:
                return gray;
            case FILTER_NOSTALGIC:
                return nostalgic;
            case FILTER_NEGATIVE:
                return negative;
            case FILTER_SATURATION:
                return saturation;
            case FILTER_RELIEF:
                return relief;
        }
        return null;
    }

    private static int[] disColor(int[] pixels) {
        for (int i = 0; i < pixels.length; i += 4) {
            double grayScale = pixels[i] * 0.299 + pixels[i + 1] * 0.587 + pixels[i + 2] * 0.114;
            pixels[i] = pixels[i + 1] = pixels[i + 1] = (int) grayScale;
        }
        return pixels;
    }

    private static int[] invert(int[] pixels) {
        for (int i = 0; i < pixels.length; i += 4) {
            pixels[i] = 255 - pixels[i];//R
            pixels[i + 1] = 255 - pixels[i + 1];//G
            pixels[i + 2] = 255 - pixels[i + 2];//B
        }
        return pixels;
    }

    private static int[] dodgeColor(int[] basePixel, int[] mixPixel) {
        for (int i = 0; i < basePixel.length; i += 4) {
            basePixel[i] = basePixel[i] + (basePixel[i] * mixPixel[i]) / (255 - mixPixel[i]);
            basePixel[i + 1] = basePixel[i + 1] + (basePixel[i + 1] * mixPixel[i + 1]) / (255 - mixPixel[i + 1]);
            basePixel[i + 2] = basePixel[i + 2] + (basePixel[i + 2] * mixPixel[i + 2]) / (255 - mixPixel[i + 2]);
        }
        return basePixel;
    }

    //高斯模糊
    private static int[] gaussBlur(int[] data, int width, int height, int radius,
                                   float sigma) {

        float pa = (float) (1 / (Math.sqrt(2 * Math.PI) * sigma));
        float pb = -1.0f / (2 * sigma * sigma);

        // generate the Gauss Matrix
        float[] gaussMatrix = new float[radius * 2 + 1];
        float gaussSum = 0f;
        for (int i = 0, x = -radius; x <= radius; ++x, ++i) {
            float g = (float) (pa * Math.exp(pb * x * x));
            gaussMatrix[i] = g;
            gaussSum += g;
        }

        for (int i = 0, length = gaussMatrix.length; i < length; ++i) {
            gaussMatrix[i] /= gaussSum;
        }

        // x direction
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                float r = 0, g = 0, b = 0;
                gaussSum = 0;
                for (int j = -radius; j <= radius; ++j) {
                    int k = x + j;
                    if (k >= 0 && k < width) {
                        int index = y * width + k;
                        int color = data[index];
                        int cr = (color & 0x00ff0000) >> 16;
                        int cg = (color & 0x0000ff00) >> 8;
                        int cb = (color & 0x000000ff);

                        r += cr * gaussMatrix[j + radius];
                        g += cg * gaussMatrix[j + radius];
                        b += cb * gaussMatrix[j + radius];

                        gaussSum += gaussMatrix[j + radius];
                    }
                }

                int index = y * width + x;
                int cr = (int) (r / gaussSum);
                int cg = (int) (g / gaussSum);
                int cb = (int) (b / gaussSum);

                data[index] = cr << 16 | cg << 8 | cb | 0xff000000;
            }
        }
        // y direction
        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                float r = 0, g = 0, b = 0;
                gaussSum = 0;
                for (int j = -radius; j <= radius; ++j) {
                    int k = y + j;
                    if (k >= 0 && k < height) {
                        int index = k * width + x;
                        int color = data[index];
                        int cr = (color & 0x00ff0000) >> 16;
                        int cg = (color & 0x0000ff00) >> 8;
                        int cb = (color & 0x000000ff);

                        r += cr * gaussMatrix[j + radius];
                        g += cg * gaussMatrix[j + radius];
                        b += cb * gaussMatrix[j + radius];

                        gaussSum += gaussMatrix[j + radius];
                    }
                }

                int index = y * width + x;
                int cr = (int) (r / gaussSum);
                int cg = (int) (g / gaussSum);
                int cb = (int) (b / gaussSum);
                data[index] = cr << 16 | cg << 8 | cb | 0xff000000;
            }
        }

        return data;
    }

}
