package a8;

import java.util.ArrayList;
import java.util.List;

public class LifeModel {
	
	private List<LifeObserver> observers;
	
	private boolean[][] grid;
	private int size;
	
	public LifeModel() {
		observers = new ArrayList<LifeObserver>();
		grid = new boolean[size][size];
	}
	
	public void setSize(int size) {
		this.size = size;
		grid = new boolean[size][size];
		notifyObservers(grid);
	}
	
	public int getSize() {
		return size;
	}
	
	public void next(int surviveMin, int surviveMax, int birthMin, int birthMax) {
		boolean[][] newGrid = new boolean[size][size];
		// for every tile
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				// count neighbors
				int neighbors = 0;
				// EDGE cases
				if (x == 0 || x == size -1 || y == 0 || y == size -1) {
					// top left corner
					if (x == 0 && y == 0) {
						if (grid[1][0]) neighbors++;
						if (grid[0][1]) neighbors++;
						if (grid[1][1]) neighbors++;
					} else
					
					// top right corner
					if (x == size-1 && y == 0) {
						if (grid[size-2][0]) neighbors++;
						if (grid[size-1][1]) neighbors++;
						if (grid[size-2][1]) neighbors++;
					} else
					
					// bottom left corner
					if (x == 0 && y == size-1) {
						if (grid[0][size-2]) neighbors++;
						if (grid[1][size-1]) neighbors++;
						if (grid[1][size-2]) neighbors++;
					} else
					
					// bottom right corner
					if (x == size-1 && y == size-1) {
						if (grid[size-1][size-2]) neighbors++;
						if (grid[size-2][size-1]) neighbors++;
						if (grid[size-2][size-2]) neighbors++;
					} else
					
					// top edge
					if (y == 0) {
						for (int i = -1; i < 2; i++) {
							for (int j = 0; j < 2; j++) {
								if (grid[x+i][y+j]) {
									neighbors++;
								}
							}
						}
						if (grid[x][y]) {
							neighbors--;
						}
					} else
						
					// left edge
					if (x == 0) {
						for (int i = 0; i < 2; i++) {
							for (int j = -1; j < 2; j++) {
								if (grid[x+i][y+j]) {
									neighbors++;
								}
							}
						}
						if (grid[x][y]) {
							neighbors--;
						}
					} else
						
					// bottom edge
					if (y == size-1) {
						for (int i = -1; i < 2; i++) {
							for (int j = -1; j < 1; j++) {
								if (grid[x+i][y+j]) {
									neighbors++;
								}
							}
						}
						if (grid[x][y]) {
							neighbors--;
						}
					} else
						
					// right edge
					if (x == size-1) {
						for (int i = -1; i < 1; i++) {
							for (int j = -1; j < 2; j++) {
								if (grid[x+i][y+j]) {
									neighbors++;
								}
							}
						}
						if (grid[x][y]) {
							neighbors--;
						}
					} 
				} else {
					// normal tile
					for (int i = -1; i < 2; i++) {
						for (int j = -1; j < 2; j++) {
							if (grid[x+i][y+j]) {
								neighbors++;
							}
						}
					}
					if (grid[x][y]) {
						neighbors--;
					}
				}
				// do stuff based on neighbors
				if (grid[x][y] && neighbors < surviveMin) {
					newGrid[x][y] = !grid[x][y];
				} else if (grid[x][y] && neighbors > surviveMax) {
					newGrid[x][y] = !grid[x][y];
				} else {
					newGrid[x][y] = grid[x][y];
				}
				
				if (!grid[x][y] && neighbors >= birthMin && neighbors <= birthMax) newGrid[x][y] = !grid[x][y]; 
			}	
		}
		grid = newGrid;
		notifyObservers(grid);
	}
	
	public void torusNext(int surviveMin, int surviveMax, int birthMin, int birthMax) {
		boolean[][] newGrid = new boolean[size][size];
		
		for (int x = 0; x < size; x++) {
			for(int y = 0; y < size; y++) {
				int neighbors = 0;
				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						int col = (x + i + size) % size;
						int row = (y + j + size) % size;
						if (grid[col][row]) {
							neighbors++;
						}
					}
				}
				if (grid[x][y]) {
					neighbors--;
				}
				
				if (grid[x][y] && neighbors < surviveMin) {
					newGrid[x][y] = !grid[x][y];
				} else if (grid[x][y] && neighbors > surviveMax) {
					newGrid[x][y] = !grid[x][y];
				} else {
					newGrid[x][y] = grid[x][y];
				}
				
				if (!grid[x][y] && neighbors >= birthMin && neighbors <= birthMax) newGrid[x][y] = !grid[x][y]; 

			}
		}
		grid = newGrid;
		notifyObservers(grid);
	}
	
	public void toggleTileAt(int row, int col) {
		grid[row][col] = !grid[row][col];
		notifyObservers(grid);
	}
	
	public void clear() {
		grid = new boolean[size][size];
		notifyObservers(grid);
	}
	
	public void randomize() {
		boolean[][] newGrid = new boolean[size][size];
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (Math.random() < .5) {
					newGrid[i][j] = true;
				}
			}
		}
		grid = newGrid;
		notifyObservers(grid);
	}
	
	public void addObserver(LifeObserver o) {
		observers.add(o);
	}
	
	public void removeObserver(LifeObserver o) {
		observers.remove(o);
	}

	private void notifyObservers(boolean[][] grid) {
		for (LifeObserver o : observers) {
			o.update(this, grid);
		}
	}
}
