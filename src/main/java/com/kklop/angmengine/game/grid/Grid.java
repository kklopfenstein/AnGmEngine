package com.kklop.angmengine.game.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import android.graphics.PointF;
import android.util.SparseArray;

import com.kklop.angmengine.game.grid.exception.GridException;
import com.kklop.angmengine.game.sprite.Sprite;

/**
 * Sprite grid to help track object collisions.
 * @author Kevin Klopfenstein
 *
 */
public class Grid {
	
	List<Sprite> sprites;
	/* sprite array */
	private HashMap<Integer,Sprite> grid[];
	
	private int width;
	private int height;
	private int size;
	
	/* class level counter to assign unique id
	 * to a grid member
	 */
	private int gridId = 0;
	
	private SparseArray<HashSet<Integer>> posMap;
	
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
		
		this.posMap = new SparseArray<HashSet<Integer>>();
		
		this.sprites = new ArrayList<Sprite>();
	}
	
	/**
	 * Add new sprite to grid
	 * @param sprite
	 * @throws GridException
	 */
	public void addSprite(Sprite sprite) throws GridException {
		sprite.setGridId(this.gridId);
		this.sprites.add(sprite);
		add(sprite);
		this.gridId++;
	}
	
	/**
	 * Update entire grid
	 * @throws GridException
	 */
	public void update() throws GridException {
		for(Sprite sprite : this.sprites) {
			if(Sprite.SPRITE_STATE.MOVING.equals(sprite.getState())) {
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
		// remove sprite from grid
		remove(sprite);
		// re-insert into grid at new position
		add(sprite);
	}
	
	/**
	 * Returns grid position in array.
	 * @param x
	 * @param y
	 * @return
	 * @throws GridException
	 */
	private int getGridNumForXY(PointF p) throws GridException {
		int gridY = Float.valueOf(p.y/this.size).intValue();
		int gridX = Float.valueOf(p.x/this.size).intValue();
		
		return (gridY*gridX)-1;
	}
	
	/**
	 * Return a list of cells where
	 * collisions are possible.
	 * @return
	 */
	public List<Sprite> getCollisions(Sprite sprite) throws GridException {
		List<Sprite> collisions = new ArrayList<Sprite>();
		if(sprite.getGridId() == null) {
			throw new GridException("Sprite has not been added to the grid.");
		}
		
		HashSet<Integer> cells = this.posMap.get(sprite.getGridId());
		for(Integer i : cells) {
			collisions.addAll(checkColsAtCell(i, sprite));
		}
		return collisions;
	}
	
	/**
	 * Check sprite collisions at single cell
	 * @param pos
	 * @param sprite
	 */
	private List<Sprite> checkColsAtCell(int pos, Sprite sprite) {
		List<Sprite> collisions = new ArrayList<Sprite>();
		HashMap<Integer, Sprite> cell = this.grid[pos];
		if(cell != null && cell.size() > 0) {
			Collection<Sprite> possibleSprites = cell.values();
			for(Sprite s : possibleSprites) {
				if(sprite.collided(s)) {
					collisions.add(s);
				}
			}
		}
		
		return collisions;
	}
	
	/**
	 * Add to grid and also add to position map
	 * @param pos
	 * @param sprite
	 */
	private void addToCell(int pos, Sprite sprite) {
		this.grid[pos].put(sprite.getGridId(), sprite);
		HashSet<Integer> s = this.posMap.get(pos);
		if(s == null) {
			s = new HashSet<Integer>();
		}
		s.add(pos);
		this.posMap.put(sprite.getGridId(), s);
	}
	
	/**
	 * Put sprite into grid and position map
	 * @param sprite
	 * @param gridNumber
	 * @throws GridException
	 */
	private void add(Sprite sprite) throws GridException {
		/* Put sprite in the buckets for each of it's four corners.
		 * Some or all of these could be the same bucket. 
		 */
		addToCell(getGridNumForXY(sprite.getTopLeftCrnr()), sprite);
		addToCell(getGridNumForXY(sprite.getTopRightCrnr()), sprite);
		addToCell(getGridNumForXY(sprite.getBotLeftCrnr()), sprite);
		addToCell(getGridNumForXY(sprite.getBotRightCrnr()), sprite);
	}
	
	/**
	 * Remove sprite from grid and position map
	 * @param sprite
	 */
	private void remove(Sprite sprite) {
		HashSet<Integer> pos = posMap.get(sprite.getGridId());
		if(pos != null && pos.size() > 0) {
			for(Integer p : pos) {
				this.grid[p].remove(sprite.getGridId());
			}
		}
		posMap.remove(sprite.getGridId());
	}
	
}
