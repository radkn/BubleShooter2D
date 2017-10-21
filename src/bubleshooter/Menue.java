package bubleshooter;

import java.awt.*;
import java.awt.BasicStroke;
/**
 * Created by do1ar on 9/19/2017.
 */
public class Menue {
    //Fields
    private int buttonWidth, buttonHaight;
    private Color color;
    private String buttonText;
    private int transp;
    private String gameOverText;

    //construct
    public Menue(){
        buttonWidth = 120;
        buttonHaight = 60;
        buttonText = "PLAY";
        transp = 0;
        color = Color.GREEN;
        gameOverText = "GAME OVER";
    }
    //Function
    public void update(){
        if((GamePanel.mouseX > GamePanel.WIDTH/2-buttonWidth &&  GamePanel.mouseX < GamePanel.HEIGHT/2+buttonWidth/2) &&
                (GamePanel.mouseY > GamePanel.HEIGHT/2-buttonHaight &&  GamePanel.mouseY < GamePanel.HEIGHT/2+buttonHaight/2) )
        {
            transp = 60;
            if(GamePanel.leftMouse){
                GamePanel.state = GamePanel.STATES.PLAY;
            }
        }else transp = 0;
    }

    public void draw(Graphics2D g){
        g.setColor(color);
        //g.getStroke(new BasicStroke(3));
        g.drawRect(GamePanel.WIDTH/2-buttonWidth/2,GamePanel.HEIGHT/2-buttonHaight/2, buttonWidth, buttonHaight);
        g.setColor(new Color(255,255,255,transp));
        g.fillRect(GamePanel.WIDTH/2-buttonWidth/2,GamePanel.HEIGHT/2-buttonHaight/2, buttonWidth, buttonHaight);
        g.setColor(color);
        g.setFont(new Font("Consolas", Font.BOLD, 40));
        long length = (int)g.getFontMetrics().getStringBounds(buttonText, g).getWidth();
        g.drawString(buttonText, (int)(GamePanel.WIDTH/2 - length/2), (int)(GamePanel.HEIGHT/2 + buttonHaight/4));
    }

    //TODO draw game over screen
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
    }
}
