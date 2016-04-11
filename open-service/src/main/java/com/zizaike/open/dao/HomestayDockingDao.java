package com.zizaike.open.dao;

import com.zizaike.core.framework.springext.database.Slave;
import com.zizaike.entity.open.HomestayDocking;
import com.zizaike.entity.open.qunar.HotelExt;
import com.zizaike.entity.open.qunar.response.Hotel;

import java.util.List;

/**
 * Project Name: code <br/>
 * Function:HomestayDockingDaoImpl. <br/>
 * date: 2016/3/31 17:43 <br/>
 *
 * @author alex
 * @since JDK 1.7
 */
public interface HomestayDockingDao {
    /**
     * 单表获取所有要推送的民宿
     * @return
     */
    @Slave
    List<HomestayDocking> queryAll();
    /**
     * 连表查询推送给Qunar的hotel信息
     */
    @Slave
    List<Hotel> queryAllQunarHotel();

    /**
     *查询推送给Qunar的hotel信息
     * @param id
     * @return
     */
    @Slave
    HotelExt queryQunarHotel(String id);

}
