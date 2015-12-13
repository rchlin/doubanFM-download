package org.rcl.douban;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by Lin on 2015/6/20.
 */
public class Info {
    @JSONField(name="user_info")
    private UserInfo userInfo;
    private Long r;

    public Long getR() {
        return r;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setR(Long r) {
        this.r = r;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
