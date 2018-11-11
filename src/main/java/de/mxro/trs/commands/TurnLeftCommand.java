package de.mxro.trs.commands;

import de.mxro.trs.Command;
import de.mxro.trs.Robot;

/**
 * <p>Turns the robot left.
 *
 */
public class TurnLeftCommand implements Command {

	public Robot apply(Robot robot) {
		return robot.turnLeft();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof TurnLeftCommand;
	}
	
	@Override
	public int hashCode() {
		// assuring hashcode works correctly given that all instances of the class are considered equal
		return this.getClass().hashCode();
	}
	
}
