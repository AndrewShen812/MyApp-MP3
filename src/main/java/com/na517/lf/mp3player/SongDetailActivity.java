package com.na517.lf.mp3player;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.na517.lf.model.Audio;
import com.na517.lf.model.BDSongDetail;
import com.na517.lf.model.DownloadReport;
import com.na517.lf.utils.AudioUtils;
import com.na517.lf.utils.download.DownloadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class SongDetailActivity extends Activity implements View.OnClickListener {

    private ImageView mIvPic;

    private TextView mTvSong;

    private TextView mTvSinger;

    private TextView mTvAlbum;

    private TextView mTvTime;

    private TextView mTvFormat;

    private TextView mTvSize;

    private Button mBtnTry;

    private Button mBtnDownload;

    private BDSongDetail mSong;

    private Context mContext;

    private ProgressBar mPbDownload;

    private TextView mTvDownload;

    private MediaPlayer mMediaPlayer;

    private AudioUtils mAudioUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);

        mContext = this;

        initView();
        initData();
        
        Log.e("LF", "add from co");
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (null != mAudioUtils) {
            mAudioUtils.stop();
        }
        mAudioUtils.stop();
    }

    private void initView() {
        mIvPic = (ImageView) findViewById(R.id.iv_song_detail);
        mTvSong = (TextView) findViewById(R.id.tv_song_detail_name);
        mTvSinger = (TextView) findViewById(R.id.tv_song_detail_singer);
        mTvAlbum = (TextView) findViewById(R.id.tv_song_detail_album);
        mTvTime = (TextView) findViewById(R.id.tv_song_detail_time);
        mTvFormat = (TextView) findViewById(R.id.tv_song_detail_format);
        mTvSize = (TextView) findViewById(R.id.tv_song_detail_size);
        mBtnTry = (Button) findViewById(R.id.btn_song_detail_try);
        mBtnDownload = (Button) findViewById(R.id.btn_song_detail_download);
        mPbDownload = (ProgressBar) findViewById(R.id.pb_song_detail_download_progress);
        mTvDownload = (TextView) findViewById(R.id.tv_song_detail_download_progress);

        mBtnTry.setOnClickListener(this);
        mBtnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            /**
             * 试听
             */
            case R.id.btn_song_detail_try:
                mTvDownload.setVisibility(View.VISIBLE);
                ArrayList<Audio> audios = (ArrayList<Audio>) AudioUtils.getAudioList(mContext);
//                Log.i("LF", "audio count=" + audios.size());
                if ("试听".equals(mBtnTry.getText().toString())) {
                    mAudioUtils = new AudioUtils(mContext, mPbDownload, mTvDownload);
//                    mAudioUtils.playLocal(audios.get(audios.size()-1).getPath());
                    mAudioUtils.playOnline(mSong.songLink);
                    mBtnTry.setText("暂停");
                }
                else if ("暂停".equals(mBtnTry.getText().toString())) {
                    mAudioUtils.pause();
                    mBtnTry.setText("继续试听");
                }
                else if ("继续试听".equals(mBtnTry.getText().toString())) {
                    mAudioUtils.play();
                    mBtnTry.setText("暂停");
                }
                break;
            /**
             * 下载
             */
            case R.id.btn_song_detail_download:
                mTvDownload.setVisibility(View.VISIBLE);
                mBtnDownload.setEnabled(false);
                String url = mSong.songLink;
                Log.i("LF", "download url=" + url);
                getDownloadSize(url);
                DownloadTask task = new DownloadTask(mContext, url, mPbDownload, mTvDownload, mSong.songName + "." + mSong.format);
                task.setOnDownloadStatusChangedListener(new DownloadTask.OnDownloadStatusChangedListener() {
                    @Override
                    public void onDownloadStatusChanged(DownloadReport report) {
                        Log.e("LF", "call onDownloadStatusChanged.\n" + report.error);
                        Toast.makeText(mContext, "下载失败：" + report.error, Toast.LENGTH_SHORT).show();
                        mBtnDownload.setEnabled(true);
                    }

                    @Override
                    public void onDownloadSuccess() {
                        Log.i("LF", "call onDownloadSuccess");
                        Toast.makeText(mContext, "下载成功！", Toast.LENGTH_SHORT).show();
                        mBtnDownload.setEnabled(true);
                    }
                });
                task.execute();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化页面显示数据
     */
    private void initData() {
        mSong = (BDSongDetail) getIntent().getSerializableExtra("SongDetail");
        if (null != mSong) {
            try {
                Picasso.with(mContext).load(mSong.songPicRadio).into(mIvPic);
            } catch (Exception e) {
                Log.e("LF", "Picasso Exception:" + e.getMessage());
            }
            mTvSong.setText(mSong.songName);
            mTvSinger.setText("歌手：" + mSong.artistName);
            mTvAlbum.setText("专辑：" + mSong.albumName);
            mTvTime.setText("时长：" + getFormatTime(mSong.time));
            mTvFormat.setText("格式：" + mSong.format);
            mTvSize.setText("大小：" + getFormatSize(mSong.size));

        }
        else {
            Toast.makeText(mContext, "歌曲详细信息为空", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    /**
     * 格式化播放时间
     * @param totalSec 总时间秒数
     * @return 格式：hh:MM:ss 或 MM:ss
     */
    private String getFormatTime(int totalSec) {
        int hour = totalSec / 3600;
        int min = (totalSec % 3600) / 60;
        int sec = (totalSec % 3600) % 60;
        String time = null;
        if (hour > 0) {
            time = String.format("%02d:%02d:%02d", hour, min, sec);
        }
        else {
            time = String.format("%02d:%02d", min, sec);
        }

        return time;
    }

    /**
     * 格式化显示的文件大小
     * @param byteSize 总字节数
     * @return
     */
    private String getFormatSize(long byteSize) {
        String size = "";
        if(byteSize < 1024) { // 小于1KB
            size = byteSize + "B";
        }
        else if (byteSize < 1024 * 1024) { // 小于1MB
            size = getDouble(byteSize, 1024) + "KB";
        }
        else if (byteSize < 1024 * 1024 * 1024) {// 小于1GB
            size = getDouble(byteSize, (1024 * 1024)) + "MB";
        }

        return size;
    }

    /**
     * 保留小数点后2位
     * @param byteSize
     * @param unit
     * @return
     */
    private double getDouble(long byteSize, long unit) {
        BigDecimal bigSize = new BigDecimal(byteSize + "");
        BigDecimal bigUnit = new BigDecimal(unit + "");

        return bigSize.divide(bigUnit, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取下载内容的大小
     * @param strUrl
     */
    private void getDownloadSize(final String strUrl) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(strUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setReadTimeout(1000 * 5);
                    connection.setConnectTimeout(5 * 1000);
                    Log.i("LF", "download size=" + getFormatSize(connection.getContentLength()));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e("LF", "MalformedURLException:" + e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("LF", "IOException:" + e.getMessage());
                }
            }
        });
        thread.start();
    }
}
