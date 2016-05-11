package com.zizaike.open.dao.impl;

import com.zizaike.core.framework.mybatis.impl.GenericMyIbatisDao;
import com.zizaike.entity.open.HomestayDocking;
import com.zizaike.entity.open.qunar.HotelExt;
import com.zizaike.entity.open.qunar.response.Hotel;
import com.zizaike.open.dao.HomestayDockingDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Project Name: code <br/>
 * Function:HomestayDockingDaoImpl. <br/>
 * date: 2016/3/31 17:45 <br/>
 *
 * @author alex
 * @since JDK 1.7
 */
@Repository
public class HomestayDockingDaoImpl extends GenericMyIbatisDao<HomestayDocking, Integer> implements HomestayDockingDao {

    private static final String NAMESPACE = "com.zizaike.open.dao.HomestayDockingMapper." ;

    @Override
    public List<HomestayDocking> queryAll() {
        List<HomestayDocking> homestayDockingList = this.getSqlSession().selectList(NAMESPACE+"queryAll");
        return homestayDockingList;
    }

    @Override
    public List<HotelExt> queryAllQunarHotel() {
        List<HotelExt> hotelExtList = this.getSqlSession().selectList(NAMESPACE+"queryAllQunarHotel");
        return hotelExtList;
    }

    @Override
    public HotelExt queryQunarHotel(String id) {
        HotelExt hotelExt = this.getSqlSession().selectOne(NAMESPACE+"queryQunarHotel",id);
        return hotelExt;
    }
}
