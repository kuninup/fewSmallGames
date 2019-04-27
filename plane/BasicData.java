package plane;

import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

interface BasicData {

    int STATUS_BAR_HEIGHT = 0;
    int HEALTH_BAR_HEIGHT = 30;
    int FIELD_HEIGHT = 780;
    int FRAME_WIDTH = 600;
    int FRAME_HEIGHT = HEALTH_BAR_HEIGHT + FIELD_HEIGHT + STATUS_BAR_HEIGHT;
    int HORIZONTAL = -1;
    int VERTICAL = -2;
    int ENEMY_SIZE = 20;
    int PLAYER_SPEED = 7;
    String CHARACTERISTICS = "#gkp#plane#";
    Font SMALL_FONT = new Font("Arial", Font.PLAIN, 20);
    Font LARGE_FONT = new Font("Arial", Font.PLAIN, 24);
    Random RANDOM = new Random();
    Vector<Entity> ACTIVE_ENEMY = new Vector<>(20);
    Vector<Bullet> ACTIVE_BULLET = new Vector<>(20);
    Image ENEMY_PICTURE = new ImageIcon("src\\plane\\Enemy.png").getImage();
    Image PLAYER_PICTURE = new ImageIcon("src\\plane\\Player.png").getImage();
    Frame FRAME = new Frame();
    BufferedImage FIELD_BUFFER = new BufferedImage(FRAME_WIDTH, FIELD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    Player PLAYER = new Player();
//    GameMenu MENU = new GameMenu();
}
