package com.zizaike.open.controller;

import com.zizaike.is.open.QunarService;
import com.zizaike.open.BaseXMLController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
