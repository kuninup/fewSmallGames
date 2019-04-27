package plane;

import java.awt.Color;
import java.awt.Graphics;

class HealthManager extends Thread implements BasicData {

    public void run() {
        double rate;
        int length, timer = 2, count = 10000;
        Color c = Color.GREEN;
        Graphics graphics = FRAME.health.getGraphics();
        graphics.setColor(c);
        graphics.fillRect(0, 0, FRAME_WIDTH, HEALTH_BAR_HEIGHT);
        graphics.setColor(Color.WHITE);
        while (FRAME.playing) {
            if (!FRAME.pause) {
//            graphics.clearRect(0, 0, FRAME_WIDTH, HEALTH_BAR_HEIGHT);
                rate = (0.0 + PLAYER.health) / PLAYER.maxHealth;
                if (PLAYER.health < PLAYER.maxHealth * 0.05) {
                    PLAYER.health++;
                }
                if (PLAYER.score > count) {
                    count += 10000;
                    new Assistance();
                }
                length = (int) (rate * FRAME_WIDTH);
                graphics.setColor(Color.BLACK);
                graphics.fillRect(length, 0, FRAME_WIDTH, HEALTH_BAR_HEIGHT);
                if (rate > 0) {
                    graphics.setColor(new Color((int) ((1 - rate) * 225 + 30),
                            (int) (rate * 225 + 30 > 255 ? 255 : rate * 225 + 30), (int) (rate * 30)));
                }
                graphics.fillRect(0, 0, length, HEALTH_BAR_HEIGHT);
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            if (PLAYER.health < 1) {
                if (timer-- < 0) {
                    break;
                }
            }
        }
    }
}
