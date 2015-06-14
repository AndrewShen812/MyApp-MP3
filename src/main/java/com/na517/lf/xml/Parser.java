package com.na517.lf.xml;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

/**
 * 项目名称：LianfengApp
 * 类描述：Parser
 * 创建人：lianfeng
 * 创建时间：2015/6/3 18:19
 * 修改人：lianfeng
 * 修改时间：2015/6/3 18:19
 * 修改备注：
 * 版本：V1.0
 */
public interface Parser<T> {

    List<T> parse(String xmlData) throws ParserConfigurationException, IOException, SAXException;

    String serialize(List<T> list);
}
