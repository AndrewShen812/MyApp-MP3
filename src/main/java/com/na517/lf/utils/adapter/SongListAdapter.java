package com.na517.lf.utils.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.na517.lf.model.BDSong;
import com.na517.lf.mp3player.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 项目名称：LianfengApp
 * 类描述：SongListAdapter
 * 创建人：lianfeng
 * 创建时间：2015/6/5 8:48
 * 修改人：lianfeng
 * 修改时间：2015/6/5 8:48
 * 修改备注：
 * 版本：V1.0
 */
public class SongListAdapter extends BaseListDataAdapter<BDSong> {

    private Context mContext;

    public SongListAdapter(Context context, List<BDSong> songList) {
        super(songList);

        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder item = null;
        if (convertView == null) {
            item = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.song_list_item, null);
            item.mIvPic = (ImageView) convertView.findViewById(R.id.iv_song_item_pic);
            item.mTvSong = (TextView) convertView.findViewById(R.id.tv_song_item_song);
            item.mTvSinger = (TextView) convertView.findViewById(R.id.tv_song_item_singer);
            item.mTvAlbumn = (TextView) convertView.findViewById(R.id.tv_song_item_album);

            convertView.setTag(item);
        }
        else {
            item = (ViewHolder) convertView.getTag();
        }

        try {
            Picasso.with(mContext).load(mList.get(position).singerPicSmall).error(R.drawable.music_cache).into(item.mIvPic);
        } catch (Exception e) {
            Log.e("LF", "Picasso Exception:" + e.getMessage());
            item.mIvPic.setImageResource(R.drawable.music_cache);
        }
        item.mTvSong.setText(mList.get(position).song);
        item.mTvSinger.setText("歌手：" + mList.get(position).singer);
        item.mTvAlbumn.setText("专辑：" + mList.get(position).album);

        return convertView;
    }

    private class ViewHolder {
        ImageView mIvPic;
        TextView mTvSong;
        TextView mTvSinger;
        TextView mTvAlbumn;
    }
}
