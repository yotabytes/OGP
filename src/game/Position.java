package game;

/**
 * This class represents positions that consist of x and y coordinates, and has facilities to get and set positions.
 * @author Wouter Bruyninckx
 * @version 1.0
 *
 */
public class Position {
	
	/**
	 * Initializes this position with the given x and y coordinate.
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	Position(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return The X coordinate of this position.
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * @return The Y coordinate of this position.
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * Set the X coordinate of this position to the given value.
	 * @param newx New X coordinate.
	 */
	public void setX(double newx) {
		this.x = newx;
	}
	
	/**
	 * Set the Y coordinate of this position to the given value.
	 * @param newy New Y coordinate.
	 */
	public void setY(double newy) {
		this.y = newy;
	}
	
	double x;
	double y;
	

}
