package com.zizaike.open.controller;

import com.zizaike.core.framework.exception.ZZKServiceException;
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

    @RequestMapping(value = "/book", method = RequestMethod.POST,produces={"text/xml"},consumes={"text/xml"})
    @ResponseBody
    public String getSearchResult(@RequestBody String xml ){
        LOG.info("qunarService book xml:{}",xml);
          return   qunarService.book(xml);

    }
    @RequestMapping(value = "/cancelBooking", method = RequestMethod.POST,produces={"text/xml"},consumes={"text/xml"})
    @ResponseBody
    public String cancelBooking(@RequestBody String xml ) throws ZZKServiceException{
        LOG.info("qunarService cancelBooking xml:{}",xml);
          return   qunarService.cancelBooking(xml);

    }
}
