package org.rcl.douban;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Lin on 2015/6/20.
 */
public class InfoTest {
    @Test
    public void testDecodeJSON() throws Exception{
        String jsonString = "{\"user_info\":{\"ck\":\"vwRq\",\"play_record\":{\"fav_chls_count\":0,\"liked\":111,\"banned\":33,\"played\":5769},\"is_new_user\":0,\"uid\":\"60900512\",\"third_party_info\":null,\"url\":\"http:\\/\\/www.douban.com\\/people\\/60900512\\/\",\"is_dj\":false,\"id\":\"60900512\",\"is_pro\":false,\"name\":\"dou_rcl\"},\"r\":0}";
        Info info = JSON.parseObject(jsonString, Info.class);
        String name = info.getUserInfo().getName();
        assertEquals(name, "dou_rcl");
    }
}