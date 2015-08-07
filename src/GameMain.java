//Using Slick2D.

package ponggame;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;
import org.newdawn.slick.gui.*;
import java.util.Random;
import java.awt.Font;

public class GameMain extends BasicGame{
    
    static int width = 640;
    static int height = 480;
    Vector2f ballVelocity;
    Circle ball;
    Paddle paddlePlayer;
    Paddle paddleAI;
    Rectangle middleLine;
    Random random;
    Font scoreFont;
    TrueTypeFont score;
    Color darkGray;
    int countPlayer;
    int countAI;
    
    public GameMain(String title) {
        super(title);
    }
    
    public void init(GameContainer gc) {
        gc.setShowFPS(false);
        gc.getInput().enableKeyRepeat();
        
        ballVelocity = new Vector2f(0, 0);
        ball = new Circle(width / 2, height / 2, 10);
        paddlePlayer = new Paddle(5, height);
        paddleAI = new Paddle(width - 15, height);
        middleLine = new Rectangle(width / 2, 0, 5, height);
        scoreFont = new Font("Impact", Font.PLAIN, 60);
        score = new TrueTypeFont(scoreFont, false);
        random = new Random();
        darkGray = new Color(50, 50, 50);
        
        countPlayer = 0;
        countAI = 0;
        
        if(random.nextInt(2) == 0) {
            ballVelocity = new Vector2f(9, 3);
        }
        else {
            ballVelocity = new Vector2f(9, -3);
        }
    }
    
    public void render(GameContainer gc, Graphics g) {
        score.drawString(width / 4 - 30, 10, Integer.toString(countPlayer), darkGray);
        score.drawString(width * 3 / 4 - 30, 10, Integer.toString(countAI), darkGray);
        
        g.setColor(darkGray);
        g.fill(middleLine);
        
        paddlePlayer.fillPaddle(g);
        paddleAI.fillPaddle(g);
        
        g.setColor(new Color(255, 160, 80));
        g.fill(ball);   
    }
    
    public void update(GameContainer gc, int delta) {
        paddlePlayer.controlPaddle(gc, height);
        
        ball.setLocation(ball.getX() + ballVelocity.getX(), ball.getY() + ballVelocity.getY());
        
        if(ball.getMaxX() >= width + 30) {
            countPlayer++;
            ball.setX(width / 2);
            ball.setY(height / 2);
            if(random.nextInt(2) == 0) {
                ballVelocity.set(-9, 3);
            }
            else {
                ballVelocity.set(-9, -3);
            }
        }
        if(ball.getMinX() <= -30) {
            countAI++;
            ball.setX(width / 2);
            ball.setY(height / 2);
            
            if(random.nextInt(2) == 0) {
                ballVelocity.set(9, 3);
            }
            else {
                ballVelocity.set(9, -3);
            }
        }
        
        if(paddlePlayer.getIntersection(ball) != -1) {
            float intersectPos = paddlePlayer.getIntersection(ball);
            if(Math.abs((double)ballVelocity.y / ballVelocity.x) < 0.1) {
                if(random.nextInt(2) == 0) {
                    ballVelocity = new Vector2f(9, 3);
                }
                else {
                    ballVelocity = new Vector2f(9, -3);
                }
            }
            else if(intersectPos == 0) {
                ballVelocity.x = -ballVelocity.getX();
                ballVelocity.y = ballVelocity.getY() / 3;
            }
            else if(intersectPos == 1) {
                ballVelocity.x = -ballVelocity.getX();
            }
            else if(intersectPos == 2) {
                ballVelocity.x = -ballVelocity.getX();
                ballVelocity.y = ballVelocity.getY() * 3;
            }
        }
        else if(paddleAI.getIntersection(ball) != -1) {
            float intersectPos = paddleAI.getIntersection(ball);
            if(intersectPos == 0) {
                ballVelocity.x = -ballVelocity.getX();
                ballVelocity.y = ballVelocity.getY() / 1.5f;
            }
            else if(intersectPos == 1) {
                ballVelocity.x = -ballVelocity.getX();
            }
            else if(intersectPos == 2) {
                ballVelocity.x = -ballVelocity.getX();
                ballVelocity.y = ballVelocity.getY() * 1.5f;
            }
        }
        else if(ball.getY() <= 0 || ball.getY() >= height - 20) {
            ballVelocity.y = -ballVelocity.getY();
        }
        
        float yPos = ball.getCenterY();
        paddleAI.movePaddle(yPos);
    }
    
    public static void main(String[] args) throws SlickException {
        AppGameContainer app = new AppGameContainer(new GameMain("Pong!"));
        app.setDisplayMode(width, height, false);
        app.setTargetFrameRate(60);
        app.start();
    }
}
