package FiveChessGame;


import javax.swing.JOptionPane;


public class ChessWin  {
	private int [][] chess;
	
	public ChessWin(int [][]chess){
		this.chess=chess;
	}
	
	//�ж���Ӯ�ķ���
	public void Win(int x,int y){
		System.out.println("�ж�"+sp(x,y));
			if(sp(x,y)>=5||sp1(x,y)>=5||sp2(x,y)>=5||sp3(x,y)>=5){
			if(chess[x][y]==1){
				System.out.println("1��ʤ");
				JOptionPane.showMessageDialog(null, "��ʤ");
			}else if(chess[x][y]==2){
				System.out.println("2");
				JOptionPane.showMessageDialog(null,"��ʤ");
				
			}	
     	}
	}

	/**
	 * �ж�ˮƽ���������������
	 * 
	 * @param x
	 *            ��ǰ����x�����ϵĽ�������
	 * @param y
	 *            ��ǰ����y�����ϵĽ�������
	 */
	public int sp(int x, int y) {
		int count = 0;// ��¼���Ӹ���
		for (int i = x; i < chess.length; i++) {
			if (chess[i][y] == chess[x][y]) {
				count++;
			} else
				break;
		}
		for (int i = x - 1; i >= 0; i--) {
			if (chess[i][y] == chess[x][y]) {
				count++;
			} else
				break;
		}
		return count;
	}
	//��ֱ����
	public int sp1(int x,int y){
		int count = 0;// ��¼���Ӹ���
		for (int j = y; j < chess.length; j++) {
			if (chess[x][j] == chess[x][y]) {
				count++;
			} else
				break;
		}
		for (int j = y - 1; j >= 0; j--) {
			if (chess[x][j] == chess[x][y]) {
				count++;
			} else
				break;
		}
		return count;
	}
	
	//��б
	public int sp2(int x,int y){
		int count = 0;
		for (int i=x,j = y; i<chess.length&&j >=0;i++, j--) {
			if (chess[i][j] == chess[x][y]) {
				count++;
			} else
				break;
		}
		for (int i=x-1, j = y+1; i>=0&&j<=chess.length;i--,j++) {
			if (chess[i][j] == chess[x][y]) {
				count++;
			} else
				break;
		}
		return count;
	}
	
  //��б
	public int sp3(int x,int y){
		int count = 0;
		//����
		for (int i=x,j = y; i<chess.length&&j < chess.length;i++, j++) {
			if (chess[i][j] == chess[x][y]) {
				count++;
			} else
				break;
		}
		//����
		for (int i=x-1, j = y - 1; i>=0&&j >= 0;i--,j--) {
			if (chess[i][j] == chess[x][y]) {
				count++;
			} else
				break;
		}
		return count;
	}

}	
	
	
