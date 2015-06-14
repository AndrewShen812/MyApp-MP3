package com.na517.lf.xml;

import com.na517.lf.model.BDMusicData;

import org.w3c.dom.DOMException;
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
        Document doc = builder.parse(inputStream);
        Element rootElement = doc.getDocumentElement();
        NodeList urlItems = rootElement.getElementsByTagName("url");
        NodeList durlItems = rootElement.getElementsByTagName("durl");
        getNodeList(urlItems, songData);
        getNodeList(durlItems, songData);

        return songData;
    }

    @Override
    public String serialize(List<BDMusicData> list) {
        return null;
    }

    private void getNodeList(NodeList items, List<BDMusicData> listData) {
        try {
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
                listData.add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * xml数据示例
     */
    /*
    <result>
	<count>5</count>
	<url>
		<encode>
			<![CDATA[
http://zhangmenshiting.baidu.com/data2/music/87867901/aWlramxvZ2lfn6NndK6ap5WXcGZtbmibZWVrlphmmmuWa2xpZpmZcWFqZmxqb2xolJZjbGdmmXGUl5drbZidaWdpZVqin5t1YWBrbGVncHFjamNoaGdscDE$
]]>
		</encode>
		<decode>
			<![CDATA[
87867901.mp3?xcode=2881c438bc0c3e9951cb908385950cd0820b9ced78bf1672&mid=0.88019928043158
]]>
		</decode>
		<type>8</type>
		<lrcid>0</lrcid>
		<flag>0</flag>
	</url>
	<durl>
		<encode>
			<![CDATA[
http://zhangmenshiting2.baidu.com/data2/music/84946925/aWZsaGtvaW1fn6NndK6ap5WXcGZtbmibZWVrlphmmmuWa2xoZmZtcGZilJebmGdtkmJpbJlqmpppYpSVmJyZcGNraFqin5t1YWBrbGVncHFjamNoaGdscDE$
]]>
		</encode>
		<decode>
			<![CDATA[
84946925.mp3?xcode=2881c438bc0c3e994106850acfb05a068d4cb80aacfb8295&mid=0.88019928043158
]]>
		</decode>
		<type>8</type>
		<lrcid>0</lrcid>
		<flag>0</flag>
	</durl>
	<url>
		<encode>
			<![CDATA[
http://shiting.chaishouji.com:551/file2/225/Y2RnbG5oNw$$.mp3
]]>
		</encode>
		<decode>
			<![CDATA[ 224892.mp3 ]]>
		</decode>
		<type>1</type>
		<lrcid>0</lrcid>
		<flag>0</flag>
	</url>
	<durl/>
	<url>
		<encode>
			<![CDATA[
http://flv.whcedu.cn/files/2012-12/02/YWRka2lsa2xoYmo0.mp3
]]>
		</encode>
		<decode>
			<![CDATA[ 02174644707.mp3 ]]>
		</decode>
		<type>1</type>
		<lrcid>0</lrcid>
		<flag>0</flag>
	</url>
	<durl/>
	<url>
		<encode>
			<![CDATA[ http://www.zx58.cn/userfiles/media/n5iXp6I2.mp3 ]]>
		</encode>
		<decode>
			<![CDATA[ nfdsm.mp3 ]]>
		</decode>
		<type>1</type>
		<lrcid>0</lrcid>
		<flag>0</flag>
	</url>
	<durl/>
	<url>
		<encode>
			<![CDATA[
http://shiting5.chaishouji.com:555/file2/225/Y2RnbG5oNw$$.mp3
]]>
		</encode>
		<decode>
			<![CDATA[ 224892.mp3 ]]>
		</decode>
		<type>1</type>
		<lrcid>0</lrcid>
		<flag>0</flag>
	</url>
	<durl/>
	<p2p>
		<hash>284df2cfe87b4fe1442f4469aa688abc067a6243</hash>
		<url>
			<![CDATA[ ]]>
		</url>
		<type>mp3</type>
		<size>6795585</size>
		<bitrate>192</bitrate>
	</p2p>
</result>*/
}
