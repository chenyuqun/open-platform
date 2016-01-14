package com.zizaike.open.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zizaike.open.entity.taobao.request.RequestData;
import com.zizaike.open.entity.taobao.response.ResponseData;
public  class XstreamUtil { 
    private static final String xmlHead = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n";
	private static final XStream xstream =  new XStream(new DomDriver("utf8"));
/**
 * 
 * getXml2Bean:xml to javaBean. <br/>  
 *  
 * @author snow.zhang  
 * @param responseXml
 * @param responseDataType
 * @return  
 * @since JDK 1.7
 */
 public static RequestData getXml2Bean(String responseXml,Class responseDataType){ 
	 xstream.processAnnotations(responseDataType);
	 RequestData data =  (RequestData) xstream.fromXML(responseXml);
	 return data;
 }
/**
 * 
 * getParamXml:javaBean to xml. <br/>  
 *  
 * @author snow.zhang  
 * @param data
 * @return  
 * @since JDK 1.7
 */
 public static String getParamXml(ResponseData data ){ 
	 xstream.aliasType(data.getClass().getSimpleName(), data.getClass());
	 //用注解返回xml
	 xstream.autodetectAnnotations(true);
 	 String beanXml = xstream.toXML(data);
	 StringBuffer xml = new StringBuffer(xmlHead).append(beanXml);  
 	return xml.toString();
 }
  
 
}