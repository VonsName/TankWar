package com;

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

    public Main getM() {
        return m;
    }

    public void setM(Main m) {
        this.m = m;
    }

    public Missile(int x, int y, Tanks.Direction dir, Main m) {
        this(x, y, dir);
        this.m = m;
    }

    public Missile(int x, int y, Tanks.Direction dir, boolean good, Main m) {
        this(x, y, dir,m);
        this.good = good;
    }
    private static Image[] tankImages=null;
    private static Toolkit toolkit=Toolkit.getDefaultToolkit();
    static {
        tankImages = new Image[]{
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/enemymissile.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/tankmissile.gif")),
        };
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
        if (!this.isLive()){
            m.missileList.remove(this);
            return;
        }
      /*  Color color = g.getColor();
        if (good){
            g.setColor(Color.RED);
        }else {
            g.setColor(Color.BLACK);
        }
        g.fillOval(x,y,WIDTH,HIGH);
        g.setColor(color);*/
      if (good){
          g.drawImage(tankImages[1],x,y,null);
      }else {
          g.drawImage(tankImages[0],x,y,null);
      }
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
    public Rectangle getRect(){
        return new Rectangle(x,y,WIDTH,HIGH);
    }
    public boolean hitTank(Tanks t){
        if (this.isLive&&this.getRect().intersects(t.getRect())
                &&t.isLive() && this.good!=t.isGood()){
            if (t.isGood()){
                t.setLife(t.getLife()-20);
                if (t.getLife()<=0){
                    t.setLive(false);
                }
            }else {
                t.setLive(false);
            }
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
    public boolean hitWall(Wall wall){
        if (this.isLive&&this.getRect().intersects(wall.getRect())){
            this.isLive=false;
            m.missileList.remove(this);
            return true;
        }
        return false;
    }
}
