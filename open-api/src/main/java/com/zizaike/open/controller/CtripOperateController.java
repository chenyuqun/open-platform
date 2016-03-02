/**  
 * Project Name:app-api  <br/>
 * File Name:RoomController.java  <br/>
 * Package Name:com.zizaike.app.api.controller.search  <br/>
 * Date:2015年11月23日下午4:50:13  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.controller;  

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zizaike.core.bean.ResponseResult;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.ctrip.vo.HotelGroupInterfaceRoomTypeVo;
import com.zizaike.entity.open.ctrip.vo.MappingInfoVo;
import com.zizaike.is.open.CtripService;
import com.zizaike.open.BaseAjaxController;

/**
 * 
 * ClassName: CtripController <br/>  
 * Function: ctrip调用服务. <br/>  
 * date: 2016年2月3日 下午3:04:25 <br/>  
 *  
 * @author snow.zhang  
 * @version   
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/ctrip")
public class CtripOperateController extends BaseAjaxController {
    protected final Logger LOG = LoggerFactory.getLogger(CtripOperateController.class);
    @Autowired
    private CtripService ctripService;
    /**
     * 
     * setMappingInfo:设置mapping. <br/>  
     *  
     * @author snow.zhang  
     * @param masterHotel
     * @param masterRoom
     * @param ratePlanCode
     * @param hotelGroupHotelCode
     * @param hotelGroupRoomTypeCode
     * @param hotelGroupRatePlanCode
     * @param hotelGroupRoomName
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @RequestMapping(value = "/setMappingInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult setMappingInfo(@RequestParam("masterHotel") String masterHotel,
            @RequestParam("masterRoom") String masterRoom,
            @RequestParam("ratePlanCode") String ratePlanCode,
            @RequestParam("hotelGroupHotelCode") String hotelGroupHotelCode,
            @RequestParam("hotelGroupRoomTypeCode") String hotelGroupRoomTypeCode,
            @RequestParam("hotelGroupRatePlanCode") String hotelGroupRatePlanCode,
            @RequestParam("hotelGroupRoomName") String hotelGroupRoomName) throws ZZKServiceException {
         Map<String,String> map = new HashMap<String,String>();
         map.put("masterHotel", masterHotel);
         map.put("masterRoom", masterRoom);
         map.put("ratePlanCode", ratePlanCode);
         map.put("hotelGroupHotelCode", hotelGroupHotelCode);
         map.put("hotelGroupRoomTypeCode", hotelGroupRoomTypeCode);
         map.put("hotelGroupRatePlanCode", hotelGroupRatePlanCode);
         map.put("hotelGroupRoomName", hotelGroupRoomName);
        ctripService.setMappingInfo(map);
        ResponseResult reuResult = new ResponseResult();
        return reuResult;
    }
    /**
     * 
     * setMappingInfo:(这里用一句话描述这个方法的作用). <br/>  
     *  
     * @author snow.zhang  
     * @return
     * @throws ZZKServiceException  
     * @since JDK 1.7
     */
    @RequestMapping(value = "/getHotelInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseResult setMappingInfo() throws ZZKServiceException {
        ResponseResult resultResult = new ResponseResult();
        resultResult.setInfo(ctripService.getHotelInfo());
        return resultResult;
    }
    @RequestMapping(value = "/getMappingInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult getMappingInfo(@RequestBody MappingInfoVo mappingInfo) throws ZZKServiceException {
        ResponseResult resultResult = new ResponseResult();
        resultResult.setInfo(ctripService.getMappingInfo(mappingInfo));
        return resultResult;
    }
    @RequestMapping(value = "/getCtripRoomTypeInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult getCtripRoomTypeInfo(@RequestBody HotelGroupInterfaceRoomTypeVo hotelGroupInterfaceRoomTypeVo) throws ZZKServiceException {
        ResponseResult resultResult = new ResponseResult();
        resultResult.setInfo(ctripService.getCtripRoomTypeInfo(hotelGroupInterfaceRoomTypeVo));
        return resultResult;
    }
    
}
  
