// Possibly redundant subclass of Entity.
// There are two types of items: Axe and boat.
// Upon collection, informs the Player
// that the Player does indeed have the item.

package code.Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import code.Manager.Content;
import code.TileMap.TileMap;

/**
 *
 * @author CHARKEYD
 */
public class Item extends Entity{
	
	private BufferedImage sprite;
	private int type;

    /**
     *
     */
    public static final int BOAT = 0;

    /**
     *
     */
    public static final int AXE = 1;
	
    /**
     *
     * @param tm
     */
    public Item(TileMap tm) {
		super(tm);
		type = -1;
		width = height = 16;
		cwidth = cheight = 12;
	}
	
    /**
     *
     * @param i
     */
    public void setType(int i) {
		type = i;
		if(type == BOAT) {
			sprite = Content.ITEMS[1][0];
		}
		else if(type == AXE) {
			sprite = Content.ITEMS[1][1];
		}
	}
	
    /**
     *
     * @param p
     */
    public void collected(Player p) {
		if(type == BOAT) {
			p.gotBoat();
		}
		if(type == AXE) {
			p.gotAxe();
		}
	}
	
	public void draw(Graphics2D g) {
		setMapPosition();
		g.drawImage(sprite, x + xmap - width / 2, y + ymap - height / 2, null);
	}
	
}