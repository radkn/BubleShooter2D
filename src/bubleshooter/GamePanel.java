package bubleshooter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by do1ar on 9/15/2017.
 */
public class GamePanel extends JPanel implements Runnable{

    //Field
    /*===*///ширина, высота экрана
    public static int WIDTH = 600;
    public static int HEIGHT = 600;

    public static int mouseX, mouseY;
    public static boolean leftMouse;



    private double millisToFPS;
    private int FPS, sleepTime;
    private long timerFps;

    //Перечисление пунктов меню
    public enum STATES{
        MENUE, PLAY, GAMEOVER
    }


     public static STATES state = STATES.MENUE;

    /*===*///поток игры
    private Thread thread;

    /*===*///
    private BufferedImage image;
    private Graphics2D g;

    public static GameBack background;
    public static Player player;
    public static Wave wave;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    public static Menue menue;
    // Constructor
    public GamePanel(){
        super();

        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setFocusable(true);
        requestFocus();

        //слушатель клавиатуры
        addKeyListener(new Listeners());

        //слушатель мыши
        addMouseMotionListener(new Listeners());
        addMouseListener(new Listeners());

    }

    //Functions

    public void start(){
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

        //переменные регулировки FPS
        FPS = 30;
        millisToFPS = 1000/FPS;
        sleepTime = 0;

        leftMouse = false;


        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        g =(Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
       /* String name = g.getClass().getName();
        System.out.println(name);*/
        background = new GameBack();
        player = new Player();
        bullets = new ArrayList<Bullet>();
        enemies = new ArrayList<Enemy>();
        menue = new Menue();

      /*  //TODO remuve
        enemies.add(new Enemy(1,1));
        enemies.add(new Enemy(1,1));
*/
        wave = new Wave();

        //AIM draw
        Toolkit kit = Toolkit.getDefaultToolkit();
        BufferedImage buffered = new BufferedImage(16,16, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g3 = (Graphics2D)buffered.getGraphics();
        g3.setColor(new Color(255,255,255));
        g3.drawOval(0,0,4,4);
        g3.drawLine(2,0,2,4);
        g3.drawLine(0,2,4,2);
        Cursor myCursor = kit.createCustomCursor(buffered, new Point(3,3), "myCursor");
        g3.dispose();

        while (true){//TODO States
            this.setCursor(myCursor);

            timerFps = System.nanoTime();
            if(state.equals(STATES.MENUE)){
                background.update();
                background.draw(g);
                menue.update();
                menue.draw(g);
                gameDraw();
            }
            if(state.equals(STATES.PLAY)){
                gameUpdate();
                gameRender();
                gameDraw();
            }
            if(state.equals(STATES.GAMEOVER)){

                //TODO create method or class to do Game Over end set to zero all variables
                enemies.clear();
                wave.setWaveNumber(1);

                //System.out.println("GAME OVER");
                background.draw(g);
                background.gameOver(g);
                gameDraw();
            }

            //long timer = System.nanoTime();

            //блок вычисления FPS
            timerFps = (System.nanoTime() - timerFps)/1000000;
            if(millisToFPS > timerFps){
                sleepTime = (int)(millisToFPS - timerFps);
            }else sleepTime = 1;
            //long elapsed = (System.nanoTime() - timer)/1000;
            //System.out.println(bullets.size() + "   "+elapsed);
            try {
                thread.sleep(sleepTime); //TODO FPS
                //System.out.println(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timerFps = 0;
            sleepTime = 1;
            /*#########*/

        }
    }

    public void gameUpdate(){
        //Background update
        background.update();


        //player update
        player.update();

        //Bullet update
        for (int i = 0; i < bullets.size(); i++){
            bullets.get(i).update();
            boolean remove = bullets.get(i).remove();
            if(remove){
                bullets.remove(i);
                i--;
            }
        }

        /*//ENEMY update
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).update();
        }*/

        //COLLIDE bull-enem
        for(int i = 0; i < enemies.size(); i++){
            Enemy e = enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();
            for(int j = 0; j < bullets.size(); j++){
                Bullet b = bullets.get(j);
                double bx = b.getX();
                double by = b.getY();
                double dx = ex - bx;
                double dy = ey - by;
                double dist = Math.sqrt(dx*dx + dy*dy);
                if(dist < e.getR() + b.getR()) {
                    e.hit();
                    bullets.remove(j);
                    j--;
                    boolean remove = e.remove();
                    if(remove){
                        enemies.remove(i);
                        i--;
                        break;
                    }
                }
            }

        }

        //COLLIDE play-enem
        for(int i = 0; i < enemies.size(); i++){
            Enemy e = enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();

            double px = player.getX();
            double py = player.getY();
            double dx = ex - px;
            double dy = ey - py;
            double dist = Math.sqrt(dx*dx + dy*dy);
            if((int)dist < e.getR() + player.getR()) {
                e.hit();
                player.hit();
                boolean removeE = e.remove();
                if(removeE){
                    enemies.remove(i);
                    i--;
                }

                boolean removeP = player.remove();
                if(removeP){
                    state = STATES.GAMEOVER;
                    //background.gameOver(g);
                }
            }
        }

        //ENEMY update
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).update();
        }

        //WAVE update
        wave.update();
    }

    public void gameRender(){
        //Background draw
        background.draw(g);
        background.statistic(g);
        //player draw
        player.draw(g);

        //Bullet draw
        for (int i = 0; i < bullets.size(); i++){
            bullets.get(i).draw(g);
        }

        //ENEMY draw
        for(int i = 0; i < enemies.size(); i++){
            enemies.get(i).draw(g);
        }

        //WAVE draw
        if(wave.showWave())wave.draw(g);
    }

    private void gameDraw(){
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0 , 0, null);
        g2.dispose();
    }
}
