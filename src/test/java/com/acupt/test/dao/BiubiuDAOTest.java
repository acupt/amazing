package com.acupt.test.dao;

import com.acupt.dao.BiubiuDAO;
import com.acupt.entity.Biubiu;
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
        print(biubiuDAO.findOne(1L));
    }

    @Test
    public void testInsert() {
        Biubiu biubiuDO = new Biubiu();
        biubiuDO.setIp("127.0.0.1");
        biubiuDO.setAgent("me");
        biubiuDO.setContent("test insert");
        biubiuDO.setGmtCreated(new Date());
        print(biubiuDAO.save(biubiuDO));
    }

}
