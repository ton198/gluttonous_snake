import java.awt.*;

public class Mushroom extends SnakeBlock{

    public Mushroom() {
        super(RandomGenerator.getRandom(0, Constant.WIDTH), RandomGenerator.getRandom(1, Constant.HEIGHT), -1);
    }

    @Override
    public void setX(int x) {

    }

    @Override
    public void setY(int y) {

    }

    public void changeLocation() {
        x = RandomGenerator.getRandom(0, Constant.WIDTH);
        y = RandomGenerator.getRandom(1, Constant.HEIGHT);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x*Constant.SIZE, y*Constant.SIZE, Constant.SIZE, Constant.SIZE);
    }

    @Override
    public void refresh(Graphics g, boolean coverOriginal, boolean isHead) {

    }

    @Override
    public void drawNumber(Graphics g, int number) {

    }
}
