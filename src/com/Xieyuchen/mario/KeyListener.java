package com.Xieyuchen.mario;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;


//键盘按下监听类
public class KeyListener extends KeyAdapter{

    public GameFrame gf;
    public boolean jumpFlag=true;

    public KeyListener(GameFrame gf) {
        this.gf=gf;
    }

    //键盘监听
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            //向右走
            case KeyEvent.VK_RIGHT:
                gf.mario.right=true;
                break;
            //向左走
            case KeyEvent.VK_LEFT:
                gf.mario.left=true;
                break;

            case 66:
                addBoom();
                break;

            //向上跳
            case KeyEvent.VK_SPACE:
                gf.mario.up=true;
                break;

        }
    }

    //添加子弹
    public void addBoom() {
        Boom b = new Boom(gf.mario.x,gf.mario.y+5,10);
        if(gf.mario.left) b.speed=-2;
        if(gf.mario.right) b.speed=2;
        gf.boomList.add(b);
    }

    //键盘释放监听
    public void keyReleased(KeyEvent e) {

        switch(e.getKeyCode()){
            //向右走
            case KeyEvent.VK_RIGHT:
                gf.mario.right = false;
                break;
            //向左走
            case KeyEvent.VK_LEFT:
                gf.mario.left = false;
                break;



            //向上跳
            case KeyEvent.VK_SPACE:
                gf.mario.up = false;
                break;
        }
    }

}
