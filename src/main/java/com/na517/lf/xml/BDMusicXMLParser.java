package com.na517.lf.xml;

import com.na517.lf.model.BDMusicData;

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
 * 类描述：BaiduMusicXMLParser
 * 创建人：lianfeng
 * 创建时间：2015/6/3 18:22
 * 修改人：lianfeng
 * 修改时间：2015/6/3 18:22
 * 修改备注：
 * 版本：V1.0
 */
public class BDMusicXMLParser implements Parser<BDMusicData> {
    @Override
    public List<BDMusicData> parse(String xmlData) throws ParserConfigurationException, IOException, SAXException {
//        // 百度接口的返回没有XML标头声明，需要做处理
//        String xmlHead = "<?xml version=\"1.0\" encoding=\"gb2312\" ?>";
//        StringBuilder sbXml = new StringBuilder();
//        sbXml.append(xmlHead);
//        sbXml.append(xmlData);
        InputStream inputStream = new ByteArrayInputStream(xmlData.getBytes());

        List<BDMusicData> songData = new ArrayList<BDMusicData>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
//        Document doc = builder.parse(xmlData);
        Document doc = builder.parse(inputStream);
        Element rootElement = doc.getDocumentElement();
        NodeList items = rootElement.getElementsByTagName("url");
        for (int i = 0; i < items.getLength(); i++) {
            BDMusicData data = new BDMusicData();
            Node item = items.item(i);
            NodeList proList = item.getChildNodes();
            for (int j = 0; j < proList.getLength(); j++) {
                Node property = proList.item(j);
                String nodeName = property.getNodeName();
                String nodeValue = property.getFirstChild().getNodeValue();
                if ("encode".equals(nodeName)) {
                    data.setEncode(nodeValue);
                }
                else if ("decode".equals(nodeName)) {
                    data.setDecode(nodeValue);
                }
                else if ("type".equals(nodeName)) {
                    data.setType(Integer.parseInt(nodeValue));
                }
                else if ("lrcid".equals(nodeName)) {
                    data.setLrcid(Integer.parseInt(nodeValue));
                }
                else if ("flag".equals(nodeName)) {
                    data.setFlag(Integer.parseInt(nodeValue));
                }
            }
            songData.add(data);
        }

        return songData;
    }

    @Override
    public String serialize(List<BDMusicData> list) {
        return null;
    }
}
