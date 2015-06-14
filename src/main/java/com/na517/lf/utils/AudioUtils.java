package com.na517.lf.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.na517.lf.model.Audio;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：LianfengApp
 * 类描述：AudioUtils
 * 创建人：lianfeng
 * 创建时间：2015/6/11 15:03
 * 修改人：lianfeng
 * 修改时间：2015/6/11 15:03
 * 修改备注：
 * 版本：V1.0
 */
public class AudioUtils implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnPreparedListener {

    private Context mContext;

    private MediaPlayer mMediaPlayer;

    private ProgressBar mPbDownload;

    private TextView mTvDownload;

    private PlayLocalThread mLocalThread;

    private PlayOnlineThread mOnlineThread;

    public static final String[] AUDIO_KEYS = new String[]{
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.TITLE_KEY,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ARTIST_ID,
            MediaStore.Audio.Media.ARTIST_KEY,
            MediaStore.Audio.Media.COMPOSER,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.ALBUM_KEY,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.YEAR,
            MediaStore.Audio.Media.TRACK,
            MediaStore.Audio.Media.IS_RINGTONE,
            MediaStore.Audio.Media.IS_PODCAST,
            MediaStore.Audio.Media.IS_ALARM,
            MediaStore.Audio.Media.IS_MUSIC,
            MediaStore.Audio.Media.IS_NOTIFICATION,
            MediaStore.Audio.Media.MIME_TYPE,
            MediaStore.Audio.Media.DATA
    };

    public AudioUtils(Context mContext, ProgressBar mPbDownload, TextView mTvDownload) {
        this.mContext = mContext;
        this.mPbDownload = mPbDownload;
        this.mTvDownload = mTvDownload;

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnPreparedListener(this);
    }

    public AudioUtils(Context mContext) {
        this.mContext = mContext;

        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
        mMediaPlayer.setOnBufferingUpdateListener(this);
        mMediaPlayer.setOnPreparedListener(this);
    }

    /**
     * 获取所有音乐文件的信息
     * @param context
     * @return
     */
    public static List getAudioList(Context context) {
        List audioList = new ArrayList();

        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                AUDIO_KEYS,
                null,
                null,
                null);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Bundle bundle = new Bundle ();
            for (int i = 0; i < AUDIO_KEYS.length; i++) {
                final String key = AUDIO_KEYS[i];
                final int columnIndex = cursor.getColumnIndex(key);
                final int type = cursor.getType(columnIndex);
                switch (type) {
                    case Cursor.FIELD_TYPE_BLOB:
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        float floatValue = cursor.getFloat(columnIndex);
                        bundle.putFloat(key, floatValue);
                        break;
                    case Cursor.FIELD_TYPE_INTEGER:
                        int intValue = cursor.getInt(columnIndex);
                        bundle.putInt(key, intValue);
                        break;
                    case Cursor.FIELD_TYPE_NULL:
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        String strValue = cursor.getString(columnIndex);
                        bundle.putString(key, strValue);
                        break;
                }
            }
            Audio audio = new Audio (bundle);
            audioList.add(audio);
        }
        cursor.close();
        return audioList;
    }

    public void playLocalAudio(final String path) {
        Log.i("LF", "play path:" + path);
        mLocalThread = new PlayLocalThread(path);
        mLocalThread.start();
    }

    /**
     * 在线播放音乐
     * @param url
     */
    public void playAudioOnline(final String url) {
        Log.i("LF", "play url:" + url);
        mOnlineThread = new PlayOnlineThread(url);
        mOnlineThread.start();
    }

    /**
     * 缓冲更新
     * @param mp
     * @param percent
     */
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        int currentProgress = (mMediaPlayer.getCurrentPosition()*100) / mMediaPlayer.getDuration();
        Log.i("LF", currentProgress + "% played," + percent + "% buffered");
        if (null != mPbDownload && null != mTvDownload) {
            mPbDownload.setProgress(currentProgress);
            mTvDownload.setText(percent + "%");
        }
        if (null == mPbDownload) {
            Log.i("LF", "mPbDownload is null");
        }
        if (null == mTvDownload) {
            Log.i("LF", "mTvDownload is null");
        }
    }

    /**
     * 播放准备
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    public void pause() {

    }

    public void play() {

    }

    public void stop() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        if (mLocalThread != null && mLocalThread.isAlive()) {
            mLocalThread.interrupt();
            mLocalThread = null;
        }
        if (mOnlineThread != null && mOnlineThread.isAlive()) {
            mOnlineThread.interrupt();
            mOnlineThread = null;
        }
    }

    private class PlayOnlineThread extends Thread {
        private String mUrl;

        public PlayOnlineThread(String url) {
            mUrl = url;
        }

        @Override
        public void run() {
            try {
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(mUrl); // 设置数据源
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("LF", "Play online Exception:" + e.getMessage());
            }
        }
    }

    private class PlayLocalThread extends Thread {
        private String mUrl;

        public PlayLocalThread(String mUrl) {
            this.mUrl = mUrl;
        }

        @Override
        public void run() {
            try {
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.reset();
                mMediaPlayer.setDataSource(mUrl); // 设置数据源
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("LF", "Play local Exception:" + e.getMessage());
            }
        }
    }

}
