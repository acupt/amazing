package com.acupt.test;

import com.acupt.amazing.AmazingApplication;
import com.acupt.util.GsonUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by liujie on 2017/8/14.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmazingApplication.class)
public class BaseTest {

    @Test
    public void testOut() {
        out("test out " + getClass());
    }

    protected void out(Object object) {
        System.out.println(GsonUtil.toJson(object));
    }
}
