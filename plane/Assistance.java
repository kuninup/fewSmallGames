package plane;

import java.awt.*;

public class Assistance extends Entity {

    Assistance() {
        x = FRAME_WIDTH / 2;
        y = 12;
        dy = 5;
        radius = 40;
        mark = -16;
        maxHealth = health = 10 * PLAYER.bullet;
        damage = -100;
        ACTIVE_ENEMY.add(this);
    }

    void draw(Graphics graphics) {
        graphics.setColor(Color.GREEN);
        graphics.fillOval(x - radius, y - radius, radius, radius);
        graphics.setColor(Color.BLACK);
        graphics.drawOval(x - radius, y - radius, radius, radius);
        graphics.setFont(SMALL_FONT);
        graphics.drawString("Assistance", (int) (x - 1.6 * radius), (int) (y - 1.3 * radius));
    }

    void fire() {
    }

    protected void destroy() {
        ACTIVE_ENEMY.remove(this);
    }
}
