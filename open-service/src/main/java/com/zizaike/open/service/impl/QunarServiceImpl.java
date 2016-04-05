package com.zizaike.open.service.impl;

import com.zizaike.entity.open.HomestayDocking;
import com.zizaike.entity.open.qunar.response.HotelList;
import com.zizaike.is.open.QunarService;
import com.zizaike.open.common.util.QunarPhoneUtil;
import com.zizaike.open.common.util.XstreamUtil;
import com.zizaike.open.dao.BaseInfoDao;
import com.zizaike.open.dao.HomestayDockingDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
