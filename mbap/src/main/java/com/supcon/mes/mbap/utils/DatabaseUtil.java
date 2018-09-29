package com.supcon.mes.mbap.utils;

import android.content.Context;

import java.io.File;

/**
 * Environment: hongruijun
 * Created by Xushiyun on 2018/4/18.
 * Desc: 删除app对应包中的数据库数据
 */

public class DatabaseUtil {
    /**
     * *清除所有数据库(/data/data/包名/databases)
     */
    public void clearDatabases(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/databases"));
    }

    /**
     * 按名字清除数据库
     */
    public void clearDatabaseBydbName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 只删除文件夹下的文件
     *
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

}
