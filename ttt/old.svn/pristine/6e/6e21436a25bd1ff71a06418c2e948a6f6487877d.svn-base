package ttt.util;

public class GameUtil {
	public static int[] covertGameBoard(String board) {
		int[] intarray = new int[9];
		String input = board;
		input = input.replace('[', ' ').replace(']', ' ').trim();
		String[] strarray = input.split(",");

		for (int count = 0; count < intarray.length; count++) {
			intarray[count] = Integer.parseInt(strarray[count]);
		}
		return intarray;
	}
}
