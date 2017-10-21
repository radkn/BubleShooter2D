package bubleshooter;

import java.awt.*;

/**
 * Created by do1ar on 9/17/2017.
 */
public class Wave {

    //Fields
    private int waveNumber, waveMutiplier;
    private String waveText = new String();

    private long waveTimer, waveDelay, waveTimerDiff;
    //Construct

    public Wave(){
        waveNumber = 1;
        waveMutiplier = 5;
        waveTimer = 0;
        waveDelay = 5000;
        waveTimerDiff = 0;

        waveText = "В О Л Н А";
    }


    public void update(){
        //set timer before wave
        if(GamePanel.enemies.size() == 0 && waveTimer == 0){
            waveTimer = System.nanoTime();
        }

        //counting time to wave
        if(waveTimer > 0){
            waveTimerDiff += (System.nanoTime() - waveTimer)/1000000;
            waveTimer = System.nanoTime();
        }

        //checked on time to wave
        if(waveTimerDiff > waveDelay){
            createEnemies();

            //down timers
            waveTimer = 0;
            waveTimerDiff = 0;
        }
    }

    public void draw(Graphics2D g){

        double divider = waveDelay/180;
        double alpha = waveTimerDiff/divider;
        alpha = 255 * Math.sin(Math.toRadians(alpha));
        if(alpha < 0) alpha=0;
        if(alpha > 255) alpha = 255;
        g.setFont(new Font("consolas", Font.PLAIN, 20));
        g.setColor(new Color(255,255,255, (int)alpha));
        String s = waveNumber + "-АЯ " + waveText;
        long length = (int)g.getFontMetrics().getStringBounds(s,g).getWidth();
        g.drawString(s, GamePanel.WIDTH/2 - (int)(length/2), GamePanel.HEIGHT/2);

    }

    public boolean showWave(){
        if(waveTimer != 0) return true;
        else return false;
    }

    public void createEnemies(){
        int enemyCount = waveNumber*waveMutiplier;
        //if(waveNumber < 4){
            while (enemyCount > 0){
                int type = 1, rank = 1;
                GamePanel.enemies.add(new Enemy(type, rank));
                enemyCount -= type*rank;
            }
        //}
        waveNumber++;
    }

    public void setWaveNumber(int n){
        this.waveNumber = n;
    }

}
