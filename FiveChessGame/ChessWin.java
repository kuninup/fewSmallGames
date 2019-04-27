package FiveChessGame;


import javax.swing.JOptionPane;


public class ChessWin  {
	private int [][] chess;
	
	public ChessWin(int [][]chess){
		this.chess=chess;
	}
	
	//判断输赢的方法
	public void Win(int x,int y){
		System.out.println("判断"+sp(x,y));
			if(sp(x,y)>=5||sp1(x,y)>=5||sp2(x,y)>=5||sp3(x,y)>=5){
			if(chess[x][y]==1){
				System.out.println("1黑胜");
				JOptionPane.showMessageDialog(null, "黑胜");
			}else if(chess[x][y]==2){
				System.out.println("2");
				JOptionPane.showMessageDialog(null,"白胜");
				
			}	
     	}
	}

	/**
	 * 判断水平方向棋子相连情况
	 * 
	 * @param x
	 *            当前棋子x方向上的交点坐标
	 * @param y
	 *            当前棋子y方向上的交点坐标
	 */
	public int sp(int x, int y) {
		int count = 0;// 记录棋子个数
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
	//竖直方向
	public int sp1(int x,int y){
		int count = 0;// 记录棋子个数
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
	
	//左斜
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
	
  //右斜
	public int sp3(int x,int y){
		int count = 0;
		//右下
		for (int i=x,j = y; i<chess.length&&j < chess.length;i++, j++) {
			if (chess[i][j] == chess[x][y]) {
				count++;
			} else
				break;
		}
		//右上
		for (int i=x-1, j = y - 1; i>=0&&j >= 0;i--,j--) {
			if (chess[i][j] == chess[x][y]) {
				count++;
			} else
				break;
		}
		return count;
	}

}	
	
	
