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
	// ����һ����ά��������������
	private int[][] chess = new int[ROWS][COLUMNS];
   
	public static void main(String[] args) {
		new FiveChessUI();
	}

	public FiveChessUI() {

		JFrame jf = new JFrame();
		jf.setTitle("FiveChess");
		jf.setSize(970, 850);
		// jf.getContentPane().setBackground(Color.orange); //����������ɫ
		jf.setDefaultCloseOperation(3);
		jf.setLocationRelativeTo(null);
		this.setBackground(Color.ORANGE);//���ô��屳����ɫ
		jf.add(this, BorderLayout.CENTER);// �����������ӵ������ϵ��м�

		// ����һ��JPanel����Ӱ�ť
		JPanel jp = new JPanel();
		jp.setPreferredSize(new Dimension(150, 0));
		jp.setBackground(Color.GRAY);

		mouselistener ml = new mouselistener(this);
		// ����ȥ
		// mouselistener ml=new mouselistener();
		// ml.setFiveChessUI(this);
		ml.setChess(chess);
		
		
		// �����ַ�������
		String[] array = { "��ʼ��Ϸ", "����", "����","���¿�ʼ", "ѡ���սģʽ", "���˶�ս", "�˻���ս" };
		// ����һ����ѡ��ť����Ķ���
		ButtonGroup bg = new ButtonGroup();
		// ѭ����������
		for (int i = 0; i < array.length; i++) {
			if (i < 4) {
				JButton button = new JButton(array[i]);
				button.setPreferredSize(new Dimension(100, 50));
				jp.add(button);
				button.addActionListener(ml);// ����ť�Ӽ�����
			} else if (i ==4) {
				jp.add(new JLabel(array[i]));
			} else
			// ��ѡ��ťJRadioButton
			{
				JRadioButton rb = new JRadioButton(array[i]);
				rb.setOpaque(false);// ����Ϊ͸����
				rb.setSelected(true);// ����Ĭ��ѡ��һ����ť
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
	//��д���ƴ���ķ���
	public void paint(Graphics g) {
		//�ӱ���ͼ��Ϊʲôû��ִ�У���
		
		super.paint(g);//����������
		
//		ImageIcon icon = new ImageIcon("C:\\Users\\Admin\\Pictures\\Saved Pictures\\07112515_OOgj.jpg");
//		g.drawImage(icon.getImage(), 0, 0, this.getWidth(), this.getWidth(), null);
//		
		drawChessTable(g);
		redrawChess(g);
	}
    //��������
	public void drawChessTable(Graphics g) {
		//�����̵��߱��һ��
		Graphics2D g2 = (Graphics2D) (g);
		g2.setStroke(new BasicStroke(2));
		for (int i = 0; i < ROWS; i++) {
			g.drawLine(XO, YO + SIZE * i, XO + (COLUMNS - 1) * SIZE, YO + SIZE * i);
		}
		for (int j = 0; j < COLUMNS; j++) {
			g.drawLine(XO + j * SIZE, YO, XO + j * SIZE, YO + (ROWS - 1) * SIZE);
		}

	}

	// �ػ�����
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