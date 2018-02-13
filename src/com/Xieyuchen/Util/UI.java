package com.Xieyuchen.Util;

import com.Xieyuchen.mario.GameFrame;
import com.Xieyuchen.mario.Test;

import java.awt.*;
import java.io.*;

public class UI {
    public  Font getSelfDefinedFont(String filename, int size)
    {
        Font font = null;
        //字体文件在conf下面
        String filepath ="/C:/Users/46514/IdeaProjects/SuperMario/src/" + filename;
        File file = new File(filepath);
        try
        {
            FileInputStream fi = new FileInputStream(file);
            BufferedInputStream fb = new BufferedInputStream(fi);
            font = Font.createFont(Font.TRUETYPE_FONT, fb);
        }
        catch (FontFormatException e)
        {
            return null;
        }
        catch (FileNotFoundException e)
        {
            return null;
        }
        catch (IOException e)
        {
            return null;
        }
        if("宋体.ttf".equals(filename))
        {
            font = font.deriveFont(Font.BOLD, size);
        }
        else
        {
            font = font.deriveFont(Font.PLAIN, size);
        }
        return font;
    }

    public void printInfo(Graphics g, Font font,String str,Color color,String period) {
        g.setColor(color);
        g.setFont(font);
        g.drawString("MARIO", 16*2, 24);
        g.drawString("000000",16*2, 24+8);
        g.drawString("O", 16*2 + 16*2 + 8*4, 24 + 8);
        g.drawString("X", 16*2 + 16*2 + 8*4 + 8, 24 + 8);
        g.drawString("00", 16*2 + 16*2 + 8*4 + 8+8, 24 + 8);
        g.drawString("WORLD", 255 - 16*7, 24);
        g.drawString("1-1", 255 - 16*7+8, 24+8);
        g.drawString("TIME", 255 - 8*7, 24);
        g.drawString(period,255 - 8 * 7 + 8, 24 + 8);
    }


}
