package org.rcl.douban;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lin on 2015/6/19.
 */
public class Playlist {
    private Long r;
    private Long is_show_quick_start;
    private List<Song> song = new ArrayList<Song>();
    public Long getR(){
        return this.r;
    }
    public void setR(Long r){
        this.r = r;
    }
    public Long getIs_show_quick_start(){
        return this.is_show_quick_start;
    }
    public void setIs_show_quick_start(Long is_show_quick_start){
        this.is_show_quick_start = is_show_quick_start;
    }
    public List<Song> getSong(){
        return this.song;
    }
    public void setSong(List<Song> song){
        this.song = song;
    }
}
