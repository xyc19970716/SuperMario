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
        gf.sound.Bgm.loop();

        gf.initFrame();

    }
}
