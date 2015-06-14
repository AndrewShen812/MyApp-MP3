package com.na517.lf.utils.download;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.na517.lf.model.DownloadReport;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 项目名称：LianfengApp
 * 类描述：DownloadTask
 * 创建人：lianfeng
 * 创建时间：2015/6/10 16:15
 * 修改人：lianfeng
 * 修改时间：2015/6/10 16:15
 * 修改备注：
 * 版本：V1.0
 */
public class DownloadTask extends AsyncTask<Object, Integer, DownloadReport> {

    private Context mContext;

    private ProgressBar mPbDownload;

    private TextView mTvDownload;

    private String mUrl;

    private static int mSize;

    private File mFile;

    DownloadUtils downloadUtils;

    private String mFileName;

    private boolean mIsFinished;

    /**
     * 构造函数
     * @param context
     * @param url 下载链接
     * @param pb 进度条
     * @param tv 进度文本
     * @param fileName 存储文件名，含后缀（如：*.mp3）
     */
    public DownloadTask(Context context, String url, ProgressBar pb, TextView tv, String fileName) {
        mContext = context;
        mUrl = url;
        mPbDownload = pb;
        mTvDownload = tv;
        mFileName = fileName;
        mIsFinished = false;
        downloadUtils = new DownloadUtils(context,url);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected DownloadReport doInBackground(Object... params) {
        DownloadReport result = new DownloadReport();
        try {
            URL url = new URL(mUrl);
            HttpURLConnection httpUrlCon = (HttpURLConnection) url.openConnection();
            httpUrlCon.setAllowUserInteraction(true);
            httpUrlCon.setReadTimeout(5000);
            httpUrlCon.setRequestMethod("GET");
            httpUrlCon.setRequestProperty("Charset", "UTF-8");

            mSize = getDownloadSize(mUrl);
            mFile = downloadUtils.getFile(mFileName);
            if (downloadUtils.ismFileExist()) {
                result.code = DownloadReport.CODE_EXIST;
                result.error = "文件已存在";
                return result;
            }

            InputStream inputStream = httpUrlCon.getInputStream();
            RandomAccessFile accessFile = new RandomAccessFile(mFile, "rw");
            accessFile.seek(0);

            byte[] buffer = new byte[1024 * 100];
            int curSize = 0;
            int readSize = 0;
            while (!mIsFinished) {
                Thread.sleep(500);
                readSize = inputStream.read(buffer);
                if (readSize == -1) { // 读完数据
                    break;
                }
                curSize += readSize;
                accessFile.write(buffer, 0, readSize);
                publishProgress((curSize*100) / mSize);
                if (curSize >= mSize && mSize != -1) {
                    mIsFinished = true;
                    break;
                }
            }
            inputStream.close();
            accessFile.close();
            httpUrlCon.disconnect();

            result.code = DownloadReport.CODE_SUCCESS;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            result.code = DownloadReport.CODE_ERR;
            result.error = "MalformedURLException:" + e.getMessage();
        } catch (IOException e) {
            e.printStackTrace();
            result.code = DownloadReport.CODE_ERR;
            result.error = "IOException:" + e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
            result.code = DownloadReport.CODE_ERR;
            result.error = "InterruptedException:" + e.getMessage();
        }

        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        if (mPbDownload == null || mTvDownload == null) {
            return;
        }
        mPbDownload.setProgress(values[0]);
        mTvDownload.setText("已下载" + values[0] + "%");
        Log.i("LF", "下载进度：" + values[0] + "%");
    }

    @Override
    protected void onPostExecute(DownloadReport result) {
        if (DownloadReport.CODE_SUCCESS == result.code) {
            mListener.onDownloadSuccess();
        }
        else {
            mListener.onDownloadStatusChanged(result);
        }
    }

    /**
     * 获取将要下载内容的大小
     * @param strUrl
     * @return
     */
    private int getDownloadSize(final String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(1000 * 5);
            connection.setConnectTimeout(5 * 1000);
            return connection.getContentLength();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e("LF", "MalformedURLException:" + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("LF", "IOException:" + e.getMessage());
        }

        return 0;
    }

    public interface OnDownloadStatusChangedListener {
        void onDownloadStatusChanged(DownloadReport report);
        void onDownloadSuccess();
    }

    private OnDownloadStatusChangedListener mListener;

    public void setOnDownloadStatusChangedListener(OnDownloadStatusChangedListener listener) {
        mListener = listener;
    }
}
