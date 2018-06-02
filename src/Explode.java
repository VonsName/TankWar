import java.awt.*;

public class Explode {
    private int x,y;
    private boolean live=true;
    private int diammeter[]={6,23,35,55,67,99,76,50,32,20,10,6};
    private int step=0;
    private Main m;
    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Explode(int x, int y, Main m) {
        this(x,y);
        this.m = m;
    }

    public void draw(Graphics g){
        if (!live){
            m.explodeList.remove(this);
            return;
        }
        if (step==diammeter.length){
            live=false;
            step=0;
            return;
        }
        Color color = g.getColor();
        g.setColor(Color.PINK);
        g.fillOval(x,y,diammeter[step],diammeter[step]);
        g.setColor(color);
        step++;
    }
}
