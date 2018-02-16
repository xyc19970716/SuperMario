package com.Xieyuchen.mario;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

import com.Xieyuchen.Util.Physics;
import com.Xieyuchen.Util.Sound;
import com.Xieyuchen.Util.UI;
import com.Xieyuchen.enery.Brick;
import com.Xieyuchen.enery.Coin;
import com.Xieyuchen.enery.Enery;
import com.Xieyuchen.enery.Pipe;

import com.Xieyuchen.Util.Map;

public class GameFrame extends JFrame {

    public int flashTime = 16;
    public Mario mario;
    public Enery pipe_tou, coin, brick, floor, pipe_sheng, stepFloor, powerUpCoin;
    public Image coinImage;//顶出的金币
    //背景图片
    public BackgroundImage bg;
    public Physics physics;
    public UI ui;
    public Font font;
    public int GAME_FRAME_WIDTH = 255;
    public int GAME_FRAME_HEIGHT = 224;

    public int coinFlashtime;
    public Sound sound;
    public int movs, bgspeed = 1, headmovs;
    public int startTime;
    public int currentTime;
    public int period;
    //容器装敌人
    public ArrayList<Enery> eneryList = new ArrayList<Enery>();
    public ArrayList<Enery> spriteList = new ArrayList<Enery>();
    //子弹容器
    public ArrayList<Boom> boomList = new ArrayList<Boom>();

    //子弹的速度
    public int bspeed = 0;

    //画地图，制定规则，是1画砖头，是2画金币，是3画水管
    public int[][] map = null;

    //构造函数里面初始化背景图片和马里奥对象
    public GameFrame() throws Exception {
        physics = new Physics(this);
        mario = new Mario(0 + 16 * 5, 224 - 16 - 2 * 16 - 1 - 1,
                new ImageIcon("src/images/mario_right.png").getImage(),
                "Mario", this);
        new Thread(mario).start();
        Map mp = new Map();
        bg = new BackgroundImage();
        ui = new UI(this);
        sound = new Sound();

        this.physics.Gravity();
        //窗体重绘线程
        new Thread(() -> {
            while (true) {
                //重绘窗体
                System.out.println("draw");
                repaint();

                for (int i = 0; i < eneryList.size(); i++) {
                    Enery e = eneryList.get(i);
                    if (e.name.equals("coin") || e.name.equals("powerUpCoin")) {
                        //r.delay(64);
                        e.img = new ImageIcon("src/images/coin" + actionEneryTime / 800 % 3 + ".png").getImage();

                    }

                    if (e.x < -32) {
                        eneryList.remove(e);
                    }
                    if (e.name.equals("CreateBigMushroom")) {
                        e.x += 1;
                        if (this.physics.hit((Mario) e, this.mario.Dir_Down)) {
                            e.y -= 2;
                            break;
                        }
                        if (!this.physics.hit((Mario) e, this.mario.Dir_Down)) {
                            e.y += 1;
                        }

                        if (!this.physics.hit((Mario) e, this.mario.Dir_Left)) {
                            e.x += 1;
                        }
                        if (!this.physics.hit((Mario) e, this.mario.Dir_Right)) {
                            e.x -= 1;
                        }
                    }

                }
                this.ui.uiCoinImage = new ImageIcon("src/images/uicoin" + actionEneryTime / 500 % 3 + ".png").getImage();


                actionEneryTime += flashTime;
                //检查子弹是否出界
                checkBoom();
                try {
                    Thread.sleep(flashTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        map = mp.readMap();
        //读取地图，并配置地图

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                //读取到的是1，画地砖
                if (map[i][j] == 1) {
                    floor = new Brick(j * 16, i * 16, new ImageIcon("src/images/floor.png").getImage(), "floor");
                    floor.Id = eneryList.size() - 1;
                    eneryList.add(floor);
                }
                //读到2画金币
                if (map[i][j] == 2) {
                    coin = new Coin(j * 16, i * 16, new ImageIcon("src/images/coin.png").getImage(), "coin");
                    coin.Id = eneryList.size() - 1;
                    eneryList.add(coin);
                }
                //读到3画水管
                if (map[i][j] == 3) {
                    pipe_tou = new Pipe(j * 16, i * 16, new ImageIcon("src/images/pipe_tou.png").getImage(), "pipetou");
                    pipe_tou.Id = eneryList.size() - 1;
                    eneryList.add(pipe_tou);
                    if (map[i + 2][j] == 0) {
                        pipe_sheng = new Pipe(j * 16 , (i + 2) * 16, new ImageIcon("src/images/pipe_sheng.png").getImage(), "pipesheng");
                        pipe_sheng.Id = eneryList.size() - 1;
                        eneryList.add(pipe_sheng);
                    }
                    if (map[i + 3][j] == 0) {
                        pipe_sheng = new Pipe(j * 16 , (i + 3) * 16, new ImageIcon("src/images/pipe_sheng.png").getImage(), "pipesheng");
                        pipe_sheng.Id = eneryList.size() - 1;
                        eneryList.add(pipe_sheng);
                    }
                }
                //读到4画砖头
                if (map[i][j] == 4) {
                    brick = new Brick(j * 16, i * 16, new ImageIcon("src/images/brick.png").getImage(), "brick");
                    brick.Id = eneryList.size() - 1;
                    eneryList.add(brick);
                }
                //读到5画台阶
                if (map[i][j] == 5) {
                    stepFloor = new Brick(j * 16, i * 16, new ImageIcon("src/images/stepfloor.png").getImage(), "stepfloor");
                    stepFloor.Id = eneryList.size() - 1;
                    eneryList.add(stepFloor);
                }
                //读到6画power金块
                if (map[i][j] == 6) {
                    powerUpCoin = new Coin(j * 16, i * 16, new ImageIcon("src/images/coin.png").getImage(), "powerUpCoin");
                    powerUpCoin.Id = eneryList.size() - 1;
                    eneryList.add(powerUpCoin);
                }


            }
        }

    }

    public void initFrame() {
        //设置窗体相关属性

        this.setSize(GAME_FRAME_WIDTH, GAME_FRAME_HEIGHT);
        this.setTitle("山寨版超级玛丽");

        this.setUndecorated(true);

        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);


        font = ui.getSelfDefinedFont("PrStart.woff.ttf", 8);

        //该窗体添加键盘监听
        KeyListener kl = new KeyListener(this);
        this.addKeyListener(kl);




    }

    public int actionEneryTime;



    public void paint(Graphics g) {
        //利用双缓冲画背景图片和马里奥
        BufferedImage bi = (BufferedImage) this.createImage(this.getSize().width, this.getSize().height);
        Graphics big = bi.getGraphics();
        if (!ui.startGame) {
            if (bg.width > GAME_FRAME_WIDTH + movs) {//背景图的宽大于窗口宽+移动的
                big.drawImage(bg.img, 0, 0, GAME_FRAME_WIDTH, GAME_FRAME_HEIGHT,
                        movs, 0, GAME_FRAME_WIDTH + movs, GAME_FRAME_HEIGHT, null);
            }//窗口没跑出背景
            if (bg.width <= GAME_FRAME_WIDTH + movs) {//背景图的宽小于窗口的宽+移动的
                headmovs = GAME_FRAME_WIDTH + movs - bg.width;//窗口跑出的距离
                big.drawImage(bg.img, 0, 0, GAME_FRAME_WIDTH - headmovs, GAME_FRAME_HEIGHT,
                        movs, 0, bg.width, GAME_FRAME_HEIGHT, null);
                big.drawImage(bg.img, GAME_FRAME_WIDTH - headmovs, 0, GAME_FRAME_WIDTH, GAME_FRAME_HEIGHT,
                        0, 0, headmovs, GAME_FRAME_HEIGHT, null);
                if (headmovs >= GAME_FRAME_WIDTH) { //窗口跑出的距离大于窗口宽度度
                    movs = headmovs - GAME_FRAME_WIDTH;//重新开始
                }
            }
            //总画
            for (int i = 0; i < eneryList.size(); i++) {
                Enery e = eneryList.get(i);
                big.drawImage(e.img, e.x, e.y, e.width, e.height, null);
            }

            ui.printInfo(big, font, Color.white);


        } else if (ui.showLiveUI) {
            Color c = big.getColor();
            big.setColor(Color.black);
            big.fillRect(0,0,GAME_FRAME_WIDTH,GAME_FRAME_HEIGHT);
            big.setColor(c);
            ui.printInfo(big, font, Color.white);
        } else {

            if (bg.width > GAME_FRAME_WIDTH + movs) {//背景图的宽大于窗口宽+移动的
                big.drawImage(bg.img, 0, 0, GAME_FRAME_WIDTH, GAME_FRAME_HEIGHT,
                        movs, 0, GAME_FRAME_WIDTH + movs, GAME_FRAME_HEIGHT, null);
            }//窗口没跑出背景
            if (bg.width <= GAME_FRAME_WIDTH + movs) {//背景图的宽小于窗口的宽+移动的
                headmovs = GAME_FRAME_WIDTH + movs - bg.width;//窗口跑出的距离
                big.drawImage(bg.img, 0, 0, GAME_FRAME_WIDTH - headmovs, GAME_FRAME_HEIGHT,
                        movs, 0, bg.width, GAME_FRAME_HEIGHT, null);
                big.drawImage(bg.img, GAME_FRAME_WIDTH - headmovs, 0, GAME_FRAME_WIDTH, GAME_FRAME_HEIGHT,
                        0, 0, headmovs, GAME_FRAME_HEIGHT, null);
                if (headmovs >= GAME_FRAME_WIDTH) { //窗口跑出的距离大于窗口宽度度
                    movs = headmovs - GAME_FRAME_WIDTH;//重新开始
                }
            }
            //movs +=bgspeed;

            for (int i = 0; i < spriteList.size(); i++) {
                Enery e = spriteList.get(i);
                big.drawImage(e.img, e.x, e.y, e.width, e.height, null);
            }



            //总画
            for (int i = 0; i < eneryList.size(); i++) {
                Enery e = eneryList.get(i);
                big.drawImage(e.img, e.x, e.y, e.width, e.height, null);
            }

            //画子弹
            for (Boom b : boomList) {
                Color c = big.getColor();
                big.setColor(Color.red);
                big.fillOval(b.x += b.speed, b.y += bspeed, b.width, b.width);
                big.setColor(c);
            }

            //画人物
            big.drawImage(mario.img, mario.x, mario.y, mario.width, mario.height, null);






            ui.printInfo(big, font, Color.white);





        }
        g.drawImage(bi, 0, 0, null);

    }

    //检查子弹是否出界，出界则从容器中移除，不移除的话，内存会泄漏
    private void checkBoom() {
        for (int i = 0; i < boomList.size(); i++) {
            Boom b = boomList.get(i);
            if (b.x < 0 || b.x > 800) {
                boomList.remove(i);
            }
        }
    }

}