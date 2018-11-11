package de.mxro.trs.commands;

import de.mxro.trs.Command;
import de.mxro.trs.Robot;

/**
 * <p>Reports the current position of the robot.
 *
 */
public class ReportCommand implements Command {

	public Robot apply(Robot robot) {
		// the report command does not alter the state of the system
		return robot;
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof ReportCommand;
	}

	@Override
	public int hashCode() {
		// assuring hashcode works correctly given that all instances of the class are considered equal
		return this.getClass().hashCode();
	}
	
}
