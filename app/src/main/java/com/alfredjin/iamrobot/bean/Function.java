package com.alfredjin.iamrobot.bean;

/**
 * Created by AlfredJin on 2017/4/4.
 */

public class Function {

    private String song;
    private String singer;

    public Function() {
    }

    public Function(String song, String singer) {
        this.song = song;
        this.singer = singer;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }
}
