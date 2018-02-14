package com.Xieyuchen.Util;

import com.Xieyuchen.enery.Coin;
import com.Xieyuchen.enery.Enery;
import com.Xieyuchen.enery.Mushroom;
import com.Xieyuchen.mario.Dead;
import com.Xieyuchen.mario.GameFrame;
import com.Xieyuchen.mario.Mario;
import com.Xieyuchen.mario.Test;

import javax.swing.*;
import java.awt.*;

import static java.lang.Thread.sleep;

public class Physics {
    private GameFrame gf;
    public Physics(GameFrame gf) {
        this.gf = gf;

    }
    //检测碰撞
    public boolean hit(Mario sprite,String dir){
        Rectangle myrect = new Rectangle(sprite.x,sprite.y,sprite.width,sprite.height);
        Rectangle rect =null;
        boolean isColliding = false;
        System.out.println("hit!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        for (int i = 0; i < gf.eneryList.size(); i++) {
            Enery enery = gf.eneryList.get(i);
            /*if (sprite.eatMushroom && enery.name.equals("CreateBigMushroom") && sprite.eatId == enery.Id) {//找到吃掉的蘑菇删除它
                gf.eneryList.remove(enery);
                sprite.eatMushroom = false;
            }*/
            switch (dir) {
                case "Left":
                    rect = new Rectangle(enery.x + 2, enery.y, enery.width, enery.height);//x+2

                /*if (this.y > enery.y + enery.height ||enery.y > this.y + this.height
                        || this.x > enery.x + enery.width || enery.x > this.x + this.width) {
                     isColliding = false;
                } else {
                    isColliding = true;
                }*/

                    break;
                case "Right":
                    rect = new Rectangle(enery.x - 1, enery.y, enery.width, enery.height);//x-1


                    break;
                case "Up":
                    rect = new Rectangle(enery.x, enery.y + 1, enery.width, enery.height);//y+1


                    break;
                case "Down":
                    rect = new Rectangle(enery.x, enery.y - 2, enery.width, enery.height);//y-2


                    break;
            }
            //碰撞检测
            assert rect != null;
            if( myrect.intersects(rect)){
                if (enery.name.equals("coin") && dir.equals("Up")) {  //创建蘑菇
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
                if (enery.name.equals("CreateBigMushroom") && (dir.equals("Left") ||dir.equals("Right") ||dir.equals("Down"))) { //吃蘑菇变大
                    sprite.eatMushroom = true;
                    //sprite.eatId = enery.Id;
                    sprite.status=sprite.STATUS_BIG;
                    sprite.x = sprite.x + sprite.width;
                    sprite.y = sprite.y + sprite.height;
                    sprite.img = new ImageIcon("src/images/bigmario_right.png").getImage();
                    sprite.width = sprite.img.getWidth(null);
                    sprite.height = sprite.img.getHeight(null);
                    sprite.x = sprite.x - sprite.width;
                    sprite.y = sprite.y - sprite.height;
                    sprite.xspeed = 4;//大
                    sprite.yspeed = 10;//大
                    gf.eneryList.remove(enery);
                    sprite.eatMushroom = false;

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
                    sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Test.class.getResource("/").getFile().replaceAll("%20", " ") + "../src/"+"PrStart.woff.ttf");
               /* if(!gf.mario.jumpFlag){

                }*/
                System.out.println("this is gravity thread.");
                while(true){
                    if(!gf.mario.jumpFlag){
                        break;
                    }

                    if(hit(gf.mario,gf.mario.Dir_Down)){
                        isGravity=false;
                        break;
                    }

                    /*if(y>=224){


                    }*/
                    //else if (/*y<=224*/!hit(gf.mario,gf.mario.Dir_Down)){
                        /*if (!hit(Dir_Down)) {*/
                        isGravity=true;
                        gf.mario.y+=gf.mario.jumpHeigh;
                        // }

                    //}
                    if (gf.mario.y>224) {
                        Dead dead = new Dead(this.gf);
                        //new Thread(mario).stop();
                        dead.showDead(gf.mario.x,gf.mario.y);
                    }

                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
