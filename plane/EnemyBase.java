package plane;

class EnemyBase extends Thread implements BasicData {

    private int gap, max = 2, p = 100, t = 2, count = 0;

    int[] getData() {
        int[] r = new int[5];
        int[][] d = new int[][]{r};
        r[0] = gap;
        r[1] = max;
        r[2] = p;
        r[3] = t;
        r[4] = count;
        return r;
    }

    void setData(int[] d) {
        gap = d[0];
        max = d[1];
        p = d[2];
        t = d[3];
        count = d[4];
    }

    public void run() {
        while (FRAME.playing) {
            if (!FRAME.pause) {
                if (ACTIVE_ENEMY.size() > max) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (PLAYER.score > p * 3 || PLAYER.score > p + 10000) {
                        max++;
                        System.out.println(max);
                        p = PLAYER.score;
                    }
                } else {
                    if (max > 5) {
                        t = RANDOM.nextInt(max < 10 ? max : 10) + max < 7 ? 2 : 5;
                    }
                    new Enemy(max * 30 + 70,
                            RANDOM.nextInt(FRAME_WIDTH - 64 * 2) + 64,
                            -50, RANDOM.nextInt(max) + 2, (byte) t);
                    count++;
                }
            }
            gap = RANDOM.nextInt(2) * 1000;
            try {
                Thread.sleep(gap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
