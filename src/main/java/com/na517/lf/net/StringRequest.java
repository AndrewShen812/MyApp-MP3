package com.na517.lf.net;

import android.os.AsyncTask;
import android.util.Log;

import com.na517.lf.utils.StringUtils;

import java.io.IOException;

/**
 * 项目名称：LianfengApp
 * 类描述：StringRequest
 * 创建人：lianfeng
 * 创建时间：2015/6/3 17:19
 * 修改人：lianfeng
 * 修改时间：2015/6/3 17:19
 * 修改备注：
 * 版本：V1.0
 */
public class StringRequest {

    private static Task mTask;

    private static ResponseCallback mCallback;

    public static void start(String url, ResponseCallback callBack) {
        mTask = new Task();
        mCallback = callBack;
        mTask.execute(url);
    }


    private static class Task extends AsyncTask<String, Object, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            Log.i("LF", "url=" + url);
            String result = "";
            try {
                result = Http.Get(url);
//                result = Http.Post(url);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("LF", "Exception:" + e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (StringUtils.isEmpty(s)) {
                mCallback.onError("请求数据失败");
            }
            else {
                mCallback.onSuccess(s);
            }
        }
    }
}
