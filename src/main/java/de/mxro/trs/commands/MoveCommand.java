package de.mxro.trs.commands;

import de.mxro.trs.Command;
import de.mxro.trs.Robot;

/**
 * <p>Moves the robot one field <b>forward</b> in the direction it is facing.
 *
 */
public class MoveCommand implements Command {

	public Robot apply(Robot robot) {
		return robot.move();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof MoveCommand;
	}
	
	@Override
	public int hashCode() {
		// assuring hashcode works correctly given that all instances of the class are considered equal
		return this.getClass().hashCode();
	}
	
}
