package com.Xieyuchen.mario;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;

import com.Xieyuchen.enery.Enery;
import com.Xieyuchen.enery.Mushroom;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;

//自己的角色类
public class Mario extends Thread{

    public GameFrame gf;

    public boolean jumpFlag=true;

    //马里奥的坐标
    public int x=0+16*5,y= 224 - 16 - 2* 16-1-1 ;
    //马里奥的速度
    public int xspeed=2,yspeed=1;
    //马里奥的图片
    public  Image img = new ImageIcon("src/images/mario_right.png").getImage();
    //马里奥的宽高
    public  int width = img.getWidth(null),height = img.getHeight(null);
    //马里奥的状态 0=dead 1=small 2=big
    public int status=1;
    public int STATUS_DEAD = 0;
    public int STATUS_SMALL = 1;
    public int STATUS_BIG = 2;

    public boolean eatMushroom = false;//

    public int eatId;//蘑菇
    public int actionTime = 0;
    public boolean actionLeft,actionRight=true, actionUp,actionDown;

    public boolean left=false,right=false,down=false,up=false;

    public String Dir_Up="Up",Dir_Left="Left",Dir_Right="Right",Dir_Down="Down";


    public Mario (GameFrame gf) {
        this.gf=gf;
        this.Gravity();
    }

    public void Action() {
        if (!isGravity) {
            if (right) {
                if (this.status == STATUS_SMALL) {
                    this.img = new ImageIcon("src/images/mario_right" + actionTime % 3 + ".png").getImage();

                } else if (this.status == STATUS_BIG) {
                    this.img = new ImageIcon("src/images/bigmario_right" + actionTime % 3 + ".png").getImage();
                }
                actionTime++;
                actionRight = true;
                actionLeft = false;
            }
            if (left) {
                if (this.status == STATUS_SMALL) {
                    this.img =  new ImageIcon("src/images/mario_left" + actionTime % 3 + ".png").getImage();

                } else if (this.status == STATUS_BIG) {
                    this.img = new ImageIcon("src/images/bigmario_left" + actionTime % 3 + ".png").getImage();
                }
                actionTime++;
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
                        this.img= new ImageIcon("src/images/mario_jump_left.png").getImage();
                        //actionLeft = false;
                    } else if (this.status == STATUS_BIG) {
                        this.img= new ImageIcon("src/images/bigmario_jump_left.png").getImage();
                    }

                }
                if (actionRight) {
                    if (this.status == STATUS_SMALL) {
                        this.img = new ImageIcon("src/images/mario_jump_right.png").getImage();
                        //actionRight = false;
                    } else if (this.status == STATUS_BIG) {
                        this.img= new ImageIcon("src/images/bigmario_jump_right.png").getImage();
                    }

                }

            }
            if (up && right) {
                if (this.status == STATUS_SMALL) {
                    this.img= new ImageIcon("src/images/mario_jump_right.png").getImage();


                } else if (this.status == STATUS_BIG) {
                    this.img= new ImageIcon("src/images/bigmario_jump_right.png").getImage();
                }
                actionRight = false;
            }
            if (up && left) {
                if (this.status == STATUS_SMALL) {
                    this.img= new ImageIcon("src/images/mario_jump_left.png").getImage();

                } else if (this.status == STATUS_BIG) {
                    this.img= new ImageIcon("src/images/bigmario_jump_left.png").getImage();
                }
                actionLeft = false;

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
        }
        else {
            //下落图找不到
        }



    }

    public void run(){
        while(true){
            System.out.println("this is mario thread.");
            //向左走
            if(left){
                //碰撞到了
                if(hit(Dir_Left)){
                    this.xspeed=0;
                   // this.img=new ImageIcon("src/images/mario_run0.png").getImage();
                }

                if(this.x>=0){
                    this.x-=this.xspeed;
                    //this.img=new ImageIcon("src/images/mario_run0.png").getImage();
                }

                this.xspeed=2;
            }

            //向右走
            if(right){

                if(hit(Dir_Right)){
                    this.xspeed=0;
                    //this.img=new ImageIcon("src/images/mario_run0.png").getImage();
                }
                //任人物向右移动
                if(this.x<gf.GAME_FRAME_WIDTH / 2){
                    this.x+=this.xspeed;
                    //this.img=new ImageIcon("src/images/mario_run0.png").getImage();
                }

                if(this.x>=gf.GAME_FRAME_WIDTH / 2){
                    //背景向左移动
                    gf.bg.x-=this.xspeed;
                    //障碍物项左移动
                    for (int i = 0; i <gf.eneryList.size(); i++) {
                        Enery enery = gf.eneryList.get(i);
                        enery.x-=this.xspeed;
                    }
                    //this.img=new ImageIcon("src/images/mario_run0.png").getImage();
                }
                this.xspeed=2;


            }

            //向上跳
            if(up){

                if(jumpFlag && !isGravity){ //true && !false (start)

                    jumpFlag=false;
                   new Thread(() -> {
                        System.out.println("this is jump thread.");
                        jump();
                        jumpFlag=true;

                    }).start();

                }

            }


            Action();
            try {
                this.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //向上跳的函数
    public void jump(){
        int jumpHeigh=0;
        for (int i = 0; i < 60; i++) {
            gf.mario.y-=this.yspeed;
            jumpHeigh++;
            if(hit(Dir_Up)){
                break;
            }
            try {
                Thread.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i <jumpHeigh; i++) {
            gf.mario.y+=this.yspeed;
            if(hit(Dir_Down)){
                this.yspeed=0;
            }
            try {
                Thread.sleep(8);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        this.yspeed=1;//还原速度

    }

    //检测碰撞
    public boolean hit(String dir){
        Rectangle myrect = new Rectangle(this.x,this.y,this.width,this.height);
        Rectangle rect =null;

        for (int i = 0; i < gf.eneryList.size(); i++) {
            Enery enery = gf.eneryList.get(i);
            if (eatMushroom && enery.name.equals("CreateBigMushroom") && eatId == enery.Id) {//找到吃掉的蘑菇删除它
                gf.eneryList.remove(enery);
                eatMushroom = false;
            }
            if(dir.equals("Left")){
                rect = new Rectangle(enery.x+2,enery.y,enery.width,enery.height);
            }
            else if(dir.equals("Right")){
                rect = new Rectangle(enery.x-1,enery.y,enery.width,enery.height);
            }

            else if(dir.equals("Up")){
                rect = new Rectangle(enery.x,enery.y+1,enery.width,enery.height);

            }else if(dir.equals("Down")){
                rect = new Rectangle(enery.x,enery.y-2,enery.width,enery.height);
            }
            //碰撞检测
            if(myrect.intersects(rect)){
                if (enery.name=="coin"&& dir.equals("Up")) {  //创建蘑菇
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaMushroom");
                    Mushroom createBigMushroom = new Mushroom(enery.x, enery.y, new ImageIcon("src/images/createBigMushroom.png").getImage(), "CreateBigMushroom");
                    createBigMushroom.x = enery.x;
                    createBigMushroom.y = enery.y - createBigMushroom.height;
                    createBigMushroom.Id = gf.eneryList.size();
                    gf.eneryList.add(createBigMushroom);

                }
                if (enery.name=="CreateBigMushroom" && (dir.equals("Left") ||dir.equals("Right") ||dir.equals("Down"))) { //吃蘑菇变大
                    eatMushroom = true;
                    eatId = enery.Id;
                    gf.mario.status=STATUS_BIG;
                    gf.mario.x = gf.mario.x + gf.mario.width;
                    gf.mario.y = gf.mario.y + gf.mario.height;
                    gf.mario.img = new ImageIcon("src/images/bigmario_right.png").getImage();
                    gf.mario.width = gf.mario.img.getWidth(null);
                    gf.mario.height = gf.mario.img.getHeight(null);
                    gf.mario.x = gf.mario.x - gf.mario.width;
                    gf.mario.y = gf.mario.y - gf.mario.height;


                }
                return true;
            }
        }

        return false;
    }

    //检查是否贴地
    public boolean isGravity=false;//不会掉

    public void Gravity(){
        new Thread(() -> {

            while(true){
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(!jumpFlag){

                }
                System.out.println("this is gravity thread.");
                while(true){
                   if(!jumpFlag){
                        break;
                    }

                   if(hit(Dir_Down)){
                       isGravity=false;
                       break;
                    }

                    /*if(y>=224){


                    }*/
                    else if (y<=224){
                        isGravity=true;
                        y+=yspeed;
                    }
                    if (y>224) {
                        Dead dead = new Dead(this.gf);

                        dead.showDead(gf.mario.x,gf.mario.y);
                    }

                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    //添加子弹
    public void addBoom() {
        Boom b = new Boom(gf.mario.x,gf.mario.y+5,5);
        if(gf.mario.actionLeft) b.speed=-2;
        if(gf.mario.actionRight) b.speed=2;
        gf.boomList.add(b);
    }



}