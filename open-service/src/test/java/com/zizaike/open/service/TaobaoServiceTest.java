package com.zizaike.open.service;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.is.open.TaobaoService;
import com.zizaike.open.bastest.BaseTest;

public class TaobaoServiceTest extends BaseTest {
    @Autowired
    private TaobaoService taobaoService;
    
    @Test(description = "试单请求单元测试")
    public void testValidateRQ() throws ZZKServiceException, DocumentException {
        String xml = "<ValidateRQ>" + "<AuthenticationToken>" + "<Username>taobao</Username>"
                + "<Password>B75!jaJb[eO8</Password>"
                + "<CreateToken>22251178182015010620150107497867981843210904377</CreateToken>"
                + "</AuthenticationToken>" + "<TaoBaoHotelId>1357757818</TaoBaoHotelId>"
                + "<HotelId>3FENY3V11P</HotelId>" + "<TaoBaoRoomTypeId>5501264818</TaoBaoRoomTypeId>"
                + " <RoomTypeId>398</RoomTypeId>" + "<TaoBaoRatePlanId>4978679818</TaoBaoRatePlanId>"
                + "<RatePlanCode>3FENY3V11P-RT1241-RP846</RatePlanCode>" + " <TaoBaoGid>3824371818</TaoBaoGid>"
                + "<CheckIn>2015-01-20</CheckIn>" + "<CheckOut>2015-01-22</CheckOut>" + "<RoomNum>1</RoomNum>"
                + "<PaymentType>1</PaymentType>"
                + "<Extensions>{'searchid':'22251178182015010620150107497867981843210904377'}</Extensions>"
                + " </ValidateRQ>";
        System.err.println(taobaoService.service(xml));
    }
    
    @Test(description = "创建订单单元测试")
    public void testBookRQ() throws ZZKServiceException, DocumentException {
        String xml = "<BookRQ>"
                + "<AuthenticationToken>"
                + "<Username>taobao</Username>"
                + "<Password>taobao</Password>"
                + "<CreateToken>taobao1387784033263-1387784033266</CreateToken>"
                + "</AuthenticationToken>"
                + "<TaoBaoOrderId>1387784033263</TaoBaoOrderId>"
                + "<TaoBaoHotelId>123456789</TaoBaoHotelId>"
                + "<HotelId>80</HotelId>"
                + "<TaoBaoRoomTypeId>123456978</TaoBaoRoomTypeId>"
                + "<RoomTypeId>ST</RoomTypeId>"
                + "<TaoBaoRatePlanId >123000123</TaoBaoRatePlanId >"
                + "<RatePlanCode>VIP</RatePlanCode>"
                + "<TaoBaoGid>1234567890</TaoBaoGid>"
                + "<CheckIn>2013-12-24 00:00:00</CheckIn>"
                + "<CheckOut>2013-12-26 00:00:00</CheckOut>"
                + "<HourRent>false</HourRent>"
                + "<EarliestArriveTime>2013-12-24 20:00:00</EarliestArriveTime>"
                + "<LatestArriveTime>2013-12-24 22:00:00</LatestArriveTime>"
                + "<RoomNum>1</RoomNum>"
                + "<TotalPrice>63850</TotalPrice>"
                + "<SellerDiscount>6800</SellerDiscount >"
                + "<AlitripDiscount >800</AlitripDiscount >"
                + "<Currency>CNY</Currency>"
                + "<PaymentType>5</PaymentType>"
                + "<ContactName>测试联系人</ContactName>"
                + "<ContactTel>13920682209</ContactTel>"
                + "<ContactEmail >hello@taobao.com</ContactEmail >"
                + "<DailyInfos>"
                + "<DailyInfo>"
                + "<Day>2013-12-24</Day>"
                + "<Price>17800</Price>"
                + "</DailyInfo>"
                + "<DailyInfo>"
                + "<Day>2013-12-25</Day>"
                + "<Price>46050</Price>"
                + "</DailyInfo>"
                + "</DailyInfos>"
                + "<OrderGuests>"
                + "<OrderGuest>"
                + "<Name>入住人1</Name>"
                + "<RoomPos>1</RoomPos>"
                + "</OrderGuest>"
                + "<OrderGuest>"
                + "<Name>入住人2</Name>"
                + "<RoomPos>1</RoomPos>"
                + "</OrderGuest>"
                + "</OrderGuests>"
                + "<Comment>测试</Comment>"
                + "<GuaranteeType> 0</GuaranteeType>"
                + "<MemberCardNo >NB888888888</MemberCardNo>"
                + "<ReceiptInfo>"
                + "<ReceiptTitle>淘宝软件有限公司</ReceiptTitle>"
                + "<ReceiptType>房费</ReceiptType>"
                + "<ReceiptAddress>{\"name\":\"收件人\",\"address\":\"北京市\",\"postCode\":\"100022\",\"mobile\":\"13877779999\",\"phone\":\"01082828989\"}</ReceiptAddress>"
                + "</ReceiptInfo>" 
                + "<Extensions></Extensions>"
                + "</BookRQ>";
        System.err.println(taobaoService.service(xml));
    }
    
    @Test(description = "查询订单单元测试")
    public void testQueryStatusRQ() throws ZZKServiceException, DocumentException {
        String xml = "<QueryStatusRQ>"
                + "<AuthenticationToken>"
                + "<Username>taobao</Username>"
                + "<Password>taobao</Password>"
                + "<CreateToken>taobao1230123213-1387792484913</CreateToken>"
                + "</AuthenticationToken>"
                + " <OrderId>12321323</OrderId>"
                + "<TaoBaoOrderId>1230123213</TaoBaoOrderId>"
                + " <HotelId>123456</HotelId>"
                + "</QueryStatusRQ>";
        System.err.println(taobaoService.service(xml));
    }
    
    @Test(description = "取消订单单元测试")
    public void testCancelRQ() throws ZZKServiceException, DocumentException {
        String xml = "<CancelRQ>"
                +"<AuthenticationToken>"
                + "<Username>taobao</Username>"
                + "<Password>taobao</Password>"
                + "<CreateToken>taobao125484778-1387789907859</CreateToken>"
                + "</AuthenticationToken>"
                + "<TaoBaoOrderId>21544874</TaoBaoOrderId>"
                + "<OrderId>21544874</OrderId>"
                + "<HotelId>HZJT01</HotelId>"
                + "<Reason>reason</Reason>"
                + "<CancelId>1387789907859</CancelId>"
                + "<HardCancel>true</HardCancel>"
                + "</CancelRQ>";
        System.err.println(taobaoService.service(xml));
    }
    
}
