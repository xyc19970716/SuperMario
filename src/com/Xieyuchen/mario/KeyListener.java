package com.Xieyuchen.mario;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import javax.swing.ImageIcon;


//键盘按下监听类
public class KeyListener extends KeyAdapter {

    private GameFrame gf;
    public boolean jumpFlag = true;

    public int startTime;
    KeyListener(GameFrame gf) {
        this.gf = gf;
    }

    //键盘监听
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            //向右走
            case KeyEvent.VK_RIGHT:
                if (!gf.ui.startGame) {

                } else {
                    gf.mario.right = true;
                }

                break;
            //向左走
            case KeyEvent.VK_LEFT:
                if (!gf.ui.startGame) {

                } else {
                    gf.mario.left = true;
                }


                break;

            case KeyEvent.VK_Z: //子弹
                if (!gf.ui.startGame) {

                } else {
                    gf.mario.addBoom();
                }

                break;

            case KeyEvent.VK_X: //加速
                if (!gf.ui.startGame) {

                } else {
                    if (!gf.mario.speedFlag) { //初始为false，按下可以加速。一直按的话，flag为正。无法再次加速
                        gf.mario.moveSpeed /= 2;
                    }
                    gf.mario.speedFlag = true;
                }

                break;

            //下蹲（大）
            case KeyEvent.VK_DOWN:
                if (!gf.ui.startGame) {
                    gf.ui.Play=false;
                    gf.ui.Quit=true;
                } else {
                    if (gf.mario.status == gf.mario.STATUS_BIG || gf.mario.status == gf.mario.STATUS_BIGFIRE) {
                        gf.mario.down = true;
                    }
                }

                break;


            case KeyEvent.VK_UP:
                if (!gf.ui.startGame) {
                    gf.ui.Play=true;
                    gf.ui.Quit=false;
                }
                break;

            //向上跳
            case KeyEvent.VK_SPACE:
                if (!gf.ui.startGame) {

                } else {
                    gf.mario.up = true;
                }

                break;

            //按esc退出
            case KeyEvent.VK_ESCAPE:
                System.exit(0);
                break;

            case KeyEvent.VK_ENTER:
                if ((!gf.ui.startGame) && gf.ui.Play) {
                    gf.ui.startGame=true;//关闭开始界面
                    //gf.ui.startCountTime = true;
                    gf.ui.showLiveUI=true;//打开生命界面
                } else if ((!gf.ui.startGame) && gf.ui.Quit) {
                    System.exit(0);
                }
        }
    }


    //键盘释放监听
    public void keyReleased(KeyEvent e) {

        switch (e.getKeyCode()) {
            //向右走
            case KeyEvent.VK_RIGHT:
                if (gf.ui.startGame) {
                    gf.mario.right = false;
                    if (gf.mario.status == gf.mario.STATUS_SMALL) {
                        gf.mario.img = new ImageIcon("src/images/mario_right.png").getImage();
                    } else if (gf.mario.status == gf.mario.STATUS_BIG) {
                        gf.mario.img = new ImageIcon("src/images/bigmario_right.png").getImage();
                    } else if (gf.mario.status == gf.mario.STATUS_BIGFIRE) {
                        gf.mario.img = new ImageIcon("src/images/bigfiremario_right.png").getImage();
                    }
                }


                break;
            //向左走
            case KeyEvent.VK_LEFT:
                if (gf.ui.startGame) {
                    gf.mario.left = false;
                    if (gf.mario.status == gf.mario.STATUS_SMALL) {
                        gf.mario.img = new ImageIcon("src/images/mario_left.png").getImage();
                    } else if (gf.mario.status == gf.mario.STATUS_BIG) {
                        gf.mario.img = new ImageIcon("src/images/bigmario_left.png").getImage();
                    } else if (gf.mario.status == gf.mario.STATUS_BIGFIRE) {
                        gf.mario.img = new ImageIcon("src/images/bigfiremario_left.png").getImage();
                    }
                }


                break;

            case KeyEvent.VK_X: //减速
                if (gf.ui.startGame) {
                    if (gf.mario.speedFlag) { //按键按下后，flag为正,释放后才可减速。flag设置为false。下次再按可加速。
                        gf.mario.moveSpeed *= 2;
                    }
                    gf.mario.speedFlag = false;
                }

                break;

            //向上跳
            case KeyEvent.VK_SPACE:
                if (gf.ui.startGame) {
                    gf.mario.up = false;
                }


                break;

            //下蹲
            case KeyEvent.VK_DOWN:
                if (gf.ui.startGame) {
                    if (gf.mario.status == gf.mario.STATUS_BIG) {
                        gf.mario.down = false;
                        gf.mario.actionDown = false;
                        if (gf.mario.actionLeft) {
                            gf.mario.img = new ImageIcon("src/images/bigmario_left.png").getImage();
                        }
                        if (gf.mario.actionRight) {
                            gf.mario.img = new ImageIcon("src/images/bigmario_right.png").getImage();
                        }
                        gf.mario.y += gf.mario.height;
                        gf.mario.height = gf.mario.img.getHeight(null);
                        gf.mario.y -= gf.mario.height;
                    } else if (gf.mario.status == gf.mario.STATUS_BIGFIRE) {
                        gf.mario.down = false;
                        gf.mario.actionDown = false;
                        if (gf.mario.actionLeft) {
                            gf.mario.img = new ImageIcon("src/images/bigfiremario_left.png").getImage();
                        }
                        if (gf.mario.actionRight) {
                            gf.mario.img = new ImageIcon("src/images/bigfiremario_right.png").getImage();
                        }
                        gf.mario.y += gf.mario.height;
                        gf.mario.height = gf.mario.img.getHeight(null);
                        gf.mario.y -= gf.mario.height;
                    }
                }

                break;
        }
    }

}
