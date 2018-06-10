package com;

import java.awt.*;

public class Blood {
    private int x,y,w,h;
    private boolean live=true;
    private int[][] position={
                            {400,300},{455,300},{475,350},{500,280},
                            {490,310},{510,320},{510,275},{480,365}
                             };
    private int step=0;
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
    public void draw(Graphics g){
        if (!this.isLive()) return;
        w=15;
        h=15;
        Color color = g.getColor();
        g.setColor(Color.MAGENTA);
        g.fillRect(x,y,w,h);
        g.setColor(color);
        move();
    }

    private void move() {
        /*try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        step++;
        if (step==position.length){
            step=0;
        }
        x=position[step][0];
        y=position[step][1];
    }
    public Rectangle getRect(){
        return new Rectangle(x,y,w,h);
    }
}
