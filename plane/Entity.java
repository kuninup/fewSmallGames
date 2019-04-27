package plane;

import java.awt.*;
import java.io.Serializable;

abstract class Entity implements BasicData, Serializable {

    int x, y, damage, health, maxHealth, score, dx, dy, radius;
    transient int red, green, blue;
    byte mark;
    protected transient Color color;
    protected transient Image picture;

    Entity() {
        damage = 100;
        maxHealth = health = 100;
        radius = 30;
    }

    boolean out() {
        return (out(0, FRAME_WIDTH, HORIZONTAL) || out(0, FIELD_HEIGHT, VERTICAL));
    }

    void draw(Graphics graphics) {
//        double rate = health * 1.0 / maxHealth;
//
//        graphics.setColor(new Color((int) (rate * red), (int) (rate * green), (int) (rate * blue)));
//        graphics.fillOval(x - radius, y - radius, radius * 2, radius * 2);
//
//        graphics.setColor(Color.BLACK);
//        graphics.drawOval(x - radius, y - radius, radius * 2, radius * 2);
        if (radius == 0) {
            radius = picture.getHeight(null);
        }
        graphics.drawImage(picture, x - radius, y - radius, null);
    }

    boolean attack(Entity target) {
        target.health -= damage;
        if (target == PLAYER) {
            System.out.println(damage + "  " + PLAYER.health);
        }
        if (target.health < 1) {
            if (target.mark == -16) {
                PLAYER.maxHealth += 1000;
                PLAYER.health = PLAYER.maxHealth;
                PLAYER.bullet += 500 / PLAYER.bullet;
                PLAYER.damage = (int) (PLAYER.damage * 1.3);
                return false;
            }
            if (mark == 1) {
                PLAYER.score += target.score;
            }
            target.destroy();
            return true;
        }
        return false;
    }

    void move() {
        if (y > FIELD_HEIGHT + radius || x < 0 || x > FRAME_WIDTH || health < 1) {
            destroy();
        } else {
            x += dx;
            y += dy;
        }
    }

    abstract void fire();

    protected abstract void destroy();

    boolean out(int left, int right, int d) {
        switch (d) {
            case HORIZONTAL:
                return (x - radius < left || x + radius > right);

            case VERTICAL:
                return (y - radius < left || y + radius > right);
        }
        return false;
    }


    static double distance(Entity a, Entity b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }
}
