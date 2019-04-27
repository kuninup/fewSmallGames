package FiveChessGame;

import java.awt.Color;
import java.awt.Graphics;

//棋子类，保存棋子的相关数据
public class Chess implements Config {

	private int x2, y2;
	private Color color;

	public Chess(int x2, int y2,Color color) {
		// TODO Auto-generated constructor stub

		this.x2 = x2;
		this.y2 = y2;
		this.color=color;
}

	public void drawChess(Graphics g) {
		g.setColor(color);
		g.fillOval(x2 - CHESS / 2, y2 - CHESS/ 2, CHESS, CHESS);

	}
}