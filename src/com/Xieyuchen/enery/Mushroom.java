package com.Xieyuchen.enery;


        import com.Xieyuchen.mario.GameFrame;
        import com.Xieyuchen.mario.Mario;

        import javax.swing.*;
        import java.awt.*;

public class Mushroom extends Mario {

    public int xspeed = 3;
    public Image img;
    public boolean isGravity = false;
    public int Gravity = 1;

    public Mushroom(int x, int y, Image img, String name, GameFrame gf) {
        super(x, y, img, name, gf);
        this.img = new ImageIcon("src/images/createBigMushroom.png").getImage();

    }

    public void createMushroom(Mushroom mushroom, Enery enery) {

        System.out.println("createMushroom");

    }

    @Override
    public void run() {
        while (true) {
            System.out.println("Bigmushroom........................................");
        }
    }
}
