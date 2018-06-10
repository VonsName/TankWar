package com;

import java.awt.*;

public class Explode {
    private int x,y;
    private boolean live=true;
    private static Toolkit toolkit=Toolkit.getDefaultToolkit();
    //load爆炸图片
    private static Image[] images={
            toolkit.getImage(Explode.class.getClassLoader().getResource("images/blast1.gif")),
            toolkit.getImage(Explode.class.getClassLoader().getResource("images/blast2.gif")),
            toolkit.getImage(Explode.class.getClassLoader().getResource("images/blast3.gif")),
            toolkit.getImage(Explode.class.getClassLoader().getResource("images/blast4.gif")),
            toolkit.getImage(Explode.class.getClassLoader().getResource("images/blast5.gif")),
            toolkit.getImage(Explode.class.getClassLoader().getResource("images/blast6.gif")),
            toolkit.getImage(Explode.class.getClassLoader().getResource("images/blast7.gif")),
            toolkit.getImage(Explode.class.getClassLoader().getResource("images/blast8.gif"))
                                };
    //private int diammeter[]={6,23,35,55,67,99,76,50,32,20,10,6};
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
        if (step==images.length){
            live=false;
            step=0;
            return;
        }
        g.drawImage(images[step],x,y,null);
        step++;
    }
}
