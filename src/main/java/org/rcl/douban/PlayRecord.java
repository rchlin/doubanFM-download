package org.rcl.douban;

/**
 * Created by Lin on 2015/6/20.
 */
public class PlayRecord {
    private Long fav_chls_count;
    private Long liked;
    private Long banned;
    private Long played;

    public Long getBanned() {
        return this.banned;
    }

    public Long getFav_chls_count() {
        return this.fav_chls_count;
    }

    public Long getLiked(){
        return this.liked;
    }

    public Long getPlayed(){
        return this.played;
    }

    public void setFav_chls_count(Long fav_chls_count) {
        this.fav_chls_count = fav_chls_count;
    }

    public void setBanned(Long banned) {
        this.banned = banned;
    }

    public void setLiked(Long liked) {
        this.liked = liked;
    }

    public void setPlayed(Long played) {
        this.played = played;
    }
}
