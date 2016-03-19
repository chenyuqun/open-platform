/**  
 * Project Name:app-api  <br/>
 * File Name:RoomController.java  <br/>
 * Package Name:com.zizaike.app.api.controller.search  <br/>
 * Date:2015年11月23日下午4:50:13  <br/>
 * Copyright (c) 2015, zizaike.com All Rights Reserved.  
 *  
*/  
  
package com.zizaike.open.controller;  

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
import com.zizaike.entity.open.ctrip.vo.SetMappingInfoVo;
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
    public ResponseResult setMappingInfo(@RequestBody SetMappingInfoVo  setMappingInfoVo) throws ZZKServiceException {
        ctripService.setMappingInfo(setMappingInfoVo);
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
    public ResponseResult getHotelInfo(@RequestParam Integer currentPage) throws ZZKServiceException {
        ResponseResult resultResult = new ResponseResult();
        resultResult.setInfo(ctripService.getHotelInfo(currentPage));
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
  
