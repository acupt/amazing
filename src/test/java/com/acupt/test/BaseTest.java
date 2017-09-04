package com.acupt.test;

import com.acupt.amazing.AmazingApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().create();

    @Test
    public void testOut() {
        print("test out " + getClass());
    }

    protected void print(Object object) {
        System.err.println(gson.toJson(object));
    }
}
