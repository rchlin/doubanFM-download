package org.rcl.douban;

/**
 * Created by Lin on 2015/6/20.
 */
public class UserInfo {
    private PlayRecord play_record;
    private String name;
    private String uid;
    private String url;

    public PlayRecord getPlay_record() {
        return play_record;
    }

    public String getName() {
        return name;
    }

    public String getUid() {
        return uid;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlay_record(PlayRecord play_record) {
        this.play_record = play_record;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
