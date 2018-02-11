package com.Xieyuchen.mario;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.Xieyuchen.enery.Enery;

//自己的角色类
public class Mario extends Thread{

    public GameFrame gf;

    public boolean jumpFlag=true;

    //马里奥的坐标
    public int x=0+16*5,y= 224 - 16 - 2* 16 ;
    //马里奥的速度
    public int xspeed=1,yspeed=1;
    //马里奥的图片
    public  Image img = new ImageIcon("src/images/mario_right.png").getImage();
    //马里奥的宽高
    public  int width = img.getWidth(null),height = img.getHeight(null);



    public int actionTime = 0;
    public boolean actionLeft,actionRight=true, actionUp,actionDown;

    public boolean left=false,right=false,down=false,up=false;

    public String Dir_Up="Up",Dir_Left="Left",Dir_Right="Right",Dir_Down="Down";


    public Mario (GameFrame gf) {
        this.gf=gf;
        this.Gravity();
    }

    public void Action() {
        if (right) {
            this.img = new ImageIcon("src/images/mario_right" + actionTime % 3 + ".png").getImage();
            actionTime++;
            actionRight = true;
            actionLeft = false;
        }
        if (left) {
            this.img =  new ImageIcon("src/images/mario_left" + actionTime % 3 + ".png").getImage();
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
                this.img= new ImageIcon("src/images/mario_jump_left.png").getImage();
                //actionLeft = false;
            }
            if (actionRight) {
                this.img = new ImageIcon("src/images/mario_jump_right.png").getImage();
                //actionRight = false;
            }

        }
        if (up && right) {
            this.img= new ImageIcon("src/images/mario_jump_right.png").getImage();

            actionRight = false;
        }
        if (up && left) {
            this.img= new ImageIcon("src/images/mario_jump_left.png").getImage();
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

    public void run(){
        while(true){
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

                this.xspeed=3;
            }

            //向右走
            if(right){

                if(hit(Dir_Right)){
                    this.xspeed=0;
                    //this.img=new ImageIcon("src/images/mario_run0.png").getImage();
                }
                //任人物向右移动
                if(this.x<gf.GAME_FRAME_WIDTH){
                    this.x+=this.xspeed;
                    //this.img=new ImageIcon("src/images/mario_run0.png").getImage();
                }

                if(this.x>=gf.GAME_FRAME_WIDTH){
                    //背景向左移动
                    gf.bg.x-=this.xspeed;
                    //障碍物项左移动
                    for (int i = 0; i <gf.eneryList.size(); i++) {
                        Enery enery = gf.eneryList.get(i);
                        enery.x-=this.xspeed;
                    }
                    //this.img=new ImageIcon("src/images/mario_run0.png").getImage();
                }
                this.xspeed=3;


            }

            //向上跳
            if(up){

                if(jumpFlag && !isGravity){ //true && !false (start)

                    jumpFlag=false;
                    new Thread(){
                        public void run(){
                            jump();
                            jumpFlag=true;

                        }
                    }.start();

                }

            }


            Action();
            try {
                this.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //向上跳的函数
    public void jump(){
        int jumpHeigh=0;
        for (int i = 0; i < 50; i++) {
            gf.mario.y-=this.yspeed;
            jumpHeigh++;
            if(hit(Dir_Up)){
                break;
            }
            try {
                Thread.sleep(5);
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
                Thread.sleep(5);
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

            if(dir.equals("Left")){
                rect = new Rectangle(enery.x+2,enery.y,enery.width,enery.height);
            }
            else if(dir.equals("Right")){
                rect = new Rectangle(enery.x-2,enery.y,enery.width,enery.height);
            }

            else if(dir.equals("Up")){
                rect = new Rectangle(enery.x,enery.y+1,enery.width,enery.height);
            }else if(dir.equals("Down")){
                rect = new Rectangle(enery.x,enery.y-2,enery.width,enery.height);
            }
            //碰撞检测
            if(myrect.intersects(rect)){
                return true;
            }
        }

        return false;
    }

    //检查是否贴地
    public boolean isGravity=false;

    public void Gravity(){
        new Thread(){
            public void run(){

                while(true){
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(!jumpFlag){

                    }

                    while(true){
                        if(!jumpFlag){
                            break;
                        }

                        if(hit(Dir_Down)){
                            break;
                        }

                        if(y>=224){
                            isGravity=false;

                        }
                        else{
                            isGravity=true;
                            y+=yspeed;
                        }

                        try {
                            sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();

    }



}