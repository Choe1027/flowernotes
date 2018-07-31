package com.flowernotes.core;

import com.flowernotes.common.utils.StringUtil;
import com.flowernotes.core.bean.RoleBean;
import com.flowernotes.core.cache.CacheService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoreApplicationTests {

    @Autowired
    private CacheService cacheService;

    @Test
    public void contextLoads() {

        cacheService.set("test","1111");
        RoleBean roleBean = new RoleBean();
        roleBean.setName("testObj");
        cacheService.set("testObj",roleBean);
        cacheService.setMapValue("testMapValueString","testString","flajslfjaslfjas");
        cacheService.setMapValue("testMapValueObj","testObj",roleBean);
        System.out.println("test==="+StringUtil.toString(cacheService.get("test")));
        System.out.println("testObj==="+StringUtil.toString(cacheService.get("testObj")));
        System.out.println("testMapValueString==="+StringUtil.toString(cacheService.getMapValue("testMapValueString","testString")));
        System.out.println("testMapValueObj==="+StringUtil.toString(cacheService.getMapValue("testMapValueObj","testObj")));
    }

}
