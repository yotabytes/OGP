package asteroids.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import asteroids.IFacade;
import asteroids.IShip;
import asteroids.ModelException;
import asteroids.Util;
import game.Facade;
import game.Ship;

public class ShipTest {

  private Ship ship5, ship_negativespeed, ship50;
  
  @Before
  public void setUpMutableFixture() {
	  ship5 = new Ship(0, 0, 0, 0, 5);
	  ship_negativespeed = new Ship(0, 0, Math.PI, 10);
	  ship50 = new Ship(10, 10, Math.PI/2, 50);
  }
  
  @BeforeClass
  public void SetUpImmutableFixture() {
	  
  }
  
  @Test
  public void constructor_smallradius() {
	  assertEquals(10, ship5.getRadius(), Util.EPSILON);
  }
  
  @Test
  public void constructor_negativespeed() {
	  assertEquals(0, ship_negativespeed.getVelocity(), Util.EPSILON);
  }
  
  @Test
  public void thrust_negative() {
	  ship5.thrust(-5);
	  assertEquals(0, ship5.getVelocity(), Util.EPSILON);
  }
 
  @Test
  public void thrust_positive() {
	  ship5.thrust(5);
	  assertEquals(5, ship5.getVelocity(), Util.EPSILON);
  }
  
  @Test
  public void thrust_exceedlightspeed() {
	  ship5.thrust(300000);
	  assertEquals(299800, ship5.getVelocity(), Util.EPSILON);
  }
  
  @Test (expected = NegativeTimeException.class)
  public void move_negativetime() {
	  ship50.move(-10);
  }

}
