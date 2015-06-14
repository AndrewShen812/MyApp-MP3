package com.na517.lf.xml;

import com.na517.lf.model.BDMusicData;
import com.na517.lf.model.BDSong;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 项目名称：LianfengApp
 * 类描述：BDSongXMLParser
 * 创建人：lianfeng
 * 创建时间：2015/6/4 9:28
 * 修改人：lianfeng
 * 修改时间：2015/6/4 9:28
 * 修改备注：
 * 版本：V1.0
 */
public class BDSongXMLParser implements Parser<BDSong> {
    @Override
    public List<BDSong> parse(String xmlData) throws ParserConfigurationException, IOException, SAXException {
        InputStream inputStream = new ByteArrayInputStream(xmlData.getBytes());

        List<BDSong> songData = new ArrayList<BDSong>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputStream);
        Element rootElement = doc.getDocumentElement();
        NodeList urlItems = rootElement.getElementsByTagName("res");
        getNodeList(urlItems, songData);

        return songData;
    }

    @Override
    public String serialize(List<BDSong> list) {
        return null;
    }

    private void getNodeList(NodeList items, List<BDSong> listData) {
        try {
            for (int i = 0; i < items.getLength(); i++) {
                BDSong song = new BDSong();
                Node item = items.item(i);
                NodeList proList = item.getChildNodes();
                for (int j = 0; j < proList.getLength(); j++) {
                    Node property = proList.item(j);
                    String nodeName = property.getNodeName();
                    String nodeValue = property.getFirstChild().getNodeValue();
                    if ("song".equals(nodeName)) {
                        song.song = nodeValue;
                    }
                    else if ("song_id".equals(nodeName)) {
                        song.song_id = nodeValue;
                    }
                    else if ("singer".equals(nodeName)) {
                        song.singer = nodeValue;
                    }
                    else if ("album".equals(nodeName)) {
                        song.album = nodeValue;
                    }
                    else if ("singerPicLarge".equals(nodeName)) {
                        song.singerPicLarge = nodeValue;
                    }
                    else if ("singerPicSmall".equals(nodeName)) {
                        song.singerPicSmall = nodeValue;
                    }
                    else if ("albumPicLarge".equals(nodeName)) {
                        song.albumPicLarge = nodeValue;
                    }
                    else if ("albumPicSmall".equals(nodeName)) {
                        song.albumPicSmall = nodeValue;
                    }
                }
                listData.add(song);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
