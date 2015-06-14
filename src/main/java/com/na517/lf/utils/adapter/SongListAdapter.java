package com.na517.lf.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.na517.lf.model.BDSong;
import com.na517.lf.mp3player.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SongListAdapter extends BaseListDataAdapter<BDSong> {

    private Context mContext;

    public SongListAdapter(Context context, List<BDSong> list) {
        super(list);

        mContext = context;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder item = null;
        if (null == view) {
            item = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.song_list_item, null);
            item.mTvSong = (TextView) view.findViewById(R.id.tv_main_list_item_song);
            item.mTvSinger = (TextView) view.findViewById(R.id.tv_main_list_item_singer);
            item.mTvAlbumn = (TextView) view.findViewById(R.id.tv_main_list_item_albumn);
            item.mIvPic = (ImageView) view.findViewById(R.id.iv_main_list_iten_pic);
            view.setTag(item);
        }
        else {
            item = (ViewHolder) view.getTag();
        }

        item.mTvSong.setText(mListData.get(i).song);
        item.mTvSinger.setText("歌手：" + mListData.get(i).singer);
        item.mTvAlbumn.setText("专辑：" + mListData.get(i).album);
        try {
            Picasso.with(mContext).load(mListData.get(i).singerPicSmall).into(item.mIvPic);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private class ViewHolder {
        TextView mTvSong;
        TextView mTvSinger;
        TextView mTvAlbumn;
        ImageView mIvPic;
    }
}
