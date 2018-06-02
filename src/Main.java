import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main extends Frame{
    public static final int GAME_WIDTH=800;
    public static final int GAME_HIGH=600;
        private Image image=null;
        //解决双缓冲
        @Override
        public void update(Graphics g) {
            if (image==null){
                image=this.createImage(800,600);
            }
            Graphics graphics = image.getGraphics();
            Color color = graphics.getColor();
            graphics.setColor(Color.green);
            graphics.fillRect(0,0,800,600);
            graphics.setColor(color);
            paint(graphics);
            g.drawImage(image,0,0,null);
        }

        private int x=70; //初始位置
        private int y=70; //初始位置
        private Tanks tanks=new Tanks(true,this,Tanks.Direction.STOP,x,y);

        //private Tanks enemtanks=new Tanks(false,this,200,200);
        public List<Tanks> tanksList=new LinkedList<>();
        public Missile missile=null;
        List<Missile> missileList=new ArrayList<>();
        private Explode explode=new Explode(90,90,this);
        List<Explode> explodeList=new LinkedList<>();
        Wall w1=new Wall(200,100,20,150,this);
        Wall w2=new Wall(300,200,300,20,this);
        //绘图出tank
        @Override
        public void paint(Graphics g) {
            g.drawString("missileCount:"+missileList.size(),5,y);
            g.drawString("explodeCount:"+explodeList.size(),5,y+10);
            g.drawString("tankCount:"+tanksList.size(),5,y+20);
            for (int i=0;i<missileList.size();i++){
                Missile missile = missileList.get(i);
                //if (!missile.isLive()){
                  //  missileList.remove(missile);
               //}
                missile.draw(g);
                missile.hitTank(tanks);
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
            tanks.draw(g);
            for (int i = 0; i <tanksList.size() ; i++) {
                Tanks tanks = tanksList.get(i);
                tanks.draw(g);
                tanks.hitWall(w1);
                tanks.hitWall(w2);
                tanks.hitWithTank(tanksList);
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
        private class KeyMonitor extends KeyAdapter{
            @Override
            public void keyPressed(KeyEvent e) {
                tanks.move(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                tanks.keyReleased(e);
            }
        }

    public static void main(String[] args) {
        Main tank = new Main();
        tank.lanchFrame();
    }
}
