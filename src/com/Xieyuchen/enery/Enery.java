package com.Xieyuchen.enery;

import java.awt.Image;

//障碍物的抽象父类  
public abstract class Enery {

    public int x,y;
    public int width,height;
    public Image img;
    public Enery(int x, int y, Image img) {
        this.x = x;
        this.y = y;
        this.img=img;
        this.width = img.getWidth(null);
        this.height = img.getHeight(null);

    }
}