//Using Slick2D.

package ponggame;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.*;

public class Paddle {
    
    Rectangle top;
    Rectangle middle;
    Rectangle bottom;
    
    public Paddle(float xPos, float gameHeight) {
        top = new Rectangle(xPos, (gameHeight / 2) - 30, 10, 30);
        middle = new Rectangle(xPos, gameHeight / 2, 10, 30);
        bottom = new Rectangle(xPos, (gameHeight / 2) + 30, 10, 30);
    }
    
    public void controlPaddle(GameContainer gc, float gameHeight) {
        if(gc.getInput().isKeyDown(Input.KEY_UP)) {
            if(this.top.getMinY() > 0) {
                this.top.setY(this.top.getY() - 7.0f);
                this.middle.setY(this.middle.getY() - 7.0f);
                this.bottom.setY(this.bottom.getY() - 7.0f);
            }
        }
        else if(gc.getInput().isKeyDown(Input.KEY_DOWN)) {
            if(this.bottom.getMaxY() < gameHeight) {
                this.top.setY(this.top.getY() + 7.0f);
                this.middle.setY(this.middle.getY() + 7.0f);
                this.bottom.setY(this.bottom.getY() + 7.0f);
            }
        }
    }
    
    public void movePaddle(float ballPos) {
        if(ballPos < this.middle.getCenterY() - 7) {
            this.top.setY(this.top.getY() - 6.0f);
            this.middle.setY(this.middle.getY() - 6.0f);
            this.bottom.setY(this.bottom.getY() - 6.0f);
        }
        else if(ballPos > this.middle.getCenterY() + 7) {
            this.top.setY(this.top.getY() + 6.0f);
            this.middle.setY(this.middle.getY() + 6.0f);
            this.bottom.setY(this.bottom.getY() + 6.0f);
        }
    }
    
    public void fillPaddle(Graphics g) {
        g.setColor(Color.white);
        g.fill(this.top);
        g.fill(this.middle);
        g.fill(this.bottom);
    }
    
    public float getIntersection(Shape shape) {
        if(shape.intersects(this.middle)) {
            return 1;
        }
        else if(shape.intersects(this.top)) {
            return 0;
        }
        else if(shape.intersects(this.bottom)) {
            return 2;
        }
        return -1;
    }
    
    public float getSurfaceX() {
        return top.getMaxX();
    }
}
