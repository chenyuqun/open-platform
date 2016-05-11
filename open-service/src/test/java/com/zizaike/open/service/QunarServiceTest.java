package com.zizaike.open.service;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.qunar.OtaOptVO;
import com.zizaike.entity.open.qunar.response.OptCode;
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
    @Test(description = "getHotelList")
    public void getHotelList(){
        long startTime=System.currentTimeMillis();
        System.err.println(qunarService.getHotelList());
        long endTime=System.currentTimeMillis(); 
        System.out.println("getHotelListTime: "+(endTime-startTime)+"ms");   
    }
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

    @Test(description = "book")
    public void getBookResponse() {
        String xml="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<bookingRequest>\n" +
                "    <hotelId>66</hotelId>\n" +
                "    <checkin>2016-04-20</checkin>\n" +
                "    <checkout>2016-04-22</checkout>\n" +
                "    <totalPrice>380</totalPrice>\n" +
                "    <currencyCode>USD</currencyCode>\n" +
                "    <rmbPrice>2356.87</rmbPrice>\n" +
                "    <customerArriveTime>16:00-18:00</customerArriveTime>\n" +
                "    <specialRemarks>PREFER_NON_SMOKING,PREFER_HIGH_FLOOR</specialRemarks>\n" +
                "    <numberOfRooms>1</numberOfRooms>\n" +
                "    <bedChoice>1</bedChoice>\n" +
                "    <instantConfirm>false</instantConfirm>\n" +
                "    <requiredAction>CONFIRM_ROOM_SUCCESS</requiredAction>\n" +
                "    <room id=\"398\" name=\"特色房\" broadband=\"FREE\" payType=\"PREPAY\" prices=\"200|200\" status=\"ACTIVE|ACTIVE\" counts=\"5|5\" \n" +
                "        roomRate=\"180|180\" taxAndFee=\"20|20\" maxOccupancy=\"2\" occupancyNumber=\"2\"\n" +
                "        freeChildrenNumber=\"1\" freeChildrenAgeLimit=\"8\" instantConfirmRoomCount=\"3|3\" wifi=\"FREE\" \n" +
                "        checkinTime=\"\" checkoutTime=\"\" area=\"\" >\n" +
                "      <bedType>\n" +
                "            <beds seq=\"1\" code=\"SINGLE\" desc=\"单人床\" count=\"2\" size=\"1.2m*2m\" >\n" +
                "            </beds>\n" +
                "      </bedType>\n" +
                "      <meal>\n" +
                "            <breakfast count=\"2|2\" desc=\"self-service breakfast\" />\n" +
                "            <lunch count=\"0|0\" desc=\"\" />\n" +
                "            <dinner count=\"0|0\" desc=\"\" />\n" +
                "      </meal>\n" +
                "      <promotionRules>\n" +
                "        <promotionRule code=\"INSTANT_DEDUCT\" desc=\"立减\" value=\"20\"></promotionRule>\n" +
                "      </promotionRules>\n" +
                "    </room>\n" +
                "    <customerInfos>\n" +
                "            <customerInfo seq=\"1\" numberOfAdults=\"2\" numberOfChildren=\"2\" childrenAges=\"8|12\" >\n" +
                "                <customer firstName=\"Deng\" lastName=\"Ziqiang\" nationality=\"CN\" gender=\"male\" />\n" +
                "                <customer firstName=\"Li\" lastName=\"Xuejuan\" nationality=\"CN\" gender=\"female\" />\n" +
                "                <customer firstName=\"Child\" lastName=\"One\" nationality=\"CN\" gender=\"female\" />\n" +
                "            </customerInfo>\n" +
                "            <customerInfo seq=\"2\" numberOfAdults=\"2\" numberOfChildren=\"0\" childrenAges=\"\" >\n" +
                "                <customer firstName=\"Li\" lastName=\"XoXo\" nationality=\"CN\" gender=\"male\" />\n" +
                "                <customer firstName=\"Sun\" lastName=\"MoMo\" nationality=\"CN\" gender=\"female\" />\n" +
                "            </customerInfo>\n" +
                "    </customerInfos>\n" +
                "    <qunarOrderInfo>\n" +
                "        <orderNum>j3gm141219163017759</orderNum>\n" +
                "        <hotelSeq>osaka_2202</hotelSeq>\n" +
                "        <hotelName>阪急阪神大阪国际酒店(Hotel Hankyu International)</hotelName>\n" +
                "        <hotelAddress>19-19, Chayamachi, Kita-ku, Osaka 530-0013, Japan</hotelAddress>\n" +
                "        <cityName>大阪</cityName>\n" +
                "        <hotelPhone>0081-6-63772100</hotelPhone>\n" +
                "        <orderDate>2014-12-19 16:30:17</orderDate>\n" +
                "        <contactName>张三</contactName>\n" +
                "        <contactPhone>1381****818</contactPhone>\n" +
                "        <contactEmail>miao.****@qunar.com</contactEmail>\n" +
                "        <payType>PREPAY</payType>\n" +
                "        <customerIp>103.24.27.9</customerIp>\n" +
                "        <invoiceCode>E</invoiceCode>\n" +
                "    </qunarOrderInfo>\n" +
                "</bookingRequest>";
        System.err.println(qunarService.book(xml));
    }
    @Test(description = "cancelBooking")
    public void cancel() throws ZZKServiceException{
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                + "<cancelRequest>"
                + "<qunarOrderNum>j3gm141219163019999</qunarOrderNum>"
                + "<orderId>9987654</orderId>"
                + "<requiredAction>AGREE_UNSUBSCRIBE</requiredAction>"
                + "<reason></reason>"
                + "<extras><!-- optional -->"
                + "<property key=\"TOKEN\" value=\"ASDFJJJJ9999XXXXYYY\" />"
                + "<property key=\"OTHER_KEY\" value=\"XXXYYY\" />"
                + "</extras>"
                + "</cancelRequest>";
        System.err.println(qunarService.cancel(xml));
    }

    @Test(description = "查询qunar订单")
    public void qunarOrderQuery() throws ZZKServiceException{
        String orderNums="test0127191351";
        System.err.println(qunarService.qunarOrderQuery(orderNums));
    }

    @Test(description = "操作qunar订单")
    public void qunarOpt() throws ZZKServiceException{
        OtaOptVO otaOptVO=new OtaOptVO();
        otaOptVO.setOrderNum("test0127191351");
        otaOptVO.setOpt(OptCode.CONFIRM_ROOM_SUCCESS);
        System.err.println(qunarService.qunarOrderOpt(otaOptVO));
    }
}
