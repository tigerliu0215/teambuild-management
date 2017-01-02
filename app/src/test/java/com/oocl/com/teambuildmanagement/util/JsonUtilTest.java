package com.oocl.com.teambuildmanagement.util;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author：Jonas Yu on 2017/1/2 00:54
 * Description:
 */
public class JsonUtilTest {

    @Test
    public void toJsonTest(){
        Map<String,Integer> testMap = new HashMap<String,Integer>();
        testMap.put("test",1);
        testMap.put("test2",2);
        testMap.put("test3",3);
        List<Object> list = new ArrayList<>();
        list.add(testMap);
        list.add("123");
        String resultStr = JsonUtil.toJson(list);
        Assert.assertNotNull(resultStr);
        String resultStr2 = JsonUtil.toJson(null);
        Assert.assertEquals(resultStr2,"null");

        list.add(new Date());
        String resultStr3 = JsonUtil.toJson(list);
        System.out.println(resultStr3);
    }

    @Test
    public void fromJsonTest(){

    }
}
