package bubleshooter;

import java.awt.*;
/**
 * Created by do1ar on 9/15/2017.
 */

//
public class Player {

    //fields
    private double x, y, dx, dy, hp;
    private int r;
    private int speed;
    private Color color1;
    private Color color2;


    public static boolean up, down, left, right, isFiring;


    //Constructor
    public Player(){
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT / 2;
        speed = 5;
        hp=100;
        r = 5;

        dx = 0;
        dy = 0;
        color1 = Color.WHITE;

        up = false;
        down = false;
        left = false;
        right = false;
        isFiring = false;

    }
    //Functions
    public double getX(){
        return this.x;
    }

    public double getY(){
        return this.y;
    }
    public double getR(){
        return this.r;
    }
    public double getHp(){
        return this.hp;
    }

    public void update(){

        if(isFiring){
            GamePanel.bullets.add(new Bullet());
        }
        if(up && y > r){
            dy = -speed;
        }
        if(down && y < GamePanel.HEIGHT - r){
            dy = speed;
        }

        if(left && x > r){
            dx = -speed;
        }
        if(right && x < GamePanel.WIDTH - r){
            dx = speed;
        }
        if(up && left || up && right || down && left || down && right){
            double angle = Math.toRadians(45);
            dy = dy * Math.sin(angle);
            dx = dx * Math.cos(angle);
        }

        y += dy;
        x += dx;

        dy = 0;
        dx = 0;


    }


    public void draw(Graphics2D g){
        g.setColor(color1);
        g.fillOval((int)(x - r), (int)(y - r), 2*r, 2*r);
        g.setStroke(new BasicStroke(2));
        g.setColor(color1.darker());
        g.drawOval((int)(x - r), (int)(y - r), 2*r, 2*r);
        g.setStroke(new BasicStroke(1));
    }

    public boolean remove(){
        if(hp <= 0){
            hp=100;
            return true;
        }
        return false;
    }

    public void hit(){
        hp-=5;
        System.out.println("PlayerHP = " + hp);
    }
}
