package de.mxro.trs;

import org.junit.Assert;
import org.junit.Test;


public class TestDirection {
	
	@Test
	public void test_north_delta() {
		Direction dir = Direction.NORTH;
		
		Assert.assertEquals(1, dir.getDeltaY());
		Assert.assertEquals(0, dir.getDeltaX());
	}

	@Test
	public void test_south_delta() {
		Direction dir = Direction.SOUTH;
		
		Assert.assertEquals(-1, dir.getDeltaY());
		Assert.assertEquals(0, dir.getDeltaX());
	}
	
	@Test
	public void test_west_delta() {
		Direction dir = Direction.WEST;
		
		Assert.assertEquals(0, dir.getDeltaY());
		Assert.assertEquals(-1, dir.getDeltaX());
	}
	
	@Test
	public void test_east_delta() {
		Direction dir = Direction.EAST;
		
		Assert.assertEquals(0, dir.getDeltaY());
		Assert.assertEquals(1, dir.getDeltaX());
	}
	
	@Test
	public void test_angle_mapping() {
		
		Assert.assertEquals(Direction.NORTH, Direction.fromAngle(90));
		Assert.assertEquals(Direction.SOUTH, Direction.fromAngle(-90));
		Assert.assertEquals(Direction.SOUTH, Direction.fromAngle(270));
		Assert.assertEquals(Direction.EAST, Direction.fromAngle(0));
		Assert.assertEquals(Direction.NORTH, Direction.fromAngle(90));
		
		// testing angle wraparound
		Assert.assertEquals(Direction.EAST, Direction.fromAngle(0+360*4));
		Assert.assertEquals(Direction.NORTH, Direction.fromAngle(90+360*2));
		
		
	}
	
}


