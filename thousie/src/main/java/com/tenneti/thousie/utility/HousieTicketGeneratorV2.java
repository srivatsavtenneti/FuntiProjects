package com.tenneti.thousie.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HousieTicketGeneratorV2 {

	public List<int[][]> generateTickets(int count) {
		List<int[][]> tickets = new ArrayList<int[][]>();
		int singleTicket[][] = new int[9][3];
		List<Integer[]> counts = new ArrayList<Integer[]>();
		List<Integer[]> modifiedCountsList = new ArrayList<Integer[]>();
		for(int i = 0; i < count; i++) {
			Integer[] columnCounts = Arrays.stream( randomColumnCounts() ).boxed().toArray( Integer[]::new );
			counts.add(columnCounts);
		}
				
		modifiedCountsList = modifiedColCounts(counts);
		
		for (int i = 0; i < modifiedCountsList.size(); i++) {
			int rowPos[][] = tablePositions(getIntArray(modifiedCountsList.get(i)));

			singleTicket = populateTable(getIntArray(modifiedCountsList.get(i)), rowPos);
			tickets.add(singleTicket);
		}
		
		return tickets;
	}
	
	private int[] getIntArray(Integer[] array) {
		int[] intArray = new int[array.length];

		for(int ctr = 0; ctr < array.length; ctr++) {
			intArray[ctr] = array[ctr].intValue();
		}
		
		return intArray;
	}
	
	private List<Integer[]> modifiedColCounts(List<Integer[]> colList) {
		List<Integer> oneColumn = new ArrayList<Integer>();

		int range = 10;
		for(int i=0; i < 9; i++) {
			int singleColSum = 0;
			List<Integer> twoColumn = new ArrayList<Integer>();
			List<Integer> threeColumn = new ArrayList<Integer>();
			for(int j=0; j < colList.size(); j++) {
				int count = colList.get(j)[i];
				singleColSum += colList.get(j)[i];
				if(count == 1) {
					oneColumn.add(j);
				} else if (count == 2) {
					twoColumn.add(j);
				} else {
					threeColumn.add(j);
				}
			}
			if(i == 0) {
				range = 9;
			} else if (i == 8) {
				range = 11;
			} else {
				range = 10;
			}
			modifyColumn(singleColSum, colList, threeColumn, twoColumn, i, range);
			
		}
		
		return colList;
	}
	
	
	private void modifyColumn(int singleColSum, List<Integer[]> colList, List<Integer> threeColumn, List<Integer> twoColumn, int i, int range) {
		if(singleColSum > range) {
			int k = 0;
			int twoCount = 0;
			int colSum = singleColSum;
			while(colSum > range) {	
				if(threeColumn.size() > 0) {
					int mcol = adjustColumnCount(colList.get(threeColumn.get(k)),i, colList);
					if(mcol != 10) {
						colList.get(threeColumn.get(k))[i] = colList.get(threeColumn.get(k))[i] - 1;
					}
					threeColumn.remove(threeColumn.get(k));
					colSum = getColSum(colList, i);
				} else {
					int col = adjustColumnCount(colList.get(twoColumn.get(twoCount)), i, colList);
					if(col != 10) {
						colList.get(twoColumn.get(twoCount))[i] = colList.get(twoColumn.get(twoCount))[i] - 1;
					}
					twoColumn.remove(twoColumn.get(twoCount));
					colSum = getColSum(colList, i);
				}		
			}
		}
	}
	
	private int getColSum(List<Integer[]> colList, int column) {
		int colSum = 0;
		for(int j=0; j < colList.size(); j++) {
			colSum += colList.get(j)[column]; 
		}

		return colSum;
	}
	
	private int adjustColumnCount(Integer[] columnCounts,int currentCol, List<Integer[]> colList) {
		int index = 10;
		int sum = 10;
		int colSum = 0;
		for(int i=0; i < 9; i++) {
			colSum = getColSum(colList, i);
			if(columnCounts[i] == 1) {
				if(i == 0) {
					sum = 9;
				} else if (i == 8) {
					sum = 11;
				} else {
					sum = 10;
				}
				
				if (getColSum(colList, i) < sum && i != currentCol && columnCounts[i] == 1) {
					columnCounts[i] = columnCounts[i] + 1;
					index = i;
					break;
				}
			}
		}
		
		if(index == 10) {
			for(int i=0; i < 9; i++) {
				if(i == 0) {
					sum = 9;
				} else if (i == 8) {
					sum = 11;
				} else {
					sum = 10;
				}
				colSum = getColSum(colList, i);
				if(colSum < sum && columnCounts.length < 15 && columnCounts[i] == 1) {
					columnCounts[i] = columnCounts[i] + 1;
					break;
				}
			}
		}
		
		
		
		return index;
	}

	private int[][] populateTable(int colCounts[], int rowPos[][]) {
		int table[][] = new int[9][3];

		for (int i = 0; i < 9; i++) {
			table[i] = populateColumn(i, colCounts[i], rowPos);
		}

		return table;

	}

	private int[] populateColumn(int colIndex, int colCount, int rowPos[][]) {
		int columnData[] = new int[3];
		Random random = new Random();

		if (colCount == 1) {
			int num = 0;
			if (colIndex == 0) {
				num = random.nextInt(9) + 1;

			} else if (colIndex == 8) {
				num = random.nextInt(11) + getIncrement(colIndex);

			} else {
				num = random.nextInt(10) + getIncrement(colIndex);
			}

			for (int i = 0; i < 3; i++) {
				if (rowPos[i][colIndex] == 100) {
					columnData[i] = num;
				}
			}
		} else if (colCount == 2) {
			int fNum = 0;
			int sNum = 0;

			if (colIndex == 0) {
				fNum = random.nextInt(8) + 1;
				sNum = random.nextInt(9 - fNum) + fNum + 1;
			} else if (colIndex == 8) {
				fNum = random.nextInt(10);
				sNum = random.nextInt(10 - fNum) + fNum + 1;
			} else {
				fNum = random.nextInt(9);
				sNum = random.nextInt(9 - fNum) + fNum + 1;
			}

			if (rowPos[0][colIndex] == 100) {
				columnData[0] = fNum + getIncrement(colIndex);
			}
			if (rowPos[2][colIndex] == 100) {
				columnData[2] = sNum + getIncrement(colIndex);
			}
			if ((rowPos[0][colIndex] != 100 && rowPos[1][colIndex] == 100)) {
				columnData[1] = fNum + getIncrement(colIndex);
			}
			if ((rowPos[0][colIndex] == 100 && rowPos[1][colIndex] == 100)) {
				columnData[1] = sNum + getIncrement(colIndex);
			}
		} else {
			int fNum, sNum, tNum = 0;
			if (colIndex == 0) {
				fNum = random.nextInt(8) + 1;
				sNum = random.nextInt(8 - fNum) + fNum + 1;
				tNum = random.nextInt(9 - sNum) + sNum + 1;
			} else if (colIndex == 8) {
				fNum = random.nextInt(9);
				sNum = random.nextInt(9 - fNum) + fNum + 1;
				tNum = random.nextInt(10 - sNum) + sNum + 1;
			} else {
				fNum = random.nextInt(8);
				sNum = random.nextInt(8 - fNum) + fNum + 1;
				tNum = random.nextInt(9 - sNum) + sNum + 1;
			}
			columnData[0] = fNum + getIncrement(colIndex);
			columnData[1] = sNum + getIncrement(colIndex);
			columnData[2] = tNum + getIncrement(colIndex);
		}

		return columnData;
	}

	private int getIncrement(int colIndex) {
		int index = 0;
		switch (colIndex) {
		case 1:
			index = 10;
			break;
		case 2:
			index = 20;
			break;
		case 3:
			index = 30;
			break;
		case 4:
			index = 40;
			break;
		case 5:
			index = 50;
			break;
		case 6:
			index = 60;
			break;
		case 7:
			index = 70;
			break;
		case 8:
			index = 80;
			break;
		default:
			index = 0;
			break;

		}

		return index;
	}

	private int[] randomColumnCounts() {
		Random random = new Random();
		int columnCounts[] = new int[9];
		int sum = 0;

		for (int i = 0; i < 9; i++) {
			int count = 1;

			if ((15 - sum) == 1 && i == 8) {
				count = 1;
			} else if ((15 - sum) == 2 && i == 8) {
				count = 2;
			} else if ((15 - sum) == 3 && i == 8) {
				count = 3;
			} else if ((15 - sum) == (9 - i)) {
				count = 1;
			} else if ((15 - sum) == (9 - i) * 3) {
				count = 3;
			} else if ((15 - sum) > (9 - i) * 2) {
				count = random.nextInt(2) + 2;
			} else if ((15 - sum) < (9 - i) * 2) {
				count = random.nextInt(2) + 1;
			} else {
				count = random.nextInt(3) + 1;
			}

			columnCounts[i] = count;
			sum += count;
		}

		return columnCounts;
	}

	private int[][] tablePositions(int columnCounts[]) {
		int rowPos[][] = new int[3][9];
		Random random = new Random();
		int maxColumnCount = 0;

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				if (columnCounts[j] == 3) {
					rowPos[i][j] = 100;
					if (i == 0) {
						maxColumnCount += 1;
					}
				}
			}
		}

		for (int j = 0; j < (5 - maxColumnCount); j++) {
			int num = 0;
			do {
				num = random.nextInt(9);
			} while (rowPos[0][num] == 100);
			rowPos[0][num] = 100;
		}

		for (int i = 0; i < 9; i++) {
			if (columnCounts[i] == 2 && rowPos[0][i] != 100) {
				rowPos[1][i] = 100;
				maxColumnCount += 1;
			}
		}

		for (int j = 0; j < (5 - maxColumnCount); j++) {
			int num = 0;
			do {
				num = random.nextInt(9);
			} while ((columnCounts[num] == 1 && rowPos[0][num] == 100) || rowPos[1][num] == 100);
			rowPos[1][num] = 100;
		}

		for (int k = 0; k < 9; k++) {
			if (columnCounts[k] == 1 && !((rowPos[0][k]) == 100 || rowPos[1][k] == 100)) {
				rowPos[2][k] = 100;
			} else if ((columnCounts[k] == 2 && rowPos[0][k] == 100 && rowPos[1][k] != 100)
					|| (columnCounts[k] == 2 && rowPos[0][k] != 100 && rowPos[1][k] == 100)) {
				rowPos[2][k] = 100;
			}
		}

		return rowPos;
	}
}
