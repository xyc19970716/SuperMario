package com.Xieyuchen.Util;

import com.Xieyuchen.enery.Coin;
import com.Xieyuchen.enery.Enery;
import com.Xieyuchen.enery.Mushroom;
import com.Xieyuchen.mario.Dead;
import com.Xieyuchen.mario.GameFrame;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class Physics {
    private GameFrame gf;
    public Physics(GameFrame gf) {
        this.gf = gf;
    }
    //检测碰撞
    public boolean hit(String dir){
        Rectangle myrect = new Rectangle(gf.mario.x,gf.mario.y,gf.mario.width,gf.mario.height);
        Rectangle rect =null;
        boolean isColliding = false;

        for (int i = 0; i < gf.eneryList.size(); i++) {
            Enery enery = gf.eneryList.get(i);
            if (gf.mario.eatMushroom && enery.name.equals("CreateBigMushroom") && gf.mario.eatId == enery.Id) {//找到吃掉的蘑菇删除它
                gf.eneryList.remove(enery);
                gf.mario.eatMushroom = false;
            }
            if(dir.equals("Left")){
                rect = new Rectangle(enery.x+2,enery.y,enery.width,enery.height);//x+2
                /*if (this.y > enery.y + enery.height ||enery.y > this.y + this.height
                        || this.x > enery.x + enery.width || enery.x > this.x + this.width) {
                     isColliding = false;
                } else {
                    isColliding = true;
                }*/

            }
            else if(dir.equals("Right")){
                rect = new Rectangle(enery.x-1,enery.y,enery.width,enery.height);//x-1

            }

            else if(dir.equals("Up")){
                rect = new Rectangle(enery.x,enery.y+1,enery.width,enery.height);//y+1


            }else if(dir.equals("Down")){
                rect = new Rectangle(enery.x,enery.y-2,enery.width,enery.height);//y-2

            }
            //碰撞检测
            if( myrect.intersects(rect)){
                if (enery.name=="coin"&& dir.equals("Up")) {  //创建蘑菇
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaMushroom");
                    Mushroom createBigMushroom = new Mushroom(enery.x, enery.y, new ImageIcon("src/images/createBigMushroom.png").getImage(), "CreateBigMushroom");
                    createBigMushroom.x = enery.x;
                    createBigMushroom.y = enery.y - createBigMushroom.height;
                    createBigMushroom.Id = gf.eneryList.size();
                    Enery coin = new Coin(enery.x,enery.y,  new ImageIcon("src/images/withoutcoin.png").getImage(),"without coin");//顶过就没了
                    coin.Id = enery.Id;
                    gf.eneryList.remove(enery);
                    gf.eneryList.add(coin);
                    gf.eneryList.add(createBigMushroom);

                }
                if (enery.name=="CreateBigMushroom" && (dir.equals("Left") ||dir.equals("Right") ||dir.equals("Down"))) { //吃蘑菇变大
                    gf.mario.eatMushroom = true;
                    gf.mario.eatId = enery.Id;
                    gf.mario.status=gf.mario.STATUS_BIG;
                    gf.mario.x = gf.mario.x + gf.mario.width;
                    gf.mario.y = gf.mario.y + gf.mario.height;
                    gf.mario.img = new ImageIcon("src/images/bigmario_right.png").getImage();
                    gf.mario.width = gf.mario.img.getWidth(null);
                    gf.mario.height = gf.mario.img.getHeight(null);
                    gf.mario.x = gf.mario.x - gf.mario.width;
                    gf.mario.y = gf.mario.y - gf.mario.height;
                    gf.mario.xspeed = 8;
                    gf.mario.yspeed = 8;

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

               /* if(!gf.mario.jumpFlag){

                }*/
                System.out.println("this is gravity thread.");
                while(true){
                    if(!gf.mario.jumpFlag){
                        break;
                    }

                    if(hit(gf.mario.Dir_Down)){
                        isGravity=false;
                        break;
                    }

                    /*if(y>=224){


                    }*/
                    else if (/*y<=224*/!hit(gf.mario.Dir_Down)){
                        /*if (!hit(Dir_Down)) {*/
                        isGravity=true;
                        gf.mario.y+=gf.mario.yspeed;
                        // }

                    }
                    if (gf.mario.y>224) {
                        Dead dead = new Dead(this.gf);
                        gf.mario.stop();
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
}
