package plane;

import java.awt.Color;
import java.awt.Graphics;

class MotionManager implements BasicData, Runnable {

    private Graphics graphics;
//    private static final Color SILVER = new Color(0xc0, 0xc0, 0xc0);
//    private static final int SCORE_BOARD_HEIGHT = 40;
//    private static final int SCORE_BOARD_WIDTH = 100;

    MotionManager() {
        graphics = FIELD_BUFFER.getGraphics();
    }

    public void run() {
        Entity e;
        int timer = 2, timer2 = 12;
        while (FRAME.playing) {
            if (!FRAME.pause) {
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, FRAME_WIDTH, FIELD_HEIGHT);
                if (!FRAME.guide) {
                    for (int i = 0; i < ACTIVE_BULLET.size(); i++) {
                        e = ACTIVE_BULLET.get(i);
                        e.move();
                        ((Bullet) e).crash();
                        e.draw(graphics);
                    }
                    for (int i = 0; i < ACTIVE_ENEMY.size(); i++) {
                        e = ACTIVE_ENEMY.get(i);
                        e.move();
                        e.draw(graphics);
                    }
                }
                if (PLAYER.health > 0) {
                    PLAYER.move();
                    PLAYER.crash();
                    PLAYER.draw(graphics);
                } else {
                    if (timer-- < 0) {
                        FRAME.over();
                        break;
                    }
                }
                graphics.setColor(Color.BLACK);
                graphics.setFont(LARGE_FONT);
                graphics.drawString("Score: " + PLAYER.score, 40, 70);
                FRAME.getGraphics().drawImage(FIELD_BUFFER, 0, -4, null);
                while (FRAME.command == Frame.LOAD && timer2 > 0) {
                    timer2--;
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}
