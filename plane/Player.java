package plane;

class Player extends Entity {

	private static final long serialVersionUID = 1L;
	//    boolean living;
    int bullet;
    transient boolean firing;
//    PlayerFire timer;

    Player() {
        picture = PLAYER_PICTURE;
        mark = 1;
        x = FRAME_WIDTH / 2;
        y = FIELD_HEIGHT - 90;
        dy = 0;
        dx = 0;
        firing = false;
        maxHealth = health = 1000;
        bullet = 25;
//        red = MYSELF.getRed();
//        green = MYSELF.getGreen();
//        blue = MYSELF.getBlue();
        radius = PLAYER_PICTURE.getHeight(null) / 2;
        score = 0;
//        living = true;
//        timer = new PlayerFire();
    }

    void move() {
        x += dx;
        y += dy;
        if (x < radius || x > FRAME_WIDTH - radius || y < radius || y > FIELD_HEIGHT - radius) {
            x -= dx;
            y -= dy;
        }
    }

    protected void destroy() {
//        living = false;
    }

    void fire() {
        if (!firing) {
            firing = true;
            (new PlayerFire()).start();
        }
    }

    void crash() {
        Entity e;
        for (int i = 0; i < ACTIVE_ENEMY.size(); i++) {
            e = ACTIVE_ENEMY.get(i);
            if (distance(this, e) < 40) {
                if (attack(e)) {
                    score += e.score;
                }
                e.attack(this);
            }
        }
    }

    void fresh(Player n) {
        x = n.x;
        y = n.y;
        dx = 0;
        dy = 0;
        damage = n.damage;
        health = n.health;
        maxHealth = n.maxHealth;
        score = n.score;
    }
}
