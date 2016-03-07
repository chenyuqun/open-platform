package com.zizaike.open.common.util;

import java.io.Writer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.zizaike.entity.open.alibaba.request.RequestData;
import com.zizaike.entity.open.alibaba.response.ResponseData;
public  class XstreamUtil { 
    private static final String xmlHead = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n";
    //private static final XStream xstream =  new XStream(new DomDriver("utf8"));
    //去掉 &quot 等特殊字符
    private static final XStream xstream = new XStream(new XppDriver() {
        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (!text.isEmpty()) {
                        writer.write(text);
                    } else {
                        super.writeText(writer, text);
                    }
                }
            };
        }
    });
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
 public static String getResponseXml(ResponseData data){ 
     xstream.aliasType(data.getClass().getSimpleName(), data.getClass());
     //用注解返回xml
     xstream.autodetectAnnotations(true);
     String beanXml = xstream.toXML(data);
     StringBuffer xml = new StringBuffer(xmlHead).append(beanXml);  
    return xml.toString();
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
 public static String getCtripResponseXml(ResponseData data){ 
     xstream.aliasType(data.getClass().getSimpleName(), data.getClass());
     //用注解返回xml
     xstream.autodetectAnnotations(true);
     String beanXml = xstream.toXML(data);
     StringBuffer xml = new StringBuffer(xmlHead).append("<RequestResponse>\r\n").append(beanXml).append("<RequestResponse>\r\n");  
     return xml.toString();
 }
  

}