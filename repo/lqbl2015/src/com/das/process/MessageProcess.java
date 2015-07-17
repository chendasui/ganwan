package com.das.process;

import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.das.util.DateUtil;

public class MessageProcess {
	
	public String processWechatMsg(String str){
		String result = "";
		try {
			Document dom =  DocumentHelper.parseText(str);
			Element xml = dom.getRootElement();
			Element usernameE = xml.element("ToUserName");
			Element fromUserNameE = xml.element("FromUserName");
			Element createTimeE = xml.element("CreateTime");
			Element msgTypeE = xml.element("MsgType");
			Element contentE = xml.element("Content");
//			Element msgIdE = root.element("MsgId");
			String toUserName = usernameE.getText();
			String fromUserName = fromUserNameE.getText();
			String createTime = createTimeE.getText();
			String msgType = msgTypeE.getText();
			String content = contentE.getText();
//			String msgId = msgIdE.getText();
			String cont = "";
			if("text".equals(msgType)){
				cont = "\""+content+"\"";
			}else if("image".equals(msgType)){
				cont = "一张图片";
			}else if("voice".equals(msgType)){
				cont = "一段语音";
			}else if("video".equals(msgType)){
				cont = "一个视频";
			}else if("shortvideo".equals(msgType)){
				cont = "一段小视频";
			}else if("location".equals(msgType)){
				cont = "一个地理位置";
			}else if("link".equals(msgType)){
				cont = "一个链接";
			}else{
				cont = "未知消息";
				System.out.println(msgType);
			}
			String response = "你发送的东西是:"+cont+"\n发送的时间是:"+DateUtil.dateToString(new Date(Long.valueOf(createTime)*1000))+"\n微信平台转发给我的是:\n"+dom.asXML();
			response = response.replaceAll("<!\\[", "<!(").replaceAll("\\]>", ")>");
			usernameE.setText(fromUserName);
			fromUserNameE.setText(toUserName);
			createTimeE.setText(""+System.currentTimeMillis());
			msgTypeE.clearContent();
			msgTypeE.addCDATA("text");
			contentE.clearContent();
			contentE.addCDATA(response);
			result = dom.asXML();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	public static void main(String[] args) {
		String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<xml><ToUserName>ouOHTsp28v3PQ2c2P1rEou5I_KLc</ToUserName><FromUserName>gh_f991350af705</FromUserName><CreateTime>1437069364</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[MyUserName:gh_f991350af705,YourUserName:ouOHTsp28v3PQ2c2P1rEou5I_KLc,SendTime:1437069364,msgType:text,YouSay:哈哈,MsgId:6172165920671193627]]></Content><MsgId>6172165920671193627</MsgId></xml>";
		System.out.println(new MessageProcess().processWechatMsg(s));
	}
	
}
