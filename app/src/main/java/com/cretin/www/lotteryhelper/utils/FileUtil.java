/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.cretin.www.lotteryhelper.utils;

import android.content.Context;

import java.io.File;

import static android.os.Environment.DIRECTORY_PICTURES;

public class FileUtil {
    public static File getSaveFile(Context context) {
        File file = new File(context.getExternalFilesDir(DIRECTORY_PICTURES), "pic.jpg");
        return file;
    }
}
