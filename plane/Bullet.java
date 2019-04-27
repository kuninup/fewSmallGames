package plane;

import java.awt.*;

class Bullet extends Entity {

    Bullet(Entity source) {
        x = source.x;
        health = 1;
        radius = 5;
        if (source != PLAYER) {
            y = source.y + source.radius;
            dy = 4 + source.dy;
            damage = dy * 3 + source.mark * 2;
            if (damage > 100) {
                damage = 100;
            }
//            red = BULLET.getRed();
//            green = BULLET.getGreen();
//            blue = BULLET.getBlue();
        } else {
            y = source.y - source.radius;
            damage = ((Player) source).bullet;
            dy = -PLAYER_SPEED - 1;
            mark = 1;
        }
        ACTIVE_BULLET.add(this);
    }

    Bullet(Entity source, int dx, int dy) {
        x = source.x;
        y = source.y + source.radius;
        health = 1;
        radius = 5;
        this.dx = dx;
        this.dy = dy;
        damage = dy * 3 + source.mark * 2;
        if (damage > 100) {
            damage = 100;
        }
        ACTIVE_BULLET.add(this);
    }

    Bullet(Entity source, int x, int y, int dx, int dy) {
        this.x = x;
        this.y = y;
        health = 1;
        radius = 5;
        this.dx = dx;
        this.dy = dy;
        damage = dy * 3 + source.mark * 2;
        if (damage > 100) {
            damage = 100;
        }
        ACTIVE_BULLET.add(this);
    }

    void fire() {
    }

    protected void destroy() {
        ACTIVE_BULLET.remove(this);
    }

    void draw(Graphics graphics) {
        graphics.setColor(Color.BLUE);
        graphics.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    void crash() {
        if (mark == 1) {
            Entity e;
            for (int i = 0; i < ACTIVE_ENEMY.size(); i++) {
                e = ACTIVE_ENEMY.get(i);
                if (distance(this, e) < 40) {
                    attack(e);
                    e.attack(this);
                }
            }
        } else {
            if (distance(this, PLAYER) < 40) {
                attack(PLAYER);
                PLAYER.attack(this);
            }
        }
    }
}
