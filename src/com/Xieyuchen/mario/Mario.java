package com.Xieyuchen.mario;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import javax.swing.ImageIcon;


import com.Xieyuchen.Util.Sound;
import com.Xieyuchen.enery.Enery;

//自己的角色类
public class Mario extends Enery implements Runnable {

    public GameFrame gf;

    public double jumpHeigh = 1;//重力
    public int coinCount;
    public boolean jumpFlag = true;
    public boolean jumpAction = false;
    public String name;
    //马里奥的坐标
    public int x = 0 + 16 * 5, y = 224 - 16 - 2 * 16 - 1 - 1 - 5 - 9;
    //马里奥的速度
    public int xspeed = 3/*小的速度*/, yspeed = 12;//y速度 小
    //马里奥的图片
    public Image img = new ImageIcon("src/images/mario_right.png").getImage();
    //马里奥的宽高
    public int width = img.getWidth(null), height = img.getHeight(null);
    //马里奥的状态 0=dead 1=small 2=big
    public int status = 1;
    public int STATUS_DEAD = 0;
    public int STATUS_SMALL = 1;
    public int STATUS_BIG = 2;
    //马里奥移动速度
    public int moveSpeed = 16 * 2;
    public boolean speedFlag = false;
    public boolean eatMushroom = false;//

    //public boolean hitsmallBgm = false;//跳是否被顶
    public Sound sound = new Sound();


    public int eatId;//蘑菇
    private int actionTime = 0;
    public boolean actionLeft, actionRight = true, actionUp, actionDown;

    public boolean left = false, right = false, down = false, up = false;

    public String Dir_Up = "Up", Dir_Left = "Left", Dir_Right = "Right", Dir_Down = "Down";


    public Mario(int x, int y, Image img, String name, GameFrame gf) {
        super(x, y, img, name);
        this.gf = gf;

    }

    public void Action() {
        if (!gf.physics.isGravity) {
            if (right) {
                if (this.status == STATUS_SMALL) {
                    this.img = new ImageIcon("src/images/mario_right" + actionTime / 50 % 3 + ".png").getImage();//50ms

                } else if (this.status == STATUS_BIG) {
                    this.img = new ImageIcon("src/images/bigmario_right" + actionTime / 50 % 3 + ".png").getImage();
                }
                actionTime += gf.flashTime;
                actionRight = true;
                actionLeft = false;
            }
            if (left) {
                if (this.status == STATUS_SMALL) {
                    this.img = new ImageIcon("src/images/mario_left" + actionTime / 50 % 3 + ".png").getImage();

                } else if (this.status == STATUS_BIG) {
                    this.img = new ImageIcon("src/images/bigmario_left" + actionTime / 50 % 3 + ".png").getImage();
                }
                actionTime += gf.flashTime;
                actionLeft = true;
                actionRight = false;
            }

            if (jumpAction) {//按键松开也是跳动作
                if (actionLeft) {
                    if (this.status == STATUS_SMALL) {
                        this.img = new ImageIcon("src/images/mario_jump_left.png").getImage();
                        //actionLeft = false;
                    } else if (this.status == STATUS_BIG) {
                        this.img = new ImageIcon("src/images/bigmario_jump_left.png").getImage();
                    }

                }
                if (actionRight) {
                    if (this.status == STATUS_SMALL) {
                        this.img = new ImageIcon("src/images/mario_jump_right.png").getImage();
                        //actionRight = false;
                    } else if (this.status == STATUS_BIG) {
                        this.img = new ImageIcon("src/images/bigmario_jump_right.png").getImage();
                    }

                }

            }
            if (jumpAction && right) {
                if (this.status == STATUS_SMALL) {
                    this.img = new ImageIcon("src/images/mario_jump_right.png").getImage();


                } else if (this.status == STATUS_BIG) {
                    this.img = new ImageIcon("src/images/bigmario_jump_right.png").getImage();
                }
                actionLeft = false;
                actionRight = true;
            }
            if (jumpAction && left) {
                if (this.status == STATUS_SMALL) {
                    this.img = new ImageIcon("src/images/mario_jump_left.png").getImage();

                } else if (this.status == STATUS_BIG) {
                    this.img = new ImageIcon("src/images/bigmario_jump_left.png").getImage();
                }
                actionLeft = false;
                actionRight = true;
            }
            if (down) {


                if (actionLeft) {
                    if (this.status == STATUS_BIG) {
                        this.img = new ImageIcon("src/images/bigmario_down_left.png").getImage();
                    }
                }
                if (actionRight) {
                    if (this.status == STATUS_BIG) {
                        this.img = new ImageIcon("src/images/bigmario_down_right.png").getImage();
                    }
                }
                if (!actionDown) {
                    if (this.status == STATUS_BIG) {
                        gf.mario.y += gf.mario.height;
                        gf.mario.height = gf.mario.img.getHeight(null);
                        gf.mario.y -= gf.mario.height;
                        actionDown = true;
                    }
                }

            }

        } else {
            //下落图找不到
        }


    }

    public void run() {
        while (true) {
            System.out.println("this is mario thread.");
            //向左走
            if (left) {
                //碰撞到了

                if (!gf.physics.hit(this, Dir_Left)) {
                    if (this.x >= 0) {
                        this.x -= this.xspeed;

                    }


                }


            }

            //向右走
            if (right) {


                if (!gf.physics.hit(this, Dir_Right)) {
                    //任人物向右移动
                    if (this.x < gf.GAME_FRAME_WIDTH / 2) {
                        this.x += this.xspeed;

                    }

                    if (this.x >= gf.GAME_FRAME_WIDTH / 2) {
                        //背景向左移动
                        gf.movs += this.xspeed;
                        //障碍物项左移动
                        for (int i = 0; i < gf.eneryList.size(); i++) {
                            Enery enery = gf.eneryList.get(i);
                            enery.x -= this.xspeed;
                        }

                    }

                }


            }

            //向上跳
            if (up) {

                if (jumpFlag && !gf.physics.isGravity) { //true && !false (start)

                    jumpFlag = false;
                    new Thread(() -> {
                        System.out.println("this is jump thread.");
                        jumpAction = true;//跳的动作不受按键影响
                        //跳音乐
                        sound.jumpBgm.play();
                        jump();
                        jumpFlag = true;
                        jumpAction = false;
                        if (gf.mario.actionLeft) {//跳完动作复位
                            if (gf.mario.status == gf.mario.STATUS_SMALL) {
                                gf.mario.img = new ImageIcon("src/images/mario_left.png").getImage();
                            } else if (gf.mario.status == gf.mario.STATUS_BIG) {
                                gf.mario.img = new ImageIcon("src/images/bigmario_left.png").getImage();
                            }

                        }
                        if (gf.mario.actionRight) {
                            if (gf.mario.status == gf.mario.STATUS_SMALL) {
                                gf.mario.img = new ImageIcon("src/images/mario_right.png").getImage();
                            } else if (gf.mario.status == gf.mario.STATUS_BIG) {
                                gf.mario.img = new ImageIcon("src/images/bigmario_right.png").getImage();
                            }
                        }
                    }).start();

                }

            }


            Action();
            try {
                Thread.sleep(moveSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //向上跳的函数
    private void jump() {

        double Length = this.yspeed;//临时
        while (this.yspeed > 0) {
            this.yspeed -= jumpHeigh;//减速
            this.y -= this.yspeed;//上升
            if (gf.physics.hit(this, Dir_Up) || gf.physics.hit(this, Dir_Left) || gf.physics.hit(this, Dir_Right)) {
                break;//碰到东西
            }
            try {
                Thread.sleep(2 * gf.flashTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (this.yspeed <= Length) {
            this.yspeed += jumpHeigh;//加速
            this.y += this.yspeed;//下降
            if (gf.physics.hit(this, Dir_Down)) {
                gf.physics.isGravity = false;
                gf.mario.y -= 10;

                break;
            }
            if (gf.physics.hit(this, Dir_Left)) {
                gf.physics.isGravity = false;
                gf.mario.x += 10;

                break;
            }
            if (gf.physics.hit(this, Dir_Right)) {
                gf.physics.isGravity = false;
                gf.mario.x -= 10;

                break;
            }

            try {
                Thread.sleep(2 * gf.flashTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.yspeed = (int) Length;
        if (this.status == STATUS_BIG) {
            this.xspeed = 4;
        }
        this.xspeed = 3;
    }


    //添加子弹
    public void addBoom() {
        Boom b = new Boom(gf.mario.x, gf.mario.y + 5, 5);
        if (gf.mario.actionLeft) b.speed = -2;
        if (gf.mario.actionRight) b.speed = 2;
        gf.boomList.add(b);
    }


}