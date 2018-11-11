package de.mxro.trs;

import org.junit.Assert;
import org.junit.Test;

import de.mxro.trs.internal.PlacedRobot;


public class TestPlacedRobot {
		
	@Test
	public void test_turn_left() {
		PlacedRobot robot = new PlacedRobot(2, 2, Direction.NORTH);
		robot = (PlacedRobot) robot.turnRight();
		Assert.assertEquals(Direction.EAST, robot.direction);
		robot = (PlacedRobot) robot.turnRight();
		Assert.assertEquals(Direction.SOUTH, robot.direction);
		robot = (PlacedRobot) robot.turnRight();
		Assert.assertEquals(Direction.WEST, robot.direction);
		robot = (PlacedRobot) robot.turnRight();
		Assert.assertEquals(Direction.NORTH, robot.direction);
	}
	
	@Test
	public void test_turn_right() {
		PlacedRobot robot = new PlacedRobot(2, 2, Direction.WEST);
		robot = (PlacedRobot) robot.turnLeft();
		Assert.assertEquals(Direction.SOUTH, robot.direction);
		robot = (PlacedRobot) robot.turnLeft();
		Assert.assertEquals(Direction.EAST, robot.direction);
		robot = (PlacedRobot) robot.turnLeft();
		Assert.assertEquals(Direction.NORTH, robot.direction);
		robot = (PlacedRobot) robot.turnLeft();
		Assert.assertEquals(Direction.WEST, robot.direction);
	}
	
}
