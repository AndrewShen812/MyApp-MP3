package com.na517.lf.utils.download;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * 项目名称：LianfengApp
 * 类描述：DownloadUtils
 * 创建人：lianfeng
 * 创建时间：2015/6/10 16:46
 * 修改人：lianfeng
 * 修改时间：2015/6/10 16:46
 * 修改备注：
 * 版本：V1.0
 */
public class DownloadUtils {

    private Context mContext;

    private String mUrl;

    public static final String CACHE_ROOT = "/MyMP3_test/";

    private boolean mFileExist;

    public DownloadUtils(Context context, String url) {
        mContext = context;
        mUrl = url;
        mFileExist = true;
    }

    public File getFile(String fileName) throws IOException {
        File savefile = null;
        // 判断是否有SDCard，并具有读写的权限
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            savefile = new File(Environment.getExternalStorageDirectory(),  "/" + CACHE_ROOT + fileName);
            if (!savefile.getParentFile().exists()) {
                savefile.getParentFile().mkdirs();
            }
            if (!savefile.exists()) {
                savefile.createNewFile();
                mFileExist = false;
            }
        }
        else {
            savefile = new File(CACHE_ROOT, fileName);
            if (!savefile.getParentFile().exists()) {
                savefile.getParentFile().mkdirs();
            }
            if (!savefile.exists()) {
                savefile.createNewFile();
                mFileExist = false;
            }
        }

        return savefile;
    }

    public boolean ismFileExist() {
        return mFileExist;
    }
}
