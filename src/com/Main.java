package com;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main extends Frame{
    public static final int GAME_WIDTH=800;
    public static final int GAME_HIGH=600;
    NetClient netClient;
        private Image image=null;
        //解决双缓冲
        @Override
        public void update(Graphics g) {
            if (image==null){
                image=this.createImage(800,600);
            }
            Graphics graphics = image.getGraphics();
            Color color = graphics.getColor();
            graphics.setColor(Color.GREEN);
            graphics.fillRect(0,0,800,600);
            graphics.setColor(color);
            paint(graphics);
            g.drawImage(image,0,0,null);
        }

        private int x=70; //初始位置
        private int y=70; //初始位置
        public Tanks myTank=new Tanks(true,this,Tanks.Direction.STOP,x,y);

        //private com.Tanks enemtanks=new com.Tanks(false,this,200,200);
        public List<Tanks> tanksList=new LinkedList<>();
        public Missile missile=null;
        List<Missile> missileList=new ArrayList<>();
        private Explode explode=new Explode(90,90,this);
        List<Explode> explodeList=new LinkedList<>();
        Wall w1=new Wall(200,100,20,150,this);
        Wall w2=new Wall(300,200,300,20,this);
        Blood blood=new Blood();
        //绘图出tank
        @Override
        public void paint(Graphics g) {
            g.drawString("missileCount:"+missileList.size(),5,y);
            g.drawString("explodeCount:"+explodeList.size(),5,y+10);
            g.drawString("tankCount:"+tanksList.size(),5,y+20);
            g.drawString("tankLife:"+myTank.getLife(),5,y+30);
            if (tanksList.isEmpty()){
                for (int i = 0; i < 10; i++) {
                    tanksList.add(new Tanks(false,this,Tanks.Direction.D,x+x*(i+1),y));
                }
            }
            for (int i=0;i<missileList.size();i++){
                Missile missile = missileList.get(i);
                //if (!missile.isLive()){
                  //  missileList.remove(missile);
               //}
                missile.draw(g);
                missile.hitTank(myTank);
                missile.hitWall(w1);
                missile.hitWall(w2);
                missile.hitTank(tanksList);
                //missile.hitTank(enemtanks);
            }

            for (int i = 0; i < explodeList.size(); i++) {
                Explode explode = explodeList.get(i);
                explode.draw(g);
            }
            w1.draw(g);
            w2.draw(g);
            myTank.draw(g);
            myTank.eat(blood);
            blood.draw(g);
            for (int i = 0; i <tanksList.size() ; i++) {
                Tanks tanks = tanksList.get(i);
                tanks.draw(g);
                tanks.hitWall(w1);
                tanks.hitWall(w2);
                tanks.hitWithTank(tanksList);
                tanks.hitWithTank(myTank);
            }

            //enemtanks.draw(g);
        }

        public void lanchFrame(){
            for (int i = 0; i < 10; i++) {
                tanksList.add(new Tanks(false,this,Tanks.Direction.D,x+x*(i+1),y));
            }
            this.setLocation(400,300);
            this.setSize(800,600);
            this.setTitle("TankWar");
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    System.exit(0);
                }
            });
            this.setBackground(Color.green);
            //是否可以改变窗体大小
            this.setResizable(false);
            setVisible(true);
            //添加键盘监听器
            this.addKeyListener(new KeyMonitor());
            new Thread(new PaintThread()).start();
            netClient= new NetClient(this);
            //netClient.connect("127.0.0.1",TankServer.TCP_PORT);
        }
        private class PaintThread implements Runnable{

            @Override
            public void run() {
                while (true){
                    repaint();//调用父类的方法，会自动调用子类重写的paint()方法
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        Condialog condialog=new Condialog();
        private class KeyMonitor extends KeyAdapter{
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode==KeyEvent.VK_C){
                    condialog.setVisible(true);
                }else {
                    myTank.move(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                myTank.keyReleased(e);
            }
        }

        private class Condialog extends Dialog{
            Button bt = new Button("onclick");
           TextField ips= new TextField("127.0.0.1",12);
           TextField tcp= new TextField(TankServer.TCP_PORT+"",5);
            TextField udp=new TextField("2223",5);
            public Condialog() {
                super(Main.this,true);

                this.setLayout(new FlowLayout());
                this.add(new Label("ip:"));
                this.add(ips);
                this.add(new Label("port:"));
                this.add(tcp);
                this.add(new Label("myUDP:"));
                this.add(bt);
                this.add(udp);
                this.setLocation(400,300);
                this.pack();
                this.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        setVisible(false);
                    }
                });
                bt.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String ip=ips.getText().trim();
                        int port=Integer.parseInt(tcp.getText().trim());
                        int udpport=Integer.parseInt(udp.getText().trim());
                        netClient.setUDP_PORT(udpport);
                        netClient.connect(ip,port);
                    }
                });
            }
        }

    public static void main(String[] args) {
        Main tank = new Main();
        tank.lanchFrame();

    }
}
