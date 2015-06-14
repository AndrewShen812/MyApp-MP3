package com.na517.lf.model;

import com.na517.lf.net.StringRequest;

/**
 * 项目名称：LianfengApp
 * 类描述：BDSong 百度新音乐接口歌曲信息
 * 创建人：lianfeng
 * 创建时间：2015/6/4 8:35
 * 修改人：lianfeng
 * 修改时间：2015/6/4 8:35
 * 修改备注：
 * 版本：V1.0
 */
public class BDSong {

    /** 歌曲名 */
    public String song;

    /** 歌曲ID */
    public String song_id;

    /** 歌手名 */
    public String singer;

    /** 唱片名 */
    public String album;

    /** 歌手小图片链接 */
    public String singerPicSmall;

    /** 歌手大图片链接 */
    public String singerPicLarge;

    /** 唱片小图片链接 */
    public String albumPicLarge;

    /** 唱片大图片链接 */
    public String albumPicSmall;
}
