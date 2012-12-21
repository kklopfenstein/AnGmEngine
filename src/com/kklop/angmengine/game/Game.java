package com.kklop.angmengine.game;

import java.util.ArrayList;

import com.kklop.angmengine.game.exception.GameException;
import com.kklop.angmengine.game.grid.Grid;
import com.kklop.angmengine.game.sprite.Sprite;

/**
 * 
 * @author Kevin Klopfenstein
 *
 */
public class Game {
	ArrayList<Sprite> sprites;
	Grid grid;
	
	/**
	 * Initialize game with grid
	 * @param width
	 * @param height
	 * @param size
	 * @throws GameException
	 */
	public Game(int width, int height, int size) throws GameException {
		grid = new Grid(width, height, size);
	}
}
