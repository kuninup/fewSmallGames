package plane;

class PlayerFire extends Thread implements BasicData {

    private static int gap = 7, timer = 4;

    public void run() {
        while (PLAYER.health > 0 && FRAME.playing && PLAYER.firing) {
            if (!FRAME.pause) {
                if (timer++ > gap) {
                    timer = 0;
                    new Bullet(PLAYER);
                }
            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
