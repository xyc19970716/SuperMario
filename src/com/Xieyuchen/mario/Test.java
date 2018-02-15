package com.Xieyuchen.mario;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class Test {

    //主函数，程序入口
    public static void main(String[] args) throws Exception {
        GameFrame gf = new GameFrame();
        //背景音乐
        try {
            AudioClip Bgm = Applet.newAudioClip(new File("src/sounds/地上.wav").toURL());
            Bgm.loop();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        gf.initFrame();

    }
}
