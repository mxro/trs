package de.mxro.trs;

import de.mxro.trs.internal.OffTableRobot;
import de.mxro.trs.internal.PlacedRobot;

/**
 * <p>
 * Allows creating implementations of {@link RobotValidator}.
 * <p>
 * Currently there is only one implementation for asserting that the robot stays
 * within the bounds of a rectangular table. However, it is easy to add more
 * sophisticated validators, for instance for introducing obstacles to the
 * field.
 *
 */
public class Validators {

	/**
	 * <p>
	 * Asserts that the toy robot stays within a rectangular table of the provided
	 * dimensions.
	 * 
	 * @param tableWidth
	 *            The number of horizontal fields.
	 * @param tableHeight
	 *            The number of vertical fields.
	 * @return A new instance of {@link RobotValidator}
	 */
	public static RobotValidator onTable(int tableWidth, int tableHeight) {
		if (tableWidth <= 0 || tableHeight <= 0) {
			throw new IllegalArgumentException("Invalid table dimensions: " + tableWidth + ", " + tableHeight);
		}

		return robot -> {
			// A robot off the table is never on the table...
			if (robot instanceof OffTableRobot) {
				return false;
			}

			// A placed robot must stay within the bounds.
			if (robot instanceof PlacedRobot) {
				PlacedRobot placedRobot = (PlacedRobot) robot;
				return placedRobot.x >= 0 && placedRobot.y >= 0 && placedRobot.x <= tableWidth
						&& placedRobot.y <= tableHeight;
			}

			// Currently there are no more implementations of robot than the two listed
			// above.
			// The following exception is to aid future developers in debugging issues if
			// additional implementations of robot are introduced.
			throw new IllegalStateException("Unsupported subclass of " + Robot.class + ": " + robot.getClass());
		};
	}

}
