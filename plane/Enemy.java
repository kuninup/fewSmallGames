package plane;

class Enemy extends Entity {

    private int fireGap, fireTimer;

    Enemy(int health, int x, int y, int dy, byte mark) {
        picture = ENEMY_PICTURE;
        maxHealth = this.health = health;
        damage = 100 + (int) (health * 0.2);
        this.mark = mark;
        this.x = x;
        this.y = y;
        this.dy = dy;
        score = dy * 20 + mark * health;
        score = score > 400 ? 400 : score;
//        red = ENEMY.getRed();
//        green = ENEMY.getGreen();
//        blue = ENEMY.getBlue();
        radius = ENEMY_PICTURE.getHeight(null) / 2;
        fireGap = 50 - 6 * mark;
        fireTimer = 44;
        ACTIVE_ENEMY.add(this);
    }

    void move() {
        if (picture == null) {
            picture = ENEMY_PICTURE;
        }
        super.move();
    }

    void fire() {
        if (fireTimer++ > fireGap) {
            if (mark > 7) {
                new Bullet(this, -1, 4 + dy);
                new Bullet(this, 1, 4 + dy);
                new Bullet(this, -2, 8 + dy);
                new Bullet(this, 2, 8 + dy);
                if (mark > 8) {
                    new Bullet(this);
                    new Bullet(this);
                    new Bullet(this, x - 40, y + radius, -2, 8 + dy);
                    new Bullet(this, x + 40, y + radius, 2, 8 + dy);
                    new Bullet(this, x - 40, y + radius, 0, 8 + dy);
                    new Bullet(this, x + 40, y + radius, 0, 8 + dy);
                    new Bullet(this, x, y - 2 * radius, 0, 3 * dy);
                }
            } else {
                new Bullet(this);
                if (mark > 4) {
                    new Bullet(this, -1, 4 + dy);
                    new Bullet(this, 1, 4 + dy);
                    new Bullet(this, x - 40, y + radius, 0, 4 + dy);
                    new Bullet(this, x + 40, y + radius, 0, 4 + dy);
                }
                if (mark > 6) {
                    new Bullet(this, 0, 12);
                    new Bullet(this, 0, 12);
                    new Bullet(this, x, y - 2 * radius, 0, 3 * dy);
                    new Bullet(this, x, y - 2 * radius, 0, 3 * dy);
                }
            }
            fireTimer = 0;
        }
    }

    protected void destroy() {
        ACTIVE_ENEMY.remove(this);
    }
}
