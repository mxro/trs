package de.mxro.trs.internal;

import de.mxro.trs.Direction;
import de.mxro.trs.Robot;

/**
 * The state of the robot when placed on the table.
 *
 */
public class PlacedRobot implements Robot {

	/**
	 * The x coordinate of the robot on the table.
	 */
	public final int x;

	/**
	 * The y coordinate of the robot on the table.
	 */
	public final int y;

	/**
	 * The direction the robot is facing.
	 */
	public final Direction direction;

	/**
	 * Move the robot to the direction it is facing.
	 */
	public Robot move() {
		return new PlacedRobot(x + direction.getDeltaX(), y + direction.getDeltaY(), direction);
	}

	/**
	 * Turn the robot right.
	 */
	public Robot turnRight() {
		return new PlacedRobot(x, y, Direction.fromAngle(direction.angle - 90));
	}

	/**
	 * Turn the robot left.
	 */
	public Robot turnLeft() {
		return new PlacedRobot(x, y, Direction.fromAngle(direction.angle + 90));
	}

	/**
	 * Provides a simple text rendering of the string of the robot. If more complex
	 * rendering is required, this logic should be moved out of this class.
	 */
	@Override
	public String toString() {
		return x + "," + y + "," + direction;
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
		PlacedRobot other = (PlacedRobot) obj;
		if (direction != other.direction)
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public PlacedRobot(int x, int y, Direction direction) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

}
