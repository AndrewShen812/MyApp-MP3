package com.na517.lf.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.na517.lf.mp3player.R;

/**
 * 项目名称：LianfengApp
 * 类描述：SearchView
 * 创建人：lianfeng
 * 创建时间：2015/6/11 10:03
 * 修改人：lianfeng
 * 修改时间：2015/6/11 10:03
 * 修改备注：
 * 版本：V1.0
 */
public class SearchView extends RelativeLayout implements View.OnClickListener{

    private OnStartSearchListener mOnStartSearchListener;

    private EditText mEtContent;

    private ImageView mIvDelete;

    private ImageView mIvSearch;

    private Context mContext;

    public SearchView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_search_view, this);
        mEtContent = (EditText) view.findViewById(R.id.et_search_view_content);
        mIvDelete = (ImageView) view.findViewById(R.id.iv_search_view_clear);
        mIvSearch = (ImageView) view.findViewById(R.id.iv_search_view_search);

        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mIvDelete.setVisibility(View.VISIBLE);
                }
                else {
                    mIvDelete.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mIvDelete.setOnClickListener(this);
        mIvSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search_view_clear: // 清除内容
                mEtContent.setText("");
                break;
            case R.id.iv_search_view_search: // 搜索
                if (null != mOnStartSearchListener) {
                    mOnStartSearchListener.OnStartSearch(mEtContent.getText().toString().trim());
                }
                break;
            default:
                break;
        }
    }

    /**
     * 搜索接口
     */
    public interface OnStartSearchListener {
        void OnStartSearch(String content);
    }

    /**
     * 设置搜索监听事件
     * @param onStartSearchListener
     */
    public void setOnStartSearchListener(OnStartSearchListener onStartSearchListener) {
        mOnStartSearchListener = onStartSearchListener;
    }
}
