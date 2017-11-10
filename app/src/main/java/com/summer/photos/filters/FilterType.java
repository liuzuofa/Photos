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
     * 素描效果---铅笔画
     */
    public static final int FILTER4SKETCH_PENCIL = 1322;

    public static final int FILTER4COLORSKETCH = 1324;

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
}
