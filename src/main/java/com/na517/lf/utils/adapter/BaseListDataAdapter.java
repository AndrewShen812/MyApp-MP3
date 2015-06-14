package com.na517.lf.utils.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 项目名称：LianfengApp
 * 类描述：BaseListDataAdapter
 * 创建人：lianfeng
 * 创建时间：2015/6/5 8:46
 * 修改人：lianfeng
 * 修改时间：2015/6/5 8:46
 * 修改备注：
 * 版本：V1.0
 */
public class BaseListDataAdapter<T> extends BaseAdapter {

    List<T> mList;

    public BaseListDataAdapter(List<T> listData) {
        mList = listData;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
