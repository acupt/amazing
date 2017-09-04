package com.acupt.test.service;

import com.acupt.service.BiubiuService;
import com.acupt.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by liujie on 2017/8/31.
 */
public class BiubiuServiceTest extends BaseTest {

    @Resource
    private BiubiuService biubiuService;

    @Test
    public void testGet() {
        print(biubiuService.getMaxId());
        print(biubiuService.getById(1));
        print(biubiuService.biu());
    }

}
