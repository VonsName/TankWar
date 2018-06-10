package com;

import java.awt.*;

public class Wall {
    private int x,y,w,h;
    private Main m;
    private static Image[] tankImages=null;
    private static Toolkit toolkit=Toolkit.getDefaultToolkit();
    static {
        tankImages = new Image[]{
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/wall.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/walls.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/tugai.net.20101117134209.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/tugai.net.20101117144551.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/tugai.net.20101117144625.gif")),
                toolkit.getImage(Explode.class.getClassLoader().getResource("images/tugai.net.20101117145147.gif")),
        };
    }
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
        g.drawImage(tankImages[0],x,y,null);
        g.drawImage(tankImages[1],x,y,null);
        g.drawImage(tankImages[2],500,200,null);
        g.drawImage(tankImages[3],600,350,null);
        g.drawImage(tankImages[4],100,550,null);
        g.drawImage(tankImages[5],200,550,null);
        g.drawImage(tankImages[5],250,550,null);
    }
}
