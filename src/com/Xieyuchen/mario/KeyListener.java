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

            case KeyEvent.VK_Z:
                gf.mario.addBoom();
                break;

            //向上跳
            case KeyEvent.VK_SPACE:
                gf.mario.up=true;
                break;

            //按esc退出
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;
        }
    }



    //键盘释放监听
    public void keyReleased(KeyEvent e) {

        switch(e.getKeyCode()){
            //向右走
            case KeyEvent.VK_RIGHT:
                gf.mario.right = false;
                if (gf.mario.status == gf.mario.STATUS_SMALL) {
                    gf.mario.img=new ImageIcon("src/images/mario_right.png").getImage();
                } else if (gf.mario.status == gf.mario.STATUS_BIG) {
                    gf.mario.img=new ImageIcon("src/images/bigmario_right.png").getImage();
                }

                break;
            //向左走
            case KeyEvent.VK_LEFT:
                gf.mario.left = false;
                if (gf.mario.status == gf.mario.STATUS_SMALL) {
                    gf.mario.img=new ImageIcon("src/images/mario_left.png").getImage();
                } else if (gf.mario.status == gf.mario.STATUS_BIG) {
                    gf.mario.img=new ImageIcon("src/images/bigmario_left.png").getImage();
                }

                break;



            //向上跳
            case KeyEvent.VK_SPACE:
                gf.mario.up = false;
                if (gf.mario.actionLeft) {
                    if (gf.mario.status == gf.mario.STATUS_SMALL) {
                        gf.mario.img =  new ImageIcon("src/images/mario_left.png").getImage();
                    } else if (gf.mario.status == gf.mario.STATUS_BIG) {
                        gf.mario.img= new ImageIcon("src/images/bigmario_left.png").getImage();
                    }

                }
                if (gf.mario.actionRight) {
                    if (gf.mario.status == gf.mario.STATUS_SMALL) {
                        gf.mario.img = new ImageIcon("src/images/mario_right.png").getImage();
                    } else if (gf.mario.status == gf.mario.STATUS_BIG) {
                        gf.mario.img=new ImageIcon("src/images/bigmario_right.png").getImage();
                    }
                }
                break;
        }
    }

}
