package com.zizaike.open.common.util;

import org.testng.annotations.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.open.basetest.BaseTest;
import com.zizaike.open.entity.taobao.request.RequestData;
import com.zizaike.open.entity.taobao.request.ValidateRQRequest;
import com.zizaike.open.entity.taobao.response.ResponseData;
import com.zizaike.open.entity.taobao.response.ValidateRQResponse;

public class XstreamUtilTest extends BaseTest {
    @Test
    public void getXml2Bean() throws ZZKServiceException {
        String xml = "<ValidateRQ>" + "<AuthenticationToken>" + "<Username>taobao</Username>"
                + "<Password>B75!jaJb[eO8</Password>"
                + "<CreateToken>22251178182015010620150107497867981843210904377</CreateToken>"
                + "</AuthenticationToken>" + "<TaoBaoHotelId>1357757818</TaoBaoHotelId>"
                + "<HotelId>3FENY3V11P</HotelId>" + "<TaoBaoRoomTypeId>5501264818</TaoBaoRoomTypeId>"
                + " <RoomTypeId>3FENY3V11P-RT1241</RoomTypeId>" + "<TaoBaoRatePlanId>4978679818</TaoBaoRatePlanId>"
                + "<RatePlanCode>3FENY3V11P-RT1241-RP846</RatePlanCode>" + " <TaoBaoGid>3824371818</TaoBaoGid>"
                + "<CheckIn>2015-01-06</CheckIn>" + "<CheckOut>2015-01-07</CheckOut>" + "<RoomNum>1</RoomNum>"
                + "<PaymentType>1</PaymentType>"
                + "<Extensions>{'searchid':'22251178182015010620150107497867981843210904377'}</Extensions>"
                + " </ValidateRQ>";
        ValidateRQRequest validateRQ = (ValidateRQRequest) XstreamUtil.getXml2Bean(xml, ValidateRQRequest.class);
        System.out.println(validateRQ.toString());
    }

    @Test
    public void getParamXml() throws ZZKServiceException {
        ValidateRQResponse validateRQResponse = new ValidateRQResponse("满房", "-1");
        System.err.println(XstreamUtil.getParamXml(validateRQResponse));
    }

}