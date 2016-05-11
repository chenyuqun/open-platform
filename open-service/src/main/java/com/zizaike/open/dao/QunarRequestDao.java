package com.zizaike.open.dao;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.springext.database.Master;
import com.zizaike.core.framework.springext.database.Slave;
import com.zizaike.entity.open.QunarRequest;

/**
 * Project Name: code <br/>
 * Function:QunarRequestDao. <br/>
 * date: 2016/4/21 10:38 <br/>
 *
 * @author alex
 * @since JDK 1.7
 */
public interface QunarRequestDao {
    /**
     * 获取QunarRequest
     * @param orderId
     * @return
     */
    @Slave
    QunarRequest getQunarRequest(String orderId) throws ZZKServiceException;

    /**
     *
     */
    @Master
    void add(QunarRequest qunarRequest) throws ZZKServiceException;
}
