package de.mxro.trs.commands;

import de.mxro.trs.Command;
import de.mxro.trs.Direction;
import de.mxro.trs.Robot;
import de.mxro.trs.internal.PlacedRobot;

/**
 * <P>Places the robot onto the table facing in a specific direction.
 *
 */
public class PlaceCommand implements Command {
	
	public final int x;
	public final int y;
	public final Direction direction;
	
	public Robot apply(Robot robot) {
		return new PlacedRobot(x, y, direction);
	}

	public PlaceCommand(int x, int y, Direction direction) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlaceCommand other = (PlaceCommand) obj;
		if (direction != other.direction)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	

	
}
