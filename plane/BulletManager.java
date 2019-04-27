package plane;

class BulletManager extends Thread implements BasicData {

    public void run() {
        Entity entity;
        while (true) {
//            PLAYER.fireTimer++;
            for (int i = 0; i < ACTIVE_ENEMY.size(); i++) {
                entity = ACTIVE_ENEMY.get(i);
                entity.fire();
            }
            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
