package com.zizaike.open.service;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.open.QunarService;
import com.zizaike.open.bastest.BaseTest;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Project Name: code <br/>
 * Function:QunarServiceTest. <br/>
 * date: 2016/4/12 19:54 <br/>
 *
 * @author alex
 * @since JDK 1.7
 */
public class QunarServiceTest extends BaseTest {
    @Autowired
    private QunarService qunarService;

    @Test(description = "priceRespnse")
    public void getPriceResponse() {
        String xml="<?xml version=\"1.0\"     encoding=\"utf-8\"?>\n" +
                "<priceRequest> \n" +
                "  <hotelId>66</hotelId>  \n" +
                "  <checkin>2016-04-10</checkin>  \n" +
                "  <checkout>2016-04-12</checkout>  \n" +
                "  <roomId>395</roomId>\n" +
                "  <numberOfRooms>2</numberOfRooms>  \n" +
                "  <customerInfos> \n" +
                "    <customerInfo seq=\"0\" numberOfAdults=\"2\" numberOfChildren=\"2\" childrenAges=\"8|12\"></customerInfo>  \n" +
                "    <customerInfo seq=\"1\" numberOfAdults=\"2\" numberOfChildren=\"0\" childrenAges=\"\"></customerInfo> \n" +
                "  </customerInfos> \n" +
                "</priceRequest>";
        System.err.println(qunarService.getPriceResponse(xml));
    }
}
