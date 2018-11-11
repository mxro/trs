package de.mxro.trs.commands;

import de.mxro.trs.Command;
import de.mxro.trs.Robot;

/**
 * Turns the robot right.
 *
 */
public class TurnRightCommand implements Command {

	public Robot apply(Robot robot) {
		return robot.turnRight();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof TurnRightCommand;
	}

	@Override
	public int hashCode() {
		// assuring hashcode works correctly given that all instances of the class are considered equal
		return this.getClass().hashCode();
	}
	
}
