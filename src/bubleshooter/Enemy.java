package bubleshooter;

import java.awt.*;

/**
 * Created by do1ar on 9/16/2017.
 */
public class Enemy {
    //FIELDS

    private double x, y, speed, dx, dy, hp, distPlayEnem;

    private int r;
    private Color color;
    private int type, rank;
    //CONSTRUCT
    public Enemy(int type, int rank){

        switch (type){
            case 1:
                color = Color.GREEN;
                switch (rank){
                case 1:
                    x=Math.random()*GamePanel.WIDTH;
                    y = 0;
                    r = 7;
                    speed = 5;
                    hp = 100;
                    double angle = Math.toRadians(Math.random()*360);
                    dx = Math.sin(angle)*speed;
                    dy = Math.cos(angle)*speed;
                    break;

            }

                break;

        }




    }

    //FUNCTIONS
    public boolean remove(){
        if(hp <= 0){
            return true;
        }
        System.out.println("EnemyHP = " + hp);
        return false;
    }
    public void update(){
        x+=dx;
        y+=dy;

        //collide with border
        if((x < 0 && dx < 0) ||(x > GamePanel.WIDTH && dx > 0)){
            dx =-dx;
        }
        if((y < 0 && dy < 0) ||(y > GamePanel.HEIGHT && dy > 0)){
            dy =-dy;
        }

        //collide with player
        distPlayEnem = Math.abs(Math.sqrt(Math.pow(GamePanel.player.getX()-x,2)+Math.pow(GamePanel.player.getY()-y,2)));
        if(distPlayEnem<12){
            System.out.println(distPlayEnem);
            System.out.println(GamePanel.player.getX() + "  " + GamePanel.player.getY());
            System.out.println(x + " " + y);
            boolean xBack = GamePanel.player.getX()-x < 0 && dx > 0 || GamePanel.player.getX()-x > 0 && dx < 0;
            boolean yBack = GamePanel.player.getY()-y < 0 && dy > 0 || GamePanel.player.getY()-y > 0 && dy < 0;
            if(xBack || yBack){
                if(GamePanel.player.getX()-x < 0 && dx > 0){
                    y += 5;
                }
                if(GamePanel.player.getX()-x > 0 && dx < 0){
                    y -= 5;
                }
                if(GamePanel.player.getY()-y < 0 && dy > 0){
                    x +=5;
                }
                if(GamePanel.player.getY()-y > 0 && dy < 0){
                    x -=5;
                }
            }else {
                dx = -dx;
                dy = -dy;
            }
        }
    }

    public void draw(Graphics2D g){
        g.setColor(color);
        g.fillOval((int)x-r, (int)y-r, 2*r,2*r);
        g.setStroke(new BasicStroke(3));
        g.setColor(color.darker());
        g.drawOval((int)x-r, (int)y-r, 2*r,2*r);
        g.setStroke(new BasicStroke(3));
    }

    public void hit(){
        hp -=50;
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
