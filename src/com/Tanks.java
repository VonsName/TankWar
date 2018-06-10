package com;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Tanks {
    private static Image[] tankImages=null;
    private int id;
    private static Toolkit toolkit=Toolkit.getDefaultToolkit();
    private static Map<String,Image> imageMap=new LinkedHashMap<>(12);
    //加载敌人坦克图片
    static {
        tankImages=new Image[]{
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/enemy1D.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/enemy1L.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/enemy1R.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/enemy1U.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/enemy2D.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/enemy2L.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/enemy2R.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/enemy2U.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/enemy3D.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/enemy3L.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/enemy3R.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/enemy3U.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/p1tankD.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/p1tankL.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/p1tankR.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/p1tankU.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/p2tankD.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/p2tankL.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/p2tankR.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/p2tankU.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/tugai.net.20101117235923.gif"))
        };
        imageMap.put("1D",tankImages[0]);
        imageMap.put("1L",tankImages[1]);
        imageMap.put("1R",tankImages[2]);
        imageMap.put("1U",tankImages[3]);
        imageMap.put("2D",tankImages[4]);
        imageMap.put("2L",tankImages[5]);
        imageMap.put("2R",tankImages[6]);
        imageMap.put("2U",tankImages[7]);
        imageMap.put("3D",tankImages[8]);
        imageMap.put("3L",tankImages[9]);
        imageMap.put("3R",tankImages[10]);
        imageMap.put("3U",tankImages[11]);
        imageMap.put("P1D",tankImages[12]);
        imageMap.put("P1L",tankImages[13]);
        imageMap.put("P1R",tankImages[14]);
        imageMap.put("P1U",tankImages[15]);
        imageMap.put("P2D",tankImages[16]);
        imageMap.put("P2L",tankImages[17]);
        imageMap.put("P2R",tankImages[18]);
        imageMap.put("P2U",tankImages[19]);
        imageMap.put("MY",tankImages[20]);
    }
    public static final int XSPEED=10;
    public static final int YSPEED=10;
    public static final int WIDTH=30;
    public static final int HIGH=30;
    public boolean good;
    private int life=100;
    private int oldX,oldY;
    private boolean isLive=true;
    private BloodBar bloodBar=new BloodBar();

    //load爆炸图片


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tanks(int x, int y) {
        this.x = x;
        this.y = y;
        this.oldX=x;
        this.oldY=y;
    }
    private Main m;

    public Tanks(Main m, int x, int y) {
        this(x,y);
        this.m = m;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Tanks(boolean good, Main m, int x, int y) {
        this(m, x, y);
        this.good = good;
    }

    public Tanks(boolean good, Main m, Direction dir, int x, int y) {
        this(good, m, x, y);
        this.dir = dir;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    //键盘松开事件
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_CONTROL://ctrl事件
                fire();
                break;
            case KeyEvent.VK_F2:
                if (!this.isLive()){
                    this.setLive(true);
                    this.setLife(100);
                }
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
                default:
        }
        direction();
    }

    private void fire() {
        if (!this.isLive()) return;
        int x=this.x+Tanks.WIDTH/2-Missile.WIDTH/2;
        int y=this.y+Tanks.HIGH/2-Missile.HIGH/2;
        Missile missile = new Missile(x,y,prDir,good,this.m);
        if (this.isLive()){
            m.missileList.add(missile);
        }
    }
    public boolean hitWall(Wall wall){
        if (this.isLive&&this.getRect().intersects(wall.getRect())){
            this.stay();
            return true;
        }
        return false;
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
    private void stay(){
        x=oldX;
        y=oldY;
    }
    //根据方向移动位置
    private void move(){
        oldX=x;
        oldY=y;
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
                default:
        }
        if (this.dir!=Direction.STOP){
            this.prDir=this.dir;
        }
        if (x<0) {x=0;}
        if (y<30) {y=30;}
        if (x+Tanks.WIDTH>Main.GAME_WIDTH){x=Main.GAME_WIDTH-Tanks.WIDTH;}
        if (y+Tanks.HIGH>Main.GAME_HIGH){y=Main.GAME_HIGH-Tanks.HIGH;}
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
    private void fire(Direction dir){
        if (!this.isLive()){
            return;
        }

        int x=this.x+tankImages[16].getWidth(null)/2-Missile.WIDTH/2;
        int y=this.y+tankImages[16].getHeight(null)/2-Missile.HIGH/2;
        Missile missile = new Missile(x,y,dir,good,this.m);
        if (this.isLive){
            m.missileList.add(missile);
        }
    }
    public void superFire(){
        Direction[] directions = Direction.values();
        for (int i = 0; i <directions.length ; i++) {
            if (directions[i]!=Direction.STOP){
                fire(directions[i]);
            }
        }
    }
    public void draw(Graphics g){
        if (!isLive){
            if (!good){
                m.tanksList.remove(this);
            }
            return;
        }
        /*Color color = g.getColor();
        if (good){
            g.setColor(Color.RED);
        }else {
            g.setColor(Color.BLUE);
        }*/
        g.drawString("id:"+id,x,y);
        //g.fillOval(x,y,WIDTH,HIGH);
        //g.setColor(color);
        if (this.isGood()){
            bloodBar.draw(g);
        }

        switch (prDir){
            case L:
                //g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x,y+Tanks.HIGH/2);
                if (this.isGood()){
                    g.drawImage(imageMap.get("MY"),x,y,null);
                }else {
                    g.drawImage(imageMap.get("P2L"),x,y,null);
                }
                break;
            case LU:
                //g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x,y);
                if (this.isGood()){
                    g.drawImage(imageMap.get("MY"),x,y,null);
                }else {
                    g.drawImage(imageMap.get("P2L"),x,y,null);
                }
                break;
            case LD:
                //g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x,y+Tanks.HIGH);
                if (this.isGood()){
                    g.drawImage(imageMap.get("MY"),x,y,null);
                }else {
                    g.drawImage(imageMap.get("P2D"),x,y,null);
                }
                break;
            case R:
                //g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x+Tanks.WIDTH,y+Tanks.HIGH/2);
                if (this.isGood()){
                    g.drawImage(imageMap.get("MY"),x,y,null);
                }else {
                    g.drawImage(imageMap.get("P2R"),x,y,null);
                }
                break;
            case RU:
                //g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x+Tanks.WIDTH,y);
                if (this.isGood()){
                    g.drawImage(imageMap.get("MY"),x,y,null);
                }else {
                    g.drawImage(imageMap.get("P2R"),x,y,null);
                }
                break;
            case RD:
               // g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x+Tanks.WIDTH,y+Tanks.HIGH);
                if (this.isGood()){
                    g.drawImage(imageMap.get("MY"),x,y,null);
                }else {
                    g.drawImage(imageMap.get("P2D"),x,y,null);
                }
                break;
            case U:
                //g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x+Tanks.WIDTH/2,y);
                if (this.isGood()){
                    g.drawImage(imageMap.get("MY"),x,y,null);
                }else {
                    g.drawImage(imageMap.get("P2U"),x,y,null);
                }
                break;
            case D:
                //g.drawLine(x+Tanks.WIDTH/2,y+Tanks.HIGH/2,x+Tanks.WIDTH/2,y+Tanks.HIGH);
                if (this.isGood()){
                    g.drawImage(imageMap.get("MY"),x,y,null);
                }else {
                    g.drawImage(imageMap.get("P2D"),x,y,null);
                }
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
            case KeyEvent.VK_A:
                superFire();
                break;
        }
        direction();
    }
    //设置tank的方向
    public void direction(){
        Direction direction=this.dir;
        if (bl&&!br&&!bd&&!bu) dir=Direction.L;
        else if (!bl&&br&&!bd&&!bu) dir=Direction.R;
        else if (!bl&&!br&&!bd&&bu) dir=Direction.U;
        else if (!bl&&!br&&bd&&!bu) dir=Direction.D;
        else if (bl&&!br&&!bd&&bu) dir=Direction.LU;
        else if (bl&&!br&&bd&&!bu) dir=Direction.LD;
        else if (!bl&&br&&!bd&&bu) dir=Direction.RU;
        else if (!bl&&br&&bd&&!bu) dir=Direction.RD;
        else if (!bl&&!br&&!bd&&!bu) dir=Direction.STOP;
        if (this.dir!=direction){
            TankMoveMsg tankMoveMsg = new TankMoveMsg(dir,x,y,id);
            m.netClient.send(tankMoveMsg);
        }

    }
    public Rectangle getRect(){
        return new Rectangle(x,y,tankImages[16].getWidth(null),tankImages[16].getHeight(null));
    }
    public boolean hitWithTank(Tanks tanks){
        //撞击敌军坦克死亡
        if (tanks.isGood()){
            if (!this.isGood()&&this.getRect().intersects(tanks.getRect())){
                tanks.setLife(0);
                tanks.setLive(false);
                Explode explode=new Explode(x,y,m);
                m.explodeList.add(explode);
                return true;
            }
        }
        if (this!=tanks){
            if (this.isLive&&tanks.isLive&&this.getRect().intersects(tanks.getRect())){
                this.stay();
                tanks.stay();
                return true;
            }
        }
        return false;
    }
    public boolean hitWithTank(List<Tanks> tanks){
        boolean flag=false;
        for (int i = 0; i <tanks.size() ; i++) {
            flag=hitWithTank(tanks.get(i));
        }
        return flag;
    }
    private class BloodBar{
        public void draw(Graphics g){
            Color color = g.getColor();
            g.setColor(Color.RED);
            g.drawRect(x,y,WIDTH,5);
            int w=WIDTH*life/100;
            g.fillRect(x,y,w,5);
            g.setColor(color);
        }
    }
    public void eat(Blood blood){
        if (this.isLive()&&blood.isLive()&&this.getRect().intersects(blood.getRect())){
            this.setLife(100);
            blood.setLive(false);
        }
    }
}
