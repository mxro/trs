package de.mxro.trs.internal;

import de.mxro.trs.Robot;

/**
 * The state of the robot when it is not placed on the table.
 */
public class OffTableRobot implements Robot {
	
	@Override
	public Robot move() {
		return this;
	}
	
	@Override
	public Robot turnRight() {
		return this;
	}

	@Override
	public Robot turnLeft() {
		return this;
	}
	
	@Override
	public String toString() {
		return "OFFTABLE";
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof OffTableRobot;
	}
	
	@Override
	public int hashCode() {
		// assuring hashcode works correctly given that all instances of the class are considered equal
		return this.getClass().hashCode();
	}
	
	
}
