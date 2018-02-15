package com.Xieyuchen.Util;


import com.Xieyuchen.mario.GameFrame;

import java.awt.*;
import java.io.*;

public class UI {
    public Image uiCoinImage;
    //public GameFrame gf;
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

    public void printInfo(Graphics g, Font font, String str, Color color, int periodTime, int coinCount) {

        g.setColor(color);
        g.setFont(font);
        g.drawString("MARIO", 16 * 2, 24);
        g.drawString("000000", 16 * 2, 24 + 8);
        //UI小金币图标
        g.drawImage(uiCoinImage, 16 * 2 + 16 * 2 + 8 * 4 + 1, 24, 5, 8, null);
        g.drawString("X", 16 * 2 + 16 * 2 + 8 * 4 + 8, 24 + 8);
        if (coinCount < 10) {
            g.drawString(0 + String.valueOf(coinCount), 16 * 2 + 16 * 2 + 8 * 4 + 8 + 8, 24 + 8);
        } else {
            g.drawString(String.valueOf(coinCount), 16 * 2 + 16 * 2 + 8 * 4 + 8 + 8, 24 + 8);
        }

        g.drawString("WORLD", 255 - 16 * 7, 24);
        g.drawString("1-1", 255 - 16 * 7 + 8, 24 + 8);
        g.drawString("TIME", 255 - 8 * 7, 24);

        g.drawString(String.valueOf(periodTime), 255 - 8 * 7 + 8, 24 + 8);
    }


}
