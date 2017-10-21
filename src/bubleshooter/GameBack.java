package bubleshooter;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;

/**
 * Created by do1ar on 9/15/2017.
 */

//настройка фона
public class GameBack {
    //Fields
    private Color color;
    private String gameOverText = "GAME OVER";

    public GameBack(){
        color = Color.blue;
    }

    //functions
    public void update(){
        color = Color.BLUE;
    }

    public void gameOver(Graphics2D g){
        color = Color.red;
        g.setColor(color);

        g.setFont(new Font ("Consolas", Font.BOLD, 40));
        long length = (int)g.getFontMetrics().getStringBounds(gameOverText, g).getWidth();
        color = Color.green;
        g.setColor(color);
        long height = (int)g.getFontMetrics().getStringBounds(gameOverText, g).getHeight();
        g.drawString(gameOverText, (int)GamePanel.WIDTH/2-length/2, GamePanel.HEIGHT/2 - height/2);

        color = Color.red;
        g.setColor(color);
        /*String text = "GAME OVER";
        FontRenderContext frc = g.getFontRenderContext();
        Font f = new Font("Serif", Font.BOLD,15);
        TextLayout tl = new TextLayout(text, f, frc);
        tl.draw(g, 20,20);*/
    }

    public void draw(Graphics2D g){

        g.setColor(color);
        g.fillRect(0,0, GamePanel.WIDTH, GamePanel.HEIGHT);

    }

    public void statistic(Graphics2D g){
        g.setColor(Color.pink);
        String enemyName = "Enemy "+GamePanel.enemies.size();
        String playerName = "Player "+(int)GamePanel.player.getHp();
        long haight = g.getFontMetrics().getHeight();
        g.setFont(new Font("Consolas", Font.BOLD,20));
        g.drawString(enemyName, 10,haight+10);
        long lengthPl = (int)g.getFontMetrics().getStringBounds(playerName, g).getWidth();
        g.drawString(playerName, GamePanel.WIDTH-20-lengthPl, haight+10);
    }
}
