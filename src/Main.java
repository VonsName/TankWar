import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main extends Frame{
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
        private Tanks tanks=new Tanks(x,y);
        //绘图出tank
        @Override
        public void paint(Graphics g) {
           tanks.draw(g);
        }

        public void lanchFrame(){
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
                        Thread.sleep(20);
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
        }

    public static void main(String[] args) {
        Main tank = new Main();
        tank.lanchFrame();
    }
}
