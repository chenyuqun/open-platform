package com.zizaike.open.controller;

import com.alibaba.fastjson.JSONObject;
import com.zizaike.core.bean.ResponseResult;
import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.entity.open.ctrip.vo.MappingInfoVo;
import com.zizaike.entity.open.qunar.OrderQueryVO;
import com.zizaike.entity.open.qunar.OtaOptVO;
import com.zizaike.entity.open.qunar.response.OptCode;
import com.zizaike.entity.open.qunar.response.OrderInfoResponse;
import com.zizaike.entity.solr.SearchType;
import com.zizaike.is.open.QunarService;
import com.zizaike.open.BaseXMLController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Class Name: QunarController <br/>
 * Function:Qunar调用. <br/>
 * date: 2016/3/31 11:59 <br/>
 *
 * @author alex
 * @since JDK 1.7
 */
@Controller
@RequestMapping("/qunarService")
public class QunarController extends BaseXMLController {
    protected final Logger LOG = LoggerFactory.getLogger(QunarController.class);
    @Autowired
    private QunarService qunarService;
    @RequestMapping(value = "/getHotelList", method = RequestMethod.GET,produces={"text/xml"})
    @ResponseBody
    public String getHotelList(){
        return qunarService.getHotelList();
    }

    @RequestMapping(value = "/getPriceResponse", method = RequestMethod.GET,produces={"text/xml"})
    @ResponseBody
    public String getPriceResponse(@RequestParam("xml") String xml ){
        LOG.info("qunarService priceRequest xml:{}",xml);
        return qunarService.getPriceResponse(xml);
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST,produces={"text/xml"})
    @ResponseBody
    public String getSearchResult(@RequestParam("xml") String xml ) throws ZZKServiceException{
        LOG.info("qunarService book xml:{}",xml);
        return   qunarService.book(xml);

    }
    @RequestMapping(value = "/cancel", method = RequestMethod.POST,produces={"text/xml"})
    @ResponseBody
    public String cancel(@RequestParam("xml") String xml ) throws ZZKServiceException{
        LOG.info("qunarService cancel xml:{}",xml);
        return   qunarService.cancel(xml);

    }

    @RequestMapping(value = "/query", method = RequestMethod.GET,produces={"text/xml"})
    @ResponseBody
    public String query(@RequestParam("xml") String xml ) throws ZZKServiceException{
        LOG.info("qunarService query xml:{}",xml);
        return   qunarService.query(xml);

    }

    /**
     * 调用qunar接口查询信息
     * @param orderNums
     * @return
     * @throws ZZKServiceException
     */
    @RequestMapping(value = "/queryQunar", method = RequestMethod.GET)
    @ResponseBody
    public OrderInfoResponse queryQunar(@RequestParam("orderNums") String orderNums) throws ZZKServiceException{
        LOG.info("qunarService queryQunar xml:{}",orderNums);
        return   qunarService.qunarOrderQuery(orderNums);

    }

    /**
     * 调用qunar接口操作
     * @TODO 目前没有后台使用,报错不方便查看，目前直接用的qunar返回，以后可能要改成这边的标准返回
     * @return
     * @throws ZZKServiceException
     */
    @RequestMapping(value = "/opt", method = RequestMethod.POST,produces={"application/json"})
    @ResponseBody
    public JSONObject opt(@RequestBody OtaOptVO otaOptVO) throws ZZKServiceException{
        LOG.info("qunarService OtaOptVO {}",otaOptVO);
        return qunarService.qunarOrderOpt(otaOptVO);

    }

}
