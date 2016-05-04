
package com.zizaike.open.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zizaike.core.common.util.encrypt.MD5Encrypt;
import com.zizaike.core.common.util.http.HttpProxyUtil;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.exception.open.ErrorCodeFields;
import com.zizaike.entity.open.HomestayDocking;
import com.zizaike.entity.open.OpenChannelType;
import com.zizaike.entity.open.QunarRequest;
import com.zizaike.entity.open.QunarRoomInfoDto;
import com.zizaike.entity.open.qunar.HotelExt;
import com.zizaike.entity.open.qunar.OtaOptVO;
import com.zizaike.entity.open.qunar.request.*;
import com.zizaike.entity.open.qunar.response.*;
import com.zizaike.entity.order.request.BookOrderRequest;
import com.zizaike.entity.order.request.CancelOrderRequest;
import com.zizaike.entity.order.request.OrderGuest;
import com.zizaike.entity.order.request.QueryStatusOrderRequest;
import com.zizaike.is.open.BaseInfoService;
import com.zizaike.is.open.QunarService;
import com.zizaike.open.common.util.QunarPhoneUtil;
import com.zizaike.open.common.util.QunarUtil;
import com.zizaike.open.common.util.XstreamUtil;
import com.zizaike.open.dao.HomestayDockingDao;
import com.zizaike.open.dao.QunarRequestDao;
import com.zizaike.open.gateway.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project Name: code <br/>
 * Function:QunarServiceImpl. <br/>
 * date: 2016/3/31 14:06 <br/>
 *
 * @author alex
 * @since JDK 1.7
 */
@Service
public class QunarServiceImpl implements QunarService {
    protected final Logger LOG = LoggerFactory.getLogger(QunarServiceImpl.class);
    @Autowired
    private HomestayDockingDao homestayDockingDao;
    @Autowired
    private BaseInfoService baseInfoService;
    @Autowired
    private OrderService orderService;
    @Value("${qunar.signKey}")
    private String signKey;
    @Value("${qunar.url}")
    private String qunarUrl;
    @Autowired
    private HttpProxyUtil httpProxy;
    @Autowired
    private QunarRequestDao qunarRequestDao;

    @Override
    public String getHotelList() {
        List<HotelExt> hotelExtList = homestayDockingDao.queryAllQunarHotel();

        for (int i = 0; i < hotelExtList.size(); i++) {
            hotelExtList.get(i).setTel(QunarUtil.StandardPhoneUtil(hotelExtList.get(i).getTel(), hotelExtList.get(i).getDest_id()));
        }
        List<Hotel> list = new ArrayList<Hotel>();
        HotelList hoteList = new HotelList();
        for(int i = 0;i<hotelExtList.size();i++){
            Hotel hotel = new Hotel();
            BeanUtils.copyProperties(hotelExtList.get(i), hotel);
            list.add(hotel);
        }
        hoteList.setHotel(list);
        String xml = XstreamUtil.getResponseXml(hoteList);
        return xml;
    }

    @Override
    public List<HomestayDocking> queryAll() {
        return homestayDockingDao.queryAll();
    }

    @Override
    public String getPriceResponse(String xml) {

        try {
            PriceResponse priceResponse=new PriceResponse();
            PriceRequest priceRequest = (PriceRequest) XstreamUtil.getXml2Bean(xml, PriceRequest.class);
            String hotelId = priceRequest.getHotelId();
            HotelExt hotelExt = homestayDockingDao.queryQunarHotel(hotelId);
            QunarPhoneUtil qunarPhoneUtil = new QunarPhoneUtil();
            String checkIn = priceRequest.getCheckin();
            String checkOut = priceRequest.getCheckout();

            priceResponse.setHotelId(hotelId);
            priceResponse.setHotelName(hotelExt.getName());
            priceResponse.setHotelAddress(hotelExt.getAddress());
            priceResponse.setHotelCity(hotelExt.getCity());
            priceResponse.setHotelPhone(qunarPhoneUtil.StandardPhoneUtil(hotelExt.getTel()));
            priceResponse.setCheckin(checkIn);
            priceResponse.setCheckout(checkOut);
            priceResponse.setCurrencyCode("CNY");
            /**
             * 入住总人数
             */
            int num=0;
            if(priceRequest.getCustomerInfos()!=null){
            for (int i=0;i<priceRequest.getCustomerInfos().size();i++) {
                    num+=priceRequest.getCustomerInfos().get(i).getNumberOfAdults()
                            +priceRequest.getCustomerInfos().get(i).getNumberOfChildren();
                }
            }
            if(num==0){
                num=1;
            }
            // 加入roomList
            /**
             * 房间列表
             */
            List<Room> roomList = new ArrayList<Room>();
            String roomId = priceRequest.getRoomId();
            if (StringUtils.isEmpty(roomId)) {
                String[] rids = hotelExt.getRids().split(",");
                for (String rid : rids) {
                    roomList.add(this.getRoomPriceResponse(rid, checkIn, checkOut,num));
                }
            } else {
                roomList.add(this.getRoomPriceResponse(roomId, checkIn, checkOut,num));
            }
            priceResponse.setRooms(roomList);
            String priceResponeXml = XstreamUtil.getResponseXml(priceResponse);
            return priceResponeXml;
        } catch (Exception e) {
            LOG.error("getPriceResponse exception{}",e);
            PriceResponse priceResponse=new PriceResponse();
            String priceResponeXml = XstreamUtil.getResponseXml(priceResponse);
            return priceResponeXml;
        }

    }

    @Override
    public String book(String xml) {


            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            BookingRequest bookingRequest = (BookingRequest) XstreamUtil.getXml2Bean(xml, BookingRequest.class);
            QunarOrderInfo qunarOrderInfo=bookingRequest.getQunarOrderInfo();
            BookingResponse bookingResponse=new BookingResponse();
            /**
             * requset入库
             */
        try{
            QunarRequest qunarRequest=new QunarRequest();
            qunarRequest.setOrderId(qunarOrderInfo.getOrderNum());
            qunarRequest.setRequest(xml);
            qunarRequestDao.add(qunarRequest);
            BookOrderRequest  bookOrderRequest=new BookOrderRequest();

            bookOrderRequest.setCheckIn(sdf.parse(bookingRequest.getCheckin()));
            bookOrderRequest.setCheckOut(sdf.parse(bookingRequest.getCheckout()));
            bookOrderRequest.setContactEmail(qunarOrderInfo.getContactEmail());
            bookOrderRequest.setContactTel(qunarOrderInfo.getContactPhone());
            bookOrderRequest.setContactName(qunarOrderInfo.getContactName());
            bookOrderRequest.setHotelId(bookingRequest.getHotelId());
            bookOrderRequest.setRoomTypeId(bookingRequest.getRoom().getId());
            bookOrderRequest.setOpenChannelType(OpenChannelType.QUNAR);
            bookOrderRequest.setOpenOrderId(qunarOrderInfo.getOrderNum());
            bookOrderRequest.setPaymentType(1);
            bookOrderRequest.setTotalPrice((long)(Double.valueOf(bookingRequest.getRmbPrice())*100));
            bookOrderRequest.setRoomNum(Integer.valueOf(bookingRequest.getNumberOfRooms()));
            List<OrderGuest> orderGuestList=new ArrayList<OrderGuest>();
            int realNum=0;
            for(int i=0;i<bookingRequest.getCustomerinfo().size();i++) {
                for(int j=0;j<bookingRequest.getCustomerinfo().get(i).getCustomer().size();j++) {
                    String name = bookingRequest.getCustomerinfo().get(i).getCustomer().get(j).getFirstname()+
                            bookingRequest.getCustomerinfo().get(i).getCustomer().get(j).getLastName();
                    orderGuestList.add(new OrderGuest(name,i));
                }
                realNum+=bookingRequest.getCustomerinfo().get(i).getNumberOfChildren()+bookingRequest.getCustomerinfo().get(i).getNumberOfAdults();
            }
            /**
             * 用真实人数填充
             */
            while(realNum>orderGuestList.size()){
                orderGuestList.add(new OrderGuest("",0));

            }
            bookOrderRequest.setOrderGuests(orderGuestList);

                JSONObject result=orderService.bookRQ(bookOrderRequest);
                /**
                 * 200是success 201是网站端口下单成功[非速订]
                 */
                if(result.getString("resultCode").equals("200")||result.getString("resultCode").equals("201")){
                    bookingResponse.setOrderId(result.getJSONObject("info").getString("orderId"));
                    bookingResponse.setResult(QunarResultCode.SUCCESS);
                    bookingResponse.setQunarOrderNum(qunarOrderInfo.getOrderNum());
                    String bookRQResponseXml = XstreamUtil.getResponseXml(bookingResponse);
                    return bookRQResponseXml;
                }else{
                    bookingResponse.setResult(QunarResultCode.FAILURE);
                    bookingResponse.setQunarOrderNum(qunarOrderInfo.getOrderNum());
                    bookingResponse.setMsg(result.getString("message"));
                    bookingResponse.setOrderId("");
                    String bookRQResponseXml = XstreamUtil.getResponseXml(bookingResponse);
                    return bookRQResponseXml;
                }

       }catch(ZZKServiceException | ParseException e){
            LOG.error("qunar Book exception{}",e);
            bookingResponse.setResult(QunarResultCode.FAILURE);
            bookingResponse.setMsg("");
            bookingResponse.setQunarOrderNum(qunarOrderInfo.getOrderNum()==null?"":qunarOrderInfo.getOrderNum());
            String bookRQResponseXml = XstreamUtil.getResponseXml(bookingResponse);
            return bookRQResponseXml;
        }

    }

    @Override
    public String query(String xml) {
        try{
            OrderQueryRequest queryRequest = (OrderQueryRequest) XstreamUtil.getXml2Bean(xml, OrderQueryRequest.class);
            /**
             * 根据Qunar订单号（PK）去查询请求
             */
            QunarRequest qunarRequest=qunarRequestDao.getQunarRequest(queryRequest.getQunarOrderNum());
            BookingRequest bookingRequest = (BookingRequest) XstreamUtil.getXml2Bean(qunarRequest.getRequest(), BookingRequest.class);
            OrderQueryResponse orderQueryResponse=new OrderQueryResponse();
            QunarOrderInfoResponse orderInfoResponse=new QunarOrderInfoResponse();
            orderInfoResponse.setOrderNum(bookingRequest.getQunarOrderInfo().getOrderNum());
            orderInfoResponse.setPayType(bookingRequest.getQunarOrderInfo().getPayType());
            orderInfoResponse.setHotelSeq(bookingRequest.getQunarOrderInfo().getHotelSeq());
            orderInfoResponse.setHotelName(bookingRequest.getQunarOrderInfo().getHotelName());
            orderInfoResponse.setHotelAddress(bookingRequest.getQunarOrderInfo().getHotelAddress());
            orderInfoResponse.setCityName(bookingRequest.getQunarOrderInfo().getCityName());
            orderInfoResponse.setHotelPhone(bookingRequest.getQunarOrderInfo().getHotelPhone());
            orderInfoResponse.setOrderDate(bookingRequest.getQunarOrderInfo().getOrderDate());
            orderInfoResponse.setContactName(bookingRequest.getQunarOrderInfo().getContactName());
            orderInfoResponse.setContactPhone(bookingRequest.getQunarOrderInfo().getContactPhone());
            orderInfoResponse.setContactEmail(bookingRequest.getQunarOrderInfo().getContactEmail());
            orderInfoResponse.setCustomerIp(bookingRequest.getQunarOrderInfo().getCustomerIp());
            /**
             * N不需要 Y纸质收据 E电子收据
             */
            orderInfoResponse.setInvoiceCode(bookingRequest.getQunarOrderInfo().getInvoiceCode());
            //orderInfoResponse.setInvoice();
            orderInfoResponse.setHotelId(bookingRequest.getHotelId());
            orderInfoResponse.setCheckin(bookingRequest.getCheckin());
            orderInfoResponse.setCheckout(bookingRequest.getCheckout());
            orderInfoResponse.setTotalPrice(bookingRequest.getTotalPrice());
            orderInfoResponse.setCurrencyCode(bookingRequest.getCurrencyCode());
            orderInfoResponse.setRoom(bookingRequest.getRoom());
            orderInfoResponse.setCustomerArriiveTime(bookingRequest.getCustomerArriveTime());
            orderInfoResponse.setSpecialRemarks(bookingRequest.getSpecialRemarks());
            orderInfoResponse.setCustomerInfos(bookingRequest.getCustomerinfo());
            /**
             * 查询zizaike订单信息
             */
            QueryStatusOrderRequest queryStatusOrderRequest=new QueryStatusOrderRequest();
            queryStatusOrderRequest.setOpenChannelType(OpenChannelType.QUNAR);
            queryStatusOrderRequest.setOpenOrderId(bookingRequest.getQunarOrderInfo().getOrderNum());
            queryStatusOrderRequest.setHotelId(bookingRequest.getHotelId());
            JSONObject result=orderService.aueryStatusOrder(queryStatusOrderRequest);
            if(result.getString("resultCode").equals("200")) {
                orderInfoResponse.setOrderId(result.getJSONObject("info").getString("orderId"));
                if ("2".equals(result.getJSONObject("info").getString("status")) || "6".equals(result.getJSONObject("info").getString("status"))) {
                    orderInfoResponse.setStatus(Status.CONFIRMED_SUCCESS);
                } else {
                    orderInfoResponse.setStatus(Status.CANCELED);
                }
            }else{
                ErrorCodeFields errorCodeFields;
                errorCodeFields =  ErrorCodeFields.QUERY_FAILURE;
                throw new ZZKServiceException(errorCodeFields);
            }
            orderQueryResponse.setOrderInfo(orderInfoResponse);
            String orderQueryResponseXml = XstreamUtil.getResponseXml(orderQueryResponse);
            return orderQueryResponseXml;
        }catch(ZZKServiceException e){
            LOG.error("qunar Query exception{}",e);
            OrderQueryResponse orderQueryResponse=new OrderQueryResponse();
            String orderQueryResponseXml = XstreamUtil.getResponseXml(orderQueryResponse);
            return orderQueryResponseXml;
        }

    }

    public Room getRoomPriceResponse(String roomId, String checkIn, String checkOut, int number) {
        Room room = new Room();
        /**
         * 订单填写时才需要
         */
        try {
            QunarRoomInfoDto qunarRoomInfo = baseInfoService.getQunarRoomInfo(Integer.valueOf(roomId));
            int dateDiff = QunarUtil.dateDiff(checkIn, checkOut);
            /**
             * 房态房价
             */
            JSONObject result = baseInfoService.getZizaikePrice(roomId, checkIn, checkOut);
            if (result.getString("status").equals("ok")) {
                int roomStyle = result.getJSONObject("response").getIntValue("room_style");
                JSONArray jsonArray = result.getJSONObject("response").getJSONArray("list");
                List<Integer> priceCNList = new ArrayList<Integer>();
                List<Integer> numList = new ArrayList<Integer>();
                List<String> statusList = new ArrayList<String>();
                for (int i = 0; i < jsonArray.size()-1; i++) {
                    //取人民币
                    int priceCN = jsonArray.getJSONObject(i).getIntValue("price_cn");
                    priceCNList.add(priceCN);
                    //房间数

                    int num = jsonArray.getJSONObject(i).getIntValue("num");
                    if(roomStyle==2){
                        num=1;
                    }
                    numList.add(num);
                    if (num > 0) {
                        String status = "ACTIVE";
                        statusList.add(status);
                    } else {
                        String status = "DISABLED";
                        statusList.add(status);
                    }
                }
                room.setId(roomId);
//                room.setArea("");
                room.setBroadband(FeeMode.UNKNOWN.getCode());
                room.setCheckinTime(qunarRoomInfo.getCheckinAt());
                room.setCheckoutTime(qunarRoomInfo.getCheckoutAt());
                room.setCounts(QunarUtil.listToString(numList));
                room.setStatus(QunarUtil.listToString(statusList));
//            room.setFreeChildrenAgeLimit(8);
//            room.setFreeChildrenNumber(1);
                room.setInstantConfirmRoomCount(QunarUtil.listToString(numList));
                room.setPrices(QunarUtil.listToString(priceCNList));
                room.setName(qunarRoomInfo.getTitle());
                int maxOccupancy = 2;
                if (qunarRoomInfo.getName().equals("10+")) {
                    maxOccupancy = 10;
                } else {
                    maxOccupancy = Integer.parseInt(qunarRoomInfo.getName());
                }
                room.setMaxOccupancy(maxOccupancy);
                if (roomStyle == 2) {
                    room.setOccupancyNumber(number);
                } else {
                    room.setOccupancyNumber(room.getMaxOccupancy());
                }

                room.setPayType(PayType.PREPAY);
                room.setRoomRate(QunarUtil.listToString(priceCNList));
                if (qunarRoomInfo.getWifi() == 1) {
                    room.setWifi(FeeMode.FREE.getCode());
                } else {
                    room.setWifi(FeeMode.NONE.getCode());
                }

                room.setTaxAndFee(QunarUtil.repeatToString(0, dateDiff));
            } else {
                return room;
            }
            /*
             * 床型
             */
            BedType bedType = new BedType();
            //bedType.setRelation("OR");
            List<Beds> bedsList = new ArrayList<Beds>();
            Beds beds = new Beds();
            beds.setSeq("1");
            /**
             * count
             */
            if (StringUtils.isEmpty(qunarRoomInfo.getFieldChuangshuTid())) {
                beds.setCount(2);
            } else if (qunarRoomInfo.getFieldChuangshuTid() == 320) {
                beds.setCount(0);
            } else if (qunarRoomInfo.getFieldChuangshuTid() == 309) {
                beds.setCount(1);
            } else if (qunarRoomInfo.getFieldChuangshuTid() == 310) {
                beds.setCount(2);
            } else if (qunarRoomInfo.getFieldChuangshuTid() == 315) {
                beds.setCount(3);
            } else if (qunarRoomInfo.getFieldChuangshuTid() == 313) {
                beds.setCount(4);
            } else if (qunarRoomInfo.getFieldChuangshuTid() == 321) {
                beds.setCount(5);
            } else if (qunarRoomInfo.getFieldChuangshuTid() == 312) {
                beds.setCount(6);
            } else if (qunarRoomInfo.getFieldChuangshuTid() == 322) {
                beds.setCount(7);
            } else if (qunarRoomInfo.getFieldChuangshuTid() == 311) {
                beds.setCount(8);
            } else if (qunarRoomInfo.getFieldChuangshuTid() == 323) {
                beds.setCount(9);
            } else if (qunarRoomInfo.getFieldChuangshuTid() == 314) {
                beds.setCount(10);
            }
            /**
             * 床型
             */
            if (StringUtils.isEmpty(qunarRoomInfo.getFieldChuangxingTid())) {
                beds.setCode(BedTypeCode.OTHERS);
                beds.setDesc(BedTypeCode.getByCode("OTHERS"));
            } else if (qunarRoomInfo.getFieldChuangxingTid() == 316) {
                beds.setCount(2);
                beds.setCode(BedTypeCode.SINGLE);
                beds.setDesc(BedTypeCode.getByCode("SINGLE"));
            } else if (qunarRoomInfo.getFieldChuangxingTid() == 324) {
                beds.setCode(BedTypeCode.DORM_BED);
                beds.setDesc(BedTypeCode.getByCode("DORM_BED"));
            } else if (qunarRoomInfo.getFieldChuangxingTid() == 318) {
                beds.setCode(BedTypeCode.TATAMI);
                beds.setDesc(BedTypeCode.getByCode("TATAMI"));
            } else if (qunarRoomInfo.getFieldChuangxingTid() == 317) {
                beds.setCode(BedTypeCode.SINGLE);
                beds.setDesc(BedTypeCode.getByCode("SINGLE"));
            } else if (qunarRoomInfo.getFieldChuangxingTid() == 325) {
                beds.setCode(BedTypeCode.BUNK);
                beds.setDesc(BedTypeCode.getByCode("BUNK"));
            }
            // beds.setSize("1.8m*2m");
            bedsList.add(beds);
            bedType.setBeds(bedsList);
            room.setBedType(bedType);
            /**
             * meal节点
             */
            Meal meal = new Meal();
            int breakfastNum = 0;
            if (qunarRoomInfo.getValue() == 1) {
                if (qunarRoomInfo.getName().equals("10+")) {
                    breakfastNum = 10;
                } else {
                    breakfastNum = Integer.parseInt(qunarRoomInfo.getName());
                }
            }
            String breakfast = QunarUtil.repeatToString(breakfastNum, dateDiff);
            String lunch = QunarUtil.repeatToString(0, dateDiff);
            meal.setBreakfast(new Breakfast(breakfast, ""));
            meal.setLunch(new Lunch(lunch, ""));
            meal.setDinner(new Dinner(lunch, ""));
            room.setMeal(meal);
            /**
             * 退款政策
             */
            List<RefundRule> refundRules = new ArrayList<RefundRule>();
            List<NonRefundableRange> nonRefundableRanges = new ArrayList<NonRefundableRange>();

            if (StringUtils.isEmpty(qunarRoomInfo.getRefundRule())) {
                refundRules.add(new RefundRule(720, RefundType.DEDUCT_BY_PERCENT, "0"));
                refundRules.add(new RefundRule(0, RefundType.DEDUCT_BY_PERCENT, "100"));
            } else {
                JSONObject refundRule = JSON.parseObject(qunarRoomInfo.getRefundRule());
                //现在没有type为0这种说法
                //if (refundRule.get("type").equals(1)) {
                    JSONArray refundList = refundRule.getJSONArray("refund_list");
                    Boolean firstRefund = true;
                    for (int i = 1; i <= refundList.size(); i++) {
                        if (firstRefund) {
                            refundRules.add(new RefundRule(refundList.getJSONObject(i).getIntValue("day"), RefundType.DEDUCT_BY_PERCENT, "0"));
                            firstRefund = false;
                        } else {
                            refundRules.add(new RefundRule(refundList.getJSONObject(i).getIntValue("day"), RefundType.DEDUCT_BY_PERCENT, refundList.getJSONObject(i).getString("per")));
                        }
                    }
                    refundRules.add(new RefundRule(0, RefundType.DEDUCT_BY_PERCENT, "100"));
                //} else {
                //   nonRefundableRanges.add(new NonRefundableRange(checkIn, checkOut));
                //}
            }
            Refund refund = new Refund();
            refund.setReturnable(Boolean.TRUE);
            refund.setTimeZone("GMT+8");
            refund.setRefundRules(refundRules);
            refund.setNonRefundableRanges(nonRefundableRanges);
            room.setRefund(refund);
            /**
             * 可选节点
             */
//            room.setRemarks(remarkList);
//            room.setOptionRules(optionRuleList);
//            room.setPromotionRules(promotionRuleList);
//            room.setExtras(extraList);

        } catch (ZZKServiceException e) {
            LOG.error("getRoomPriceResponse exception{}",e);
        }


        return room;
    }

    /**  
     * TODO 去哪儿取消预订  
     * @throws ZZKServiceException 
     * 
     */
    @Override
    public String cancel(String xml) {
       
       CancelRequest cancelRequest = (CancelRequest)XstreamUtil.getXml2Bean(xml, CancelRequest.class);
       CancelResponse cancelResponse = new CancelResponse();
       CancelOrderRequest cancelOrderRequest = new CancelOrderRequest();
       cancelOrderRequest.setOpenChannelType(OpenChannelType.QUNAR);
       cancelOrderRequest.setOpenOrderId(cancelRequest.getQunarOrderNum());
       cancelOrderRequest.setOrderId(cancelRequest.getOrderId());
       
       cancelResponse.setQunarOrderNum(cancelRequest.getQunarOrderNum());
       cancelResponse.setOrderId(cancelRequest.getOrderId());
       cancelResponse.setMsg("");   
       try{
           JSONObject result = orderService.cancelRQ(cancelOrderRequest);
           if(result.getString("resultCode").equals("200")){
               cancelResponse.setResult(QunarResultCode.SUCCESS);                   
               }
           else{
               cancelResponse.setResult(QunarResultCode.FAILURE);               
               }
           
       }catch(ZZKServiceException e){
           cancelResponse.setResult(QunarResultCode.FAILURE);
           LOG.error("qunarCancelBooking IOException {}",e.toString());
       }
       String cancelBookingXML = XstreamUtil.getResponseXml(cancelResponse);
       return cancelBookingXML;

    }


    @Override
    public String qunarOrderQuery(String orderNums) {
        String result=null;
        try {
            Map<String,String> map=new HashMap<String,String>();
            map.put("orderNums",orderNums);
            String hmac=MD5Encrypt.encrypt(signKey+orderNums);
            map.put("hmac",hmac);
            result = httpProxy.httpGetXMl(qunarUrl + "qunarOrderQuery", map);
            LOG.info("qunarOrderQuery return{}",result);
        } catch (IOException e) {
            LOG.error("qunarOrderQuery IOException {}", e.toString());
        }
        return result;
    }

    @Override
    public JSONObject qunarOrderOpt(OtaOptVO otaOptVO) {
        JSONObject result=null;
        try {
            Map<String,String> map=new HashMap<String,String>();
            map.put("orderNum",otaOptVO.getOrderNum());
            map.put("opt",otaOptVO.getOpt().getCode());
            if(otaOptVO.getOpt().getCode().equals("CONFIRM_ROOM_SUCCESS")||
                    otaOptVO.getOpt().getCode().equals("CONFIRM_ROOM_FAILURE")||
                    otaOptVO.getOpt().getCode().equals("REFUSE_UNSUBSCRIBE")){
                /**
                 * 确认可住，确认不可住，拒绝退订
                 */
                String hmac=MD5Encrypt.encrypt(signKey+otaOptVO.getOrderNum()+otaOptVO.getOpt());
                map.put("hmac",hmac);
            }else if(otaOptVO.getOpt().getCode().equals("ADD_REMARKS")){
                /**
                 * 添加备注
                 */
                map.put("remark",otaOptVO.getRemark());
                String hmac=MD5Encrypt.encrypt(signKey+otaOptVO.getOrderNum()+otaOptVO.getOpt()+otaOptVO.getRemark());
                map.put("hmac",hmac);
            }else if(otaOptVO.getOpt().getCode().equals("SEND_SMS")){
                /**
                 * 发送短信
                 */
                map.put("smsContent",otaOptVO.getSmsContent());
                String hmac=MD5Encrypt.encrypt(signKey+otaOptVO.getOrderNum()+otaOptVO.getOpt()+otaOptVO.getSmsContent());
                map.put("hmac",hmac);

            }else if(otaOptVO.getOpt().getCode().equals("AGREE_UNSUBSCRIBE")||
                    otaOptVO.getOpt().getCode().equals("APPLY_UNSUBSCRIBE")){
                /**
                 * 同意退订，申请退订（从代理商一方）money，需要返还给消费者的金额，币别为RMB。代理商需要根据bookingRequest@rmbPrice 计算出相应的人民币金额。
                 */
                map.put("money",otaOptVO.getMoney());
                String hmac=MD5Encrypt.encrypt(signKey+otaOptVO.getOrderNum()+otaOptVO.getOpt()+otaOptVO.getMoney());
                map.put("hmac",hmac);
            }


            result = httpProxy.httpUrlPOST(qunarUrl + "otaOpt", map);
            LOG.info("qunarOrderOpt return{}",result);
        } catch (Exception e) {
            LOG.error("qunarOrderOpt Exception {}", e.toString());
        }
        return result;
    }
}