package com.Xieyuchen.mario;

import java.awt.*;
import javax.swing.ImageIcon;


import com.Xieyuchen.enery.Enery;

//自己的角色类
public class Mario extends Enery implements Runnable {

    private GameFrame gf;

    public double jumpHeigh = 1;//重力

    public boolean jumpFlag = true;
    public String name;
    //马里奥的坐标
    public int x = 0 + 16 * 5, y = 224 - 16 - 2 * 16 - 1 - 1;
    //马里奥的速度
    public int xspeed = 2/*小的速度*/, yspeed = 10;//y速度 小
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

    public int eatId;//蘑菇
    private int actionTime = 0;
    public boolean actionLeft, actionRight = true, actionUp, actionDown;

    public boolean left = false, right = false, down = false, up = false;

    public String Dir_Up = "Up", Dir_Left = "Left", Dir_Right = "Right", Dir_Down = "Down";


    public Mario(int x, int y, Image img, String name, GameFrame gf) {
        super(x, y, img, name);
        this.gf = gf;
        //gf.physics.Gravity();
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
        /*if (up && actionRight) {
            this.img= new ImageIcon("src/images/mario_jump_right.png").getImage();
            actionUp = true;
            actionRight = false;
        }
        if (up && actionLeft) {
            this.img= new ImageIcon("src/images/mario_jump_left.png").getImage();
            actionUp = true;
            actionLeft = false;
        }*/
            if (up) {
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
            if (up && right) {
                if (this.status == STATUS_SMALL) {
                    this.img = new ImageIcon("src/images/mario_jump_right.png").getImage();


                } else if (this.status == STATUS_BIG) {
                    this.img = new ImageIcon("src/images/bigmario_jump_right.png").getImage();
                }
                actionLeft = false;
                actionRight = true;
            }
            if (up && left) {
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
            // if (!up && !right && !left) {
            //if (actionLeft) {
            //this.img= new ImageIcon("src/images/mario_left.png").getImage();
            //actionLeft = false;
            // }
            //if (actionRight) {
            //this.img = new ImageIcon("src/images/mario_right.png").getImage();
            //actionRight = false;
            //}
            //this.img = new ImageIcon("src/images/mario_right.png").getImage();
            //}
            // actionUp=false;actionLeft=false;actionRight=false;
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
                /*if(hit(Dir_Left)){
                    this.xspeed=0;
                    this.x+=1;
                   // this.img=new ImageIcon("src/images/mario_run0.png").getImage();
                }*/
                if (!gf.physics.hit(this, Dir_Left)) {
                    if (this.x >= 0) {
                        this.x -= this.xspeed;
                        //this.img=new ImageIcon("src/images/mario_run0.png").getImage();
                    }

                    //if (this.status == STATUS_BIG) {
                       // this.xspeed = 4;
                    //}
                   // this.xspeed = 2;
                }


            }

            //向右走
            if (right) {

                /*if(hit(Dir_Right)){
                    this.xspeed=0;
                    this.x-=1;
                    //this.img=new ImageIcon("src/images/mario_run0.png").getImage();
                }*/
                if (!gf.physics.hit(this, Dir_Right)) {
                    //任人物向右移动
                    if (this.x < gf.GAME_FRAME_WIDTH / 2) {
                        this.x += this.xspeed;
                        //this.img=new ImageIcon("src/images/mario_run0.png").getImage();
                    }

                    if (this.x >= gf.GAME_FRAME_WIDTH / 2) {
                        //背景向左移动
                        gf.movs += this.xspeed;
                        //障碍物项左移动
                        for (int i = 0; i < gf.eneryList.size(); i++) {
                            Enery enery = gf.eneryList.get(i);
                            enery.x -= this.xspeed;
                        }
                        //this.img=new ImageIcon("src/images/mario_run0.png").getImage();
                    }
                    //if (this.status == STATUS_BIG) {
                        //this.xspeed = 4;
                    //}
                    //this.xspeed = 2;
                }


            }

            //向上跳
            if (up) {

                if (jumpFlag && !gf.physics.isGravity) { //true && !false (start)

                    jumpFlag = false;
                    new Thread(() -> {
                        System.out.println("this is jump thread.");
                        jump();
                        jumpFlag = true;

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
        /*if (this.status == STATUS_BIG) {
            Length = 80;
        } else {
            Length = 60;
        }*/
       // for (int i = 0; i < Length; i++) {

        while (this.yspeed>0) {
            this.yspeed -= jumpHeigh;//减速
            this.y-=this.yspeed;//上升
            //jumpHeigh++;
            if (gf.physics.hit(this, Dir_Up)/*||hit(Dir_Left) || hit(Dir_Right)||hit(Dir_Up)&&hit(Dir_Left)||hit(Dir_Up)&& hit(Dir_Right)*/) {
                break;//碰到东西
            }
            try {
                Thread.sleep(gf.flashTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (this.yspeed<=Length) {
            this.yspeed+=jumpHeigh;//加速
            this.y+=this.yspeed;//下降
            if (gf.physics.hit(this, Dir_Down)) {
                this.yspeed = 0;
                gf.physics.isGravity=false;
                gf.mario.y-=10;

                break;
            }
            try {
                Thread.sleep(gf.flashTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //for (int i = 0; i < jumpHeigh; i++) {

            //gf.mario.y += this.yspeed;




        //}
        //if (this.status == STATUS_BIG) {
            //this.yspeed = 4;
        //}
        //this.yspeed = 1;//还原速度
        this.yspeed = (int)Length;
   }


    //添加子弹
    public void addBoom() {
        Boom b = new Boom(gf.mario.x, gf.mario.y + 5, 5);
        if (gf.mario.actionLeft) b.speed = -2;
        if (gf.mario.actionRight) b.speed = 2;
        gf.boomList.add(b);
    }


}