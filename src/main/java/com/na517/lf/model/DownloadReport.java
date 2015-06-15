package com.na517.lf.model;

/**
 * 项目名称：LianfengApp
 * 类描述：DownloadReport 下载结果报告对象
 * 创建人：lianfeng
 * 创建时间：2015/6/11 8:25
 * 修改人：lianfeng
 * 修改时间：2015/6/11 8:25
 * 修改备注：
 * 版本：V1.0
 */
public class DownloadReport {

    public static final int CODE_ERR = 0;

    public static final int CODE_SUCCESS = 1;

    public static final int CODE_EXIST = 2;

    public int code;

    public String error;
}
