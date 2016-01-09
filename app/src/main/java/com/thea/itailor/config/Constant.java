package com.thea.itailor.config;

import android.os.Environment;


/**
 * Created by hunter on 2015/6/19.
 */
public class Constant {
    public static final String PATH = Environment.getExternalStorageDirectory() + "/iTailor";
    public static final String DIRECTORY_ARMOIRE = "/armoire";
    public static final String DIRECTORY_USER = "/user";

    public static final String APP_KEY = "BWDBjksxBvesb5wIVTOz9Te3";

    public static final String CUR_PORTRAIT = "cur_portrait.jpg";
    public static final String DEFAULT_PORTRAIT = "default_portrait.png";

    public static final String APP_ID = "222222";

    public static final String EXTRA_FIRST_PAGE = "is_first";

    public static final int REQUEST_CAMERA = 45;
    public static final int REQUEST_SCAN_ACTIVITY = 46;
    public static final int REQUEST_UPDATE_CLOTH = 47;
}
