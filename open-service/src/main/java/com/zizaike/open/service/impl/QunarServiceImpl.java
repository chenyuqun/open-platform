package com.zizaike.open.service.impl;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.HomestayDocking;
import com.zizaike.entity.open.QunarRoomInfoDto;
import com.zizaike.entity.open.RoomInfoDto;
import com.zizaike.entity.open.qunar.HotelExt;
import com.zizaike.entity.open.qunar.request.PriceRequest;
import com.zizaike.entity.open.qunar.response.HotelList;
import com.zizaike.entity.open.qunar.response.PriceResponse;
import com.zizaike.entity.open.qunar.response.Room;
import com.zizaike.is.open.BaseInfoService;
import com.zizaike.is.open.QunarService;
import com.zizaike.open.common.util.QunarPhoneUtil;
import com.zizaike.open.common.util.XstreamUtil;
import com.zizaike.open.dao.HomestayDockingDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Project Name: code <br/>
 * Function:QunarServiceImpl. <br/>
 * date: 2016/3/31 14:06 <br/>
 *
 * @author alex
 * @since JDK 1.7
 */
@Service
public class QunarServiceImpl implements QunarService{
    protected final Logger LOG = LoggerFactory.getLogger(QunarServiceImpl.class);
    @Autowired
    private HomestayDockingDao homestayDockingDao;
    @Autowired
    private BaseInfoService baseInfoService;

    @Override
    public String getHotelList() {
        HotelList hotelList=new HotelList();
        hotelList.setHotel(homestayDockingDao.queryAllQunarHotel());
        QunarPhoneUtil qunarPhoneUtil= new QunarPhoneUtil();
        hotelList = qunarPhoneUtil.CoverPhoneNumber(hotelList);
        String xml=XstreamUtil.getResponseXml(hotelList);
        return xml;
    }

    @Override
    public List<HomestayDocking> queryAll() {
        return homestayDockingDao.queryAll();
    }

    @Override
    public String getPriceResponse(String xml) {
        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-mm-dd");
        PriceRequest priceRequest = (PriceRequest) XstreamUtil.getXml2Bean(xml, PriceRequest.class);
        String hotelId=priceRequest.getHotelId();
        HotelExt hotelExt=homestayDockingDao.queryQunarHotel(hotelId);
        QunarPhoneUtil qunarPhoneUtil= new QunarPhoneUtil();
        String checkIn=priceRequest.getCheckin();
        String checkOut=priceRequest.getCheckout();
        PriceResponse priceResponse=new PriceResponse();
        priceResponse.setHotelId(hotelId);
        priceResponse.setHotelName(hotelExt.getName());
        priceResponse.setHotelAddress(hotelExt.getAddress());
        priceResponse.setHotelCity(hotelExt.getCity());
        priceResponse.setHotelPhone(qunarPhoneUtil.StandardPhoneUtil(hotelExt.getTel()));
        priceResponse.setCheckin(checkIn);
        priceResponse.setCheckout(checkOut);
        priceResponse.setCurrrencyCode("CNY");
        //TODO 加入roomList
        /**
         * 房间列表
         */
        List<Room> roomList=new ArrayList<Room>();
        String roomId=priceRequest.getRoomId();
        if(StringUtils.isEmpty(roomId)){
            String[] rids=hotelExt.getRids().split(",");
            for(String rid:rids){
                roomList.add(this.getRoomPriceResponse(rid));
            }
        }else{
            roomList.add(this.getRoomPriceResponse(roomId));
        }
        priceResponse.setRooms(roomList);
        String priceResponeXml=XstreamUtil.getResponseXml(priceResponse);
        return priceResponeXml;
    }

    public Room getRoomPriceResponse(String roomId){
        Room room=new Room();
        /**
         * 订单填写时才需要
         */
        try {
            QunarRoomInfoDto qunarRoomInfo=baseInfoService.getQunarRoomInfo(Integer.valueOf(roomId));
            /**
             * 退款政策
             */
            qunarRoomInfo.getRefundRule();
            /**
             * 是否有早餐
             */
            qunarRoomInfo.getValue();
            /**
             * 最大入住人数 有个10+的
             */
            qunarRoomInfo.getName();
            /**
             * 床型
             */
            if(StringUtils.isEmpty(qunarRoomInfo.getFieldChuangxingTid())){

            }else if(qunarRoomInfo.getFieldChuangxingTid()==316){

            }
            else if(qunarRoomInfo.getFieldChuangxingTid()==324){

            }
            else if(qunarRoomInfo.getFieldChuangxingTid()==318){

            }
            else if(qunarRoomInfo.getFieldChuangxingTid()==317){

            }
            else if(qunarRoomInfo.getFieldChuangxingTid()==325){

            }
            else{

            }
            ;
        } catch (ZZKServiceException e) {
            e.printStackTrace();
        }
//        try {
//            Date checkInDate = sdf.parse(checkIn);
//            Date checkOutDate= sdf.parse(checkOut);
//            /**
//             * 房态房价
//             */
//            JSONObject result=baseInfoService.getZizaikePrice(roomId);
//            if(result.getString("status").equals("ok")){
//                JSONArray jsonArray=result.getJSONArray("list");
//                for(int i=0;i<jsonArray.size();i++){
//                    //日期不在其中
//                    if(sdf.parse(jsonArray.getJSONObject(i).getString("date")).before(checkInDate)||
//                            sdf.parse(jsonArray.getJSONObject(i).getString("date")).after(checkOutDate)){
//                        continue;
//                    }else{
//                        //取人民币
//                        jsonArray.getJSONObject(i).getString("price_cn");
//                        //房间数
//                        jsonArray.getJSONObject(i).getString("num");
//                    }
//                }
//            }
//        } catch (ParseException e) {
//        }
        return room;
    }
}
