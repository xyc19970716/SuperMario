package com.Xieyuchen.Util;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class Sound {
    public AudioClip jumpBgm;
    public AudioClip smallHitBgm;
    public AudioClip hitCoinBgm;
    public AudioClip hitPowerupBgm;
    public AudioClip eatPowerupBgm;

    {
        try {
            jumpBgm = Applet.newAudioClip(new File("src/sounds/跳.wav").toURL());
            smallHitBgm = Applet.newAudioClip(new File("src/sounds/顶砖石块,壳击墙或火球撞墙.wav").toURL());
            hitCoinBgm = Applet.newAudioClip(new File("src/sounds/金币.wav").toURL());
            hitPowerupBgm = Applet.newAudioClip(new File("src/sounds/顶出蘑菇,花或星.wav").toURL());
            eatPowerupBgm = Applet.newAudioClip(new File("src/sounds/吃到蘑菇或花.wav").toURL());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


}
