package com.Xieyuchen.enery;

import java.awt.Image;
import java.util.Date;

//障碍物的抽象父类  
public abstract class Enery {

    public int x,y;
    public int width,height;
    public Image img;
    public String name;
    public Enery(int x, int y, Image img,String name) {
        this.x = x;
        this.y = y;
        this.img=img;
        this.name = name;
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);

    }
}