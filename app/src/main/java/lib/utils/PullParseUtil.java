package lib.utils;

import android.annotation.SuppressLint;
import android.util.Xml;

import com.mumu.meishijia.model.RegionModel;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析城市xml
 * 
 * @author dongmei zhang
 * @createdate 2014-9-27
 */
public class PullParseUtil {
	@SuppressLint("DefaultLocale")
	public static List<RegionModel> getCityBeans(InputStream inputStream,
													  String parentid) throws Exception {
		List<RegionModel> cityList = null;
		RegionModel cityBean = null;
		 //获取XmlPullParser的实例
		XmlPullParser parser = Xml.newPullParser();
		 //设置输入流  xml文件
		parser.setInput(inputStream, "UTF-8");
		//开始
		int event = parser.getEventType();
		while (event != XmlPullParser.END_DOCUMENT) {
			String nodeName = parser.getName();
			switch (event) {
			case XmlPullParser.START_DOCUMENT://文档开始
				cityList = new ArrayList<RegionModel>();
				break;
			case XmlPullParser.START_TAG://开始节点
				if ("RECORD".equals(nodeName)) {
					cityBean = new RegionModel();
				} else if ("id".equals(nodeName)) {
					cityBean.setId(parser.nextText());
				} else if ("parentid".equals(nodeName)) {
					cityBean.setParentid(parser.nextText());
				} else if ("name".equals(nodeName)) {
					cityBean.setName(parser.nextText());
				} 
//					else if ("name_en".equals(nodeName)) {
//					String pinyin = parser.nextText().substring(0, 1);
//					String sortString = pinyin.toUpperCase();
//					if (sortString.matches("[A-Z]")) {
//						cityBean.setSortLetters(sortString);
//					} else {
//						cityBean.setSortLetters("#");
//					}
//				}
				break;
			case XmlPullParser.END_TAG: //结束节点
				if ("RECORD".equals(parser.getName())) {
					if(parentid.equals(cityBean.getParentid()))
						cityList.add(cityBean);
					cityBean = null;
				}
				break;
			}
			event = parser.next();//
		}// end while
		return cityList;
	}

}
