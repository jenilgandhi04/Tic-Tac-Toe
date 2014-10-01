package ttt.bean;

public class GameBoard {
	private int cells[] = new int[9];

	public GameBoard() {
		for (int i = 0; i < 9; i++) {
			cells[i] = -1;
		}
	}
	public GameBoard(int[] cells){
		this.cells=cells;
	}
	public int[] getCells() {
		return cells;
	}

	public void setCells(int[] cells) {
		this.cells = cells;
	}
	@Override
	public String toString(){
		String board="[";
		for (int i = 0; i < 9; i++) {
			board+=cells[i];
			if(i!=8)
				board+=",";
		}
		board+="]";
		return board;
	}
}
