package bubleshooter;

import java.awt.*;

/**
 * Created by do1ar on 9/15/2017.
 */
public class Bullet {

    //Fields
    private double x,y;
    private int r, speed;
    private Color color;
    //bullet coordinate
    private double bulletDX, bulletDY, distX, distY, dist;
    //CONSTRUCT
    public Bullet(){
        x=GamePanel.player.getX();
        y=GamePanel.player.getY();
        r=2;
        speed = 10;

        distX = GamePanel.mouseX - x;
        distY = GamePanel.mouseY - y;
        dist = Math.sqrt(distX*distX + distY*distY);

        bulletDX = distX/dist *speed;
        bulletDY = distY/dist *speed;

        color = Color.WHITE;
    }

    //FUNCTION
    public boolean remove(){
        if(y<0 || x<0 || y>GamePanel.HEIGHT || x>GamePanel.WIDTH){
            return true;
        }
        return false;
    }

    public void update(){
        y+= bulletDY;
        x+= bulletDX;
    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillOval((int)(x), (int)(y), r, 2*r);
    }

    public double getY(){
        return y;
    }
    public double getX(){
        return x;
    }
    public int getR(){
        return r;
    }
}
