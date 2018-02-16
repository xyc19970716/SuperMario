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
import java.awt.image.BufferedImage;

import static java.lang.Thread.sleep;

public class Physics {
    private GameFrame gf;
    public Image brickDestoryLeftup = new ImageIcon("src/images/bick_destory_left_up.png").getImage();
    public Image brickDestoryLeftdown = new ImageIcon("src/images/bick_destory_left_down.png").getImage();
    public Image brickDestoryRightup = new ImageIcon("src/images/bick_destory_right_up.png").getImage();
    public Image brickDestoryRightdown = new ImageIcon("src/images/bick_destory_right_down.png").getImage();

    public boolean hitCoinhasCoin;

    public Physics(GameFrame gf) {
        this.gf = gf;

    }

    //检测碰撞
    public boolean hit(Mario sprite, String dir) {
        Rectangle myrect = new Rectangle(sprite.x, sprite.y, sprite.width, sprite.height);
        Rectangle rect = null;

        System.out.println("hit!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        for (int i = 0; i < gf.eneryList.size(); i++) {
            Enery enery = gf.eneryList.get(i);
            switch (dir) {
                case "Left":
                    rect = new Rectangle(enery.x, enery.y, enery.width, enery.height);//x+2
                    break;
                case "Right":
                    rect = new Rectangle(enery.x, enery.y, enery.width, enery.height);//x-1
                    break;
                case "Up":
                    rect = new Rectangle(enery.x, enery.y + 1, enery.width, enery.height);//y+1
                    break;
                case "Down":
                    rect = new Rectangle(enery.x, enery.y - 1, enery.width, enery.height);//y-2
                    break;
            }
            //碰撞检测
            assert rect != null;
            if (myrect.intersects(rect)) {
                if (enery.name.equals("powerUpCoin") && dir.equals("Up")) {  //创建蘑菇
                    System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaMushroom");
                    gf.sound.hitPowerupBgm.play();
                    if (gf.mario.status == gf.mario.STATUS_BIG) {
                        Enery createBigMushroom = new Mushroom(enery.x, enery.y, new ImageIcon("src/images/flower.png").getImage(), "CreateFlower", gf);
                        createBigMushroom.x = enery.x;
                        createBigMushroom.y = enery.y - createBigMushroom.height;
                        createBigMushroom.Id = gf.eneryList.size();
                        gf.eneryList.add(createBigMushroom);
                    } else if (gf.mario.status == gf.mario.STATUS_SMALL) {
                        Enery createBigMushroom = new Mushroom(enery.x, enery.y, new ImageIcon("src/images/createBigMushroom.png").getImage(), "CreateBigMushroom", gf);
                        createBigMushroom.x = enery.x;
                        createBigMushroom.y = enery.y - createBigMushroom.height;
                        createBigMushroom.Id = gf.eneryList.size();
                        //new Thread(createBigMushroom).start();
                        gf.eneryList.add(createBigMushroom);
                    }
                    Enery coin = new Coin(enery.x, enery.y, new ImageIcon("src/images/withoutcoin.png").getImage(), "without coin");//顶过就没了
                    coin.Id = enery.Id;
                    gf.eneryList.remove(enery);
                    gf.eneryList.add(coin);

                }
                if (enery.name.equals("CreateBigMushroom") && (dir.equals("Left") || dir.equals("Right") || dir.equals("Down") || dir.equals("Up"))) { //吃蘑菇变大
                    gf.sound.eatPowerupBgm.play();
                    sprite.eatMushroom = true;
                    //sprite.eatId = enery.Id;
                    sprite.status = sprite.STATUS_BIG;
                    sprite.x = sprite.x + sprite.width;
                    sprite.y = sprite.y + sprite.height;
                    sprite.img = new ImageIcon("src/images/bigmario_right.png").getImage();
                    sprite.width = sprite.img.getWidth(null);
                    sprite.height = sprite.img.getHeight(null);
                    sprite.x = sprite.x - sprite.width;
                    sprite.y = sprite.y - sprite.height;
                    sprite.xspeed = 4;//大
                    sprite.yspeed = 12;//大
                    gf.eneryList.remove(enery);
                    sprite.eatMushroom = false;
                    sprite.score += 1000;
                }
                if (enery.name.equals("CreateFlower") && (dir.equals("Left") || dir.equals("Right") || dir.equals("Down") || dir.equals("Up"))) { //吃花
                    gf.sound.eatPowerupBgm.play();
                    sprite.eatMushroom = true;
                    //sprite.eatId = enery.Id;
                    sprite.status = sprite.STATUS_BIGFIRE;
                    sprite.x = sprite.x + sprite.width;
                    sprite.y = sprite.y + sprite.height;
                    sprite.img = new ImageIcon("src/images/bigfiremario_right.png").getImage();
                    sprite.width = sprite.img.getWidth(null);
                    sprite.height = sprite.img.getHeight(null);
                    sprite.x = sprite.x - sprite.width;
                    sprite.y = sprite.y - sprite.height;
                    sprite.xspeed = 4;//大
                    sprite.yspeed = 12;//大
                    gf.eneryList.remove(enery);
                    sprite.eatMushroom = false;
                    sprite.score += 1000;
                }
                if (enery.name.equals("coin") && dir.equals("Up")) {//顶到金币

                    hitCoinhasCoin = true;
                    gf.sound.hitCoinBgm.play();
                    Enery coin = new Coin(enery.x, enery.y, new ImageIcon("src/images/withoutcoin.png").getImage(), "without coin");//顶过就没了
                    coin.Id = enery.Id;
                    gf.eneryList.remove(enery);
                    gf.eneryList.add(coin);
                    new Thread(() ->
                    {
                        int temp = coin.y;
                        while (coin.y > temp - 4) {
                            coin.y--;
                            //gf.coinImage = new ImageIcon("src/images/uicoin" + time % 500 /3 +".png").getImage();
                            //big.drawImage(gf.coinImage, enery.x, enery.y-16, enery.width, enery.height, null);
                            //time += gf.flashTime;
                            try {
                                Thread.sleep(gf.flashTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        while (coin.y < temp + 4) {
                            coin.y++;
                            try {
                                Thread.sleep(gf.flashTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        while (coin.y > temp) {
                            coin.y--;
                            try {
                                Thread.sleep(gf.flashTime);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }


                    }).start();
                    sprite.coinCount++;
                    sprite.score += 200;
                    if (sprite.coinCount == 100) {
                        sprite.coinCount = 0;
                        //加命
                    }

                }
                if (enery.name.equals("without coin") && dir.equals("Up")) {
                    gf.sound.smallHitBgm.play();
                }
                if (enery.name.equals("brick") && dir.equals("Up")) {

                    if (gf.mario.status ==gf.mario.STATUS_SMALL) {
                        gf.sound.smallHitBgm.play();
                        new Thread(() ->
                        {
                            int temp = enery.y;
                            while (enery.y > temp - 4) {
                                enery.y--;
                                try {
                                    Thread.sleep(gf.flashTime);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            while (enery.y < temp + 4) {
                                enery.y++;
                                try {
                                    Thread.sleep(gf.flashTime);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            while (enery.y > temp) {
                                enery.y--;
                                try {
                                    Thread.sleep(gf.flashTime);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } else if (gf.mario.status == gf.mario.STATUS_BIG || gf.mario.status==gf.mario.STATUS_BIGFIRE) {
                        gf.sound.bigDestoryBrickBgm.play();
                        gf.eneryList.remove(enery);
                        gf.mario.score+=50;
                        Graphics g= gf.getGraphics();
                        new Thread(() ->
                        {
                            BufferedImage bi = (BufferedImage) gf.createImage(gf.getSize().width, gf.getSize().height);
                            Graphics big = bi.getGraphics();
                            int high = 0;
                            int a = enery.x + 8;
                            int b = enery.y + 8;
                            while (high<32) {

                                big.drawImage(this.brickDestoryLeftup, enery.x-=high,enery.y-=2*high,8,8,null);
                                big.drawImage(this.brickDestoryLeftdown, enery.x-=high/2,(b)-=high,8,8,null);
                                big.drawImage(this.brickDestoryRightup, (a) +=high,enery.y-=2*high,8,8,null);
                                big.drawImage(this.brickDestoryRightdown, (a)+=high/2,(b)-=high,8,8,null);

                                high++;
                                try {
                                    Thread.sleep(gf.flashTime);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }

                        }).start();
                    }

                }

                return true;
            }
        }
        return false;
    }

    //检查是否贴地
    public boolean isGravity = false;//不会掉

    public void Gravity() {
        new Thread(() -> {
            while (true) {
                try {
                    sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("this is gravity thread.");
                while (true) {

                    if (!gf.mario.jumpFlag) {
                        break;
                    }

                    if (hit(gf.mario, gf.mario.Dir_Down)) {
                        isGravity = false;
                        break;
                    }
                    if (!hit(gf.mario, gf.mario.Dir_Down)) {
                        isGravity = true;
                        gf.mario.y += gf.mario.jumpHeigh;
                    }

                    if (gf.mario.y > 224) {
                        Dead dead = new Dead(this.gf);
                        //new Thread(mario).stop();
                        dead.showDead(gf.mario.x, gf.mario.y);
                        gf.mario.status = 1;
                    }
                    try {
                        sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
