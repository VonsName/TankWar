import java.awt.*;
import java.awt.event.KeyEvent;

public class Tanks {
    public static final int XSPEED=5;
    public static final int YSPEED=5;
    public Tanks(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //tank方向
    private enum Direction{L,LU,LD,R,RU,RD,U,D,STOP}
    //tank朝向
    private boolean bl=false,bu=false,br=false,bd=false;
    //默认停止
    private Direction dir=Direction.STOP;
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
    }

    public void draw(Graphics g){
        Color color = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x,y,30,30);
        g.setColor(color);
        move();
    }
    public void move(KeyEvent e){
        int keyCode = e.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_RIGHT:
                br=true;
                bl=false;
                bd=false;
                bu=false;
                break;
            case KeyEvent.VK_LEFT:
                bl=true;
                br=false;
                bd=false;
                bu=false;
                break;
            case KeyEvent.VK_DOWN:
                bd=true;
                br=false;
                bl=false;
                bu=false;
                break;
            case KeyEvent.VK_UP:
                bu=true;
                br=false;
                bd=false;
                bl=false;
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
}
