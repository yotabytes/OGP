package game;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import asteroids.IShip;

/**
 * A class of ships involving a radius, x and y coordinates, a direction, a velocity, a radius and movement/collision
 * detecting facilities.
 * All velocities are expressed in km/s, and Distances and radii in kilometers.
 * @author Wouter Bruyninckx
 * @version 1.0
 *
 */
public class Ship implements IShip {

	private final double MINRADIUS = 10; // km
	private final double LIGHTSPEED = 299800; // km/s
	
	/**
	 * Initialize this new ship with an x and y coordinate, a direction and velocity, and a radius.
	 * @param x The x coordinate for this new ship.
	 * @param y The y coordinate for this new ship.
	 * @param direction The direction this new ship is facing, expressed as its angle in radians.
	 * @param velocity The velocity for this new ship, expressed in km/s.
	 * @param radius The radius for this new ship.
	 * @post A new ship is created with a valid x and y coordinate, a valid direction, a valid velocity, and a valid radius.
	 * 			|new.getX() == x
	 * 			|new.getY() == y
	 * 			|new.getDirection == direction
	 * 			|new.getVelocity == velocity
	 * 			|new.getRadius == radius
	 */
	public Ship(double x, double y, double direction, double velocity, double radius) {
		this.setPosition(x, y);
		this.setX(x);
		this.setY(y);
		this.setDirection(direction);
		this.velocity = velocity;
		if (radius > MINRADIUS) {
			this.radius = radius;
		} else {
			this.radius = MINRADIUS;
		}
	}
	
	/**
	 * Set the new position of this ship.
	 * @param x The x coordinate of this new position.
	 * @param y The y coordinate of this new position.
	 * @post A new position is created with the given x and y coordinates.
	 * 		|new.getPosition() == new Position(x,y) 
	 */
	private void setPosition(double x, double y) {
		this.position = new Position(x,y);
		
	}

	/**
	 * Returns the x coordinate of this ship.
	 * @return The x coordinate of this ship.
	 * | result = position.getX()
	 */
	@Basic @Raw 
	public double getX() {
		return this.position.getX();
	}
	/**
	 * Sets the x coordinate of this ship to the given value. Also updates the position of this ship to give clients the flexibility
	 * of using either the position or x/y coordinates separately.
	 * @param x New x coordinate of this ship.
	 * @post The x coordinate of this ship is equal to the given value.
	 * 		|new.getX() == x
	 */
	@Raw
	public void setX(double x) {
		this.x = x;
		this.position.setX(x);
	}
	
	
	/**
	 * Returns the y coordinate of this ship.
	 * @return The x coordinate of this ship.
	 * | Result = position.getY()
	 */
	@Basic 
	public double getY() {
		return this.position.getY();
	}
	/**
	 * Sets the y coordinate of this ship, and of the position of this ship, to the given value.
	 * @param y The y coordinate of this ship.
	 * @post The y coordinate of this ship is equal to the given value.
	 * 		|new.getY() == y
	 */
	public void setY(double y) {
		this.y = y;
		this.position.setY(y);
	}
	private double x;
	private double y;
	private Position position;
	
	/**
	 * Moves the ship based on its current position, velocity and a given duration.
	 * @param duration Time span over which the ship will move, used to calculate the new position of the ship.
	 * @throws NegativeTimeException If the given time duration is smaller than zero.
	 * @post The position of this ship is equal to the position consisting of the new x and y coordinates that are calculated, based
	 * on the current position and velocities in their respective axis.
	 * 		|new.getX() == x
	 * 		|new.getY() == y
	 */
	public void move(double duration) { //Defensively
		if (duration < 0)
			throw new NegativeTimeException(duration);
		double newx = this.getX() + duration * this.getXVelocity();
		double newy = this.getY() + duration * this.getYVelocity();
		this.setX(newx);
		this.setY(newy);
	}
	
	/**
	 * Returns the true velocity of this ship.
	 */
	@Basic @Immutable public double getVelocity() {
		return this.velocity;
	}
	
	/**
	 * Sets the velocity of this ship to the given amount, if this amount does not exceed the speed of light, and if this amount is
	 * not smaller than zero.
	 * @param newvelocity The new (true) velocity of this ship.
	 * @post If the given velocity does not exceed the speed of light, and if it is not smaller than zero, the velocity of this ship
	 * is equal to the given velocity.
	 */
	public void setVelocity(double newvelocity) {
		if (newvelocity >= 0 && newvelocity <= LIGHTSPEED)
			this.velocity = newvelocity;
	}
	
	/**
	 * Returns the x component of the velocity. This is equal to the true velocity, multiplied by the cos of the current direction.
	 * @return The x component of the velocity of this ship.
	 */
	@Immutable public double getXVelocity() {
		return this.velocity * Math.cos(this.direction);
	}
	
	/**
	 * Returns the y component of the velocity. This is equal to the true velocity, multiplied by the sin of the current direction.
	 * @return The y component of the velocity of this ship.
	 */
	@Immutable public double getYVelocity() {
		return this.velocity * Math.sin(this.direction);
	}
	
	private double velocity;
	
	/**
	 * Returns the direction of this ship as its current angle in radians.
	 * @return Direction of this ship.
	 */
	@Basic @Immutable public double getDirection() {
		return this.direction;
	}
	 /**
	  * Set the direction of this ship to the given amount in radians.
	  * @param newdirection New direction of this ship.
	  * @pre The given direction is a valid direction.
	  * 	isValidDirection()
	  * @post The direction of this ship is equal to the given direction, if this is a valid direction.
	  */
	public void setDirection(double newdirection) {
			this.direction = newdirection;
	}
	
	/**
	 * Checks whether the given direction is a valid direction.
	 * @return True if the given direction does not exceed the limits of -2PI and 2PI.
	 */
	public static boolean isValidDirection(double dir) {
		if ((dir > -2 * Math.PI) && (dir < 2 * Math.PI))
			return true;
		else
			return false;
	}
	
	/**
	 * Turns the ship by adding the given angle to its current angle in radians.
	 * @param angle The amount of radians to be added to the current angle.
	 * @pre The given angle is smaller than 2PI and greater than -2PI.
	 * @post The direction of this ship is equal to the old direction, added with the given angle. If the new direction is smaller
	 * than -2PI, add k times 2PI until it is not smaller anymore. If the new direction is greater than 2PI, subtract k times 2PI 
	 * until it is not smaller anymore.
	 */
	public void turn(double angle) { //Nominally
		double newdirection = this.direction + angle;
		while (newdirection < -2 * Math.PI) {
			newdirection += 2 * Math.PI;
		}
		while (newdirection > 2 * Math.PI) {
			newdirection -= 2 * Math.PI;
		}
		this.direction = newdirection;
	}
	
	private double direction;

	
	/**
	 * Returns the radius of this ship.
	 * @return The radius of this ship.
	 */
	@Basic @Immutable public double getRadius() {
		return this.radius;
	}
	
	
	private final double radius; // Must be >10, defensive
	
	/**
	 * Changes the velocity of the ship based on the given amount, its current direction and its current velocity.
	 * @param amount Amount to be added to current velocity (km/s). 
	 * @post If the given amount is larger than zero, and the new velocity would not exceed the speed of light c, the new 
	 * velocity is the old velocity added with the new velocity. If the given amount is smaller than zero, the new velocity is
	 * the old velocity. If the given amount added with the current amount exceeds c, the new velocity is set to c.
	 */
	public void thrust(double amount) { //Totally
		if (amount < 0)
			return;
		else if (amount + this.velocity > LIGHTSPEED)
			this.setVelocity(LIGHTSPEED);
		else {
			//double newxvelocity = this.getXVelocity() + amount * Math.cos(direction);
			//double newyvelocity = this.getYVelocity() + amount * Math.cos(direction);
			//double newvelocity = Math.sqrt(Math.pow(newxvelocity, 2) + Math.pow(newyvelocity, 2));
			//this.setVelocity(newvelocity);
			this.setVelocity(this.getVelocity() + amount);
		}
	}
	
	/**
	 * Returns the distance between this ship and the given ship. May be negative if both ships overlap.
	 * @param other Other space craft, to get the distance between this ship and the given other ship.
	 * @return Distance in kilometers between the two ships.
	 * @throws IllegalArgumentException If other ship is a null-pointer.
	 */
	public double getDistanceBetween(Ship other) throws IllegalArgumentException { //Defensively
		try {
		double distancex = Math.abs(this.x - other.getX());
		double distancey = Math.abs(this.y - other.getY());
		double distance = Math.sqrt(distancex*distancex+distancey*distancey); //Distance between centers
		return distance - this.radius - other.getRadius();
		} catch (NullPointerException exc) {
			throw new IllegalArgumentException("Other ship is not a valid ship");
		}
	}
	
	/**
	 * Returns whether this ship and the given ship overlap with each other.
	 * @param other Other ship to check with.
	 * @return True if both ships overlap, false if they do not.
	 * @throws IllegalArgumentException If other ship is a null-pointer.
	 */
	public boolean overlap(Ship other) throws IllegalArgumentException { //Defensively
		try { if (getDistanceBetween(other) < 0)
			return true;
		else
			return false;
		} catch (NullPointerException exc) {
			throw new IllegalArgumentException("Other ship is not a valid ship");
		}
	}
	
	/**
	 * Returns the time until this ship will collide with the given other ship.
	 * @param other Other ship to calculate collision time with.
	 * @return Time until collision between this ship and the other ship. If the ships never collide, this is equal to 
	 * Double.POSITIVE_INFINITY.
	 * @throws IllegalArgumentException If other ship is a null-pointer.
	 */
	public double getTimeToCollision(Ship other) throws IllegalArgumentException { //Defensively
		try { double deltavx = this.getXVelocity() - other.getXVelocity(); 
		
		double deltavy = this.getYVelocity() - other.getYVelocity();
		double deltarx = this.getX() - other.getX();
		double deltary = this.getY() - other.getY();
		double sigma = this.getRadius() + other.getRadius();
		double a = Math.pow(deltavx, 2) + Math.pow(deltavy, 2);
		double b = deltavx * deltarx + deltavy * deltary;
		double c = Math.pow(deltarx, 2) + Math.pow(deltary, 2) - Math.pow(sigma, 2);
		double d = Math.pow(b, 2) - a * c;
		double solution = (-b - Math.sqrt(d)) / a;
		
		if (b >= 0)
			solution = Double.POSITIVE_INFINITY;
		if (d <= 0)
			solution = Double.POSITIVE_INFINITY;
		return solution;
		}
		catch (NullPointerException exc) {
			throw new IllegalArgumentException("Other ship is not a valid ship");
		}
	}
	
	/**
	 * Returns the position where this ship will collide with the given other ship, if a valid other ship argument is given and
	 * they will ever collide based on their current tracks.
	 * @param other Other ship to calculate collision point with
	 * @return Position where both ships will collide. If the ships never collide, this is null.
	 * @throws IllegalArgumentException If other ship is a null-pointer.
	 */
	public Position getCollisionPosition(Ship other) { //Defensively; Return x and y coordinates
		double timetocollision = getTimeToCollision(other);
		if (timetocollision == Double.POSITIVE_INFINITY)
			return null;
		double newxi = this.getX() + timetocollision * this.getXVelocity();
		double newxj = other.getX() + timetocollision * other.getXVelocity();
		double newyi = this.getY() + timetocollision * this.getYVelocity();
		double newyj = other.getY() + timetocollision * other.getYVelocity();
		double distance = this.getRadius() + other.getRadius();
		double cosangle = Math.abs(newxi-newxj) / distance;
		double finalx = cosangle * this.getRadius();
		double sinangle = Math.abs(newyi - newyj) / distance;
		double finaly = sinangle * this.getRadius();
		return new Position(finalx, finaly);
	}

}
