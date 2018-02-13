package com.Xieyuchen.Util;

import com.Xieyuchen.mario.GameFrame;
import com.Xieyuchen.mario.Test;

import java.awt.*;
import java.io.*;

public class UI {
    public  Font getSelfDefinedFont(String filename)
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
            font = font.deriveFont(Font.BOLD, 25);
        }
        else
        {
            font = font.deriveFont(Font.PLAIN, 5);
        }
        return font;
    }

    public void printInfo(Graphics g, Font font,String str, int x,int y,Color color) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(str, x, y);

    }
}
