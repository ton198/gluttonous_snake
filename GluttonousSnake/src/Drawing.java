import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

public class Drawing extends JFrame {

    private int currentID = 0;

    private int getID() {
        return currentID++;
    }

    private int direction;

    private Graphics g;

    private boolean locker = true;

    private Mushroom mushroom;

    LinkedList<SnakeBlock> snakeBlocks;

    private boolean isUpdate = true;

    public Drawing() {
        addListener();
        initSnake();
        initMushroom();
        lunchWindow();
        startUpdateThread();
    }

    private void startUpdateThread() {
        new Thread(() -> {
            while (isUpdate) {
                try {
                    long t = System.currentTimeMillis();
                    SwingUtilities.invokeAndWait(this::draw);
                    locker = true;
                    long sleepTime = 1000 / Constant.FPS - (System.currentTimeMillis()-t);
                    if (sleepTime > 0) {
                        Thread.sleep(sleepTime);
                    }
                } catch (InterruptedException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initSnake() {
        snakeBlocks = new LinkedList<>();
        snakeBlocks.add(new SnakeBlock(Constant.WIDTH/2, Constant.HEIGHT/2, getID()));
        direction = RandomGenerator.getRandom(0, 4);
        for (int i = 0;i < Constant.primateLength;i++) {
            snakeBlocks.add(new SnakeBlock(Constant.WIDTH/2, Constant.HEIGHT/2, getID()));
        }
    }

    private void initMushroom() {
        mushroom = new Mushroom();
    }

    private void addListener() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (locker) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP -> {
                            if (direction != Constant.DOWN) {
                                direction = Constant.UP;
                                locker = false;
                            }
                        }
                        case KeyEvent.VK_DOWN -> {
                            if (direction != Constant.UP) {
                                direction = Constant.DOWN;
                                locker = false;
                            }
                        }
                        case KeyEvent.VK_LEFT -> {
                            if (direction != Constant.RIGHT) {
                                direction = Constant.LEFT;
                                locker = false;
                            }
                        }
                        case KeyEvent.VK_RIGHT -> {
                            if (direction != Constant.LEFT) {
                                direction = Constant.RIGHT;
                                locker = false;
                            }
                        }
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_J) {
                    addBlock();
                }
            }
        });

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void lunchWindow() {
        this.setSize(Constant.WIDTH * Constant.SIZE, Constant.HEIGHT * Constant.SIZE);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        g = this.getGraphics();
        g.setFont(g.getFont().deriveFont(25f));
        this.setTitle("score: 0");
    }

    private void addBlock() {
        SnakeBlock last = snakeBlocks.getLast();
        snakeBlocks.addLast(new SnakeBlock(last.getX(), last.getY(), getID()));
        this.setTitle("score: " + (snakeBlocks.size() - Constant.primateLength - 1));
    }

    private void draw() {
        move();
        updateMushroom();
    }

    private void updateMushroom() {
        SnakeBlock head = snakeBlocks.getFirst();
        if (isLocationSame(head, mushroom)) {
            mushroom.changeLocation();
            addBlock();
        }
        mushroom.draw(g);
    }

    private void move() {
        SnakeBlock block = snakeBlocks.removeLast();
        SnakeBlock currentHead = snakeBlocks.getFirst();
        boolean cover = isCover(block);
        switch (direction) {
            case Constant.UP -> {
                block.setX(currentHead.getX());
                block.setY(currentHead.getY() - 1);
            }
            case Constant.DOWN -> {
                block.setX(currentHead.getX());
                block.setY(currentHead.getY() + 1);
            }
            case Constant.LEFT -> {
                block.setX(currentHead.getX() - 1);
                block.setY(currentHead.getY());
            }
            case Constant.RIGHT -> {
                block.setX(currentHead.getX() + 1);
                block.setY(currentHead.getY());
            }
        }
        snakeBlocks.getFirst().refresh(g, false, false);
        block.refresh(g, !cover, true);
        snakeBlocks.addFirst(block);
        if (block.getX() < 0 || block.getX() > Constant.WIDTH - 1 || block.getY() < 1 || block.getY() > Constant.HEIGHT - 1) { //whether head touches edge.
            dead();
        }
    }

    private void stopUpdateThread() {
        isUpdate = false;
    }

    private boolean isLocationSame(SnakeBlock b1, SnakeBlock b2) {
        return b1.getX() == b2.getX() && b1.getY() == b2.getY();
    }

    private void dead() {
        stopUpdateThread();
        Font f = g.getFont();
        g.setFont(f.deriveFont(40f));
        g.setColor(Color.BLACK);
        g.drawString("You dead", Constant.WIDTH*Constant.SIZE/2-90, Constant.HEIGHT*Constant.SIZE/2-80);
        g.drawString("Score: " + (snakeBlocks.size()-4), Constant.WIDTH*Constant.SIZE/2-80, Constant.HEIGHT*Constant.SIZE/2);
    }

    private boolean isCover(SnakeBlock block) {
        for (SnakeBlock currentBlock : snakeBlocks) {
            if (currentBlock.getID() != block.getID() && isLocationSame(block, currentBlock)) {
                return true;
            }
        }
        return false;
    }
}
