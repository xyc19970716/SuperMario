package com.Xieyuchen.Util;


import com.Xieyuchen.mario.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;

public class UI extends JFrame{
    public Image uiCoinImage;
    public GameFrame gf;
    public Image titleImage = new ImageIcon("src/images/title.png").getImage();
    public Image optionImage = new ImageIcon("src/images/option.png").getImage();
    public boolean startGame;
    public boolean startCountTime;
    public int startTime;
    public boolean Play = true;
    public boolean Quit = false;
    public boolean showLiveUI;
    public int showLiveUIflash;
    public int getShowLiveUItime;
    public UI(GameFrame gf) {
        this.gf = gf;

        new Thread(()-> {
            while (true) {
                repaint();
            }

        }).start();
    }

    //public int periodTime;
    public Font getSelfDefinedFont(String filename, int size) {
        Font font;
        //字体文件在conf下面
        String filepath = "/C:/Users/46514/IdeaProjects/SuperMario/src/" + filename;
        File file = new File(filepath);
        try {
            FileInputStream fi = new FileInputStream(file);
            BufferedInputStream fb = new BufferedInputStream(fi);
            font = Font.createFont(Font.TRUETYPE_FONT, fb);
        } catch (FontFormatException | IOException e) {
            return null;
        }
        if ("宋体.ttf".equals(filename)) {
            font = font.deriveFont(Font.BOLD, size);
        } else {
            font = font.deriveFont(Font.PLAIN, size);
        }
        return font;
    }

    public void printInfo(Graphics g, Font font, Color color) {

        g.setColor(color);
        g.setFont(font);
        g.drawString("MARIO", 16 * 2, 24);
        //score
        if (gf.mario.score < 10) {
            g.drawString("00000" + String.valueOf(gf.mario.score), 16 * 2, 24 + 8);
        } else if (gf.mario.score < 100) {
            g.drawString("0000" + String.valueOf(gf.mario.score), 16 * 2, 24 + 8);
        } else if (gf.mario.score < 1000) {
            g.drawString("000" + String.valueOf(gf.mario.score), 16 * 2, 24 + 8);
        } else if (gf.mario.score < 10000) {
            g.drawString("00" + String.valueOf(gf.mario.score), 16 * 2, 24 + 8);
        } else if (gf.mario.score < 100000) {
            g.drawString("0" + String.valueOf(gf.mario.score), 16 * 2, 24 + 8);
        } else {
            g.drawString(String.valueOf(gf.mario.score), 16 * 2, 24 + 8);
        }
        //g.drawString("000000", 16 * 2, 24 + 8);
        //UI小金币图标
        g.drawImage(uiCoinImage, 16 * 2 + 16 * 2 + 8 * 4 + 1, 24, 5, 8, null);
        g.drawString("X", 16 * 2 + 16 * 2 + 8 * 4 + 8, 24 + 8);
        //coincount
        if (gf.mario.coinCount < 10) {
            g.drawString("0" + String.valueOf(gf.mario.coinCount), 16 * 2 + 16 * 2 + 8 * 4 + 8 + 8, 24 + 8);
        } else {
            g.drawString(String.valueOf(gf.mario.coinCount), 16 * 2 + 16 * 2 + 8 * 4 + 8 + 8, 24 + 8);
        }

        g.drawString("WORLD", 255 - 16 * 7, 24);
        g.drawString("1-1", 255 - 16 * 7 + 8, 24 + 8);
        g.drawString("TIME", 255 - 8 * 7, 24);

        if (!startGame) {//游戏开始界面
            //g.drawString("400", 255 - 8 * 7 + 8, 24 + 8);
            g.drawImage(titleImage, 16*2+8,24 + 8,titleImage.getWidth(null),titleImage.getHeight(null),null);
            g.drawImage(gf.mario.img, 2*16+8, gf.GAME_FRAME_HEIGHT-3*16, gf.mario.width, gf.mario.height, null);
            Color c=g.getColor();
            g.setColor(Color.PINK);
            g.drawString("@2018 xyc19970716",16 * 2 + 16 * 2 + 8 * 2,24+8 + 8+titleImage.getHeight(null));
            g.setColor(c);
            g.drawString("1 PLAYER GAME",16 * 2 + 16 * 2 + 8 * 2+8,24+8 + 8+16+2+titleImage.getHeight(null));
            g.drawString("2 QUIT GAME",16 * 2 + 16 * 2 + 8 * 2+8,24+8 + 8+16+8+8+titleImage.getHeight(null));
            if (Play) {
                g.drawImage(optionImage,16*2+16*2+8 ,24+8+2+16+titleImage.getHeight(null),8,8,null);
            } else if (Quit) {
                g.drawImage(optionImage,16*2+16*2+8 ,24+8+16+16+titleImage.getHeight(null),8,8,null);
            }
        } else if (showLiveUI) {//按下回车秀一波生命
            g.drawString("WORLD 1-1",16 * 2 + 16 * 2 + 8 * 2+8,80);
            g.drawImage(gf.mario.img,16 * 2 + 16 * 2 + 8 * 2+8+8,80+24,gf.mario.width,gf.mario.height,null);
            g.drawString("x",16 * 2 + 16 * 2 + 8 * 2+8+16+16,80+24+12);
            g.drawString(String.valueOf(gf.mario.liveCount),16 * 2 + 16 * 2 + 8 * 2+8+16+16+24,80+24+12);
            showLiveUIflash+=gf.flashTime;
            getShowLiveUItime = showLiveUIflash/16;
            if (getShowLiveUItime > 125) {//2
                showLiveUI = false;//关闭生命界面
                startCountTime = true;//开始计时
                //开始播放背景音乐
                gf.sound.Bgm.loop();
            }

        } else {
            //time
            if (startCountTime) {
                startTime = (int)(new Date().getTime() / 1000);
                startCountTime=false;
            }
            g.drawString(String.valueOf(400 - ((int) (new Date().getTime() / 1000 - startTime))), 255 - 8 * 7 + 8, 24 + 8);
        }



    }


    public void WeclomeUI() {

    }
    public void paint(Graphics g) {
        //利用双缓冲画背景图片和马里奥
        //BufferedImage bi = (BufferedImage) this.createImage(this.getSize().width, this.getSize().height);
        //Graphics big = bi.getGraphics();
        g.setFont(gf.font);
        g.setColor(Color.WHITE);

        g.drawString("hello",100,100);
        //g.drawImage(bi,0,0,null);
    }

}
