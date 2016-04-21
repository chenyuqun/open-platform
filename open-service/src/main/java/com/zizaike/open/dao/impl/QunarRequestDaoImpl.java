package com.zizaike.open.dao.impl;

import com.zizaike.core.framework.exception.ZZKServiceException;
import com.zizaike.core.framework.mybatis.impl.GenericMyIbatisDao;
import com.zizaike.entity.open.QunarRequest;
import com.zizaike.open.dao.QunarRequestDao;
import org.springframework.stereotype.Repository;

/**
 * Project Name: code <br/>
 * Function:QunarRequestDaoImpl. <br/>
 * date: 2016/4/21 11:10 <br/>
 *
 * @author alex
 * @since JDK 1.7
 */
@Repository
public class QunarRequestDaoImpl extends GenericMyIbatisDao<QunarRequest, Integer> implements QunarRequestDao {

    private static final String NAMESPACE = "com.zizaike.open.dao.QunarRequestMapper.";

    @Override
    public QunarRequest getQunarRequest(String orderId) throws ZZKServiceException {
        QunarRequest result = this.getSqlSession().selectOne(NAMESPACE+"selectByPrimaryKey", orderId);
        return result;
    }

    @Override
    public void add(QunarRequest qunarRequest) throws ZZKServiceException {
        this.getSqlSession().insert(NAMESPACE+"insertOrUpdate", qunarRequest);
    }
}
