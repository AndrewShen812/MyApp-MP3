package com.na517.lf.model;

import com.na517.lf.net.StringRequest;

import java.io.Serializable;

/**
 * 项目名称：LianfengApp
 * 类描述：BDMusicData
 * 创建人：lianfeng
 * 创建时间：2015/6/3 18:24
 * 修改人：lianfeng
 * 修改时间：2015/6/3 18:24
 * 修改备注：
 * 版本：V1.0
 */
public class BDMusicData implements Serializable {
    private String encode;

    private String decode;

    private int type;

    private int lrcid;

    private int flag;

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getDecode() {
        return decode;
    }

    public void setDecode(String decode) {
        this.decode = decode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getLrcid() {
        return lrcid;
    }

    public void setLrcid(int lrcid) {
        this.lrcid = lrcid;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
