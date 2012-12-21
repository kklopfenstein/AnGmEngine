package com.kklop.angmengine.game.grid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kklop.angmengine.game.grid.exception.GridException;
import com.kklop.angmengine.game.sprite.Sprite;

/**
 * Sprite grid to help track object collisions.
 * @author Kevin Klopfenstein
 *
 */
public class Grid {
	
	/* sprite array */
	private HashMap<Integer,Sprite> grid[];
	
	private int width;
	private int height;
	private int size;
	
	/* class level counter to assign unique id
	 * to a grid member
	 */
	private int gridId = 0;
	
	private HashMap<Integer,Integer> posMap;
	
	/**
	 * Initialize grid based on width, height and size in
	 * units of pixels.
	 * @param widthPx
	 * @param heightPx
	 * @param sizePx
	 * @throws GridException
	 */
	@SuppressWarnings("unchecked")
	public Grid(int widthPx, int heightPx, int sizePx) throws GridException {
		if(widthPx%sizePx != 0 || heightPx%sizePx != 0) {
			throw new GridException("Grid size invalid.");
		}
		
		this.width = widthPx/sizePx;
		this.height = heightPx/sizePx;
		
		this.grid = new HashMap[width*height];
		this.size = sizePx;
		
		this.posMap = new HashMap<Integer, Integer>();
	}
	
	/**
	 * Update sprite positions in grid.
	 * @param sprites
	 * @throws GridException
	 */
	public void update(ArrayList<Sprite> sprites) throws GridException {
		for(Sprite sprite : sprites) {
			// if it isn't added to the grid add it
			if(this.posMap.get(sprite.getGridId()) == null) {
				addSprite(sprite);
			}
			
			if(Sprite.SPRITE_STATE.MOVING.
					equals(sprite.getState())) {
				updateSprite(sprite);
			}
		}
	}
	
	/**
	 * Update sprite position on grid
	 * @param sprite
	 * @throws GridException
	 */
	private void updateSprite(Sprite sprite) throws GridException {
		Integer pos = posMap.get(sprite.getGridId());
		if(pos == null) {
			throw new GridException("Sprite not found in method " +
					"updateSprite of Grid.");
		}
		
		// remove from grid cell
		this.grid[pos].remove(sprite.getGridId());
		
		// re-insert into grid at new position
		gridInsert(sprite);
	}
	
	/**
	 * Put sprite into 
	 * @param sprite
	 * @param gridNumber
	 * @throws GridException
	 */
	private Integer gridInsert(Sprite sprite) throws GridException {
		// get the grid position
		int gridPos = getGridNumForXY(sprite.getX(), sprite.getY());
		
		// add sprite to that grid cell
		this.grid[gridPos].put(this.gridId, sprite);
		this.grid[gridPos-1].put(this.gridId, sprite);
		this.grid[gridPos+1].put(this.gridId, sprite);
		//this.grid[gridPos]
		
		// add sprite to position map
		this.posMap.put(sprite.getGridId(), gridPos);
		
		return gridPos;
	}
	
	/**
	 * For any particular cell, the numbering is
	 * as follows
	 * -------------
	 * | 1 | 2 | 3 |
	 * | 4 | C | 5 |
	 * | 6 | 7 | 8 |
	 * ------------- 
	 * @param sprite
	 * @param pos
	 * @param id
	 * @throws GridException
	 */
	private void addToCellWithBounds(Sprite sprite, int pos, int id) 
			throws GridException {
		
		int cell1 = pos-this.width-1;
		int cell2 = pos-this.width;
		int cell3 = pos-this.width+1;
		int cell4 = pos-1;
		int cell5 = pos+1;
		int cell6 = pos+width-1;
		int cell7 = pos+width;
		int cell8 = pos+width+1;
		
		// first put in position
		this.grid[pos].put(pos, sprite);
		
		// left side
		if((pos%this.width) == 0) {
			this.grid[cell5].put(id, sprite);
			// if we're first cell
			if(pos == 0) {
				this.grid[cell7].put(id, sprite);
				this.grid[cell8].put(id, sprite);
			}
			// if we're first cell last row
			else if(pos == (this.grid.length-this.width)) {
				this.grid[cell2].put(id, sprite);
				this.grid[cell3].put(id, sprite);
			}
			// otherwise
			else {
				this.grid[cell2].put(id, sprite);
				this.grid[cell3].put(id, sprite);
				this.grid[cell7].put(id, sprite);
				this.grid[cell8].put(id, sprite);
			}
			 
		}
		// right side
		else if((pos%width) == 1) {
			this.grid[cell4].put(id, sprite);
			// if we're last cell first row
			if(pos == width-1) {
				this.grid[cell6].put(id, sprite);
				this.grid[cell7].put(id, sprite);
			}
			// if we're last cell
			else if(pos == (this.grid.length-1)) {
				this.grid[cell1].put(id, sprite);
				this.grid[cell2].put(id, sprite);
			}
			// otherwise
			else {
				this.grid[cell2].put(id, sprite);
				this.grid[cell1].put(id, sprite);
				this.grid[cell6].put(id, sprite);
				this.grid[cell7].put(id, sprite);
			}
		}
	}
	
	/**
	 * Add new sprite to grid
	 * @param sprite
	 * @throws GridException
	 */
	public void addSprite(Sprite sprite) throws GridException {
		sprite.setGridId(this.gridId);

		gridInsert(sprite);
		
		this.gridId++;
	}

	/**
	 * Returns grid position in array.
	 * @param x
	 * @param y
	 * @return
	 * @throws GridException
	 */
	private int getGridNumForXY(float x, float y) throws GridException {
		int gridY = Float.valueOf(y/this.size).intValue();
		int gridX = Float.valueOf(x/this.size).intValue();
		
		return (gridY*gridX)-1;
	}
	
	/**
	 * Return a list of cells where
	 * collisions are possible.
	 * @return
	 */
	private List<HashMap<Integer, Sprite>> getCollCells() {
		List<HashMap<Integer, Sprite>> result = 
				new ArrayList<HashMap<Integer, Sprite>>();
		
		for(int i=0;i<this.grid.length;i++) {
			HashMap<Integer,Sprite> cell = grid[i];
			if(cell != null && cell.size() > 1) {
				result.add(cell);
			}
		}
		
		return result;
	}
	
}
