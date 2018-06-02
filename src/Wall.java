import java.awt.*;

public class Wall {
    private int x,y,w,h;
    private Main m;

    public Wall(int x, int y, int w, int h, Main m) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.m = m;
    }

    public Rectangle getRect(){
        Rectangle rectangle=new Rectangle(x,y,w,h);
        return rectangle;
    }
    public void draw(Graphics g){
        g.fillRect(x,y,w,h);
    }
}
