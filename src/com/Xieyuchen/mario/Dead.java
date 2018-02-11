package com.Xieyuchen.mario;

import javax.swing.*;
import java.awt.*;

public class Dead {

    public GameFrame gf;

    public Image marioDeadImage = new ImageIcon("src/images/mario_dead.png").getImage();

    public Dead(GameFrame gf) {
            this.gf=gf;
    }

    public void showDead(int x,int y) {
        gf.mario.img = marioDeadImage;
        for (int i = 0; i < 10000; i++) {
            gf.mario.y-=0.02;

        }
        for (int i = 0; i < 10000; i++) {
            gf.mario.y+=0.02;
        }
    }
}
