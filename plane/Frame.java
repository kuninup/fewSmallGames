package plane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Vector;

public class Frame extends JFrame implements BasicData, KeyListener, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1L;
	
	private static final int NONE = -1;
    private static final int MAIN_MENU = 1;
    private static final int PAUSING_MENU = 2;
    private static final int START = 32;
    private static final int PAUSE = 64;
    private static final int EXIT = 128;
    private static final int SAVE = 256;
    static final int LOAD = 256;

    EnemyBase eb = new EnemyBase();
    int command;
    JPanel health;
    boolean playing, pause, guide;
    ObjectOutputStream out;
    ObjectInputStream in;

    private int currentMenu;
    private Graphics graphics;
    private char lock;

    public static void main(String[] args) {
        FRAME.display();
        FRAME.showMenu();
    }

    Frame() {
        super("飞机大战");
        health = new JPanel();
        health.setPreferredSize(new Dimension(FRAME_WIDTH, HEALTH_BAR_HEIGHT));
        add(health, BorderLayout.SOUTH);

        playing = false;
        pause = true;
        guide = true;
        lock = 0;
        currentMenu = MAIN_MENU;

        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.WHITE);
        setLocationRelativeTo(null);
        addKeyListener(this);
        addMouseListener(this);
    }

    void clear() {
        ACTIVE_BULLET.clear();
        ACTIVE_ENEMY.clear();
        PLAYER.fresh(new Player());
    }

    void display() {
        setVisible(true);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphics = getGraphics();
    }

    void showMenu() {
        switch (currentMenu) {
            case MAIN_MENU:
                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font("微软雅黑", Font.PLAIN, 40));
                graphics.drawString("飞  机  大  战", 220, 200);
                graphics.setFont(LARGE_FONT);
                graphics.drawString("START", 260, 400);
                graphics.drawString("LOAD", 265, 530);
                graphics.drawString("EXIT", 268, 660);
                break;

            case PAUSING_MENU:
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                graphics.setColor(Color.PINK);
                graphics.fillRect(0, FRAME_HEIGHT / 2 - 230, FRAME_WIDTH, 450);
                graphics.setColor(Color.BLACK);
                graphics.setFont(new Font("Arial", Font.PLAIN, 10));
                graphics.drawString("gkp", FRAME_WIDTH - 40, FRAME_HEIGHT - 30);
                graphics.drawString("gkp", FRAME_WIDTH - 40, 50);
                graphics.drawString("gkp", 20, 50);
                graphics.drawString("gkp", 20, FRAME_HEIGHT - 30);
                graphics.setFont(LARGE_FONT);
                graphics.drawString("Resume", FRAME_WIDTH / 2 - 45, FRAME_HEIGHT / 2 - 150);
                graphics.drawString("Save", FRAME_WIDTH / 2 - 28, FRAME_HEIGHT / 2 - 50);
                graphics.drawString("Restart", FRAME_WIDTH / 2 - 43, FRAME_HEIGHT / 2 + 50);
                graphics.drawString("Return", FRAME_WIDTH / 2 - 41, FRAME_HEIGHT / 2 + 150);
                break;

            default:
                break;
        }
    }

    void load() {
        try {
            in = new ObjectInputStream(new FileInputStream("Plane.gbd"));
            String s = (String) in.readObject();
            if (s.equals(CHARACTERISTICS)) {
                clear();
                PLAYER.fresh((Player) in.readObject());
                Vector<Entity> v = (Vector<Entity>) in.readObject();
                for (Entity entity : v) {
                    ACTIVE_ENEMY.add(entity);
                }
                v = (Vector<Entity>) in.readObject();
                for (Entity entity : v) {
                    ACTIVE_BULLET.add((Bullet) entity);
                }
                int[] t = new int[5];
                for (int i : t) {
                    i = in.readInt();
                }
                eb.setData(t);
            }
            start();
            in.close();
        } catch (Throwable e1) {
//            graphics.setColor(Color.BLACK);
//            graphics.fillRect(400, 350, 150, 100);
            e1.printStackTrace();
        }
        PLAYER.firing = false;
    }

    void start() {
        int left = 150, right = 350;
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        playing = true;
        (new Thread(new MotionManager())).start();
        (new HealthManager()).start();
        pause = true;
        guide = true;
        (new BulletManager()).start();
        (eb = new EnemyBase()).start();
        graphics.setFont(LARGE_FONT);
        graphics.setColor(Color.BLACK);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        graphics.drawString("[ESC]", left, 350);
        graphics.drawString("Menu", right, 350);
        graphics.drawString("↑  ↓  ←  →", left, 410);
        graphics.drawString("Move", right, 410);
        graphics.drawString("[SPACE]", left, 470);
        graphics.drawString("Fire", right, 470);
        graphics.drawString("Ready? Press any key to start", 140, 700);
        try {
            Thread.sleep(40);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PLAYER.firing = false;
    }

    private boolean inRange(int x, int y, int left, int right, int lower, int upper) {
        return (x >= left && x <= right && y >= lower && y <= upper);
    }

    void save() {
        try {
            out = new ObjectOutputStream(new FileOutputStream("Plane.gbd"));
            out.writeObject(CHARACTERISTICS);
            out.writeObject(PLAYER);
            out.writeObject(ACTIVE_ENEMY);
            out.writeObject(ACTIVE_BULLET);
            int[] d = eb.getData();
            for (int i : d) {
                out.writeInt(i);
            }
            out.writeObject("#");
            out.close();
            graphics.setFont(SMALL_FONT);
            graphics.drawString("Complete", FRAME_WIDTH / 2 - 42, FRAME_HEIGHT / 2 - 20);
            graphics.setFont(LARGE_FONT);
            Thread.sleep(1500);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    void over() {
        graphics.setColor(Color.PINK);
        graphics.fillRect(0, 380, FRAME_WIDTH, 150);
        graphics.setColor(Color.BLACK);
        graphics.setFont(LARGE_FONT);
        graphics.drawString("Game over. Your Score: " + PLAYER.score, 146, 420);
        graphics.drawString("Press [ESC] for main menu", 145, 460);
        playing = false;
    }

    public void mouseReleased(MouseEvent e) {
        int x = e.getX(), y = e.getY();
        lock = 0;
        switch (currentMenu) {
            case MAIN_MENU:
                if (inRange(x, y, 240, 350, 360, 420)) {
                    command = START;
                    currentMenu = NONE;
                    clear();
                    start();
                    return;
                }
                if (inRange(x, y, 240, 350, 610, 680)) {
                    command = EXIT;
                    System.exit(0);
                    return;
                }
                if (inRange(x, y, 240, 350, 480, 560)) {
                    command = LOAD;
                    load();
                    currentMenu = NONE;
                    return;
                }
                break;

            case PAUSING_MENU:
                if (inRange(x, y, 250, 350, 280, 330)) {
                    pause = false;
                    currentMenu = NONE;
                }

                if (inRange(x, y, 250, 350, 380, 430)) {
                    save();
                    currentMenu = NONE;
                    pause = false;
                }

                if (inRange(x, y, 250, 350, 480, 530)) {
                    showMenu();
                    lock = 'n';
                    graphics.setFont(SMALL_FONT);
                    graphics.drawString("Restart? Press [Ctrl] to confirm", FRAME_WIDTH / 2 - 130, FRAME_HEIGHT / 2 + 80);
                    graphics.setFont(LARGE_FONT);
                }

                if (inRange(x, y, 250, 350, 580, 630)) {
                    showMenu();
                    lock = 'b';
                    graphics.setFont(SMALL_FONT);
                    graphics.drawString("Return? Press [Ctrl] to confirm", FRAME_WIDTH / 2 - 130, FRAME_HEIGHT / 2 + 180);
                    graphics.setFont(LARGE_FONT);
                }
                break;
        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (!playing && currentMenu == NONE && key == KeyEvent.VK_ESCAPE) {
            currentMenu = MAIN_MENU;
            showMenu();
            return;
        }

        if (guide) {
            guide = false;
            pause = false;
            return;
        }

        if (PLAYER.health > 0) {
            switch (key) {
                case KeyEvent.VK_LEFT:
                    PLAYER.dx = -PLAYER_SPEED;
                    break;

                case KeyEvent.VK_RIGHT:
                    PLAYER.dx = PLAYER_SPEED;
                    break;

                case KeyEvent.VK_UP:
                    PLAYER.dy = -PLAYER_SPEED;
                    break;

                case KeyEvent.VK_DOWN:
                    PLAYER.dy = PLAYER_SPEED;
                    break;

                case KeyEvent.VK_SPACE:
                    if (!PLAYER.firing) {
                        PLAYER.fire();
                        PLAYER.firing = true;
                    }
                    break;
            }
        }

        if (key == KeyEvent.VK_ESCAPE && currentMenu != MAIN_MENU) {
            pause = !pause;
            currentMenu = pause ? PAUSING_MENU : NONE;
            showMenu();
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_CONTROL && lock != 0) {
            pause = false;
            try {
                Thread.sleep(80);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            playing = false;
            try {
                Thread.sleep(80);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            if (lock == 'n') {
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
                currentMenu = NONE;
                clear();
                start();
            }

            if (lock == 'b') {
                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
                currentMenu = MAIN_MENU;
                showMenu();
            }
        }

        lock = 0;

        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN
                || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_LEFT) {
            PLAYER.dx = 0;
            PLAYER.dy = 0;
        }
    }


    public void keyTyped(KeyEvent e) {
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {

    }
}
