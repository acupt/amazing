package com.acupt.test.dao;

import com.acupt.dao.BiubiuDAO;
import com.acupt.domain.Biubiu;
import com.acupt.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by liujie on 2017/8/14.
 */
public class BiubiuDAOTest extends BaseTest {

    @Resource
    private BiubiuDAO biubiuDAO;

    @Test
    public void testGet() {
        out(biubiuDAO.findOne(1L));
    }

    @Test
    public void testInsert() {
        Biubiu biubiuDO = new Biubiu();
        biubiuDO.setIp("127.0.0.1");
        biubiuDO.setAgent("mac");
        biubiuDO.setContent("hello,world!");
        biubiuDO.setGmtCreated(new Date());
        out(biubiuDAO.save(biubiuDO));
    }

}
