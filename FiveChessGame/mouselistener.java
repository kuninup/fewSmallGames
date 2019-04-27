package FiveChessGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;


public class mouselistener extends MouseAdapter implements ActionListener, Config {

	private FiveChessUI fc;
	private Graphics g;
	boolean flag = true;
	private int[][] chess;// 棋盘的下棋情况
	private ChessWin win;
	private int xx, yy,mxx,myy;
	private HashMap<String, Integer> map = new HashMap<String, Integer>();// 创建集合对象，用途是存储每一种棋子相连对应的权值
	private String type = "人人对战";// 记录对战模式的属性
	private JRadioButton rb1, rb2;
	int[][] weightArray = new int[ROWS][COLUMNS];// 创建一个存储权值的二维数组
	
	// 构造函数
	public mouselistener(FiveChessUI fc) {
		this.fc = fc;
	}

	public void setChess(int[][] chess) {
		this.chess = chess;
		win = new ChessWin(chess);
	}

	public void setRb1(JRadioButton rb1) {
		this.rb1 = rb1;
	}

	public void setRb2(JRadioButton rb2) {
		this.rb2 = rb2;
	}
	// //setFiveChessUI方法
	// public void setFiveChessUI(FiveChessUI fc){
	// this.fc=fc;
	// }

	@Override
	public void mouseClicked(MouseEvent e) {
		int r = (e.getX() - XO + SIZE / 2) / SIZE;
		int c = (e.getY() - YO + SIZE / 2) / SIZE;
		int x = XO + r * SIZE - SIZE / 2;
		int y = YO + c * SIZE - SIZE / 2;
		if (type.equals("人人对战")) {
			pvp(r, c, x, y);
			win.Win(r, c);
		} else if (type.equals("人机对战")) {
			pvai(r, c, x, y);
//			win.Win(r, c); // 没有调用到?
		}

	}

	public void pvp(int r, int c, int x, int y) {
		if (g == null)
			// 得到画笔
			g = fc.getGraphics();

		if (chess[r][c] == 0) {
			if (flag) {
				g.setColor(Color.black);
				chess[r][c] = 1;
			} else {
				g.setColor(Color.white);
				chess[r][c] = 2;
			}
			g.fillOval(x, y, CHESS, CHESS);
			flag = !flag;
			// System.out.println(chess[r][c]);
			xx = r;
			yy = c;
		}
	}

	public void pvai(int r, int c, int x, int y) {
		if (g == null)
			// 得到画笔
			g = fc.getGraphics();
		if(chess[r][c]==0){
		g.setColor(Color.BLACK);
		g.fillOval(x, y, CHESS, CHESS);
		xx=r;
		yy=c;
		chess[r][c] = 1;
		win.Win(r, c);
		ai(g);
	}
	}
	public void ai(Graphics g) {
		g.setColor(Color.WHITE); // 设置ai所下棋子的颜色为白色

		/**
		 * 设置每种棋子相连情况下的权值
		 */
		//1为黑棋，2为白棋
		map.put("1", 1);
		map.put("11", 20);
		map.put("111", 100);
		map.put("1111", 500);
		map.put("11010", 350);
		map.put("11101", 350);
		map.put("2", 10);
		map.put("22", 30);
		map.put("222",150);
		map.put("2222", 500);
		map.put("21", 1);
		map.put("211", 10);
		map.put("112", 10);
		map.put("2111", 30);
		map.put("1112", 30);
		map.put("21111", 500);
		map.put("11112", 500);
		map.put("12", 1);
		map.put("122", 10);
		map.put("221", 10);
		map.put("1222", 30);
		map.put("2221", 30);
		map.put("12222", 500);
		map.put("22221", 500);
		// 权值设置有问题？要如何设置？

		// 求出权值 将权值存到数组中
		for (int r = 0; r < chess.length; r++) {
			for (int c = 0; c < chess[r].length; c++) {
				if (chess[r][c] == 0) { // 判断是否是空位
					
					String code = count1(r, c);
					Integer weight = map.get(code);// 获取棋子相连情况对应的权值
					
					if (null != weight) // 判断权值不为null
						weightArray[r][c] += weight;// 累加权值
					code = count2(r, c);
					weight = map.get(code);
					
					if (null != weight)
						weightArray[r][c] += weight;
					code = count3(r, c);
					weight = map.get(code);
					if (null != weight)
						weightArray[r][c] += weight;
					code = count4(r, c);
					weight = map.get(code);
					if (null != weight)
						weightArray[r][c] += weight;
					code = count5(r, c);
					weight = map.get(code);
					if (null != weight)
						weightArray[r][c] += weight;
					code = count6(r, c);
					weight = map.get(code);
					if (null != weight)
						weightArray[r][c] += weight;
					code = count7(r, c);
					weight = map.get(code);
					if (null != weight)
						weightArray[r][c] += weight;
					code = count8(r, c);
					weight = map.get(code);
					if (null != weight)
						weightArray[r][c] += weight;
						
				}
			}
		}
		// 打印权值
		for (int i = 0; i < weightArray.length; i++) {
			for (int j = 0; j < weightArray[i].length; j++) {
				System.out.print(weightArray[j][i] + " ");
			}
			System.out.println();
		}
		System.out.println("====================");
		int max = 0;
		int maxx = 0, maxy = 0;
		for (int i = 0; i < weightArray.length; i++) {
			for (int j = 0; j < weightArray[i].length; j++) {
				if (weightArray[i][j] > max) {
					max = weightArray[i][j];
					// 记录权值最大的交点坐标
					maxx = i;
					maxy = j;
				}
			}
		}
		if (chess[maxx][maxy] == 0 && weightArray[maxx][maxy] == max) {
			g.fillOval(maxx * SIZE + XO - SIZE / 2, maxy * SIZE + YO - SIZE / 2, CHESS, CHESS);
           mxx=maxx;
           myy=maxy;
			chess[maxx][maxy] = 2;
            win.Win(maxx, maxy);//判断输赢
			//清空权值
			for (int i = 0; i < weightArray.length; i++) {
			for (int j = 0; j < weightArray[i].length; j++) {
			weightArray[i][j]=0;
				}
				}
		}
	}

	// 水平向左
	private String count1(int x, int y) {
		String code = ""; // 默认记录r,c位置的情况
		int color = 0; // 记录第一次出现的棋子的颜色
		for (int i = x - 1; i >= 0; i--) {
			if (chess[i][y] == 0) {
				break;
			} else {
				if (color == 0) { // 左边第一颗棋子
					color = chess[i][y]; // 保存第一颗棋子颜色
					code += chess[i][y]; // 保存棋子相连情况
				} else if (chess[i][y] == color) {
					code += chess[i][y]; // 保存棋子相连情况
				} else {
					code += chess[i][y]; // 保存棋子相连情况
					break;
				}
			}
		}
		System.out.println(code);
		return code;

	}

	// 向右
	private String count2(int x, int y) {
		String code = "";
		int color = 0;
		for (int i = x+1; i < 15; i++) {
			if (chess[i][y] == 0) {
				break;
			} else {
				if (color == 0) {
					color = chess[i][y];
					code += chess[i][y];
				} else if (chess[i][y] == color) {
					code += chess[i][y];
				} else {
					code += chess[i][y];
					break;
				}
			}
		}
		return code;
	}

	// 向上
	private String count3(int x, int y) {
		String code = "";
		int color = 0;
		for (int i = y - 1; i >= 0; i--) {
			if (chess[x][i] == 0) {
				break;
			} else {
				if (color == 0) {
					color = chess[x][i];
					code += chess[x][i];
				} else if (chess[x][i] == color) {
					code += chess[x][i];
				} else {
					code += chess[x][i];
					break;
				}
			}
		}
		return code;
	}

	// 向下
	private String count4(int x, int y) {
		String code = "";
		int color = 0;
		for (int i = y+1; i < 15; i++) {
			if (chess[x][i] == 0) {
				break;
			} else {
				if (color == 0) {
					color = chess[x][i];
					code += chess[x][i];
				} else if (chess[x][i] == color) {
					code += chess[x][i];
				} else {
					code += chess[x][i];
					break;
				}
			}
		}
		return code;
	}

	// 左上
	private String count5(int x, int y) {
		String code = "";
		int color = 0;
		for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
			if (chess[i][j] == 0) {
				break;
			} else {
				if (color == 0) {
					color = chess[i][j];
					code += chess[i][j];
				} else if (chess[i][j] == color) {
					code += chess[i][j];
				} else {
					code += chess[i][j];
					break;
				}
			}
		}
		return code;
	}

	// 左下
	private String count6(int x, int y) {
		String code = "";
		int color = 0;
		for (int i = x+1, j = y+1; i < 15 && j < 15; i++, j++) {
			if (chess[i][j] == 0) {//判断是否为空位
				break;
			} else {
				if (color == 0) {
					color = chess[i][j];
					code += chess[i][j];
				} else if (chess[i][j] == color) {
					code += chess[i][j];
				} else {
					code += chess[i][j];
					break;
				}
			}
		}
		System.out.println(code);

		return code;
	}

	// 右下
	private String count7(int x, int y) {
		String code = "";
		int color = 0;
		for (int i = x - 1, j = y+1; i >= 0 && j < 15; i--, j++) {
			if (chess[i][j] == 0) {
				break;
			} else {
				if (color == 0) {
					color = chess[i][j];
					code += chess[i][j];
				} else if (chess[i][j] == color) {
					code += chess[i][j];
				} else {
					code += chess[i][j];
					break;
				}
			}
		}
		return code;
	}

	// 右上
	private String count8(int x, int y) {
		String code = "";
		int color = 0;
		for (int i = x+1, j = y - 1; i < 15 && j >= 0; i++, j--) {
			if (chess[i][j] == 0) {
				break;
			} else {
				if (color == 0) {
					color = chess[i][j];
					code += chess[i][j];
				} else if (chess[i][j] == color) {
					code += chess[i][j];
				} else {
					code += chess[i][j];
					break;
				}
			}
		}
		return code;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// System.out.println("actionPerformed");
		if (e.getActionCommand().equals("开始游戏")) {
			System.out.println(e.getActionCommand());
			fc.addMouseListener(this); // 点击按钮后给面板对象加监听器
			/*
			fc.addMouseMotionListener(new MouseMotionListener() {//内部类
				public void mouseMoved(MouseEvent e) {

				int	x = e.getX();
				int	y = e.getY();
				if(x>=50&&x<750&&y>=50&&y<750){
					fc.setCursor(new Cursor(Cursor.HAND_CURSOR));// 设置成手型
				} else {
					fc.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 设置成默认形状
				}
				}

			

				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

			
				});*/
			
			if (type.equals("人人对战"))
				rb2.setEnabled(false);
			else if (type.equals("人机对战"))
				rb1.setEnabled(false);
		} else if (e.getActionCommand().equals("认输")) {
			if (flag) {
				JOptionPane.showMessageDialog(fc, "黑棋认输，白棋胜利！");
				fc.removeMouseListener(this);
			} else {
				JOptionPane.showMessageDialog(fc, "白棋认输，黑棋胜利！");
				fc.removeMouseListener(this);
				if (type.equals("人人对战"))
					rb2.setEnabled(false);
				else if (type.equals("人机对战"))
					rb1.setEnabled(false);
			}
		} else if (e.getActionCommand().equals("重新开始")) {
			// 清空存储棋子的数组
			for (int i = 0; i < chess.length; i++)
				for (int j = 0; j < chess[0].length; j++) {
					chess[i][j] = 0;
				}
			fc.repaint();
			flag = true;
			rb1.setEnabled(true);
			rb2.setEnabled(true);
		} else if (e.getActionCommand().equals("悔棋")) {
			if (type == "人人对战") {
				fc.addMouseListener(this);
				chess[xx][yy] = 0;
				fc.repaint();
				flag = !flag;
			} else {// 人机对战
				fc.addMouseListener(this);
				chess[xx][yy] = 0;
				chess[mxx][myy]=0;
				fc.repaint();
			}
		} else {
			type = e.getActionCommand();// 记录对战模式
		}

	}
}
