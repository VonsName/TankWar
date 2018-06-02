import java.awt.*;
import java.util.List;

public class Missile {
    private static final int XSPEED=10;
    private static final int YSPEED=10;
    public static final int WIDTH=10;
    public static final int HIGH=10;
    private int x,y;
    private Tanks.Direction dir;
    private boolean good;
    private boolean isLive=true;
    private Main m;
    public boolean isLive() {
        return isLive;
    }

    public Missile(int x, int y, Tanks.Direction dir, Main m) {
        this(x, y, dir);
        this.m = m;
    }

    public Missile(int x, int y, Tanks.Direction dir, boolean good, Main m) {
        this(x, y, dir,m);
        this.good = good;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

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

    public Tanks.Direction getDir() {
        return dir;
    }

    public void setDir(Tanks.Direction dir) {
        this.dir = dir;
    }

    public Missile(int x, int y, Tanks.Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }
    public void draw(Graphics g){
        if (!isLive){
            m.missileList.remove(this);
            return;
        }
        Color color = g.getColor();
        g.setColor(Color.BLACK);
        g.fillOval(x,y,WIDTH,HIGH);
        g.setColor(color);
        move();
    }

    private void move() {
        if (!isLive){
            m.missileList.remove(this);
        }
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
        }
        if (x<0||y<0||x>800||y>600){
            isLive=false;
        }
    }

    //碰撞检测
    private Rectangle getRect(){
        return new Rectangle(x,y,WIDTH,HIGH);
    }
    public boolean hitTank(Tanks t){
        if (this.isLive&&this.getRect().intersects(t.getRect())
                &&t.isLive() && this.good!=t.isGood()){
            t.setLive(false);
            this.setLive(false);
            Explode explode=new Explode(x,y,m);
            m.explodeList.add(explode);
            return true;
        }
        return false;
    }
    public boolean hitTank(List<Tanks> tanksList){
        for (int i =0;i<tanksList.size();i++){
            boolean b = hitTank(tanksList.get(i));
            if (b){
                //m.tanksList.remove(i);
                return true;
            }
        }
        return false;
    }
}