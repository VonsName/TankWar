import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tanks {
    public static final int XSPEED=10;
    public static final int YSPEED=10;
    public static final int WIDTH=30;
    public static final int HIGH=30;
    public boolean good;
    private boolean isLive=true;
    public Tanks(int x, int y) {
        this.x = x;
        this.y = y;
    }
    private Main m;

    public Tanks(Main m, int x, int y) {
        this(x,y);
        this.m = m;
    }

    public Tanks(boolean good, Main m, int x, int y) {
        this(m, x, y);
        this.good = good;
    }

    public Tanks(boolean good, Main m, Direction dir, int x, int y) {
        this(good, m, x, y);
        this.dir = dir;
    }

    //键盘松开事件
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_CONTROL://ctrl事件
                fire();
                break;
            case KeyEvent.VK_RIGHT:
                br=false;
                break;
            case KeyEvent.VK_LEFT:
                bl=false;
                break;
            case KeyEvent.VK_DOWN:
                bd=false;
                break;
            case KeyEvent.VK_UP:
                bu=false;
                break;
        }
        direction();
    }

    private void fire() {
        int x=this.x+Tanks.WIDTH/2-Missile.WIDTH/2;
        int y=this.y+Tanks.HIGH/2-Missile.HIGH/2;
        Missile missile = new Missile(x,y,prDir,good,this.m);
        if (this.isLive){
            m.missileList.add(missile);
        }
    }

    public boolean isGood() {
        return good;
    }

    public void setGood(boolean good) {
        this.good = good;
    }

    //tank方向
    public enum Direction{L,LU,LD,R,RU,RD,U,D,STOP}
    //tank朝向
    private boolean bl=false,bu=false,br=false,bd=false;
    //默认停止
    private Direction dir=Direction.STOP;
    private Direction prDir=Direction.D;
    public static int getXSPEED() {
        return XSPEED;
    }

    public static int getYSPEED() {
        return YSPEED;
    }

    public boolean isBl() {
        return bl;
    }

    public void setBl(boolean bl) {
        this.bl = bl;
    }

    public boolean isBu() {
        return bu;
    }

    public void setBu(boolean bu) {
        this.bu = bu;
    }

    public boolean isBr() {
        return br;
    }

    public void setBr(boolean br) {
        this.br = br;
    }

    public boolean isBd() {
        return bd;
    }

    public void setBd(boolean bd) {
        this.bd = bd;
    }

    private int x,y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public static Random random=new Random();
    private int step=random.nextInt(12)+3;
    //根据方向移动位置
    private void move(){
        switch (dir){
            case L:
                x-=XSPEED;
                break;
            case LU:
                x-=XSPEED;
                y-=YSPEED;
                break;
            case LD:
                x-=XSPEED;
                y+=YSPEED;
                break;
            case R:
                x+=XSPEED;
                break;
            case RU:
                x+=XSPEED;
                y-=YSPEED;
                break;
            case RD:
                x+=XSPEED;
                y+=YSPEED;
                break;
            case U:
                y-=YSPEED;
                break;
            case D:
                y+=YSPEED;
                break;
            case STOP:
                break;
        }
        if (this.dir!=Direction.STOP){
            this.prDir=this.dir;
        }
        if (x<0) x=0;
        if (y<30) y=30;
        if (x+Tanks.WIDTH>Main.GAME_WIDTH)x=Main.GAME_WIDTH-Tanks.WIDTH;
        if (y+Tanks.HIGH>Main.GAME_HIGH)y=Main.GAME_HIGH-Tanks.HIGH;
        if (!good){
            Direction[] directions = Direction.values();
            if (step==0){
                step=random.nextInt(12)+3;
                int anInt = random.nextInt(directions.length);
                dir=directions[anInt];
            }
           step--;
            if (random.nextInt(40)>38)this.fire();
        }
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public void draw(Graphics g){
        if (!isLive){
            if (!good){
                m.tanksList.remove(this);
            }
            return;

        }
        Color color = g.getColor();
        if (good){
            g.setColor(Color.RED);
        }else {
            g.setColor(Color.BLUE);
        }

        g.fillOval(x,y,WIDTH,HIGH);
        g.setColor(color);
        switch (prDir){
            case L:
                g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x,y+Tanks.HIGH/2);
                break;
            case LU:
                g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x,y);
                break;
            case LD:
                g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x,y+Tanks.HIGH);
                break;
            case R:
                g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x+Tanks.WIDTH,y+Tanks.HIGH/2);
                break;
            case RU:
                g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x+Tanks.WIDTH,y);
                break;
            case RD:
                g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x+Tanks.WIDTH,y+Tanks.HIGH);
                break;
            case U:
                g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x+Tanks.WIDTH/2,y);
                break;
            case D:
                g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x+Tanks.WIDTH/2,y+Tanks.HIGH);
                break;
        }
        move();
    }
    public void move(KeyEvent e){
        int keyCode = e.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_RIGHT:
                br=true;
                break;
            case KeyEvent.VK_LEFT:
                bl=true;
                break;
            case KeyEvent.VK_DOWN:
                bd=true;
                break;
            case KeyEvent.VK_UP:
                bu=true;
                break;
        }
        direction();
    }
    //设置tank的方向
    public void direction(){
        if (bl&&!br&&!bd&&!bu) dir=Direction.L;
        else if (!bl&&br&&!bd&&!bu) dir=Direction.R;
        else if (!bl&&!br&&!bd&&bu) dir=Direction.U;
        else if (!bl&&!br&&bd&&!bu) dir=Direction.D;
        else if (bl&&!br&&!bd&&bu) dir=Direction.LU;
        else if (bl&&!br&&bd&&!bu) dir=Direction.LD;
        else if (!bl&&br&&!bd&&bu) dir=Direction.RU;
        else if (!bl&&br&&bd&&!bu) dir=Direction.RD;
        else if (!bl&&!br&&!bd&&!bu) dir=Direction.STOP;
    }
    public Rectangle getRect(){
        return new Rectangle(x,y,WIDTH,HIGH);
    }
}
