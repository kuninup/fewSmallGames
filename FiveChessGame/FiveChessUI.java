package FiveChessGame;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class FiveChessUI extends JPanel implements Config {
	
	private static final long serialVersionUID = 1L;
	// 定义一个二维数组来储存棋子
	private int[][] chess = new int[ROWS][COLUMNS];
   
	public static void main(String[] args) {
		new FiveChessUI();
	}

	public FiveChessUI() {

		JFrame jf = new JFrame();
		jf.setTitle("FiveChess");
		jf.setSize(970, 850);
		// jf.getContentPane().setBackground(Color.orange); //窗体设置颜色
		jf.setDefaultCloseOperation(3);
		jf.setLocationRelativeTo(null);
		this.setBackground(Color.ORANGE);//设置窗体背景颜色
		jf.add(this, BorderLayout.CENTER);// 将棋盘面板添加到窗体上的中间

		// 创建一个JPanel，添加按钮
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(150, 0));
		jp.setBackground(Color.GRAY);

		mouselistener ml = new mouselistener(this);
		// 传过去
		// mouselistener ml=new mouselistener();
		// ml.setFiveChessUI(this);
		ml.setChess(chess);
		
		
		// 定义字符串数组
		String[] array = { "开始游戏", "悔棋", "认输","重新开始", "选择对战模式", "人人对战", "人机对战" };
		// 创建一个单选按钮分组的对象
		ButtonGroup bg = new ButtonGroup();
		// 循环遍历数组
		for (int i = 0; i < array.length; i++) {
			if (i < 4) {
				JButton button = new JButton(array[i]);
				button.setPreferredSize(new Dimension(100, 50));
				jp.add(button);
				button.addActionListener(ml);// 给按钮加监听器
			} else if (i ==4) {
				jp.add(new JLabel(array[i]));
			} else
			// 单选按钮JRadioButton
			{
				JRadioButton rb = new JRadioButton(array[i]);
				rb.setOpaque(false);// 设置为透明的
				rb.setSelected(true);// 设置默认选中一个按钮
				rb.addActionListener(ml);
				if(i==5)
					ml.setRb1(rb);
				else
					ml.setRb2(rb);
				bg.add(rb);
				jp.add(rb);
			}

			jf.add(jp, BorderLayout.EAST);
			
			jf.setVisible(true);
			// jf.addMouseListener(ml);
		}
	}
	//重写绘制窗体的方法
	public void paint(Graphics g) {
		//加背景图（为什么没有执行？）
		
		super.paint(g);//将画板重制
		
//		ImageIcon icon = new ImageIcon("C:\\Users\\Admin\\Pictures\\Saved Pictures\\07112515_OOgj.jpg");
//		g.drawImage(icon.getImage(), 0, 0, this.getWidth(), this.getWidth(), null);
//		
		drawChessTable(g);
		redrawChess(g);
	}
    //绘制棋盘
	public void drawChessTable(Graphics g) {
		//把棋盘的线变粗一点
		Graphics2D g2 = (Graphics2D) (g);
		g2.setStroke(new BasicStroke(2));
		for (int i = 0; i < ROWS; i++) {
			g.drawLine(XO, YO + SIZE * i, XO + (COLUMNS - 1) * SIZE, YO + SIZE * i);
		}
		for (int j = 0; j < COLUMNS; j++) {
			g.drawLine(XO + j * SIZE, YO, XO + j * SIZE, YO + (ROWS - 1) * SIZE);
		}

	}

	// 重绘棋子
	public void redrawChess(Graphics g) {
		for (int i = 0; i < chess.length; i++) {
			for (int j = 0; j < chess[0].length; j++) {
				if (chess[i][j] == 1) {
					g.setColor(Color.BLACK);
					g.fillOval(XO + i * SIZE - CHESS / 2, YO + j * SIZE - CHESS / 2, CHESS, CHESS);
				} else if (chess[i][j] == 2) {
					g.setColor(Color.WHITE);
					g.fillOval(XO + i * SIZE - CHESS / 2, YO + j * SIZE - CHESS / 2, CHESS, CHESS);
				}
			}
		}

	}

}