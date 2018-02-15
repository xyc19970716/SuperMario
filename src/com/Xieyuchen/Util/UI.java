package com.Xieyuchen.Util;


import com.Xieyuchen.mario.GameFrame;

import java.awt.*;
import java.io.*;
import java.util.Date;

public class UI {
    public Image uiCoinImage;
    public GameFrame gf;

    public UI(GameFrame gf) {
        this.gf = gf;
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
        //time
        g.drawString(String.valueOf(400 - ((int) (new Date().getTime() / 1000 - gf.startTime))), 255 - 8 * 7 + 8, 24 + 8);
    }


}
