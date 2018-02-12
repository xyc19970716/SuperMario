package com.Xieyuchen.mario;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

import com.Xieyuchen.enery.Brick;
import com.Xieyuchen.enery.Coin;
import com.Xieyuchen.enery.Enery;
import com.Xieyuchen.enery.Pipe;

import com.Xieyuchen.Util.Map;

public class GameFrame extends JFrame{

    public Mario mario;
    public Enery pipe_tou, coin,brick,pipe_sheng;
    //背景图片
    public BackgroundImage bg ;
    public  int GAME_FRAME_WIDTH = 255;
    public  int GAME_FRAME_HEIGHT = 224;
    //容器装敌人
    public ArrayList<Enery> eneryList = new ArrayList<Enery>();

    //子弹容器
    public ArrayList<Boom> boomList = new ArrayList<Boom>();

    //子弹的速度
    public int bspeed=0;

    //画地图，制定规则，是1画砖头，是2画金币，是3画水管
    public int [][] map =null;

    //构造函数里面初始化背景图片和马里奥对象
    public GameFrame() throws Exception {

        mario = new Mario(this);
        mario.start();
        Map mp= new Map();
        bg = new BackgroundImage();

        //窗体重绘线程
        new Thread(){
            public void run(){
                while(true){
                    //重绘窗体
                    System.out.println("draw");
                    repaint();
                    //检查子弹是否出界
                    checkBoom();
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        map=mp.readMap();
        //读取地图，并配置地图

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                //读取到的是1，画砖头
                if(map[i][j]==1){
                    brick = new Brick(j * 16,i * 16, new ImageIcon("src/images/brick.png").getImage(),"brick");
                    brick.Id = eneryList.size()-1;
                    eneryList.add(brick);
                }
                //读到2画金币
                if(map[i][j]==2){
                    coin = new Coin(j * 16,i * 16,  new ImageIcon("src/images/coin.png").getImage(),"coin");
                    coin.Id = eneryList.size()-1;
                    eneryList.add(coin);
                }
                //读到3画水管
                if(map[i][j]==3){
                    pipe_tou = new Pipe(j*16,i*16, new ImageIcon("src/images/pipe_tou.png").getImage(),"pipetou");
                    pipe_tou.Id = eneryList.size()-1;
                    eneryList.add(pipe_tou);
                    if (map[i+2][j] == 0 ) {
                        pipe_sheng = new Pipe(j * 16+2,(i+2)*16,new ImageIcon("src/images/pipe_sheng.png").getImage(),"pipesheng");
                        pipe_sheng.Id = eneryList.size()-1;
                        eneryList.add(pipe_sheng);
                    }
                    if (map[i+3][j] == 0 ) {
                        pipe_sheng = new Pipe(j * 16+2,(i+3)*16,new ImageIcon("src/images/pipe_sheng.png").getImage(),"pipesheng");
                        pipe_sheng.Id = eneryList.size()-1;
                        eneryList.add(pipe_sheng);
                    }
                }


            }
        }
        //读完整水管
       /* for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if(map[i][j]==3) {
                    if (map[i+2][j] == 0 ) {
                        pipe_sheng = new Pipe(j * 16+2,(i+2)*16,new ImageIcon("src/images/pipe_sheng.png").getImage(),"pipesheng");
                        pipe_sheng.Id = eneryList.size()-1;
                        eneryList.add(pipe_sheng);
                    }
                    if (map[i+3][j] == 0 ) {
                        pipe_sheng = new Pipe(j * 16+2,(i+3)*16,new ImageIcon("src/images/pipe_sheng.png").getImage(),"pipesheng");
                        pipe_sheng.Id = eneryList.size()-1;
                        eneryList.add(pipe_sheng);
                    }
                    /*if (map[i+4][j] == 0 ) {
                        pipe_sheng = new Pipe(j * 16+2,(i+4)*16,new ImageIcon("src/images/pipe_sheng.png").getImage(),"pipesheng");
                        pipe_sheng.Id = eneryList.size()-1;
                        eneryList.add(pipe_sheng);
                    }*/
               // }
           // }
       // }*/

        //设置背景音乐
        //com.Xieyuchen.music.Util.startMusic("music/bg1.wav");
    }

    public void initFrame(){
        //设置窗体相关属性
        this.setSize(GAME_FRAME_WIDTH,GAME_FRAME_HEIGHT);
        this.setTitle("山寨版超级玛丽");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

        //该窗体添加键盘监听
        KeyListener kl = new KeyListener(this);
        this.addKeyListener(kl);

        while (true) {
            for (int i = 0; i <eneryList.size(); i++) {
                Enery e =eneryList.get(i);
                if (e.name.equals("coin")) {
                    r.delay(400);
                    e.img = new ImageIcon("src/images/coin" + actionEneryTime  + ".png").getImage();
                    actionEneryTime++;
                    if (actionEneryTime == 3) {
                        actionEneryTime = 0;
                    }
                }
            }


        }
    }
    public int actionEneryTime;

    Robot r= new Robot();

    public void paint(Graphics g) {
        //利用双缓冲画背景图片和马里奥
        BufferedImage bi =(BufferedImage)this.createImage(this.getSize().width,this.getSize().height);
        Graphics big =bi.getGraphics();
        big.drawImage(bg.img, bg.x, bg.y, null);
        if (bg.x == -2*255) {
            bg.x = 0;
        }



        //总画
        for (int i = 0; i <eneryList.size(); i++) {
            Enery e =eneryList.get(i);
            big.drawImage(e.img, e.x, e.y, e.width, e.height,null);
        }

        //画子弹
        for (int i = 0; i < boomList.size(); i++) {
            Boom b =boomList.get(i);
            Color c =big.getColor();
            big.setColor(Color.red);
            big.fillOval(b.x+= b.speed, b.y+=bspeed, b.width, b.width);
            big.setColor(c);
        }

        //画人物
        big.drawImage(mario.img, mario.x, mario.y, mario.width, mario.height,null);
        g.drawImage(bi,0,0,null);



    }

    //检查子弹是否出界，出界则从容器中移除，不移除的话，内存会泄漏
    public void checkBoom(){
        for (int i = 0; i < boomList.size(); i++) {
            Boom b = boomList.get(i);
            if(b.x<0 || b.x>800){
                boomList.remove(i);
            }
        }
    }

}