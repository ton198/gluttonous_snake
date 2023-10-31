import java.awt.*;

public class SnakeBlock {
    
    protected int x, y, oldX, oldY;
    private final int ID;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getID() {
        return ID;
    }

    public SnakeBlock(int x, int y, int ID) {
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        this.ID = ID;
    }

    public void refresh(Graphics g, boolean coverOriginal, boolean isHead) {
        if (isHead) {
            g.setColor(Color.RED);
            g.fillRect(x*Constant.SIZE, y*Constant.SIZE, Constant.SIZE, Constant.SIZE);
        } else {
            g.setColor(Color.ORANGE);
            g.fillRect(x*Constant.SIZE, y*Constant.SIZE, Constant.SIZE, Constant.SIZE);
        }
        if (coverOriginal && (oldX != x || oldY != y)) {
            g.clearRect(oldX * Constant.SIZE, oldY * Constant.SIZE, Constant.SIZE, Constant.SIZE);
        }
        oldX = x;
        oldY = y;
    }

    public void drawNumber(Graphics g, int number) {
        g.setColor(Color.BLACK);
        g.drawString(Integer.toString(number), x*Constant.SIZE + 1, y*Constant.SIZE + 22);
    }
}
