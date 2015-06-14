package com.na517.lf.mp3player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.na517.lf.model.BDMusicData;
import com.na517.lf.model.BDSong;
import com.na517.lf.model.BDSongDetail;
import com.na517.lf.net.ResponseCallback;
import com.na517.lf.net.StringRequest;
import com.na517.lf.utils.StringUtils;
import com.na517.lf.utils.adapter.SongListAdapter;
import com.na517.lf.view.SearchView;
import com.na517.lf.xml.BDMusicXMLParser;
import com.na517.lf.xml.BDSongXMLParser;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;


public class MainActivity extends Activity implements SearchView.OnStartSearchListener, AdapterView.OnItemClickListener {

    private Context mContext;

    private SongListAdapter mAdapter;

    private ArrayList<BDSong> mSongList;

    private ListView mLvResult;

    private SearchView mSvSearch;

    private ProgressBar mPbLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        initView();
        
        Log.i("LF", "add from web.");
    }

    private void initView() {
        mLvResult = (ListView) findViewById(R.id.lv_main_result);
        mSvSearch = (SearchView) findViewById(R.id.sv_main_search);
        mPbLoad = (ProgressBar) findViewById(R.id.pb_main_search_progress);

        mSvSearch.setOnStartSearchListener(this);
        mLvResult.setOnItemClickListener(this);
    }

    private void searchSongs(String content) {
        String searchStr = content.replace(" ", "%20");

//        String urlFormat = "http://box.zhangmen.baidu.com/x?op=12&count=1&title=%s$$%s";
//        String url = String.format(urlFormat, singer, song);
        //新接口
//        String newURL = "http://mp3.baidu.com/dev/api/?tn=getinfo&ct=0&word=%s&ie=utf-8&format=xml"; //json或XML
        String newURL = "http://mp3.baidu.com/dev/api/?tn=getinfo&ct=0&word=%s&ie=utf-8&format=json"; //json或XML
        String url = String.format(newURL, searchStr);

        StringRequest.start(url, new ResponseCallback() {
            @Override
            public void onSuccess(String result) {
                mPbLoad.setVisibility(View.GONE);
                Log.i("LF", "call onSuccess.");
                Log.i("LF", "result=" + result);
                try {
                    mSongList = (ArrayList<BDSong>) JSON.parseArray(result, BDSong.class);
//                    BDSongXMLParser parser = new BDSongXMLParser();
//                    ArrayList<BDSong> songList = (ArrayList<BDSong>) parser.parse(result);
                    if (mSongList == null || mSongList.size() == 0) {
                        Toast.makeText(mContext, "搜索歌曲失败，请稍后重试", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (int i=0; i<mSongList.size(); i++) {
                        Log.i("LF", "songId:" + mSongList.get(i).song_id);
                        Log.i("LF", "singer:" + mSongList.get(i).singer);
                        Log.i("LF", "album:" + mSongList.get(i).album);
                        Log.i("LF", "singerPicSmall:" + mSongList.get(i).singerPicSmall);
                        Log.i("LF", "singerPicLarge:" + mSongList.get(i).singerPicLarge);
                        Log.i("LF", "albumPicSmall:" + mSongList.get(i).albumPicSmall);
                        Log.i("LF", "albumPicLarge:" + mSongList.get(i).albumPicLarge);
                    }
                    mAdapter = new SongListAdapter(mContext, mSongList);
                    mLvResult.setAdapter(mAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("LF", "Exception:" + e.getMessage());
                }
            }

            @Override
            public void onError(String result) {
                mPbLoad.setVisibility(View.GONE);
            }

            @Override
            public void onStartLoading() {
                mPbLoad.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        BDSong song = mSongList.get(position);

        String urlFormat = "http://ting.baidu.com/data/music/links?songIds=%s";
        String url = String.format(urlFormat, song.song_id);

        StringRequest.start(url, new ResponseCallback() {
            @Override
            public void onSuccess(String result) {
                mPbLoad.setVisibility(View.GONE);
                Log.i("LF", "call onSuccess.");
                Log.i("LF", "result=" + result);

                try {
                    JSONObject jsonObject = JSON.parseObject(result);
                    JSONObject jsonData = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = jsonData.getJSONArray("songList");
                    if (jsonArray.size() > 0) {
                        BDSongDetail songDetail = JSON.parseObject(jsonArray.get(0).toString(), BDSongDetail.class);
                        songDetail.xcode = jsonData.getString("xcode");
                        Intent i = new Intent(mContext, SongDetailActivity.class);
                        i.putExtra("SongDetail", songDetail);
                        startActivity(i);
                    }
                    else {
                        Toast.makeText(mContext, "没有获取到该歌曲信息", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("LF", "Exception:" + e.getMessage());
                    Toast.makeText(mContext, "没有获取到该歌曲信息", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String result) {
                mPbLoad.setVisibility(View.GONE);
            }

            @Override
            public void onStartLoading() {
                mPbLoad.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void OnStartSearch(String content) {
        if (!content.isEmpty()) {
            Toast.makeText(mContext, "开始搜索：" + content, Toast.LENGTH_SHORT).show();
            searchSongs(content);
        }
        else {
            Toast.makeText(mContext, "请输入搜索内容", Toast.LENGTH_SHORT).show();
        }
    }
}
